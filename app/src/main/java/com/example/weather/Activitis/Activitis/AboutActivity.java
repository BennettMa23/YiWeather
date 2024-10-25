package com.example.weather.Activitis.Activitis;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.Activitis.Adapter.TommorowAdapter;
import com.example.weather.Activitis.Domains.TommorowDomain;
import com.example.weather.R;

import java.util.ArrayList;

public class CityActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterTommorow;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        initRecyclerview();
        setVariable();
    }

    private void setVariable() {
        ConstraintLayout backBtn = findViewById(R.id.back_view_top);
        backBtn.setOnClickListener(v -> startActivity(new Intent(CityActivity.this, MainActivity.class)));
    }

    private void initRecyclerview() {
        ArrayList<TommorowDomain> items = new ArrayList<>();
        items.add(new TommorowDomain("Sat", "storm", "torm", 25, 10));
        items.add(new TommorowDomain("Sun", "cloudy", "sunny", 24,  6));
        items.add(new TommorowDomain("Mon", "cloudy", "cloudy", 29, 15));
        items.add(new TommorowDomain("Tue", "cloudy", "cloudy", 22, 13));
        items.add(new TommorowDomain("Wed", "sun", "sunny", 28, 11));
        items.add(new TommorowDomain("Thu", "rainy", "Rainy", 23, 2));
        items.add(new TommorowDomain("Thu", "rainy", "Rainy", 23, 2));
        items.add(new TommorowDomain("Thu", "rainy", "Rainy", 23, 2));
        items.add(new TommorowDomain("Thu", "rainy", "Rainy", 23, 2));
        items.add(new TommorowDomain("Thu", "rainy", "Rainy", 23, 2));
        items.add(new TommorowDomain("Thu", "rainy", "Rainy", 23, 2));
        items.add(new TommorowDomain("Thu", "rainy", "Rainy", 23, 2));
        items.add(new TommorowDomain("Thu", "rainy", "Rainy", 23, 2));
        items.add(new TommorowDomain("Thu", "rainy", "Rainy", 23, 2));

        recyclerView = findViewById(R.id.cityListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapterTommorow = new TommorowAdapter(items);
        recyclerView.setAdapter(adapterTommorow);
    }
}
