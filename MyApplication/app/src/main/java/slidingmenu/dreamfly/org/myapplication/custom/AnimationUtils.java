package slidingmenu.dreamfly.org.myapplication.custom;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

/**
 * Created by asus on 2015/11/6.
 */
public class AnimationUtils {

      public static Animation getAplhaAnimation(float fromAlpha,float toAlpha){
          AlphaAnimation alphaAnimation=new AlphaAnimation(fromAlpha,toAlpha);
          alphaAnimation.setFillAfter(true);
          alphaAnimation.setDuration(0);
          return(alphaAnimation);
      }
}
