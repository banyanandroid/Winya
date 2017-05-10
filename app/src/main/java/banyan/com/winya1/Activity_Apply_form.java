package banyan.com.winya1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sdsmdg.tastytoast.TastyToast;
import com.tapadoo.alerter.Alerter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import banyan.com.winya1.global.AppConfig;
import dmax.dialog.SpotsDialog;

/**
 * Created by Schan on 22-Apr-17.
 */

public class Activity_Apply_form extends AppCompatActivity {


    EditText edt_name, edt_email, edt_mobilenum, edt_city;

    Button btn_Submit;

    Spinner spn_Year_of_study;

    RadioGroup RGroup_Course_type;

    String str_name, str_email, str_mobile_num, str_year_of_study, str_course_type, str_city , str_college_name , str_course = "";

    RadioButton radio_btn_parttime, radio_btn_fulltime;

    SpotsDialog dialog;

    public static RequestQueue queue;
    String TAG = " ";

    private Toolbar mToolbar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_form);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


        RGroup_Course_type = (RadioGroup) findViewById(R.id.form_apply_radio_group);
        radio_btn_parttime = (RadioButton) findViewById(R.id.radio_parttime);
        radio_btn_fulltime = (RadioButton) findViewById(R.id.radio_fulltime);

        edt_name = (EditText) findViewById(R.id.form_apply_edt_name);
        edt_email = (EditText) findViewById(R.id.form_apply_edt_email);
        edt_mobilenum = (EditText) findViewById(R.id.form_apply_edt_mobile);
        edt_city = (EditText) findViewById(R.id.form_apply_edt_city);

        spn_Year_of_study = (Spinner) findViewById(R.id.spn_year_of_study);


        btn_Submit = (Button) findViewById(R.id.form_apply_btn_submit);

        RGroup_Course_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // find which radio button is selected
                if (checkedId == R.id.radio_parttime) {

                    str_course_type = "Part Time";

                } else if (checkedId == R.id.radio_fulltime) {

                    str_course_type = "Full Time";

                } else {

                    str_course_type = "null";
                }

            }


        });

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_year_of_study = String.valueOf(spn_Year_of_study.getSelectedItem());

                str_name = edt_name.getText().toString();
                str_email = edt_email.getText().toString();
                str_mobile_num = edt_mobilenum.getText().toString();
                str_city = edt_city.getText().toString();

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                str_college_name = sharedPreferences.getString("str_college_name", "str_college_name");
                str_course = sharedPreferences.getString("str_course", "str_course");


                if (str_name.equals("null")) {

                    edt_name.setError("Please Enter Name");
                    TastyToast.makeText(getApplicationContext(), "Name cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);

                } else if (str_email.equals("null")) {

                    edt_email.setError("Please Enter Email ID");
                    TastyToast.makeText(getApplicationContext(), "Email ID cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);

                } else if (str_mobile_num.equals("null")) {

                    edt_email.setError("Please Enter Mobile Number");
                    TastyToast.makeText(getApplicationContext(), "Mobile Number Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);

                } else if (str_course_type.equals("null")) {

                    TastyToast.makeText(getApplicationContext(), "Select Course Type", TastyToast.LENGTH_LONG, TastyToast.WARNING);

                }else if (str_city.equals("null")) {

                    edt_city.setError("Please Enter Your City");
                    TastyToast.makeText(getApplicationContext(), "City Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);

                }else{

                    try {

                        dialog = new SpotsDialog(Activity_Apply_form.this);
                        dialog.show();
                        queue = Volley.newRequestQueue(getApplicationContext());
                        Function_Submit();

                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                }

                System.out.println("Applied successfully");



            }
        });

    }


    private void Function_Submit() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_apply, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                Log.d("USER_LOGIN", response.toString());
                try {
                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("success");

                    if (success == 1) {


                        Alerter.create(Activity_Apply_form.this)
                                .setTitle("WINYA")
                                .setText("Applied Sucessfully :)")
                                .setBackgroundColor(R.color.Alert_Success)
                                .show();


                    } else {

                        Alerter.create(Activity_Apply_form.this)
                                .setTitle("WINYA")
                                .setText("Oops..! Process Failed :(")
                                .setBackgroundColor(R.color.Alert_Fail)
                                .show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Alerter.create(Activity_Apply_form.this)
                        .setTitle("WINYA")
                        .setText("Internal Error !")
                        .setBackgroundColor(R.color.Alert_Warning)
                        .show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("enq_name", str_name);
                params.put("enq_email_id", str_email);
                params.put("enq_mobile_no", str_mobile_num);
                params.put("enq_city", str_city);
                params.put("year_of_study", str_year_of_study);
                params.put("course_type", str_course_type);

                params.put("college_id", str_college_name);
                params.put("course_id", str_course);


                return checkParams(params);
            }


            private Map<String, String> checkParams(Map<String, String> map) {
                Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();
                    if (pairs.getValue() == null) {
                        map.put(pairs.getKey(), "");
                    }
                }
                return map;
            }

        };

        queue.add(request);
    }


}
