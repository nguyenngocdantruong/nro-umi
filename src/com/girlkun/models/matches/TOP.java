package com.girlkun.models.matches;


public class TOP {
    private int id_player;
    private long power;
    private long ki;
    private long hp;
    private long sd;
    private byte nv;
    private int sk;
    private int pvp;
    private String info1;
    private String info2;
    public boolean online;

    public void setInfo1(String string) {
        this.info1 = string;
    }

    public void setInfo2(String string) {
        this.info2 = string;
    }
    
    public TOP(int id_player){
        this.id_player = id_player;
    }

    public String getInfo1() {
        return info1;
    }

    public String getInfo2() {
        return info2;
    }

    public int getId_player() {
        return id_player;
    }
    
}
