package ru.mirea.filmboom.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {@ForeignKey(entity = Hall.class, parentColumns = "idHall", childColumns = "idHall", onUpdate = CASCADE, onDelete = CASCADE), @ForeignKey(entity = Ticket.class, parentColumns = "idTicket", childColumns = "idTicket", onUpdate = CASCADE, onDelete = CASCADE)})
public class Seat {
    @PrimaryKey(autoGenerate = true)
    private long idSeat;

    private int numberSeat;
    @ColumnInfo(index = true)
    private long idHall;
    @ColumnInfo(index = true)
    private long idTicket;

    public Seat() {
    }

    @Ignore
    public Seat(int numberSeat, long idHall, long idTicket) {
        this.numberSeat = numberSeat;
        this.idHall = idHall;
        this.idTicket = idTicket;
    }

    public long getIdSeat() {
        return idSeat;
    }

    public void setIdSeat(long idSeat) {
        this.idSeat = idSeat;
    }

    public int getNumberSeat() {
        return numberSeat;
    }

    public void setNumberSeat(int numberSeat) {
        this.numberSeat = numberSeat;
    }

    public long getIdHall() {
        return idHall;
    }

    public void setIdHall(long idHall) {
        this.idHall = idHall;
    }

    public long getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(long idTicket) {
        this.idTicket = idTicket;
    }
}

