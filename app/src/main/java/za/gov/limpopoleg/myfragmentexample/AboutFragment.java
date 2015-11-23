package za.gov.limpopoleg.myfragmentexample;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {


    private ActionBar actionBar;
    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = (View) inflater.inflate(R.layout.fragment_about, container, false);
        return mainView;
    }


    public void setActionBar(ActionBar actionBar){
        this.actionBar = actionBar;
        this.actionBar.setTitle("About Us");
    }

}
