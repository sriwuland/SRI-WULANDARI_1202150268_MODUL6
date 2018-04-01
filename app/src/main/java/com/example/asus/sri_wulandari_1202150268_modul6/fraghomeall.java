package com.example.asus.sri_wulandari_1202150268_modul6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class fraghomeall extends AppCompatActivity {
    RecyclerView rv;
    DatabaseReference dataref;
    ArrayList<databasePost> list;
    adapterPost ap;


    public fraghomeall() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fraghomeall, container, false);
        rv = v.findViewById(R.id.rvhomeall);
        list = new ArrayList<>();
        dataref = FirebaseDatabase.getInstance().getReference().child("post");
        ap = new adapterPost(list, this.getContext());

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rv.setAdapter(ap);

        dataref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                databasePost post = dataSnapshot.getValue(databasePost.class);
                post.key = dataSnapshot.getKey();
                list.add(post);
                ap.notifyDataSetChanged();

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

