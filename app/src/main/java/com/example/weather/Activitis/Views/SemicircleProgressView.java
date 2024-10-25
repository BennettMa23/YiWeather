package com.example.weather.Activitis.Views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class SemicircleProgressView extends View {

    private int progress = 0;
    private int maxProgress = 500;
    private int arcColor = Color.YELLOW;
    private int backgroundColor = Color.GRAY;
    private TextPaint textPaint1;
    private TextPaint textPaint2;
    private int currentIndexValue = 0;

    private Paint arcPaint;
    private Paint backgroundPaint;
    private ValueAnimator indexAnimator;

    private ValueAnimator animator;

    public SemicircleProgressView(Context context) {
        this(context, null);
    }

    public SemicircleProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SemicircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaints();
    }

    private void initPaints() {
        arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(20);
        arcPaint.setStrokeCap(Paint.Cap.ROUND);  // 设置圆弧两端为圆角

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(backgroundColor);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(20);
        backgroundPaint.setStrokeCap(Paint.Cap.ROUND);  // 设置圆弧两端为圆角

        textPaint1 = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint1.setColor(Color.WHITE);
        textPaint1.setTextSize(23);

        textPaint2 = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint2.setColor(Color.WHITE);
        textPaint2.setTextSize(50);

        createGradient();  // 初始化渐变色
    }

    private void createGradient() {
        int[] colors = {Color.GREEN, Color.GREEN, Color.YELLOW, 0xFFFFA500, Color.RED, Color.MAGENTA, 0x8B4513};
        float[] positions = {0f, 0.2f, 0.36f, 0.45f, 0.62f, 0.8f, 1f};
        LinearGradient gradient = new LinearGradient(0, 0, getWidth(), 0, colors, positions, Shader.TileMode.CLAMP);
        arcPaint.setShader(gradient);
        // 添加日志输出以确认渐变设置成功
//        Log.d("SemicircleProgressView", "Gradient set successfully.");
    }

    public void setProgress(int progress) {
        if (progress < 0 || progress > maxProgress) {
            throw new IllegalArgumentException("Progress must be between 0 and maxProgress");
        }

        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }

        animator = ValueAnimator.ofInt(this.progress, progress);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedProgress = (int) animation.getAnimatedValue();
                SemicircleProgressView.this.progress = animatedProgress;
                invalidate();
            }
        });

        if (indexAnimator!= null && indexAnimator.isRunning()) {
            indexAnimator.cancel();
        }
        indexAnimator = ValueAnimator.ofInt(currentIndexValue, progress);
        indexAnimator.setDuration(2000);
        indexAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentIndexValue = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
        indexAnimator.start();
    }

    public int getProgress() {
        return progress;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        int padding = 10;

        int centerX = width / 2;
        int centerY = height / 2;
        int radius = Math.min(width - padding * 2, height - padding * 2) / 2;

        // 绘制背景半圆弧，逆时针旋转 45 度
        RectF backgroundRectF = new RectF(centerX - radius + padding, centerY - radius + padding,
                centerX + radius - padding, centerY + radius - padding);
        canvas.drawArc(backgroundRectF, 135, 270, false, backgroundPaint);

        // 绘制进度半圆弧，逆时针旋转 45 度
        RectF progressRectF = new RectF(centerX - radius + padding, centerY - radius + padding,
                centerX + radius - padding, centerY + radius - padding);
        float sweepAngle = (float) progress / maxProgress * 270;
        canvas.drawArc(progressRectF, 135, sweepAngle, false, arcPaint);


        // 绘制“空气质量”文本
        String text1 = "空气质量";
        float text1Width = textPaint1.measureText(text1);
        canvas.drawText(text1, centerX - text1Width / 2, centerY + 85, textPaint1);

        // 绘制“指数：[具体指数值]”文本
        String text2 = String.valueOf(currentIndexValue);
        float text2Width = textPaint2.measureText(text2);
        canvas.drawText(text2, centerX - text2Width / 2, centerY, textPaint2);

        // 重新创建渐变色
        createGradient();
    }
}