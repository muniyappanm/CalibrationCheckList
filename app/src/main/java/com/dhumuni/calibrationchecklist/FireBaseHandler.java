package com.dhumuni.calibrationchecklist;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class FireBaseHandler extends AppCompatActivity
{
    public void Add(String TTNumber, String Date) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();
        data.put("TTNumber",TTNumber );
        data.put("Date", Date);
        db.collection("Calibration").document(TTNumber+"_"+Date)
                .set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    Log.d("Document", "ok");
            }
        });
    }
    public void AddChecklist(String Sno, String Remarks,String TTNumber, String Date)
    {
        Map<String, Object> checklist=new HashMap<>();
        checklist.put(Sno,Remarks);
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("Calibration").document(TTNumber+"_"+Date)
                .set(checklist, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    Log.d("Document", "ok");
            }
        });
    }
    public Task<QuerySnapshot> View()
    {
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        Task<QuerySnapshot> doc=null;
        Map<String ,Object> data= new HashMap<>();
        doc= FirebaseFirestore.getInstance().collection("Calibration").
                        get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.d("Document", "ok");
                    }
                });
        while (!doc.isComplete())
            continue;
        return doc;
    }
    public Task<QuerySnapshot> View(String  Label,String finder)
    {
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        Task<QuerySnapshot> doc=null;
        Map<String ,Object> data= new HashMap<>();
        doc= FirebaseFirestore.getInstance().collection("Calibration")
                .whereEqualTo(Label,finder).
                        get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.d("Document", "ok");
                    }
                });
        while (!doc.isComplete())
            continue;
        return doc;
    }
    public Task<QuerySnapshot> ViewOne(String Date,String TTNumber)
    {
        FirebaseFirestore db=null;
        db=FirebaseFirestore.getInstance();
        Map<String ,Object> data=null;
        data= new HashMap<>();
        Task<QuerySnapshot> doc=null;
        doc= FirebaseFirestore.getInstance().collection("Calibration")
                .whereEqualTo("Date",Date).whereEqualTo("TTNumber",TTNumber).
                        get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.d("Document", "ok");
                    }
                });
        while (!doc.isComplete())
            continue;
        return doc;
    }

    public void delete(String Date,String TTNumber) {
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        FirebaseFirestore.getInstance().collection("Calibration").document(TTNumber+"_"+Date).delete().
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("Document", "ok");
                    }
                });
    }

    public void Update(String Date,String TTNumber, String Sno,String Remarks) {
        DocumentReference doc=FirebaseFirestore.getInstance().collection("Calibration").
                document(TTNumber+"_"+Date);
        doc.update(Sno,Remarks);
    }

}

