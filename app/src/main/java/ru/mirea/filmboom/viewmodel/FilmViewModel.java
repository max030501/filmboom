package ru.mirea.filmboom.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;


import java.util.List;

import ru.mirea.filmboom.model.entity.Film;
import ru.mirea.filmboom.model.entity.FilmWithSession;
import ru.mirea.filmboom.model.entity.Hall;
import ru.mirea.filmboom.model.entity.Seat;
import ru.mirea.filmboom.model.entity.Ticket;
import ru.mirea.filmboom.repo.FilmRepository;

public class FilmViewModel extends AndroidViewModel {
    private MutableLiveData<String> filterFilms = new MutableLiveData<>();
    private FilmRepository filmRepository;
    private LiveData<List<FilmWithSession>> searchByGenre;
    private List<String> genres;

    public FilmViewModel(Application application) {
        super(application);
        filmRepository = new FilmRepository(application);
        genres = filmRepository.getGenres();
        searchByGenre = Transformations.switchMap(filterFilms, v -> filmRepository.getFilmWithSessionByGenre(v));
    }

    public LiveData<List<FilmWithSession>> getFilmWithSessionByGenre() {
        return searchByGenre;
    }

    public void setFilterFilms(String filterFilms) {
        this.filterFilms.setValue(filterFilms);
    }

    public List<String> getGenres() {
        return genres;
    }

    public void updateFilm(Film film) {
        filmRepository.updateFilm(film);
    }

    public LiveData<List<FilmWithSession>> getFilmWithSessionFavourite() {
        return filmRepository.getFilmWithSessionFavourite();
    }
}
