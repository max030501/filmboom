package ru.mirea.filmboom.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.mirea.filmboom.activity.BuyTicketActivity;
import ru.mirea.filmboom.R;
import ru.mirea.filmboom.model.entity.FilmWithSession;
import ru.mirea.filmboom.model.entity.Session;
import ru.mirea.filmboom.fragment.FavouriteFragment;

public class FavouriteFilmAdapter extends RecyclerView.Adapter<FavouriteFilmAdapter.FilmViewHolder> {
    private final LayoutInflater inflater;
    private List<FilmWithSession> filmWithSessions;
    private Map<Long, Boolean> visibility = new HashMap<>();
    private FavouriteFragment favouriteFragment;
    private Context contextGetter;

    public FavouriteFilmAdapter(Context context, FavouriteFragment favouriteFragment) {
        inflater = LayoutInflater.from(context);
        this.favouriteFragment = favouriteFragment;
        contextGetter = context;
    }

    @NonNull
    @Override
    public FavouriteFilmAdapter.FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.film_card, parent, false);
        return new FavouriteFilmAdapter.FilmViewHolder(view);
    }


    public void setFilmWithSessions(List<FilmWithSession> filmWithSessions) {
        this.filmWithSessions = filmWithSessions;
        notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull FavouriteFilmAdapter.FilmViewHolder holder, int i) {
        if (visibility.containsKey(filmWithSessions.get(i).film.getId())) {
            if (visibility.get(filmWithSessions.get(i).film.getId()))
                holder.details.setVisibility(View.VISIBLE);
            else
                holder.details.setVisibility(View.GONE);
        } else {
            visibility.put(filmWithSessions.get(i).film.getId(), false);
            holder.details.setVisibility(View.GONE);
        }
        holder.filmDesc.setText(filmWithSessions.get(i).film.getDescription());
        holder.filmGenre.setText(filmWithSessions.get(i).film.getGenre());
        holder.filmName.setText(filmWithSessions.get(i).film.getName());
        holder.filmRating.setText(String.valueOf(filmWithSessions.get(i).film.getRating()));
       // holder.filmPhoto.setImageResource((int) filmWithSessions.get(i).film.getImage());
        Picasso.with(holder.itemView.getContext())
                .load((int) filmWithSessions.get(i).film.getImage())
                .fit()
                .into(holder.filmPhoto);
        //ImageLoader il =ImageLoader.getInstance();
//        il.init(ImageLoaderConfiguration.createDefault(holder.itemView.getContext()));
//        il.displayImage("drawable://" + filmWithSessions.get(i).film.getImage(),holder.filmPhoto);
//        il.destroy();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.details.getVisibility() == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(holder.details);
                    holder.details.setVisibility(View.GONE);
                    holder.show_details.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand_more, 0);
                    visibility.put(filmWithSessions.get(i).film.getId(), false);
                } else {
                    TransitionManager.beginDelayedTransition(holder.details);
                    holder.details.setVisibility(View.VISIBLE);
                    holder.show_details.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand_less, 0);
                    visibility.put(filmWithSessions.get(i).film.getId(), true);
                }
            }
        });

        DateFormat df = new SimpleDateFormat("kk:mm");
        if (filmWithSessions != null) {
            filmWithSessions.get(i).sessions.sort(Comparator.comparingLong(m -> m.getDate()));
            for (int a = 0; a < filmWithSessions.get(i).sessions.size(); a++) {
                holder.buttons.get(a).setText(df.format(filmWithSessions.get(i).sessions.get(a).getDate()) + "\n" + (int) filmWithSessions.get(i).sessions.get(a).getPrice() + " руб.");
                holder.buttons.get(a).setVisibility(View.VISIBLE);
            }
        }
        if (filmWithSessions.get(i).film.isFavourite()) {
            holder.favourite.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_favorite, 0);
        } else {
            holder.favourite.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_favorite_border, 0);
        }
        holder.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filmWithSessions.get(i).film.setFavourite(false);
                holder.details.setVisibility(View.GONE);
                visibility.put(filmWithSessions.get(i).film.getId(), false);
                favouriteFragment.onUpdateButtonClicked(filmWithSessions.get(i).film);
                holder.favourite.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_favorite_border, 0);

            }
        });
        holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = holder.time.getCheckedRadioButtonId();
                RadioButton selectedButton = (RadioButton) holder.itemView.findViewById(selected);
                if (selectedButton != null) {
                    long idHall = 1;
                    long idSession = 1;
                    long time = 0;
                    int price = 0;
                    String name = filmWithSessions.get(i).film.getName();
                    long image = filmWithSessions.get(i).film.getImage();
                    for (Session session : filmWithSessions.get(i).sessions) {
                        if (session.findSession((String) selectedButton.getText().toString().split("\n")[0])) {
                            idHall = session.getIdHall();
                            idSession = session.getIdSession();
                            price = (int) session.getPrice();
                        }
                    }
                    SimpleDateFormat df = new SimpleDateFormat("kk:mm");
                    try {
                        time = df.parse((String) selectedButton.getText()).getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    openBuy(idHall, idSession, name, time, price, image);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (filmWithSessions != null)
            return filmWithSessions.size();
        else return 0;
    }

    class FilmViewHolder extends RecyclerView.ViewHolder {
        private final CardView cv;
        private final TextView filmName;
        private final TextView filmDesc;
        private final TextView filmRating;
        private final TextView filmGenre;
        private final ImageView filmPhoto;
        private final RelativeLayout details;
        private final TextView show_details;
        private final RadioGroup time;
        private List<RadioButton> buttons = new ArrayList<>();
        private final Button buy;
        private final Button favourite;

        FilmViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            filmName = (TextView) itemView.findViewById(R.id.film_name);
            filmRating = (TextView) itemView.findViewById(R.id.film_rating);
            filmGenre = (TextView) itemView.findViewById(R.id.film_genre);
            filmDesc = (TextView) itemView.findViewById(R.id.film_description);
            filmPhoto = (ImageView) itemView.findViewById(R.id.film_photo);
            details = (RelativeLayout) itemView.findViewById(R.id.rl_bot);
            show_details = (TextView) itemView.findViewById(R.id.details_button);
            time = (RadioGroup) itemView.findViewById(R.id.time);
            buy = (Button) itemView.findViewById(R.id.buy);
            favourite = (Button) itemView.findViewById(R.id.favourite);
            List<Integer> tmp = Arrays.asList(R.id.time1, R.id.time2, R.id.time3, R.id.time4, R.id.time5);
            for (Integer btn : tmp) {
                buttons.add(itemView.findViewById(btn));
            }
        }
    }

    private void openBuy(long idHall, long idSession, String name, long time, int price, long image) {
        Intent intent = new Intent(favouriteFragment.getActivity(), BuyTicketActivity.class);
        intent.putExtra("idHall", idHall);
        intent.putExtra("idSession", idSession);
        intent.putExtra("name", name);
        intent.putExtra("time", time);
        intent.putExtra("price", price);
        intent.putExtra("image", image);
        contextGetter.startActivity(intent);
    }
}
