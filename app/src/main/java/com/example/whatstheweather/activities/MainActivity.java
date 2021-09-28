package com.example.whatstheweather.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.whatstheweather.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        cityName = findViewById(R.id.cityText);

        Button searchButton = findViewById(R.id.weatherButton);
        searchButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (R.id.weatherButton == view.getId()) {
            Intent intent = new Intent(this, CityWeatherActivity.class);
            intent.putExtra("cityName", cityName.getText().toString());
            startActivity(intent);
        }
    }
}
