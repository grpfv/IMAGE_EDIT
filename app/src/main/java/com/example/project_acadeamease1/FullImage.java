package com.example.project_acadeamease1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FullImage extends AppCompatActivity {
    private ImageView fullImageView;
    Button deleteIcon;
    final private CollectionReference databaseReference = FirebaseFirestore.getInstance().collection("Album");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        fullImageView = findViewById(R.id.fullImageView);
        deleteIcon = findViewById(R.id.deletepngbutton);

        String imageUrl = getIntent().getStringExtra("imageUrl");

        Glide.with(this).load(imageUrl).into(fullImageView);

        deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog(imageUrl); // Show confirmation dialog before deleting
            }
        });
    }

    private void showDeleteConfirmationDialog(String imageUrl) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this image?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteImage(imageUrl);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteImage(String imageUrl) {
        databaseReference.document(imageUrl).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(FullImage.this, "Image deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FullImage.this, "Failed to delete image", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
