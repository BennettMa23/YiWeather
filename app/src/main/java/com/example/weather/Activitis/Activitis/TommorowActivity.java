package com.example.weather.Activitis.Activitis;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.Activitis.Adapter.TommorowAdapter;
import com.example.weather.Activitis.Domains.TommorowDomain;
import com.example.weather.R;


import java.util.ArrayList;

public class TommorowActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterTommorow;
    private RecyclerView recyclerView;
//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(TommorowActivity.this, MainActivity.class);
//        startActivity(intent);
//        finish(); // 确保结束当前的 TommorowActivity，避免栈中保留多个实例
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tommorow);

        initRecyclerview();
        setVariable();
    }

    private void setVariable() {
        ConstraintLayout backBtn = findViewById(R.id.back_view);
        backBtn.setOnClickListener(v -> startActivity(new Intent(TommorowActivity.this, MainActivity.class)));
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

        recyclerView = findViewById(R.id.view2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapterTommorow = new TommorowAdapter(items);
        recyclerView.setAdapter(adapterTommorow);
    }
}
