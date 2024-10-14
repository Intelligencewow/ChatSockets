package com.example.chatsockets;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final List<Message> messageList;



    public MessagesAdapter(Context context,List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @Override
    public int getItemViewType(int position) {

        Message message = messageList.get(position);

        if (message.isSent()){
            return 1;
        }
        return 0;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 1){
            View view = LayoutInflater.from(context).inflate(R.layout.message_sent, parent, false);
            return new sentMessagesViewHolder(view);

        }
        View view = LayoutInflater.from(context).inflate(R.layout.message_received, parent, false);
        return new receivedMessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messageList.get(position);

        if (holder instanceof sentMessagesViewHolder){
            ((sentMessagesViewHolder) holder).bind(message);
        } else {
            ((receivedMessageViewHolder) holder).bind(message);
        }
    }


    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class sentMessagesViewHolder extends RecyclerView.ViewHolder {

        public TextView message;
        public TextView time;


        public sentMessagesViewHolder(View view) {
            super(view);
            Log.i("SentMessage", "SentMessagesViewHolder: ");
            message = view.findViewById(R.id.messageSent);
            time = view.findViewById(R.id.sendTime);

        }

        public void bind(Message message){
            this.message.setText(message.getText());
            this.time.setText(message.getLocalTime());

        }
    }

    public class receivedMessageViewHolder extends RecyclerView.ViewHolder {
        public TextView message;
        public TextView time;
        public TextView userName;

        public receivedMessageViewHolder(View view) {
            super(view);
            message = view.findViewById(R.id.messageReceived);
            time = view.findViewById(R.id.receivedTime);
            userName = view.findViewById(R.id.userName);
        }

        public void bind(Message message){
            this.message.setText(message.getText());
            this.time.setText(message.getLocalTime());
            this.userName.setText(message.getSender());
        }

    }
}
