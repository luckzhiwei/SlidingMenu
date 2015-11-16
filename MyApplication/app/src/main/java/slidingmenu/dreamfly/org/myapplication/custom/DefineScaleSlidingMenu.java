package slidingmenu.dreamfly.org.myapplication.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by asus on 2015/11/16.
 */
public class DefineScaleSlidingMenu extends DefineSlidingMenu {

       private int mHalfMenuHeight;

       private boolean isScale;

       private boolean isalpha;

       private float menuAlphaRadio;
       //设置菜单的透明度

       public DefineScaleSlidingMenu(Context context, AttributeSet attrs){
           super(context, attrs);
           this.setContentScaleRaido(0.16f);
           this.menuAlphaRadio=1.0f;
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
             }
            return (super.onTouchEvent(ev));
       }

    /**
     * 设置屏幕的缩放比例
     * @param contentScaleRadio
     */
    public void setContentScaleRaido(float contentScaleRadio){
         this.mHalfMenuHeight=(int)(this.mScreenWidth*contentScaleRadio);
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
        }
    }

    @Override
    public void showMenu(){
        super.showMenu();
        this.alphaMenu(this.menuAlphaRadio);
        this.scaleContentLayout(1.0f);
    }

    @Override
    public void closeMenu(){
        super.closeMenu();
        this.alphaMenu(0.0f);
        this.scaleContentLayout(0.0f);
    }

    /**
     * 设置菜单的透明度比例
     * @param menuAlphaRadio
     */
    public void setMenuAlphaRadio(float menuAlphaRadio){
        this.menuAlphaRadio=menuAlphaRadio;
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


}
