package lht.test.com.invention_simpleheart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import lht.test.com.UI.Config;
import lht.test.com.webview.GameActivity;

public class InventionActivity extends Activity {

    private ImageButton imageButton;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_invention);
        //设置沉浸状态栏
        Config.setStatusTrans(this);
        init();
    }
    private void init(){
        imageButton = (ImageButton) findViewById(R.id.title_bt_back);
        imageButton.setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.title_tv);
        title.setText("发现");

    }
    public void  start_game(View v){
        switch (v.getId()){
            case R.id.game1_button:
                Intent intent = new Intent(InventionActivity.this,GameActivity.class);
                intent.putExtra("msString","http://yx.h5uc.com/2048/");
                startActivity(intent);
                break;
            case R.id.game2_button:
                Intent intent1 = new Intent(InventionActivity.this,GameActivity.class);
                intent1.putExtra("msString","http://sda.4399.com/4399swf/upload_swf/ftp14/yzg/20141021/3a/game.htm");
                startActivity(intent1);
                break;
            case R.id.game3_button:
                Toast.makeText(InventionActivity.this, "期待更多的游戏", Toast.LENGTH_SHORT).show();
                break;
        }
    }


}
