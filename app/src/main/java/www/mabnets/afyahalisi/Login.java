package www.mabnets.afyahalisi;

import androidx.appcompat.app.AppCompatActivity;
import maes.tech.intentanim.CustomIntent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
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
import com.kosalgeek.android.caching.FileCacher;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private EditText etjobno;
    private EditText etpassword;
    private Button lgnbtn;
    private TextView regref;
    private ProgressDialog progressDialog;
    private Mycommand mycommand;
    private Boolean checked;
    private CheckBox cb;
    private SharedPreferences preferences;
    private FileCacher<String> detailscacher;
    final String Tag=this.getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etjobno=(EditText)findViewById(R.id.etJobno);
        etpassword=(EditText)findViewById(R.id.etpass);
        lgnbtn=(Button)findViewById(R.id.signinbtn);
        cb=(CheckBox)findViewById(R.id.cbL);
        regref=(TextView)findViewById(R.id.regreff);
        detailscacher=new FileCacher<>(Login.this,"details.txt");

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("loading..");
        mycommand=new Mycommand(this);

        checked=cb.isChecked();

        preferences=getSharedPreferences("logininfo.conf",MODE_PRIVATE);
        String jbn=preferences.getString("jobno","");
        String pwd=preferences.getString("passsword","");
        Log.d(Tag, pwd);
        Log.d(Tag, jbn);
        if(!jbn.equals("") && !pwd.equals("")){
            /*startActivity here*/
            Log.d(Tag, jbn);
            startActivity(new Intent(Login.this,index.class));
            Login.this.finish();
        }


        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                checked=b;
                Toast.makeText(Login.this, String.valueOf(b), Toast.LENGTH_SHORT).show();

            }
        });

        lgnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jb=etjobno.getText().toString();
                String pd=etpassword.getText().toString();

                validatelogin(jb,pd);
            }
        });
        regref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Registration.class));
                CustomIntent.customType(Login.this,"left-to-right");
            }
        });
    }
    private void validatelogin(final String jobn,final  String pswrd ) {
        if (jobn.isEmpty()) {
            etjobno.setError("jobnumber is invalid");
            etjobno.requestFocus();
            return;
        } else if (pswrd.isEmpty()) {
            etpassword.setError("password is invalid");
            etpassword.requestFocus();
            return;
        } else {
            if (pswrd.length() <= 8) {
                etpassword.setError("password is must be 8 characters or more");
                etpassword.requestFocus();
                return;
            } else {
                String url = "http://www.afyahalisi.mabnets.com/android/login.php ";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.d(Tag, response);
                        if(response.equals("success")){
                            if (checked) {
                                preferences = getSharedPreferences("logininfo.conf", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("jobno",jobn);
                                editor.putString("passsword", pswrd);
                                editor.apply();
                                Log.d(Tag, "saved");
                            }
                            try {
                                detailscacher.writeCache(jobn);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(Login.this, response, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this, index.class));
                            Login.this.finish();
                            CustomIntent.customType(Login.this, "left-to-right");
                        } else {
                            Toast.makeText(Login.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        if (error instanceof TimeoutError) {
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "error time out ", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NoConnectionError) {
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "error no connection", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NetworkError) {
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "error network error", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof AuthFailureError) {
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "errorin Authentication", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError) {
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "error while parsing", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError) {
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "error  in server", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ClientError) {
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "error with Client", Toast.LENGTH_SHORT).show();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "error while loading", Toast.LENGTH_SHORT).show();
                        }

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("jobno", jobn);
                        params.put("password",pswrd);
                        return params;
                    }
                };
                mycommand.add(stringRequest);
                progressDialog.show();
                mycommand.execute();
                mycommand.remove(stringRequest);

            }
        }
    }

}
