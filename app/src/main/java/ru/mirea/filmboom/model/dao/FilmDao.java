package ru.mirea.filmboom.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import ru.mirea.filmboom.model.entity.Film;
import ru.mirea.filmboom.model.entity.FilmWithSession;

@Dao
public interface  FilmDao{

    @Query("Select distinct genre From Film")
    List<String> getGenres();

    @Transaction
    @Query("Select * from Film")
    LiveData<List<FilmWithSession>> getFilmWithSession();

    @Transaction
    @Query("Select * from Film where genre = :genre")
    LiveData<List<FilmWithSession>> getFilmWithSessionByGenre(String genre);

    @Transaction
    @Query("Select * from Film where isFavourite=1")
    LiveData<List<FilmWithSession>> getFilmWithSessionFavourite();

    @Update
    void update(Film film);
}

