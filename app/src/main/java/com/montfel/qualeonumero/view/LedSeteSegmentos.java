package com.montfel.qualeonumero.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.montfel.qualeonumero.R;

public class LedSeteSegmentos extends View {
    private int color;
    private final int x = 50;
    private final int y = 50;
    private final int width = 100;
    private final int height = 400;
    private Paint paint;
    private Rect top = new Rect(x + width, y, x + width + height, y + width);
    private Rect top_left = new Rect(x, y, x + width, y + height);
    private Rect top_right = new Rect(x + width + height, y, x + (2 * width) + height, y + height);
    private Rect middle = new Rect(x + width, y + height - (width/2), x + width + height, y + height + (width/2));
    private Rect bottom_left = new Rect(x, y + height, x + width, y + (2 * height));
    private Rect bottom_right = new Rect(x + width + height, y + height, x + (2 * width) + height, y + (2 * height));
    private Rect bottom = new Rect(x + width, y + (2 * height) - width, x + width + height, y + (2 * height));

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
        canvas.drawRect(top, paint);
        canvas.drawRect(top_left, paint);
        canvas.drawRect(top_right, paint);
        canvas.drawRect(middle, paint);
        canvas.drawRect(bottom_left, paint);
        canvas.drawRect(bottom_right, paint);
        canvas.drawRect(bottom, paint);
    }

    private void setupPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


}
