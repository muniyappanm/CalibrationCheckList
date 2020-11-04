package com.dhumuni.calibrationchecklist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity {
    Button ttchecklist,report,logout;
    TextView loading;
    ImageView budget_imageview;
    FireBaseHandler db=new FireBaseHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setTitle("TT Calibration");
        ttchecklist=(Button)findViewById(R.id.button_ttchecklist);
        report=(Button)findViewById(R.id.button_report);
        loading=(TextView)findViewById(R.id.loading);
        logout=findViewById(R.id.button_logout);
        budget_imageview=findViewById(R.id.budget_imageview);
        TTChecklist();
        Report();
        Logout();
    }

    private void Logout() {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MenuActivity.this, PdfHandler.class);
                startActivity(in);
               /* FirebaseAuth.getInstance().signOut();
                Intent in = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(in);
*/
            }
        });
    }

    /*  @Override
      public boolean onCreateOptionsMenu(Menu menu) {

          getMenuInflater().inflate(R.menu.menu_scrolling,menu);
          return true;

      }

      @Override
      public boolean onOptionsItemSelected(@NonNull MenuItem item) {

          int id=item.getItemId();
          switch (id){
              case R.id.additem_menu:
                  startActivity(new Intent(MenuActivity.this,AddItem.class));
                  break;
          }

          return true;
      }
  */
    @Override
    protected void onStart() {
        super.onStart();
        ttchecklist.setVisibility(View.VISIBLE);
        report.setVisibility(View.VISIBLE);
        logout.setVisibility(View.VISIBLE);
        budget_imageview.setVisibility(View.VISIBLE);
        loading.setVisibility(View.INVISIBLE);
    }

    private void TTChecklist() {
        ttchecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MenuActivity.this, TTChecklist.class);
                startActivity(in);
                ttchecklist.setVisibility(View.INVISIBLE);
                budget_imageview.setVisibility(View.INVISIBLE);
                report.setVisibility(View.INVISIBLE);
                logout.setVisibility(View.INVISIBLE);
                loading.setVisibility(View.VISIBLE);
            }
        });
    }
    private void Report() {
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ttchecklist.setVisibility(View.INVISIBLE);
                budget_imageview.setVisibility(View.INVISIBLE);
                report.setVisibility(View.INVISIBLE);
                logout.setVisibility(View.INVISIBLE);
                loading.setVisibility(View.VISIBLE);
                startActivity(new Intent(MenuActivity.this,Report.class));
            }
        });
    }

}
