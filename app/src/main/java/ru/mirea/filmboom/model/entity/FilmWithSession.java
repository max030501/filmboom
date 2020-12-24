package ru.mirea.filmboom.model.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class FilmWithSession {
    @Embedded
    public Film film;

    @Relation(parentColumn = "id",entity = Session.class,entityColumn = "idFilm")
    public List<Session> sessions;

}
