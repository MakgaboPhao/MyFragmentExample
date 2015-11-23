package za.gov.limpopoleg.myfragmentexample;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadsFragment extends Fragment {


    private ProgressBar progressBar;

    public DownloadsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = (View) inflater.inflate(R.layout.fragment_committee_reports, container, false);
        WebView webView = (WebView) mainView.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        progressBar = (ProgressBar) mainView.findViewById(R.id.progressBar);
        webView.setWebViewClient(new MyWebViewClient(progressBar));

        webView.loadUrl("https://www.googles.co.za");
        return mainView;
    }


}
