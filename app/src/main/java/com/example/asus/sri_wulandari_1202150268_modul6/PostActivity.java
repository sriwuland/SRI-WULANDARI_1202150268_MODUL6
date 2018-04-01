package com.example.asus.sri_wulandari_1202150268_modul6;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity {

    TextView user, judul, caption;
    ImageView image;
    EditText komentar;
    RecyclerView rv;
    adapterKomen adapter;
    ArrayList<databaseKomen> list;
    DatabaseReference dref;
    ProgressDialog pd;
    String usernya, idfoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);


        user = findViewById(R.id.yangupload);
        image = findViewById(R.id.gambardaripost);
        komentar = findViewById(R.id.srckomentar);
        pd = new ProgressDialog(this);
        judul = findViewById(R.id.judulgambarpost);
        caption = findViewById(R.id.deskripsigambarpost);
        dref = FirebaseDatabase.getInstance().getReference().child("comment");
        rv = findViewById(R.id.rvkomentar);
        list = new ArrayList<>();
        adapter = new adapterKomen(this, list);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        String [] usernow = FirebaseAuth.getInstance().getCurrentUser().getEmail().split("@");
        usernya = usernow[0];
        idfoto = getIntent().getStringExtra("key");
        user.setText(getIntent().getStringExtra("user"));
        judul.setText(getIntent().getStringExtra("judul"));
        caption.setText(getIntent().getStringExtra("caption"));
        Glide.with(this).load(getIntent().getStringExtra("image")).override(250,250).into(image);

        dref.addChildEventListener(new ChildEventListener(){
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                databaseKomen cur = dataSnapshot.getValue(databaseKomen.class);
                if (cur.getFotokomen().equals(idfoto)) {
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
    }

    public void postcomment(View view) {
        pd.setMessage("Adding comment");
        pd.show();

        databaseKomen com = new databaseKomen(usernya, komentar.getText().toString(), idfoto);
        dref.push().setValue(com).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(PostActivity.this, "Comment ditambahkan", Toast.LENGTH_SHORT).show();
                    komentar.setText(null);
                }else {
                    Toast.makeText(PostActivity.this, "Comment gagal ditambahkan", Toast.LENGTH_SHORT).show();
                }
                pd.dismiss();
            }
        });
    }
}

