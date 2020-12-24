package ru.mirea.filmboom.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ru.mirea.filmboom.model.dao.FilmDao;
import ru.mirea.filmboom.model.dao.HallDao;
import ru.mirea.filmboom.model.dao.SeatDao;
import ru.mirea.filmboom.model.dao.SessionDao;
import ru.mirea.filmboom.model.dao.TicketDao;
import ru.mirea.filmboom.model.entity.Film;
import ru.mirea.filmboom.model.entity.Hall;
import ru.mirea.filmboom.model.entity.Seat;
import ru.mirea.filmboom.model.entity.Session;
import ru.mirea.filmboom.model.entity.Ticket;

@Database(entities =  {Film.class, Hall.class, Seat.class, Session.class, Ticket.class},version = 10)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FilmDao filmDao();
    public abstract SessionDao sessionDao();
    public abstract SeatDao seatDao();
    public abstract TicketDao ticketDao();
    public abstract HallDao hallDao();
    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "filmboom")
                            //.fallbackToDestructiveMigration()
                            //.allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

