package com.example.programmer2.votingsystem;

import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * Created by PROGRAMMER2 on 4/22/2017.
 */
public class VoteActivity extends AppCompatActivity{
    private GridView gridView;
    private ArrayList<Candidate> candidatelist;
    private CandidateListAdapter adapter = null;
    private SQLiteHelper sqLiteHelper = new SQLiteHelper(this);

    private Cursor candidateListCursor ;

//    private int counter = 0;
    private boolean receivedResult;

    private int receivedCount,receivedId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.candidate_list_activity);

        EventBus.getDefault().register(this);

        candidateListCursor = sqLiteHelper.getData("SELECT * FROM candidates");

        gridView = (GridView) findViewById(R.id.gridView);
        candidatelist = new ArrayList<>();
        adapter = new CandidateListAdapter(this, R.layout.candidate_item, candidatelist);
        gridView.setAdapter(adapter);

        //init. Dialog fragment
        final VoteConfirmationFragment vcDialogFrag = new VoteConfirmationFragment();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                candidatelist.get(position).getId();
                candidatelist.get(position).getVoteCount();
                //SEND ITEM POSITION USING BUNDLE
                Bundle bundle = new Bundle();
                bundle.putInt("ID", candidatelist.get(position).getId());
                bundle.putInt("VOTECOUNT", candidatelist.get(position).getVoteCount());
                vcDialogFrag.setArguments(bundle);

                vcDialogFrag.show(getFragmentManager(),"My Dialog Fragment");
                vcDialogFrag.getFragmentManager().executePendingTransactions();
            }
        });

        //UPDATE VOTE COUNT
        //if(receivedResult || candidateListCursor != null || candidateListCursor == null){
            //GET ALL DATA FROM SQLITE
            candidatelist.clear();
            while (candidateListCursor.moveToNext()){
                int id = candidateListCursor.getInt(0);
                String name = candidateListCursor.getString(1);
                String description = candidateListCursor.getString(2);
                byte[] image = candidateListCursor.getBlob(3);
                int votecount = candidateListCursor.getInt(4);
                candidatelist.add(new Candidate(id, name, description, image, votecount));
            }
            adapter.notifyDataSetChanged();

//            Toast.makeText(VoteActivity.this, "occur: "+ receivedResult, Toast.LENGTH_SHORT).show();
//            int addVote = 1;
//            int sumOfVotes = receivedCount + addVote;
//            updateVoteCount(receivedId, sumOfVotes);
//
//            adapter.notifyDataSetChanged();
        //}


    }

    @Subscribe
    public void onEvent(SendEvent event){
        receivedResult = event.isFired();
        receivedId = event.getSendId();
        receivedCount = event.getSendVote();

//        Toast.makeText(VoteActivity.this, "Result:"+ receivedResult, Toast.LENGTH_SHORT).show();
    }

//    public void updateVoteCount(int id,String newCount){
//        sqLiteHelper.update_voteCount(id,newCount);
//        sqLiteHelper.close();
//    }
}
