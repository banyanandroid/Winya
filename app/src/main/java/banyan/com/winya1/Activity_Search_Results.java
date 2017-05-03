package banyan.com.winya1;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v4.widget.SwipeRefreshLayout;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import banyan.com.winya1.adapter.Search_Adapter;
import banyan.com.winya1.global.AppConfig;
import banyan.com.winya1.global.SessionManager;
import dmax.dialog.SpotsDialog;

import static banyan.com.winya1.Activity_Search.queue;


public class Activity_Search_Results extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView List;

    public static RequestQueue queue;

    String TAG = "";

    String str_country_id, str_course = "";

    private Toolbar mToolbar;

    public static final String TAG_COLLEGE_NAME = "college_name";
    public static final String TAG_COUNTRY_NAME = "country_name";
    public static final String TAG_COUNTRY_IMAGE = "country_image";
    public static final String TAG_COLLEGE_PHOTO = "college_photo";
    public static final String TAG_COLLEGE_ADDRESS = "college_address";
    public static final String TAG_COLLEGE_FOUNDED_YEAR = "founded_year";
    public static final String TAG_COLLEGE_TYPE = "type";
    public static final String TAG_COLLEGE_INTAKE = "intake";
    public static final String TAG_COLLEGE_DETAILS = "college_details";

    static ArrayList<HashMap<String, String>> search_result_list;

    HashMap<String, String> params = new HashMap<String, String>();

    public Search_Adapter adapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        isInternetOn();

        List = (ListView) findViewById(R.id.search_results_listview_search_univ);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_search_univ);

        swipeRefreshLayout.setOnRefreshListener(Activity_Search_Results.this);


        // Hashmap for ListView
        search_result_list = new ArrayList<HashMap<String, String>>();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        str_country_id = sharedPreferences.getString("str_selected_country_id", "str_selected_country_id");
        str_course = sharedPreferences.getString("str_selected_course_id", "str_selected_course_id");

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        if (str_country_id.equals("str_selected_country_id")) {
                                            str_country_id = "0";
                                        } else if (str_course.equals("str_selected_course_id")) {
                                            str_course = "0";
                                        }
                                        try {
                                            queue = Volley.newRequestQueue(Activity_Search_Results.this);
                                            Get_Search_Result();

                                        } catch (Exception e) {
                                            // TODO: handle exceptions
                                        }
                                    }
                                }
        );


        /***********************************
         *  LISTVIEW OnItemClick
         * ************************************/

        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                String str_college_name = search_result_list.get(position).get(TAG_COLLEGE_NAME);
                String str_country_name = search_result_list.get(position).get(TAG_COUNTRY_NAME);
                String str_country_image = search_result_list.get(position).get(TAG_COUNTRY_IMAGE);
                String str_college_photo = search_result_list.get(position).get(TAG_COLLEGE_PHOTO);
                String str_college_address = search_result_list.get(position).get(TAG_COLLEGE_ADDRESS);
                String str_college_founded_year = search_result_list.get(position).get(TAG_COLLEGE_FOUNDED_YEAR);
                String str_college_type = search_result_list.get(position).get(TAG_COLLEGE_TYPE);
                String str_college_intake = search_result_list.get(position).get(TAG_COLLEGE_INTAKE);
                String str_college_details = search_result_list.get(position).get(TAG_COLLEGE_DETAILS);

                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("str_college_name", str_college_name);
                editor.putString("str_country_name", str_country_name);
                editor.putString("str_country_image", str_country_image);
                editor.putString("str_college_photo", str_college_photo);
                editor.putString("str_college_address", str_college_address);
                editor.putString("str_college_founded_year", str_college_founded_year);
                editor.putString("str_college_type", str_college_type);
                editor.putString("str_college_intake", str_college_intake);
                editor.putString("str_college_details", str_college_details);


                editor.commit();

                Intent i = new Intent(getApplicationContext(), Activity_University_Description.class);
                startActivity(i);


            }

        });





    }

    /**
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {
        try {
            search_result_list.clear();
            queue = Volley.newRequestQueue(Activity_Search_Results.this);
            Get_Search_Result();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    /*****************************
     * GET Search Results
     ***************************/

    public void Get_Search_Result() {

        String tag_json_obj = "json_obj_req";

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_search_results, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                System.out.println("CAME RESPONSE ::: " + response.toString());
                try {
                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("success");

                    if (success == 1) {

                        JSONArray arr;

                        arr = obj.getJSONArray("course");

                        for (int i = 0; arr.length() > i; i++) {
                            JSONObject obj1 = arr.getJSONObject(i);

                            String name = obj1.getString(TAG_COLLEGE_NAME);
                            String country_name = obj1.getString(TAG_COUNTRY_NAME);
                            String country_image = obj1.getString(TAG_COUNTRY_IMAGE);
                            String photo = obj1.getString(TAG_COLLEGE_PHOTO);
                            String address = obj1.getString(TAG_COLLEGE_ADDRESS);
                            String founded = obj1.getString(TAG_COLLEGE_FOUNDED_YEAR);
                            String type = obj1.getString(TAG_COLLEGE_TYPE);
                            String intake = obj1.getString(TAG_COLLEGE_INTAKE);
                            String details = obj1.getString(TAG_COLLEGE_DETAILS);

                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<String, String>();

                            // adding each child node to HashMap key => value
                            map.put(TAG_COLLEGE_NAME, name);
                            map.put(TAG_COUNTRY_NAME, country_name);
                            map.put(TAG_COUNTRY_IMAGE, country_image);
                            map.put(TAG_COLLEGE_PHOTO, photo);
                            map.put(TAG_COLLEGE_ADDRESS, address);
                            map.put(TAG_COLLEGE_FOUNDED_YEAR, founded);
                            map.put(TAG_COLLEGE_TYPE, type);
                            map.put(TAG_COLLEGE_INTAKE, intake);
                            map.put(TAG_COLLEGE_DETAILS, details);

                            search_result_list.add(map);

                            System.out.println("HASHMAP ARRAY" + search_result_list);


                            adapter = new Search_Adapter(Activity_Search_Results.this,
                                    search_result_list);
                            List.setAdapter(adapter);

                        }

                    } else if (success == 0) {

                        adapter = new Search_Adapter(Activity_Search_Results.this,
                                search_result_list);
                        List.setAdapter(adapter);

                        Alerter.create(Activity_Search_Results.this)
                                .setTitle("WINYA")
                                .setText("Data Not Found :( \n Try Again")
                                .setBackgroundColor(R.color.Alert_Fail)
                                .show();
                    }


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                swipeRefreshLayout.setRefreshing(false);
                // dialog.dismiss();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);

                Alerter.create(Activity_Search_Results.this)
                        .setTitle("WINYA")
                        .setText("Internal Error :(\n" + error.getMessage())
                        .setBackgroundColor(R.color.colorPrimaryDark)
                        .show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("country", str_country_id); // replace as str_id
                params.put("course", str_course);

                System.out.println("country ::: " + str_country_id);
                System.out.println("course ::: " + str_course);

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }


    /***********************************
     *  Internet Connection
     * ************************************/

    public final boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {

            // if connected with internet

            //Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {

            new AlertDialog.Builder(Activity_Search_Results.this)
                    .setTitle("GEM CRM")
                    .setMessage("Oops no internet !")
                    .setIcon(R.mipmap.ic_launcher)
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub

                                }
                            }).show();
            return false;
        }
        return false;
    }

}
