package com.example.erikagafe.hello_world;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebResourceRequest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.Queue;


public class MainActivity extends AppCompatActivity {

    Button button;
    EditText edit1;
    WebView wv1;
    TextView textview1;
    RequestQueue queue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        edit1 = (EditText) findViewById(R.id.editText);
        textview1 = (TextView) findViewById(R.id.textview1);
        wv1 = (WebView) findViewById(R.id.webView);
        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv1.setWebViewClient(new WebViewClient());
        wv1.setWebViewClient(new MyBrowser());
        queue = Volley.newRequestQueue(this);
        //https://www.tutorialspoint.com/android/android_webview_layout.htm
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = edit1.getText().toString();
                if (url.trim().startsWith("http")) {
                    //https://developer.android.com/training/volley/simple.html
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                public void onResponse(String response) {
                                    textview1.setText(response.substring(0,500));
                                }
                            }, new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            textview1.setText("That didn't work!");
                        }
                    });
                    queue.add(stringRequest);
                    wv1.loadUrl(url);
                } else {
                    //http://www.javatpoint.com/android-toast-example
                    Toast.makeText(getApplicationContext(), "Not a webpage", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
    //https://www.tutorialspoint.com/android/android_webview_layout.htm
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;

        }
    }
}

