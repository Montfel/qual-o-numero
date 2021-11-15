package com.montfel.qualeonumero.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.montfel.qualeonumero.R;

public class LedSeteSegmentos extends View {
    private int color;
    private int width = 100;
    private int height = 400;
    private Paint paint;

    public LedSeteSegmentos(Context context) {
        super(context);
    }

    public LedSeteSegmentos(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupAttributes(attrs);
        setupPaint();
    }

    public LedSeteSegmentos(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LedSeteSegmentos(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
                            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void setupAttributes(@Nullable AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs,
                R.styleable.LedSeteSegmentos, 0, 0);
        try {
            color = a.getColor(R.styleable.LedSeteSegmentos_android_background,
                    ContextCompat.getColor(getContext(), R.color.rosa_claro));
        } finally {
            a.recycle();
        }
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        invalidate();
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, width, height, paint);
    }

    private void setupPaint() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
