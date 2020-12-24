package ru.mirea.filmboom.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.mirea.filmboom.model.entity.Ticket;

@Dao
public interface TicketDao {
    @Insert
    void insert(Ticket ticket);

    @Query("Select * from Ticket")
    LiveData<List<Ticket>> getAll();

    @Delete
    void delete(Ticket ticket);

    @Query("Select COALESCE(max(idTicket),0) from Ticket")
    long getMaxId();
}
