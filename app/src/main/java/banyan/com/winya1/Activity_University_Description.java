package banyan.com.winya1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;


public class Activity_University_Description extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton fab_apply;

    TextView txt_College_name, txt_College_founded_year, txt_College_type,
            txt_College_intake, txt_College_Details, txt_College_address;


    String str_college_name, str_country_name, str_country_image, str_college_photo, str_college_address,
            str_college_founded_year, str_college_type, str_college_intake, str_college_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_description);

        txt_College_name = (TextView) findViewById(R.id.txt_college_name);
        txt_College_founded_year = (TextView) findViewById(R.id.txt_college_founded_year);
        txt_College_type = (TextView) findViewById(R.id.txt_college_type);
        txt_College_intake = (TextView) findViewById(R.id.txt_college_intake);
        txt_College_Details = (TextView) findViewById(R.id.txt_college_details);
        txt_College_address = (TextView) findViewById(R.id.txt_college_address);


        fab_apply = (FloatingActionButton) findViewById(R.id.btn_fab_apply);


        fab_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("Apply Now Clicked");

                Intent i = new Intent(getApplicationContext(), Activity_Apply_form.class);
                startActivity(i);
                finish();


            }
        });


        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());


        str_college_name = sharedPreferences.getString("str_college_name", "str_college_name");
        str_country_name = sharedPreferences.getString("str_country_name", "str_country_name");
        str_country_image = sharedPreferences.getString("str_country_image", "str_country_image");
        str_college_photo = sharedPreferences.getString("str_college_photo", "str_college_photo");
        str_college_address = sharedPreferences.getString("str_college_address", "str_college_address");
        str_college_founded_year = sharedPreferences.getString("str_college_founded_year", "str_college_founded_year");
        str_college_type = sharedPreferences.getString("str_college_type", "str_college_type");
        str_college_intake = sharedPreferences.getString("str_college_intake", "str_college_intake");
        str_college_details = sharedPreferences.getString("str_college_details", "str_college_details");

        try {
            txt_College_name.setText(str_college_name);
            txt_College_founded_year.setText("SINCE - " + str_college_founded_year);
            txt_College_type.setText("TYPE : " + str_college_type);
            txt_College_intake.setText("INTAKE - " + str_college_intake);
            txt_College_address.setText(str_college_address);
            txt_College_Details.setText(str_college_details);


        } catch (Exception e) {

        }


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
