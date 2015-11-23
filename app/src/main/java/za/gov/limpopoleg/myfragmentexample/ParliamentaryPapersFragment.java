package za.gov.limpopoleg.myfragmentexample;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ParliamentaryPapersFragment extends Fragment implements AdapterView.OnItemClickListener {


    //private ProgressBar progressBar;
    ListView navList;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    public ParliamentaryPapersFragment() {
        // Required empty public constructor
        //fragmentManager =
    }

    public void setFragmentManager(FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = (View)inflater.inflate(R.layout.fragment_home, container, false);
        navList = (ListView)mainView.findViewById(R.id.listView);

        ArrayList<NavItem> navArray = new ArrayList<NavItem>();
        navArray.add(new NavItem("Order Paper", "Orders of the day", R.drawable.order_paper));
        //navArray.add(new NavItem("Committee Reports", "Reports of the portfolio committees", R.drawable.ic_action_folder_2));
        navArray.add(new NavItem("Hansard", "Verbatim minutes of the house", R.drawable.minutes));
        navArray.add(new NavItem("Weekly Programme", "Action plan for the week", R.drawable.ic_action_folder_2));
        navArray.add(new NavItem("Legistalk", "Newsletter of the Legislature", R.drawable.newsletter));

        navList.setOnItemClickListener(this);
        DrawerListAdapter adapter = new DrawerListAdapter(getContext(), navArray, R.layout.drawer_item_2);
        navList.setAdapter(adapter);

        return mainView;
    }


    private void loadSelection(int i){
        navList.setItemChecked(i, true);

        switch(i){
            case 0:
                OrderPaperFragment orderPaperFragment = new OrderPaperFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentholder, orderPaperFragment);
                fragmentTransaction.commit();
                break;
            case 1:
                HansardFragment hansardFragments = new HansardFragment();
                hansardFragments.setFragmentManager(fragmentManager);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentholder, hansardFragments);
                fragmentTransaction.commit();
                break;
            case 2:
                WeeklyProgrammeFragment weeklyProgrammeFragment = new WeeklyProgrammeFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentholder, weeklyProgrammeFragment);
                fragmentTransaction.commit();
                break;
            case 3:
                NewsletterFragment newsletterFragment = new NewsletterFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentholder, newsletterFragment);
                fragmentTransaction.commit();
                break;
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        loadSelection(position);
    }
}
