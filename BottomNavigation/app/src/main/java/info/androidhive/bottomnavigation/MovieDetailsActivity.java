package info.androidhive.bottomnavigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import info.androidhive.bottomnavigation.fragment.MovieShowTimeFragment;

public class MovieDetailsActivity extends AppCompatActivity {
    private static final String TAG = "MovieDetailsActivity";
    String moviename;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviedetails);
        Log.d(TAG, "onCreate: started.");

        getIncomingIntent();

        //Add Back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        Log.d(TAG, "getIncomingIntent:checking for incoming intents.");
        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("movie_name")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");
            String imageurl = getIntent().getStringExtra("image_url");
            moviename = getIntent().getStringExtra("movie_name");
            String movieadvice = getIntent().getStringExtra("movie_advice");
            String movielanguage = getIntent().getStringExtra("movie_language");
            String movieduration = getIntent().getStringExtra("movie_duration");
            String moviesynopsis = getIntent().getStringExtra("movie_synopsis");
            String moviedirector = getIntent().getStringExtra("movie_director");
            String moviecast = getIntent().getStringExtra("movie_cast");
            setDetails(imageurl, moviename, movieadvice, movielanguage, movieduration, moviesynopsis, moviedirector, moviecast);
        }
    }

    private void setDetails(String imageurl, String moviename, String movieadvice, String movielanguage, String movieduration, String moviesynopsis, String moviedirector, String moviecast){
        Log.d(TAG, "setDetails: setting to image and name to widgets.");

        TextView title = findViewById(R.id.movie_title);
        title.setText(moviename);


        TextView advice = findViewById(R.id.movie_advice);
        advice.setText(movieadvice);

        TextView language = findViewById(R.id.movie_language);
        language.setText(movielanguage);

        TextView duration = findViewById(R.id.movie_duration);
        duration.setText(movieduration);

        TextView synopsis = findViewById(R.id.movie_synopsis);
        moviecast = moviecast.replace("\n", " ");
        moviecast = moviecast.replace("  ", "");
        synopsis.append(": " + moviesynopsis + "\n" + "\n" + moviedirector + "\n"+  "\n" + " " + moviecast);

        //TextView director = findViewById(R.id.movie_director);
        //director.setText(moviedirector);

        //TextView cast = findViewById(R.id.movie_cast);
        //cast.setText(moviecast);

        ImageView imageView = findViewById(R.id.movie_image);


        Glide.with(this)
                .asBitmap()
                .load(imageurl)
                .into(imageView);

    }

    public void viewShowTimes(View v){
        Button btnShowtimes = findViewById(R.id.btnShowtimes);
        Fragment fragment;
        fragment = new MovieShowTimeFragment(moviename);
        btnShowtimes.setVisibility(View.INVISIBLE);
        String moviename = getIntent().getStringExtra("movie_name");
        Bundle bundle = new Bundle();
        bundle.putString("movie_name", moviename);
        fragment.setArguments(bundle);
        Log.d(TAG, "Calling loadFragment");
        loadFragment(fragment);
    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Log.d(TAG, "Entering new Fragment");
        transaction.replace(R.id.layout_container, fragment);
        transaction.commit();
        Log.d(TAG, "Entered new Fragment");
    }



}
