package info.androidhive.bottomnavigation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieShowTimesActivity extends AppCompatActivity {
    private static final String TAG = "MovieShowTimesActivity";

    //
    private Context mContext;
    private Activity mActivity;

    private CoordinatorLayout mCLayout;
    private TextView mTextView;
    private String mJSONString = "https://jsonstorage.net/api/items/e0e1035c-a474-4f85-a488-980a7c34ca0b";
    private Button btnSelectDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtimes);

        getIncomingIntent();

        //Add Back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.d(TAG, "getIncomingIntent:checking for incoming intents.");
        Log.d(TAG, "getIncomingIntent: found intent extras.");
        final String moviename = getIntent().getStringExtra("movie_name");
        Log.d(TAG, "movie_name show times for " + moviename + " is.... ");

        mContext = getApplicationContext();
        mActivity = MovieShowTimesActivity.this;
        //
        mCLayout = (CoordinatorLayout) findViewById(R.id.layout_showtimes);
        mTextView = (TextView)findViewById(R.id.text_view_result);
        mTextView.setText("");
        btnSelectDate = (Button)findViewById(R.id.btnSelectDate);

        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MovieShowTimesActivity.this, "Date is chosen", Toast.LENGTH_SHORT);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        int found = 0;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, mJSONString, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject movie = response.getJSONObject(i);

                                String movieName = movie.getString("movieName");
                                Log.d(TAG, "movie_name retrieved is " + movieName);
                                String showDateshref = movie.getString("showDates-href");
                                if(movieName.equals(moviename) && showDateshref.contains("tab_0"))
                                {
                                    String showDates = movie.getString("showDates");
                                    String showTimes = movie.getString("showTimes");
                                    btnSelectDate.setText(showDates);
                                    mTextView.append(movieName + " Today's showtime " + showTimes);
                                    Log.d(TAG, "Same Movie name");
                                    int found = 1;
                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar.make(mCLayout, "Error...", Snackbar.LENGTH_LONG).show();
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            //ends the activity
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }



    private void getIncomingIntent(){

    }
}
