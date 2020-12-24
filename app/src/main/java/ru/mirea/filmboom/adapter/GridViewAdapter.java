package ru.mirea.filmboom.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.mirea.filmboom.R;
import ru.mirea.filmboom.model.entity.Seat;

public class GridViewAdapter extends BaseAdapter {
    private List<Seat> seatList;
    private TextView priceFilm;
    private Context context;
    private List<Integer> selectedPositions;
    private Map<Integer, Boolean> seats = new HashMap<>();
    private int sum;
    private int price;

    public GridViewAdapter(Context context, int price, TextView priceFilm) {
        this.context = context;
        this.selectedPositions = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            seats.put(i, false);
        }
        sum = 0;
        this.price = price;
        this.priceFilm = priceFilm;
    }

    public void setSeatList(List<Seat> seatList) {
        this.seatList = seatList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (seatList != null)
            return seats.size();
        else return 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;
        if (convertView == null) {
            button = new Button(context);
            button.setLayoutParams(new GridView.LayoutParams(100, 100));
            button.setPadding(8, 8, 8, 8);
            button.setText(Integer.toString(position + 1));
            for (Seat s : seatList) {
                seats.put(s.getNumberSeat(), true);
            }
            if (seats.get(position + 1)) {
                button.setBackgroundColor(Color.LTGRAY);
                button.setEnabled(false);
            } else {
                button.setBackgroundColor(Color.GREEN);
            }
            button.setTextColor(Color.BLACK);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = Integer.parseInt(((Button) view).getText().toString()) - 1;
                    int selectedIndex = selectedPositions.indexOf(pos);
                    if (seats.get(position + 1) && selectedIndex != -1) {
                        selectedPositions.remove(selectedIndex);
                        seats.put(position + 1, false);
                        sum -= price;
                        priceFilm.setText("Сумма:\n" + sum + " руб.");
                        button.setBackgroundColor(Color.GREEN);
                    } else if (!seats.get(position + 1) && selectedIndex == -1) {
                        selectedPositions.add(pos);
                        seats.put(position + 1, true);
                        sum += price;
                        priceFilm.setText("Сумма:\n" + sum + " руб.");
                        ((Button) view).setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.orange));
                    }
                }
            });
        } else {
            button = (Button) convertView;
        }

        return button;
    }

    public List<Integer> getSelected() {
        return selectedPositions;
    }
}
