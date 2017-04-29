package banyan.com.winya1.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

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
            v = inflater.inflate(R.layout.list_item_search_results, null);

        TextView college_title = (TextView) v.findViewById(R.id.list_college_name);
        TextView college_since = (TextView) v.findViewById(R.id.list_since);
        TextView college_descrip = (TextView) v.findViewById(R.id.list_description);
        TextView college_location = (TextView) v.findViewById(R.id.list_location);
        ImageView college_image = (ImageView) v.findViewById(R.id.complaint_complete_img);
        ImageView country_img = (ImageView) v.findViewById(R.id.list_img_location_flag);


        HashMap<String, String> result = new HashMap<String, String>();
        result = data.get(position);

        try {
            college_title.setText(result.get(Activity_Search_Results.TAG_COLLEGE_NAME));
            college_since.setText(result.get(Activity_Search_Results.TAG_COLLEGE_FOUNDED_YEAR));
            college_descrip.setText(result.get(Activity_Search_Results.TAG_COLLEGE_DETAILS));
            college_location.setText(result.get(Activity_Search_Results.TAG_COUNTRY_NAME));

            String str_college_img = result.get(Activity_Search_Results.TAG_COLLEGE_PHOTO);

            if (!str_college_img.equals("")) {

                Glide.with(activity.getApplicationContext()).load(str_college_img)
                        .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(college_image);

            } else {

            }
            String str_country_img = result.get(Activity_Search_Results.TAG_COUNTRY_IMAGE);
            if (!str_country_img.equals("")) {

                Glide.with(activity.getApplicationContext()).load(str_country_img)
                        .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(country_img);

            } else {

            }

        } catch (Exception e) {

        }

        return v;

    }

}