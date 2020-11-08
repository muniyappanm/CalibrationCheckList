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
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TTChecklist extends AppCompatActivity {

    List<String> list = new ArrayList<String>();
    public String Remark;
    ArrayList<String> Remarks=new ArrayList<>();
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
    TextView Date,TTNo,ttnumber,cap,capacity;
    FloatingActionButton addnew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("TT CheckList");
        setContentView(R.layout.activity_ttchecklist);
        Date=findViewById(R.id.textView_date);
        TTNo=findViewById(R.id.textView_ttno);
        ttnumber=findViewById(R.id.textView_ttnumber);
        TTNo=findViewById(R.id.textView_ttno);
        cap=findViewById(R.id.textView_cap);
        capacity=findViewById(R.id.textView_capacity);
        addnew=findViewById(R.id.addnew);
        buildRecyclerView();
        PopUp();
        onSetDate();
    }

    private void PopUp() {
        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExampleItem.clear();
                mRecyclerView.setLayoutManager(null);
                buildRecyclerView();
                ttnumber.setText("");
                capacity.setText("");
                Intent in = new Intent(TTChecklist.this, Popup.class);
                startActivityForResult(in,1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
            if(resultCode==RESULT_OK)
            {
                buildRecyclerView();
                assert data != null;
                ttnumber.setText(data.getStringExtra("ttnumber"));
                capacity.setText(data.getStringExtra("capacity"));
                Map<String, Object> addData = new HashMap<>();
                addData.put("TTNumber",ttnumber.getText().toString() );
                addData.put("Date", Date.getText().toString());
                addData.put("Transporter",data.getStringExtra("transporter"));
                addData.put("Capacity", capacity.getText().toString());
                db.Add(addData);
                additem();

            }
    }
    private void additem() {
                String[] Numbers=new ExampleItem().GetNumbers();
                String [] Particulars=new ExampleItem().GetParticular();
                mExampleItem.clear();
                mRecyclerView.setLayoutManager(null);
                buildRecyclerView();
                for(int i=0;i<Numbers.length;i++) {
                    db.AddChecklist(Numbers[i],"",
                            ttnumber.getText().toString(),Date.getText().toString());
                    mExampleItem.add(new ExampleItem(
                            Numbers[i], Particulars[i], "", R.drawable.ic_thumbup,R.drawable.ic_thumbdown,R.drawable.ic_save));
                }


    }
    private void edititem() {
                String[] Numbers=new ExampleItem().GetNumbers();
                String [] Particulars=new ExampleItem().GetParticular();
                mExampleItem.clear();
                mRecyclerView.setLayoutManager(null);
                buildRecyclerView();
                String[] re=new String[Remarks.size()];
                int a=0;
                for (String j:Remarks) { re[a]=j;a++;}
                for(int i=0;i<Numbers.length;i++) {
                    mExampleItem.add(new ExampleItem(
                            Numbers[i], Particulars[i], re[i], R.drawable.ic_thumbup,R.drawable.ic_thumbdown,R.drawable.ic_save));
                }
            }

    // to initialise data from firestore.
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent=getIntent();
        if (intent.getStringExtra("Date")!=null||intent.getStringExtra("TTNumber")!=null)
        {
            String tt=intent.getStringExtra("TTNumber");
            String date=intent.getStringExtra("Date");
            Date.setText(date);
            ttnumber.setText(tt);
            ViewOne(tt,date);
            edititem();
        }
        else {
            String date_n = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            Date.setText(date_n);
        }


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
                db.AddChecklist(Sno.getText().toString(),"YES",
                        ttnumber.getText().toString(),Date.getText().toString());
                changeItem(position,"YES");
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
                Remark="NO: ";
                changeItem(position,Remark+Remarks.getText().toString());
                db.Update(Date.getText().toString(),ttnumber.getText().toString(),
                        Sno.getText().toString(),Remark+Remarks.getText().toString());
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
    void ViewOne(String  TTNumber,String Date){
        Task<QuerySnapshot> data=null;
        Remarks.clear();
        data=db.ViewOne(Date,TTNumber);
        if(data.getResult().isEmpty())
        {
            //showMessage("Error","Nothing found");
            return;
        }
        String[] Numbers=new ExampleItem().GetNumbers();
        for (QueryDocumentSnapshot Qdoc:data.getResult())
        {
            Map<String ,Object> Mlist=null;
            Mlist= Qdoc.getData();
            capacity.setText((String) Mlist.get("Capacity"));
            for(int j=0;j<Numbers.length;j++)
            {
                Remarks.add(Mlist.get(Numbers[j]).toString());
            }
        }
        data=null;
    }
}
