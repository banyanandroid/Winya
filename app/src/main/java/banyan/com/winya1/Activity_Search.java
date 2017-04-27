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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;


public class Activity_Search extends AppCompatActivity {


    FloatingActionButton fab_search;

    String[] Str_Country = {"Australia", "Newzealand"}, Str_Universities = {"Oxford", "Cambridge"}, Str_Courses = {"Aeronautical", "Marine", "Computer Science", "Civil"};

    /*String[] Str_Universities = {"Australia", "Newzealand"};

    String[] Str_Courses = {"Australia", "Newzealand"};
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(getString(R.string.expand));

        fab_search = (FloatingActionButton) findViewById(R.id.btn_fab_search);


        fab_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("Apply Now Clicked");

                Intent i = new Intent(getApplicationContext(), Activity_University_Description.class);
                startActivity(i);
                finish();


            }
        });


        final MultiAutoCompleteTextView Txt_Country = (MultiAutoCompleteTextView) findViewById(R.id.txt_apply_country);
       /* final AutocompleteAdapter adapter = new AutocompleteAdapter(this,android.R.layout.simple_dropdown_item_1line);
        //when autocomplete is clicked
        Txt_Country.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String countryName = adapter.getItem(position).getName();
                Txt_Country.setText(countryName);
            }
        });*/


        Txt_Country.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        MultiAutoCompleteTextView Txt_University = (MultiAutoCompleteTextView) findViewById(R.id.txt_apply_university);
        Txt_University.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        MultiAutoCompleteTextView Txt_Course = (MultiAutoCompleteTextView) findViewById(R.id.txt_apply_course);
        Txt_Course.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());


    /*    final AutoCompleteTextView countrySearch = (AutoCompleteTextView) findViewById(R.id.search);
        final AutocompleteAdapter adapter = new AutocompleteAdapter(this,android.R.layout.simple_dropdown_item_1line);
        countrySearch.setAdapter(adapter);

        //when autocomplete is clicked
        countrySearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String countryName = adapter.getItem(position).getName();
                countrySearch.setText(countryName);
            }
        });
    */



       /* ArrayAdapter<String> adapter_country = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, Str_Country);
        Txt_Country.setThreshold(1);
        Txt_Country.setAdapter(adapter_country);
*/

        ArrayAdapter<String> adapter_university = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, Str_Universities);
        Txt_University.setThreshold(1);
        Txt_University.setAdapter(adapter_university);


        ArrayAdapter<String> adapter_courses = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, Str_Courses);
        Txt_Course.setThreshold(1);
        Txt_Course.setAdapter(adapter_courses);


    }


}
