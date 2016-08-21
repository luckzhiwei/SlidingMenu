### 模仿手机QQ Android 版本的侧滑栏目:

####  1.普通的侧滑栏:
  使用 HorizontalScrollView 来实现侧滑   
  重点在于onTouchEvent中的事件的处理过程中getScrollX()函数所代表的含义。

####  2.带有缩放的侧滑栏:
  依然使用HorizontalScrollView 来实现侧滑
  但是需要在onTouchEvent的事件中，计算缩放比率，然后再通过缩放比率设置主界面大小（setLayoutParams）
  同时加上一些辅助的动画（透明度动画）



