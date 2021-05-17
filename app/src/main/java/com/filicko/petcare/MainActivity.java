package com.filicko.petcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton addPetBtn;

    private static DatabaseHelper databaseHelper;

    GridView gridView;

    ArrayList<String> names = new ArrayList<String>();
    ArrayList<Bitmap> images = new ArrayList<Bitmap>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        databaseHelper = new DatabaseHelper(MainActivity.this);

        addPetBtn = findViewById(R.id.addPetBtn);

        ArrayList<String> mena;
        ArrayList<byte[]> fotky;

        mena = databaseHelper.getData("meno");
        fotky = databaseHelper.getData("image");
        for(int i = 0; i < mena.size(); i++) {
            names.add(mena.get(i));
            images.add(BitmapFactory.decodeByteArray(fotky.get(i), 0, fotky.get(i).length));

        }
        gridView = findViewById(R.id.gridView);

        CustomAdapter customAdapter = new CustomAdapter(names, images, this);
        gridView.setAdapter(customAdapter);

        addPetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddPetActivity();
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position);

                openPetActivity(position);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        moveTaskToBack(true);
    }

    /**
     * otvori AddPetActivity
     */
    public void openAddPetActivity() {
        Intent intent = new Intent(this, addPetActivity.class);
        startActivity(intent);

    }

    /**
     * otvori petAktivitu s konkretnym petom ktory je na position
     * @param position pozicia peta
     */
    public void openPetActivity(int position) {
        Intent intent = new Intent(this, petActivity.class).putExtra("position", position);
        startActivity(intent);

    }

    /**
     * Adapter pre grid view
     */
    public class CustomAdapter extends BaseAdapter {
        private ArrayList<String> imageNames;
        private ArrayList<Bitmap> imagePhoto;
        private Context context;
        private LayoutInflater layoutInflater;

        /**
         *
         * @param imageNames meno peta
         * @param imagePhoto fotka peta
         * @param context context aktivity
         */
        public CustomAdapter(ArrayList<String> imageNames, ArrayList<Bitmap> imagePhoto, Context context) {
            this.imageNames = imageNames;
            this.imagePhoto = imagePhoto;
            this.context = context;
            this.layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if(view == null) {
                view = layoutInflater.inflate(R.layout.row_items, viewGroup, false);
            }

                TextView tvName = view.findViewById(R.id.tvName);
                ImageView imageView = view.findViewById(R.id.imageViewEditPet);

                tvName.setText(imageNames.get(i));
                imageView.setImageBitmap(imagePhoto.get(i));

            return view;
        }
    }

}