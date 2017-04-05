package com.example.laubi.myapplication.Activities;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.app.Activity;
import android.view.Display;
import android.view.MotionEvent;

import com.example.backend.Database.Database;
import com.example.backend.Dto.M8;
import com.example.backend.Dto.Position;
import com.example.backend.Services.PositionService;
import com.example.laubi.myapplication.CustomViews.SeatingPlan;
import com.example.laubi.myapplication.R;

import java.util.ArrayList;

public class SittingPlanActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitting_plan);
        Database.getInstance().setPositions(PositionService.getInstance().getPositions());

        System.out.println(Database.getInstance().getPositions());
        setTitle("Sitting-Plan");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        float widthOfDisplay = size.x;
        System.out.println(widthOfDisplay/6);
        float factor =  ((widthOfDisplay/(6*6) * (((float)6/(float)100))));

        float avgWidthOfDesks = (float)100 * factor;
        float avgHeightOfDesks = avgWidthOfDesks/(float)2;

        System.out.println("Factor: " + factor);

        SeatingPlan sp = (SeatingPlan) this.findViewById(R.id.sittingplanview);

        System.out.println(avgWidthOfDesks);
        System.out.println(avgHeightOfDesks);

        for(Position p: Database.getInstance().getPositions()){
            try {
                int left = (int) Math.round(p.getCoordinate().getX() * factor);
                int top = (int) Math.round(p.getCoordinate().getY() * factor);
                int bottom = top + Math.round(avgHeightOfDesks);
                int right = left + Math.round(avgWidthOfDesks);

                sp.addItem(p.getOwner().toM8(), new Rect(left, top, right, bottom));
            }catch ( Exception e){
                System.out.println("nothing");
            }
        }
        System.out.println(widthOfDisplay);
        System.out.println(sp.getWidth());
    }
}
