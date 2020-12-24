package ru.mirea.filmboom.model.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Hall {
    @PrimaryKey(autoGenerate = true)
    private long idHall;

    private int numberHall;

    public Hall(){}

    public long getIdHall() {
        return idHall;
    }

    public void setIdHall(long idHall) {
        this.idHall = idHall;
    }

    public int getNumberHall() {
        return numberHall;
    }

    public void setNumberHall(int numberHall) {
        this.numberHall = numberHall;
    }
}
