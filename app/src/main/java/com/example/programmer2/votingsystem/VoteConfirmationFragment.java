package com.example.programmer2.votingsystem;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by PROGRAMMER2 on 4/25/2017.
 */
public class VoteConfirmationFragment extends DialogFragment {
    Button btnOk,btnCancel;
    TextView txtContainerforAddingOne;
    SQLiteHelper sqLiteHelper;
    Activity activity;

    public VoteConfirmationFragment() {
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof Activity){
//            activity = getActivity();
//        }
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.voteconfirmation_fragment, container, false);

        sqLiteHelper = new SQLiteHelper(getActivity());

        Bundle bundle = this.getArguments();
//
//        int dummy1 = 0;
//        int dummy2 = 0;
        final int id = bundle.getInt("ID",0);
        final int oldCount = bundle.getInt("VOTECOUNT",0);
        String strOldCount = Integer.toString(oldCount);
        txtContainerforAddingOne = (TextView)rootView.findViewById(R.id.txtContainer);
        txtContainerforAddingOne.setText(strOldCount);
//        String strOldCount = String.valueOf(oldCount);


       // Cursor candidatetCursor = sqLiteHelper.getData("SELECT FROM candidates WHERE id='"+ id +"'");
        //final int newCount = candidatetCursor.getInt(4);

        getDialog().setTitle("Vote Confirmation");
        btnOk =(Button) rootView.findViewById(R.id.btnOk);
        btnCancel =(Button) rootView.findViewById(R.id.btnCancel);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int convertedAddOne = Integer.parseInt(txtContainerforAddingOne.getText().toString());
                updateVoteCount(id,convertedAddOne + 1);

//                SendEvent sEvent = new SendEvent();
//                sEvent.setFired(true);
//                sEvent.setSendId(id);
//                sEvent.setSendVote(oldCount);
//                EventBus.getDefault().post(sEvent);

                dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return rootView;
    }

    public void updateVoteCount(int cid,int newCount){
        sqLiteHelper.update_voteCount(cid,newCount);

//        Toast.makeText(getActivity(), "Vote Casted Successfully!", Toast.LENGTH_SHORT).show();
        sqLiteHelper.close();
    }
}
