package slidingmenu.dreamfly.org.myapplication.custom;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import slidingmenu.dreamfly.org.myapplication.R;

/**
 * Created by asus on 2015/11/6.
 */
public class ScaleSlidingMenu extends HorizontalScrollView {

    private int mMenuWidth;
    private int mContentWidth;
    private int mScreenWidth;
    private int mMaxMoveDis;
    private int mHalfMenuHeight;

    private boolean isOpen;
    private boolean once;

    private LinearLayout mMenuLayout;
    private LinearLayout mContentLayout;
    private Button btnSlidingMenuShowMenu;


    public ScaleSlidingMenu(Context context, AttributeSet attrs){
        super(context,attrs);
        WindowManager wm=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        this.mScreenWidth=outMetrics.widthPixels;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        if(!once) {
            LinearLayout wrapper=(LinearLayout)this.getChildAt(0);
            if (wrapper != null) {
                this.mMenuLayout = (LinearLayout) wrapper.getChildAt(0);
                this.mContentLayout = (LinearLayout) wrapper.getChildAt(1);
                ViewGroup.LayoutParams layoutParamsMenu = this.mMenuLayout.getLayoutParams();
                ViewGroup.LayoutParams layoutParamsContent = this.mContentLayout.getLayoutParams();
                this.mMenuWidth = this.mScreenWidth - this.mScreenWidth / 3;
                this.mContentWidth = this.mScreenWidth;
                layoutParamsMenu.width = this.mMenuWidth;
                layoutParamsContent.width = this.mContentWidth;
                mMaxMoveDis = this.mMenuWidth / 2;
                mHalfMenuHeight=this.mScreenWidth/6;
                btnSlidingMenuShowMenu=(Button)this.findViewById(R.id.btn_mainactivity_showmenu);
                btnSlidingMenuShowMenu.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        controlMenu();
                    }
                });
            }
        }
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b){
        super.onLayout(changed,l,t,r,b);
        if(changed){
            this.smoothScrollTo(this.mMenuWidth,0);
            isOpen=false;
            once=true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev){
        switch (ev.getAction()){
            case MotionEvent.ACTION_MOVE:
                int scrollX=this.getScrollX();
                float scaleRadio = 1 - (scrollX / (float) mMenuWidth);
                this.scaleContentLayout(scaleRadio);
                this.alphaMenu(scaleRadio);
                break;
            case MotionEvent.ACTION_UP:
                /**
                 * 理解getScrollX的含义，就是当前滑动的X的的数值
                 * 屏幕左边为正，右边为负数
                 */
                if(this.getScrollX()<this.mMaxMoveDis){
                    this.smoothScrollTo(0,0);
                    this.scaleContentLayout(1.0f);
                    isOpen=true;
                }else{
                    this.smoothScrollTo(mMenuWidth,0);
                    this.scaleContentLayout(0.0f);
                    isOpen=false;
                }
                return(true);
        }
        return (super.onTouchEvent(ev));
    }

    public void showMenu(){
        if(!this.isOpen) {
            this.smoothScrollTo(0, 0);
            this.alphaMenu(1.0f);
            this.scaleContentLayout(1.0f);
        }
    }

    public void closeMenu(){
        if(this.isOpen){
            this.smoothScrollTo(this.mMenuWidth,0);
            this.alphaMenu(0.0f);
            this.scaleContentLayout(0.0f);
        }
    }

    public void controlMenu(){
        if(this.isOpen){
            this.closeMenu();
        }else{
            this.showMenu();
        }
        isOpen=!isOpen;
    }

    private void scaleContentLayout(float radioX){
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) this.mContentLayout.getLayoutParams();
        marginLayoutParams.bottomMargin = (int) (this.mHalfMenuHeight * radioX);
        marginLayoutParams.topMargin = (int) (this.mHalfMenuHeight * radioX);
       this.mContentLayout.setLayoutParams(marginLayoutParams);
    }

    private void alphaMenu(float alphaRaido){
         this.mMenuLayout.startAnimation(AnimationUtils.getAplhaAnimation(alphaRaido,alphaRaido));
    }

}
