package ru.mirea.filmboom.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {@ForeignKey(entity = Film.class, parentColumns = "id", childColumns = "idFilm",onDelete = CASCADE,onUpdate = CASCADE),
    @ForeignKey(entity = Hall.class,parentColumns = "idHall",childColumns = "idHall",onUpdate = CASCADE,onDelete = CASCADE)})
public class Session {
    @PrimaryKey(autoGenerate = true)
    private long idSession;

    private long date;

    @ColumnInfo(index = true)
    private long idFilm;

    private double price;

    @ColumnInfo(index = true)
    private long idHall;


    public Session(){
    }
    @Ignore
    public Session(long date, long idFilm, double price, long idHall) {
        this.date = date;
        this.idFilm = idFilm;
        this.price = price;
        this.idHall = idHall;
    }

    public long getIdSession() {
        return idSession;
    }

    public void setIdSession(long idSession) {
        this.idSession = idSession;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(long idFilm) {
        this.idFilm = idFilm;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getIdHall() {
        return idHall;
    }

    public void setIdHall(long idHall) {
        this.idHall = idHall;
    }

    public boolean findSession(String date) {
        SimpleDateFormat df = new SimpleDateFormat("kk:mm");
        Date d = null;
        try {
            d = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long dt = d.getTime();
        if(this.date==dt){
            return true;
        }else
            return false;
    }

}
