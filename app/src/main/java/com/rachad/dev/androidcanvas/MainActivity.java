package com.rachad.dev.androidcanvas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.Math;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener {
    private Canvas mCanvas;
    private final Paint mPaint = new Paint();
    private final Paint mPaintText = new Paint(Paint.UNDERLINE_TEXT_FLAG);
    private Bitmap mBitmap;
    private ImageView mImageView;
    private int mColorBackground;

    boolean clicked = false;

        float[][] balls = {};
        float [] coordinates;
       // GestureDetector gd = new GestureDetector(this,this);
        ImageView img = findViewById(R.id.myimageview);

        List<float[]> list = new ArrayList<float[]>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mColorBackground = ResourcesCompat.getColor(getResources(),
                R.color.colorBackground, null);
        mPaint.setColor(mColorBackground);
        mPaintText.setColor(
                ResourcesCompat.getColor(getResources(),
                        R.color.colorPrimaryDark, null)
        );
        mPaintText.setTextSize(70);
        mImageView = findViewById(R.id.myimageview);
    }
    public float[][] generate(int number,float w,float h){
        for(int i=0;i<number;i++){
            float[] bl = new float[5];
            float r = (float) (10 + Math.random() * (40 - 10));
            float x = (float) (r + Math.random() * (w - r));
            float y = (float) (r + Math.random() * (h - r));
            float vx = (float)  (6 + Math.random() * (20 - 6));
            float vy = (float)  (6 + Math.random() * (20 - 6));
            bl[0] = x;
            bl[1] = y;
            bl[2] = r;
            bl[3] = vx;
            bl[4] = vy;
            list.add(bl);
        }
        float[][] bls = new float[number][5];
        return list.toArray(bls);
    }

    public void connect(){
        for(int i1=0;i1<balls.length;i1++){
            for(int i2=0;i2<balls.length;i2++){
                float dx = balls[i1][0] - balls[i2][0];
                float dy = balls[i1][1] - balls[i2][1];
                float distance = (float) Math.sqrt(dx*dx + dy*dy);
                if(distance < 220){
                    mPaintText.setStrokeWidth(5);
                    mPaintText.setColor(
                            ResourcesCompat.getColor(getResources(),
                                    R.color.white, null)
                    );
                    mCanvas.drawLine(balls[i1][0], balls[i1][1], balls[i2][0], balls[i2][1], mPaintText);
                }
            }
        }
    }

    public void drawSomething(View view) {
        img.setOnTouchListener(this);
        if(clicked){return;}
        else{clicked=true;}
        balls = generate(40,view.getWidth(),view.getHeight());
        new CountDownTimer(10000000, 1) {

            public void onTick(long millisUntilFinished) {
                int vWidth = view.getWidth();
                int vHeight = view.getHeight();
                int halfWidth = vWidth / 2;
                int halfHeight = vHeight / 2;
                mBitmap = Bitmap.createBitmap(vWidth, vHeight, Bitmap.Config.ARGB_8888);
                mImageView.setImageBitmap(mBitmap);
                mCanvas = new Canvas(mBitmap);
                mCanvas.drawColor(mColorBackground);
           //     mCanvas.drawText(getString(R.string.keep_tapping), halfWidth, halfHeight, mPaintText);
                connect();
                for(int i=0;i<balls.length;i++) {
                    mPaintText.setColor(
                            ResourcesCompat.getColor(getResources(),
                                    R.color.purple_500, null)
                    );
                    mCanvas.drawCircle(balls[i][0], balls[i][1], balls[i][2], mPaintText);

                    if (balls[i][0] - balls[i][2] < 0 && balls[i][3] < 0) {
                        balls[i][3] *= -1;
                    }
                    if (balls[i][0] + balls[i][2] > vWidth && balls[i][3] > 0) {
                        balls[i][3] *= -1;
                    }
                    if (balls[i][1] - balls[i][2] < 0 && balls[i][4] < 0) {
                        balls[i][4] *= -1;
                    }
                    if (balls[i][1] + balls[i][2] > vHeight && balls[i][4] > 0) {
                        balls[i][4] *= -1;
                    }
                    balls[i][0] += balls[i][3];
                    balls[i][1] += balls[i][4];
                    view.invalidate();
                }
            }


            public void onFinish() {

            }
        }.start();

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch(action){
            case(MotionEvent.ACTION_DOWN):
                coordinates[0] = event.getX();
                coordinates[1] = event.getY();
                return true;
            case (MotionEvent.ACTION_UP):
                coordinates[0] = event.getX();
                coordinates[1] = event.getY();
                return true;
            case (MotionEvent.ACTION_MOVE):
                coordinates[0] = event.getX();
                coordinates[1] = event.getY();
                return true;
            case (MotionEvent.ACTION_CANCEL):
                coordinates[0] = event.getX();
                coordinates[1] = event.getY();
                return true;
            case (MotionEvent.ACTION_OUTSIDE):
                coordinates[0] = event.getX();
                coordinates[1] = event.getY();
                return true;

        }
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}