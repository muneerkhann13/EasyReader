package com.example.asce.easyreader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class FavoriteListFragment extends Fragment {


    RecyclerView favoriteList;
    SharedPreference sharedPreference;
    List<Word> favorites;

    Activity activity;
    WordAdapter1 movieAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_list, container,
                false);

        // Get favorite items from SharedPreferences.
        sharedPreference = new SharedPreference();
        favorites = sharedPreference.getFavorites(activity);

        if (favorites == null) {
            {
                showAlert(getResources().getString(R.string.no_favorites_items),
                        getResources().getString(R.string.no_favorites_msg));
            }
        } else {

            if (favorites.size() == 0) {

                showAlert(getResources().getString(R.string.no_favorites_items), getResources().getString(R.string.no_favorites_msg));
            }

            favoriteList = (RecyclerView) view.findViewById(R.id.list_product);
            if (favorites != null) {

                movieAdapter = new WordAdapter1(activity, favorites);
                LinearLayoutManager llm = new LinearLayoutManager(getContext());
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                favoriteList.setLayoutManager(llm);
                favoriteList.setItemAnimator(new DefaultItemAnimator());
                favoriteList.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
                favoriteList.setAdapter(movieAdapter);

            }
        }
        return view;
    }

    public void showAlert(String title, String message) {
        if (activity != null && !activity.isFinishing()) {
            AlertDialog alertDialog = new AlertDialog.Builder(activity)
                    .create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setCancelable(false);

            // setting OK Button
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            activity.finish();
                        }
                    });
            alertDialog.show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}