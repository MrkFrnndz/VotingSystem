package com.example.programmer2.votingsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.sql.Blob;

/**
 * Created by PROGRAMMER2 on 4/20/2017.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DBVoting.db";
//    private static final String DATABASE_NAME = "VotingSystemDB.db";
    private static final String TABLE_CANDIDATES = "candidates";
    private static final String TABLE_VOTERS = "voters";

    private static final String COLUMN_ID_CANDIDATES = "id";
    private static final String COLUMN_NAME_CANDIDATES = "name";
    private static final String COLUMN_DESCRIPTION_CANDIDATES = "description";
    private static final String COLUMN_IMAGE_CANDIDATES = "image";
    private static final String COLUMN_VOTECOUNT_CANDIDATES = "votecount";

    private static final String COLUMN_ID_VOTERS = "id";
    private static final String COLUMN_VOTERNUMBER_VOTERS = "number";
    private static final String COLUMN_PASSWORD_VOTERS = "password";

    private static final String TABLE_CANDIDATES_CREATE = "create table if not exists "+TABLE_CANDIDATES+" ("+COLUMN_ID_CANDIDATES+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+COLUMN_NAME_CANDIDATES+" TEXT, "+COLUMN_DESCRIPTION_CANDIDATES+" TEXT, "+COLUMN_IMAGE_CANDIDATES+" BLOB, "+COLUMN_VOTECOUNT_CANDIDATES+" INTEGER)";
    private static final String TABLE_VOTERS_CREATE = "create table if not exists "+TABLE_VOTERS+" ("+COLUMN_ID_VOTERS+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+COLUMN_VOTERNUMBER_VOTERS+" TEXT, "+COLUMN_PASSWORD_VOTERS+" TEXT)";

    //sqLiteHelper = new SQLiteHelper(this,"CandidatesDB.sqlite",null,1);
    //sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS candidates (Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, description VARCHAR, image BLOB)");

//    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }
    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(TABLE_CANDIDATES_CREATE);
        database.execSQL(TABLE_VOTERS_CREATE);
    }

    public void queryData(String sql){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(sql);
    }

    public void update_candidates(int id, String newName, String newDescription, int newCount){
        this.getWritableDatabase().execSQL("UPDATE "+TABLE_CANDIDATES+" SET "+COLUMN_NAME_CANDIDATES+"='" + newName + "' ,description='"+ newDescription +"' ,votecount='"+newCount+"' WHERE id='" + id + "'");
    }

    public void update_voteCount(int id, int newCount){
        this.getWritableDatabase().execSQL("UPDATE "+TABLE_CANDIDATES+" SET "+COLUMN_VOTECOUNT_CANDIDATES+"='" + newCount + "' WHERE id='" + id + "'");
    }

    public void delete_candidates(int id){
        try
        {
            this.getWritableDatabase().execSQL("DELETE FROM "+TABLE_CANDIDATES+" WHERE id='" + id + "'");
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

//    public void insertVoterData(Voter voter){
//        SQLiteDatabase database = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
////        String query = "select * from "+TABLE_VOTERS+"";
////        Cursor cursor = database.rawQuery(query, null);
////        int votersCount = cursor.getCount();
////
////        values.put(COLUMN_ID_VOTERS, votersCount);
//        values.put(COLUMN_VOTERNUMBER_VOTERS, voter.getVoterNumber());
//        values.put(COLUMN_PASSWORD_VOTERS, voter.getVoterPassword());
//
//        database.insert(TABLE_VOTERS , null , values);
//        database.close();
//    }

    public void insertVoterData(String vname,String vpass){
        SQLiteDatabase database = this.getWritableDatabase();
        String sql = "INSERT INTO "+TABLE_VOTERS+" VALUES (NULL, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1,vname);
        statement.bindString(2,vpass);

        statement.executeInsert();
        database.close();
    }

    public int addingVoterValidation(String vname) {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] selectionArgs = new String[]{ vname };
        try {
            int i = 0;
            Cursor cursor = null;
            cursor = database.rawQuery("select * from " + TABLE_VOTERS + " where " + COLUMN_VOTERNUMBER_VOTERS + "=?", selectionArgs);
            cursor.moveToFirst();
            i = cursor.getCount();
            cursor.close();
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int loginValidation(String vname,String vpass)
    {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] selectionArgs = new String[]{vname, vpass};
        try
        {
            int i = 0;
            Cursor cursor = null;
            cursor = database.rawQuery("select * from "+TABLE_VOTERS+" where "+COLUMN_VOTERNUMBER_VOTERS+"=? and "+ COLUMN_PASSWORD_VOTERS+"=?", selectionArgs);
            cursor.moveToFirst();
            i = cursor.getCount();
            cursor.close();
            return i;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

//    public String searchVoterNumber(String intVnum){
//        SQLiteDatabase database = this.getReadableDatabase();
//        String query = "SELECT "+COLUMN_VOTERNUMBER_VOTERS+", "+COLUMN_PASSWORD_VOTERS+" FROM "+TABLE_VOTERS+"";
//        Cursor cursor = database.rawQuery(query, null);
//        String a=""; //Voters Number
//
//        if(cursor.moveToFirst()){
//            do{
//                a = cursor.getString(1);
//                if(a == intVnum){
//                    a = cursor.getString(1);
//                    break;
//                }
//            }while (cursor.moveToNext());
//        }
//        return a;
//    }

//    public String searchPass(String vnum){
//        SQLiteDatabase database = this.getReadableDatabase();
//        String query = "SELECT "+COLUMN_VOTERNUMBER_VOTERS+", "+COLUMN_PASSWORD_VOTERS+" FROM "+TABLE_VOTERS+"";
//        Cursor cursor = database.rawQuery(query, null);
//        String a; //Voters Number
//        String b; // Voters Password
//        b = "not found!";
//        if(cursor.moveToFirst()){
//            do{
//                a = cursor.getString(1);
//                if(a == vnum){
//                    b = cursor.getString(2);
//                    break;
//                }
//            }while (cursor.moveToNext());
//        }
//        return b;
//    }

    public void insertData(Candidate candidate){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME_CANDIDATES, candidate.getName());
        values.put(COLUMN_DESCRIPTION_CANDIDATES, candidate.getDescription());
        values.put(COLUMN_IMAGE_CANDIDATES, candidate.getImage());
        values.put(COLUMN_VOTECOUNT_CANDIDATES, candidate.getVoteCount());

        database.insert(TABLE_CANDIDATES , null , values);
        database.close();
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = this.getReadableDatabase();
        return database.rawQuery(sql,null);
    }

//    public Cursor getVoterData(String sql){
//        SQLiteDatabase database = this.getReadableDatabase();
//        return database.rawQuery(sql,null);
//    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
