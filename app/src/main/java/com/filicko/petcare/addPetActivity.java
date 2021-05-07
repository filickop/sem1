package com.filicko.petcare;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class addPetActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    ImageView imageView;
    Button captureBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);
        databaseHelper = new DatabaseHelper(addPetActivity.this);
        imageView = findViewById(R.id.imageViewCapture);
        captureBtn = findViewById(R.id.captureBtn);

        if(ContextCompat.checkSelfPermission(addPetActivity.this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(addPetActivity.this, new String[] {Manifest.permission.CAMERA}, 100);
        }
        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });
    }
    public void saveButton(View view) {

        TextView editMeno = findViewById(R.id.editMeno);
        TextView editDruh = findViewById(R.id.editDruh);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        boolean imageTaken = false;

        if(imageView.getDrawable() != null) {
            Bitmap bm = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            imageTaken = true;

        }
        else if (imageView.getDrawable() == null) {

            Toast.makeText(getApplicationContext(), "Spravte fotku", Toast.LENGTH_SHORT).show();
        }

        byte[] fotka = baos.toByteArray();
        String meno = editMeno.getText().toString();
        String druh = editDruh.getText().toString();
        if (meno.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Vyplnte meno", Toast.LENGTH_SHORT).show();
        }
        if(!meno.isEmpty() && imageTaken) {
            if(databaseHelper.addPetToDatabase(meno, druh, fotka)) {


                Toast.makeText(getApplicationContext(), "Meno vlozene...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }

        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 100) {
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(captureImage);

        }
    }
}