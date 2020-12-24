package ru.mirea.filmboom.model.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class HallWithSession {
    @Embedded
    public Hall hall;

    @Relation(parentColumn = "idHall",entity = Session.class,entityColumn = "idHall")
    public List<Session> sessions;
}
