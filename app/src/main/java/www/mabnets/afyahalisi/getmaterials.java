package www.mabnets.afyahalisi;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class getmaterials extends Fragment {

    private WebView wv;
    private String document;
    private ProgressDialog progressDialog;
    public getmaterials() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View m=inflater.inflate(R.layout.fragment_getmaterials, container, false);
        Bundle bundle = getArguments();

        if (bundle != null) {
            document = bundle.getString("doc");


        }
        progressDialog=new ProgressDialog(getContext());
        String doc="<iframe src='http://docs.google.com/viewer?url=http://www.afyahalisi.mabnets.com/documents/"+ document+"&embedded=true'"+
                " width='100%' height='100%' style='border: none;'></iframe>";
        wv=(WebView)m.findViewById(R.id.fileWebView);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setAllowFileAccess(true);
        //wv.loadUrl(doc);
        wv.setWebChromeClient(new WebChromeClient());
        wv.loadData( doc , "text/html",  "UTF-8");

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
       return m;

    }

}
