package www.mabnets.afyahalisi;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class getjournals extends Fragment {
    private String url;
    private WebView wv;
    private ProgressDialog progressDialog;
    final String Tag=this.getClass().getName();

    public getjournals() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View jnlz=inflater.inflate(R.layout.fragment_getjournals, container, false);
        wv=(WebView)jnlz.findViewById(R.id.WebView);
        Bundle bundle = getArguments();
        if (bundle != null) {
            url = bundle.getString("url");
            Log.d(Tag, url);
        }
        progressDialog = new ProgressDialog(getContext());


        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setAllowFileAccess(true);
        wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        wv.clearHistory();
        wv.clearCache(true);
        wv.requestFocus(View.FOCUS_DOWN);
        wv.setFocusable(true);
        wv.setFocusableInTouchMode(true);
        wv.getSettings().setDomStorageEnabled(true);
        wv.getSettings().setDatabaseEnabled(true);
        wv.getSettings().setAppCacheEnabled(true);
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        wv.getSettings().setPluginState(WebSettings.PluginState.ON);
        wv.getSettings().setJavaScriptEnabled(true);
        /*   wv.loadUrl("http://10.0.2.2/benny/request.php");*/
        wv.loadUrl("http://"+url);
        wv.setWebChromeClient(new WebChromeClient());
        if (Build.VERSION.SDK_INT >= 19) {
            wv.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 19) {
            wv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressDialog.setMessage("Loading please wait");
                progressDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressDialog.dismiss();
            }


            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast toast = Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_LONG);
                toast.show();
                AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
                alert.setMessage("please check your internet connection");
                alert.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Fragment fragment=new main();
                        getFragmentManager().beginTransaction().replace(R.id.framelayout,fragment).addToBackStack(null).commit();

                    }
                });

                alert.show();
            }
        });
        return jnlz;
    }

}
