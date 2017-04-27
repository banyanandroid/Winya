package banyan.com.winya1;


/**
 * Created by Schan on 20-Apr-17.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

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


    FloatingActionButton fab_search;
    AutoCompleteTextView auto_country, auto_cource;
    TextView t1;

    SpotsDialog dialog;
    public static RequestQueue queue;

    String TAG = "Apply Now";

    public static final String TAG_COUNTRY_ID = "country_id";
    public static final String TAG_COUNTRY_TITLE = "country_name";

    ArrayList<String> Arraylist_country_id = null;
    ArrayList<String> Arraylist_country_title = null;

    private ArrayAdapter<String> adapter_contact;
    ArrayList<String> stringArray_contact = null;

    String str_selected_country, str_selected_country_id = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(getString(R.string.expand));

        auto_country = (AutoCompleteTextView) findViewById(R.id.txt_apply_country);
        auto_cource = (AutoCompleteTextView) findViewById(R.id.txt_apply_course);

        fab_search = (FloatingActionButton) findViewById(R.id.btn_fab_search);

        Arraylist_country_id = new ArrayList<String>();
        Arraylist_country_title = new ArrayList<String>();

        fab_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("Apply Now Clicked");
                // Intent
                Intent i = new Intent(getApplicationContext(), Activity_University_Description.class);
                startActivity(i);
                finish();


            }
        });

        try {
            dialog = new SpotsDialog(Activity_Search.this);
            dialog.show();
            queue = Volley.newRequestQueue(getApplicationContext());
            Get_Country();
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

                        arr = obj.getJSONArray("country");

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
                                    str_selected_country_id = Arraylist_country_id.get(arg2);
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


}
