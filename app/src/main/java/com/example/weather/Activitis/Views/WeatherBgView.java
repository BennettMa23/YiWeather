package com.example.weather.Activitis.Views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.rainy.weahter_bg_plug.entity.MeteorParam;
import com.rainy.weahter_bg_plug.entity.RainSnowParams;
import com.rainy.weahter_bg_plug.entity.StarParam;
import com.rainy.weahter_bg_plug.entity.ThunderParams;
import com.rainy.weahter_bg_plug.utils.Logger;
import com.rainy.weahter_bg_plug.utils.Unit;
import com.rainy.weahter_bg_plug.utils.WeatherUtil;
import com.rainy.weahter_bg_plug.utils.WeatherUtil.WeatherType;

import java.util.ArrayList;
import java.util.List;

import static android.animation.ValueAnimator.REVERSE;
import static android.util.TypedValue.COMPLEX_UNIT_DIP;
import static android.util.TypedValue.COMPLEX_UNIT_PX;
import com.rainy.weahter_bg_plug.WeatherBg;

public class WeatherBgWithMovingClouds extends WeatherBg {

    private float cloudX = 0;
    private float cloudSpeed = 2;

    public WeatherBgWithMovingClouds(Context context, String weatherType) {
        super(context, weatherType);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 更新云层位置
        updateCloudPosition();
        super.onDraw(canvas);
    }

    private void updateCloudPosition() {
        cloudX += cloudSpeed;
        if (cloudX > getWidth()) {
            cloudX = -cloudBitmap.getWidth();
        }
    }

    // 重写绘制云层背景的方法以应用云层位置
    @Override
    private void drawCloudBg(Canvas canvas) {
        switch (weatherType) {
            case WeatherType.sunny:
                drawSunny(canvas);
                break;
            case WeatherType.cloudy:
                drawCloudy(canvas);
                break;
            case WeatherType.cloudyNight:
                drawCloudyNight(canvas);
                break;
            case WeatherType.overcast:
                drawOvercast(canvas);
                break;

            case WeatherType.lightRainy:
            case WeatherType.middleRainy:
            case WeatherType.heavyRainy:
            case WeatherType.thunder:
                drawRainCloudBg(canvas);
                break;

            case WeatherType.hazy:
                drawHazy(canvas);
                break;
            case WeatherType.foggy:
                drawFoggy(canvas);
                break;

            case WeatherType.lightSnow:
            case WeatherType.middleSnow:
            case WeatherType.heavySnow:
                drawSnowCloudBg(canvas);
                break;

            case WeatherType.dusty:
                drawDusty(canvas);
                break;
            default:
                break;
        }
    }

    // 重写绘制雨天云层背景方法以应用云层位置
    @Override
    private void drawRainCloudBg(Canvas canvas) {
        canvas.save();
        if (weatherType.equals(WeatherType.lightRainy)) rainyCloudIdentity = rainCloudFilters[0];
        else if (weatherType.equals(WeatherType.middleRainy)) rainyCloudIdentity = rainCloudFilters[1];
        else rainyCloudIdentity = rainCloudFilters[2];

        cloudPaint.setColorFilter(rainyCloudIdentity);
        float widthRatio = getWidth() / 392.0f;
        float scale = widthRatio * 0.8f;
        canvas.scale(scale, scale);
        canvas.drawBitmap(cloudBitmap, cloudX - 380, (float) -150, cloudPaint);
        canvas.drawBitmap(cloudBitmap, cloudX, (float) -60, cloudPaint);
        canvas.drawBitmap(cloudBitmap, cloudX, (float) 60, cloudPaint);

        canvas.restore();
    }

    // 重写绘制雪云层背景方法以应用云层位置
    @Override
    private void drawSnowCloudBg(Canvas canvas) {
        canvas.save();
        if (weatherType.equals(WeatherType.lightSnow)) snowCloudIdentity = snowCloudFilters[0];
        else if (weatherType.equals(WeatherType.middleSnow)) snowCloudIdentity = snowCloudFilters[1];
        else snowCloudIdentity = snowCloudFilters[2];

        cloudPaint.setColorFilter(snowCloudIdentity);
        float widthRatio = getWidth() / 392.0f;
        float scale = widthRatio * 0.8f;
        canvas.scale(scale, scale);
        canvas.drawBitmap(cloudBitmap, cloudX - 380, (float) -150, cloudPaint);
        canvas.drawBitmap(cloudBitmap, cloudX, (float) -170, cloudPaint);

        canvas.restore();
    }

    // 重写绘制云层浮沉方法以应用云层位置
    @Override
    private void drawDusty(Canvas canvas) {
        canvas.save();
        if (dustyIdentity == null) {
            dustyIdentity = new ColorMatrixColorFilter(new float[]{
                    0.62f, 0, 0, 0, 0,
                    0, 0.55f, 0, 0, 0,
                    0, 0, 0.45f, 0, 0,
                    0, 0, 0, 1, 0});
        }

        cloudPaint.setColorFilter(dustyIdentity);
        float widthRatio = getWidth() / 392.0f;
        float scale = widthRatio * 2.0f;
        canvas.scale(scale, scale);
        canvas.drawBitmap(cloudBitmap, cloudX - cloudBitmap.getWidth() * 0.5f, -200, cloudPaint);

        canvas.restore();
    }
}