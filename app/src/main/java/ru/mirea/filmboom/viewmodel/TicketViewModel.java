package ru.mirea.filmboom.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.mirea.filmboom.model.entity.Hall;
import ru.mirea.filmboom.model.entity.Seat;
import ru.mirea.filmboom.model.entity.Ticket;
import ru.mirea.filmboom.repo.TicketRepository;

public class TicketViewModel extends AndroidViewModel {
    private TicketRepository ticketRepository;
    private LiveData<List<Ticket>> ticketList;


    public TicketViewModel(Application application) {
        super(application);
        ticketRepository = new TicketRepository(application);
        ticketList = ticketRepository.getTicketList();
    }

    public LiveData<List<Seat>> getSeatByHall(long idHall) {
        return ticketRepository.getSeatByHall(idHall);
    }

    public LiveData<List<Ticket>> getTicketList() {
        return ticketList;
    }

    public LiveData<Hall> getHall(long idHall) {
        return ticketRepository.getHall(idHall);
    }

    public void deleteTicket(Ticket ticket) {
        ticketRepository.deleteTicket(ticket);
    }

    public void insertSeatAndTicket(Ticket ticket, List<Seat> seats) {
        ticketRepository.insertSeatAndTicket(ticket, seats);
    }

}
