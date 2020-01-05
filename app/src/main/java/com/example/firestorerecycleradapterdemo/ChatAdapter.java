package com.example.firestorerecycleradapterdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.List;

public class ChatAdapter extends FirestoreRecyclerAdapter<Chat, ChatAdapter.ViewHolder> {


    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    PrettyTime p = new PrettyTime();
    FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ChatAdapter(@NonNull FirestoreRecyclerOptions<Chat> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Chat model) {
        holder.message.setText(model.getMessage());
        holder.sender.setText(model.getSender());
        holder.timestamp.setText(p.format(model.getTimestamp()));

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if(viewType == MSG_TYPE_RIGHT)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_right, parent, false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_left, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if(getItem(position).getUserid().equals(user.getUid()))
            return MSG_TYPE_RIGHT;
        else
            return MSG_TYPE_LEFT;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView sender, message, timestamp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sender = itemView.findViewById(R.id.sendername);
            message = itemView.findViewById(R.id.textmessage);
            timestamp = itemView.findViewById(R.id.timestamp);

        }
    }

}
