package com.team.smart.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.team.smart.R;
import com.team.smart.blockchain.Web3jAPI;

public class WalletHistoryActivity extends Activity {

    private WebView webView;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Web3jAPI web3jAPI = Web3jAPI.getInstance();
        String address = Web3jAPI.getCredentials().getAddress();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_wallet_history);
        init(address);
    }

    private void init(String address){

        webView = (WebView) findViewById(R.id.webview_txs);
        //WebView는 로컬 리소스를로드합니다
        //webView.loadUrl("file:///android_asset/example.html");
        //WebView 로딩 웹 리소스
        webView.loadUrl("https://ropsten.etherscan.io/address/"+address);
        System.out.println("https://ropsten.etherscan.io/address/"+address);
        //WebView에서 웹 페이지를 열 수 있도록 타사 또는 시스템 브라우저를 통해 웹 페이지를 여는 WebView의 기본 동작을 재정의하십시오.
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //반환값이 true이면 제어 페이지가 WebView에서 열리고, false이면 시스템 브라우저 또는 타사 브라우저가 열리도록 호출됩니다.
                view.loadUrl(url);
                return true;
            }
            //WebViewClient는 WebView가 일부 페이지 제어 및 요청 알림을 처리하도록 도와줍니다.
        });
        //자바 스크립트 지원 활성화
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        //WebView로드 페이지가 캐시로드보다 우선합니다
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //페이지 로딩
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //newProgress   1-100之间的整数
                if (newProgress == 100) {
                    //페이지 로딩이 완료되었습니다. ProgressDialog를 닫으십시오
                    closeDialog();
                } else {
                    //페이지가로드 중입니다. ProgressDialog를여십시오.
                    openDialog(newProgress);
                }
            }

            private void openDialog(int newProgress) {
                if (dialog == null) {
                    dialog = new ProgressDialog(WalletHistoryActivity.this);
                    dialog.setTitle("로딩");
                    dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    dialog.setProgress(newProgress);
                    dialog.setCancelable(true);
                    dialog.show();
                } else {
                    dialog.setProgress(newProgress);
                }
            }

            private void closeDialog() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                    dialog = null;
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();   //이전 페이지로 돌아 가기
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
