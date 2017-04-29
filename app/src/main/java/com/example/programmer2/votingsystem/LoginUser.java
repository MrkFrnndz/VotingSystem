package com.example.programmer2.votingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by PROGRAMMER2 on 4/22/2017.
 */
public class LoginUser extends AppCompatActivity{

    private EditText etUserNumber,etUserPassword;
    private Button btnLogin,btnAddUser;
    private TextView tvVotersList;

    private ArrayList<Voter> voterList;

    SQLiteHelper sqLiteHelper = new SQLiteHelper(this);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_user_activity);

        etUserNumber = (EditText) findViewById(R.id.etUserid);
        etUserPassword = (EditText) findViewById(R.id.etUserpass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnAddUser = (Button) findViewById(R.id.btnAddUser);
        tvVotersList = (TextView) findViewById(R.id.txtVotersList);

        voterList = new ArrayList<>();

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String vnum = etUserNumber.getText().toString().trim();
                    String vpass = etUserPassword.getText().toString().trim();

                    int result= sqLiteHelper.addingVoterValidation(vnum);

                    Voter voter = new Voter();


                    if(result > 0){
                        Toast.makeText(LoginUser.this, vnum + " is already taken!", Toast.LENGTH_SHORT).show();
                    }
                    else if(etUserNumber.getText().toString().isEmpty() && etUserPassword.getText().toString().isEmpty()){
                        Toast.makeText(getApplicationContext(), "Fill all fields!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        voter.setVoterNumber(vnum);
                        voter.setVoterPassword(etUserPassword.getText().toString());
                        sqLiteHelper.insertVoterData(vnum,vpass);
                        Toast.makeText(getApplicationContext(), "Successfully Added Voter!", Toast.LENGTH_SHORT).show();

                        //tvVotersList.setText("Voter " + " Number: "+voter.getVoterNumber());
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
//                if(sqLiteHelper.checkVoter(etUserNumber.getText().toString().trim(), etUserPassword.getText().toString().trim())){
//                    Toast.makeText(LoginUser.this, "Login Successfull!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(LoginUser.this, "Incorrect Number/Password!", Toast.LENGTH_SHORT).show();
//                }

                String vnum = etUserNumber.getText().toString().trim();
                String vpass = etUserPassword.getText().toString().trim();

                int result = sqLiteHelper.loginValidation(vnum,vpass);
                if(result > 0){
                    Intent intent = new Intent(getApplicationContext(),VoteActivity.class);
                    startActivity(intent);

                    Toast.makeText(LoginUser.this, "Login Successfull!", Toast.LENGTH_SHORT).show();
                }
                else if(vnum.isEmpty() && vpass.isEmpty()){
                    Toast.makeText(LoginUser.this, "Fill all Fields!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(LoginUser.this, "Incorrect Number/Password!", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
