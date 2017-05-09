package banyan.com.winya1.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import banyan.com.winya1.Activity_Timeline;
import banyan.com.winya1.R;


public class TimeLine_Adapter extends BaseAdapter {
    private Activity activity;
    private Context context;
    private LinearLayout singleMessageContainer;

    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;

    private String[] bgColors;

    public TimeLine_Adapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
//        bgColors = activity.getApplicationContext().getResources().getStringArray(R.array.movie_serial_bg);
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (convertView == null)
            v = inflater.inflate(R.layout.timeline_listview, null);

        TextView country = (TextView) v.findViewById(R.id.timeline_country);

        HashMap<String, String> result = new HashMap<String, String>();
        result = data.get(position);

        country.setText(result.get(Activity_Timeline.TAG_COUNTRY));

        return v;

    }

}