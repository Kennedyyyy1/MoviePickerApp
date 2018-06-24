package com.example.ross.moviepickerapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DisplayMovie extends AppCompatActivity {

    private Context context = this;
    private android.support.v7.widget.ShareActionProvider mShareActionProvider;

    ImageView posterImageIV;
    TextView idTV;
    TextView titleTV;
    TextView posterPathTV;
    TextView ratingTV;
    TextView genresTV;
    TextView overviewTV;
    TextView releaseDateTV;
    TextView runtimeTV;
    TextView budgetTV;
    TextView revenueTV;
    Button moreMovieBtn;
    Button prevMovieBtn;
    Button nextMovieBtn;
    Button shareMovieButton;

    int movieIndex = 0;
    int movieIndexMax = 0;

    ArrayList<Movie> moviesList = new ArrayList<>();

    private static ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movie);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        Intent intent = getIntent();
        int movieCount = intent.getIntExtra("movieCount",0);        //returns 1 if single Movie object, 2 if ArrayList<Movie> object and default 0 if error.
        if (movieCount == 1) {
            Movie movie = intent.getParcelableExtra("movie");
            displaySingleMovie(movie);
        } else if (movieCount == 2){
            ArrayList<Movie> movieList = intent.getParcelableArrayListExtra("movieList");
            movieIndex = 0;
            movieIndexMax = movieList.size();
            this.moviesList = movieList;
            displayMovieList();
        } else if (movieCount == 3){
            ArrayList<Movie> movieList = intent.getParcelableArrayListExtra("movieList");
            movieIndex = 0;
            movieIndexMax = movieList.size();
            this.moviesList = movieList;
            displayMovieList();
        } else {
            Log.e("Movie count error", "Got result 0 Error");
        }

        nextMovieBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context, "Display Next Movie", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        prevMovieBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context, "Display Previous Movie", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        moreMovieBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context, "More Information on this movie", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.menu_share, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (android.support.v7.widget.ShareActionProvider) MenuItemCompat.getActionProvider(item);

        updateShare();

        // Return true to display menu
        return true;
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    // Takes a Movie object and displays it to user
    public void displaySingleMovie(final Movie movie) {

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait ...");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();

        posterImageIV = (ImageView)findViewById(R.id.IV_PosterImage);
        idTV = (TextView)findViewById(R.id.TV_ID);
        titleTV = (TextView)findViewById(R.id.TV_MovieTitle);
        posterPathTV = (TextView)findViewById(R.id.TV_PosterPath);
        ratingTV = (TextView)findViewById(R.id.TV_Rating);
        genresTV = (TextView)findViewById(R.id.TV_Genres);
        overviewTV = (TextView)findViewById(R.id.TV_Overview);
        releaseDateTV = (TextView)findViewById(R.id.TV_ReleaseDate);
        runtimeTV = (TextView)findViewById(R.id.TV_Runtime);
        budgetTV = (TextView)findViewById(R.id.TV_Budget);
        revenueTV = (TextView)findViewById(R.id.TV_Revenue);
        moreMovieBtn = (Button)findViewById(R.id.Btn_More);
        prevMovieBtn = (Button)findViewById(R.id.Btn_Prev);
        nextMovieBtn = (Button)findViewById(R.id.Btn_Next);
        shareMovieButton = (Button)findViewById(R.id.Btn_ShareMovie);

        idTV.setVisibility(View.GONE);
        posterPathTV.setVisibility(View.GONE);
        moreMovieBtn.setVisibility(View.GONE);
        prevMovieBtn.setVisibility(View.GONE);
        nextMovieBtn.setVisibility(View.GONE);

        // Movie image
        final Bitmap[] bmp = {null};

        // Thread to download the movie poster
        new Thread(new Runnable() {
            public void run() {
                URL url = null;
                try {
                    url = new URL("https://image.tmdb.org/t/p/w500"+movie.getPosterPath());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    bmp[0] = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Setting the Movie information fields from the Movie object
        idTV.setText(Integer.toString(movie.getId()));
        titleTV.setText(movie.getTitle());
        posterPathTV.setText(movie.getPosterPath());
        ratingTV.setText("Rating of : "+Double.toString(movie.getRating())+" with "+Integer.toString(movie.getVoteCount())+" votes");
        if (movie.getGenres().size() >= 1) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i=0; i<movie.getGenres().size(); i++) {            // For loop because movies have different numbers of genres
                stringBuilder.append(movie.getGenres().get(i)+", ");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 2);
            String finalString = stringBuilder.toString();
            genresTV.setText("Genres : "+finalString);
        } else {
            genresTV.setText("No listed Genres for this movie.");
        }
        overviewTV.setText(movie.getOverview());
        releaseDateTV.setText("Released on : "+movie.getReleaseDate());
        runtimeTV.setText("Runtime : "+Integer.toString(movie.getRuntime())+" Minutes");
        budgetTV.setText("Budget of : $"+Integer.toString(movie.getBudget()));
        revenueTV.setText("Made : $"+Integer.toString(movie.getRevenue()));
        while (bmp[0] == null) {
            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        posterImageIV.setImageBitmap(bmp[0]);
        updateShare();
        dialog.hide();
        dialog.dismiss();
    }

    public void displayMovieList() {

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait ...");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();

        posterImageIV = (ImageView)findViewById(R.id.IV_PosterImage);
        idTV = (TextView)findViewById(R.id.TV_ID);
        titleTV = (TextView)findViewById(R.id.TV_MovieTitle);
        posterPathTV = (TextView)findViewById(R.id.TV_PosterPath);
        ratingTV = (TextView)findViewById(R.id.TV_Rating);
        genresTV = (TextView)findViewById(R.id.TV_Genres);
        overviewTV = (TextView)findViewById(R.id.TV_Overview);
        releaseDateTV = (TextView)findViewById(R.id.TV_ReleaseDate);
        runtimeTV = (TextView)findViewById(R.id.TV_Runtime);
        budgetTV = (TextView)findViewById(R.id.TV_Budget);
        revenueTV = (TextView)findViewById(R.id.TV_Revenue);
        moreMovieBtn = (Button)findViewById(R.id.Btn_More);
        prevMovieBtn = (Button)findViewById(R.id.Btn_Prev);
        nextMovieBtn = (Button)findViewById(R.id.Btn_Next);
        shareMovieButton = (Button)findViewById(R.id.Btn_ShareMovie);

        idTV.setVisibility(View.GONE);
        posterPathTV.setVisibility(View.GONE);
        releaseDateTV.setVisibility(View.GONE);
        runtimeTV.setVisibility(View.GONE);
        budgetTV.setVisibility(View.GONE);
        revenueTV.setVisibility(View.GONE);
        shareMovieButton.setVisibility(View.GONE);

        if (movieIndex < movieIndexMax) {

            final Bitmap[] bmp = {null};

            new Thread(new Runnable() {
                public void run() {
                    URL url = null;
                    try {
                        url = new URL("https://image.tmdb.org/t/p/w500"+moviesList.get(movieIndex).getPosterPath());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    try {
                        bmp[0] = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();


            idTV.setText(Integer.toString(moviesList.get(movieIndex).getId()));
            titleTV.setText(moviesList.get(movieIndex).getTitle());
            posterPathTV.setText(moviesList.get(movieIndex).getPosterPath());
            ratingTV.setText("Rating of : "+Double.toString(moviesList.get(movieIndex).getRating()));
            if (moviesList.get(movieIndex).getGenres().size() >= 1) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i=0; i<moviesList.get(movieIndex).getGenres().size(); i++) {
                    stringBuilder.append(moviesList.get(movieIndex).getGenres().get(i)+", ");
                }
                stringBuilder.deleteCharAt(stringBuilder.length() - 2);
                String finalString = stringBuilder.toString();
                genresTV.setText("Genres : "+finalString);
            } else {
                genresTV.setText("No listed Genres for this movie.");
            }
            overviewTV.setText(moviesList.get(movieIndex).getOverview());
            while (bmp[0] == null) {
                try {
                    Thread.sleep(80);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            posterImageIV.setImageBitmap(bmp[0]);
            updateShare();
            dialog.hide();
            dialog.dismiss();
        } else {
            Log.e("DisplayMovieList queue", "movie list consists of at most 20 movies and all have been shown or Error");
            Toast.makeText(this, "End of Movie queue", Toast.LENGTH_SHORT).show();
            dialog.hide();
            dialog.dismiss();
        }
    }

    public void onNextBtnClick (View view) {
        if (movieIndex < movieIndexMax-1) {
            movieIndex++;
            displayMovieList();
        } else {
            Toast.makeText(this, "End of Movie queue", Toast.LENGTH_SHORT).show();
        }
    }

    public void onPrevBtnClick (View view) {
        if(movieIndex > 0) {
            movieIndex--;
            displayMovieList();
        } else {
            Toast.makeText(this, "Already at beginning of Movie queue", Toast.LENGTH_SHORT).show();
        }
    }

        public void onMoreBtnClick(View view) {
            String id = idTV.getText().toString();
            System.out.print(Integer.parseInt(id));
            MovieDatabaseController.getMovieByID( this, Integer.parseInt(id));
        }

    public void onShareBtnClick(View view) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this Movie");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "https://www.themoviedb.org/movie/"+idTV.getText());
        startActivity(shareIntent);
    }

    public void updateShare() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this Movie");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "https://www.themoviedb.org/movie/"+idTV.getText());

        setShareIntent(shareIntent);
    }

}
