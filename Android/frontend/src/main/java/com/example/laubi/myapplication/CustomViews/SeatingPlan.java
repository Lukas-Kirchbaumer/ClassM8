package com.example.laubi.myapplication.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.example.backend.Dto.M8;

import java.util.LinkedHashMap;
import java.util.Map;


public class SeatingPlan extends View {

    private LinkedHashMap<M8,Rect> rectangles;
    private Paint paint;

    public SeatingPlan(Context context) {
        super(context);
        init(null, 0);
    }

    public SeatingPlan(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public SeatingPlan(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        rectangles = new LinkedHashMap<>();
        paint = new Paint();
        paint.setColor(Color.LTGRAY);
    }

    public void addItem(M8 mate, Rect rectangle){
        rectangles.put(mate, rectangle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int textSize = 20;
        Paint tempTextPaint = new Paint();
        tempTextPaint.setAntiAlias(true);
        tempTextPaint.setStyle(Paint.Style.FILL);

        tempTextPaint.setColor(Color.BLACK);
        tempTextPaint.setTextSize(textSize);


        for (Map.Entry<M8, Rect> e: this.rectangles.entrySet()) {
            System.out.println(e.getValue().width());
            System.out.println(e.getValue().height());
            canvas.drawRect(e.getValue(),paint);
            String text = e.getKey().getLastname();
            float textWidth = tempTextPaint.measureText(text);
            canvas.drawText(text, e.getValue().centerX()-(textWidth/2), e.getValue().centerY()+(textSize/2), tempTextPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int) event.getY();
        for (Map.Entry<M8, Rect> e: this.rectangles.entrySet()) {
            if(e.getValue().contains(x,y)){
                System.out.println(e.getValue() + " klicked");
                //TODO: open intent with m8
            }
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int maxbottom = 0;
        for (Map.Entry<M8, Rect> e : this.rectangles.entrySet()) {
            if (e.getValue().bottom > maxbottom) {
                maxbottom = e.getValue().bottom;
            }
        }
        maxbottom = maxbottom + 75;

        int maxY = 0;
        for (Map.Entry<M8, Rect> e : this.rectangles.entrySet()) {
            if (e.getValue().right > maxY) {
                maxY = e.getValue().right;
            }
        }
        maxY = maxY + 75;

        this.setMeasuredDimension(maxY, maxbottom);
    }
}
