package za.gov.limpopoleg.myfragmentexample;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderPaperFragment extends Fragment implements AdapterView.OnItemClickListener {

    HttpURLConnection urlConnection;
    InputStream in;
    String result;
    BufferedReader reader;
    StringBuilder stringBuilder;

    ArrayList<NavItem> navArray;
    ListView navList;

    ImageView leftNav, rightNav;
    TextView title;

    int index;

    private android.support.v4.app.FragmentManager fragmentManager;

    private ProgressBar progressBar;


    private int[] years;

    public OrderPaperFragment() {
        // Required empty public constructor
        years = new int[]{2015, 2014, 2013, 2012, 2011, 2010};
        index = 0;
    }
    public void setFragmentManager(android.support.v4.app.FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = (View) inflater.inflate(R.layout.fragment_hansard, container, false);

        try
        {
            navList = (ListView)mainView.findViewById(R.id.listView);
            navArray = new ArrayList<NavItem>();
            navList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            //navList.setOnItemClickListener(this);
            DrawerListAdapter adapter = new DrawerListAdapter(getContext(), navArray, R.layout.drawer_item_2);
            navList.setAdapter(adapter);
            navList.setOnItemClickListener(this);

            progressBar = (ProgressBar) mainView.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);

            leftNav = (ImageView) mainView.findViewById(R.id.leftNav);

            rightNav = (ImageView) mainView.findViewById(R.id.rightNav);
            rightNav.setClickable(true);

            title = (TextView) mainView.findViewById(R.id.title);
            title.setText(Calendar.getInstance().get(Calendar.YEAR)+"");

            /**
             * Add click listener for the left_nav imageView
             */
            leftNav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (index == 0) {
                        leftNav.setClickable(false);
                        leftNav.setImageResource(R.drawable.left_arrow_gray);
                        rightNav.setClickable(true);
                        rightNav.setImageResource(R.drawable.right_nav);

                    } else {
                        index--;
                        title.setText(years[index] + "");
                        leftNav.setImageResource(R.drawable.left_nav);
                        rightNav.setImageResource(R.drawable.right_nav);
                        rightNav.setClickable(true);
                    }
                    loadData(years[index]);
                }
            });

            /**
             * Add click listener for the right_nav imageView
             */

            rightNav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (index == years.length - 1) {
                        rightNav.setClickable(false);
                        rightNav.setImageResource(R.drawable.right_arrow_gray);
                    } else {
                        index++;
                        title.setText(years[index]+"");
                        rightNav.setImageResource(R.drawable.right_nav);
                        leftNav.setImageResource(R.drawable.left_nav);
                        leftNav.setClickable(true);
                    }
                    loadData(years[index]);
                }
            });


            loadData(Calendar.getInstance().get(Calendar.YEAR));
        }
        catch (Exception e){
            Log.e("log_tag", "Error" + e.getMessage());
        }
        return mainView;
    }
    /*
     * Retrieve members data from the server
     */
    private void loadData(final int year){
        new Thread(new Runnable(){
            @Override
            public void run() {
                //do network action in this function
                try{

                    String _url = "http://10.153.136.157/index1.php?action=hansard&year="+year;
                    URL url = new URL(_url);
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

                    bundle.putString("hansard", stringBuilder.toString());
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
            result = bundle.getString("hansard");

            try{
                // Hide progressbar
                progressBar.setVisibility(View.GONE);
                JSONArray jsonArray = new JSONArray(result);

                navArray.clear();
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject json_data = jsonArray.getJSONObject(i);
                    navArray.add(new NavItem(json_data.getString("heading").substring(10), json_data.getString("date"), R.drawable.minutes));
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(R.string.documents_url+""));
        startActivity(browserIntent);*/

        DownloadsFragment downlodFragment = new DownloadsFragment();
        //downlodFragment.setUrl(R.string.documents_url+"");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentholder, downlodFragment);
        fragmentTransaction.commit();
    }
}
