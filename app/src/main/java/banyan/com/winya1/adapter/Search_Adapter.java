package banyan.com.winya1.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import banyan.com.winya1.Activity_Search;
import banyan.com.winya1.Activity_Search_Results;
import banyan.com.winya1.R;


public class Search_Adapter extends BaseAdapter {
    private Activity activity;
    private Context context;
    private LinearLayout singleMessageContainer;

    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;

    private String[] bgColors;

    public Search_Adapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
        bgColors = activity.getApplicationContext().getResources().getStringArray(R.array.movie_serial_bg);
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
            v = inflater.inflate(R.layout.list_item_search_results, null);

        TextView college_title = (TextView) v.findViewById(R.id.list_college_title);
        TextView college_description = (TextView) v.findViewById(R.id.list_college_description);
        TextView college_founded_year = (TextView) v.findViewById(R.id.list_college_founded_year);


        HashMap<String, String> result = new HashMap<String, String>();
        result = data.get(position);

        try {

            college_title.setText(result.get(Activity_Search_Results.TAG_College_Name).substring(0,1));

        }catch (Exception e) {

        }

        college_title.setText(result.get(Activity_Search_Results.TAG_College_Name));
        college_description.setText(result.get(Activity_Search_Results.TAG_College_Desc));
        college_founded_year.setText(result.get(Activity_Search_Results.TAG_College_Founded_Year));


        String color = bgColors[position % bgColors.length];
        college_title.setBackgroundColor(Color.parseColor(color));

        return v;

    }

}