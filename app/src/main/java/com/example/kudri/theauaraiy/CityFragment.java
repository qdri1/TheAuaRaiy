package com.example.kudri.theauaraiy;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CityFragment extends Fragment implements Listener {

    private String TAG = CityFragment.class.getSimpleName();
    private int country;
    private static final String COUNTRY = "country";

    public static CityFragment newInstance(int country) {

        Bundle args = new Bundle();

        CityFragment fragment = new CityFragment();
        args.putInt(COUNTRY, country);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_city, container, false);

        country = getArguments().getInt(COUNTRY);
        String ids = CitiesService.getIds(country);
        String appid = "07ed2f9abc0a3eaccd0a9aff406b4532";
        String url = "https://api.openweathermap.org/data/2.5/group?id=" + ids + "&units=metric&appid=" + appid;

        final RecyclerView recyclerView = v.findViewById(R.id.rec_view);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        // Запрос тастап, ответ алатын жер
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Келген ответти листка сактап алатын жер
                List<Weather> wList = getDataAsList(response);
                // Каланын списогын корсететин жер, болису, аудио.
                ListAdapter adapter = new ListAdapter(getContext(), wList, CityFragment.this, country);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(request);

        return v;
    }

    // каланы басканда тандайтын жер
    @Override
    public void onItemClick(View view, Weather weather) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("weather", weather);
        intent.putExtra("country", country);
        startActivity(intent);
    }

    private List<Weather> getDataAsList(JSONObject response) {
        try {
            JSONArray list = response.getJSONArray("list");
            List<Weather> wList = new ArrayList<>();
            for (int i = 0; i < list.length(); i++) {
                JSONObject object = list.getJSONObject(i);

                //coord
                JSONObject coord = object.getJSONObject("coord");
                double lon = coord.getDouble("lon");
                double lat = coord.getDouble("lat");

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
                int windDeg = 0;

                //the rest
                long date = 0;
                long id = object.getLong("id");
                String name = object.getString("name");

                Weather w = new Weather(lon, lat, weatherMain, weatherDesc, temp, humidity, windSpeed, windDeg, date, id, name, icon);
                wList.add(w);
            }
            return wList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
