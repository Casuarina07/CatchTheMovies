package info.androidhive.bottomnavigation.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import info.androidhive.bottomnavigation.Movie;
import info.androidhive.bottomnavigation.MovieRedirect;
import info.androidhive.bottomnavigation.R;
import info.androidhive.bottomnavigation.app.MyApplication;
import info.androidhive.bottomnavigation.MovieRedirect;

public class MovieShowTimeFragment extends Fragment  {

    private static final String TAG = "MovieShowTimeFragment";
    //private static final String TAG = MovieFragment.class.getSimpleName();

    // url to fetch movie show time
    private static final String URL = "https://jsonstorage.net/api/items/2bf00d01-c3b8-44b4-af4c-e1ccfa94fc18";

    private RecyclerView recyclerView;
    private List<Movie> showtimelist;
    private String moviename;
    private TextView txtShowtime;

    private List<Movie> items;
    private List<Movie> movieList;
    private List<Movie> itemsList;
    private ArrayAdapter<String> adapter1;
    private ArrayAdapter<String> adapter2;
    private ArrayAdapter<String> adapter3;
    private ArrayAdapter<String> adapter4;
    private ArrayAdapter<String> adapter5;

    private ListView listViewShowtime;
    private JSONObject movie;
    private ArrayList<String> listDay1;
    private ArrayList<String> listDay2;
    private ArrayList<String> listDay3;
    private ArrayList<String> listDay4;
    private ArrayList<String> listDay5;

    private ArrayList<String> arrayOfDays;

    private String movieName = null;
    private String showDateshref = null;
    public MovieShowTimeFragment() {
        // Required empty public constructor
    }

    public static MovieShowTimeFragment newInstance(String param1, String param2)  {
        MovieShowTimeFragment fragment = new MovieShowTimeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MovieShowTimeFragment(String moviename)
    {
        this.moviename = moviename;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_showtime, container, false);
        itemsList = new ArrayList<>();
        items = new ArrayList<>();
        listViewShowtime = view.findViewById(R.id.showtimelist);
        listViewShowtime.setVisibility(View.INVISIBLE);
        final Button day1 = view.findViewById(R.id.day1);
        final Button day2 = view.findViewById(R.id.day2);
        final Button day3 = view.findViewById(R.id.day3);
        final Button day4 = view.findViewById(R.id.day4);
        final Button day5 = view.findViewById(R.id.day5);
        listDay1 = new ArrayList<String>();
        listDay2 = new ArrayList<String>();
        listDay3 = new ArrayList<String>();
        listDay4 = new ArrayList<String>();
        listDay5 = new ArrayList<String>();

        JsonArrayRequest request = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response == null) {
                            Toast.makeText(getActivity(), "Couldn't fetch the store items! Pleas try again.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        items = new Gson().fromJson(response.toString(), new TypeToken<List<Movie>>() {
                        }.getType());

                        itemsList.clear();
                        itemsList.addAll(items);



                        for(int i=0; i<itemsList.size(); i++)
                        {
                            try {
                                movie = response.getJSONObject(i);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                movieName = movie.getString("title");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                showDateshref = movie.getString("showDates-href");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if(movieName.equals(moviename))
                            {
                                if(showDateshref.contains("tab_0"))
                                {

                                    try {
                                        Log.d(TAG, "this movie_name is " + moviename);

                                        String showDate = movie.getString("showDates");
                                        Log.d(TAG, "this movie_date is " + showDate);
                                        day1.setText(showDate);

                                        String showTime = movie.getString("showtimelocation");
                                        showTime = showTime.replace("\n", " ");
                                        showTime = showTime.replace("                                        ", "    ");
                                        showTime = showTime.replace("    ", "\n");
                                        listDay1.add(showTime);

                                        adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, listDay1);
                                        // showTime = showTime.replace("   ")
                                        //String[] showlocation = showTime.split("   ");
                                        Log.d(TAG, "movie_showtime is " + showTime);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                if(showDateshref.contains("tab_1"))
                                {
                                    try {
                                        Log.d(TAG, "this movie_name is " + moviename);

                                        String showDate = movie.getString("showDates");
                                        Log.d(TAG, "this movie_date is " + showDate);
                                        day2.setText(showDate);
                                        String showTime = movie.getString("showtimelocation");
                                        showTime = showTime.replace("\n", " ");
                                        showTime = showTime.replace("                                        ", "    ");
                                        showTime = showTime.replace("    ", "\n");
                                        listDay2.add(showTime);
                                        adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, listDay2);
                                        // showTime = showTime.replace("   ")
                                        //String[] showlocation = showTime.split("   ");
                                        Log.d(TAG, "movie_showtime is " + showTime);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if(showDateshref.contains("tab_2"))
                                {
                                    try {
                                        Log.d(TAG, "this movie_name is " + moviename);

                                        String showDate = movie.getString("showDates");
                                        Log.d(TAG, "this movie_date is " + showDate);
                                        day3.setText(showDate);

                                        String showTime = movie.getString("showtimelocation");
                                        showTime = showTime.replace("\n", " ");
                                        showTime = showTime.replace("                                        ", "    ");
                                        showTime = showTime.replace("    ", "\n");
                                        listDay3.add(showTime);
                                        // showTime = showTime.replace("   ")
                                        //String[] showlocation = showTime.split("   ");
                                        adapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, listDay3);
                                        Log.d(TAG, "movie_showtime is " + showTime);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                if(showDateshref.contains("tab_3"))
                                {
                                    try {
                                        Log.d(TAG, "this movie_name is " + moviename);

                                        String showDate = movie.getString("showDates");
                                        Log.d(TAG, "this movie_date is " + showDate);
                                        day4.setText(showDate);
                                        String showTime = movie.getString("showtimelocation");
                                        showTime = showTime.replace("\n", " ");
                                        showTime = showTime.replace("                                        ", "    ");
                                        showTime = showTime.replace("    ", "\n");
                                        listDay4.add(showTime);
                                        adapter4 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, listDay4);
                                        // showTime = showTime.replace("   ")
                                        //String[] showlocation = showTime.split("   ");
                                        Log.d(TAG, "movie_showtime is " + showTime);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                if(showDateshref.contains("tab_4"))
                                {
                                    try {
                                        Log.d(TAG, "this movie_name is " + moviename);

                                        String showDate = movie.getString("showDates");
                                        Log.d(TAG, "this movie_date is " + showDate);
                                        day5.setText(showDate);

                                        String showTime = movie.getString("showtimelocation");
                                        showTime = showTime.replace("\n", " ");
                                        showTime = showTime.replace("                                        ", "    ");
                                        showTime = showTime.replace("    ", "\n");
                                        listDay5.add(showTime);
                                        adapter5 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, listDay5);
                                        // showTime = showTime.replace("   ")
                                        //String[] showlocation = showTime.split("   ");
                                        Log.d(TAG, "movie_showtime is " + showTime);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                Log.d(TAG, "This is array of dates: " + arrayOfDays);
                            }
                            Log.d(TAG,"This is the listDay1:"+ listDay1.toString());



                        }
                        // adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, list);
                        //listViewMovie.setAdapter(adapter);
                        Toast.makeText(getActivity(),"This is num of items: " +itemsList.size(), Toast.LENGTH_SHORT).show();
                        // refreshing recycler view
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error in getting json
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        MyApplication.getInstance().addToRequestQueue(request);


        day1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewShowtime.setAdapter(adapter1);
                listViewShowtime.setVisibility(View.VISIBLE);
            }
        });

        day2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewShowtime.setAdapter(adapter2);
                listViewShowtime.setVisibility(View.VISIBLE);
            }
        });

        day3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewShowtime.setAdapter(adapter3);
                listViewShowtime.setVisibility(View.VISIBLE);
            }
        });

        day4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewShowtime.setAdapter(adapter4);
                listViewShowtime.setVisibility(View.VISIBLE);
            }
        });

        day5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewShowtime.setAdapter(adapter5);
                listViewShowtime.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }


    /*
    public void showShowtimeForDay(View view)
    {
        Button day = (Button) view;

        if(day.getTag().toString() == "day2")
        {
            listViewShowtime.setAdapter(adapter2);
        }
        else if(day.getTag().toString() == "day3")
        {
            listViewShowtime.setAdapter(adapter3);
        }
        else if(day.getTag().toString() == "day4")
        {
            listViewShowtime.setAdapter(adapter4);
        }
        else if(day.getTag().toString() == "day5")
        {
            listViewShowtime.setAdapter(adapter5);
        }
        listViewShowtime.setVisibility(View.VISIBLE);

    }

    public ArrayAdapter<String> getAdapter1() {
        return adapter1;
    }

    public ArrayAdapter<String> getAdapter2() {
        return adapter2;
    }

    public ArrayAdapter<String> getAdapter3() {
        return adapter3;
    }

    public ArrayAdapter<String> getAdapter4() {
        return adapter4;
    }

    public ArrayAdapter<String> getAdapter5() {
        return adapter5;
    }

    public ArrayList<String> getListDay1() {
        return listDay1;
    }

    public ArrayList<String> getListDay2() {
        return listDay2;
    }

    public ArrayList<String> getListDay3() {
        return listDay3;
    }

    public ArrayList<String> getListDay4() {
        return listDay4;
    }

    public ArrayList<String> getListDay5() {
        return listDay5;
    }

 */
}
