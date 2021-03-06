package com.ns.yc.lifehelper.ui.guide.view.activity;
import android.content.res.TypedArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.ns.yc.lifehelper.R;
import com.ycbjie.library.base.mvp.BaseActivity;
import com.ycbjie.library.constant.Constant;
import com.yc.cn.ycbannerlib.banner.BannerView;
import com.yc.cn.ycbannerlib.banner.adapter.AbsDynamicPagerAdapter;
import java.util.ArrayList;
import java.util.List;
import cn.ycbjie.ycstatusbarlib.bar.StateAppBar;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2015/03/22
 *     desc  : 启动引导页
 *     revise:
 * </pre>
 */
public class SplashPagerActivity extends BaseActivity {


    private BannerView bannerView;
    private Button btnGo;
    private List<Integer> imageId;

    /**
     * 屏蔽返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public int getContentView() {
        return R.layout.activity_splash_pager;
    }

    @Override
    public void initView() {
        StateAppBar.translucentStatusBar(this, true);
        bannerView = findViewById(R.id.bannerView);
        btnGo = findViewById(R.id.btn_go);
        initGetImage();
        initBanner();
    }

    private void initGetImage() {
        imageId = new ArrayList<>();
        TypedArray images = this.getResources().obtainTypedArray(R.array.splash_image);
        for (int a = 0; a < 4; a++) {
            int image = images.getResourceId(a, R.drawable.bg_small_kites_min);
            imageId.add(image);
        }
        images.recycle();
    }

    @Override
    public void initListener() {
        btnGo.setOnClickListener(v -> {
            ActivityUtils.startActivity(SelectFollowActivity.class);
            finish();
            SPUtils.getInstance(Constant.SP_NAME).put(Constant.KEY_FIRST_SPLASH, false);
        });
    }

    @Override
    public void initData() {

    }


    private void initBanner() {
        bannerView.setPlayDelay(0);
        bannerView.setHintGravity(1);
        bannerView.setHintPadding(SizeUtils.dp2px(10), 0,
                SizeUtils.dp2px(10), SizeUtils.dp2px(30));
        bannerView.setAdapter(new ImageNormalAdapter());
        bannerView.setOnPageListener(position -> {
            if (position >= 0 && position == imageId.size() - 1) {
                btnGo.setVisibility(View.VISIBLE);
            } else {
                btnGo.setVisibility(View.GONE);
            }
        });
    }


    private class ImageNormalAdapter extends AbsDynamicPagerAdapter {

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            view.setImageResource(imageId.get(position));
            return view;
        }

        @Override
        public int getCount() {
            return imageId == null ? 0 : imageId.size();
        }
    }


}
