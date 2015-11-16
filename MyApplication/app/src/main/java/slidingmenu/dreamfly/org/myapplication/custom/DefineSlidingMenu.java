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

    protected int mMenuWidth;
    protected int mContentWidth;
    protected int mScreenWidth;
    protected int mMaxMoveDis;

    protected boolean isOpen;
    //侧滑初始化打开的状态
    protected boolean once;
    //是否已经确定出了layout,之后不用再加载
    protected float menuRaido;
    //菜单的占的屏幕比例

    protected View mMenuLayout;
    protected View mContentLayout;
    protected LinearLayout mWrapperLayout;


    public DefineSlidingMenu(Context context, AttributeSet attrs){
           super(context,attrs);
           this.init(context);
    }

    /**
     * 初始化设置，加入wrapLayout
     * @param context
     */
    protected void init(Context context){
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

    /**
     * 得到菜单布局的View
     * @return
     */
    public  View getMenuLayout(){
        return(this.mMenuLayout);
    }

    /**
     * 设置主体界面的内容
     * @param mContentLayout
     */
    public void setmContentLayout(View mContentLayout){
        this.mContentLayout=mContentLayout;
        this.mWrapperLayout.addView(mContentLayout);
        ViewGroup.LayoutParams mContentParams=mContentLayout.getLayoutParams();
        this.mContentWidth=this.mScreenWidth;
        mContentParams.width=this.mContentWidth;
        mContentParams.height= ViewGroup.LayoutParams.MATCH_PARENT;
        this.mContentLayout.setLayoutParams(mContentParams);
    }

    /**
     * 得到主界面布局的View引用
     * @return
     */
    public View getmContentLayout(){
        return(this.mContentLayout);
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
        //最大滑动距离是菜单长度的一半
    }

    /**
     * 设置菜单完全展示出来之后，占屏幕的比例
     * @param menuRaido
     */
    public void setMenuRaido(float menuRaido){
         this.menuRaido=menuRaido;
         this.setMenuWidth(this.menuRaido);
    }

    /**
     * 显示菜单
     */
    public void showMenu(){
        this.smoothScrollTo(0, 0);
    }

    /**
     * 关闭菜单
     */
    public void closeMenu(){
        this.smoothScrollTo(this.mMenuWidth,0);
    }

    /**
     * 设置初始化的时候，是否进行是打开菜单的
     * @param isOpen
     */
    public void  setIsOpenInit(boolean isOpen){
        this.isOpen=isOpen;
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


    @Override
    public boolean onTouchEvent(MotionEvent ev){
        switch (ev.getAction()){
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



}
