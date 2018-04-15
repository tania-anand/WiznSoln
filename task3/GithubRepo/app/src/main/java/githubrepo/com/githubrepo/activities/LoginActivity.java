package githubrepo.com.githubrepo.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import githubrepo.com.githubrepo.Interface.MyResponseConnectivity;
import githubrepo.com.githubrepo.R;

public class LoginActivity extends UtilActivity  implements MyResponseConnectivity {

    EditText edtUserName, edtUserPAssword;
    TextView txtRegister;

    TextInputLayout edtUserNameTI,edtUserPAsswordTI;
    Button btnLogin;

    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    boolean isLogin=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtUserPAssword = (EditText) findViewById(R.id.edtUserPassword);
        edtUserNameTI = (TextInputLayout) findViewById(R.id.edtUserNameTI);
        edtUserPAsswordTI = (TextInputLayout) findViewById(R.id.edtUserPAsswordTI);

        txtRegister = findViewById(R.id.textViewRegister);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginRegister();

            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isLogin) {
                    isLogin = false;
                    btnLogin.setText("Register");
                    txtRegister.setText("Registerd User ? Login Here");
                }
                else {
                        isLogin = true;
                        btnLogin.setText("Login");
                        txtRegister.setText("New User ? Registered here");

                    }

            }
        });
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    public boolean validation() {
        boolean ch = true;
        if (edtUserPAssword.getText().toString().isEmpty()) {
            edtUserPAsswordTI.setError("Invalid Password");
            requestFocus(edtUserPAssword);
            ch = false;
        } else
            edtUserPAsswordTI.setError(null);
        if (!edtUserName.getText().toString().trim().matches(emailPattern)
                || edtUserName.getText().toString().trim().length() == 0) {
            edtUserNameTI.setError("Invalid username");
            ch = false;
            requestFocus(edtUserName);
        } else
            edtUserNameTI.setError(null);

        return ch;
    }

    void signInFireBase() {
        String username = edtUserName.getText().toString();
        String password = edtUserPAssword.getText().toString();
        mAuth.signInWithEmailAndPassword(username,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            showProgressDialog(0);

                            //start Activity
                            Intent i = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(i);
                            finish();

                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_LONG).show();
                            showProgressDialog(0);

                        }

                    }
                });

    }


    void createAccount() {
        String username = edtUserName.getText().toString();
        String password = edtUserPAssword.getText().toString();
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            isLogin=true;
                            edtUserName.setText(null);
                            edtUserPAssword.setText(null);
                            btnLogin.setText("Login");
                            showProgressDialog(0);
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",Toast.LENGTH_SHORT).show();
                            showProgressDialog(0);

                        }

                    }
                });
        // [END create_user_with_email]
    }

    @Override
    public void onMyResponseConnectivity(int i) {

        if(i==1) {
            LoginRegister();
        }
        else if(i==0)
            showMsg(this);

    }

    void LoginRegister(){
        if (validation()) {

            if(isNetworkConnected(LoginActivity.this)) {
                initProgressDialog();
                showProgressDialog(1);
                if (isLogin)
                    signInFireBase();
                else
                    createAccount();
            }
            else
                showMsg(LoginActivity.this);


        }

    }


}
