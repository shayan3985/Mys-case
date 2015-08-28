package com.example.siamak.ebook5;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.siamak.ebook5.R;

/**
 * Created by shayan on 8/8/2015.
 */
public class page5 extends Activity {
    ImageButton mImageButton_left;
    ImageButton mImageButton_right;
    TextView first_text_page5;
    TextView second_text_page5;
    TextView third_text_page5;
    TextView fourth_text_page5;
    TextView fifth_text_page5;

    LinearLayout scrollView;
    ScrollView page5_scroll;

    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    static final int DRAW =3;
    int mode = NONE;
    float scale;
    private float maxFont;
    private float minFont;

    // Remember some things for zooming
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    ImageView imageView_j;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page5);
        Intent intent = getIntent();
        mImageButton_left = (ImageButton) findViewById(R.id.image_button_left_page5);
        mImageButton_right = (ImageButton) findViewById(R.id.image_button_right_page5);

        scrollView = (LinearLayout)findViewById(R.id.scroll_page5_id);
        first_text_page5= (TextView)findViewById(R.id.page5_text1);
        second_text_page5= (TextView)findViewById(R.id.page5_text2);
        third_text_page5= (TextView)findViewById(R.id.page5_text3);
        fourth_text_page5= (TextView)findViewById(R.id.page5_text4);
        fifth_text_page5= (TextView)findViewById(R.id.page5_text5);

        page5_scroll =(ScrollView)findViewById(R.id.page5_scroll);

        imageView_j = (ImageView)findViewById(R.id.page5_image1);
        imageView_j.setImageResource(R.drawable.image5);


        try {
            getActionBar().hide();
        }catch (Exception e){
            Log.d("oncreate",e.toString());
        }

        listener_setup();
    }

    public void listener_setup(){
        mImageButton_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        v.getBackground().setColorFilter(R.color.onTouch, PorterDuff.Mode.SRC_OVER);
                        v.invalidate();
                        break;
                    case MotionEvent.ACTION_UP:
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                }

                return false;
            }
        });


        mImageButton_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        v.getBackground().setColorFilter(R.color.onTouch, PorterDuff.Mode.SRC_OVER);
                        v.invalidate();
                        break;
                    case MotionEvent.ACTION_UP:
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                }
                return false;
            }
        });

        mImageButton_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });
        mImageButton_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(page5.this);
                builder.setTitle(getResources().getString(R.string.contactUs));
                builder.setMessage(getResources().getString(R.string.msg));
                final EditText editText= new EditText(page5.this);
                builder.setView(editText);

                builder.setPositiveButton("ارسال", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Ebook Feedback");
                        intent.putExtra(Intent.EXTRA_TEXT, editText.getText());
                        intent.setData(Uri.parse("mailto:shayanshs94@gmail.com")); // or just "mailto:" for blank
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                        startActivity(intent);
                        dialog.dismiss();

                    }
                });
                builder.setNegativeButton("لغو", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();

            }
        });

        scrollView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float final_scale = 0;
                Log.e("------------->", "" + event.getPointerCount());
                if (event.getPointerCount()==2)
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                   /* case MotionEvent.ACTION_DOWN:
                        savedMatrix.set(matrix);
                        start.set(event.getX(), event.getY());
                        Log.d("onTouch", "mode=DRAG");
                        mode = DRAG;
                        break;*/
                        case MotionEvent.ACTION_POINTER_DOWN:
                            if (event.getPointerCount()==2) {
                                page5_scroll.setEnabled(false);


                                oldDist = spacing(event);
                                Log.d("onTouch", "oldDist=" + oldDist);
                                if (oldDist > 10f) {
                                    savedMatrix.set(matrix);
                                    midPoint(mid, event);
                                    mode = ZOOM;
                                    Log.d("onTouch", "mode=ZOOM");
                                }
                            }
                            break;
                        //case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_POINTER_UP:
                            Log.d("font=",first_text_page5.getTextSize()+"");
                            page5_scroll.setEnabled(true);
                            mode = NONE;
                            Log.d("onTouch", "mode=NONE");
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (event.getPointerCount()==2) {
                                if (mode == DRAW) {
                                    onTouchEvent(event);
                                }
                                if (mode == DRAG) {
                                    ///code for draging..
                                } else if (mode == ZOOM) {
                                    float newDist = spacing(event);
                                    Log.d("onTouch", "newDist=" + newDist);
                                    if (newDist > 50) {
                                        matrix.set(savedMatrix);
                                        scale = newDist / oldDist;

                                        Log.d("scale=", scale + "");
                                        // limit zoom
                                        final_scale = scale * first_text_page5.getTextSize();
                                        if (final_scale > maxFont) {
                                            final_scale = maxFont;
                                        }
                                        if (final_scale < minFont) {
                                            final_scale = minFont;
                                        }

                                        if (scale > 10)
                                            scale=10;
                                        if (scale<0.1)
                                            scale=(float)0.1;

                                        //matrix.postScale(scale, scale, mid.x, mid.y);
                                    }
                                }
                            }

                            break;


                    }

                if (final_scale>minFont && final_scale<maxFont) {
                    Log.e("final scale", final_scale + "");
                    Log.e("min"+minFont, maxFont + "max");

                    first_text_page5.setTextSize(TypedValue.COMPLEX_UNIT_PX,final_scale);
                    second_text_page5.setTextSize(TypedValue.COMPLEX_UNIT_PX,final_scale);
                    third_text_page5.setTextSize(TypedValue.COMPLEX_UNIT_PX,final_scale);
                    fourth_text_page5.setTextSize(TypedValue.COMPLEX_UNIT_PX,final_scale);
                    fifth_text_page5.setTextSize(TypedValue.COMPLEX_UNIT_PX,final_scale);

                }else {
                    Log.d("overflow",scale+"");
                    scale=1;
                }
                return false;
            }
        });



    }



    //*******************Determine the space between the first two fingers
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }

    //************* Calculate the mid point of the first two fingers
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){


            init();
        }
    }

    private void init() {
        maxFont = 1000;
        minFont = 50;

    }

    @Override
    protected void onStart() {
        super.onStart();
        //overridePendingTransition(R.animabc_slide_in_top,R.anim.abc_slide_out_bottom);

    }
}
