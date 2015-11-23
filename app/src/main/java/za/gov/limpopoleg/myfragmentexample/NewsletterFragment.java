package za.gov.limpopoleg.myfragmentexample;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsletterFragment extends Fragment {


    private ProgressBar progressBar;
    HttpURLConnection urlConnection;
    InputStream in;
    String result;
    BufferedReader reader;
    StringBuilder stringBuilder;

    ArrayList<NavItem> navArray;
    ListView navList;

    public NewsletterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = (View) inflater.inflate(R.layout.fragment_home, container, false);

        try
        {
            navList = (ListView)mainView.findViewById(R.id.listView);
            navArray = new ArrayList<NavItem>();
            navList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            //navList.setOnItemClickListener(this);
            DrawerListAdapter adapter = new DrawerListAdapter(getContext(), navArray, R.layout.drawer_item);
            navList.setAdapter(adapter);


            loadData();
        }
        catch (Exception e){
            Log.e("log_tag","Error" + e.getMessage());
        }
        return mainView;
    }

    /*
     * Retrieve members data from the server
     */
    private void loadData(){
        new Thread(new Runnable(){
            @Override
            public void run() {
                //do network action in this function
                try{

                    URL url = new URL("http://10.153.136.157/index1.php?action=programme");
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

                    Message msg = handler.obtainMessage();
                    Bundle bundle = new Bundle();

                    bundle.putString("programme", stringBuilder.toString());
                    msg.setData(bundle);
                    handler.sendMessage(msg);

                }catch(Exception e){
                    Log.e("log_tag", "Error in http connection " + e.toString());
                }
            }
        }).start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            result = bundle.getString("programme");

            try{
                JSONArray jsonArray = new JSONArray(result);

                for(int i=0;i<jsonArray.length();i++){
                    JSONObject json_data = jsonArray.getJSONObject(i);
                    navArray.add(new NavItem(json_data.getString("heading").substring(10), json_data.getString("date"), R.drawable.ic_action_publications));
                }
            }
            catch(JSONException je){
                Log.e("log_tag", "Error converting result "+je.toString());
            }
            catch (NullPointerException npe){
                Log.e("log_tag", "Error Null pointer " + npe.toString());
            }

            DrawerListAdapter adapter = new DrawerListAdapter(getContext(), navArray, R.layout.drawer_item);
            navList.setAdapter(adapter);
        }
    };

}
