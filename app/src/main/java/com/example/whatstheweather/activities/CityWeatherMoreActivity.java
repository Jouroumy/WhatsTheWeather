package com.example.whatstheweather.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.whatstheweather.R;
import com.example.whatstheweather.api.Retrofit2Client;
import com.example.whatstheweather.api.WeatherAPI;
import com.example.whatstheweather.models.basicweather.Weather;
import com.example.whatstheweather.models.dayweather.DayWeather;
import com.example.whatstheweather.models.dayweather.List;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityWeatherMoreActivity extends AppCompatActivity implements View.OnClickListener, Callback<DayWeather> {

    private DayWeather dayWeather;
    private TextView text;
    private ListView listView;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_more_weather);

        Bundle extras = getIntent().getExtras();
        String cityName = extras.getString("cityName");

        Button showButton = findViewById(R.id.ShowMore);
        showButton.setOnClickListener(this);

        listView = findViewById(R.id.listItem);
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,  arrayList);
        listView.setAdapter(adapter);

        WeatherAPI api = Retrofit2Client.getRetrofit2Client().create(WeatherAPI.class);
        Call<DayWeather> call = api.getCityMoreWeather(cityName, "metric", 40);
        call.enqueue(this);
    }

    @Override
    public void onClick(View view) {
        if (R.id.ShowMore == view.getId()) {
            if (dayWeather != null) {
                String nextDays = "Here is the weather for the next 24 hours";
                arrayList.add(nextDays);
                adapter.notifyDataSetChanged();
                int index = 0;
                int size = 0;
                for (List e : dayWeather.getList()) {
                    if(index < 8) {
                        String add = "Date : " + e.getDt_txt().toString()
                                + " / Temp : " + e.getMain().getTemp().toString() + " °C";
                        arrayList.add(add);
                        adapter.notifyDataSetChanged();
                    }
                    index++;
                }
                index = 0;
                nextDays = "Here is the weather for the five next days";
                arrayList.add(nextDays);
                adapter.notifyDataSetChanged();
                for (List e : dayWeather.getList()) {
                    if(index % 5 == 0 && size < 5) {
                        String add = "Date : " + e.getDt_txt().toString()
                                + " / Temp : " + e.getMain().getTempMin().toString() + " °C";
                        arrayList.add(add);
                        adapter.notifyDataSetChanged();
                    }
                    index++;
                }
            }
        }
    }

    @Override
    public void onResponse(Call<DayWeather> call, Response<DayWeather> response) {
        Log.d("azerty", response.body().toString());
        if (!response.isSuccessful()) {
            Toast.makeText(this, response.message(), Toast.LENGTH_SHORT).show();
            return;
        }

        if (response.body() != null) {
            this.dayWeather = response.body();
        }
    }

    @Override
    public void onFailure(Call<DayWeather> call, Throwable t) {
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
        Log.d("azerty", t.getMessage());
    }

}
