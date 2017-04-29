package com.example.programmer2.votingsystem;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by PROGRAMMER2 on 4/20/2017.
 */
public class CandidateListAdapter extends BaseAdapter{

    private Context context;
    private int layout;
    private ArrayList<Candidate> candidateList;

    public CandidateListAdapter(Context context, int layout, ArrayList<Candidate> candidateList) {
        this.context = context;
        this.layout = layout;
        this.candidateList = candidateList;
    }

//    public CandidateListAdapter(Context context) {
//        this.context = context;
//    }

    @Override
    public int getCount() {
        return candidateList.size();
    }

    @Override
    public Object getItem(int position) {
        return candidateList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtName,txtDescription,txtVoteCount;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();
        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtName = (TextView) row.findViewById(R.id.txtName);
            holder.txtDescription = (TextView) row.findViewById(R.id.txtDescription);
            holder.imageView = (ImageView) row.findViewById(R.id.candidateImage);
            holder.txtVoteCount = (TextView) row.findViewById(R.id.txtVoteCount);
            row.setTag(holder);

        }
        else{
            holder = (ViewHolder) row.getTag();
        }

        Candidate candidate = candidateList.get(position);

        holder.txtName.setText(candidate.getName());
        holder.txtDescription.setText(candidate.getDescription());

        byte[] candidateImage = candidate.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(candidateImage, 0, candidateImage.length);
        holder.imageView.setImageBitmap(bitmap);

        //Convert int Vote Count to String in order to pass as String in textview
        int mCount = candidate.getVoteCount();
        String strCount = String.valueOf(mCount);

        holder.txtVoteCount.setText(strCount);

        return row;
    }



}
