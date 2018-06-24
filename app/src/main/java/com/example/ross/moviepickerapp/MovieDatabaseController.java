package com.example.ross.moviepickerapp;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class MovieDatabaseController {

    private static final String APIKEY = "e6a93fbec084934ee2c815931d53c2c7";

    private static ProgressDialog dialog;

    public void test(Context context){
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String URL = "https://api.themoviedb.org/3/movie/76341?api_key=e6a93fbec084934ee2c815931d53c2c7";

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){
                Log.d("Rest Response", response.toString());

                int id = 0;
                String title = "";
                String posterPath = "";
                double rating = 0;
                ArrayList<String> genres = new ArrayList<>();
                String overview = "";
                int runtime = 0;
                int budget =0;
                String releaseDate ="";
                int revenue = 0;
                int voteCount = 0;

                try {
                    id = response.getInt("id");
                    title = response.getString("original_title");
                    posterPath = response.getString("poster_path");
                    rating = response.getDouble("vote_average");
                    Log.d("response genre", response.get("genres").toString());
                    JSONArray jsonArray = response.getJSONArray("genres");
                    for (int i=0; i<jsonArray.length(); i++) {
                        genres.add(jsonArray.getJSONObject(i).getString("name"));
                    }
                    overview = response.getString("overview");
                    runtime = response.getInt("runtime");
                    budget = response.getInt("budget");
                    releaseDate = response.getString("release_date");
                    revenue = response.getInt("revenue");
                    voteCount = response.getInt("vote_count");
                } catch(JSONException e) {
                    Log.e("JSONException", e.toString());
                }

                System.out.println("Id = "+id);
                System.out.println("Title = "+title);
                System.out.println("Poster Path = "+posterPath);
                System.out.println("Rating = "+rating);
                System.out.print("Genres = ");
                for (int i=0; i<genres.size(); i++) {
                    System.out.print(genres.get(i)+" ");
                }
                System.out.println("\nOverview = "+overview);
                System.out.println("runtime = "+runtime+" minutes");
                System.out.println("Budget = "+budget);
                System.out.println("Release Date = "+releaseDate);
                System.out.println("Revenue = "+revenue);
                System.out.println("Number of votes = "+voteCount);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Rest Response ERROR", error.toString());
            }
        });

        requestQueue.add(objectRequest);
    }

    public static void randomMovie(final Context context){

        dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait ...");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();

        final int randomPage = new Random().nextInt(1000) + 1; // [0, 999] + 1 => [1, 1000]
        final int randomMovie = new Random().nextInt(20);   // [0,19]

        String URL = "https://api.themoviedb.org/3/discover/movie?api_key="+APIKEY+"&language=en-US&page="+randomPage;

        Log.d("RandomMovie", "url = "+URL);
        Log.d("RandomMovie", "randomPage = "+randomPage+", randomMovie = "+randomMovie);

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){
                try {
                    JSONArray results = response.getJSONArray("results");
                    JSONObject movieJsonObject = (JSONObject) results.get(randomMovie);
                    int id = movieJsonObject.getInt("id");
                    dialog.hide();
                    dialog.dismiss();
                    getMovieByID(context,id);
                } catch(JSONException e) {
                    Log.e("JSONException", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Rest Response ERROR", error.toString());
                Toast.makeText(context, "Error response from site Check Internet Connection", Toast.LENGTH_SHORT).show();
                dialog.hide();
                dialog.dismiss();
            }
        });

        requestQueue.add(objectRequest);
    }

    public static void getMovieByID(final Context context, int id) {

        dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait ...");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String URL = "https://api.themoviedb.org/3/movie/"+id+"?api_key="+APIKEY;
        Log.d("getMovieById", URL);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){
                Log.d("Rest Response", response.toString());

                Movie movie = new Movie();
                ArrayList<String> genres = new ArrayList<>();

                try {
                    movie.setId(response.getInt("id"));
                    movie.setTitle(response.getString("original_title"));
                    movie.setPosterPath(response.getString("poster_path"));
                    movie.setRating(response.getDouble("vote_average"));
                    JSONArray jsonArray = response.getJSONArray("genres");
                    for (int i=0; i<jsonArray.length(); i++) {
                        genres.add(jsonArray.getJSONObject(i).getString("name"));
                    }
                    movie.setGenres(genres);
                    movie.setOverview(response.getString("overview"));
                    movie.setRuntime(response.getInt("runtime"));
                    movie.setBudget(response.getInt("budget"));
                    movie.setReleaseDate(response.getString("release_date"));
                    movie.setRevenue(response.getInt("revenue"));
                    movie.setVoteCount(response.getInt("vote_count"));
                } catch(JSONException e) {
                    Log.e("JSONException", e.toString());
                }
                Intent intent = new Intent(context, DisplayMovie.class);
                intent.putExtra("movieCount",1);
                intent.putExtra("movie", movie);
                dialog.hide();
                dialog.dismiss();
                context.startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Rest Response ERROR", error.toString());
                Toast.makeText(context, "Error Movie with that Id does not exist", Toast.LENGTH_SHORT).show();
                dialog.hide();
                dialog.dismiss();
            }
        });

        requestQueue.add(objectRequest);
    }

    public static void discoverMovies(final Context context, String query) {

        dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait ...");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();

        String URL = "https://api.themoviedb.org/3/discover/movie?api_key="+APIKEY+query+"&sort_by=popularity.desc";

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){
                Log.d("Rest Response", response.toString());

                ArrayList<Movie> movieList = new ArrayList<>();

                try {
                    JSONArray results = response.getJSONArray("results");

                    for (int i=0; i<results.length(); i++) {
                        JSONObject movieJsonObject = (JSONObject) results.get(i);

                        JSONArray genreJsonArray = movieJsonObject.getJSONArray("genre_ids");
                        ArrayList<String> genres = new ArrayList<>();
                        for (int j=0; j<genreJsonArray.length(); j++) {
                            genres.add(getGenreByGenreId(genreJsonArray.getInt(j)));
                        }

                        movieList.add(new Movie(movieJsonObject.getInt("id"), movieJsonObject.getString("original_title"),
                                movieJsonObject.getString("poster_path"),movieJsonObject.getDouble("vote_average"),genres,movieJsonObject.getString("overview")));

                    }
                } catch(JSONException e) {
                    Log.e("JSONException", e.toString());
                }
                Intent intent = new Intent(context, DisplayMovie.class);
                intent.putExtra("movieCount",2);
                intent.putParcelableArrayListExtra("movieList", movieList);
                dialog.hide();
                dialog.dismiss();
                context.startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Rest Response ERROR", error.toString());
                Toast.makeText(context, "Error response from site Check Internet Connection", Toast.LENGTH_SHORT).show();
                dialog.hide();
                dialog.dismiss();
            }
        });

        requestQueue.add(objectRequest);
    }

    public static void searchMovies(final Context context, String query) {

        dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait ...");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();

        String URL = "https://api.themoviedb.org/3/search/movie?api_key="+APIKEY+"&query="+query+"&language=en-US&sort_by=popularity.desc";
        Log.d("MDBC Search test", URL);

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){
                Log.d("Rest Response", response.toString());

                ArrayList<Movie> movieList = new ArrayList<>();

                try {
                    JSONArray results = response.getJSONArray("results");

                    for (int i=0; i<results.length(); i++) {
                        JSONObject movieJsonObject = (JSONObject) results.get(i);

                        JSONArray genreJsonArray = movieJsonObject.getJSONArray("genre_ids");
                        ArrayList<String> genres = new ArrayList<>();
                        for (int j=0; j<genreJsonArray.length(); j++) {
                            genres.add(getGenreByGenreId(genreJsonArray.getInt(j)));
                        }

                        movieList.add(new Movie(movieJsonObject.getInt("id"), movieJsonObject.getString("original_title"),
                                movieJsonObject.getString("poster_path"),movieJsonObject.getDouble("vote_average"),genres,movieJsonObject.getString("overview")));

                    }
                } catch(JSONException e) {
                    Log.e("JSONException", e.toString());
                }
                if (movieList.size()>0) {
                    Intent intent = new Intent(context, DisplayMovie.class);
                    intent.putExtra("movieCount",3);
                    intent.putParcelableArrayListExtra("movieList", movieList);
                    dialog.hide();
                    dialog.dismiss();
                    context.startActivity(intent);
                } else {
                    dialog.hide();
                    dialog.dismiss();
                    Toast.makeText(context, "No movies were found from that search term.", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Rest Response ERROR", error.toString());
                Toast.makeText(context, "Error response from site Check Internet Connection", Toast.LENGTH_SHORT).show();
                dialog.hide();
                dialog.dismiss();
            }
        });

        requestQueue.add(objectRequest);
    }

    public static String getGenreByGenreId (int id) {
        String genre = "";

        switch (id) {
            case 28:
                genre = "Action";
                break;
            case 12:
                genre = "Adventure";
                break;
            case 16:
                genre = "Animation";
                break;
            case 35:
                genre = "Comedy";
                break;
            case 80:
                genre = "Crime";
                break;
            case 99:
                genre = "Documentary";
                break;
            case 18:
                genre = "Drama";
                break;
            case 10751:
                genre = "Family";
                break;
            case 14:
                genre = "Fantasy";
                break;
            case 36:
                genre = "History";
                break;
            case 27:
                genre = "Horror";
                break;
            case 10402:
                genre = "Music";
                break;
            case 9648:
                genre = "Mystery";
                break;
            case 10749:
                genre = "Romance";
                break;
            case 878:
                genre = "Science Fiction";
                break;
            case 10770:
                genre = "TV Movie";
                break;
            case 53:
                genre = "Thriller";
                break;
            case 10752:
                genre = "War";
                break;
            case 37:
                genre = "Western";
                break;
        }
        return genre;
    }

}
