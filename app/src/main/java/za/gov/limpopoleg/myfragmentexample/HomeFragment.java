package za.gov.limpopoleg.myfragmentexample;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private ProgressBar progressBar;
    private HttpURLConnection urlConnection;
    private InputStream in;
    private BufferedReader reader;
    private StringBuilder stringBuilder;
    private TextView news;
    private String result;
    private ActionBar actionBar;
    private TextView message;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = (View) inflater.inflate(R.layout.fragment_index, container, false);
        news = (TextView) mainView.findViewById(R.id.feed);
        progressBar = (ProgressBar) mainView.findViewById(R.id.progressBar);
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
        this.actionBar.setTitle("Limpopo Legislature");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
                    Looper.prepare();
                    URL url = new URL("http://10.153.136.157/index1.php?action=news");
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

                    bundle.putString("news", stringBuilder.toString());
                    msg.setData(bundle);
                    handler.sendMessage(msg);

                }
                catch (ConnectException ce){
                    Log.e("log_tag", "Error in http connection " + ce.toString());
                    AppStatus.getInstance(getContext()).emptyMessage(handler);
                }
                catch(Exception e){
                    Log.e("log_tag", "General Error "+e.toString());
                    AppStatus.getInstance(getContext()).emptyMessage(handler);
                }
            }
        }).start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            result = bundle.getString("news");

            if(result != null) {
                try {
                    progressBar.setVisibility(View.GONE);
                    JSONArray jsonArray = new JSONArray(result);
                    JSONObject json_data = jsonArray.getJSONObject(0);
                    news.setText(json_data.getString("article"));

                } catch (JSONException je) {
                    Log.e("log_tag", "Error: Json Exception " + je.toString());
                } catch (NullPointerException npe) {
                    Log.e("log_tag", "Error Null pointer " + npe.toString());
                }
            }
            else{
                message.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

        }
    };
}
