package io.github.andhikayuana.mqttdemo.ui.chat;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.github.andhikayuana.mqttdemo.R;
import io.github.andhikayuana.mqttdemo.util.Const;
import io.github.andhikayuana.mqttdemo.util.SharedPref;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 8/11/17
 */

public class ChatAdapter extends BaseAdapter {

    private List<Chat> chatList = new ArrayList<>();
    private Context context;

    public ChatAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return chatList.size();
    }

    @Override
    public Chat getItem(int position) {
        return chatList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Chat item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater
                    .from(context)
                    .inflate(R.layout.item_chat_message, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.bind(item);

        return convertView;
    }

    public void add(Chat chat) {
        chatList.add(chat);
        notifyDataSetChanged();
    }


    private class ViewHolder {

        private final TextView tvItemChatMessage;

        public ViewHolder(View itemView) {
            tvItemChatMessage = (TextView) itemView.findViewById(R.id.tvItemChatMessage);
        }

        public void bind(Chat item) {
            tvItemChatMessage.setText(item.getMessage());
            tvItemChatMessage.setGravity(item.getClientId().equals(SharedPref.getString(Const.KEY.CLIENT_ID)) ?
                    Gravity.RIGHT : Gravity.LEFT);
        }
    }
}
