package com.dhumuni.calibrationchecklist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Report extends AppCompatActivity {
    FireBaseHandler db=new FireBaseHandler();
    TextView reportbytt,reportbydate,rbdate_date,creport,fromdate,to,todate,loading_graph;
    AutoCompleteTextView rbtt_ttnumber;
    Button btn_reportbytt,btn_reportbydate,btn_customeport;
    DatePickerDialog datePickerDialog;
    Calendar e=Calendar.getInstance();
    int yyyy=e.get(Calendar.YEAR);
    int mm=e.get(Calendar.MONTH);
    int dd=e.get(Calendar.DAY_OF_MONTH);
    ArrayList<String> tt=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        setTitle("Report");
        String date_n = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        loading_graph=findViewById(R.id.loading_graph);
        reportbytt=findViewById(R.id.reportbytt);
        reportbydate=findViewById(R.id.reportbydate);
        rbdate_date=findViewById(R.id.rbdate_date);
        rbdate_date.setText(date_n);
        rbdate_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetDate(rbdate_date);
            }
        });
        /*creport=findViewById(R.id.creport);
        fromdate=findViewById(R.id.fromdate);
        fromdate.setText(date_n);
        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetDate(fromdate);
            }
        });
        to=findViewById(R.id.to);
        todate=findViewById(R.id.todate);
        todate.setText(date_n);
        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetDate(todate);
            }
        });*/
        rbtt_ttnumber=findViewById(R.id.rbtt_ttnumber);
        btn_reportbytt=findViewById(R.id.btn_reportbytt);
        btn_reportbydate=findViewById(R.id.btn_reportbydate);
       /* btn_customeport=findViewById(R.id.btn_customeport);*/
        ReportbyDate();
        ReportbyTT();
        /*CustomReport();*/
        View();
        AutoComplete(rbtt_ttnumber);
        
    }

    @Override
    protected void onStart() {
        super.onStart();
        loading_graph.setVisibility(View.INVISIBLE);
        reportbytt.setVisibility(View.VISIBLE);
        reportbydate.setVisibility(View.VISIBLE);
        rbdate_date.setVisibility(View.VISIBLE);
        /*creport.setVisibility(View.VISIBLE);
        fromdate.setVisibility(View.VISIBLE);
        to.setVisibility(View.VISIBLE);
        todate.setVisibility(View.VISIBLE);*/
        rbtt_ttnumber.setVisibility(View.VISIBLE);
        btn_reportbytt.setVisibility(View.VISIBLE);
        btn_reportbydate.setVisibility(View.VISIBLE);
        /*btn_customeport.setVisibility(View.VISIBLE);*/
    }

    private void ReportbyDate() {
        btn_reportbydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading_graph.setVisibility(View.VISIBLE);
                Intent in =new Intent(Report.this,ReportView.class);
                in.putExtra("ChecklistDate",rbdate_date.getText().toString());
                startActivity(in);
                reportbytt.setVisibility(View.INVISIBLE);
                reportbydate.setVisibility(View.INVISIBLE);
                rbdate_date.setVisibility(View.INVISIBLE);
               /* creport.setVisibility(View.INVISIBLE);
                fromdate.setVisibility(View.INVISIBLE);
                to.setVisibility(View.INVISIBLE);
                todate.setVisibility(View.INVISIBLE);*/
                rbtt_ttnumber.setVisibility(View.INVISIBLE);
                btn_reportbytt.setVisibility(View.INVISIBLE);
                btn_reportbydate.setVisibility(View.INVISIBLE);
               /* btn_customeport.setVisibility(View.INVISIBLE);*/
            }
        });
    }

    private void ReportbyTT() {
        btn_reportbytt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading_graph.setVisibility(View.VISIBLE);
                Intent in =new Intent(Report.this,ReportView.class);
                in.putExtra("TTNumber",rbtt_ttnumber.getText().toString());
                startActivity(in);
                reportbytt.setVisibility(View.INVISIBLE);
                reportbydate.setVisibility(View.INVISIBLE);
                rbdate_date.setVisibility(View.INVISIBLE);
                /*creport.setVisibility(View.INVISIBLE);
                fromdate.setVisibility(View.INVISIBLE);
                to.setVisibility(View.INVISIBLE);
                todate.setVisibility(View.INVISIBLE);*/
                rbtt_ttnumber.setVisibility(View.INVISIBLE);
                btn_reportbytt.setVisibility(View.INVISIBLE);
                btn_reportbydate.setVisibility(View.INVISIBLE);
               /* btn_customeport.setVisibility(View.INVISIBLE);*/
            }
        });
    }

    private void CustomReport() {
        btn_customeport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void SetDate(TextView textView){
        datePickerDialog = new DatePickerDialog(Report.this,
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
                        textView.setText(DOM + "-" + M + "-" + year);
                        Log.d("Document:", "Month is:" + M);

                    }
                }, yyyy, mm, dd);
        datePickerDialog.show();
    }
    private void AutoComplete(AutoCompleteTextView rbtt_ttnumber){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, tt);

        rbtt_ttnumber.setThreshold(2);
        rbtt_ttnumber.setAdapter(adapter);

    }
    void View(){
        Task<QuerySnapshot> data=null;
        data=db.View();
        tt.clear();
        if(data.getResult().isEmpty())
        {
            //showMessage("Error","Nothing found");
            return;
        }
        for (QueryDocumentSnapshot Qdoc:data.getResult())
        {
            Map<String ,Object> Mlist=null;
            Mlist= Qdoc.getData();
            tt.add(Mlist.get("TTNumber").toString());
        }
        data=null;
    }

}