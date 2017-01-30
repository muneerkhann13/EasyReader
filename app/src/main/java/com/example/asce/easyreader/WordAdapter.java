package com.example.asce.easyreader;

/**
 * Created by Asce on 16/07/16.
 */
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class WordAdapter extends  RecyclerView.Adapter<WordAdapter.MyViewHolder> {


    public List<Word> wordsList;
    public Activity activity;
    public SharedPreference sharedPreference=new SharedPreference();


    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title, part_of_speech,meaning,example,number;
        public ImageButton shortlist;

        public MyViewHolder(View view) {

            super(view);
            shortlist=(ImageButton)view.findViewById(R.id.imageButton);
            number=(TextView)view.findViewById(R.id.number);
            title = (TextView) view.findViewById(R.id.title);
            part_of_speech = (TextView) view.findViewById(R.id.part_of_speech);
            meaning = (TextView) view.findViewById(R.id.meaning);
            example=(TextView) view.findViewById(R.id.example);

        }
    }

    public WordAdapter(Activity activity,List<Word> wordsList) {
        this.activity = activity;
        this.wordsList = wordsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Log.i("dsd","dsad");
        final Word wordd = wordsList.get(position);
        int pos=position+1;
        holder.number.setText(pos+". ");
        holder.title.setText(wordd.getTitle());
        holder.part_of_speech.setText(wordd.getPart_of_speech());
        holder.meaning.setText(wordd.getMeaning());
        holder.example.setText(wordd.getExample());
        if(holder.shortlist.getTag().toString().equalsIgnoreCase("unlike"))
        {
            holder.shortlist.setImageResource(R.drawable.right_icon_unselected);
        }
        else
            holder.shortlist.setImageResource(R.drawable.right_icon_selected);

        if (checkFavoriteItem(wordd)) {
            holder.shortlist.setImageResource(R.drawable.right_icon_selected);
            holder.shortlist.setTag("like");
        } else {
            holder.shortlist.setImageResource(R.drawable.right_icon_unselected);
            holder.shortlist.setTag("unlike");
        }

        holder.shortlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder.shortlist.getTag().toString().equalsIgnoreCase("unlike"))
                {
                    holder.shortlist.setTag("like");
                    wordsList.get(position).setTag("like");
                    holder.shortlist.setImageResource(R.drawable.right_icon_selected);
                    sharedPreference.addFavorite(activity, wordsList.get(position));
                }
                else
                {
                    holder.shortlist.setTag("unlike");
                    holder.shortlist.setImageResource(R.drawable.right_icon_unselected);
                    wordsList.get(position).setTag("unlike");
                    sharedPreference.removeFavorite(activity,wordsList.get(position));

                }
                //notifyItemChanged(position);
            }
        });
    }

    public boolean checkFavoriteItem(Word checkMovie) {
        Log.i("mama","papa");
        boolean check = false;
        List<Word> favorites = sharedPreference.getFavorites(activity);
        if (favorites != null) {
            for (Word movie : favorites) {
                if (movie.equals(checkMovie)) {
                    Log.i("hello","hinjm");
                    check = true;
                    break;
                }
            }
        }
        return check;
    }



    @Override
    public int getItemCount() {
        return wordsList.size();
    }

}