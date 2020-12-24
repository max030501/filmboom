package ru.mirea.filmboom.fragment;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mirea.filmboom.viewmodel.FilmViewModel;
import ru.mirea.filmboom.R;
import ru.mirea.filmboom.adapter.BuyAdapter;
import ru.mirea.filmboom.model.entity.Ticket;
import ru.mirea.filmboom.viewmodel.TicketViewModel;

public class BoughtFragment extends Fragment {
    private TicketViewModel ticketViewModel;
    private TextView emptyList;
    private BuyAdapter buyAdapter;
    private RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.buy_fragment, container, false);
        buyAdapter = new BuyAdapter(getActivity(), this);
        ticketViewModel = ViewModelProviders.of(this).get(TicketViewModel.class);
        rv = view.findViewById(R.id.rv_buy);
        emptyList = view.findViewById(R.id.empty_ticket);
        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rv.setHasFixedSize(true);
        rv.setAdapter(buyAdapter);
        ticketViewModel.getTicketList().observe(this, new Observer<List<Ticket>>() {
            @Override
            public void onChanged(List<Ticket> tickets) {
                buyAdapter.setTickets(tickets);
                if (tickets.isEmpty()) {
                    emptyList.setVisibility(View.VISIBLE);
                }
                else
                    emptyList.setVisibility(View.GONE);
            }
        });
        return view;
    }

    public void deleteTicket(Ticket ticket) {
        ticketViewModel.deleteTicket(ticket);
    }
}
