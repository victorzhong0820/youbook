package youbook.victor.com.youbook.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import youbook.victor.com.youbook.R;
import youbook.victor.com.youbook.common.BaseActivity;
import youbook.victor.com.youbook.common.MainActivity;
import youbook.victor.com.youbook.common.ViewPagerAdapter;

/**
 * Created by Victor on 2015/3/21.
 */
public class SplashActivity extends BaseActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    //Splash时长
    private final int SPLASH_DISPLAY_TIME = 1200;
    //引导图片数组
    private int[] guide_picture = {R.drawable.guide1,R.drawable.guide2,R.drawable.guide3};
    //View动态数组
    private List<View>views = new ArrayList<View>();
    //底部圆点
    private ImageView[] dots;

    private ViewPager viewPager;
    private Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("count_app",MODE_WORLD_READABLE);
        int count_app = sharedPreferences.getInt("count_app",0);
        //第一次打开应用，进入引导，否则进入Splash
        if(count_app == 0){
            setContentView(R.layout.activity_guide);
            init();
        } else {
            setContentView(R.layout.activity_splash);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            },SPLASH_DISPLAY_TIME);
        }
        editor = sharedPreferences.edit();
        editor.putInt("count_app",++count_app);
        editor.commit();
    }

    /**
     * 初始化组件，监听
     */
    private void init(){
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.ll);
        dots = new ImageView[guide_picture.length];
        for(int i = 0; i < guide_picture.length; i++){
            dots[i] = (ImageView)linearLayout.getChildAt(i);
            dots[i].setEnabled(false);
            dots[i].setTag(i);
        }
        dots[0].setEnabled(true);

        startBtn = (Button)findViewById(R.id.btn_start);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        viewPager = (ViewPager)findViewById(R.id.viewPager_guide);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ImageView imageView;
        for(int i = 0; i < guide_picture.length; i ++){
            imageView = new ImageView(this);
            imageView.setImageResource(guide_picture[i]);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            views.add(imageView);
        }
        viewPager.setAdapter(new ViewPagerAdapter(views));

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                DisableDots();
                dots[i].setEnabled(true);

                if (i == views.size() - 1) {
                    startBtn.setVisibility(View.VISIBLE);
                } else {
                    startBtn.setVisibility(View.GONE);
                }
            }

            private void DisableDots(){
                for(int i = 0; i <dots.length;i++){
                    dots[i].setEnabled(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}
