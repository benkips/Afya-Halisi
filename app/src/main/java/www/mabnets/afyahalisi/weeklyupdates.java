package www.mabnets.afyahalisi;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kosalgeek.android.json.JsonConverter;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class weeklyupdates extends Fragment {
    private RecyclerView rvwku;
    private Mycommand mycommand;
    final String Tag=this.getClass().getName();
    private ProgressDialog pd;

    public weeklyupdates() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View wku=inflater.inflate(R.layout.fragment_weeklyupdates, container, false);
        rvwku=(RecyclerView)wku.findViewById(R.id.rvwk);
        mycommand=new Mycommand(getContext());
        pd=new ProgressDialog(getContext());
        pd.setMessage("Loading");


        rvwku.setHasFixedSize(true);
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        rvwku.setLayoutManager(manager);
        loadweekupdtes();
      return wku;
    }
    private void loadweekupdtes(){

        String url="http://www.afyahalisi.mabnets.com/android/updates.php";
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                Log.d(Tag,response);
                if(!response.isEmpty()){
                    if(response.contains("No updates found")){
                        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d(Tag, response);
                        ArrayList<week> materlist= new JsonConverter<week>().toArrayList(response, week.class);
                       weekadapter weekadapter=new weekadapter(materlist,getContext());
                        rvwku.setAdapter(weekadapter);

                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error!=null) {
                    Log.d(TAG, error.toString());
                    if (error instanceof TimeoutError) {
                        pd.dismiss();
                        AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
                        alert.setMessage("please check your internet connectivity");
                        alert.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.exit(0);
                            }
                        });
                        alert.show();
                    } else if (error instanceof NoConnectionError) {
                        pd.dismiss();
                        AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
                        alert.setMessage("please check your internet connectivity");
                        alert.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.exit(0);
                            }
                        });
                        alert.show();
                    } else if (error instanceof NetworkError) {
                        pd.dismiss();
                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                        alert.setMessage("please check your internet connectivity");
                        alert.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.exit(0);
                            }
                        });
                        alert.show();
                    }else if (error instanceof AuthFailureError) {
                        pd.dismiss();
                        Toast.makeText(getContext(), "errorin Authentication", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ParseError) {
                        pd.dismiss();
                        Toast.makeText(getContext(), "error while parsing", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ServerError) {
                        pd.dismiss();
                        Toast.makeText(getContext(), "error  in server", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ClientError) {
                        pd.dismiss();
                        Toast.makeText(getContext(), "error with Client", Toast.LENGTH_SHORT).show();
                    } else {
                        pd.dismiss();
                        Toast.makeText(getContext(), "error while loading", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mycommand.add(request);
        pd.show();
        mycommand.execute();
        mycommand.remove(request);
    }
}
