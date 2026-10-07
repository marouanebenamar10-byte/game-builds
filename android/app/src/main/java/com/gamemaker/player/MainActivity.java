package com.gamemaker.player;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;

public class MainActivity extends Activity {
  private WebView web;
  @Override protected void onCreate(Bundle b) {
    super.onCreate(b);
    web = new WebView(this);
    WebSettings s = web.getSettings();
    s.setJavaScriptEnabled(true);
    s.setDomStorageEnabled(true);
    s.setMediaPlaybackRequiresUserGesture(false);
    web.setWebViewClient(new WebViewClient());
    web.addJavascriptInterface(new Bridge(), "AndroidGame");
    setContentView(web);
    web.loadUrl("https://gamemakerstudio.lovable.app/play");
  }
  @Override public void onBackPressed() { if (web.canGoBack()) web.goBack(); else super.onBackPressed(); }
  class Bridge {
    @JavascriptInterface public String getGame() {
      try (InputStream in = getAssets().open("game.json")) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[8192]; int n;
        while ((n = in.read(buf)) > 0) out.write(buf, 0, n);
        return out.toString("UTF-8");
      } catch (Exception e) { return ""; }
    }
  }
}
