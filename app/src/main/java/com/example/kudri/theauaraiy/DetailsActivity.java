package com.example.kudri.theauaraiy;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Arrays;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final Weather w = (Weather) getIntent().getSerializableExtra("weather");
        int country = getIntent().getIntExtra("country", 0);
        setTitle(CitiesService.getRuName(country, w.getName()) + " " + w.getTemp() + getString(R.string.label_temp_item));

        ImageView imageView = findViewById(R.id.city_iv_details);
        TypedArray testArrayIcon;

        if (country == 0) {
            testArrayIcon = getResources().obtainTypedArray(R.array.array_images_kz);
        } else if (country == 1) {
            testArrayIcon = getResources().obtainTypedArray(R.array.array_images_ru);
        } else {
            testArrayIcon = getResources().obtainTypedArray(R.array.array_images_oth);
        }

        imageView.setImageResource(testArrayIcon.getResourceId(CitiesService.getEnNames(country).indexOf(w.getName()), 0));

        TextView detailsTV = findViewById(R.id.tv_details);

        String weather = getString(R.string.label_clear_ru);
        try {
            weather = getResources().getStringArray(R.array.array_weather_ru)[Arrays.asList(getResources().getStringArray(R.array.array_weather_en)).indexOf(w.getWeatherMain())];
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        ImageView iconView = findViewById(R.id.icon_view);
        String iconUrl = "http://openweathermap.org/img/w/";
        Glide.with(this)
                .load(iconUrl + w.getIcon() + ".png")
                .into(iconView);

        detailsTV.setText(getString(R.string.label_weather_main) + ": " + weather + ", " + getString(R.string.label_humidity) + ": " + w.getHumidity() + getString(R.string.label_humidity_item) + "\n" + getString(R.string.label_speed_wind) + ": " + w.getWindSpeed() + getString(R.string.label_speed_item));

        TextView nameTv = findViewById(R.id.tv_name);
        SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss (dd.MM.yy)");
        String date = ft.format(System.currentTimeMillis());
        nameTv.setText(CitiesService.getRuName(country, w.getName()) + " " + w.getTemp() + getString(R.string.label_temp_item) + ", " + date);

        Button toMap = findViewById(R.id.to_map);
        toMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this, MapsActivity.class);
                intent.putExtra("lon", w.getLon());
                intent.putExtra("lat", w.getLat());
                intent.putExtra("name", w.getName());
                startActivity(intent);
            }
        });
    }
}
