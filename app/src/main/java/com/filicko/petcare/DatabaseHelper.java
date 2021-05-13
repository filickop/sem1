package com.filicko.petcare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database";
    private static final String TABLE_NAME = "basicInfo";
    private static final String TABLE_MEDICAL_INFO = "medicalInfo";
    private static final String TABLE_REMINDERS ="reminders";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create Table
        String createTableBasicInfo = "create table " + TABLE_NAME + "(id INTEGER PRIMARY KEY, meno TEXT, druh TEXT, image BLOB)";
        String createTableMedicalInfo =  "create table " + TABLE_MEDICAL_INFO + "(id INTEGER PRIMARY KEY, idPet INTEGER, info TEXT, date TEXT)";
        String createTableReminders = "create table " + TABLE_REMINDERS + "(id INTEGER PRIMARY KEY, idPet INTEGER, idBudik INTEGER, time TEXT, jeZapnuty INTEGER)";
                db.execSQL(createTableBasicInfo);
                db.execSQL(createTableMedicalInfo);
                db.execSQL(createTableReminders);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }
    public boolean addPetToDatabase(String meno, String druh, byte[] image) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("meno", meno);
        contentValues.put("druh", druh);
        contentValues.put("image", image);
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return true;
    }
    public boolean addMedicalInfoToDatabase(int idPet, String info, String date) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idPet", idPet + 1);
        contentValues.put("info", info);
        contentValues.put("date", date);
        sqLiteDatabase.insert(TABLE_MEDICAL_INFO, null, contentValues);
        return true;
    }
    public boolean addBudikToDatabase(int idPet, int idBudik, String cas, int jeZapnuty) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        int id = jeVDatabaze(idPet, idBudik);
        contentValues.put("time", cas);
        contentValues.put("jeZapnuty", jeZapnuty);
        contentValues.put("idPet", idPet);
        contentValues.put("idBudik", idBudik);
        if(id > 0) {
            Cursor cursor = sqLiteDatabase.rawQuery("select * from "+ TABLE_REMINDERS + " where id = ?", new String[] {String.valueOf(id)});
            long result = sqLiteDatabase.update(TABLE_REMINDERS, contentValues,"id=?", new String[] {String.valueOf(id)});
            if (result == -1) {
                return false;
            }
            else {
                return true;
            }
        }
        else {
            sqLiteDatabase.insert(TABLE_REMINDERS, null, contentValues);
            return true;
        }
    }
    public int jeVDatabaze(int idPet, int idBudik) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        System.out.println(idPet + " pet");
        System.out.println(idBudik + " budik");
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_REMINDERS + " where idPet = ?", new String[] {String.valueOf(idPet)});
        while (cursor.moveToNext()) {
            if(cursor.getString(2).equals(String.valueOf(idBudik))) {
                return Integer.parseInt(cursor.getString(0));
            }
        }
        return -1;

    }
    public boolean updatePetToDatabase(String meno, String druh, byte[] image, int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("meno", meno);
        contentValues.put("druh", druh);
        contentValues.put("image", image);
        Cursor cursor = sqLiteDatabase.rawQuery("select * from basicInfo where id = ?", new String[] {String.valueOf(id + 1)});
        if(cursor.getCount() > 0) {
            long result = sqLiteDatabase.update(TABLE_NAME, contentValues,"id=?", new String[] {String.valueOf(id + 1)});
            if (result == -1) {
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }

    }
    public ArrayList getData(String column) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList arrayList;
        if(!column.equals("image")) {
            arrayList = new ArrayList<String>();
        }
        else {
            arrayList = new ArrayList<byte[]>();
        }
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            if(!column.equals("image")) {
                arrayList.add(cursor.getString(cursor.getColumnIndex(column)));
            }
            else {
                arrayList.add(cursor.getBlob(cursor.getColumnIndex("image")));
            }

            cursor.moveToNext();
        }
        return arrayList;
    }
    public ArrayList getMedicalInfoData(int idPet, String column) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<String>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from medicalInfo where idPet = ?", new String[] {String.valueOf(idPet + 1)});
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            arrayList.add(cursor.getString(cursor.getColumnIndex(column)));
            cursor.moveToNext();
        }

        return arrayList;
    }
    public ArrayList getDataAtIndex(int index, String column) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList arrayList;
        if(!column.equals("image")) {
            arrayList = new ArrayList<String>();
        }
        else {
            arrayList = new ArrayList<byte[]>();
        }
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME, null);
        cursor.moveToFirst();
        cursor.move(index);
        if(!column.equals("image")) {
            arrayList.add(cursor.getString(cursor.getColumnIndex(column)));
        }
        else {
            arrayList.add(cursor.getBlob(cursor.getColumnIndex("image")));
        }
        return arrayList;
    }
    public String getCas(int idPet, int idBudik) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_REMINDERS + " where idPet = ?", new String[] {String.valueOf(idPet + 1)});
        while (cursor.moveToNext()) {
            if(cursor.getString(2).equals(String.valueOf(idBudik))) {
                return (cursor.getString(3));
            }
        }
        return "Nastav čas";
    }
    public boolean getChecked(int idPet, int idBudik) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_REMINDERS + " where idPet = ?", new String[] {String.valueOf(idPet + 1)});
        while (cursor.moveToNext()) {
            if(cursor.getString(2).equals(String.valueOf(idBudik))) {
                return (cursor.getInt(4)) == 1;
            }
        }
        return false;
    }
    public Cursor getCur(String table) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        return sqLiteDatabase.rawQuery("select * from " + table , null);
    }
    public boolean compareTimes(String cas1) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date cas = new Date();
        Date aktualnyCas = new Date();
        String aktCas = aktualnyCas.getHours() + ":" + aktualnyCas.getMinutes();
        try {
            cas = sdf.parse(cas1);
            aktualnyCas = sdf.parse(aktCas);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        long c1 = cas.getTime();
        long c2 = aktualnyCas.getTime();

        return c1 - c2  == 0;

    }
}
