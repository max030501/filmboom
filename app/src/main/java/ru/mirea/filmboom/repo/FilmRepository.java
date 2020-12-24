package ru.mirea.filmboom.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Transaction;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import ru.mirea.filmboom.model.AppDatabase;
import ru.mirea.filmboom.model.entity.Film;
import ru.mirea.filmboom.model.dao.FilmDao;
import ru.mirea.filmboom.model.entity.FilmWithSession;
import ru.mirea.filmboom.model.entity.Hall;
import ru.mirea.filmboom.model.dao.HallDao;
import ru.mirea.filmboom.model.entity.Seat;
import ru.mirea.filmboom.model.dao.SeatDao;
import ru.mirea.filmboom.model.entity.Session;
import ru.mirea.filmboom.model.dao.SessionDao;
import ru.mirea.filmboom.model.entity.Ticket;
import ru.mirea.filmboom.model.dao.TicketDao;

public class FilmRepository {
    private FilmDao filmDao;
    private LiveData<List<FilmWithSession>> filmWithSession;

    public FilmRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        filmDao = db.filmDao();
        filmWithSession = filmDao.getFilmWithSession();
    }

    public List<String> getGenres() {
        Callable<List<String>> call = () -> filmDao.getGenres();
        FutureTask<List<String>> futureTask = new FutureTask<>(call);
        new Thread(futureTask).start();
        List<String> genres = null;
        try {
            genres = futureTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return genres;
    }


    public LiveData<List<FilmWithSession>> getFilmWithSessionByGenre(String genre) {
        if (genre == "Всё")
            return filmWithSession;
        else
            return filmDao.getFilmWithSessionByGenre(genre);
    }


    public void updateFilm(Film film) {
        new Thread(() -> filmDao.update(film)).start();
    }


    public LiveData<List<FilmWithSession>> getFilmWithSessionFavourite() {
        return filmDao.getFilmWithSessionFavourite();
    }


}
