package com.filicko.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class EditPetActivity extends AppCompatActivity {

    int position;
    byte[] fotka;
    Bitmap bm;
    TextView editMeno;
    TextView editDruh;
    ImageView imageView;
    DatabaseHelper databaseHelper;
    Button captureBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pet);
        databaseHelper = new DatabaseHelper(this);
        imageView = findViewById(R.id.imageViewEditPet);

        editMeno = findViewById(R.id.editMeno);
        editDruh = findViewById(R.id.editDruh);
        position = getIntent().getIntExtra("position", 1);
        fotka = (byte[]) databaseHelper.getDataAtIndex(position, "image").get(0);
        bm = BitmapFactory.decodeByteArray(fotka, 0, fotka.length);
        editMeno.setText((String) databaseHelper.getDataAtIndex(position, "meno").get(0));
        editDruh.setText((String) databaseHelper.getDataAtIndex(position, "druh").get(0));
        imageView.setImageBitmap(bm);
        captureBtn = findViewById(R.id.captureBtn);
        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });

    }

    /**
     * spusti aktivitu editMedicalInfo pre konkretneho peta
     * @param view pre funkcnost tlacidla
     */
    public void medicalInfoBtn(View view) {
        startActivity(new Intent(this, EditMedicalInfo.class).putExtra("position", position));

    }

    /**
     * otvori aktivitu s notifikaciami
     * @param view pre funkcnost tlacidla
     */
    public void openNotificationsActivity(View view) {
        startActivity(new Intent(this, notification.class).putExtra("position", position));
    }

    /**
     * ulozi do databazy upravene udaje o petovi
     * @param view pre funkcnost tlacidla
     */
    public void saveEditBtn(View view) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        boolean imageTaken = false;

        if (imageView.getDrawable() != null) {
            bm = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            imageTaken = true;

        } else if (imageView.getDrawable() == null) {
            Toast.makeText(getApplicationContext(), "Spravte fotku", Toast.LENGTH_SHORT).show();
        }

        fotka = baos.toByteArray();
        String meno = editMeno.getText().toString();
        String druh = editDruh.getText().toString();
        System.out.println(meno);
        System.out.println(position);
        if (meno.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Vyplnte meno", Toast.LENGTH_SHORT).show();
        }
        if (!meno.isEmpty() && imageTaken) {
            if (databaseHelper.updatePetToDatabase(meno, druh, fotka, position)) {


                Toast.makeText(getApplicationContext(), "Pet zmeneny...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, petActivity.class).putExtra("position", position));
            }
        }
    }
    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, petActivity.class).putExtra("position", position));
    }
}
