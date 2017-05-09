package banyan.com.winya1;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
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


/**
 * Created by steve on 3/5/17.
 */

public class Activity_Timeline extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    ListView list_timeline;
    public static RequestQueue queue;
    private SwipeRefreshLayout swipeRefreshLayout;

    String str_country;

    public static final String TAG_COUNTRY="country_name";

    static ArrayList<HashMap<String, String>> timeline_list;
    HashMap<String, String> params = new HashMap<String, String>();
    TimeLine_Adapter adapter;
    String TAG = "add task";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);


        list_timeline=(ListView)findViewById(R.id.timeline_listview);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.timeline_swipe_refresh_layout);

        // Hashmap for ListView
        timeline_list = new ArrayList<HashMap<String, String>>();

        list_timeline.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                System.out.println("position" + position);


                str_country = timeline_list.get(position).get(TAG_COUNTRY);

                System.out.println("str_country" + str_country);




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
                                            GetCountry();

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
            GetCountry();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    public void  GetCountry() {

        String tag_json_obj = "json_obj_req";
        System.out.println("CAME 1");
        StringRequest request = new StringRequest(Request.Method.GET,
                AppConfig.url_country, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject obj = new JSONObject(response);

                    JSONArray arr;

                    arr = obj.getJSONArray("course");

                    System.out.println(arr);

                    for (int i = 0; arr.length() > i; i++) {
                        JSONObject obj1 = arr.getJSONObject(i);

                        String country1 = obj1.getString(TAG_COUNTRY);


                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_COUNTRY, country1);


                       timeline_list.add(map);

                        System.out.println("HASHMAP ARRAY" + timeline_list);


                        adapter = new TimeLine_Adapter(Activity_Timeline.this,
                                timeline_list);
                        list_timeline.setAdapter(adapter);


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


                params.put("str_country", str_country);

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }

}
