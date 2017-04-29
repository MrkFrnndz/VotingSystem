package com.example.programmer2.votingsystem;

/**
 * Created by PROGRAMMER2 on 4/22/2017.
 */
public class Voter {
   // private int voterId;
    private String voterNumber;
    private String voterPassword;

    public Voter() {
    }

    public Voter(String voterNumber, String voterPassword) {
        //this.voterId = voterId;
        this.voterNumber = voterNumber;
        this.voterPassword = voterPassword;
    }

//    public int getVoterId() {
//        return voterId;
//    }
//
//    public void setVoterId(int voterId) {
//        this.voterId = voterId;
//    }

    public String getVoterPassword() {
        return voterPassword;
    }

    public void setVoterPassword(String voterPassword) {
        this.voterPassword = voterPassword;
    }

    public String getVoterNumber() {
        return voterNumber;
    }

    public void setVoterNumber(String voterNumber) {
        this.voterNumber = voterNumber;
    }


}
