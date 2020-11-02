package com.dhumuni.calibrationchecklist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TTChecklist extends AppCompatActivity {

    List<String> list = new ArrayList<String>();
    public ArrayList<ExampleItem> mExampleItem=new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton floatingActionButton;
    FireBaseHandler db=new FireBaseHandler();
    DatePickerDialog datePickerDialog;
    Calendar c=Calendar.getInstance();
    int yyyy=c.get(Calendar.YEAR);
    int mm=c.get(Calendar.MONTH);
    int dd=c.get(Calendar.DAY_OF_MONTH);
    TextView Date,TTNo;
    EditText ttnumber;
    ImageView addchecklist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("TT CheckList");
        setContentView(R.layout.activity_ttchecklist);
        Date=findViewById(R.id.textView_date);
        TTNo=findViewById(R.id.textView_ttnumber);
        ttnumber=findViewById(R.id.editText_ttnumber);
        addchecklist=findViewById(R.id.addchecklist);
        buildRecyclerView();
        additem();
        String date_n = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        Date.setText(date_n);
        onSetDate();
    }

    private void additem() {
        addchecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] Numbers = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18",
                "19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40"};
                mExampleItem.clear();
                mRecyclerView.setLayoutManager(null);
                buildRecyclerView();
                for (String i:Numbers
                     ) {
                    mExampleItem.add(new ExampleItem(
                            i, "Advertisements. Java provides a data structure, the array, which stores a " +
                            "fixed-size sequential collection of elements of the same type. An array is used to store a", "", R.drawable.ic_thumbup,R.drawable.ic_thumbdown,R.drawable.ic_save));
                }

                db.Add(ttnumber.getText().toString(),Date.getText().toString());

            }
        });

    }

    // to initialise data from firestore.
    @Override
    protected void onStart() {
        super.onStart();
    }
    public void changeItem(int position, String Remarks) {
        mExampleItem.get(position).changeText(Remarks);
        mAdapter.notifyItemChanged(position);
    }
    public void buildRecyclerView() {
        mRecyclerView=findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mAdapter=new ExampleAdapter(mExampleItem);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener()
        {
            @Override
            public void onItemYes(int position, TextView Sno, EditText Remarks) {
                db.AddChecklist(Sno.getText().toString(),Remarks.getText().toString(),
                        ttnumber.getText().toString(),Date.getText().toString());
            }

            @Override
            public void onItemNo(int position, TextView Sno, EditText Remarks,ImageView yes, ImageView no, ImageView ok) {
                db.AddChecklist(Sno.getText().toString(),Remarks.getText().toString(),
                        ttnumber.getText().toString(),Date.getText().toString());
                yes.setVisibility(View.INVISIBLE);
                no.setVisibility(View.INVISIBLE);
                ok.setVisibility(View.VISIBLE);
                Sno.setEnabled(false);
                Remarks.setEnabled(true);
            }

            @Override
            public void onItemOk(int position, TextView Sno, EditText Remarks,ImageView yes, ImageView no, ImageView ok) {

                changeItem(position,Remarks.getText().toString());
                db.Update(Date.getText().toString(),ttnumber.getText().toString(),
                        Sno.getText().toString(),Remarks.getText().toString()
                        );
                yes.setVisibility(View.VISIBLE);
                no.setVisibility(View.VISIBLE);
                ok.setVisibility(View.INVISIBLE);
                Sno.setEnabled(false);
                Remarks.setEnabled(false);

            }
        });
    }

    public void onSetDate() {
        mExampleItem.clear();
        mRecyclerView.setLayoutManager(null);
        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datePickerDialog = new DatePickerDialog(TTChecklist.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                month += 1;
                                String DOM = "" + dayOfMonth;
                                if (DOM.length() == 1)
                                    DOM = "0" + DOM;
                                String M = "" + month;
                                if (M.length() == 1)
                                    M = "0" + M;
                                Date.setText(DOM + "-" + M + "-" + year);
                                Log.d("Document:", "Month is:" + M);

                            }
                        }, yyyy, mm, dd);
                datePickerDialog.show();

            }
        });

    }
}
