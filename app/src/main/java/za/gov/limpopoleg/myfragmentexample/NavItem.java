package za.gov.limpopoleg.myfragmentexample;

/**
 * Created by phaoe on 2015/11/05.
 */
class NavItem {
    String mTitle;
    String mSubtitle;
    int mIcon;

    public NavItem(String title, String subtitle, int icon) {
        mTitle = title;
        mSubtitle = subtitle;
        mIcon = icon;
    }
    public NavItem(String title, String subtitle, int icon, String image) {
        mTitle = title;
        mSubtitle = subtitle;
        mIcon = icon;
    }
}
