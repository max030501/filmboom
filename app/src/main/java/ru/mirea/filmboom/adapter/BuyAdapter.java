package ru.mirea.filmboom.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

import ru.mirea.filmboom.R;
import ru.mirea.filmboom.model.entity.Ticket;
import ru.mirea.filmboom.fragment.BoughtFragment;

public class BuyAdapter extends RecyclerView.Adapter<BuyAdapter.BuyHolder> {
    private final LayoutInflater inflater;
    private Context context;
    private List<Ticket> ticketList;
    private BoughtFragment boughtFragment;

    public BuyAdapter(Context context, BoughtFragment boughtFragment) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.boughtFragment = boughtFragment;
    }

    public void setTickets(List<Ticket> tickets) {
        ticketList = tickets;
        notifyDataSetChanged();
    }
    public List<Ticket> getTickets(){
        return ticketList;
    }

    @NonNull
    @Override
    public BuyAdapter.BuyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.buy_card, parent, false);
        return new BuyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyAdapter.BuyHolder holder, int position) {
        holder.filmName.setText(ticketList.get(position).getName());
        DateFormat df = new SimpleDateFormat("kk:mm");
        holder.filmTime.setText(df.format(ticketList.get(position).getTime()));
        holder.ticket = ticketList.get(position);
        holder.hall.setText("Зал: " + String.valueOf(ticketList.get(position).getHall()));
        holder.deleteTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boughtFragment.deleteTicket(ticketList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (ticketList != null)
            return ticketList.size();
        else return 0;
    }

    class BuyHolder extends RecyclerView.ViewHolder {
        private Ticket ticket;
        private final TextView filmName;
        private final TextView filmTime;
        private final TextView seats;
        private final ImageView qrCode;
        private final Dialog popup;
        private final TextView hall;
        private final ImageView deleteTicket;

        public BuyHolder(@NonNull View itemView) {
            super(itemView);
            filmName = itemView.findViewById(R.id.filmName);
            filmTime = itemView.findViewById(R.id.filmTime);
            hall = itemView.findViewById(R.id.hall);
            popup = new Dialog(Objects.requireNonNull(context));
            popup.setContentView(R.layout.popup_activity);
            deleteTicket = itemView.findViewById(R.id.delete_ticket);
            seats = popup.findViewById(R.id.reserved_seats);
            qrCode = popup.findViewById(R.id.qr_code);
            Button popupButton = itemView.findViewById(R.id.detail_button);
            popupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    seats.setText("Места: " + ticket.getSeats());
                    qrCode.setImageBitmap(generateQR(ticket));
                    popup.show();
                    ImageView close = popup.findViewById(R.id.close_popup);
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popup.dismiss();
                        }
                    });
                }
            });

        }
    }

    private Bitmap generateQR(Ticket ticket) {
        SimpleDateFormat df = new SimpleDateFormat("kk:mm");
        String time = df.format(ticket.getTime());
        StringBuilder infoB = new StringBuilder(ticket.getName() + " " + ticket.getSeats() + " " + ticket.getHall() + " " + time);
        String info = infoB.toString();
        System.out.println(info);
        Bitmap bitmap = null;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(info, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
