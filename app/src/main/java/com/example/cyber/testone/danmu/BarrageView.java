package com.example.cyber.testone.danmu;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Random;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Cyber on 28/06/2018.
 */
    public class BarrageView extends RelativeLayout {
        private Context mContext;
        private BarrageHandler mHandler = new BarrageHandler();
        private Random random = new Random(System.currentTimeMillis());
        private static final long BARRAGE_GAP_MIN_DURATION = 1000;//两个弹幕的最小间隔时间
        private static final long BARRAGE_GAP_MAX_DURATION = 2000;//两个弹幕的最大间隔时间
        private int maxSpeed = 10000;//速度，ms
        private int minSpeed = 5000;//速度，ms
        private int maxSize = 30;//文字大小，dp
        private int minSize = 15;//文字大小，dp
        private int totalHeight = 0;//整个的高度
        private int lineHeight = 0;//每一行弹幕的高度
        private int totalLine = 0;//弹幕的行数
        private String[] itemText = {"大头死变态", "老圩人最屌了", "唉这把中单是火男，难玩了", "大头是傻子", "世界上最长的路是套路", "英雄联盟最强的是补丁",
                "我不会轻易的go die", "嘿嘿", "加班加班"};
        private int textCount;//文本的组数
        public BarrageView(Context context) {
            this(context, null);
        }

        public BarrageView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public BarrageView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            mContext = context;
            init();
        }

        private void init() {
            textCount = itemText.length;
            int duration = (int) ((BARRAGE_GAP_MAX_DURATION - BARRAGE_GAP_MIN_DURATION) * Math.random());
//            mHandler.sendEmptyMessageDelayed(0, duration);
        }

        public void addBarrage(String text){
            Message msg = Message.obtain();
            msg.what = 1;
            Bundle bundle = new Bundle();
            bundle.putString("text", text);
            msg.setData(bundle);
            mHandler.sendMessageDelayed(msg, 500);
        }

    class BarrageHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                Bundle bundle = msg.getData();
                String text = bundle.getString("text", "Not got the text.");
                generateItem(text);
                System.out.println("===============" + text + "===generate============");
            }

            //每个弹幕产生的间隔时间随机
//            int duration = (int) ((BARRAGE_GAP_MAX_DURATION - BARRAGE_GAP_MIN_DURATION) * Math.random());
//            //多个消息可以使用同一个handler, 通过what不同区分不同的消息来源, 从而获取消息内容
//            this.sendEmptyMessageDelayed(0, duration);
        }
    }

        @Override
        //Activity生命周期中，onStart, onResume, onCreate都不是真正visible的时间点，真正的visible时间点是onWindowFocusChanged()函数被执行时。
        //当你屏幕的焦点发生变化时候，想要操作什么也完全可以在这个方法里面执行。
        public void onWindowFocusChanged(boolean hasWindowFocus) {
            super.onWindowFocusChanged(hasWindowFocus);
            totalHeight = getMeasuredHeight();
            //获取每一行弹幕的最大高度
            lineHeight = getLineHeight();
            //我们整个弹幕的高度view/每一行的最大弹幕高度=
            totalLine = totalHeight / lineHeight;
        }

        private void generateItem(String text) {
            BarrageItem item = new BarrageItem();
            String tx = text;
            if(text == null){
                //把我们的每行弹幕的行数顺序跟弹幕进行一个随机
                tx = itemText[(int) (Math.random() * textCount)];
            }
            item.text = tx;
            //随机弹幕大小
            int sz = (int) (minSize + (maxSize - minSize) * Math.random());
            item.textView = new TextView(mContext);
            item.textView.setText(tx);
            item.textView.setTextSize(sz);
            item.textView.setTextColor(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            //这里我们需要传入三个参数 文本对象，文字行数跟大小
            item.textMeasuredWidth=(int) getTextWidth(item, tx, sz);
            //这是设置弹幕移动速度，实现有快有慢的感觉
            item.moveSpeed = 3000;

            //这里为了实现一个弹幕循环播放的项目，在我们实际中看情况而定
            if (totalLine != 0) {
                totalHeight = getMeasuredHeight();
                lineHeight = getLineHeight();
                totalLine = totalHeight / lineHeight;
            }else{
                totalLine = 10;
            }

            //弹幕在y轴上出现的位置
            item.verticalPos = random.nextInt(totalLine) * lineHeight;
//        itemList.add(item);
            showBarrageItem(item);
        }

        private void showBarrageItem(final BarrageItem item) {
//paddingLeft是设置布局里面的内容左边的距离，这样我们这就可以让这个弹幕的textview完全消失
            int leftMargin = this.getRight() - this.getLeft() - this.getPaddingLeft();
//这里我们通过动态的方式去设置一些我们布局的属性。
//        int verticalMargin = getRandomTopMargin();
//        item.textView.setTag(verticalMargin);

            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            params.topMargin = item.verticalPos;
            this.addView(item.textView, params);

            Animation anim = generateTranslateAnim(item, leftMargin);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }
                @Override
                //当我们动画结束的时候，清除该条弹幕
                public void onAnimationEnd(Animation animation) {
                    item.textView.clearAnimation();
                    BarrageView.this.removeView(item.textView);
                    System.out.println("===============" + item.text + "===removed============");
                }

                @Override
                //动画被取消的时候出发
                public void onAnimationRepeat(Animation animation) {

                }
            });
            item.textView.startAnimation(anim);
        }

        //
        private TranslateAnimation generateTranslateAnim(BarrageItem item, int leftMargin) {
            //这里我们有四个参数（动画开始的x点，结束点，开始y轴点，结束的y点）
            TranslateAnimation anim = new TranslateAnimation(leftMargin, -item.textMeasuredWidth, 0, 0);
            //我们设置动画的持续时间，弹幕移动多久，我们就持续多久动画
            anim.setDuration(item.moveSpeed);
            // Interpolator 被用来修饰动画效果，定义动画的变化率，可以使存在的动画效果accelerated(加速)，decelerated(减速),repeated(重复),bounced(弹跳)等。
        /*
         * AccelerateDecelerateInterpolator 在动画开始与结束的地方速率改变比较慢，在中间的时候加速
          AccelerateInterpolator  在动画开始的地方速率改变比较慢，然后开始加速
              AnticipateInterpolator 开始的时候向后然后向前甩
          AnticipateOvershootInterpolator 开始的时候向后然后向前甩一定值后返回最后的值
              BounceInterpolator   动画结束的时候弹起
            CycleInterpolator 动画循环播放特定的次数，速率改变沿着正弦曲线
          DecelerateInterpolator 在动画开始的地方快然后慢
            LinearInterpolator   以常量速率改变
            OvershootInterpolator    向前甩一定值后再回到原来位置
         * */
            anim.setInterpolator(new AccelerateDecelerateInterpolator());
        /*
         * fillBefore是指动画结束时画面停留在此动画的第一帧;
           fillAfter是指动画结束是画面停留在此动画的最后一帧。
       Java代码设置如下：
      /*****动画结束时，停留在最后一帧*********
          setFillAfter(true);
          setFillBefore(false);
    /*****动画结束时，停留在第一帧*********
       setFillAfter(false);
        setFillBefore(true);
         *
         * */
            anim.setFillAfter(true);
            return anim;
        }

        /**
         * 计算TextView中字符串的长度
         *
         * @param text 要计算的字符串
         * @param Size 字体大小
         * @return TextView中字符串的长度
         */
        //因为我们的弹幕包裹在一个矩形中
        public float getTextWidth(BarrageItem item, String text, float Size) {
            Rect bounds = new Rect();
            TextPaint paint;
            paint = item.textView.getPaint();
            //这里参数是获取文本对象，开始的长度，结束的长度，我们绘制好的矩形框
            paint.getTextBounds(text, 0, text.length(), bounds);
            return bounds.width();
        }


        /**
         * 获得每一行弹幕的最大高度
         *
         * @return
         */
        private int getLineHeight() {
            BarrageItem item = new BarrageItem();
            String tx = itemText[0];
            item.textView = new TextView(mContext);
            item.textView.setText(tx);
            item.textView.setTextSize(maxSize);
            Rect bounds = new Rect();
            TextPaint paint;
            paint = item.textView.getPaint();
            paint.getTextBounds(tx, 0, tx.length(), bounds);
            return bounds.height();
        }

        //记录一下当前在显示弹幕的高度，避免弹幕出现重叠
        private Set existMarginValues = new HashSet<>();
        private int linesCount;
//    private int getRandomTopMargin()
//    {
//      //计算弹幕的空间高度
//      if(totalLine==0)
//      {
//          totalLine=Rparams.getBottom()-Rparams.getTop()-Rparams.getPaddingTop()
//                  -Rparams.getPaddingBottom();
//          if (totalHeight==0) {
//               totalHeight = getMeasuredHeight();
//                  lineHeight = getLineHeight();
//                  totalLine = totalHeight / lineHeight;
//          }
//          //检查重叠
//            while (true) {
//                int randomIndex =  (int) (Math.random() * linesCount);
//                int marginValue = (int) (randomIndex * (totalLine / linesCount));
//
//                if (!existMarginValues.contains(marginValue)) {
//                    existMarginValues.add(marginValue);
//                    return marginValue;
//                }
//            }
//
//


        // }


    public class BarrageItem {
        public TextView textView;//文本框
        public int textColor;//文本颜色
        public String text;//文本对象
        public int textSize;//文本的大小
        public int moveSpeed;//移动速度
        public int verticalPos;//垂直方向显示的位置
        public int textMeasuredWidth;//字体显示占据的宽度
    }
}
