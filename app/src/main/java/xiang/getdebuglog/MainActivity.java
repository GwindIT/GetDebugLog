package xiang.getdebuglog;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Author : Xiang
 * E-mail : Gwind_IT@163.com
 * Date   : 18-12-12
 * Version: 1.0
 * Desc   : 主界面
 */
public class MainActivity extends AppCompatActivity {

    Button mBtnStartServer, mBtnGetLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnGetLog = findViewById(R.id.btn_start_server);
        mBtnStartServer = findViewById(R.id.btn_get_log);

        mBtnStartServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this, LogService.class));
            }
        });

        mBtnGetLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogService.SWITCH_LOG_FILE_ACTION);
                sendBroadcast(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestPermission();
    }

    private String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WAKE_LOCK};


    private void requestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            Toast.makeText(MainActivity.this, "权限未授予无法正常获取Log", Toast.LENGTH_SHORT).show();
        }
    }
}
