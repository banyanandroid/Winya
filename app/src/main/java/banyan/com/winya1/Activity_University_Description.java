package banyan.com.winya1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;

/**
 * Created by Schan on 21-Apr-17.
 */

public class Activity_University_Description extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton fab_apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_description);

        fab_apply = (FloatingActionButton) findViewById(R.id.btn_fab_apply);


        fab_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("Apply Now Clicked");

                Intent i = new Intent(getApplicationContext(),Activity_Apply_form.class);
                startActivity(i);
                finish();


            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_fab_apply:

                System.out.println("Clicked");


                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


}
