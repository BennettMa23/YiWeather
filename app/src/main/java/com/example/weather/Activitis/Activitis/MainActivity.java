package com.example.weather.Activitis.Activitis;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.Activitis.Adapter.HourlyAdapter;
import com.example.weather.Activitis.Domains.Hourly;
import com.example.weather.Activitis.Views.SemicircleProgressView;
import com.example.weather.R;

import java.util.ArrayList;

import android.widget.Toast; // 确保导入了正确的 Toast 类



public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterHourly;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_main);

        LinearLayout linearLayout = findViewById(R.id.topTxt);
        if (linearLayout!= null) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) linearLayout.getLayoutParams();
            if (layoutParams!= null) {
                layoutParams.topMargin = getStatusBarHeight();
            }
        }

        LinearLayout jinggaoLayout = findViewById(R.id.jinggao);
        // 在这里进行判断警报是否显示
        if (1 == 1) {
            jinggaoLayout.setVisibility(View.VISIBLE);
        } else {
            jinggaoLayout.setVisibility(View.GONE);
        }
        // 设置半圆进度条的进度
        SemicircleProgressView progressView = findViewById(R.id.semicircleProgressView);
        progressView.setProgress(86);


        initRecyclerView();
        setVariable();
        setVariableCity();
    }
    public void showRichTextForm(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ScrollView scrollView = new ScrollView(this);

        // 创建一个线性布局作为 ScrollView 的唯一直接子视图
        LinearLayout contentLayout = new LinearLayout(this);
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.setPadding(43, 24, 24, 24); // 设置内边距为 16dp

        // 创建标题文本，设置为加粗且 18sp
        TextView titleTextView = new TextView(this);
        titleTextView.setTextSize(20);
        titleTextView.setTypeface(null, android.graphics.Typeface.BOLD);
        titleTextView.setText("大雾黄色预警");
        contentLayout.addView(titleTextView);

        // 创建防御指南文本，16sp
        TextView guideTextView = new TextView(this);
        guideTextView.setTextSize(16);
        guideTextView.setText("含义：12 小时内可能出现能见度小于 500 米的雾，或者已经出现能见度小于 500 米、大于等于 200 米的雾并将持续。\n防御指南：\n" +
                "1.有关部门和单位按照职责做好防雾准备工作；\n2.机场、高速公路、轮渡码头等单位加强交通管理，保障安全；\n3.驾驶人员注意雾的变化，小心驾驶；\n4.户外活动注意安全。" +
                "1.有关部门和单位按照职责做好防雾准备工作；\n2.机场、高速公路、轮渡码头等单位加强交通管理，保障安全；\n3.驾驶人员注意雾的变化，小心驾驶；\n4.户外活动注意安全。" +
                "1.有关部门和单位按照职责做好防雾准备工作；\n2.机场、高速公路、轮渡码头等单位加强交通管理，保障安全；\n3.驾驶人员注意雾的变化，小心驾驶；\n4.户外活动注意安全。" +
                "1.有关部门和单位按照职责做好防雾准备工作；\n2.机场、高速公路、轮渡码头等单位加强交通管理，保障安全；\n3.驾驶人员注意雾的变化，小心驾驶；\n4.户外活动注意安全。");
        contentLayout.addView(guideTextView);
        scrollView.addView(contentLayout);


        // 创建圆角形状并设置浅蓝色背景
        GradientDrawable dialogBackground = new GradientDrawable();
        dialogBackground.setCornerRadius(16);
        dialogBackground.setColor(Color.parseColor("#E0F8FF"));

        builder.setView(scrollView);

        AlertDialog dialog = builder.create();


        // 设置淡入淡出动画
        dialog.getWindow().setWindowAnimations(android.R.style.DeviceDefault_ButtonBar);

        // 使用 dialog.getWindow() 的替代方法设置背景
        dialog.setOnShowListener(d -> {
            dialog.getWindow().setBackgroundDrawable(dialogBackground);
        });

        // 创建一个居中的灰色小字号标题文本
        TextView closeTitleTextView = new TextView(this);
        closeTitleTextView.setTextSize(16);
        closeTitleTextView.setTextColor(Color.GRAY);
        closeTitleTextView.setText("点击空白处或按返回键关闭");
        closeTitleTextView.setGravity(Gravity.CENTER);
        dialog.setCustomTitle(closeTitleTextView);

        dialog.show();

    }

    private long lastBackPressedTime = 0;

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBackPressedTime < 800) {
            super.onBackPressed();
            finishAffinity();
            return;
        } else {
            Toast toast = new Toast(this);
            View view = getLayoutInflater().inflate(R.layout.custom_toast, null);
            TextView textView = view.findViewById(R.id.toast_text);
            textView.setText("再按一次返回键退出应用");

            toast.setView(view);
            toast.setDuration(Toast.LENGTH_SHORT);

            // 设置半透明效果
            toast.getView().getBackground().setAlpha(128);

            toast.show();
            lastBackPressedTime = currentTime;
        }
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void setVariable() {
//        TextView next7dayBtn = findViewById(R.id.nextBtn1);
//        next7dayBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TommorowActivity.class)));
        try {
            TextView next7dayBtn = findViewById(R.id.nextBtn1);
            next7dayBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TommorowActivity.class)));
        } catch (Exception e) {
            Log.e("Error", "Error setting variable", e);
        }
    }

    private void setVariableCity() {
        try {
            ImageView nextcity = findViewById(R.id.nextCity);
            nextcity.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CityActivity.class)));
        } catch (Exception e) {
            Log.e("Error", "Error setting variable", e);
        }
    }

    private void initRecyclerView() {
        ArrayList<Hourly> items = new ArrayList<>();
        items.add(new Hourly("10 pm", 28, "cloudy"));
        items.add(new Hourly("11 pm", 2, "sunny"));
        items.add(new Hourly("12 pm", 0, "rainy"));
        items.add(new Hourly("1 am", 29, "sun"));
        items.add(new Hourly("2 am", 27, "windy"));

        recyclerView = findViewById(R.id.view1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapterHourly = new HourlyAdapter(items);
        recyclerView.setAdapter(adapterHourly);
    }
}