package ru.mirea.filmboom.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Session.class,parentColumns = "idSession",childColumns = "idSession",onDelete = CASCADE,onUpdate = CASCADE))
public class Ticket {
    @PrimaryKey(autoGenerate = true)
    private long idTicket;

    @ColumnInfo(index = true)
    private long idSession;

    private String seats;

    private String name;

    private long time;

    private int hall;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getHall() {
        return hall;
    }

    public void setHall(int hall) {
        this.hall = hall;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public Ticket(){}
    @Ignore
    public Ticket(long idSession, String seats,String name,long time, int hall) {
        this.idSession = idSession;
        this.seats = seats;
        this.name = name;
        this.time = time;
        this.hall = hall;
    }

    public long getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(long idTicket) {
        this.idTicket = idTicket;
    }

    public long getIdSession() {
        return idSession;
    }

    public void setIdSession(long idSession) {
        this.idSession = idSession;
    }
}
