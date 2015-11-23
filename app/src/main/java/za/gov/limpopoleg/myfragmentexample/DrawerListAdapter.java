package za.gov.limpopoleg.myfragmentexample;

/**
 * Created by phaoe on 2015/11/05.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

class DrawerListAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<NavItem> mNavItems;
    int drawer;
    ImageView actionIconView;

    public DrawerListAdapter(Context context, ArrayList<NavItem> navItems, int drawer) {
        mContext = context;
        mNavItems = navItems;
        this.drawer = drawer;
    }

    @Override
    public int getCount() {
        return mNavItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mNavItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(drawer, null);
        }
        else {
            view = convertView;
        }

        TextView titleView = (TextView) view.findViewById(R.id.title);
        TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);
        ImageView iconView = (ImageView) view.findViewById(R.id.icon);

        if(drawer == R.id.icon_2){
            actionIconView = (ImageView) view.findViewById(R.id.icon_2);
        }

        titleView.setText( mNavItems.get(position).mTitle );
        subtitleView.setText(mNavItems.get(position).mSubtitle);
        iconView.setImageResource(mNavItems.get(position).mIcon);
        if(drawer == R.id.icon_2){
            actionIconView.setImageResource(R.drawable.ic_action_arrow);
        }

        return view;
    }
}
