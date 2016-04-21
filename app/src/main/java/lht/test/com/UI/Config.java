package lht.test.com.UI;

import android.app.Activity;
import android.os.Build;
import android.view.WindowManager;

import lht.test.com.invention_simpleheart.R;
import lht.test.com.lib.SystemBarTintManager;

/**
 * Created by Administrator on 2016/3/19 0019.
 */
public class Config {
    public static void setStatusTrans(Activity activity){
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            SystemBarTintManager systemBarTintManager = new SystemBarTintManager(activity);
            systemBarTintManager.setStatusBarTintEnabled(true);
            systemBarTintManager.setStatusBarTintResource(R.color.zhuantai);
        }
    }
}
