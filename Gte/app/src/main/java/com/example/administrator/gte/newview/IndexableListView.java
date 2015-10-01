package com.example.administrator.gte.newview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ListAdapter;
import android.widget.ListView;




/**
 * Created by J on 15/10/1.
 */
public class IndexableListView extends ListView {

    private boolean mIsfastScrollEnabled = false;
    private IndexScroller mScroller = null;
    private GestureDetector gestureDetector = null;

    public IndexableListView(Context context) {
        super(context);
    }

    public IndexableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IndexableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean isFastScrollEnable() {
        return mIsfastScrollEnabled;
    }

    @Override
    public void setFastScrollEnabled(boolean enabled) {
        mIsfastScrollEnabled = true;
        if (mIsfastScrollEnabled) {
            if (mScroller == null) {
                mScroller = new IndexScroller(getContext(), this);

            } else {
                if (mScroller != null) {
                    mScroller.hide();
                }
            }
        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (mScroller != null) {
            mScroller.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mScroller != null && mScroller.onTouchEvent(ev)) {
            return true;
        }
        if (gestureDetector == null) {
            gestureDetector = new GestureDetector(getContext(), new
                    GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public boolean onFling(MotionEvent e1,
                                               MotionEvent e2,
                                               float velocityX,
                                               float velocityY) {
                            mScroller.show();
                            return super.onFling(e1, e2, velocityX, velocityY);
                        }
                    });
        }
        gestureDetector.onTouchEvent(ev);
        return super.onTouchEvent(ev);
    }

    public  void setAdapter(ListAdapter adapter){

        super.setAdapter(adapter);
        if(mScroller!=null){
            mScroller.setAdapter(adapter);
        }

    }

    protected  void onSizeChanged(int w,int h,int oldw,int oldh){

        super.onSizeChanged(w,h,oldw,oldh);
        if(mScroller!=null)  {
            mScroller.onSizeChanged(w,h,oldw,oldh);

        }
    }


}
