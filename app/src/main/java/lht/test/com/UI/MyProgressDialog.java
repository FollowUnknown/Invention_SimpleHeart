package lht.test.com.UI;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Administrator on 2016/4/21 0021.
 */
public class MyProgressDialog extends ProgressDialog {
    public MyProgressDialog(Context context) {
        super(context);
        setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置为圆形进度框
        setCancelable(true);//设置进度条是否可以按退回键取消
        setCanceledOnTouchOutside(false);//设置点击进度对话框外的区域对话框不消失
    }
}
