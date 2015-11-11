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
    //侧滑初始化打开的状态
    private boolean once;

    private boolean isalpha;
    //侧滑时候菜单是否渐变
    private boolean isScale;
    //侧滑是主页面是否缩小
    private float menuRaido;
    //菜单的占的屏幕比例
    private float contentScaleRaido;
    //设定主菜单在侧滑时候的缩放比例
    private float menuAlphaRadio;
    //设置菜单的透明度

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
         this.menuRaido=0.67f;
         this.isOpen=true;
         this.contentScaleRaido=0.16f;
         this.menuAlphaRadio=1.0f;
    }

    /**
     * 加载初始化的菜单的XML布局，默认为0.67f
     * @param mMenuLayout
     */
    public void setmMenuLayout(View mMenuLayout){
          this.mMenuLayout=(LinearLayout)mMenuLayout;
          this.mWrapperLayout.addView(mMenuLayout);
          this.setMenuWidth(0.67f);
    }

    public  View getMenuLayout(){
        return(this.mMenuLayout);
    }

    public void setMenuAlphaRadio(float menuAlphaRadio){
         this.menuAlphaRadio=menuAlphaRadio;
    }

    /**
     * 设置菜单的宽度
     * @param menuRaido
     */
    private void setMenuWidth(float menuRaido){
        ViewGroup.LayoutParams mMenuLayoutParams=mMenuLayout.getLayoutParams();
        this.mMenuWidth=(int)(this.mScreenWidth*menuRaido);
        mMenuLayoutParams.width=this.mMenuWidth;
        mMenuLayoutParams.height= ViewGroup.LayoutParams.MATCH_PARENT;
        this.mMenuLayout.setLayoutParams(mMenuLayoutParams);
        mMaxMoveDis = this.mMenuWidth / 2;
    }

    /**
     * 设置菜单的缩放比例
     * @param menuRaido
     */
    public void setMenuRaido(float menuRaido){
         this.menuRaido=menuRaido;
         this.setMenuWidth(this.menuRaido);
    }

    /**
     * 设置主体界面的内容
     * @param mContentLayout
     */
    public void setmContentLayout(View mContentLayout){
        this.mContentLayout=mContentLayout;
        this.mWrapperLayout.addView(mContentLayout);
        this.setContentScale(this.contentScaleRaido);
    }

    private void setContentScale(float contentScaleRadio){
        ViewGroup.LayoutParams mContentParams=mContentLayout.getLayoutParams();
        this.mContentWidth=this.mScreenWidth;
        mContentParams.width=this.mContentWidth;
        mContentParams.height= ViewGroup.LayoutParams.MATCH_PARENT;
        this.mContentLayout.setLayoutParams(mContentParams);
        mHalfMenuHeight=(int)(this.mScreenWidth*contentScaleRadio);
    }

    public View getmContentLayout(){
        return(this.mContentLayout);
    }

    public void setContentScaleRaido(float contentScaleRaido){
         this.contentScaleRaido=contentScaleRaido;
         this.setContentScale(contentScaleRaido);
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
            if(this.isOpen) {
                this.showMenu();
            }else{
                this.closeMenu();
            }
            once=true;
        }
    }

    /**
     * 设置是否进行透明度
     * @param isalpha
     */
    public void setIsalpha(boolean isalpha){
          this.isalpha=isalpha;
    }

    /**
     * 设置是否进行缩放
     * @param isScale
     */
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
                       this.showMenu();
                    } else {
                       this.closeMenu();
                    }
                }
                return(true);
        }
        return (super.onTouchEvent(ev));
    }

    /**
     * 显示菜单
     */
    public void showMenu(){
            this.smoothScrollTo(0, 0);
            this.alphaMenu(this.menuAlphaRadio);
            this.scaleContentLayout(1.0f);
    }

    /**
     * 关闭菜单
     */
    public void closeMenu(){
            this.smoothScrollTo(this.mMenuWidth,0);
            this.alphaMenu(0.0f);
            this.scaleContentLayout(0.0f);
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
            if (alphaRaido <= this.menuAlphaRadio) {
                this.mMenuLayout.startAnimation(AnimationUtils.getAplhaAnimation(alphaRaido, alphaRaido));
            }else{
                this.mMenuLayout.startAnimation(AnimationUtils.getAplhaAnimation(this.menuAlphaRadio,this.menuAlphaRadio));
            }
//            this.mMenuLayout.startAnimation(AnimationUtils.getAplhaAnimation(alphaRaido, alphaRaido));
        }
    }

    /**
     * 设置初始化的时候，是否进行是打开菜单的
     * @param isOpen
     */
    public void  setIsOpenInit(boolean isOpen){
           this.isOpen=isOpen;
    }



}
