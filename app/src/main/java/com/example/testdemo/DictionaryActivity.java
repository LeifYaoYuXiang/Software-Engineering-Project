package com.example.testdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
//creater YaoYuxiang
public class DictionaryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String words=bundle.getString("checkItem");
        Toast.makeText(this, "查询的单词是"+words, Toast.LENGTH_SHORT).show();



        WebView webView=findViewById(R.id.checkUpResult);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);

        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);



        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://dict.youdao.com/m/search?keyfrom=dict.mindex&q="+words);
    }
}
