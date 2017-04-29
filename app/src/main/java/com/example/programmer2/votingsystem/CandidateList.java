package com.example.programmer2.votingsystem;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by PROGRAMMER2 on 4/20/2017.
 */
public class CandidateList extends AppCompatActivity {

    GridView gridView;
    ArrayList<Candidate> candidatelist;
    CandidateListAdapter adapter = null;
    SQLiteHelper sqLiteHelper = new SQLiteHelper(this);

    Cursor candidatetCursor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.candidate_list_activity);

        candidatetCursor = sqLiteHelper.getData("SELECT * FROM candidates");

        gridView = (GridView) findViewById(R.id.gridView);
        candidatelist = new ArrayList<>();
        adapter = new CandidateListAdapter(this, R.layout.candidate_item, candidatelist);
        gridView.setAdapter(adapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(),CandidateDetails.class);
                //PASS INDEX
                intent.putExtra("ID",candidatelist.get(position).getId());
                intent.putExtra("NAME",candidatelist.get(position).getName());
                intent.putExtra("DESCRIPTION",candidatelist.get(position).getDescription());
                intent.putExtra("IMAGE",candidatelist.get(position).getImage());
                intent.putExtra("VOTECOUNT",candidatelist.get(position).getVoteCount());
                startActivity(intent);
            }
        });

        //GET ALL DATA FROM SQLITE
        candidatelist.clear();

        while (candidatetCursor.moveToNext()){
            int id = candidatetCursor.getInt(0);
            String name = candidatetCursor.getString(1);
            String description = candidatetCursor.getString(2);
            byte[] image = candidatetCursor.getBlob(3);
            int votecount = candidatetCursor.getInt(4);
            candidatelist.add(new Candidate(id, name, description, image, votecount));
        }
        adapter.notifyDataSetChanged();
    }
}
