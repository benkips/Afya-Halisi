package www.mabnets.afyahalisi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

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
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.kosalgeek.android.caching.FileCacher;
import com.kosalgeek.android.json.JsonConverter;

import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import maes.tech.intentanim.CustomIntent;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class index extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static TextView carttext;
    public static int cart_count=0;
    private TextView navjob;
    private FileCacher<String> detailscacher;
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;
    private ProgressDialog progressDialog;
    private Mycommand mycommand;
    final String Tag=this.getClass().getName();
    private FileCacher<String> maxicacher;
    private String xt="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        detailscacher=new FileCacher<>(index.this,"details.txt");
        maxicacher=new FileCacher<>(index.this,"maxid.txt");

        preferences=getSharedPreferences("logininfo.conf",MODE_PRIVATE);

        progressDialog=new ProgressDialog(index.this);
        progressDialog.setMessage("loading..");
        mycommand=new Mycommand(index.this);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Fragment fragmentmain=new main();
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,fragmentmain).addToBackStack(null).commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View NavHeader=navigationView.getHeaderView(0);
        navjob=(TextView)NavHeader.findViewById(R.id.jjjbn);
        if(detailscacher.hasCache()){
            try {
                String t=detailscacher.readCache();
                /*cartcacher.clearCache();*/
                navjob.setText(t);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if(maxicacher.hasCache()){
            try {
                String x=maxicacher.readCache();
                xt=x;
                Log.d(Tag, x);
                recieve_notification(x);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {
            check_notification();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.index, menu);
        final MenuItem menuItem=menu.findItem(R.id.cart_action);

        View actionView= MenuItemCompat.getActionView(menuItem);
        carttext=(TextView)actionView.findViewById(R.id.cart_badge);

        setupbadge(cart_count);
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);

            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            editor = preferences.edit();
            editor.clear();
            editor.commit();
            startActivity(new Intent(index.this, Login.class));
            index.this.finish();
            try {
                maxicacher.clearCache();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }else if(id==R.id.cart_action){
            if(xt.equals("")){
                xt="0";
                Bundle bundle = new Bundle();
                bundle.putString("maxiddd", xt);
                Fragment fragment = new viewnotification();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, fragment).addToBackStack(null).commit();
            }else{
            Bundle bundle = new Bundle();
            bundle.putString("maxiddd", xt);
            Fragment fragment = new viewnotification();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, fragment).addToBackStack(null).commit();
            }
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_aboutapp) {
            // Handle the camera action
            Fragment fragmentmain=new aboutapp();
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,fragmentmain).addToBackStack(null).commit();
        }  else if (id == R.id.nav_manage) {
            Fragment fragmentmain=new developer();
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,fragmentmain).addToBackStack(null).commit();
        }else if(id==R.id.aboutkismu){
            Fragment fragmentmain=new about_kisumucounty();
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,fragmentmain).addToBackStack(null).commit();
        }else if(id==R.id.nav_contactus){
            Fragment fragmentmain=new contactus();
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,fragmentmain).addToBackStack(null).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public static void setupbadge(int cart){
        /*carttext.setText(String.valueOf(cart));*/
        if(cart==0){
            if(carttext.getVisibility()!=View.GONE){
                carttext.setVisibility(View.GONE);
            }
        }else{
            carttext.setText(String.valueOf(Math.min(cart,99)));
            carttext.setText(String.valueOf(cart));
            if(carttext.getVisibility()!=View.VISIBLE){
                carttext.setVisibility(View.VISIBLE);
            }
        }
    }
    private void check_notification(){
        final String v="0";
        String url = "http://www.afyahalisi.mabnets.com/android/getnotices.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d(Tag, response);
                if(!response.equals("")){
                    ArrayList<maxxxx> locodetails = new JsonConverter<maxxxx>().toArrayList(response, maxxxx.class);
                    ArrayList title = new ArrayList<String>();
                    for (maxxxx value : locodetails) {
                        title.add(value.maxn);
                        title.add(value.total);
                    }

                    setupbadge(Integer.parseInt(String.valueOf(title.get(1))));

                    try {
                        maxicacher.writeCache(String.valueOf(title.get(0)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(index.this ,response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if (error instanceof TimeoutError) {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "error time out ", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "error no connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "error network error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "errorin Authentication", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "error while parsing", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "error  in server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ClientError) {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "error with Client", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "error while loading", Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("checkfirst", v);
                return params;
            }
        };
        mycommand.add(stringRequest);
        progressDialog.show();
        mycommand.execute();
        mycommand.remove(stringRequest);

    }
    private void recieve_notification( final String t){
        String url = "http://www.afyahalisi.mabnets.com/android/getnotices.php ";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d(Tag, response);
                if(!response.equals("")){
                    ArrayList<maxxxx> locodetails = new JsonConverter<maxxxx>().toArrayList(response, maxxxx.class);
                    ArrayList title = new ArrayList<String>();
                    for (maxxxx value : locodetails) {
                        title.add(value.maxn);
                        title.add(value.total);
                    }

                    setupbadge(Integer.parseInt(String.valueOf(title.get(1))));

                    try {
                        maxicacher.writeCache(String.valueOf(title.get(0)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(index.this ,response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if (error instanceof TimeoutError) {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "error time out ", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "error no connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "error network error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "errorin Authentication", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "error while parsing", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "error  in server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ClientError) {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "error with Client", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "error while loading", Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("maxid", t);
                return params;
            }
        };
        mycommand.add(stringRequest);
        progressDialog.show();
        mycommand.execute();
        mycommand.remove(stringRequest);
    }
}
