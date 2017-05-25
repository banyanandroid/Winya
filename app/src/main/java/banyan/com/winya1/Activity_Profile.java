package banyan.com.winya1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import banyan.com.winya1.adapter.Search_Adapter;
import banyan.com.winya1.global.AppConfig;
import banyan.com.winya1.global.SessionManager;
import de.hdodenhof.circleimageview.CircleImageView;


public class Activity_Profile extends AppCompatActivity {

    private Toolbar mToolbar;

    TextView txt_username, txt_primary_number, txt_mail_id,
            txt_university, txt_course;

    TextView txt_edit;

    ImageView img_edit;

    CircleImageView img_profile_picture;

    EditText edt_fname, edt_lname, edt_phone, edt_email, edt_addressline_one, edt_addressline_two, edt_city,
            edt_state, edt_pincode, edt_country;

    TextInputLayout txt_in_fname, txt_in_lname, txt_in_phone, txt_in_email;

    Button btn_update;

    SessionManager session;
    String str_user_name;
    public static String str_user_id;

    String str_first_name, str_second_name, str_mail_id, str_primary_number, str_secondary_number, str_univ_applied, str_course_applied, str_prof_picture, str_address_line_one, str_address_line_two, str_city, str_state, str_pincode, str_country, str_img = "";
    String str_get_first_name, str_get_second_name, str_get_mail_id, str_get_primary_number, str_get_secondary_number, str_get_univ_applied, str_get_course_applied, str_get_address_line_one, str_get_address_line_two, str_get_city, str_get_state, str_get_pincode, str_get_country = "";

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

        HashMap<String, String> user = session.getUserDetails();
        str_user_name = user.get(SessionManager.KEY_USER);
        str_user_id = user.get(SessionManager.KEY_USER_ID);

        btn_update = (Button) findViewById(R.id.profile_btn_update);

        img_profile_picture = (CircleImageView) findViewById(R.id.user_profile_picture);
        img_edit = (ImageView) findViewById(R.id.profile_img_edit);
        txt_edit = (TextView) findViewById(R.id.profile_txt_edit);

        txt_username = (TextView) findViewById(R.id.txt_username);
        txt_primary_number = (TextView) findViewById(R.id.txt_primary_mobile_num);
        txt_mail_id = (TextView) findViewById(R.id.txt_mail_id);
        txt_university = (TextView) findViewById(R.id.txt_applied_univ);
        txt_course = (TextView) findViewById(R.id.txt_course_applied);

        txt_in_fname = (TextInputLayout) findViewById(R.id.txt_in_fname);
        txt_in_lname = (TextInputLayout) findViewById(R.id.txt_in_lname);
        txt_in_phone = (TextInputLayout) findViewById(R.id.txt_in_phone);
        txt_in_email = (TextInputLayout) findViewById(R.id.txt_in_email);

        edt_fname = (EditText) findViewById(R.id.first_name);
        edt_lname = (EditText) findViewById(R.id.last_name);
        edt_phone = (EditText) findViewById(R.id.phone_number);
        edt_email = (EditText) findViewById(R.id.email);

        edt_addressline_one = (EditText) findViewById(R.id.addressline_one);
        edt_addressline_two = (EditText) findViewById(R.id.addressline_two);
        edt_city = (EditText) findViewById(R.id.city);
        edt_state = (EditText) findViewById(R.id.state);
        edt_pincode = (EditText) findViewById(R.id.pincode);
        edt_country = (EditText) findViewById(R.id.country);

        edt_addressline_one.setFocusable(false);
        edt_addressline_one.setEnabled(false);

        edt_addressline_two.setFocusable(false);
        edt_addressline_two.setEnabled(false);

        edt_city.setFocusable(false);
        edt_city.setEnabled(false);

        edt_state.setFocusable(false);
        edt_state.setEnabled(false);

        edt_pincode.setFocusable(false);
        edt_pincode.setEnabled(false);

        edt_country.setFocusable(false);
        edt_country.setEnabled(false);

        try {
            queue = Volley.newRequestQueue(Activity_Profile.this);
            Get_User_Profile();

        } catch (Exception e) {
            // TODO: handle exceptions
        }

        txt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txt_in_fname.setVisibility(View.VISIBLE);
                txt_in_lname.setVisibility(View.VISIBLE);
                txt_in_phone.setVisibility(View.VISIBLE);
                txt_in_email.setVisibility(View.VISIBLE);

                btn_update.setVisibility(View.VISIBLE);

                edt_addressline_one.setFocusable(true);
                edt_addressline_one.setEnabled(true);

                edt_addressline_two.setFocusable(true);
                edt_addressline_two.setEnabled(true);

                edt_city.setFocusable(true);
                edt_city.setEnabled(true);

                edt_state.setFocusable(true);
                edt_state.setEnabled(true);

                edt_pincode.setFocusable(true);
                edt_pincode.setEnabled(true);

                edt_country.setFocusable(true);
                edt_country.setEnabled(true);

            }
        });

        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txt_in_fname.setVisibility(View.VISIBLE);
                txt_in_lname.setVisibility(View.VISIBLE);
                txt_in_phone.setVisibility(View.VISIBLE);
                txt_in_email.setVisibility(View.VISIBLE);

                btn_update.setVisibility(View.VISIBLE);

                edt_addressline_one.setFocusable(true);
                edt_addressline_one.setEnabled(true);

                edt_addressline_two.setFocusable(true);
                edt_addressline_two.setEnabled(true);

                edt_city.setFocusable(true);
                edt_city.setEnabled(true);

                edt_state.setFocusable(true);
                edt_state.setEnabled(true);

                edt_pincode.setFocusable(true);
                edt_pincode.setEnabled(true);

                edt_country.setFocusable(true);
                edt_country.setEnabled(true);

            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txt_in_fname.setVisibility(View.GONE);
                txt_in_lname.setVisibility(View.GONE);
                txt_in_phone.setVisibility(View.GONE);
                txt_in_email.setVisibility(View.GONE);

                edt_addressline_one.setFocusable(false);
                edt_addressline_one.setEnabled(false);

                edt_addressline_two.setFocusable(false);
                edt_addressline_two.setEnabled(false);

                edt_city.setFocusable(false);
                edt_city.setEnabled(false);

                edt_state.setFocusable(false);
                edt_state.setEnabled(false);

                edt_pincode.setFocusable(false);
                edt_pincode.setEnabled(false);

                edt_country.setFocusable(false);
                edt_country.setEnabled(false);

                str_get_first_name = edt_fname.getText().toString();
                str_get_second_name = edt_lname.getText().toString();
                str_get_mail_id = edt_email.getText().toString();
                str_get_primary_number = edt_phone.getText().toString();
                str_get_address_line_one = edt_addressline_one.getText().toString();
                str_get_address_line_two = edt_addressline_two.getText().toString();
                str_get_city = edt_city.getText().toString();
                str_get_state = edt_state.getText().toString();
                str_get_pincode = edt_pincode.getText().toString();
                str_get_country = edt_country.getText().toString();
            }
        });

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

                        }

                        try {

                            str_img = "http://epictech.in/winya/" + str_prof_picture;

                            System.out.println("IMGGGGGGGGGGGGGGGG ::: " + str_img);

                            Glide.with(getApplicationContext()).load(str_img)
                                    .thumbnail(0.5f)
                                    .crossFade()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(img_profile_picture);

                            txt_username.setText("" + str_first_name + " " + str_second_name);
                            txt_primary_number.setText("" + str_primary_number);
                            txt_mail_id.setText("" + str_mail_id);
                            txt_university.setText("" + str_univ_applied);
                            txt_course.setText("" + str_course_applied);

                            edt_fname.setText("" + str_first_name);
                            edt_lname.setText("" + str_second_name);
                            edt_phone.setText("" + str_primary_number);
                            edt_email.setText("" + str_mail_id);
                            edt_addressline_one.setText("" + str_address_line_one);
                            edt_addressline_two.setText("" + str_address_line_two);
                            edt_city.setText("" + str_city);
                            edt_state.setText("" + str_state);
                            edt_pincode.setText("" + str_pincode);
                            edt_country.setText("" + str_country);

                        } catch (Exception e) {

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