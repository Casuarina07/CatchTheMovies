package info.androidhive.bottomnavigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MovieDetailsActivity extends AppCompatActivity {
    private static final String TAG = "MovieDetailsActivity";

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
            String moviename = getIntent().getStringExtra("movie_name");
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

        TextView image = findViewById(R.id.movie_title);
        image.setText(moviename);

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
        Log.d(TAG, "viewShowTimes triggered");
        Intent intent = new Intent(MovieDetailsActivity.this, MovieShowTimesActivity.class);
        String moviename = getIntent().getStringExtra("movie_name");
        intent.putExtra("movie_name" , moviename);
        Log.d(TAG, "moviename that was chosen was " + moviename);
        startActivity(intent);
        Log.d(TAG, "new intent started");
    }

}
