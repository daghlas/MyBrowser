package com.daghlas.mybrowser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText urlText;
    ImageView clearText;
    ImageView navNext,navHome, navRefresh;
    WebView webView;
    @SuppressLint("StaticFieldLeak")
    static RelativeLayout searchBar, tag;
    @SuppressLint("StaticFieldLeak")
    static ProgressBar progressBar;

    static Animation exitTop, enterBottom;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.theme));

        urlText = findViewById(R.id.url);
        //launch keyboard when activity starts
        urlText.requestFocus();
        //InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        clearText = findViewById(R.id.cancel_icon);
        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progress_horizontal);
        searchBar = findViewById(R.id.search_bar);
        tag = findViewById(R.id.search_tag);

        navNext = findViewById(R.id.navForward);
        navHome = findViewById(R.id.navHome);
        navRefresh = findViewById(R.id.navRefresh);

        navNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webView.canGoForward()){
                    webView.goForward();
                }
            }
        });
        navRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    webView.reload();
            }
        });
        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Launch.class);
                startActivity(intent);
            }
        });

        exitTop = AnimationUtils.loadAnimation(this, R.anim.exit_top_anim);
        enterBottom = AnimationUtils.loadAnimation(this, R.anim.enter_bottom_anim);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }
        });

        //search entered text/url when user clicks go/done on keyboard
        urlText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_DONE){
                    //hide keyboard when user clicks the search button
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(urlText.getWindowToken(),0);
                    //load the page for the search value
                    loadMyUrl(urlText.getText().toString());
                    return true;
                }
                return false;
            }
        });

        //clear button, clear search bar
        clearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlText.setText("");
            }
        });

    }

    //method to load url/text from search bar
    void loadMyUrl(String url){
        boolean matchUrl = Patterns.WEB_URL.matcher(url).matches();
        //VISIBILITY
        if(matchUrl){
            webView.loadUrl(url);

        }else{
            webView.loadUrl("google.com/search?q="+url);

        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                searchBar.startAnimation(exitTop);
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //VISIBILITY
                        searchBar.setVisibility(View.INVISIBLE);
                        tag.setVisibility(View.VISIBLE);
                    }
                });
                tag.startAnimation(enterBottom);
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else{
            super.onBackPressed();
        }
    }

    //hide or show progress bar
    static class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}