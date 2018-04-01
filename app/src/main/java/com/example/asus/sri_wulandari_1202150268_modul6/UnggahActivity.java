package com.example.asus.sri_wulandari_1202150268_modul6;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UnggahActivity extends AppCompatActivity {
    private final int SELECT_PICTURE = 1;
    String idCurrentUser;
    StorageReference storage;
    Uri imageUri;
    DatabaseReference dataref;
    ImageView uploadgambar;
    EditText judul, caption;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unggah);
        setTitle("Upload Picture Here");

        idCurrentUser = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        dialog = new ProgressDialog(this);
        uploadgambar = findViewById(R.id.uploadgambar);
        judul = findViewById(R.id.posttitle);
        caption = findViewById(R.id.postcaption);
        storage = FirebaseStorage.getInstance().getReference();
        dataref = FirebaseDatabase.getInstance().getReference().child("post");
    }

    public void setfoto(View view) {
        Intent pickImage = new Intent(Intent.ACTION_PICK);
        pickImage.setType("image/*");

        startActivityForResult(pickImage, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Ketika user memilih foto
        if(resultCode==RESULT_OK){
            if(requestCode==SELECT_PICTURE){

                //Mendapatkan data dari intent
                imageUri = data.getData();
                try {
                    //Merubah data menjadi inputstream yang diolah menjadi bitmap dan ditempatkan pada imageview
                    InputStream stream = getContentResolver().openInputStream(imageUri);
                    Bitmap gambar = BitmapFactory.decodeStream(stream);
                    uploadgambar.setImageBitmap(gambar);
                } catch (FileNotFoundException e) {
                    Log.w("FileNotFoundException", e.getMessage());
                    Toast.makeText(this, "Unable to load image", Toast.LENGTH_SHORT).show();
                }
            }

            //Ketika user tidak memilih foto
        }else{
            Toast.makeText(this, "Picture not selected", Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadgambar(View view) {
        dialog.setMessage("Uploading..");
        dialog.show();

        StorageReference filepath = storage.child(judul.getText().toString());

        uploadgambar.setDrawingCacheEnabled(true);
        uploadgambar.buildDrawingCache();
        Bitmap bitmap = uploadgambar.getDrawingCache();
        ByteArrayOutputStream barr = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, barr);
        byte[] data = barr.toByteArray();
        UploadTask task = filepath.putBytes(data);

        task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String image = taskSnapshot.getDownloadUrl().toString();
                databasePost user = new databasePost(caption.getText().toString(), image, judul.getText().toString(), idCurrentUser);

                dataref.push().setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Unggah.this, "Post uploaded", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Unggah.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Unggah.this, "Upload gagal", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}
