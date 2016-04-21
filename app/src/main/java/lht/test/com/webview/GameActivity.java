package lht.test.com.webview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import lht.test.com.UI.Config;
import lht.test.com.UI.MyProgressDialog;
import lht.test.com.invention_simpleheart.R;

public class GameActivity extends AppCompatActivity {

    private WebView webView;
    private String msString = "";
    private ImageButton return_button;
    private TextView title_tv;
    private WebSettings settings;
    private MyProgressDialog myProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //设置沉浸状态栏
        Config.setStatusTrans(this);
        Intent intent = getIntent();
        msString = intent.getStringExtra("msString");
        init();
    }
    private void init(){
        return_button = (ImageButton) findViewById(R.id.title_bt_back);
        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title_tv = (TextView) findViewById(R.id.title_tv);
        if(msString.equals("http://yx.h5uc.com/2048/")){
            title_tv.setText("心理2048");
        }else{
            title_tv.setText("霍兰德测试");
        }


        myProgressDialog = new MyProgressDialog(this);
        myProgressDialog.setTitle("页面加载中，请稍候...");
        myProgressDialog.setMax(100);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            myProgressDialog.create();
        }

        //Webview
        webView = (WebView) findViewById(R.id.webView1);

        //支持js要设置webView.getSettings();
        settings = webView.getSettings();
        settings.setUseWideViewPort(true);//将图片调整至适合webview的大小
        settings.setSupportZoom(true);//支持缩放
        settings.setLoadWithOverviewMode(true);//缩放至屏幕大小
        settings.setLoadsImagesAutomatically(true);//支持自动加载图片
        settings.setDomStorageEnabled(true); //开启dom storage AOI功能

        //缓存问题 在没有网络的情况下，也可以打开网页
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//设置缓存模式
        settings.setAppCacheEnabled(true);//加载缓存
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/gamecache";
        Log.d("GameActivity", path);
        settings.setAppCachePath(path);

        //加载JS
        settings.setJavaScriptEnabled(true);//支持js


        webView.loadUrl(msString);//加载网页
        //覆盖浏览器
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(msString);
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.setWebChromeClient(new WebChromeClientProgress());
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                myProgressDialog.show();
                webView.setVisibility(View.GONE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (myProgressDialog.isShowing()){
                    myProgressDialog.dismiss();
                    webView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                view.stopLoading();
                view.clearView();
                String data="游戏暂时出错了，正在修复哟。";
                view.loadUrl("javascript:document.body.innerHTML=\"" + data + "\"");
            }
        });
        }

        private class WebChromeClientProgress extends WebChromeClient{
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                myProgressDialog.setProgress(newProgress);

                if(myProgressDialog !=null){

                if(newProgress >= 100){
                    myProgressDialog.dismiss();
                    Log.d("GameActivity","100");
                }

            }
            super.onProgressChanged(view, newProgress);
        }
        }

    //返回上一个加载的网页
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()){
            //返回键退回
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
