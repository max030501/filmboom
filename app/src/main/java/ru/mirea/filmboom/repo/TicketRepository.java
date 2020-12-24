package ru.mirea.filmboom.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Transaction;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import ru.mirea.filmboom.model.AppDatabase;
import ru.mirea.filmboom.model.dao.HallDao;
import ru.mirea.filmboom.model.dao.SeatDao;
import ru.mirea.filmboom.model.dao.TicketDao;
import ru.mirea.filmboom.model.entity.Hall;
import ru.mirea.filmboom.model.entity.Seat;
import ru.mirea.filmboom.model.entity.Ticket;

public class TicketRepository {
    private SeatDao seatDao;
    private TicketDao ticketDao;
    private HallDao hallDao;
    private LiveData<List<Ticket>> ticketList;

    public TicketRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        seatDao = db.seatDao();
        ticketDao = db.ticketDao();
        hallDao = db.hallDao();
        ticketList = ticketDao.getAll();
    }

    public LiveData<List<Ticket>> getTicketList() {
        return ticketList;
    }

    public LiveData<List<Seat>> getSeatByHall(long idHall) {
        return seatDao.getByHall(idHall);
    }


    public LiveData<Hall> getHall(long idHall) {
        return hallDao.getHall(idHall);
    }


    public void insertSeatAndTicket(Ticket ticket, List<Seat> seats) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                ticketDao.insert(ticket);
                long idTicket = -1;
                Callable<Long> call = () -> ticketDao.getMaxId();
                FutureTask<Long> futureTask = new FutureTask<>(call);
                new Thread(futureTask).start();
                try {
                    idTicket = futureTask.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for(Seat s:seats){
                    s.setIdTicket(idTicket);
                }
                seatDao.insert(seats);
            }
        }).start();
    }

    public void deleteTicket(Ticket ticket) {
        new Thread(() -> ticketDao.delete(ticket)).start();
    }

}
