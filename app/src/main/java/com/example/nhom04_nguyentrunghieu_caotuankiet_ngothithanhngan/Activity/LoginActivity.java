package com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText tv_email_login, tv_password_login;
    AppCompatButton btn_Login, btn_Sign_up, btn_Forget;
    FirebaseAuth firebaseAuth;

    ActivityResultLauncher<Intent> mActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent intent = result.getData();
                    String email = intent.getStringExtra("email");
//                    String pass = intent.getStringExtra("password");

                    tv_email_login.setText(email);
                    Log.d("ABC", tv_email_login.toString());

                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv_email_login = findViewById(R.id.tv_email_login);
        tv_password_login = findViewById(R.id.tv_password_login);
        btn_Login = findViewById(R.id.btn_login);
        btn_Sign_up = findViewById(R.id.btn_Sign_Up_login);
        btn_Forget = findViewById(R.id.btn_forgot);
        /**
         * Firebase
         */
        firebaseAuth = FirebaseAuth.getInstance();


        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = tv_email_login.getText().toString();
                String password = tv_password_login.getText().toString();
                Log.d("ABC", tv_email_login.toString());
                if (tv_email_login.getText().toString().isEmpty()) {
                    tv_email_login.setError("Khong duoc bo trong");
                    return;
                }
                if (tv_password_login.getText().toString().isEmpty()) {
                    tv_password_login.setError("Khong duoc bo trong");
                    return;
                }
                if (tv_password_login.length() < 6) {
                    tv_password_login.setError("Mat khau khong duoc duoi 6 ki tu");
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
//                            tvEmail.setText(user.getEmail());
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn_Sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                mActivityLauncher.launch(intent);
            }
        });
    }
    /* Access modifiers changed, original: protected */
//    public void onResume() {
//        super.onResume();
//        if (this.firebaseAuth.getCurrentUser() != null) {
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
//        }
//    }
}
