package githubrepo.com.githubrepo.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import githubrepo.com.githubrepo.Interface.MyResponseConnectivity;
import githubrepo.com.githubrepo.R;


public class UtilActivity extends AppCompatActivity {


    ProgressDialog progressDialog;
    private MyResponseConnectivity myResponseConnectivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_util);
    }


    void initProgressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
    }

    void showProgressDialog(int i){
        if(i==1)
            progressDialog.show();
        else
            progressDialog.dismiss();

    }


    public static boolean isNetworkConnected(Activity activity) {
        ConnectivityManager cmgr;
        cmgr = (ConnectivityManager) activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cmgr.getActiveNetworkInfo();
        if (info != null) {
            if (info.isConnected()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void showMsg(final Activity activity) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setMessage("No Internet Connectivity. Please enable and Retry");
        dialog.setCancelable(false);
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
            }
        });
        dialog.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (isNetworkConnected(activity)) {
                    myResponseConnectivity.onMyResponseConnectivity(1);
                } else {
                    myResponseConnectivity.onMyResponseConnectivity(0);
                }
            }
        });
        dialog.create().show();
    }


}
