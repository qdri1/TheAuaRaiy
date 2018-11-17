package com.example.kudri.theauaraiy;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Kudri on 04.06.2018.
 */

public class ListAdapter extends RecyclerView.Adapter<VH> {

    private List<Weather> wList;
    private Listener listener;
    private Context context;
    private int country;
    private String iconUrl = "http://openweathermap.org/img/w/";
    private TextToSpeech tToS;

    public ListAdapter(Context context, List<Weather> wList, Listener listener, int country) {
        this.context = context;
        this.wList = wList;
        this.listener = listener;
        this.country = country;
        tToS = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

            }
        });
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_amaze, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(final VH holder, final int position) {
        TextView cityNameTV = holder.cityNameTV;
        TextView tempTV = holder.tempTV;
        TextView detailsTV = holder.detailsTV;

        cityNameTV.setText(CitiesService.getRuName(country, wList.get(position).getName()));
        tempTV.setText(String.valueOf(wList.get(position).getTemp()) + context.getString(R.string.label_temp_item));

        TypedArray testArrayIcon;
        if (country == 0) {
            testArrayIcon = context.getResources().obtainTypedArray(R.array.array_images_kz);
        } else if (country == 1) {
            testArrayIcon = context.getResources().obtainTypedArray(R.array.array_images_ru);
        } else {
            testArrayIcon = context.getResources().obtainTypedArray(R.array.array_images_oth);
        }

        Glide.with(context)
                .load(testArrayIcon.getResourceId(CitiesService.getEnNames(country).indexOf(wList.get(position).getName()), 0))
                .into(holder.backgroundImg);

        Glide.with(context)
                .load(iconUrl + wList.get(position).getIcon() + ".png")
                .into(holder.iconView);

        String weather = context.getString(R.string.label_clear_ru);
        try {
            weather = context.getResources().getStringArray(R.array.array_weather_ru)[Arrays.asList(context.getResources().getStringArray(R.array.array_weather_en)).indexOf(wList.get(position).getWeatherMain())];
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        detailsTV.setText(context.getString(R.string.label_weather_main) + ": " + weather + ", " + context.getString(R.string.label_humidity) + ": " + wList.get(position).getHumidity() + context.getString(R.string.label_humidity_item) + "\n" + context.getString(R.string.label_speed_wind) + ": " + wList.get(position).getWindSpeed() + context.getString(R.string.label_speed_item));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view, wList.get(holder.getAdapterPosition()));
            }
        });

        holder.shareNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Сегодня в городе " + CitiesService.getRuName(country, wList.get(position).getName()) + " температура " + wList.get(position).getTemp() + "" +
                        context.getString(R.string.label_temp_item) + ". \nХорошего вам дня!");
                sendIntent.setType("text/plain");
                context.startActivity(Intent.createChooser(sendIntent, context.getResources().getText(R.string.send_to)));
            }
        });


        final String finalWeather = weather;
        holder.playNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String prepareText = "Сегодня в городе " + CitiesService.getRuName(country, wList.get(position).getName()) + " погода " + finalWeather + ". " + context.getString(R.string.label_humidity) + ": " + wList.get(position).getHumidity() + context.getString(R.string.label_humidity_item) + ". " + context.getString(R.string.label_speed_wind) + ": " + wList.get(position).getWindSpeed() + " метр в секунду";
                tToS.speak(prepareText, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

    }

    @Override
    public int getItemCount() {
        return wList.size();
    }
}

class VH extends RecyclerView.ViewHolder {
    TextView cityNameTV, tempTV, detailsTV;
    ImageView shareNameView, backgroundImg, iconView, playNameView;

    VH(View itemView) {
        super(itemView);
        cityNameTV = itemView.findViewById(R.id.city_name_tv);
        tempTV = itemView.findViewById(R.id.temperature_tv);
        detailsTV = itemView.findViewById(R.id.tv_details);
        shareNameView = itemView.findViewById(R.id.share_name);
        playNameView = itemView.findViewById(R.id.play_name);
        backgroundImg = itemView.findViewById(R.id.background_img);
        iconView = itemView.findViewById(R.id.icon_view);
    }
}

interface Listener {
    void onItemClick(View view, Weather weather);
}
