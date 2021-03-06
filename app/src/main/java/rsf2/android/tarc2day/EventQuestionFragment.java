package rsf2.android.tarc2day;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventQuestionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventQuestionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Message[] messageArray;
    private User user;
    private boolean admin;
    String passedEventId;
    CustomMessageListAdapter messageListAdapter;
    List<Message> messageList;
    ListView messageListView;

    private OnFragmentInteractionListener mListener;

    public EventQuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventQuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventQuestionFragment newInstance(String param1, String param2) {
        EventQuestionFragment fragment = new EventQuestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_event_question, container,false);

        final Bundle bundle = getArguments();
        //Get back the parcel
        //But cant cast parcel into message array
        // Need to copy the data from parcel back into a messge array

        passedEventId = getArguments().getString("eventId");

        //This is to retrieve message for the activity
        Parcelable[] messageParcel = bundle.getParcelableArray("messageArray");
        messageArray = Arrays.copyOf(messageParcel,messageParcel.length,Message[].class);
        messageListAdapter = new CustomMessageListAdapter(getContext(),convertToArrayList());

        messageListView = (ListView) view.findViewById(R.id.list_of_messages);
        messageListView.setAdapter(messageListAdapter);

        FloatingActionButton btnSend = (FloatingActionButton) view.findViewById(R.id.fab);

        messageListView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });



        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editTextMessage = (EditText) view.findViewById(R.id.inputMessage);
                String messageText = editTextMessage.getText().toString();

                if(!messageText.trim().equals("")) {
                    //This is to post the message
                    //Retrieve the necessary data

                    String eventId = bundle.getString("eventId");
                    //Get shared preference to get the user id
                    getUserData();

                    //Get the current date of user comment post
                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String messageDate = simpleDateFormat.format(date);

                    BackgroundPostMessageTask backgroundPostMessageTask = new BackgroundPostMessageTask();
                    backgroundPostMessageTask.execute(user.getUsername(), eventId, messageText, messageDate);
                    new BackgroundMessageTask().execute();

                    editTextMessage.setText("");
                }
                else{
                    Toast.makeText(getActivity(),"Please enter some text",Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    protected void getUserData() {

        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPreferences",Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(Config.TAG_USER, "");

        user = gson.fromJson(json,User.class);
        admin = sharedPreferences.getBoolean(Config.TAG_ADMIN,false);

    }

    class BackgroundPostMessageTask extends AsyncTask<String,Void,String> {

        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            loading = ProgressDialog.show(getContext(),"Posting message","Please wait...",true,true);
        }

        @Override
        protected String doInBackground(String... params) {

            HashMap<String,String> parameter = new HashMap<>();
            parameter.put("username",params[0]);
            parameter.put("eventId",params[1]);
            parameter.put("messageText",params[2]);
            parameter.put("messageDate",params[3]);

            RequestHandler rh = new RequestHandler();
            String res = rh.sendPostRequest(Config.URL_POST_MESSAGE, parameter);
            return res;


        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            loading.dismiss();
            if(!response.isEmpty()) {
                Toast.makeText(getContext(),"Successfully posted!", Toast.LENGTH_SHORT).show();

                //getActivity().finish();


            } else {
                Toast.makeText(getContext(),"Failed to post", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void refreshFragment(Message[] messageArray) {

    }

    public ArrayList convertToArrayList(){
        ArrayList<Message> arrayList = new ArrayList<Message>(Arrays.asList(messageArray));
        return  arrayList;
    }

    class BackgroundMessageTask extends AsyncTask<Void,Void,String>{
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            loading = ProgressDialog.show(getContext(),"Refreshing Messages","Please wait...",true,true);
        }

        @Override
        protected String doInBackground(Void... params) {
            RequestHandler requestHandler = new RequestHandler();
            String response = requestHandler.sendGetRequest("http://thammingkeat.esy.es/GetMessage.php?eventId="+passedEventId);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            messageList = new ArrayList<Message>();

            try {
                JSONArray jsonArray = new JSONArray(result);
                for(int i = 0; i < jsonArray.length(); i++) {
                    String messageId = jsonArray.getJSONObject(i).getString("messageId");
                    String username = jsonArray.getJSONObject(i).getString("username");
                    String eventId = jsonArray.getJSONObject(i).getString("eventId");
                    String message = jsonArray.getJSONObject(i).getString("message");
                    String messageDate = jsonArray.getJSONObject(i).getString("messageDate");
                    String name = jsonArray.getJSONObject(i).getString("name");

                    Message newMessage = new Message(messageId,username,eventId,message,messageDate,name);

                    messageList.add(newMessage);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            messageListAdapter.messages().clear();
            messageListAdapter.messages().addAll(messageList);
            messageListAdapter.notifyDataSetChanged();

            messageListView.post(new Runnable() {
                @Override
                public void run() {
                    // Select the last row so it will scroll into view...
                    messageListView.setSelection(messageListAdapter.getCount() - 1);
                }
            });

            loading.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}
