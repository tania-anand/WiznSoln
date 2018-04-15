package githubrepo.com.githubrepo.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import githubrepo.com.githubrepo.Interface.MyResponseConnectivity;
import githubrepo.com.githubrepo.R;
import githubrepo.com.githubrepo.adapter.GitHubRepAdapter;
import githubrepo.com.githubrepo.bean.GitHubRepo;
import githubrepo.com.githubrepo.helper.Util;

public class MainActivity extends UtilActivity implements MyResponseConnectivity{

    JsonArrayRequest stringRequest;
    RequestQueue requestQueue;
    ArrayList<GitHubRepo> gitHubRepoArrayList;
    RecyclerView recyclerView;
    GitHubRepAdapter adapter;


    void init(){
        requestQueue= Volley.newRequestQueue(this);

        recyclerView = (RecyclerView)findViewById(R.id.rvGit);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        init();

        if(isNetworkConnected(this)) {
            initProgressDialog();
            showProgressDialog(1);
            getRepos();
        }
        else
            showMsg(this);

    }


        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            askLogout();
        }
        return super.onOptionsItemSelected(item);
    }


    void getRepos(){
        stringRequest = new JsonArrayRequest(Request.Method.GET, Util.URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                if(response.length()>0) {
                    gitHubRepoArrayList = new ArrayList<>();

                    try {

                    for (int i = 0; i < response.length(); i++) {


                            JSONObject jObj = response.getJSONObject(i);
                            GitHubRepo gitHubRepo = new GitHubRepo();
                            gitHubRepo.setId(jObj.getString("id"));
                            gitHubRepo.setFullName(jObj.getString("full_name"));
                            gitHubRepo.setForkCount(String.valueOf(jObj.getInt("forks_count")));
                            gitHubRepo.setStarCount(String.valueOf(jObj.getInt("stargazers_count")));
                            gitHubRepo.setUrl(jObj.getString("html_url"));

                            gitHubRepoArrayList.add(gitHubRepo);

                    }
                    Log.i("show","data "+gitHubRepoArrayList.toString());

                    adapter = new GitHubRepAdapter(getApplicationContext(),gitHubRepoArrayList);
                    recyclerView.setAdapter(adapter);
                        showProgressDialog(0);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        showProgressDialog(0);
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "No Data ", Toast.LENGTH_LONG).show();
                    showProgressDialog(0);
                }
            }},
            new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Toast.makeText(MainActivity.this, "Error "+error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    showProgressDialog(0);

                }

            });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Add the request to the RequestQueue.
           requestQueue.add(stringRequest);

    }

    @Override
    public void onMyResponseConnectivity(int i) {

        if(i==1)
            getRepos();
         else if(i==0)
            showMsg(this);

    }

    void askLogout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Kindly Confirm to Logout");
        builder.setNegativeButton("Cancel",null);
        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
        builder.show();
    }
}
