package com.example.siamak.ebook5;

import android.content.Context;

import android.content.Intent;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;


import com.example.siamak.ebook5.R;

import java.util.ArrayList;



public class CustomAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<String> mList_Content;
    LayoutInflater inflater;
    OpenNewPage listener;

    public CustomAdapter(Context context , ArrayList<String> myList_Content, OpenNewPage openNewPage){
        mContext=context;
        mList_Content = myList_Content;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listener = openNewPage;
    }

    @Override
    public int getCount() {
        return mList_Content.size();
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View vi = convertView;
        try {
            if (vi == null)
                vi = inflater.inflate(R.layout.list_item, parent, false);
            final TextView textView = (TextView) vi.findViewById(R.id.list_item_text);
            textView.setText(mList_Content.get(position));

                textView.setTextSize(TypedValue.COMPLEX_UNIT_PT,10);

            //parent.setBackground(mContext.getResources().getDrawable(R.drawable.menu_on_release));



            textView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Log.i("tocuh",position+"");

                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                        case MotionEvent.ACTION_HOVER_MOVE:
                        case MotionEvent.ACTION_MOVE:
                            v.setBackground(mContext.getResources().getDrawable(R.drawable.menu_on_touch));

                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                        case MotionEvent.ACTION_HOVER_ENTER:
                        case MotionEvent.ACTION_HOVER_EXIT:


                            v.setBackground(mContext.getResources().getDrawable(R.drawable.menu_items));

                            break;

                    }
                    return false;
                }
            });


            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   listener.Page_requsted(position);
                }
            });

        }catch (Exception e){
            Log.d("CustomAdaptor getView",e.toString());
        }
        return vi;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return mList_Content.get(position);
    }
}
