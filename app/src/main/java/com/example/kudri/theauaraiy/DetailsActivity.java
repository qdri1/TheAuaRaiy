package com.example.kudri.theauaraiy;

import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = DetailsActivity.class.getSimpleName();

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

        String id = String.valueOf(w.getId());
        System.out.println("###id: " + id);
        String appid = "07ed2f9abc0a3eaccd0a9aff406b4532";
        String url = "https://api.openweathermap.org/data/2.5/forecast?id=" + id + "&units=metric&appid=" + appid;

        final RecyclerView recyclerView = findViewById(R.id.rec_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        // запрос тастап ответ алатын жер
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                List<Weather> wList = getDataForIntervals(response);
                // список времени погоды
                TimesListAdapter adapter = new TimesListAdapter(DetailsActivity.this, wList);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(request);
    }

    private List<Weather> getDataForIntervals(JSONObject response) {
        try {
            JSONArray list = response.getJSONArray("list");
            List<Weather> wList = new ArrayList<>();
            for (int i = 0; i < list.length(); i++) {
                JSONObject object = list.getJSONObject(i);

                //weather
                JSONArray weather = object.getJSONArray("weather");
                JSONObject wObject = weather.getJSONObject(0);
                String weatherMain = wObject.getString("main");
                String weatherDesc = wObject.getString("description");
                String icon = wObject.getString("icon");

                //main
                JSONObject main = object.getJSONObject("main");
                int temp = main.getInt("temp");
                int humidity = main.getInt("humidity");

                //wind
                JSONObject wind = object.getJSONObject("wind");
                int windSpeed = wind.getInt("speed");

                long date = object.getLong("dt");
                String dateTxt = object.getString("dt_txt");

                if (i == 0) {
                    String dateTitleTxt = dateTxt;
                    dateTitleTxt = dateTitleTxt.substring(0, 11);
                    wList.add(new Weather(dateTitleTxt, true));
                } else if (dateTxt.contains("00:00:00")) {
                    String dateTitleTxt = dateTxt;
                    dateTitleTxt = dateTitleTxt.substring(0, 11);
                    wList.add(new Weather(dateTitleTxt, true));
                }
                dateTxt = dateTxt.substring(11);

                wList.add(new Weather(weatherMain, weatherDesc, icon, temp, humidity, windSpeed, date, dateTxt, false));
            }
            return wList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
