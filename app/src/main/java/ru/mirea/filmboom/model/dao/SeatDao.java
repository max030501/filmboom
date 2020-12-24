package ru.mirea.filmboom.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.mirea.filmboom.model.entity.Seat;

@Dao
public interface SeatDao {
    @Query("Select * from Seat where idHall=:idHall")
    LiveData<List<Seat>> getByHall(long idHall);


    @Insert
    void insert(List<Seat> seats);

}
