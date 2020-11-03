package com.dhumuni.calibrationchecklist;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class ReportView extends AppCompatActivity {
    public ArrayList<ReportItem> mReportItem=new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ReportItemAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    FireBaseHandler db=new FireBaseHandler();
    ArrayList<String> tt=new ArrayList<>();
    ArrayList<String> date=new ArrayList<>();
    String[] Sno;
    String[] Particulars;
    ExampleItem exampleItem=new ExampleItem();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportview);
        Particulars=exampleItem.GetParticular();
        Sno=exampleItem.GetNumbers();
        Intent intent=getIntent();
        buildRecyclerView();
        /*if (intent.getStringExtra("ChecklistDate")==null&&intent.getStringExtra("TTNumber")==null)
        {
            setTitle("TT's Checked from "+intent.getStringExtra("FromDate")+"to "+intent.getStringExtra("ToDate"));
        }
        else*/ if (intent.getStringExtra("ChecklistDate")==null) setTitle(intent.getStringExtra("TTNumber")+" Checklist Histroy");
        else setTitle("TT's Checked on "+intent.getStringExtra("ChecklistDate"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent=getIntent();
          /*if (intent.getStringExtra("ChecklistDate")==null&&intent.getStringExtra("TTNumber")==null)
        {
            setTitle("TT's Checked from "+intent.getStringExtra("FromDate")+"to "+intent.getStringExtra("ToDate"));
        }
        else*/ if (intent.getStringExtra("ChecklistDate")==null)
        {
            View("TTNumber",intent.getStringExtra("TTNumber"));
            mReportItem.clear();
            mRecyclerView.setLayoutManager(null);
            buildRecyclerView();
            for (String i:tt) {
                mReportItem.add(new ReportItem(i, R.drawable.ic_edit,R.drawable.ic_delete));
            }
        }
        else {
            View("Date",intent.getStringExtra("ChecklistDate"));
            mReportItem.clear();
            mRecyclerView.setLayoutManager(null);
            buildRecyclerView();
            for (String i:date) {
                mReportItem.add(new ReportItem(i, R.drawable.ic_edit,R.drawable.ic_delete));
            }
        }
    }
    public void buildRecyclerView() {
        mRecyclerView=findViewById(R.id.recyclerViewreport);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mAdapter=new ReportItemAdapter(mReportItem);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ReportItemAdapter.OnItemClickListener()
        {
            @Override
            public void onItemEdit(int position, TextView Data) {

            }

            @Override
            public void onItemDelete(int position, TextView Data) {
                mReportItem.remove(position);
                mAdapter.notifyItemRemoved(position);
                Intent intent=getIntent();
                String Date="",TTNumber="";
          /*if (intent.getStringExtra("ChecklistDate")==null&&intent.getStringExtra("TTNumber")==null)
        {
            setTitle("TT's Checked from "+intent.getStringExtra("FromDate")+"to "+intent.getStringExtra("ToDate"));
        }
        else*/ if (intent.getStringExtra("ChecklistDate")==null)
                {
                    String tt=intent.getStringExtra("TTNumber");
                    String date=Data.getText().toString();
                    Date=date;
                    TTNumber=tt;
                }
                else {
                    String date=intent.getStringExtra("ChecklistDate");
                    String tt=Data.getText().toString();
                    Date=date;
                    TTNumber=tt;
                }
                db.delete(Date,TTNumber);

            }
        });
    }
    void View(String  Label,String finder){
        tt.clear();
        date.clear();
        Task<QuerySnapshot> data=null;
        data=db.View(Label,finder);
        if(data.getResult().isEmpty())
        {
            //showMessage("Error","Nothing found");
            return;
        }
        int i=0;
        for (QueryDocumentSnapshot Qdoc:data.getResult())
        {
            Map<String ,Object> Mlist=null;
            Mlist= Qdoc.getData();
            if(Label.equals("TTNumber")) tt.add(Mlist.get("Date").toString());
            else if (Label.equals("Date")) date.add(Mlist.get("TTNumber").toString());
        }
        data=null;
    }
}