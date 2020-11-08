package com.dhumuni.calibrationchecklist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Popup extends AppCompatActivity {
    FireBaseHandler db=new FireBaseHandler();
    private FloatingActionButton add;
    private EditText ttnumber,transporter,capacity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        add=findViewById(R.id.add);
        ttnumber=findViewById(R.id.editText_ttnumber);
        transporter=findViewById(R.id.editText_transporter);
        capacity=findViewById(R.id.editText_capacity);
        Add();
    }
    private void Add(){
        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if( ttnumber.getText().toString().isEmpty()||transporter.getText().toString().isEmpty()||capacity.getText().toString().isEmpty())
                    Toast.makeText(Popup.this, "Empty Data not alloweded",Toast.LENGTH_SHORT).show();
                else  if(ttnumber.getText().toString().length()<6) {
                    Toast.makeText(Popup.this, "Please enter Valid TT No", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent in = new Intent();
                    in.putExtra("ttnumber", ttnumber.getText().toString()).
                            putExtra("capacity", capacity.getText().toString()).
                            putExtra("transporter",transporter.getText().toString());
                    setResult(RESULT_OK, in);
                    finish();
                }
            }
        });
    }
}