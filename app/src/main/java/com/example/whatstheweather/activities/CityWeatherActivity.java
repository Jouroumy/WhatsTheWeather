package com.example.whatstheweather.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.whatstheweather.R;
import com.example.whatstheweather.api.Retrofit2Client;
import com.example.whatstheweather.models.basicweather.Weather;
import com.example.whatstheweather.api.WeatherAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityWeatherActivity extends AppCompatActivity implements View.OnClickListener, Callback<Weather> {

    private Weather weather;
    private TextView cityNameText;
    private TextView cityWindText;
    private TextView cityTempText;
    private TextView cityPressureText;
    private TextView cityMaxTempText;
    private TextView cityMinTempText;
    private TextView cityStateText;
    private String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_weather);

        Bundle extras = getIntent().getExtras();
        cityName = extras.getString("cityName");

        this.cityNameText = findViewById(R.id.CityNameText);
        this.cityPressureText = findViewById(R.id.CityPressureText);
        this.cityTempText = findViewById(R.id.CityTempText);
        this.cityMaxTempText = findViewById(R.id.CityMaxTempText);
        this.cityMinTempText = findViewById(R.id.CityMinTempText);
        this.cityWindText = findViewById(R.id.CityWindText);
        this.cityStateText = findViewById(R.id.CityStateText);

        Button showButton = findViewById(R.id.ShowDetails);
        showButton.setOnClickListener(this);

        Button detailsButton = findViewById(R.id.MoreDetails);
        detailsButton.setOnClickListener(this);

        WeatherAPI api = Retrofit2Client.getRetrofit2Client().create(WeatherAPI.class);
        Call<Weather> call = api.getCityWeather(cityName, "metric");
        call.enqueue(this);
    }

    @Override
    public void onClick(View view) {
        if(R.id.ShowDetails == view.getId()) {
            if(weather != null) {
                Toast.makeText(this, weather.getName(), Toast.LENGTH_SHORT).show();
                //this.cityText.setText(weather.getName() + "is actually °" + weather.getMain().getTemp());
                this.cityNameText.setText(weather.getName());
                String pressure = "Pressure : " + weather.getMain().getPressure() + " / humidity : " + weather.getMain().getHumidity();
                this.cityPressureText.setText(pressure);
                int far = (int) (weather.getMain().getTemp() * 9/5 + 32);
                String temp = "Temperature : " + weather.getMain().getTemp() + "°C / " + far + "°F";
                this.cityTempText.setText(temp);
                int min = (int) (weather.getMain().getTempMin() * 9/5 + 32);
                int max = (int) (weather.getMain().getTempMax() * 9/5 + 32);
                String tempMin = "Temperature Min : " + weather.getMain().getTempMin() + "°C / " + min + "°F";
                this.cityMinTempText.setText(tempMin);
                String tempMax = "Temperature Max : " + weather.getMain().getTempMax() + "°C / " + max + "°F";
                this.cityMaxTempText.setText(tempMax);
                String wind = "Wind speed : " + Double.toString(weather.getWind().getSpeed()) + " / Wind degrees : " + Double.toString(weather.getWind().getDeg());
                this.cityWindText.setText(wind);
                String state = weather.getWeather().get(0).getDescription();
                this.cityStateText.setText(state);
            }
        }
        if(R.id.MoreDetails == view.getId()) {
            Intent intent = new Intent(this, CityWeatherMoreActivity.class);
            intent.putExtra("cityName", cityName);
            startActivity(intent);
        }
    }

    @Override
    public void onResponse(Call<Weather> call, Response<Weather> response) {
        if(!response.isSuccessful()) {
            Toast.makeText(this, response.message(), Toast.LENGTH_SHORT).show();
            return;
        }

        if(response.body() != null) {
            this.weather = response.body();
        }
    }

    @Override
    public void onFailure(Call<Weather> call, Throwable t) {
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

}
