package githubrepo.com.githubrepo.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import githubrepo.com.githubrepo.R;

public class Splash extends AppCompatActivity {

    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        handler.sendEmptyMessageDelayed(100,5000);

    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if (msg.what == 100) {
                Intent i ;
                if(firebaseUser!=null)
                    i = new Intent(Splash.this,MainActivity.class);
                else
                    i = new Intent(Splash.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        }
    };
}
