package com.example.administrator.test;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import static com.example.administrator.test.R.id.webview;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    String Url = "http://m.sieuthicobap.com/";
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(webview);
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mConnect = connManager.getActiveNetworkInfo();
        WebSettings set = webView.getSettings();
        set.setJavaScriptEnabled(true);
        Mywebview webViewClient = new Mywebview();
        webView.setWebViewClient(webViewClient);

        if (mConnect != null && mConnect.isConnected() == true) {
            webView.loadUrl(Url);
        } else {
            finish();
            Toast.makeText(MainActivity.this, "Không có mạng , bạn hãy kết nối mạng", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();

        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    public class Mywebview extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (url.startsWith("tel:0933267788") || url.startsWith("sms:0933267788")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;

            }else {
                ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mConnect = connManager.getActiveNetworkInfo();
                if (mConnect != null && mConnect.isConnected() == true){
                    view.loadUrl(url);
                }else {
                    Toast.makeText(MainActivity.this,"Bạn hãy kiểm tra lại kết nối",Toast.LENGTH_SHORT).show();
                }

            }
            return true;
        }

    }

}
