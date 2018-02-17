package com.example.phoneapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class BrowsingActivity extends AppCompatActivity {

    TextView webAddress;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browsing);

        webAddress = (TextView)findViewById(R.id.webAddressField);

        Intent selectedThisBrowserIntent = this.getIntent();
        String intentAddress = selectedThisBrowserIntent.getDataString().toString();
        if(intentAddress != null && !intentAddress.isEmpty()){
            webView.loadUrl(intentAddress);
            webAddress.setText(intentAddress);
        }

        webAddress.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                switch (i) {
                    case (KeyEvent.KEYCODE_ENTER):
                    browseWeb(webAddress.getText().toString());
                }
                return false;
            }
        });
        webView = (WebView)findViewById(R.id.browserView);
        webView.setWebViewClient(new WebViewClient());
    }

    private void browseWeb(String address){
        if(address.contains("http://www.")){
            webView.loadUrl(address);
        } else if (address.contains("www.")){
            webView.loadUrl("http://"+address);
        } else {
            webView.loadUrl("http://www."+address);
        }
    }
}
