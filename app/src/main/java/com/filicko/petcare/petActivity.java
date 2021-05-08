package com.filicko.petcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class petActivity extends AppCompatActivity {
    int position;
    RecyclerView medInfoView;
    MedInfoAdapter adapter;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);

        position = getIntent().getIntExtra("position", 1);
        databaseHelper = new DatabaseHelper(petActivity.this);
        byte[] fotka = (byte[]) databaseHelper.getDataAtIndex(position,"image").get(0);
        Bitmap bm = BitmapFactory.decodeByteArray(fotka, 0, fotka.length);
        ImageView imageView = findViewById(R.id.imageViewEditPet);
        imageView.setImageBitmap(bm);
        TextView textViewName = findViewById(R.id.tvName);
        textViewName.setText((String) databaseHelper.getDataAtIndex(position, "meno").get(0));
        TextView textViewDruh = findViewById(R.id.tvDruh);
        textViewDruh.setText((String) databaseHelper.getDataAtIndex(position, "druh").get(0));

        FloatingActionButton editPetBtn = findViewById(R.id.editPetBtn);

        medInfoView = findViewById(R.id.medInfoView);
        setRecyclerView();

        editPetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditPetActivity(position);
            }
        });

    }

    private void setRecyclerView() {
        medInfoView.setHasFixedSize(true);
        medInfoView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MedInfoAdapter(this, getList());
        medInfoView.setAdapter(adapter);
    }
    private ArrayList<MedInfoModel> getList() {
        ArrayList<MedInfoModel> medInfoArrayList = new ArrayList<>();
        ArrayList<String> datum = databaseHelper.getMedicalInfoData(position, "date");
        ArrayList<String> info = databaseHelper.getMedicalInfoData(position, "info");
        for(int i = 0; i < datum.size(); i++) {

            medInfoArrayList.add(new MedInfoModel(datum.get(i), info.get(i)));
        }

        return medInfoArrayList;
    }

    public void openEditPetActivity(int position) {
        startActivity(new Intent(this, EditPetActivity.class).putExtra("position", position));
    }

}