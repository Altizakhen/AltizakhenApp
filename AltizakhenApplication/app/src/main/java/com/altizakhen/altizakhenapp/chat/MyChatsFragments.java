package com.altizakhen.altizakhenapp.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.altizakhen.altizakhenapp.ApiHelper;
import com.altizakhen.altizakhenapp.MainActivity;
import com.altizakhen.altizakhenapp.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by personal on 1/15/15.
 */
public class MyChatsFragments extends Activity {

    ListView lv;
    Timer timer;
    ApiHelper apiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_chat);

        lv = (ListView) findViewById(R.id.my_chat_listview);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MyChatsFragments.this, ChatActivity.class);
                intent.putExtra("otherUserId", ((TextView) view.findViewById(R.id.my_chat_row_user_id_textview)).getText());
                startActivity(intent);
            }
        });
        apiHelper = new ApiHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                MyChatsFragments.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        apiHelper.getUserChats(MainActivity.userServerId, lv);
                    }
                });
            }
        }, 0, 7000);

    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }
}
