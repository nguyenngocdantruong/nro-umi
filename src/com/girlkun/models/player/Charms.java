package com.girlkun.models.player;


public class Charms {
    
    public long tdTriTue;
    public long tdManhMe;
    public long tdDaTrau;
    public long tdOaiHung;
    public long tdBatTu;
    public long tdDeoDai;
    public long tdThuHut;
    public long tdDeTu;
    public long tdTriTue3;
    public long tdTriTue4;
    
    public long lastTimeSubMinTriTueX4;

    public void addTimeCharms(int itemId, int min) {
        long curr = System.currentTimeMillis();
        switch (itemId) {
            case 213:
                if (tdTriTue < curr) {
                    tdTriTue = curr;
                }
                tdTriTue += min * 60 * 1000;
                break;
            case 214:
                if (tdManhMe < curr) {
                    tdManhMe = curr;
                }
                tdManhMe += min * 60 * 1000;
                break;
            case 215:
                if (tdDaTrau < curr) {
                    tdDaTrau = curr;
                }
                tdDaTrau += min * 60 * 1000;
                break;
            case 216:
                if (tdOaiHung < curr) {
                    tdOaiHung = curr;
                }
                tdOaiHung += min * 60 * 1000;
                break;
            case 217:
                if (tdBatTu < curr) {
                    tdBatTu = curr;
                }
                tdBatTu += min * 60 * 1000;
                break;
            case 218:
                if (tdDeoDai < curr) {
                    tdDeoDai = curr;
                }
                tdDeoDai += min * 60 * 1000;
                break;
            case 219:
                if (tdThuHut < curr) {
                    tdThuHut = curr;
                }
                tdThuHut += min * 60 * 1000;
                break;
            case 522:
                if (tdDeTu < curr) {
                    tdDeTu = curr;
                }
                tdDeTu += min * 60 * 1000;
                break;
            case 671:
                if (tdTriTue3 < curr) {
                    tdTriTue3 = curr;
                }
                tdTriTue3 += min * 60 * 1000;
                break;
            case 672:
                if (tdTriTue4 < curr) {
                    tdTriTue4 = curr;
                }
                tdTriTue4 += min * 60 * 1000;
                break;
        }
    }
    
    public void dispose(){
    }
}
