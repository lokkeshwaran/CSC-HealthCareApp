package andyfolders.com.csc;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class list_mrecord extends ActionBarActivity {

    ProgressBar Pbar;
    WebView myWebView;

    public boolean isNetworkAvailable() {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ist_mrecord);

        final String pid =  getIntent().getStringExtra("pid");

        myWebView = (WebView) findViewById(R.id.webView2);
        myWebView.setWebViewClient(new WebViewClient());
        Pbar = (ProgressBar) findViewById(R.id.pB1);



        myWebView.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = myWebView.getSettings();
        myWebView.getSettings().setJavaScriptEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        if (isNetworkAvailable()) {


            myWebView.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    if (progress < 80 && Pbar.getVisibility() == ProgressBar.GONE) {
                        Pbar.setVisibility(ProgressBar.VISIBLE);

                    }
                    Pbar.setProgress(progress);
                    if (progress == 100) {
                        Pbar.setVisibility(ProgressBar.GONE);

                    }
                }
            });
            myWebView.loadUrl("http://lokkeshwaran.cu.ma/csccoin/listmrecord.php?pid="+pid);



        } else {
            myWebView.loadUrl("file:///android_asset/testfail.html");
        }


    }

    @Override
    public void onBackPressed() {

        if (myWebView.canGoBack())
            myWebView.goBack();
        else {
            finish();

        }

    }
}
