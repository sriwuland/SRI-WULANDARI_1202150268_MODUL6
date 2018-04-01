package com.example.asus.sri_wulandari_1202150268_modul6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class fraghomeuser extends AppCompatActivity {

    RecyclerView rv;
    DatabaseReference ref;
    ArrayList<databasePost> list;
    adapterPost adapter;


    public fraghomeuser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fraghomeuser, container, false);
        ref = FirebaseDatabase.getInstance().getReference().child("post");
        list = new ArrayList<>();
        adapter = new adapterPost(list, this.getContext());
        rv = v.findViewById(R.id.rvhomeuser);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rv.setAdapter(adapter);

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                databasePost cur = dataSnapshot.getValue(databasePost.class);
                if (cur.getUser().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                    cur.key = dataSnapshot.getKey();
                    list.add(cur);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return v;
    }

}
