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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import banyan.com.winya1.global.AppConfig;
import dmax.dialog.SpotsDialog;


public class Activity_Search extends AppCompatActivity {


    AutoCompleteTextView auto_country, auto_course;
    TextView t1;
    Button btn_search;

    SpotsDialog dialog;
    public static RequestQueue queue;

    String TAG = "Apply Now";

    public static final String TAG_COUNTRY_ID = "country_id";
    public static final String TAG_COUNTRY_TITLE = "country_name";

    public static final String TAG_COURSE_ID = "course_id";
    public static final String TAG_COURSE_TITLE = "course_name";

    ArrayList<String> Arraylist_country_id = null;
    ArrayList<String> Arraylist_country_title = null;

    ArrayList<String> Arraylist_course_id = null;
    ArrayList<String> Arraylist_course_title = null;

    private ArrayAdapter<String> adapter_contact;
    ArrayList<String> stringArray_contact = null;

    private ArrayAdapter<String> adapter_contact1;
    ArrayList<String> stringArray_contact1 = null;

    String str_selected_country, str_selected_country_id = "";
    String str_selected_course, str_selected_course_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(getString(R.string.expand));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Activity_Dashboard.class);
                startActivity(i);
                finish();
            }
        });

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());


        auto_country = (AutoCompleteTextView) findViewById(R.id.txt_apply_country);
        auto_course = (AutoCompleteTextView) findViewById(R.id.txt_apply_course);
        btn_search = (Button) findViewById(R.id.apply_btn_search);


        Arraylist_country_id = new ArrayList<String>();
        Arraylist_country_title = new ArrayList<String>();

        Arraylist_course_id = new ArrayList<String>();
        Arraylist_course_title = new ArrayList<String>();


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("COUNTRY : " + str_selected_country_id);
                System.out.println("COURSE : " + str_selected_course);

                if (str_selected_country_id.isEmpty() && str_selected_course_id.isEmpty()) {
                    auto_country.setError("Please Select Country");
                    auto_course.setError("Please Enter Course");
                    TastyToast.makeText(getApplicationContext(), "Both Country & Course cannot be empty Please select atleast one", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else {

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("str_selected_country_id", str_selected_country_id);
                    editor.putString("str_selected_course_id", str_selected_course);
                    editor.commit();

                    Intent i = new Intent(getApplicationContext(), Activity_Search_Results.class);
                    startActivity(i);
                    finish();
                }


            }
        });


        try {
            dialog = new SpotsDialog(Activity_Search.this);
            dialog.show();
            queue = Volley.newRequestQueue(getApplicationContext());
            Get_Country();
            queue = Volley.newRequestQueue(getApplicationContext());
            Get_Course();


        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    /*****************************
     * GET Country Details
     ***************************/

    public void Get_Country() {

        String tag_json_obj = "json_obj_req";
        System.out.println("CAME 1");
        StringRequest request = new StringRequest(Request.Method.GET,
                AppConfig.url_country, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("success");

                    if (success == 1) {

                        JSONArray arr;

                        arr = obj.getJSONArray("course");

                        for (int i = 0; arr.length() > i; i++) {
                            JSONObject obj1 = arr.getJSONObject(i);

                            String camp_id = obj1.getString(TAG_COUNTRY_ID);
                            String camp_title = obj1.getString(TAG_COUNTRY_TITLE);

                            Arraylist_country_id.add(camp_id);
                            Arraylist_country_title.add(camp_title);
                        }
                        try {
                            adapter_contact = new ArrayAdapter<String>(Activity_Search.this,
                                    android.R.layout.simple_list_item_1, Arraylist_country_title);
                            auto_country.setAdapter(adapter_contact);
                            auto_country.setThreshold(1);

                            auto_country.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                        long arg3) {
                                    t1 = (TextView) arg1;
                                    str_selected_country = t1.getText().toString();
                                    System.out.println("ARGGGGGGGGGGGGGGGGGGGGGGGGGGGGb : " + arg2);
                                    str_selected_country_id = Arraylist_country_id.get(arg2 + 1);
                                }
                            });

                        } catch (Exception e) {

                        }


                    } else if (success == 0) {
                        TastyToast.makeText(getApplicationContext(), "Something Went Wrong :(", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    }

                    dialog.dismiss();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }


    /*****************************
     * GET COURSE Details
     ***************************/

    public void Get_Course() {

        String tag_json_obj = "json_obj_req";
        System.out.println("CAME 1");
        StringRequest request = new StringRequest(Request.Method.GET,
                AppConfig.url_course, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("success");

                    if (success == 1) {

                        JSONArray arr;

                        arr = obj.getJSONArray("course");

                        for (int i = 0; arr.length() > i; i++) {
                            JSONObject obj1 = arr.getJSONObject(i);

                            String course_id = obj1.getString(TAG_COURSE_ID);
                            String course_title = obj1.getString(TAG_COURSE_TITLE);

                            Arraylist_course_id.add(course_id);
                            Arraylist_course_title.add(course_title);

                            System.out.println("CAMEEEEEE : " + Arraylist_course_id);
                        }
                        try {
                            adapter_contact1 = new ArrayAdapter<String>(Activity_Search.this,
                                    android.R.layout.simple_list_item_1, Arraylist_course_title);
                            auto_course.setAdapter(adapter_contact1);
                            auto_course.setThreshold(1);

                            auto_course.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                        long arg3) {
                                    t1 = (TextView) arg1;
                                    String str_select;
                                    str_select = t1.getText().toString();
                                    str_selected_course_id = Arraylist_course_id.get(arg2);
                                    str_selected_course = Arraylist_course_title.get(arg2);
                                }
                            });

                        } catch (Exception e) {

                        }

                        dialog.dismiss();

                    } else if (success == 0) {
                        TastyToast.makeText(getApplicationContext(), "Something Went Wrong :(", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    }

                    dialog.dismiss();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }

    @Override
    public void onBackPressed() {
        // your code.
        Intent i = new Intent(getApplicationContext(), Activity_Dashboard.class);
        startActivity(i);
        finish();

    }

}
