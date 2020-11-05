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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PdfHandler extends AppCompatActivity {
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
        btnCreatePdf.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                createpdf();
            }
        });

    }
    public void Display(Rect bounds,String st,Canvas canvas,int y,Paint paint,int warplength){
        if(canvas.getWidth()>bounds.width()) canvas.drawText(st.replace("$%"," ").trim(), 20, y, paint);
        else {
            st=st.replaceAll(" ","~!");
            String sub;
            for(int i=0;i<warplength;i++){
                int a;
                int b;
                int c;
                a=canvas.getWidth();
                b=bounds.width();
                c=st.length();
                int add=(int)(Math.floor(c*a/b));
                if(warplength<=add)
                         sub=st.substring(0,-warplength+i+add);
                else sub=st.substring(0,1);


               /* if(warplength<=st.length())
                sub=st.substring(0,(-warplength+i+add));*/

                if(sub.endsWith("~!"))  {
                    sub=sub.replaceAll("~!"," ").trim();
                    canvas.drawText(sub, 20, y, paint);
                    st= st.replaceAll("~!"," ");
                    st=st.replace(sub,"").trim();
                    y += paint.descent() - paint.ascent();
                    Rect bound = new Rect();
                    paint.getTextBounds(st,0,st.length(),bound);
                    Display(bound,st,canvas,y,paint,warplength);
                }
                else if(sub.endsWith("$%")){
                    sub=sub.replaceAll("~!"," ");
                    sub=sub.replace("$%","").trim();
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void createpdf() {
        Rect bounds = new Rect();
        int pageWidth = 595;
        int pageheight = 842;
        int pathHeight = 2;

        final String fileName = "Report";
        file_name_path = "/pdfsdcard_location/" + fileName + ".pdf";
        PdfDocument myPdfDocument = new PdfDocument();
        Paint paint = new Paint();
        Paint paint2 = new Paint();
        Path path = new Path();
        int page=1;
            myPageInfo= new PdfDocument.PageInfo.Builder(pageWidth, pageheight,page).create();
            documentPage = myPdfDocument.startPage(myPageInfo);
            Canvas canvas = documentPage.getCanvas();
            int y = 50; // x = 10,
            int x = 0;
            ExampleItem exampleItem=new ExampleItem();
            String[] numbers,Particulars;
            numbers=exampleItem.GetNumbers();
            Particulars=exampleItem.GetParticular();
            String st="The longest word in any of the major English language dictionaries is pne, " +
                    "a word that refers to a lung disease contracted " +
                    "from the inhalation of very fine silica particles, specifically from a volcano; medically, it is the same as silicosis"+"$%";
            /*String st=numbers[0]+" "+Particulars[0]+"$%";*/
            paint.getTextBounds(st,0,st.length(),bounds);
            double a,b,c;
            a=canvas.getWidth();
            b=bounds.width();
            c=st.length();
           Display(bounds,st,canvas,y,paint,100);
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