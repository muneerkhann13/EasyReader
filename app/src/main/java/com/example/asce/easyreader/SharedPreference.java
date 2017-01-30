package com.example.asce.easyreader;


        import android.content.Context;
        import android.content.SharedPreferences;

        import com.google.gson.Gson;

        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;

public class SharedPreference {

    public static final String PREFS_NAME = "MOVIE_APP";
    public static final String FAVORITES = "Movie_Favorite";

    public SharedPreference() {
        super();
    }

    // This four methods are used for maintaining favorites.

    public void removeall(Context context)
    {
        SharedPreferences settings;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        settings.edit().commit();
    }


    public void saveFavorites(Context context, List<Word> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public void addFavorite(Context context, Word movie) {
        List<Word> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<Word>();
        favorites.add(movie);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, Word movie) {
        List<Word> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(movie);
            saveFavorites(context, favorites);
        }
    }
    public ArrayList<Word> getFavorites(Context context) {
        SharedPreferences settings;
        List<Word> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            Word[] favoriteItems = gson.fromJson(jsonFavorites,
                    Word[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Word>(favorites);
        } else
            return null;

        return (ArrayList<Word>) favorites;
    }
}