package za.gov.limpopoleg.myfragmentexample;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MembersFragment extends Fragment {


    public MembersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = (View) inflater.inflate(R.layout.fragment_home, container, false);
        ListView navList = (ListView)mainView.findViewById(R.id.listView);

        

        ArrayList<NavItem> navArray = new ArrayList<NavItem>();
        navArray.add(new NavItem("Hon. T.K Samson", "Speaker", R.drawable.ic_action_home));

        DrawerListAdapter adapter = new DrawerListAdapter(getContext(), navArray, R.layout.drawer_item_2);
        navList.setAdapter(adapter);
        return mainView;
    }


}
