package banyan.com.winya1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Schan on 22-Apr-17.
 */

public class Activity_Apply_form extends AppCompatActivity {


    EditText edt_name, edt_email, edt_mobilenum;

    Button btn_Submit;

    Spinner Year_of_study;

    RadioGroup Course_type;

    RadioButton radio_btn_parttime, radio_btn_fulltime;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_form);

        Course_type = (RadioGroup) findViewById(R.id.form_apply_radio_group);
        radio_btn_parttime = (RadioButton) findViewById(R.id.radio_parttime);
        radio_btn_fulltime = (RadioButton) findViewById(R.id.radio_fulltime);

        edt_name = (EditText) findViewById(R.id.form_apply_edt_name);
        edt_email = (EditText) findViewById(R.id.form_apply_edt_email);
        edt_mobilenum = (EditText) findViewById(R.id.form_apply_edt_mobile);

        Year_of_study = (Spinner) findViewById(R.id.spn_year_of_study);

        btn_Submit = (Button) findViewById(R.id.form_apply_btn_submit);

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Applied successfully");

                Toast.makeText(getApplicationContext(),"Applied successfully", Toast.LENGTH_LONG).show();
            }
        });

    }

}
