package com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.R;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    TextInputEditText tv_ho_Ten, tv_Email, tv_Password, tv_Confirm_Password;
    AppCompatButton btn_Sign_Up;
    FirebaseAuth fAuth;
    FirebaseDatabase fDatabase;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tv_ho_Ten = findViewById(R.id.tv_ho_va_ten_Sign_up);
        tv_Email = findViewById(R.id.tv_email_Sign_up);
        tv_Password = findViewById(R.id.tv_password_Sign_up);
        tv_Confirm_Password = findViewById(R.id.tv_confirm_password_Sign_up);
        btn_Sign_Up = findViewById(R.id.btn_Sign_Up);

        fAuth = FirebaseAuth.getInstance();
        fDatabase = FirebaseDatabase.getInstance();

        btn_Sign_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String HoVaTen = tv_ho_Ten.getText().toString();
                String Email = tv_Email.getText().toString();
                String Password = tv_Password.getText().toString();
                String uriImage = "https://firebasestorage.googleapis.com/v0/b/mxhfb-1b72a.appspot.com/o/avtuser%2Favt.png?alt=media&token=b49fcde2-a23e-42e7-88ed-3776995764cd";
                String uriCover = "https://firebasestorage.googleapis.com/v0/b/mxhfb-1b72a.appspot.com/o/coveruser%2Fcover.jpg?alt=media&token=a5219079-fb53-488e-be46-1680253cf77d";


                if (!isValid(Email)) {
                    tv_Email.setError("Invalid Email Address");
                    return;
                }
                if (tv_Password.getText().toString().isEmpty()) {
                    tv_Password.setError("Password required");
                    return;
                } else if (tv_Password.getText().toString().length() < 6) {
                    tv_Password.setError("Minimum 6 number");
                    return;
                }
                if (tv_Confirm_Password.getText().toString().isEmpty()) {
                    tv_Confirm_Password.setError("Password required");
                }
                if (!tv_Password.getText().toString().equals(tv_Confirm_Password.getText().toString())) {

                    tv_Password.setError("Password and confirm password does not match");
                    tv_Confirm_Password.setText("");
                    return;
                }
                fAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DatabaseReference fdb = fDatabase.getReference();

                            Map<String, Object> user = new HashMap<>();
                            user.put("name", HoVaTen);
                            user.put("email", Email);
                            user.put("pass", Password);

                            fdb.child("users").child(userID).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> voidTask) {
                                    if (voidTask.isSuccessful()) {
                                        User user = new User(HoVaTen, Email, Password, uriImage, uriCover, "", 0, 0,0);
                                        String id = task.getResult().getUser().getUid();
                                        fDatabase.getReference().child("users").child(id).setValue(user);

                                        Toast.makeText(SignUpActivity.this, "OK", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                        intent.putExtra("email", Email);
                                        setResult(Activity.RESULT_OK, intent);
                                        finish();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SignUpActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    public static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}