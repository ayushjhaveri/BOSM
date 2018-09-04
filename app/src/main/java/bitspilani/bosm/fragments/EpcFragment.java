package bitspilani.bosm.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import bitspilani.bosm.HomeActivity;
import bitspilani.bosm.R;


/**
 * Created by Prashant on 14-05-2017.
 */

public class EpcFragment extends Fragment {

    ProgressBar progress;

    public EpcFragment(){
        HomeActivity.currentFragment.equals("EpcFragment");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_web, container, false);


        final WebView webview = (WebView)rootView.findViewById(R.id.webview);
        progress = (ProgressBar) rootView.findViewById(R.id.progressBar);
        progress.setVisibility(View.GONE);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new MyWebViewClient());

        webview.loadUrl("https://epcbits.wordpress.com");

        return rootView;
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progress.setVisibility(View.GONE);
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progress.setVisibility(View.VISIBLE);
            super.onPageStarted(view, url, favicon);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.currentFragment="EpcFragment";
    }
}
