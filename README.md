# Invention_SimpleHeart
Webview的初步尝试，实现了加载h5小游戏和webview基本缓存机制



整体架构：

InventionActivity：实现UI的实例化，基本的按钮Activity之间跳转

GameActivity：实现UI的实例化，Webview的基本使用

MyProgressDialog：UI进度对话框属性类

依赖库类：

SystemBarTintManager + Config =  实现兼容版本沉浸状态栏

----------------------我是天天酷涛---------------------------

Webview 运行加载 H5小游戏

Webview基本属性

WebSettings ：

WebSettings setting = webView.getSettings();
setting.setUseWideViewPort(true); 　　//将图片调整至适合Webview大小
settings.setSupportZoom(true);//支持缩放
settings.setLoadWithOverviewMode(true);//缩放至屏幕大小
settings.setLoadsImagesAutomatically(true);//支持自动加载图片
settings.setDomStorageEnabled(true); //开启dom storage AOI功能
//进行缓存

settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//设置缓存模式
settings.setAppCacheEnabled(true);//加载缓存
String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/gamecache";
Log.d("GameActivity", path);
settings.setAppCachePath(path);　　//设计缓存路径
//加载JS
settings.setJavaScriptEnabled(true);//支持js
webView.loadUrl(msString);//加载网页


//禁止第三方浏览器打开

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
//借用了网上h5小游戏的资源








