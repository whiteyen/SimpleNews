package com.bilibili.news.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.bilibili.news.R;
import com.daimajia.numberprogressbar.NumberProgressBar;

public class WebViewActivity extends AppCompatActivity {
    public static final String URL = "url";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initView();
        initData();

    }
    String url;
    private void init() {
        Intent intent = getIntent();
        url = intent.getStringExtra(URL);
    }
    private WebView mWebView;
    private NumberProgressBar progressBar;
    private void initData() {

        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.setWebChromeClient(new WebChromeClient());
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        mWebView.setWebChromeClient(new ChromeClient());
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                loadUrl(url);
                return true;
            }
        });
        if(!TextUtils.isEmpty(url)){
            loadUrl(url);
        }
        mWebView.setDownloadListener(new MyWebViewDownloadListener());
    }
    private  class MyWebViewDownloadListener implements DownloadListener{
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK)&&mWebView.canGoBack()){
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private  class ChromeClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressBar.setProgress(newProgress);
            if(newProgress==100){
                progressBar.setVisibility(View.GONE);
            }else {
                progressBar.setVisibility(View.VISIBLE);
            }
        }
    }
    private void initView() {
        setContentView(R.layout.activity_web_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_launcher_foreground);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mWebView = findViewById(R.id.web_view);
        progressBar = findViewById(R.id.progressbar);
    }
    public void loadUrl(String url){
        if(mWebView!=null){
            mWebView.loadUrl(url);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mWebView!=null)mWebView.destroy();
    }

    @Override
    protected void onPause() {
        if(mWebView!=null)mWebView.onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mWebView!=null)mWebView.onResume();
    }
    public static Intent newIntent(Context context,String url){
        Intent intent = new Intent(context,WebViewActivity.class);
        intent.putExtra(URL,url);
        return intent;
    }
}
