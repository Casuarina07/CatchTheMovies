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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.bottomnavigation.Movie;
import info.androidhive.bottomnavigation.MovieRedirect;
import info.androidhive.bottomnavigation.R;
import info.androidhive.bottomnavigation.app.MyApplication;
import info.androidhive.bottomnavigation.MovieRedirect;

public class MovieShowTimeFragment extends Fragment  {

    private static final String TAG = "MovieShowTimeFragment";
    //private static final String TAG = MovieFragment.class.getSimpleName();

    // url to fetch movie show time
    private static final String URL = "https://jsonstorage.net/api/items/6b7807fa-dbd7-4847-90ea-f55bb6204ec1";

    private RecyclerView recyclerView;
    private List<Movie> showtimelist;
    private StoreAdapter mAdapter;
    private TextView txtShowtime;
    public MovieShowTimeFragment() {
        // Required empty public constructor
    }

    public static MovieShowTimeFragment newInstance(String param1, String param2) {
        MovieShowTimeFragment fragment = new MovieShowTimeFragment();
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
        View view = inflater.inflate(R.layout.fragment_showtime, container, false);

        ProgressBar spinner;
        spinner = view.findViewById(R.id.progressBar1);
        recyclerView = view.findViewById(R.id.recycler_view);

        TextView status = (TextView) view.findViewById(R.id.click);
        showtimelist = new ArrayList<>();
        mAdapter = new StoreAdapter(getActivity(), showtimelist);
        txtShowtime = view.findViewById(R.id.txtShowtimes);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);


        fetchMovieList();

        return view;
    }


    /**
     * fetching movie list by making http call
     */
    private void fetchMovieList() {
        JsonArrayRequest request = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response == null) {
                            Toast.makeText(getActivity(), "Couldn't fetch the store items! Pleas try again.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        List<Movie> items = new Gson().fromJson(response.toString(), new TypeToken<List<Movie>>() {
                        }.getType());

                        showtimelist.clear();
                        showtimelist.addAll(items);

                        // refreshing recycler view
                        mAdapter.notifyDataSetChanged();
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

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    /**
     * RecyclerView adapter class to render items
     * This class can go into another separate class, but for simplicity
     */
    class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.MyViewHolder> {
        private Context context;
        private List<Movie> showtimelist;


        public class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView name;
            public Button time;
            public Image thumbnail;

            public MyViewHolder(View view) {
                super(view);
                name = view.findViewById(R.id.title);
                time = view.findViewById(R.id.time);
            }

        }


        public StoreAdapter(Context context, List<Movie> showtimelist) {
            this.context = context;
            this.showtimelist = showtimelist;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.store_showtime_row, parent, false);

            return new MyViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            final Movie movie = showtimelist.get(position);
            String movieTitleClicked = getArguments().getString("movie_name");
            txtShowtime.setText("Showtime for "+ movieTitleClicked);
            if(movie.getMovieName().equals(movieTitleClicked))
            {
                Log.d(TAG, "in the if statement " + movie.getShowtimelocation());

                //set text
                holder.name.setText(movie.getShowtimelocation());
                holder.time.setText(movie.getMovieName());

                //user clicks on a particular time
                holder.time.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "onClick: clicked on: " + movie.getShowtimelocation());

                        Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(context, MovieRedirect.class);
                        //intent.putExtra("image_url", movie.getImage());
                        //intent.putExtra("movie_name", movie.getTitle());
                        context.startActivity(intent);
                    }
                });
            }

        }

        @Override
        public int getItemCount() {
            return showtimelist.size();
        }
    }

}
