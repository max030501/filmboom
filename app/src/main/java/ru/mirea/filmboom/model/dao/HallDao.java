package ru.mirea.filmboom.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import ru.mirea.filmboom.model.entity.Hall;

@Dao
public interface HallDao {

    @Transaction
    @Query("Select * from Hall where idHall=:idHall")
    LiveData<Hall> getHall(long idHall);


}
