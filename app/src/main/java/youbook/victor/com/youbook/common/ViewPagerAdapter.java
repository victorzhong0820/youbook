package youbook.victor.com.youbook.common;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

/**
 * ViewPager适配器，用来绑定数据和View
 * Created by Victor on 2015/3/23.
 */
public class ViewPagerAdapter extends PagerAdapter{
    //界面列表
    private List<View> views;

    public ViewPagerAdapter(List<View>views){
        this.views = views;
    }

    public ViewPagerAdapter(){}

    /**
     * 获得当前界面数
     * @return
     */
    @Override
    public int getCount() {
        if(views != null)
            return views.size();
        return 0;
    }

    /**
     * 初始化position位置的界面
     * @param view
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(View view, int position) {
        ((ViewPager)view).addView(views.get(position),0);
        return views.get(position);
    }

    /**
     * 判断是否则对象生成界面
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    /**
     * 销毁position的界面
     * @param view
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(View view, int position, Object object) {
        ((ViewPager)view).removeView(views.get(position));
    }
}
