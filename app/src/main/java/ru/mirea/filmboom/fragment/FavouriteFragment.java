package ru.mirea.filmboom.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mirea.filmboom.viewmodel.FilmViewModel;
import ru.mirea.filmboom.R;
import ru.mirea.filmboom.adapter.FavouriteFilmAdapter;
import ru.mirea.filmboom.model.entity.Film;
import ru.mirea.filmboom.model.entity.FilmWithSession;

public class FavouriteFragment extends Fragment {
    private TextView emptyList;
    private RecyclerView rv;
    private FilmViewModel filmViewModel;
    private FavouriteFilmAdapter favouriteFilmAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favourite_fragment, container, false);
        rv = view.findViewById(R.id.rv);
        emptyList = view.findViewById(R.id.empty_favourite);
        favouriteFilmAdapter = new FavouriteFilmAdapter(getActivity(), this);
        rv.setAdapter(favouriteFilmAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rv.setHasFixedSize(true);
        rv.setItemViewCacheSize(20);
        rv.setDrawingCacheEnabled(true);
        rv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        filmViewModel = ViewModelProviders.of(this).get(FilmViewModel.class);
        filmViewModel.getFilmWithSessionFavourite().observe(this, new Observer<List<FilmWithSession>>() {
            @Override
            public void onChanged(List<FilmWithSession> filmWithSessions) {
                favouriteFilmAdapter.setFilmWithSessions(filmWithSessions);
                if (filmWithSessions.isEmpty()) {
                    emptyList.setVisibility(View.VISIBLE);
                } else {
                    emptyList.setVisibility(View.GONE);
                }
            }
        });


        return view;
    }

    public void onUpdateButtonClicked(Film film) {
        filmViewModel.updateFilm(film);
    }

}
