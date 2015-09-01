package com.example.siamak.ebook5;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import ir.adad.Adad;

public class MainActivity extends Activity {

    ImageButton mImageButton_left;
    ImageButton mImageButton_right;
    ListView mListView;
    CustomAdapter customAdapter;
    LinearLayout linearLayout;
    OpenNewPage listener;
    ImageView imageView;
    Adad adad;

    @Override
    protected void onStart() {
        super.onStart();
        //overridePendingTransition(R.anim.abc_slide_in_top,R.anim.abc_slide_out_bottom);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageButton_left = (ImageButton) findViewById(R.id.image_button_left);
        mImageButton_right = (ImageButton) findViewById(R.id.image_button_right);
        linearLayout = (LinearLayout) findViewById(R.id.layout_list);
        mListView = (ListView)findViewById(R.id.list_index);
        imageView = (ImageView)findViewById(R.id.background);
        imageView.setImageResource(R.drawable.backg);

        ArrayList<String> index = new ArrayList<String>();
        index.addAll(Arrays.asList(getResources().getStringArray(R.array.index_list)));
        listener = new OpenNewPage() {
            @Override
            public void Page_requsted(int pos) {
                Request_Handeler(pos);
            }
        };
        //set enable for ads
        adad.setDisabled(true);




        try {

            customAdapter =new CustomAdapter(getApplicationContext(),index,listener);
            mListView.setAdapter(customAdapter);
            getActionBar().hide();

        }catch (Exception e){
            Log.d("LIST ADAPTER", e.toString());
        }

        linearLayout.bringToFront();
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

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getResources().getString(R.string.contactUs));
                builder.setMessage(getResources().getString(R.string.msg));
                final EditText editText= new EditText(MainActivity.this);
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
        mImageButton_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setData(Uri.parse("bazaar://details?id=" + "com.example.siamak.ebook5"));
                intent.setPackage("com.farsitel.bazaar");
                startActivity(intent);

            }
        });
    }


    public void Request_Handeler(int pos){

                Intent intent = new Intent(MainActivity.this, page1.class);
                intent.putExtra("pagenumber",pos);//start  with 0
                startActivity(intent);


    }


}
