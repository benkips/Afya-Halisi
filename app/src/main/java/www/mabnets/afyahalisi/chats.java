package www.mabnets.afyahalisi;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kosalgeek.android.json.JsonConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class chats extends Fragment {
    private RecyclerView recyclerV;
    private EditText msgg;
    private FloatingActionButton flsend;
    private SharedPreferences preff;
    private Mycommand mycommand;
    private String jobno;
    private String categz;
    private ProgressDialog pd;
    final String Tag=this.getClass().getName();

    public chats() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View vsend=inflater.inflate(R.layout.fragment_chats, container, false);
        recyclerV=(RecyclerView)vsend.findViewById(R.id.rvchats);
        msgg=(EditText)vsend.findViewById(R.id.etchat);
        flsend=(FloatingActionButton)vsend.findViewById(R.id.flchats);
        mycommand=new Mycommand(getContext());

        preff=getActivity().getSharedPreferences("logininfo.conf",MODE_PRIVATE);
        jobno=preff.getString("jobno","");
        Log.d(Tag,jobno);
        pd=new ProgressDialog(getContext());
        pd.setMessage("Loading");

        Bundle bundle = getArguments();
        if (bundle != null) {
            categz = bundle.getString("category");
            Log.d(Tag, categz);
        }

        flsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String text=msgg.getText().toString();
                if(text.equals("")) {
                    msgg.requestFocus();
                    return;

                }else{
                    sendmessage(text);
                    msgg.setText("");
                }

            }
        });
        recyclerV.setHasFixedSize(true);
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        recyclerV.setLayoutManager(manager);
        loadchats();
       return vsend;
    }
    private void loadchats(){

        String url="http://www.afyahalisi.mabnets.com/android/getchats.php";
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                Log.d(Tag,response);
                if(!response.isEmpty()){
                    if(response.contains("No chats found")){
                        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d(Tag, response);
                        ArrayList<chatz> xhatlist= new JsonConverter<chatz>().toArrayList(response, chatz.class);
                        chatadapter chatadapter=new chatadapter(xhatlist,getContext());
                        recyclerV.setAdapter(chatadapter);

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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("categ", categz);
                return params;
            }
        };

        mycommand.add(request);
        pd.show();
        mycommand.execute();
        mycommand.remove(request);
    }
    private void sendmessage(final String fnal){
        String url="http://www.afyahalisi.mabnets.com/android/chatsaver.php";
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                Log.d(Tag,response);
                if(!response.isEmpty()){
                    if(response.contains("success")){
                        loadchats();
                        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("sender", jobno);
                params.put("chat", fnal);
                params.put("categ", categz);
                return params;
            }
        };

        mycommand.add(request);
        pd.show();
        mycommand.execute();
        mycommand.remove(request);
    }
}
