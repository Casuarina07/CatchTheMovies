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

import java.util.ArrayList;
import java.util.List;

import info.androidhive.bottomnavigation.MainActivity;
import info.androidhive.bottomnavigation.Movie;
import info.androidhive.bottomnavigation.R;
import info.androidhive.bottomnavigation.app.MyApplication;

public class SearchFragment extends Fragment {

    private static final String TAG = "MovieFragment";
    //private static final String TAG = MovieFragment.class.getSimpleName();

    // url to fetch shopping items
    private static final String URL = "https://jsonstorage.net/api/items/5aefe02e-e550-4e88-a44e-166c0bdb64a3";

    private List<Movie> itemsList;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> list;
    private List<Movie> items;
    private List<Movie> movieList;
    private ListView listViewMovie;
    private boolean clickedSelectMovie = false;
    private MovieFragment movieFragment = new MovieFragment();
    int clickedMovie = -1;
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
      //  MovieFragment movieFragment = new MovieFragment();
        itemsList = new ArrayList<>();
        items = new ArrayList<>();
        list = new ArrayList<String>();
        fetchStoreItems();

        searchViewMovie.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if(clickedSelectMovie == false)
                {
                    searchViewDate.setVisibility(View.INVISIBLE);
                    listViewMovie.setVisibility(View.VISIBLE);
                    searchButton.setVisibility(View.INVISIBLE);
                    clickedSelectMovie = true;

                }
                else
                {
                    searchViewDate.setVisibility(View.VISIBLE);
                    listViewMovie.setVisibility(View.INVISIBLE);
                    searchButton.setVisibility(View.VISIBLE);
                    clickedSelectMovie = false;
                }

            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    MainActivity mainActivity = new MainActivity();
                    loadFragment(new MovieFragment(clickedMovie));
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
                Toast.makeText(getActivity(), "Number of movie: " + clickedMovie, Toast.LENGTH_SHORT).show();
                listViewMovie.setVisibility(View.INVISIBLE);
                searchViewDate.setVisibility(View.VISIBLE);
                searchButton.setVisibility(View.VISIBLE);
            }
        });

        return view;
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
