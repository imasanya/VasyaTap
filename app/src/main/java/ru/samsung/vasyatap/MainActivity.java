package ru.samsung.vasyatap;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;




public class MainActivity extends AppCompatActivity {
    public int screenWidth, screenHeight;
    int counter = 0;
    Handler handler;
    ConstraintLayout constraintLayout;
    TextView textView;
    ImageView imageVasya;
    TextView textTimer;
    Cat[] cats = new Cat[5];
    long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        constraintLayout = findViewById(R.id.main);
        textView = findViewById(R.id.textBOTTOM);
        imageVasya = findViewById(R.id.imageVasya);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        /*for (int i = 0; i < 5; i++) {
            cat[i] = new Cat(constraintLayout,)
        }*/
        cats[0] = new Cat(constraintLayout, 430, 1750, 200, 260);
        cats[1] = new Cat(constraintLayout, 700, 1666, 150, 400);
        cats[2] = new Cat(constraintLayout, 130, 250, 340, 260);
        cats[3] = new Cat(constraintLayout, 600, 200, 200, 275);
        cats[4] = new Cat(constraintLayout, 900, 300, 200, 130);

        imageVasya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                textView.setText("Вы погладили " + counter + ((counter % 10 > 1 && counter % 10 < 5) && counter / 10 % 10 != 1 ? " раза" : " раз"));
            }

        });

        long startTime = System.currentTimeMillis();
        handler = new Handler(Looper.getMainLooper());
        textTimer = findViewById(R.id.timer);

    }

    void moveCats() {
        for (int i = 0; i < 5; i++) {
            cats[i].move();
        }
    }

    void update() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                updateTime();
                moveCats();
                handler.postDelayed(this, 16);
            }
        };

    }

    void updateTime() {
        long currentTime = (System.currentTimeMillis() - startTime)/1000;
        textTimer.setText((currentTime - startTime) / 1000+ "");
    }

}


