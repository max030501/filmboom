package ru.mirea.filmboom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.AndroidException;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.mirea.filmboom.R;
import ru.mirea.filmboom.adapter.GridViewAdapter;
import ru.mirea.filmboom.model.entity.Hall;
import ru.mirea.filmboom.model.entity.Seat;
import ru.mirea.filmboom.model.entity.Ticket;
import ru.mirea.filmboom.viewmodel.FilmViewModel;
import ru.mirea.filmboom.viewmodel.TicketViewModel;

public class BuyTicketActivity extends AppCompatActivity {

    private TicketViewModel ticketViewModel;
    private GridViewAdapter adapter;
    private Button button;
    private ImageView poster;
    private TextView filmName;
    private TextView filmTime;
    private long idSession;
    private long idHall;
    private String name;
    private long time;
    private long image;
    private int hall;
    private int price;
    private TextView priceFilm;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (adapter.getSelected().size() > 0) {
                saveBuy();
                BuyTicketActivity.this.finish();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_ticket);
        getSupportActionBar().hide();
        priceFilm = findViewById(R.id.priceFilm);
        poster = findViewById(R.id.imageFilm);
        filmName = findViewById(R.id.filmName);
        filmTime = findViewById(R.id.filmTime);
        Intent intent = getIntent();
        image = intent.getLongExtra("image", 0);
        idSession = intent.getLongExtra("idSession", 1);
        idHall = intent.getLongExtra("idHall", 1);
        time = intent.getLongExtra("time", 0);
        name = intent.getStringExtra("name");
        price = intent.getIntExtra("price", 0);
        poster.setImageResource((int) image);
        filmName.setText(name);
        SimpleDateFormat df = new SimpleDateFormat("kk:mm");
        filmTime.setText(df.format(time));
        ticketViewModel = ViewModelProviders.of(this).get(TicketViewModel.class);
        GridView gridView = (GridView) findViewById(R.id.seatsFilm);
        ticketViewModel.getSeatByHall(idHall).observe(this, new Observer<List<Seat>>() {
            @Override
            public void onChanged(List<Seat> seats) {
                adapter.setSeatList(seats);
            }
        });
        adapter = new GridViewAdapter(this, price, priceFilm);
        gridView.setAdapter(adapter);
        ticketViewModel.getHall(idHall).observe(this, new Observer<Hall>() {
            @Override
            public void onChanged(Hall hallNumber) {
                hall = hallNumber.getNumberHall();
            }
        });

        button = findViewById(R.id.buyButton);
        button.setOnClickListener(onClickListener);


    }

    public void saveBuy() {
        String seats = "";
        List<Integer> list = adapter.getSelected();
        Collections.sort(list);
        for (Integer i : list
        ) {
            seats += (i + 1) + ",";
        }
        seats = seats.substring(0, seats.length() - 1);
        Ticket ticket = new Ticket(idSession, seats, name, time, hall);
        List<Seat> tempList = new ArrayList<>();
        for (Integer i : list) {
            Seat seat = new Seat(i + 1, idHall, 0);
            tempList.add(seat);
        }
        ticketViewModel.insertSeatAndTicket(ticket,tempList);

    }

}
