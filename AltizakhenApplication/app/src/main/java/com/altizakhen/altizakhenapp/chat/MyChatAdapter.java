package com.altizakhen.altizakhenapp.chat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.altizakhen.altizakhenapp.R;
import com.altizakhen.altizakhenapp.backend.firebaseChatApi.model.FirebaseChat;

import java.util.List;

/**
 * Created by personal on 1/15/15.
 */
public class MyChatAdapter extends BaseAdapter {
    private List<FirebaseChat> myChats;
    private Context context;
    private String currentUserId;

    public MyChatAdapter(List<FirebaseChat> myChats, Context context, String currentUserId) {
        this.myChats = myChats;
        this.context = context;
        this.currentUserId = currentUserId;
    }

    @Override
    public int getCount() {
        return myChats == null ? 0 : myChats.size();
    }

    @Override
    public Object getItem(int i) {
        return myChats.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View rootView = mInflater.inflate(R.layout.my_chat_row, null);
        FirebaseChat fbchat = myChats.get(i);
        String otherUserName, otherUserId;
        if (fbchat.getUserId1().equals(currentUserId)) {
            otherUserName = fbchat.getUserName2();
            otherUserId = fbchat.getUserId2();
        } else {
            otherUserName = fbchat.getUserName1();
            otherUserId = fbchat.getUserId1();
        }
        ((TextView) rootView.findViewById(R.id.my_chat_row_textview)).setText(otherUserName);
        ((TextView) rootView.findViewById(R.id.my_chat_row_user_id_textview)).setText(otherUserId);

        return  rootView;
    }
}
