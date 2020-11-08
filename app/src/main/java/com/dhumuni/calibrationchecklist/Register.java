package com.dhumuni.calibrationchecklist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity
{
    EditText email,password,ecno,name;
    ImageView show_pass_btn;
    Button btnRegister;
    String mail,pass,ecnumber,Name;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Register");
        setContentView(R.layout.activity_register);
        email = (EditText)findViewById(R.id.editText_email);
        password = (EditText)findViewById(R.id.editText_password);
        ecno = findViewById(R.id.ecno);
        name = findViewById(R.id.ename);
        show_pass_btn=findViewById(R.id.show_pass_btn);
        show_pass_btn.setAlpha(1.0f);
        btnRegister = (Button)findViewById(R.id.button_register);
        auth=FirebaseAuth.getInstance();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mail=email.getText().toString();
                pass=password.getText().toString();
                ecnumber=ecno.getText().toString();
                Name=name.getText().toString();
                if(mail.isEmpty()||pass.isEmpty()||ecnumber.isEmpty()||Name.isEmpty())
                    Toast.makeText(Register.this,"Empty Credentials",Toast.LENGTH_SHORT).show();
                else if(pass.length()<4)
                    Toast.makeText(Register.this,"Password too short",Toast.LENGTH_SHORT).show();
                else
                    RegisterUser(mail,pass,ecnumber,Name);
            }
        });
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
    private void RegisterUser(String mail, String pass,String ecnumber, String Name)
    {
        auth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(Register.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful()) {
                    Map<String ,Object> data= new HashMap<>();
                    data.put("ecnumber",ecnumber);
                    data.put("Name",Name);
                    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                    assert user != null;
                    FirebaseFirestore db=FirebaseFirestore.getInstance();
                    db.collection("User").
                            document(user.getUid()).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {

                                            Toast.makeText(Register.this, "Register Successful", Toast.LENGTH_SHORT).show();
                                            Intent in = new Intent(Register.this, MainActivity.class);
                                            startActivity(in);
                            }
                            else Toast.makeText(Register.this, "Register Failed..."+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                }
                else

                    Toast.makeText(Register.this, "Register Failed..."+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
