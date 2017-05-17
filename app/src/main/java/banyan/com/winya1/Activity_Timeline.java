package banyan.com.winya1;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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

import banyan.com.winya1.adapter.TimeLine_Adapter;
import banyan.com.winya1.global.AppConfig;
import banyan.com.winya1.global.SessionManager;


public class Activity_Timeline extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    ListView list_timeline;
    public static RequestQueue queue;
    private SwipeRefreshLayout swipeRefreshLayout;

    String str_country , str_title , str_expected_date , str_completed_date , str_description;

    public static final String TAG_TITLE="timeline_name";
    public static final String TAG_EXPECTED_DATE="expected_date";
    public static final String TAG_COMPLETED_DATE="completed_date";
    public static final String TAG_DESCRIPTION="timline_description";

    static ArrayList<HashMap<String, String>> timeline_list;
    HashMap<String, String> params = new HashMap<String, String>();
    TimeLine_Adapter adapter;
    String TAG = "add task";

    // Session Manager Class
    SessionManager session;

    String str_name;
    public static String str_id;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        list_timeline=(ListView)findViewById(R.id.timeline_listview);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.timeline_swipe_refresh_layout);

        session = new SessionManager(getApplicationContext());

        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // name
        str_name = user.get(SessionManager.KEY_USER);
        str_id = user.get(SessionManager.KEY_USER_ID);

        // Hashmap for ListView
        timeline_list = new ArrayList<HashMap<String, String>>();

        list_timeline.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                System.out.println("position" + position);


                str_title = timeline_list.get(position).get(TAG_TITLE);
                str_expected_date = timeline_list.get(position).get(TAG_EXPECTED_DATE);
                str_completed_date = timeline_list.get(position).get(TAG_COMPLETED_DATE);
                str_description = timeline_list.get(position).get(TAG_DESCRIPTION);

                System.out.println("str_title  -- " + str_country);
                System.out.println("str_expected_date  -- " + str_expected_date);
                System.out.println("str_completed_date  -- " + str_completed_date);
                System.out.println("str_description  -- " + str_description);




            }
        });

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        try {
                                            timeline_list.clear();
                                            queue = Volley.newRequestQueue(Activity_Timeline.this);
                                            GetTimeline();

                                        } catch (Exception e) {
                                            // TODO: handle exception
                                        }
                                    }
                                }
        );


    }

    @Override
    public void onRefresh() {

        try {
            timeline_list.clear();
            queue = Volley.newRequestQueue(Activity_Timeline.this);
            GetTimeline();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    public void  GetTimeline() {

        String tag_json_obj = "json_obj_req";
        System.out.println("CAME 1");
        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_timeline, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject obj = new JSONObject(response);


                    int success = obj.getInt("success");

                    if (success == 1) {

                        JSONArray arr;

                        arr = obj.getJSONArray("timeline");

                        for (int i = 0; arr.length() > i; i++) {

                            JSONObject obj1 = arr.getJSONObject(i);
                            System.out.println(arr);

                            String Title_1 = obj1.getString(TAG_TITLE);
                            String Exp_date1 = obj1.getString(TAG_EXPECTED_DATE);
                            String Comp_date1 = obj1.getString(TAG_COMPLETED_DATE);
                            String Desc_1 = obj1.getString(TAG_DESCRIPTION);


                            HashMap<String, String> map = new HashMap<String, String>();

                            // adding each child node to HashMap key => value
                            map.put(TAG_TITLE, Title_1);
                            map.put(TAG_EXPECTED_DATE, Exp_date1);
                            map.put(TAG_COMPLETED_DATE, Comp_date1);
                            map.put(TAG_DESCRIPTION, Desc_1);


                            timeline_list.add(map);

                            System.out.println("HASHMAP ARRAY" + timeline_list);


                            adapter = new TimeLine_Adapter(Activity_Timeline.this,
                                    timeline_list);
                            list_timeline.setAdapter(adapter);


                        }

                        Alerter.create(Activity_Timeline.this)
                                .setTitle("WINYA")
                                .setText("Applied Sucessfully :)")
                                .setBackgroundColor(R.color.Alert_Success)
                                .show();


                    } else {

                        Alerter.create(Activity_Timeline.this)
                                .setTitle("WINYA")
                                .setText("Oops..! Process Failed :(")
                                .setBackgroundColor(R.color.Alert_Fail)
                                .show();
                    }


                    swipeRefreshLayout.setRefreshing(false);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                swipeRefreshLayout.setRefreshing(false);
                Alerter.create(Activity_Timeline.this)
                        .setTitle("Winya")
                        .setText(error.getMessage())
                        .setBackgroundColor(R.color.Alert_Warning)
                        .show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                params.put("regs_id", str_id);

                System.out.println("USER ID ----" + str_id);
                System.out.println("USER ID ----" + str_id);
                System.out.println("USER ID ----" + str_id);
                System.out.println("USER ID ----" + str_id);

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }

}
