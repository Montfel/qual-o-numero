package com.montfel.qualeonumero.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.montfel.qualeonumero.R;

public class LedSeteSegmentos extends View {
    private static final int x = 0;
    private static final int y = 0;
    private static final int width = 50;
    private static final int height = 200;
    private Paint paint_top, paint_top_left, paint_top_right, paint_middle, paint_bottom_left,
                    paint_bottom_right, paint_bottom;
    private Rect top, top_left, top_right, middle, bottom_left, bottom_right, bottom;

    public LedSeteSegmentos(Context context) {
        super(context);
    }

    public LedSeteSegmentos(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public LedSeteSegmentos(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public LedSeteSegmentos(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
                            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet set) {
        paint_top = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_top_left = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_top_right = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_middle = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_bottom_left = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_bottom_right = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_bottom = new Paint(Paint.ANTI_ALIAS_FLAG);

        top = new Rect(x + width, y, x + width + height, y + width);
        top_left = new Rect(x, y, x + width, y + height);
        top_right = new Rect(x + width + height, y, x + (2 * width) + height, y + height);
        middle = new Rect(x + width, y + height - (width/2), x + width + height, y + height + (width/2));
        bottom_left = new Rect(x, y + height, x + width, y + (2 * height));
        bottom_right = new Rect(x + width + height, y + height, x + (2 * width) + height, y + (2 * height));
        bottom = new Rect(x + width, y + (2 * height) - width, x + width + height, y + (2 * height));
    }

    private void zeraCores() {
        paint_top.setColor(ContextCompat.getColor(getContext(), R.color.rosa_claro));
        paint_top_left.setColor(ContextCompat.getColor(getContext(), R.color.rosa_claro));
        paint_top_right.setColor(ContextCompat.getColor(getContext(), R.color.rosa_claro));
        paint_middle.setColor(ContextCompat.getColor(getContext(), R.color.rosa_claro));
        paint_bottom_left.setColor(ContextCompat.getColor(getContext(), R.color.rosa_claro));
        paint_bottom_right.setColor(ContextCompat.getColor(getContext(), R.color.rosa_claro));
        paint_bottom.setColor(ContextCompat.getColor(getContext(), R.color.rosa_claro));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(top, paint_top);
        canvas.drawRect(top_left, paint_top_left);
        canvas.drawRect(top_right, paint_top_right);
        canvas.drawRect(middle, paint_middle);
        canvas.drawRect(bottom_left, paint_bottom_left);
        canvas.drawRect(bottom_right, paint_bottom_right);
        canvas.drawRect(bottom, paint_bottom);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }

    public void switchColor(String numero) {
        zeraCores();

        switch (numero) {
            case "1":
                paint_top.setColor(ContextCompat.getColor(getContext(), R.color.cinza_claro));
                paint_top_left.setColor(ContextCompat.getColor(getContext(), R.color.cinza_claro));
                paint_middle.setColor(ContextCompat.getColor(getContext(), R.color.cinza_claro));
                paint_bottom_left.setColor(ContextCompat.getColor(getContext(), R.color.cinza_claro));
                paint_bottom.setColor(ContextCompat.getColor(getContext(), R.color.cinza_claro));
                break;
            case "2":
                paint_top_left.setColor(ContextCompat.getColor(getContext(), R.color.cinza_claro));
                paint_bottom_right.setColor(ContextCompat.getColor(getContext(), R.color.cinza_claro));
                break;
            case "3":
                paint_top_left.setColor(ContextCompat.getColor(getContext(), R.color.cinza_claro));
                paint_bottom_left.setColor(ContextCompat.getColor(getContext(), R.color.cinza_claro));
                break;
            case "4":
                paint_top.setColor(ContextCompat.getColor(getContext(), R.color.cinza_claro));
                paint_bottom_left.setColor(ContextCompat.getColor(getContext(), R.color.cinza_claro));
                paint_bottom.setColor(ContextCompat.getColor(getContext(), R.color.cinza_claro));
                break;
            case "5":
                paint_top_right.setColor(ContextCompat.getColor(getContext(), R.color.cinza_claro));
                paint_bottom_left.setColor(ContextCompat.getColor(getContext(), R.color.cinza_claro));
                break;
            case "6":
                paint_top_right.setColor(ContextCompat.getColor(getContext(), R.color.cinza_claro));
                break;
            case "7":
                paint_top_left.setColor(ContextCompat.getColor(getContext(), R.color.cinza_claro));
                paint_middle.setColor(ContextCompat.getColor(getContext(), R.color.cinza_claro));
                paint_bottom_left.setColor(ContextCompat.getColor(getContext(), R.color.cinza_claro));
                paint_bottom.setColor(ContextCompat.getColor(getContext(), R.color.cinza_claro));
                break;
            case "8":
                break;
            case "9":
                paint_bottom_left.setColor(ContextCompat.getColor(getContext(), R.color.cinza_claro));
                break;
            case "0":
                paint_middle.setColor(ContextCompat.getColor(getContext(), R.color.cinza_claro));
                break;
        }
        invalidate();
        requestLayout();
    }
}