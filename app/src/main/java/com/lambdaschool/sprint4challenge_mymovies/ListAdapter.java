package com.lambdaschool.sprint4challenge_mymovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.lambdaschool.sprint4challenge_mymovies.apiaccess.MovieApiDao;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private ArrayList<FavoriteMovie> favoriteMovieArrayList;
    private Context context;

    public ListAdapter(ArrayList<FavoriteMovie> favoriteMovieArrayList, Context context) {
        this.favoriteMovieArrayList = favoriteMovieArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_element_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final FavoriteMovie favoriteMovie = favoriteMovieArrayList.get(i);

        viewHolder.imageView.setImageBitmap(MovieApiDao.getSmallPoster(favoriteMovie.getImageSuffix(), context));
        viewHolder.textView.setText(String.format("%s - %s", favoriteMovie.getYear(), favoriteMovie.getTitle()));
        viewHolder.checkBox.setChecked(favoriteMovie.isFavorite());
        viewHolder.viewParent.setTag(favoriteMovie.getId());
        viewHolder.viewParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkedForFavorite = viewHolder.checkBox.isChecked();
                if (checkedForFavorite)
                    MovieDbSqlDao.deleteFavorite(favoriteMovie.getId());
                else
                    MovieDbSqlDao.addFavorite(favoriteMovie);

                viewHolder.checkBox.setChecked(!checkedForFavorite);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoriteMovieArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View viewParent;
        ImageView imageView;
        TextView textView;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewParent = itemView.findViewById(R.id.recycler_view_parent_layout);
            imageView = itemView.findViewById(R.id.recycler_view_image_view);
            textView = itemView.findViewById(R.id.recycler_view_text_view);
            checkBox = itemView.findViewById(R.id.check_box_main_favorite);
        }
    }
}
