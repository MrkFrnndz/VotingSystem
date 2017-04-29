package com.example.programmer2.votingsystem;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by PROGRAMMER2 on 4/21/2017.
 */
public class CandidateDetails extends AppCompatActivity {

    EditText etName,etDescription,etContainerVoteCount;
    ImageView candidateImage;
    Button btnUpdate,btnDelete;

    SQLiteHelper sqLiteHelper = new SQLiteHelper(this);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.candidate_details);

        //RECEIVE DAT FROM CANDIDATELIST
        Intent intent = getIntent();

        final int id = intent.getExtras().getInt("ID");
        final String name = intent.getExtras().getString("NAME");
        final String description = intent.getExtras().getString("DESCRIPTION");
        final String image = intent.getExtras().getString("IMAGE");
//        byte[] strToByte = image.getBytes();
        final int vc = intent.getExtras().getInt("VOTECOUNT");

        etName = (EditText) findViewById(R.id.etName);
        etDescription = (EditText) findViewById(R.id.etDescription);
        candidateImage = (ImageView) findViewById(R.id.candidateImage);
        etContainerVoteCount = (EditText) findViewById(R.id.etContainerVoteCount);

        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        //ASSIGN DATA
        etName.setText(name);
        etDescription.setText(description);
        String strCount = Integer.toString(vc);
        etContainerVoteCount.setText(strCount);
//        byteToImageView(strToByte);
        //candidateImage.setImageBitmap(byteToImageView(strToByte));
//        candidateImage.setImageResource();
//        Toast.makeText(CandidateDetails.this, "Image:" + image, Toast.LENGTH_SHORT).show();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mName = etName.getText().toString();
                String mDescription = etDescription.getText().toString();
                int mVcount = Integer.parseInt(etContainerVoteCount.getText().toString());
                if(!TextUtils.isEmpty(mName) && !TextUtils.isEmpty(mDescription)) {
                    update(id, mName, mDescription, mVcount);
                }
                else{
                    Toast.makeText(CandidateDetails.this, "Fill all fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(id);
            }
        });
    }

    public void update(int id,String newName, String newDescription, int newCount){
        sqLiteHelper.update_candidates(id,newName,newDescription,newCount);

//        etName.setText(newName);
//        etDescription.setText(newDescription);
        Toast.makeText(CandidateDetails.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        sqLiteHelper.close();
    }

    public void delete(int id){
        CandidateList clAdapter = new CandidateList();

        sqLiteHelper.delete_candidates(id);

        this.finish();
        Toast.makeText(CandidateDetails.this, "Record deleted!", Toast.LENGTH_SHORT).show();
        sqLiteHelper.close();

    }

//    private Bitmap byteToImageView(byte[] imgbyte) {
//
//        Bitmap bmp = BitmapFactory.decodeByteArray(imgbyte, 0, imgbyte.length);
//        ImageView image = (ImageView) findViewById(R.id.candidateImage);
//
//        image.setImageBitmap(Bitmap.createScaledBitmap(bmp, image.getWidth(),image.getHeight(), false));
//        return bmp;
//    }

}
