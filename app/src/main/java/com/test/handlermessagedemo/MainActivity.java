package com.test.handlermessagedemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {

    private MyHandler myHandler;
    Handler handler;
    private TextView textView;
    private boolean bool = true;
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.text);
        Button startButton = (Button)findViewById(R.id.start_btn);
        Button endButton = (Button)findViewById(R.id.end_btn);

        startButton.setOnClickListener(listener);
        endButton.setOnClickListener(listener);

        myHandler = new MyHandler(Looper.getMainLooper());
//        handler =  new Handler(){
//
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                Log.i("System.out", msg.obj + "");
//                if(msg.what == 1){
//                    textView.setText(msg.obj.toString());
//                }
//            }
//        };


    }

    private View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.start_btn:
                    bool = true;
                    new myThread().start();//启动线程,开始计时
                    break;
                case R.id.end_btn:
                    bool = false;//结束线程
                    break;
                default:
                    break;
            }
        }
    };

    class myThread extends Thread{

        @Override
        public void run() {
            while(bool){
                Message message = myHandler.obtainMessage();
                message.what = 1;
                message.obj = simpleDateFormat.format(new Date());
                myHandler.sendMessage(message);
                try {
                    Thread.sleep(1000);//休息一秒
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    class MyHandler extends Handler {

        public MyHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.i("System.out", msg.obj + "");
            if(msg.what == 1){
                textView.setText(msg.obj.toString());
            }
        }
    }
}