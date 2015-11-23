package za.gov.limpopoleg.myfragmentexample;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadsFragment extends Fragment {


    private ProgressBar progressBar;
    private String url;

    public DownloadsFragment() {
        // Required empty public constructor
    }

    public void setUrl(String url){
        this.url = url;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = (View) inflater.inflate(R.layout.fragment_downloads, container, false);
        WebView webView = (WebView) mainView.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setDisplayZoomControls(true);

        progressBar = (ProgressBar) mainView.findViewById(R.id.progressBar);
        webView.setWebViewClient(new MyWebViewClient(progressBar));

        webView.loadUrl("http://docs.google.com/viewer?url=" + "http://mikeschinkel.com/motorcycles/vstrom/manual/6B-Chassis.pdf"/* + this.url*/);
        return mainView;
    }


}
