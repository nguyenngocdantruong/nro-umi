package com.girlkun.models.player;

import com.girlkun.models.item.Item;
import com.girlkun.models.map.Zone;
import com.girlkun.models.mob.Mob;
import com.girlkun.models.skill.Skill;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.ItemService;
import com.girlkun.services.PlayerService;
import com.girlkun.services.Service;
import com.girlkun.services.InventoryServiceNew;
import com.girlkun.services.ItemTimeService;
import com.girlkun.services.MapService;
import com.girlkun.services.SkillService;
import com.girlkun.utils.Logger;
import com.girlkun.utils.SkillUtil;
import com.girlkun.utils.Util;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class EffectSkin {
    
    //ID Cải trang
    
    public static final int XEN_1 = 526;
    public static final int XEN_2 = 527;
    public static final int XEN_3 = 528;
    public static final int VO_HINH = 449;
    public static final int XEN_CON = 549;
    
    
    public static final int ODO = 455;
    public static final int XINBATO = 458;
    public static final int BUIBUI = 575;
    public static final int YACON = 576;
    public static final int DRABULA = 577;
    public static final int MABU = 578;
    public static final int THO_BULMA = 584;
    public static final int THO_BULMA_HSD = 464;
    
    public static final int SAIBAMEN = 647;
    
    private static final String[] textOdo = new String[]{
        "Hôi quá", "Tránh ra đi thằng ở dơ", "Mùi gì kinh quá vậy?",
        "Kinh tởm quá", "Biến đi thằng ở dơ", "Kính ngài ở dơ"
    };
    
    private static final String[] textHoaDa = new String[]{
        "Chết rồi", "Tránh ra đi tên khốn!", "Bị hóa đá rồi",
        "Kinh tởm quá", "Bú đá thái tử"
    };
    
    //custom-drsylas
    private static final String[] textBuiBui = new String[]{
        "Nặng quá", "Tránh ra đi BuiBui", "Chân mình nặng quá vậy?",
        "Biến đi", "Ai buộc gì vào chân tôi vậy!"
    };
    
    //custom-drsylas
    private static final String[] textXinbato = new String[]{
        "Im mồm đi ông Xinbato ơi!", "Khó chịu quá!", "Tiếng gì mà ồn vậy?",
        "Ai đấy cho anh ấy nước đi", "Tránh ra ông xinbato ơi"
    };
    
    //custom-drsylas
    private static final String[] textBulma = new String[]{
      "Wow, sexy quá", "Đẹp quá"
    };
    
    private Player player;
    public boolean isBiHoaXuong;

    public EffectSkin(Player player) {
        this.player = player;
        this.xHPKI = 1;
    }
    
    private long lastTimeBuiBui;
    public long lastTimeAttack;
    private long lastTimeOdo;
    private long lastTimeXenHutHpKi;
    public long lastTimeAddTimeTrainArmor;
    public long lastTimeSubTimeTrainArmor;
    public boolean isVoHinh;
    public long lastTimeXHPKI;
    public int xHPKI;
    public long lastTimeUpdateCTHT;
    
    public long lastTimeThucHienHoaDa;
    public long lastTimeBiHoaDa;
    private final int TIME_HOA_DA = 10000;
    private static final int TIME_HOA_DA_PER_SECOND = 20000;
    
    //custom-drsylas
    public boolean isMabu = false;
    public int mabuIDCaiTrang = -1;//ID cải trang mà Mabu đang copy
    private long lastTimeUseMabu = 5000;
    private Thread cancelMabuCopy;
    private final int TIME_MABU = 30000;
    private final int TIME_MABU_PER_SECOND = 50000;
    public short[] bodyCopy = new short[]{297, 298, 299}; //Body Mabu
    private long lastTimeXinbato;
    private long lastTimeBulma;
    
    //Effect halloween
    public long lastTimeBiHoaXuong;
    public int timeHoaXuong = 300;
    public short[] bodyHalloween = new short[]{545, 548, 549}; 
        
    // --------------------------------------------  Hiệu ứng bị áp đặt từ boss/player khác qua attack
    
    // Hiệu ứng biến thành cà rốt
    public boolean isCarrot;
    public long lastTimeBiBienThanhCarrot;
    public final int TIME_BIEN_CARROT =  300000;
    public short[] bodyCarrot = new short[]{406, 407, 408}; 
    
    // --------------------------------------------  

    public void update() {
        if(this.player != null){
            updateVoHinh();
            updateHoaDa();
            updateCarrot();
            updateHoaXuong();
            updateMabu();
            updateThoBulma();
            updateHasBuiBui();
            if (this.player.zone != null && !MapService.gI().isMapOffline(this.player.zone.map.mapId)) {
                updateOdo();
                updateXenHutXungQuanh();
                updateXinbato();
            }
            if (!this.player.isBoss && !this.player.isPet && !player.isNewPet) {
                updateTrainArmor();
            }
            if (xHPKI != 1 && Util.canDoWithTime(lastTimeXHPKI, 1800000)) {
                xHPKI = 1;
                Service.gI().point(player);
            }
            updateCTHaiTac();
        }
        else{
            dispose();
        }
        
    }

    private void updateHoaDa() {
        try {
            boolean CoCaiTrangHoaDa = this.player.nPoint.CoCaiTrangHoaDa || mabuIDCaiTrang == DRABULA;
            if (CoCaiTrangHoaDa) {
                boolean Check = System.currentTimeMillis() - lastTimeThucHienHoaDa > TIME_HOA_DA_PER_SECOND;
                if (Check) { // thời gian hóa đá mỗi lần
                    for (Player pl : this.player.zone.getNotBosses()) {
                        if (!this.player.equals(pl) && !pl.isBoss && !pl.isDie() && Util.getDistance(this.player, pl) <= 200 && !pl.nPoint.IsBiHoaDa) {
                            pl.nPoint.IsBiHoaDa = true;
                            Service.gI().SendMsgUpdateHoaDa(pl, (byte) 1, (byte) 0, (byte) 42);
                            Service.gI().Send_Caitrang_HoaDa(pl);
                            pl.chat(textHoaDa[Util.nextInt(0, textHoaDa.length - 1)]);
                            ItemTimeService.gI().sendItemTime(pl, 4392, (int) TIME_HOA_DA / 1000);
                            pl.effectSkin.lastTimeBiHoaDa = System.currentTimeMillis();
                        }
                    }
                    this.lastTimeThucHienHoaDa = System.currentTimeMillis();
                    player.chat("Phẹt");
                }
            }
            if (this.player.nPoint.IsBiHoaDa && (System.currentTimeMillis() - lastTimeBiHoaDa > TIME_HOA_DA)) {
                this.player.nPoint.IsBiHoaDa = false;
                Service.gI().SendMsgUpdateHoaDa(this.player, (byte) 2, (byte) 0, (byte) 42);
                Service.gI().Send_Caitrang(this.player);
                ItemTimeService.gI().removeItemTime(this.player, 4392);
                Service.gI().chat(this.player, "Phẹt Phẹt Phẹt...");
            }
        } catch (Exception e) {
            Logger.error("");
        }
    }
    
    public void updateCarrot() {
        try {
            if (this.player.effectSkin.isCarrot && ((System.currentTimeMillis() - lastTimeBiBienThanhCarrot > TIME_BIEN_CARROT) || this.player.nPoint.khangTDHS)) {
                this.player.effectSkin.isCarrot = false;
                Service.gI().Send_Caitrang(this.player);
                ItemTimeService.gI().removeItemTime(this.player, 933);
            }
        } catch (Exception e) {
            Logger.error("");
        }
    }

    private void updateCTHaiTac() {
        if (this.player.setClothes.ctHaiTac != -1
                && this.player.zone != null
                && Util.canDoWithTime(lastTimeUpdateCTHT, 5000)) {
            int count = 0;
            int[] cts = new int[9];
            cts[this.player.setClothes.ctHaiTac - 618] = this.player.setClothes.ctHaiTac;
            List<Player> players = new ArrayList<>();
            players.add(player);
            try {
                for (Player pl : player.zone.getNotBosses()) {
                    if (!player.equals(pl) && pl.setClothes.ctHaiTac != -1 && Util.getDistance(player, pl) <= 300) {
                        cts[pl.setClothes.ctHaiTac - 618] = pl.setClothes.ctHaiTac;
                        players.add(pl);
                    }
                }
            } catch (Exception e) {
            }
            for (int i = 0; i < cts.length; i++) {
                if (cts[i] != 0) {
                    count++;
                }
            }
            for (Player pl : players) {
                Item ct = pl.inventory.itemsBody.get(5);
                if (ct.isNotNullItem() && ct.template.id >= 618 && ct.template.id <= 626) {
                    for (Item.ItemOption io : ct.itemOptions) {
                        if (io.optionTemplate.id == 147
                                || io.optionTemplate.id == 77
                                || io.optionTemplate.id == 103) {
                            io.param = count * 3;
                        }
                    }
                }
                if (!pl.isPet && !pl.isNewPet && Util.canDoWithTime(lastTimeUpdateCTHT, 5000)) {
                    InventoryServiceNew.gI().sendItemBody(pl);
                }
                pl.effectSkin.lastTimeUpdateCTHT = System.currentTimeMillis();
            }
        }
    }

    private void updateXenHutXungQuanh() {
        try {
            int param = this.player.nPoint.tlHutHpMpXQ;
            boolean hasCell = (mabuIDCaiTrang >= XEN_1 && mabuIDCaiTrang <= XEN_3) || (mabuIDCaiTrang == XEN_CON);
            if(hasCell){
                switch (mabuIDCaiTrang) {
                    case XEN_1:{
                        param = 1;
                        break;
                    }
                    case XEN_2:{
                        param = 2;
                        break;
                    }
                    case XEN_3:{
                        param = 3;
                        break;
                    }
                    case XEN_CON:{
                        param = 4;
                        break;
                    }
                }
            }
            if (param > 0) {
                if (!this.player.isDie() && Util.canDoWithTime(lastTimeXenHutHpKi, 5000)) {
                    int hpHut = 0;
                    int mpHut = 0;
                    List<Player> players = new ArrayList<>();
                    List<Player> playersMap = this.player.zone.getNotBosses();
                    for (Player pl : playersMap) {
                        if (!this.player.equals(pl) && !pl.isBoss && !pl.isDie()
                                && Util.getDistance(this.player, pl) <= 200) {
                            players.add(pl);
                        }

                    }
                    for (Mob mob : this.player.zone.mobs) {
                        if (mob.point.gethp() > 1) {
                            if (Util.getDistance(this.player, mob) <= 200) {
                                int subHp = mob.point.getHpFull() * param / 100;
                                if (subHp >= mob.point.gethp()) {
                                    subHp = mob.point.gethp() - 1;
                                }
                                hpHut += subHp;
                                mob.injured(null, subHp, false);
                            }
                        }
                    }
                    for (Player pl : players) {
                        int subHp = pl.nPoint.hpMax * param / 100;
                        int subMp = pl.nPoint.mpMax * param / 100;
                        if (subHp >= pl.nPoint.hp) {
                            subHp = pl.nPoint.hp - 1;
                        }
                        if (subMp >= pl.nPoint.mp) {
                            subMp = pl.nPoint.mp - 1;
                        }
                        hpHut += subHp;
                        mpHut += subMp;
                        PlayerService.gI().sendInfoHpMpMoney(pl);
                        Service.gI().Send_Info_NV(pl);
                        pl.injured(null, subHp, true, false);
                    }
                    this.player.nPoint.addHp(hpHut);
                    this.player.nPoint.addMp(mpHut);
                    PlayerService.gI().sendInfoHpMpMoney(this.player);
                    Service.gI().Send_Info_NV(this.player);
                    this.lastTimeXenHutHpKi = System.currentTimeMillis();
                }
            }
        } catch (Exception e) {
            Logger.error("");
        }
    }
    
    //custom-drsylas
    private void updateHasBuiBui(){
        try {
            Player UserBuiBui = this.player.zone.hasBuiBui();
            //Làm chậm người khác
            for (Player pl : this.player.zone.getHumanoids()) {
                pl.nPoint.IsBiLamCham = UserBuiBui != null && !UserBuiBui.equals(pl) && Util.getDistance(UserBuiBui, pl) <= 200;
                if(pl.nPoint.IsBiLamCham && Util.canDoWithTime(pl.effectSkin.lastTimeBuiBui, 3000)){
                    pl.chat(textBuiBui[Util.nextInt(0, textBuiBui.length - 1)]);
                    pl.effectSkin.lastTimeBuiBui = System.currentTimeMillis();
                }
                pl.nPoint.speed = (byte)(pl.nPoint.IsBiLamCham ? 2 : 8);
                //Service.gI().sendThongBao(pl, "Slow : " + pl.nPoint.IsBiLamCham + " - Speed : " + pl.nPoint.speed);
                Service.gI().point(pl);
            }
        } catch (Exception e) {
            Logger.error("");
        }
    }
    
    private void updateOdo() {
        try {
            //Tỉ lệ hp ở dơ
            //custom-drsylas
            int param = this.player.nPoint.tlHpGiamODo; 
            if(mabuIDCaiTrang == ODO){
                param = 10;
            }
            if (param > 0) {
                if (Util.canDoWithTime(lastTimeOdo, 10000)) {
                    List<Player> players = new ArrayList<>();

                    List<Player> playersMap = this.player.zone.getNotBosses();
                    for (Player pl : playersMap) {
                        if (!this.player.equals(pl) && !pl.isBoss && !pl.isDie()
                                && Util.getDistance(this.player, pl) <= 200) {
                            players.add(pl);
                        }

                    }
                    for (Player pl : players) {
                        int subHp = pl.nPoint.hpMax * param / 100 + pl.nPoint.def;
                        if (subHp >= pl.nPoint.hp) {
                            subHp = pl.nPoint.hp - 1 + pl.nPoint.def;
                        }
                        Service.gI().chat(pl, textOdo[Util.nextInt(0, textOdo.length - 1)]);
                        PlayerService.gI().sendInfoHpMpMoney(pl);
                        Service.gI().Send_Info_NV(pl);
                        pl.injured(null, subHp, true, false);
                    }
                    this.lastTimeOdo = System.currentTimeMillis();
                }
            }
        } catch (Exception e) {
            Logger.error("");
        }
    }

    //giáp tập luyện
    private void updateTrainArmor() {
        if (Util.canDoWithTime(lastTimeAddTimeTrainArmor, 60000) && !Util.canDoWithTime(lastTimeAttack, 30000)) {
            if (this.player.nPoint.wearingTrainArmor) {
                for (Item.ItemOption io : this.player.inventory.trainArmor.itemOptions) {
                    if (io.optionTemplate.id == 9) {
                        if (io.param < 1000) {
                            io.param++;
                            InventoryServiceNew.gI().sendItemBody(player);
                        }
                        break;
                    }
                }
            }
            this.lastTimeAddTimeTrainArmor = System.currentTimeMillis();
        }
        if (Util.canDoWithTime(lastTimeSubTimeTrainArmor, 60000)) {
            for (Item item : this.player.inventory.itemsBag) {
                if (item.isNotNullItem()) {
                    if (ItemService.gI().isTrainArmor(item)) {
                        for (Item.ItemOption io : item.itemOptions) {
                            if (io.optionTemplate.id == 9) {
                                if (io.param > 0) {
                                    io.param--;
                                }
                            }
                        }
                    }
                } else {
                    break;
                }
            }
            for (Item item : this.player.inventory.itemsBox) {
                if (item.isNotNullItem()) {
                    if (ItemService.gI().isTrainArmor(item)) {
                        for (Item.ItemOption io : item.itemOptions) {
                            if (io.optionTemplate.id == 9) {
                                if (io.param > 0) {
                                    io.param--;
                                }
                            }
                        }
                    }
                } else {
                    break;
                }
            }
            this.lastTimeSubTimeTrainArmor = System.currentTimeMillis();
            InventoryServiceNew.gI().sendItemBags(player);
            Service.gI().point(this.player);
        }
    }

    private void updateVoHinh() {
//        if (this.player.nPoint.wearingVoHinh) {
        if (Util.canDoWithTime(lastTimeAttack, 10000) 
                && (this.player.nPoint.wearingVoHinh || mabuIDCaiTrang == VO_HINH)) {
            isVoHinh = true;
        } else {
            isVoHinh = false;
        }
//        }
    }

    public void dispose() {
        this.player = null;
    }

    private void updateHoaXuong() {
        try {
            // Xoá hiệu ứng
            if (this.player.effectSkin.isBiHoaXuong && ((System.currentTimeMillis() - lastTimeBiHoaXuong > timeHoaXuong * 1000) || this.player.nPoint.hasHalloweenCt)) {
                this.player.effectSkin.isBiHoaXuong = false;
                Service.gI().Send_Caitrang(this.player);
                ItemTimeService.gI().removeItemTime(this.player, 5101);
            }
            
        } catch (Exception e) {
            Logger.error("");
        }
    }
    
    //custom-drsylas
    public boolean hasSaibamen(){
        return this.player.inventory.itemsBody.get(5).template.id == SAIBAMEN
                || mabuIDCaiTrang == SAIBAMEN;
    }
    
    //custom-drsylas
    public void activeSaibamen(Player pltAtt){
        if(!hasSaibamen()) return;
        this.player.nPoint.hp = 2100000000;
        //gồng tự sát
        player.playerSkill.prepareTuSat = true;
        player.playerSkill.lastTimePrepareTuSat = System.currentTimeMillis();

        player.chat("Saibamen nổi giận, aaaaaa");
        SkillService.gI().sendPlayerPrepareBom(player, 2000);
        new Thread(()->{
            try {
                Thread.sleep(2350);
            } catch (Exception e) {
            }
            //nổ
            player.playerSkill.prepareTuSat = false;
            int rangeBom = SkillUtil.getRangeBom(7);

            //custom-drsylas
            float _tileHP = ((float)SkillUtil.getPercentDameBom(7)/100);
            int dame =  (int)(_tileHP * player.nPoint.hpMax);

            for (Mob mob : player.zone.mobs) {
                // Nổ toàn bộ map
                //mob.injured(player, dame, true);
                if (Util.getDistance(player, mob) <= rangeBom) { //khoảng cách có tác dụng bom
                    mob.injured(player, dame, true);
                }
            }
            List<Player> playersMap;
            if (player.isBoss) {
                playersMap = player.zone.getNotBosses();
            } else {
                playersMap = player.zone.getHumanoids();
            }
            if (!MapService.gI().isMapOffline(player.zone.map.mapId)) {
                for (Player pl : playersMap) {
                    if (!player.equals(pl) && SkillService.gI().canAttackPlayer(player, pl)) {
                        pl.injured(player, pl.isBoss ? dame / 2 : dame, false, false);
                        PlayerService.gI().sendInfoHpMpMoney(pl);
                        Service.getInstance().Send_Info_NV(pl);
                    }
                }
            }
            SkillService.gI().affterUseSkill(player, 14);
            if (player.effectSkill.tiLeHPHuytSao != 0) {
                player.effectSkill.tiLeHPHuytSao = 0;
                EffectSkillService.gI().removeHuytSao(player);
            }
            player.setDie(pltAtt);
        }).start();
    }
    //custom-drsylas
    //Cải trang Mabu
    public boolean isMabuCopying() {
        return isMabu;
    }
    //custom-drsylas
    //Cải trang Mabu
    public boolean hasMabu(){
        try {
            return this.player.inventory.itemsBody.get(5).template.id == MABU;
        } catch (Exception e) {
            return false;
        }
    }
    //custom-drsylas
    //Cải trang Mabu
    public void resetMabu(){
        isMabu = false;
        player.chat("Grrr, lại hết biến hình !");
        Service.gI().Copy_Caitrang_From(player, player);
        ItemTimeService.gI().removeItemTime(player, 2957);
        mabuIDCaiTrang = -1;
    }
    //custom-drsylas
    //Cải trang Mabu
    public void updateMabu(){
        try {
            if(!player.isDie() && this.hasMabu() && Util.canDoWithTime(lastTimeUseMabu, TIME_MABU_PER_SECOND)){
                Player pl = null;
                List<Player> players = player.zone.getNotBosses();
                while((pl == null || pl.equals(player))){
                    if(players.size() < 2) break;
                    pl = players.get(Util.nextInt(0, players.size()- 1));
                }
                if(pl != null){
                    Service.gI().Copy_Caitrang_From(player, pl);
                    Service.gI().sendThongBao(pl, "Bạn đã bị Yacon sao chép hình dạng !");
                    player.chat("Hà hà hà, bộ này đẹp quá !");
                    try {
                        mabuIDCaiTrang = pl.inventory.itemsBody.get(5).template.id;
                    } catch (Exception e) {
                        mabuIDCaiTrang = -1;
                    }
                    
                    ItemTimeService.gI().sendItemTime(player, 2957, (int) TIME_MABU / 1000);
                    isMabu = true;
                    lastTimeUseMabu = System.currentTimeMillis();
                    cancelMabuCopy = new Thread(()->{
                        try {
                            Thread.sleep(TIME_MABU);
                            resetMabu();
                        } catch (Exception e) {
                        }
                    });
                    cancelMabuCopy.start();
                }
            }
        } catch (Exception e) {
        }
    }
    
    //custom-drsylas
    //Cải trang Xinbato
    public boolean hasXinbato(){
        try {
            return this.player.inventory.itemsBody.get(5).template.id == XINBATO || mabuIDCaiTrang == XINBATO;
        } catch (Exception e) {
            return false;
        }
    }
    //custom-drsylas
    public boolean hasXinbatoNear(){
        if(player.zone != null){
            for (Player pl : this.player.zone.getHumanoids()){
                if(!pl.equals(player) && pl.effectSkill != null && pl.effectSkin.hasXinbato()
                        && Util.getDistance(player, pl) <= 200)
                    return true;
            }
        }
        return false;
    }
    //custom-drsylas
    public void updateXinbato(){
        if(!(player.nPoint.tlBanTrung == 100) && Util.canDoWithTime(lastTimeXinbato, 10000)){
            lastTimeXinbato = System.currentTimeMillis();
            player.chat(textXinbato[Util.nextInt(0, textXinbato.length - 1)]);
        }
    }
    
    //custom-drsylas
    //Cải trang Thỏ bulma hoặc cải trang có chỉ số 117
    public boolean hasBulma(){
        try {
            return !this.player.nPoint.tlSDDep.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
    //custom-drsylas
    public boolean hasBulmaNear(){
        if(player.zone != null){
            for (Player pl : this.player.zone.getHumanoids()){
                if(!pl.equals(player) && pl.effectSkill != null && pl.effectSkin.hasBulma()
                        && Util.getDistance(player, pl) <= 200)
                    return true;
            }
        }
        return false;
    }
    
    //custom-drsylas
    public void updateThoBulma(){
        if(hasBulmaNear() && Util.canDoWithTime(lastTimeBulma, 10000)){
            lastTimeBulma = System.currentTimeMillis();
            player.chat(textBulma[Util.nextInt(0, textBulma.length - 1)]);
        }
    }
}
