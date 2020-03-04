package www.mabnets.afyahalisi;

import androidx.appcompat.app.AppCompatActivity;
import maes.tech.intentanim.CustomIntent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    private EditText etnames;
    private EditText etemails;
    private EditText etjbno;
    private EditText etphno;
    private EditText etpassword;
    private TextView  loginref;
    private Button regbtn;
    private ProgressDialog progressDialog;
    private Mycommand mycommand;
    final String Tag=this.getClass().getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etnames=(EditText)findViewById(R.id.etrfullnames);
        etemails=(EditText)findViewById(R.id.etremail);
        etjbno=(EditText)findViewById(R.id.etrJobno);
        etphno=(EditText)findViewById(R.id.etrphone);
        etpassword=(EditText)findViewById(R.id.etrpass);
        regbtn=(Button) findViewById(R.id.btnreg);
        loginref=(TextView)findViewById(R.id.loginrefz);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("loading..");
        mycommand=new Mycommand(this);




        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names=etnames.getText().toString().trim();
                String em=etemails.getText().toString().trim();
                String jbno=etjbno.getText().toString().trim();
                String pass=etpassword.getText().toString().trim();
                String phone=etphno.getText().toString().trim();
                Validatedetails(names,em,phone,pass,jbno);
            }
        });
        loginref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this,Login.class));
                CustomIntent.customType(Registration.this,"left-to-right");
            }
        });

    }
    private void Validatedetails(final String f, final String e,final String ph,final  String password,final  String jb) {

        if (f.isEmpty()) {
            etnames.setError("name is invalid");
            etnames.requestFocus();
            return;
        } else if (e.isEmpty()) {
            etemails.setError("email is  is invalid");
            etemails.requestFocus();
            return;
        } else if(jb.isEmpty()){
            etjbno.setError("job number is invalid");
            etjbno.requestFocus();
            return;
        }  else if (ph.isEmpty()) {
            etphno.setError("phone is invalid");
            etphno.requestFocus();
            return;
        }else if(password.isEmpty()){
            etpassword.setError("password is invalid");
            etpassword.requestFocus();
            return;
        }
        else {
            if (!isphone(ph) || (ph.length() != 10 || !ph.startsWith("07"))) {
                etphno.setError("phone is invalid");
                etphno.requestFocus();
                return;
            } else  if (password.length() <= 8) {
                etpassword.setError("password is must be 8 characters or more");
                etpassword.requestFocus();
                return;
            }else if (!isValidEmail(e)) {
                etemails.setError("email is invalid");
                etemails.requestFocus();
                return;
            } else {

                String url="http://afyahalisi.mabnets.com/android/registeration.php";
                StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.d(Tag,response);
                        if(!response.isEmpty()){
                            if(response.contains("success")){
                                Toast.makeText(Registration.this, response, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Registration.this, response, Toast.LENGTH_SHORT).show();
                                Log.d(Tag, response);

                            }

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error!=null) {
                            Log.d(Tag, error.toString());
                            if (error instanceof TimeoutError) {
                                progressDialog.dismiss();
                                Toast.makeText(Registration.this, "error time out ", Toast.LENGTH_SHORT).show();

                            } else if (error instanceof NoConnectionError) {
                                progressDialog.dismiss();
                                Toast.makeText(Registration.this, "error no connection", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof NetworkError) {
                                progressDialog.dismiss();
                                Toast.makeText(Registration.this, "error network error", Toast.LENGTH_SHORT).show();

                            }else if (error instanceof AuthFailureError) {
                                progressDialog.dismiss();
                                Toast.makeText(Registration.this, "errorin Authentication", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof ParseError) {
                                progressDialog.dismiss();
                                Toast.makeText(Registration.this, "error while parsing", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof ServerError) {
                                progressDialog.dismiss();
                                Toast.makeText(Registration.this, "error  in server", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof ClientError) {
                                progressDialog.dismiss();
                                Toast.makeText(Registration.this, "error with Client", Toast.LENGTH_SHORT).show();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(Registration.this, "error while loading", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> params=new HashMap<>();
                        params.put("user", f);
                        params.put("email", e);
                        params.put("jobno", jb);
                        params.put("pass", password);
                        params.put("phone", ph);
                        return params;
                    }
                };
                mycommand.add(request);
                progressDialog.show();
                mycommand.execute();
                mycommand.remove(request);

            }

        }
    }
    public final static boolean isValidEmail(String target) {

        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    public static boolean isphone(CharSequence target){
        return !TextUtils.isEmpty(target) && Patterns.PHONE.matcher(target).matches();
    }
}
