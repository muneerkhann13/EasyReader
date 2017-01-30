package com.example.asce.easyreader;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

public class WordAdapter1 extends  RecyclerView.Adapter<WordAdapter1.MyViewHolder> {


    public Word wordd;
    public List<Word> moviesList;
    public Activity activity;
    public SharedPreference sharedPreference=new SharedPreference();
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

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

    public WordAdapter1(Activity activity,List<Word> moviesList) {
        this.activity = activity;
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item1, parent, false);

        return new MyViewHolder(itemView);
    }

    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        wordd = moviesList.get(position);
        //holder.number.setText(pos+"");
        holder.title.setText(wordd.getTitle());
        holder.part_of_speech.setText(wordd.getPart_of_speech());
        holder.meaning.setText(wordd.getMeaning());
        holder.example.setText(wordd.getExample());
        if(wordd.shortlist_tag.equalsIgnoreCase("unlike"))
        {
            holder.shortlist.setImageResource(R.drawable.right_icon_unselected);
        }
        else
            holder.shortlist.setImageResource(R.drawable.right_icon_selected);


        holder.shortlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(moviesList.get(position).getTag().equalsIgnoreCase("unlike"))
                {
                    moviesList.get(position).setTag("like");
                    holder.shortlist.setImageResource(R.drawable.right_icon_selected);
                    sharedPreference.addFavorite(activity, moviesList.get(position));
                }
                else
                {
                    holder.shortlist.setImageResource(R.drawable.right_icon_unselected);
                    moviesList.get(position).setTag("unlike");
                    sharedPreference.removeFavorite(activity,moviesList.get(position));
                    moviesList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getItemCount());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

}