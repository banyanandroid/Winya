package banyan.com.winya1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.kennyc.bottomsheet.BottomSheet;
import com.kennyc.bottomsheet.BottomSheetListener;

import java.util.HashMap;

import banyan.com.winya1.global.SessionManager;

/**
 * Created by User on 11/28/2016.
 */

public class Activity_Dashboard extends AppCompatActivity implements View.OnClickListener, BottomSheetListener {

    private static final String TAG = Activity_Dashboard.class.getSimpleName();

    FloatingActionButton fab_menu;

    // Session Manager Class
    SessionManager session;

   /* String str_name;
    public static String str_id;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        /*session = new SessionManager(getApplicationContext());

        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // name
        str_name = user.get(SessionManager.KEY_USER);
        str_id = user.get(SessionManager.KEY_USER_ID);*/

        fab_menu = (FloatingActionButton) findViewById(R.id.main_fab_menu);

        fab_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("Clicked");
                new BottomSheet.Builder(Activity_Dashboard.this, R.style.BottomSheet_Custom)
                        .setSheet(R.menu.grid_sheet)
                        .grid()
                        .setTitle("Menu")
                        .setListener(Activity_Dashboard.this)
                        .show();

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.main_fab_menu:


                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSheetShown(@NonNull BottomSheet bottomSheet) {
        Log.v(TAG, "onSheetShown");
    }

    @Override
    public void onSheetItemSelected(@NonNull BottomSheet bottomSheet, MenuItem item) {
       // Toast.makeText(getApplicationContext(), item.getTitle() + " Clicked", Toast.LENGTH_SHORT).show();

        if (item.getTitle().equals("Apply Now")) {

            Intent i = new Intent(getApplicationContext(), Activity_Search.class);
            startActivity(i);

        } else if (item.getTitle().equals("Exit")) {

           // session.logoutUser();

        } else if (item.getTitle().equals("Time Line")) {


            Intent i = new Intent(getApplicationContext(), Activity_Timeline.class);
            startActivity(i);

        }


    }

    @Override
    public void onSheetDismissed(@NonNull BottomSheet bottomSheet, int which) {
        Log.v(TAG, "onSheetDismissed " + which);

        switch (which) {
            case BottomSheet.BUTTON_POSITIVE:
                Toast.makeText(getApplicationContext(), "Positive Button Clicked", Toast.LENGTH_SHORT).show();
                break;

            case BottomSheet.BUTTON_NEGATIVE:
                Toast.makeText(getApplicationContext(), "Negative Button Clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }


}
