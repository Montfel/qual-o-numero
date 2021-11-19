package com.montfel.qualeonumero.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.montfel.qualeonumero.R;

public class LedSeteSegmentos extends View {
    private int width = 20;
    private int height = this.width * 6;
    private int colorEnable = ContextCompat.getColor(getContext(), R.color.rosa_claro);
    private final int colorDisable = ContextCompat.getColor(getContext(), R.color.cinza_claro);
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

        configuraTamanho();
    }

    private void configuraTamanho() {
        top = new Rect(width, 0, width + height, width);
        top_left = new Rect(0, 0, width, height);
        top_right = new Rect(width + height, 0, (2 * width) + height, height);
        middle = new Rect(width, height - (width/2), width + height, height + (width/2));
        bottom_left = new Rect(0, height, width, 2 * height);
        bottom_right = new Rect(width + height, height, (2 * width) + height, 2 * height);
        bottom = new Rect(width, (2 * height) - width, width + height, (2 * height));
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int minw = (2 * width) + height + getPaddingLeft() + getPaddingRight();
        int w = resolveSizeAndState(minw, widthMeasureSpec, 0);

        int minh = (2 * height) + getPaddingBottom() + getPaddingTop();
        int h = resolveSizeAndState(minh, heightMeasureSpec, 0);

        setMeasuredDimension(w, h);
    }

    public void switchColor(String numero) {

        zeraCores();

        switch (numero) {
            case "0":
                paint_middle.setColor(colorDisable);
                break;
            case "1":
                paint_top.setColor(colorDisable);
                paint_top_left.setColor(colorDisable);
                paint_middle.setColor(colorDisable);
                paint_bottom_left.setColor(colorDisable);
                paint_bottom.setColor(colorDisable);
                break;
            case "2":
                paint_top_left.setColor(colorDisable);
                paint_bottom_right.setColor(colorDisable);
                break;
            case "3":
                paint_top_left.setColor(colorDisable);
                paint_bottom_left.setColor(colorDisable);
                break;
            case "4":
                paint_top.setColor(colorDisable);
                paint_bottom_left.setColor(colorDisable);
                paint_bottom.setColor(colorDisable);
                break;
            case "5":
                paint_top_right.setColor(colorDisable);
                paint_bottom_left.setColor(colorDisable);
                break;
            case "6":
                paint_top_right.setColor(colorDisable);
                break;
            case "7":
                paint_top_left.setColor(colorDisable);
                paint_middle.setColor(colorDisable);
                paint_bottom_left.setColor(colorDisable);
                paint_bottom.setColor(colorDisable);
                break;
            case "8":
                break;
            case "9":
                paint_bottom_left.setColor(colorDisable);
                break;
        }
        postInvalidate();
        requestLayout();
    }

    private void zeraCores() {
        paint_top.setColor(colorEnable);
        paint_top_left.setColor(colorEnable);
        paint_top_right.setColor(colorEnable);
        paint_middle.setColor(colorEnable);
        paint_bottom_left.setColor(colorEnable);
        paint_bottom_right.setColor(colorEnable);
        paint_bottom.setColor(colorEnable);
    }

    public void setSize(int width) {
        this.width = width;
        this.height = width * 6;
        configuraTamanho();
        postInvalidate();
        requestLayout();
    }

    public void setColor(int color) {
        paint_top.setColor(paint_top.getColor() == colorEnable ? color : colorDisable);
        paint_top_left.setColor(paint_top_left.getColor() == colorEnable ? color : colorDisable);
        paint_top_right.setColor(paint_top_right.getColor() == colorEnable ? color : colorDisable);
        paint_middle.setColor(paint_middle.getColor() == colorEnable ? color : colorDisable);
        paint_bottom_left.setColor(paint_bottom_left.getColor() == colorEnable ? color : colorDisable);
        paint_bottom_right.setColor(paint_bottom_right.getColor() == colorEnable ? color : colorDisable);
        paint_bottom.setColor(paint_bottom.getColor() == colorEnable ? color : colorDisable);
        this.colorEnable = color;
        postInvalidate();
        requestLayout();
    }
}