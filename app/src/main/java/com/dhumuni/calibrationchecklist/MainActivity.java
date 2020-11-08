package com.dhumuni.calibrationchecklist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button btnLogin,btnRegister,btnForget;
    ImageView show_pass_btn;
    String user, pass;
    private FirebaseAuth auth;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Login");
        setContentView(R.layout.activity_main);
        email = (EditText)findViewById(R.id.editText_email);
        password = (EditText)findViewById(R.id.editText_password);
        btnLogin = (Button)findViewById(R.id.button_login);
        btnRegister = (Button)findViewById(R.id.button_register);
        btnForget=findViewById(R.id.button_forget);
        show_pass_btn=findViewById(R.id.show_pass_btn);
        show_pass_btn.setAlpha(1.0f);
        auth=FirebaseAuth.getInstance();
        ForgetPassword();
        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                user = email.getText().toString();
                pass = password.getText().toString();
                validate(user, pass);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Register.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
            startActivity(new Intent(MainActivity.this,MenuActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }
    public void ShowHidePass(View view) {
        if (view.getId() == R.id.show_pass_btn) {

            if (password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                show_pass_btn.setAlpha(0.3f);
                //Show Password
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                show_pass_btn.setAlpha(1.0f);
                //Hide Password
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }
    public void ForgetPassword(){
        btnForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this,"Pl enter Registered email",Toast.LENGTH_LONG).show();
                    return;
                }
                FirebaseAuth.getInstance().sendPasswordResetEmail(email.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this,"Password Reset link sent, Please check g-mail inbox.",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

    }
    public void validate(String user, String pass) {
        if (!user.equals("") && !pass.equals(""))
            auth.signInWithEmailAndPassword(user,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(MainActivity.this, MenuActivity.class);
                    startActivity(in);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "User not Registered", Toast.LENGTH_SHORT).show();
                }
            });
        else Toast.makeText(MainActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();

        /*if (user.equals("") && pass.equals("")) {
            Intent in = new Intent(MainActivity.this, MonthBudget.class);
            startActivity(in);
        } else {
            AlertDialog.Builder build = new AlertDialog.Builder(MainActivity.this);
            build.setTitle("Error").setMessage("Wrong username or password. Please enter correct credentials.")
                    .setPositiveButton("ok", null).setCancelable(false);
            AlertDialog alert = build.create();
            alert.show();

        }*/
    }

}