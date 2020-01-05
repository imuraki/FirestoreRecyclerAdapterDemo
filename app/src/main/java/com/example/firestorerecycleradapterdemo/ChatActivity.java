package com.example.firestorerecycleradapterdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    EditText textsend;
    ChatAdapter chatadapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference chatref = db.collection("chat");
    FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textsend = findViewById(R.id.textsend);

        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chat chat = new Chat(user.getUid(),user.getDisplayName(), textsend.getText().toString(), new Date());
                chatref.add(chat);
                textsend.setText("");
            }
        });

        recyclerView = findViewById(R.id.chatrecyclerview);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        Query query = FirebaseFirestore.getInstance()
                .collection("chat").orderBy("timestamp", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Chat> options = new FirestoreRecyclerOptions.Builder<Chat>().setQuery(query, Chat.class).build();
        chatadapter = new ChatAdapter(options);
        chatadapter.registerAdapterDataObserver(    new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                    recyclerView.scrollToPosition(chatadapter.getItemCount() - 1);
            }
        });
        recyclerView.setAdapter(chatadapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getTitle().toString()){
            case "logout":
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(ChatActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
                break;
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        chatadapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        chatadapter.stopListening();
    }
}
