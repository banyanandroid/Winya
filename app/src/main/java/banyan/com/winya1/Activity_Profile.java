package banyan.com.winya1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import banyan.com.winya1.adapter.Search_Adapter;
import banyan.com.winya1.global.AppConfig;
import banyan.com.winya1.global.SessionManager;


public class Activity_Profile extends AppCompatActivity {

    private Toolbar mToolbar;

    TextView txt_username, txt_primary_number, txt_mail_id,
            txt_university, txt_course;

    Button btn_update;

    // Session Manager Class
    SessionManager session;
    String str_user_name;
    public static String str_user_id;

    ImageView img_profile_picture;

    EditText edt_addressline_one, edt_addressline_two, edt_city,
            edt_state, edt_pincode, edt_country;

    String str_first_name, str_second_name, str_mail_id, str_primary_number, str_secondary_number, str_univ_applied, str_course_applied, str_prof_picture, str_address_line_one, str_address_line_two, str_city, str_state, str_pincode, str_country = "";

    public static RequestQueue queue;

    String TAG = "";

    public static final String TAG_USER_FIRST_NAME = "applicant_firstname";
    public static final String TAG_USER_SECOND_NAME = "applicant_sec_name";
    public static final String TAG_MAIL_ID = "applicant_mail";
    public static final String TAG_PRIMARY_NUMBER = "applicant_phone";
    public static final String TAG_SECONDARY_NUMBER = "applicant_scndry_ph";
    public static final String TAG_UNIVERSITY_APPLIED = "college_name";
    public static final String TAG_COURSE_APPLIED = "course_name";
    public static final String TAG_PROFILE_PICTURE = "profile_picture";
    public static final String TAG_ADDRESS_LINE_ONE = "applicant_addr1";
    public static final String TAG_ADDRESS_LINE_TWO = "applicant_addr2";
    public static final String TAG_CITY = "applicant_city";
    public static final String TAG_STATE = "applicant_state";
    public static final String TAG_PINCODE = "applicant_pincode";
    public static final String TAG_COUNTRY = "applicant_cuntry";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Activity_Dashboard.class);
                startActivity(i);
                finish();
            }
        });

        session = new SessionManager(getApplicationContext());

        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // name
        str_user_name = user.get(SessionManager.KEY_USER);
        str_user_id = user.get(SessionManager.KEY_USER_ID);

        btn_update = (Button) findViewById(R.id.btn_update);

        img_profile_picture = (ImageView) findViewById(R.id.user_profile_picture);

        txt_username = (TextView) findViewById(R.id.txt_username);
        txt_primary_number = (TextView) findViewById(R.id.txt_primary_mobile_num);
        txt_mail_id = (TextView) findViewById(R.id.txt_mail_id);
        txt_university = (TextView) findViewById(R.id.txt_applied_univ);
        txt_course = (TextView) findViewById(R.id.txt_course_applied);

        edt_addressline_one = (EditText) findViewById(R.id.addressline_one);
        edt_addressline_two = (EditText) findViewById(R.id.addressline_two);
        edt_city = (EditText) findViewById(R.id.city);
        edt_state = (EditText) findViewById(R.id.state);
        edt_pincode = (EditText) findViewById(R.id.pincode);
        edt_country = (EditText) findViewById(R.id.country);


        try {
            queue = Volley.newRequestQueue(Activity_Profile.this);
            Get_User_Profile();

        } catch (Exception e) {
            // TODO: handle exceptions
        }


        try {
            txt_username.setText(str_first_name);
            txt_primary_number.setText(str_primary_number);
            txt_mail_id.setText(str_mail_id);
            txt_university.setText(str_univ_applied);
            txt_course.setText(str_course_applied);

            edt_addressline_one.setText(str_address_line_one);
            edt_addressline_two.setText(str_address_line_two);
            edt_city.setText(str_city);
            edt_state.setText(str_state);
            edt_pincode.setText(str_pincode);
            edt_country.setText(str_country);


        } catch (Exception e) {

        }


    }

    /*****************************
     * GET User Profile
     ***************************/

    public void Get_User_Profile() {


        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_get_user_profile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                System.out.println("CAME RESPONSE ::: " + response.toString());
                try {
                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("success");

                    if (success == 1) {

                        JSONArray arr;

                        arr = obj.getJSONArray("datas");

                        for (int i = 0; arr.length() > i; i++) {
                            JSONObject obj1 = arr.getJSONObject(i);

                            str_first_name = obj1.getString(TAG_USER_FIRST_NAME);
                            str_second_name = obj1.getString(TAG_USER_SECOND_NAME);
                            str_mail_id = obj1.getString(TAG_MAIL_ID);
                            str_primary_number = obj1.getString(TAG_PRIMARY_NUMBER);
                            str_secondary_number = obj1.getString(TAG_SECONDARY_NUMBER);
                            str_univ_applied = obj1.getString(TAG_UNIVERSITY_APPLIED);
                            str_course_applied = obj1.getString(TAG_COURSE_APPLIED);
                            str_prof_picture = obj1.getString(TAG_PROFILE_PICTURE);
                            str_address_line_one = obj1.getString(TAG_ADDRESS_LINE_ONE);
                            str_address_line_two = obj1.getString(TAG_ADDRESS_LINE_TWO);
                            str_city = obj1.getString(TAG_CITY);
                            str_state = obj1.getString(TAG_STATE);
                            str_pincode = obj1.getString(TAG_PINCODE);
                            str_country = obj1.getString(TAG_COUNTRY);

                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<String, String>();
                            // adding each child node to HashMap key => value

                            map.put(TAG_USER_FIRST_NAME, str_first_name);
                            map.put(TAG_USER_SECOND_NAME, str_second_name);
                            map.put(TAG_MAIL_ID, str_mail_id);
                            map.put(TAG_PRIMARY_NUMBER, str_primary_number);
                            map.put(TAG_SECONDARY_NUMBER, str_secondary_number);
                            map.put(TAG_UNIVERSITY_APPLIED, str_univ_applied);
                            map.put(TAG_COURSE_APPLIED, str_course_applied);
                            map.put(TAG_PROFILE_PICTURE, str_prof_picture);
                            map.put(TAG_ADDRESS_LINE_ONE, str_address_line_one);
                            map.put(TAG_ADDRESS_LINE_TWO, str_address_line_two);
                            map.put(TAG_CITY, str_city);
                            map.put(TAG_STATE, str_state);
                            map.put(TAG_PINCODE, str_pincode);
                            map.put(TAG_COUNTRY, str_country);

                        }

                    } else if (success == 0) {


                        Alerter.create(Activity_Profile.this)
                                .setTitle("WINYA")
                                .setText("Oops! Something went wrong :( \n Try Again")
                                .setBackgroundColor(R.color.Alert_Fail)
                                .show();
                    }


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Alerter.create(Activity_Profile.this)
                        .setTitle("WINYA")
                        .setText("Internal Error :(\n" + error.getMessage())
                        .setBackgroundColor(R.color.colorPrimaryDark)
                        .show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", str_user_id);

                System.out.println("USER_ID ::: " + str_user_id);


                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }


}