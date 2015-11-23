package za.gov.limpopoleg.myfragmentexample;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MembersFragment extends Fragment implements AdapterView.OnItemClickListener {


    private HttpURLConnection urlConnection;
    private String result;
    private BufferedReader reader;
    private StringBuilder stringBuilder;
    private ArrayList<NavItem> navArray;
    private ListView navList;
    private ImageView imageView;
    private TextView message;
    private ActionBar actionBar;
    private DrawerLayout drawerLayout;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ProgressBar progressBar;

    public MembersFragment() {
        // Required empty public constructor
        result = "";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = (View) inflater.inflate(R.layout.fragment_home, container, false);
        navList = (ListView)mainView.findViewById(R.id.listView);
        imageView = (ImageView) mainView.findViewById(R.id.icon);
        navArray = new ArrayList<NavItem>();

        navList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        navList.setOnItemClickListener(this);

        DrawerListAdapter adapter = new DrawerListAdapter(getContext(), navArray, R.layout.drawer_item);
        navList.setAdapter(adapter);

        progressBar = (ProgressBar)mainView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        message = (TextView) mainView.findViewById(R.id.message);
        message.setVisibility(View.GONE);

        if(AppStatus.getInstance(getContext()).isOnline()){
            loadData();
        }else {
            message.setVisibility(View.VISIBLE);
        }

        return mainView;
    }

    public void setActionBar(ActionBar actionBar){
        this.actionBar = actionBar;
        this.actionBar.setTitle("Members of the Legislature");
    }

    private void loadSelection(int i){
        navList.setItemChecked(i, true);
    }


    /*
     * Retrieve members data from the server
     */
    private void loadData(){
        new Thread(new Runnable(){
            @Override
            public void run() {
                //do network action in this function
                Message msg;
                Bundle bundle;
                try{
                    URL url = new URL("http://10.153.136.157/index1.php?action=members");
                    urlConnection = (HttpURLConnection)url.openConnection();
                    urlConnection.setRequestMethod("POST");

                    reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    stringBuilder = new StringBuilder();

                    //convert response to string

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        stringBuilder.append(line + "\n");
                    }

                    msg = handler.obtainMessage();
                    bundle = new Bundle();

                    bundle.putString("members", stringBuilder.toString());
                    msg.setData(bundle);
                    handler.sendMessage(msg);

                }
                catch (ConnectException ce) {
                    Log.e("log_tag", "Error in http connection " + ce.toString());
                    AppStatus.getInstance(getContext()).emptyMessage(handler);
                }
                catch(Exception e){
                    Log.e("log_tag", "General Error " + e.toString());
                    AppStatus.getInstance(getContext()).emptyMessage(handler);
                }
            }
        }).start();
    }



    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            result = bundle.getString("members");

            if(result != null){

                try{
                    // Hide progressbar
                    progressBar.setVisibility(View.GONE);
                    JSONArray jsonArray = new JSONArray(result);

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject json_data = jsonArray.getJSONObject(i);
                        navArray.add(new NavItem(json_data.getString("lname"), json_data.getString("designation") + " - " + json_data.getString("abbreviation"), R.drawable.ic_action_members));
                        //getRemoteImage(json_data.getString("photo"));
                    }
                    DrawerListAdapter adapter = new DrawerListAdapter(getContext(), navArray, R.layout.drawer_item);
                    navList.setAdapter(adapter);
                }
                catch(JSONException je){
                    Log.e("log_tag", "Error converting result "+je.toString());
                }
                catch (NullPointerException npe){
                    Log.e("log_tag", "Error Null pointer " + npe.toString());
                }
            }
            else{
                message.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        loadSelection(position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("members", result);
        Log.i("Saving state", "members");
    }
}
