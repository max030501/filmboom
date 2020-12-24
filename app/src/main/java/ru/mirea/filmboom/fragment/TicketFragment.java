package ru.mirea.filmboom.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
import ru.mirea.filmboom.adapter.FilmAdapter;
import ru.mirea.filmboom.model.entity.Film;
import ru.mirea.filmboom.model.entity.FilmWithSession;

public class TicketFragment extends Fragment {
    private FilmViewModel filmViewModel;
    private FilmAdapter filmAdapter;
    private Spinner spinner;
    private RecyclerView rv;
    private String prev = "";
    private String genre = "Всё";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ticket_fragment, container, false);
        filmAdapter = new FilmAdapter(getActivity(), this);
        filmViewModel = ViewModelProviders.of(this).get(FilmViewModel.class);
        rv = view.findViewById(R.id.rv);
        spinner = view.findViewById(R.id.spinner);
        rv.setAdapter(filmAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rv.setHasFixedSize(true);
        rv.setItemViewCacheSize(20);
        rv.setDrawingCacheEnabled(true);
        rv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        List<String> strings = filmViewModel.getGenres();
        strings.add(0, "Всё");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, strings);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        filmViewModel.getFilmWithSessionByGenre().observe(this, new Observer<List<FilmWithSession>>() {
            @Override
            public void onChanged(List<FilmWithSession> filmWithSessions) {
                filmAdapter.setFilmWithSessions(filmWithSessions);
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genre = (String) parent.getItemAtPosition(position);
                filmViewModel.setFilterFilms(genre);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return view;
    }

    public void onUpdateButtonClicked(Film film) {
        filmViewModel.updateFilm(film);
    }

}



