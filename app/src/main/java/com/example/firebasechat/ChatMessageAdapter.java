package com.example.firebasechat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.List;

public class ChatMessageAdapter extends ArrayAdapter<ChatMessage> {

    private List<ChatMessage> messages;
    private Activity activity;


    public ChatMessageAdapter(Activity context, int resource, List<ChatMessage> messageList) {
        super(context, resource, messageList);
        this.messages = messageList;
        this.activity = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        ChatMessage chatMessage = getItem(position);
        int layoutResource = 0;
        int viewType = getItemViewType(position);
        if (viewType == 0) {
            layoutResource = R.layout.mine_message_item;
        } else {
            layoutResource = R.layout.your_message_item;
        }

        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = layoutInflater.inflate(layoutResource, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        boolean isText = chatMessage.getImageURL() == null;
        if (isText) {
            viewHolder.bubbleMessageTextView.setVisibility(View.VISIBLE);
            viewHolder.photoChatImageView.setVisibility(View.GONE);
            viewHolder.bubbleMessageTextView.setText(chatMessage.getText());
        } else {
            viewHolder.bubbleMessageTextView.setVisibility(View.GONE);
            viewHolder.photoChatImageView.setVisibility(View.VISIBLE);
            Glide.with(viewHolder.photoChatImageView.getContext())
                    .load(chatMessage.getImageURL())
                    .into(viewHolder.photoChatImageView);
        }

        return convertView;
    }


    @Override
    public int getItemViewType(int position) {
        int flag;
        ChatMessage chatMessage = messages.get(position);
        if (chatMessage.isMine()) {
            flag = 0;
        } else {
            flag = 1;
        }
        return flag;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    private class ViewHolder {

        private TextView bubbleMessageTextView;
        private ImageView photoChatImageView;

        public ViewHolder(View view) {
            photoChatImageView = view.findViewById(R.id.photoChatImageView);
            bubbleMessageTextView = view.findViewById(R.id.bubbleMessageTextView);
        }
    }


}
