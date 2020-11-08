package com.dhumuni.calibrationchecklist;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class PdfHandler extends AppCompatActivity {
    int warplength=40;
    int forwardY=0;
    ArrayList<String > list=new ArrayList<>();
    ArrayList<String > Remarks=new ArrayList<>();
    ArrayList<String > Storage=new ArrayList<>();
    ExampleItem exampleItem=new ExampleItem();
    FireBaseHandler db=new FireBaseHandler();
    boolean testing=true;
    int count=1;
    String Max = null;
    int Listcall=0;
    int Storagecall=0;
    int Maxlength=0;
    Button btnCreatePdf;
    TextView tv_title;
    TextView tv_sub_title;
    TextView tv_location;
    TextView tv_city;
    PdfDocument.PageInfo myPageInfo;
    PdfDocument.Page documentPage;
    String file_name_path = "";
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfhandler);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (!hasPermissions(PdfHandler.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(PdfHandler.this, PERMISSIONS, PERMISSION_ALL);
        }

        File file = new File(this.getExternalFilesDir(null).getAbsolutePath(), "pdfsdcard_location");
        if (!file.exists()) {
            file.mkdir();
        }
        btnCreatePdf = findViewById(R.id.btnCreatePdf);
        tv_title = findViewById(R.id.tv_title);
        tv_sub_title = findViewById(R.id.tv_sub_title);
        tv_location = findViewById(R.id.tv_location);
        tv_city = findViewById(R.id.tv_city);
     /*   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            createpdf();
        }*/
        btnCreatePdf.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                createpdf();
            }
        });

    }

    public void ViewRemarks(String Date,String TTNumber){
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
            for(int j=0;j<Numbers.length;j++)
            {
                Remarks.add(Mlist.get(Numbers[j]).toString());
            }
        }
        data=null;
    }
    public void Display(String st,int warplength) {
        if (testing) {
            st = st.replaceAll(" ", "~!");
            String sub = "";
            for (int i = 0; i < warplength; i++) {
                if (st.length() > warplength)
                    sub = st.substring(0, warplength - i);
                else sub = st;
                if (sub.endsWith("~!")) {
                    sub = sub.replaceAll("~!", " ").trim();
                    if(Storagecall==0) list.add(sub);
                    else Storage.add(sub);
                    st = st.replaceAll("~!", " ");
                    st = st.replace(sub, "").trim();
                    Display(st, warplength);
                } else if (sub.endsWith("$%")&& count==1){
                    count=count+1;
                    sub = sub.replaceAll("~!", " ");
                    sub = sub.replace("$%", "").trim();
                    if(Storagecall==0) list.add(sub);
                    else Storage.add(sub);
                    break;
                }
            }
        }
        testing=false;
        return;
    }
 /*   }*/
    public String Max(int warplength){
    for(int i=0;i<exampleItem.GetNumbers().length;i++) {
        String[] Particulars = exampleItem.GetParticular();
        String st = Particulars[i] + "$%";
        testing=true;
        count=1;
        Storagecall=1;
        Listcall=0;
        Display(st, warplength);
        for (String x:Storage) {
            if(Maxlength<x.length()){
                Max=x;
                Maxlength=x.length();
            }
        }
    }
    return Max;
}
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void createpdf() {
        Rect bounds = new Rect();
        int pageWidth = 595;
        int pageheight = 842;
        int pathHeight = 2;
        String[] numbers, Particulars;
        numbers = exampleItem.GetNumbers();
        Particulars = exampleItem.GetParticular();
        final String fileName = "Report1";
        file_name_path = "/pdfsdcard_location/" + fileName + ".pdf";
        PdfDocument myPdfDocument = new PdfDocument();
        Paint paint = new Paint();
        Paint paint2 = new Paint();
        paint2.setColor(Color.BLACK);
        paint2.setTextSize(20.0f);
        Path path = new Path();
        int page=1;
        int InitialY=0;
        int y=80;
        String Tick="✓";
        myPageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageheight, page).create();
        documentPage = myPdfDocument.startPage(myPageInfo);
        Canvas canvas = documentPage.getCanvas();
        canvas.drawLine(0,y,pageWidth,y,paint2);
        Resources res = getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.iocllogo);
        /*Bitmap b = (Bitmap.createScaledBitmap(bitmap, 80, 70,false ));*/
        Bitmap b = (Bitmap.createScaledBitmap(bitmap, 80, 70,false ));
        canvas.drawBitmap(b, 10, 5, paint);
        paint2.setTextSize(20.0f);
        String Checklist="CHECK LIST FOR CALIBRATION OT TT UNDER CONTRACTOR AT SALEM TERMINAL";
        String IoclNAme="INDIAN OIL CORPORATION LIMITED, ";
        String PlantNameHindi="इंडियन ऑयल कार्पोरेशन लिमिटेड, सेलम टर्मिनल.";
        String PlantName=" SALEM TERMINAL.";
        String WorkOrderNo="WO.NO.REF.TNSO/OPS/POL/MSHSD/4150/2016-21";
        String Transporter="THE TRANSPORTER/DEALER: ";
        String TTNumber="TT REGISTRATION NUMBER: ";
        String DateofChecking="DATE OF CHECKING: ";
        String Capacity="CAPACITY OF TT  (IN KL): ";
        canvas.drawText(PlantNameHindi,150,25,paint2);
        canvas.drawText(IoclNAme,100,50,paint2);
        paint2.getTextBounds(IoclNAme,0,IoclNAme.length(),bounds);
        paint2.setTextSize(16.0f);
        canvas.drawText(PlantName,100+bounds.width(),50,paint2);
        paint2.setTextSize(12.5f);
        canvas.drawText(Checklist,107,75,paint2);
        paint2.setTextSize(10.0f);
        y += paint.descent() - paint.ascent();
        canvas.drawText(WorkOrderNo,0,y,paint2);
        paint2.getTextBounds(WorkOrderNo,0,WorkOrderNo.length(),bounds);
        canvas.drawText(DateofChecking,bounds.width()+100,y,paint2);
        canvas.drawLine(0,y+2,pageWidth,y+2,paint2);
        y += paint.descent() - paint.ascent();
        canvas.drawText(Transporter,0,y,paint2);
        canvas.drawLine(0,y+2,pageWidth,y+2,paint2);
        y += paint.descent() - paint.ascent();
        canvas.drawText(TTNumber,0,y,paint2);
        paint2.getTextBounds(WorkOrderNo,0,WorkOrderNo.length(),bounds);
        canvas.drawText(Capacity,bounds.width()+100,y,paint2);
        canvas.drawLine(0,y+2,pageWidth,y+2,paint2);
        InitialY=y+2;
        paint2.setTextSize(20.0f);
        y += paint.descent() - paint.ascent();
        canvas.drawLine(0,y+10,pageWidth,y+10,paint2);
        String MaxParticularText=Max(warplength);
        paint.getTextBounds(MaxParticularText,0,MaxParticularText.length(),bounds);
        int start=bounds.width();
        canvas.drawText("S.No",0,y+8,paint2);
        canvas.drawText("PARTICULARS",43+25,y+8,paint2);
        canvas.drawText("YES",start+46+5,y+8,paint2);
        canvas.drawText("NO",start+100+5,y+8,paint2);
        canvas.drawText("REMARKS",start+154+25,y+8,paint2);
        y += paint.descent() - paint.ascent();
        int countsno=0;
        for(int i=0;i<exampleItem.GetNumbers().length;i++) {
            boolean firsttime=true;
            String st = Particulars[i] + "$%";
            list.clear();
            count=1;
            testing=true;
            Storagecall=0;
            Listcall=1;
            Display(st, warplength);
            y=y+10;
            for (String txt : list) {
                if(y>pageheight) {
                    y=20;
                    page+=1;
                    myPdfDocument.finishPage(documentPage);
                    myPageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageheight, page).create();
                    documentPage = myPdfDocument.startPage(myPageInfo);
                    canvas = documentPage.getCanvas();
                }
                canvas.drawText(txt, 45, y, paint);
                forwardY=y;
                if(firsttime){
                    canvas.drawText(numbers[countsno], 20, y, paint);
                    canvas.drawText(Tick, start+68, y, paint);
                    canvas.drawText(Tick, start+122, y, paint);
                    countsno+=1;
                    firsttime=false;
                }
                y += paint.descent() - paint.ascent();

            } 
            canvas.drawLine(0, y-10, pageWidth, y-10, paint2);
            if(page==1){
                canvas.drawLine(43, InitialY, 43, y-10, paint2);
                canvas.drawLine(start+46, InitialY, start+46, y-10, paint2);
                canvas.drawLine(start+100, InitialY, start+100, y-10, paint2);
                canvas.drawLine(start+154, InitialY, start+154, y-10, paint2);
            }
            else {
                canvas.drawLine(43, 0, 43, y-10, paint2);
                canvas.drawLine(start+46, 0, start+46, y-10, paint2);
                canvas.drawLine(start+100, 0, start+100, y-10, paint2);
                canvas.drawLine(start+154, 0, start+154, y-10, paint2);
            }

            canvas.drawLine(0,0,0,pageheight,paint2);
            canvas.drawLine(pageWidth,0,pageWidth,pageheight,paint2);
            canvas.drawLine(0,0,pageWidth,0,paint2);
            canvas.drawLine(0,pageheight,pageWidth,pageheight,paint2);

        }
        y=forwardY;
        canvas.drawLine(0,y+80,start+154,y+80,paint2);
        canvas.drawText("REMARKS IF ANY: ", 1, y+20, paint);
        y += paint.descent() - paint.ascent();
        canvas.drawLine(0,y+100,pageWidth,y+100,paint2);
        canvas.drawLine(start+154, forwardY, start+154, y+100, paint2);
        String Fit="TT IS FIT FOR INDUCTION/FC/CALIBRATION: ";
        canvas.drawText(Fit, 0, y+100-10, paint);
        canvas.drawText("YES/NO", start+20, y+100-10, paint);
        canvas.drawText("SIGNATURE:", start+154, y+100-50, paint);
        canvas.drawText("NAME:", start+154, y+100-10, paint);

        /*    paint.getTextBounds(tv_sub_title.getText().toString(), 0, tv_sub_title.getText().toString().length(), bounds);
            x = (canvas.getWidth() / 2) - (bounds.width() / 2);
            y += paint.descent() - paint.ascent();
            canvas.drawText(tv_sub_title.getText().toString(), x, y, paint);

            y += paint.descent() - paint.ascent();
            canvas.drawText("", x, y, paint);

//horizontal line
            path.lineTo(pageWidth, pathHeight);
            paint2.setColor(Color.GRAY);
            paint2.setStyle(Paint.Style.STROKE);
            path.moveTo(x, y);
            canvas.drawLine(0, y, pageWidth, y, paint2);

//blank space
            y += paint.descent() - paint.ascent();
            canvas.drawText("", x, y, paint);

            y += paint.descent() - paint.ascent();
            x = 10;
            canvas.drawText(tv_location.getText().toString(), x, y, paint);

            y += paint.descent() - paint.ascent();
            x = 10;
            canvas.drawText(tv_city.getText().toString(), x, y, paint);

//blank space
            y += paint.descent() - paint.ascent();
            canvas.drawText("", x, y, paint);

//horizontal line
            path.lineTo(pageWidth, pathHeight);
            paint2.setColor(Color.GRAY);
            paint2.setStyle(Paint.Style.STROKE);
            path.moveTo(x, y);
            canvas.drawLine(0, y, pageWidth, y, paint2);

//blank space
            y += paint.descent() - paint.ascent();
            canvas.drawText("", x, y, paint);
            Resources res = getResources();
            Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.iocl);
            Bitmap b = (Bitmap.createScaledBitmap(bitmap, 100, 50, false));
            canvas.drawBitmap(b, x, y, paint);
            y += 25;
            canvas.drawText(getString(R.string.app_name), 120, y, paint);*/
        myPdfDocument.finishPage(documentPage);
        File file = new File(this.getExternalFilesDir(null).getAbsolutePath() + file_name_path);
        try {
            myPdfDocument.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        myPdfDocument.close();
        viewPdfFile();
    }
    public void viewPdfFile() {
        File file = new File(this.getExternalFilesDir(null).getAbsolutePath() + file_name_path);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri test=Uri.parse("file://"+file);
        intent.setDataAndType(test, "application/pdf");
        /*intent.setDataAndType(Uri.fromFile(file), "application/pdf");*/
        startActivity(intent);
    }
    public static boolean hasPermissions(PdfHandler context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}