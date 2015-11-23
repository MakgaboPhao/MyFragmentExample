package za.gov.limpopoleg.myfragmentexample;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ActionBarDrawerToggle actionBarToggle;
    private ListView navList;
    private DrawerLayout drawerLayout;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerlayout);
        navList = (ListView)findViewById(R.id.navlist);

        actionBarToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer);

        drawerLayout.setDrawerListener(actionBarToggle);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Home");

        ArrayList<NavItem> navArray = new ArrayList<NavItem>();
        navArray.add(new NavItem("Home", "Our home",R.drawable.ic_action_home));
        navArray.add(new NavItem("Members", "Members of provincial legislature", R.drawable.ic_action_members));
        navArray.add(new NavItem("Order Paper", "Orders of the day", R.drawable.order_paper));
        navArray.add(new NavItem("Hansard", "Verbatim minutes of the house", R.drawable.minutes));
        navArray.add(new NavItem("Weekly Programme", "Action plan for the week", R.drawable.ic_action_folder_2));
        navArray.add(new NavItem("Legistalk", "Newsletter of the Legislature", R.drawable.newsletter));
        navArray.add(new NavItem("Contact Us", "Get to know about us", R.drawable.ic_action_info));

        navList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        navList.setSelector(android.R.color.holo_orange_light);

        DrawerListAdapter adapter = new DrawerListAdapter(this, navArray, R.layout.drawer_item);
        navList.setAdapter(adapter);
        navList.setOnItemClickListener(this);

        fragmentManager = getSupportFragmentManager();
        loadSelection(0);
    }

    private void loadSelection(int i){
        navList.setItemChecked(i, true);

        switch (i){
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                homeFragment.setActionBar(actionBar);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentholder, homeFragment);
                fragmentTransaction.commit();
                break;
            case 1:
                MembersFragment membersFragment = new MembersFragment();
                membersFragment.setActionBar(actionBar);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentholder, membersFragment, "members");
                fragmentTransaction.commit();

                break;
            case 2:
                OrderPaperFragment  orderPaperFragment = new OrderPaperFragment();
                orderPaperFragment.setActionBar(actionBar);
                orderPaperFragment.setFragmentManager(fragmentManager);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentholder, orderPaperFragment);
                fragmentTransaction.commit();
                break;
            case 3:
                HansardFragment hansardFragments = new HansardFragment();
                hansardFragments.setActionBar(actionBar);
                hansardFragments.setFragmentManager(fragmentManager);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentholder, hansardFragments);
                fragmentTransaction.commit();
                break;

            case 4:
                WeeklyProgrammeFragment weeklyProgrammeFragment = new WeeklyProgrammeFragment();
                weeklyProgrammeFragment.setActionBar(actionBar);
                weeklyProgrammeFragment.setFragmentManager(fragmentManager);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentholder, weeklyProgrammeFragment);
                fragmentTransaction.commit();
                break;
            case 5:
                NewsletterFragment newsletterFragment = new NewsletterFragment();
                newsletterFragment.setActionBar(actionBar);
                newsletterFragment.setFragmentManager(fragmentManager);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentholder, newsletterFragment);
                fragmentTransaction.commit();
                break;

            case 6:
                AboutFragment aboutFragment = new AboutFragment();
                aboutFragment.setActionBar(actionBar);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentholder, aboutFragment);
                fragmentTransaction.commit();
                break;
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarToggle.syncState();
        //fragmentManager.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id==android.R.id.home){
            if(drawerLayout.isDrawerOpen(navList)){
                drawerLayout.closeDrawer(navList);
            }else {
                drawerLayout.openDrawer(navList);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        loadSelection(position);

        drawerLayout.closeDrawer(navList);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

}
