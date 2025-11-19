package ru.samsung.vasyatap;

import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import static ru.samsung.vasyatap.MainActivity.*;

import java.util.Random;

public class Cat {
    ImageView img;
    int x;
    int y;
    int width;
    int height;
    int speed;
    int stepX;
    int stepY;


    public Cat(ConstraintLayout layout,int x, int y,  int width, int height, int speed){
        this.speed = speed;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        Random rnd = new Random();

        img = new ImageView(layout.getContext());
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(width, height);
        img.setLayoutParams(params);
        img.setImageResource(R.drawable.cat_funny);
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        layout.addView(img);
        img.setX(x);
        img.setY(y);
        stepX = speed;
        stepY = speed;
    }

    void move(){
        x+=stepX;
        y+=stepY;
        if (x > screenWidth - width || x < 0){
            stepX =-stepX;
        }

        if (y >screenHeight - height|| y < 0){
            stepY =-stepY;
        }
        img.setX(x);
        img.setY(y);

    }

}
