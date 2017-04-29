package banyan.com.winya1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.support.v4.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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


public class Activity_Search_Results extends AppCompatActivity {

    private ListView List;

    // Session Manager Class
    SessionManager session;

    String str_name;
    public static String str_id;

    String TAG = "";

    String str_country_id, str_course_id = "";

    public static final String TAG_College_Name = "college_name";
    public static final String TAG_College_Desc = "college_details";
    public static final String TAG_College_Founded_Year = "founded_year";


    static ArrayList<HashMap<String, String>> search_result_list;

    HashMap<String, String> params = new HashMap<String, String>();

    public Search_Adapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        search_result_list = new ArrayList<HashMap<String, String>>();
        List = (ListView) findViewById(R.id.search_results_listview);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        str_name = user.get(SessionManager.KEY_USER);
        str_id = user.get(SessionManager.KEY_USER_ID);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        str_country_id = sharedPreferences.getString("str_selected_country_id", "str_selected_country_id");
        str_course_id = sharedPreferences.getString("str_selected_course_id", "str_selected_course_id");


    }


    /*****************************
     * GET Search Results
     ***************************/

    public void GetMyAppointment() {

        String tag_json_obj = "json_obj_req";
        System.out.println("CAME 1");
        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_search_results, new Response.Listener<String>() {

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

                            String id = obj1.getString(TAG_College_Name);
                            String date = obj1.getString(TAG_College_Desc);
                            String time = obj1.getString(TAG_College_Founded_Year);

                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<String, String>();

                            // adding each child node to HashMap key => value
                            map.put(TAG_College_Name, id);
                            map.put(TAG_College_Desc, date);
                            map.put(TAG_College_Founded_Year, time);

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
                                .setBackgroundColor(R.color.colorPrimaryDark)
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

                params.put("reg_id", str_id); // replace as str_id

                System.out.println("reg_id" + str_id);

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }


}
