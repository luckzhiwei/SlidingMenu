package slidingmenu.dreamfly.org.myapplication.custom;

import android.content.Context;
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
 * Created by asus on 2015/11/10.
 */
public class DefineSlidingMenu extends HorizontalScrollView {

    private int mMenuWidth;
    private int mContentWidth;
    private int mScreenWidth;
    private int mMaxMoveDis;
    private int mHalfMenuHeight;

    private boolean isOpen;
    private boolean once;

    private boolean isalpha;
    //侧滑时候菜单是否渐变
    private boolean isScale;
    //侧滑是主页面是否缩小

    private View mMenuLayout;
    private View mContentLayout;
    private LinearLayout mWrapperLayout;


    public DefineSlidingMenu(Context context, AttributeSet attrs){
           super(context,attrs);
           this.init(context);
    }

    private void init(Context context){
         WindowManager wm=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
         DisplayMetrics outMetrics = new DisplayMetrics();
         wm.getDefaultDisplay().getMetrics(outMetrics);
         this.mScreenWidth=outMetrics.widthPixels;
         this.mWrapperLayout=new LinearLayout(context);
         this.mWrapperLayout.setOrientation(LinearLayout.HORIZONTAL);
         ViewGroup.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
         this.mWrapperLayout.setLayoutParams(layoutParams);
         this.addView(this.mWrapperLayout);
    }


    public void setmMenuLayout(View mMenuLayout){
          this.mMenuLayout=(LinearLayout)mMenuLayout;
          this.mWrapperLayout.addView(mMenuLayout);
          ViewGroup.LayoutParams mMenuLayoutParams=mMenuLayout.getLayoutParams();
          this.mMenuWidth=this.mScreenWidth-this.mScreenWidth/3;
          mMenuLayoutParams.width=this.mMenuWidth;
          mMenuLayoutParams.height= ViewGroup.LayoutParams.MATCH_PARENT;
          this.mMenuLayout.setLayoutParams(mMenuLayoutParams);
          mMaxMoveDis = this.mMenuWidth / 2;

    }
    public  View getMenuLayout(){
         return(this.mMenuLayout);
    }

    public void setmContentLayout(View mContentLayout){
        this.mContentLayout=mContentLayout;
        this.mWrapperLayout.addView(mContentLayout);
        ViewGroup.LayoutParams mContentParams=mContentLayout.getLayoutParams();
        this.mContentWidth=this.mScreenWidth;
        mContentParams.width=this.mContentWidth;
        mContentParams.height= ViewGroup.LayoutParams.MATCH_PARENT;
       this.mContentLayout.setLayoutParams(mContentParams);
        mHalfMenuHeight=this.mScreenWidth/6;
    }

    public View getmContentLayouy(){
        return(this.mContentLayout);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        if(!once) {
            if (mWrapperLayout != null) {
                this.mWrapperLayout.setOrientation(LinearLayout.HORIZONTAL);
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

    public void setIsalpha(boolean isalpha){
          this.isalpha=isalpha;
    }

    public void setIsScale(boolean isScale){
         this.isScale=isScale;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev){
        switch (ev.getAction()){
            case MotionEvent.ACTION_MOVE:
                if(this.mContentLayout!=null&& this.mMenuLayout!=null) {
                    int scrollX = this.getScrollX();
                    float scaleRadio = 1 - (scrollX / (float) mMenuWidth);
                    this.scaleContentLayout(scaleRadio);
                    this.alphaMenu(scaleRadio);
                }
                break;
            case MotionEvent.ACTION_UP:
                /**
                 * 理解getScrollX的含义，就是当前滑动的X的的数值
                 * 屏幕左边为正，右边为负数
                 */
                if(this.mContentLayout!=null && this.mMenuLayout!=null) {
                    if (this.getScrollX() < this.mMaxMoveDis) {
                        this.smoothScrollTo(0, 0);
                        this.scaleContentLayout(1.0f);
                        this.alphaMenu(1.0f);
                        isOpen = true;
                    } else {
                        this.smoothScrollTo(mMenuWidth, 0);
                        this.scaleContentLayout(0.0f);
                        this.alphaMenu(0.0f);
                        isOpen = false;
                    }
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
            this.isOpen=true;
        }
    }

    public void closeMenu(){
        if(this.isOpen){
            this.smoothScrollTo(this.mMenuWidth,0);
            this.alphaMenu(0.0f);
            this.scaleContentLayout(0.0f);
            this.isOpen=false;
        }
    }

    /**
     * 控制渐变的主体的大小
     * @param radioX
     */
    private void scaleContentLayout(float radioX){
        if(this.isScale) {
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) this.mContentLayout.getLayoutParams();
            marginLayoutParams.bottomMargin = (int) (this.mHalfMenuHeight * radioX);
            marginLayoutParams.topMargin = (int) (this.mHalfMenuHeight * radioX);
            this.mContentLayout.setLayoutParams(marginLayoutParams);
        }
    }

    /**
     * 控制渐变的时候侧滑菜单的透明度
     * @param alphaRaido
     */
    private void alphaMenu(float alphaRaido) {
        if (this.isalpha) {
            this.mMenuLayout.startAnimation(AnimationUtils.getAplhaAnimation(alphaRaido, alphaRaido));
        }
    }




}
