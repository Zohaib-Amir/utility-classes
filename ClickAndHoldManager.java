
/*
       Manages a view's touch and click listener at the same time
       to detect regular click, long click and long click hold
 */

import android.view.MotionEvent;
import android.view.View;

public final class ClickAndHoldManager implements View.OnClickListener, View.OnTouchListener, View.OnLongClickListener {

    private boolean pressed = false;

    private View.OnClickListener clickListener;
    private HoldListener holdListener;

    public ClickAndHoldManager(View view){

        view.setOnTouchListener(this);
        view.setOnLongClickListener(this);
        view.setOnClickListener(this);

    }

    public ClickAndHoldManager setClickCallback(View.OnClickListener listener){
        this.clickListener = listener;
        return this;
    }

    public ClickAndHoldManager setHoldCallback(HoldListener listener){
        this.holdListener = listener;
        return this;
    }



    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
  //      view.onTouchEvent(motionEvent);
        // We're only interested in when the button is released.
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            // We're only interested in anything if our speak button is currently pressed.
            if (pressed) {
                // Do something when the button is released.
                if(holdListener != null){
                    holdListener.holdEnded();
                }
                pressed = false;
            }
        }
        return false;
    }


    @Override
    public void onClick(View view) {
        if(clickListener != null){
            clickListener.onClick(view);
        }
    }


    @Override
    public boolean onLongClick(View view) {
        pressed = true;
        if(holdListener != null){
            holdListener.holdStarted();
        }
        return true;
    }


    public interface HoldListener{
        public void holdStarted();
        public void holdEnded();
    }


}
