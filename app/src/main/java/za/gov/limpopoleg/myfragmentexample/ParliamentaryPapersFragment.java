package za.gov.limpopoleg.myfragmentexample;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ParliamentaryPapersFragment extends Fragment {


    private ProgressBar progressBar;

    public ParliamentaryPapersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = (View)inflater.inflate(R.layout.fragment_parliamentary_papers, container, false);
        ListView navList = (ListView)mainView.findViewById(R.id.listView);

        ArrayList<NavItem> navArray = new ArrayList<NavItem>();
        navArray.add(new NavItem("Order Paper", "Orders of the day", R.drawable.ic_action_folder_2));
        navArray.add(new NavItem("Bills Introduced", "New bills of the Legislature", R.drawable.ic_action_folder_2));
        navArray.add(new NavItem("Committee Reports", "Reports of the portfolio committees", R.drawable.ic_action_folder_2));
        navArray.add(new NavItem("Hansard", "Verbatim minutes of the house", R.drawable.ic_action_folder_2));
        navArray.add(new NavItem("Weekly Programme", "Action of the Legislature for the week", R.drawable.ic_action_folder_2));

        DrawerListAdapter adapter = new DrawerListAdapter(getContext(), navArray, R.layout.drawer_item_2);
        navList.setAdapter(adapter);
        return mainView;
    }


}
