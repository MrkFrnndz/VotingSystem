package com.example.programmer2.votingsystem;

/**
 * Created by PROGRAMMER2 on 4/20/2017.
 */
public class Candidate {
    private int id;
    private String name;
    private String description;
    private byte[] image;
    private int voteCount;



    public Candidate(){

    }
    public Candidate(int id, String name, String description, byte[] image, int voteCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.voteCount = voteCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}
