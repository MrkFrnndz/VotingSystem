package com.example.programmer2.votingsystem;

/**
 * Created by PROGRAMMER2 on 4/25/2017.
 */
public class SendEvent {

    private boolean fired;
    private int sendId;
    private int sendVote;

    public int getSendVote() {
        return sendVote;
    }

    public void setSendVote(int sendVote) {
        this.sendVote = sendVote;
    }

    public int getSendId() {
        return sendId;
    }

    public void setSendId(int sendId) {
        this.sendId = sendId;
    }

    public boolean isFired() {
        return fired;
    }

    public void setFired(boolean fired) {
        this.fired = fired;
    }
}
