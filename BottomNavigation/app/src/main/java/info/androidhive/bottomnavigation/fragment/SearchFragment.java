package info.androidhive.bottomnavigation.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import info.androidhive.bottomnavigation.MainActivity;
import info.androidhive.bottomnavigation.Movie;
import info.androidhive.bottomnavigation.R;
import info.androidhive.bottomnavigation.app.MyApplication;

public class SearchFragment extends Fragment {

    private static final String TAG = "MovieFragment";
    //private static final String TAG = MovieFragment.class.getSimpleName();

    // url to fetch shopping items
   // private static MovieFragment movieFragment = new MovieFragment();


    private List<Movie> itemsList;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapterDate;
    private ArrayList<String> list;
    private ArrayList<String> listDate;
    private List<Movie> items;
    private List<Movie> movieList;
    private ListView listViewMovie;
    private ListView listViewDate;
    private boolean clickedSelectMovie = false;
    private boolean clickedSelectDate = false;

    private String movieToGo = "";
    private boolean choosenMovie = false;
    private boolean choosenDate = false;

    private static MovieFragment movieFragment = new MovieFragment();

    private static final String URL = movieFragment.getURL();
    // private static final String URL = "https://jsonstorage.net/api/items/a2c5782f-dd3d-4c2c-aa17-250a07ab8cbb";
    int clickedMovie = -1;
    int clickedDate = -1;
    public SearchFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      //  return inflater.inflate(R.layout.fragment_search, container, false);
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        final Button searchViewMovie = view.findViewById(R.id.searchViewMovie);
        final Button searchViewDate = view.findViewById(R.id.searchViewDate);
        final Button searchButton = view.findViewById(R.id.Search);
        listViewMovie = view.findViewById(R.id.listViewMovie);
        listViewDate = view.findViewById(R.id.listViewDate);
      //  MovieFragment movieFragment = new MovieFragment();
        MovieShowTimeFragment movieShowTimeFragment = new MovieShowTimeFragment();
        itemsList = new ArrayList<>();
        items = new ArrayList<>();
        list = new ArrayList<String>();
        listDate = new ArrayList<String>();
        getCurrentDates();
        fetchStoreItems();




        searchViewMovie.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if(clickedSelectMovie == false)
                {
                    listViewDate.setVisibility(View.INVISIBLE);
                    searchViewDate.setVisibility(View.INVISIBLE);
                    listViewMovie.setVisibility(View.VISIBLE);
                    searchButton.setVisibility(View.INVISIBLE);
                    clickedSelectMovie = true;

                }
                else
                {
                    listViewDate.setVisibility(View.INVISIBLE);
                    searchViewDate.setVisibility(View.VISIBLE);
                    listViewMovie.setVisibility(View.INVISIBLE);
                    searchButton.setVisibility(View.VISIBLE);
                    clickedSelectMovie = false;
                }

            }
        });



        searchViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(clickedSelectDate == false)
                {
                    listViewMovie.setVisibility(View.INVISIBLE);
                    listViewDate.setVisibility(View.VISIBLE);
                    searchButton.setVisibility(View.INVISIBLE);
                    clickedSelectDate = true;
                }
                else
                {
                    listViewMovie.setVisibility(View.INVISIBLE);
                    listViewDate.setVisibility(View.INVISIBLE);
                    searchButton.setVisibility(View.VISIBLE);
                    clickedSelectDate = false;
                }

            }
        });


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(choosenMovie == true && choosenDate == false)
                    {
                        MainActivity mainActivity = new MainActivity();
                        loadFragment(new MovieFragment(clickedMovie));
                        Log.d(TAG, "Go to moviefragment");
                    }
                    else if (choosenMovie == true && choosenDate == true)
                    {
                        MainActivity mainActivity = new MainActivity();
                        loadFragment(new MovieShowTimeFragment(movieToGo, clickedDate));
                        Log.d(TAG, "Go to movieshowtime");
                    }
            }
        });
/*
        searchViewMovie.setOnQueryTextListener(new Button.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewDate.setVisibility(View.INVISIBLE);
                listViewMovie.setVisibility(View.VISIBLE);
                searchButton.setVisibility(View.INVISIBLE);
                    if (list.contains(query)) {
                        adapter.getFilter().filter(query);
                    } else {
                        Toast.makeText(getActivity(), "No Match found", Toast.LENGTH_LONG).show();
                    }

                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                //    adapter.getFilter().filter(newText);
                searchViewDate.setVisibility(View.INVISIBLE);
                searchButton.setVisibility(View.INVISIBLE);
                listViewMovie.setVisibility(View.VISIBLE);
                return false;
            }
        });

        searchViewMovie.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                listViewMovie.setVisibility(View.INVISIBLE);
                searchViewDate.setVisibility(View.VISIBLE);
                searchButton.setVisibility(View.VISIBLE);
                return false;
            }
        });

 */
        listViewMovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                searchViewMovie.setText(list.get(i));
                clickedMovie = i;
                Movie movie = itemsList.get(i);
                movieToGo = movie.getTitle();
                Log.d(TAG, "Movie to go: " + movieToGo);
                choosenMovie = true;
                Toast.makeText(getActivity(), "Number of movie: " + clickedMovie, Toast.LENGTH_SHORT).show();
                listViewMovie.setVisibility(View.INVISIBLE);
                searchViewDate.setVisibility(View.VISIBLE);
                searchButton.setVisibility(View.VISIBLE);
            }
        });

        listViewDate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                searchViewDate.setText(listDate.get(position));
                clickedDate = position;
                Log.d(TAG, "Get clickedDate position " + clickedDate);
                choosenDate = true;
                listViewDate.setVisibility(View.INVISIBLE);
                searchButton.setVisibility(View.VISIBLE);
            }
        });


        return view;
    }

    private static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    private void getCurrentDates()
    {
        Date currentDate = new Date();
        String date;
        SimpleDateFormat objSDF = new SimpleDateFormat("EE dd MMM");
        for (int i =0; i<5; i++)
        {
            date = objSDF.format(currentDate);
            if(i ==0)
            {
                date = "Today " + date;
            }
            listDate.add(date);
            currentDate = SearchFragment.addDays(currentDate, 1);
            Log.d(TAG, "Dates: " + date);
        }
        adapterDate = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, listDate);
        listViewDate.setAdapter(adapterDate);
    }

    private void fetchStoreItems() {
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
                            Movie movie = itemsList.get(i);
                            list.add(movie.getTitle());

                        }
                        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, list);

                        listViewMovie.setAdapter(adapter);
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
    }




    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Log.d(TAG, "Entering new Fragment");
        transaction.replace(R.id.frame_container, fragment);

        transaction.commit();
        Log.d(TAG, "Entered new Fragment");
    }
}
