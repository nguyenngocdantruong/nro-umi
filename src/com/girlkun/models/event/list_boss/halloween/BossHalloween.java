package com.girlkun.models.event.list_boss.halloween;

import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossData;
import com.girlkun.models.boss.BossStatus;
import com.girlkun.models.item.ConstItem;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.services.ItemTimeService;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;


public class BossHalloween extends Boss {

    public BossHalloween(int type, BossData... data) throws Exception {
        super(type, data);
    }

    @Override
    public void reward(Player plKill) {
        int soLuongDrop = Util.nextInt(1, 3);
        for(int i  = 0; i < soLuongDrop ; i++){
            // bí từ 1s - 7s
            int[] listNgocRongXuong = new int[] {702, 703, 704, 705, 706, 707, 708};
            int itemIdBiDrop = listNgocRongXuong[Util.nextInt(0, listNgocRongXuong.length - 1)];
            int x = this.location.x + Util.nextInt(-30, 30);
            Service.getInstance().dropItemMap(this.zone, new ItemMap(zone, itemIdBiDrop, 1, x, this.location.y, plKill.id));
        }
        
        // Bí ngô nhí nhảnh
        if(Util.isTrue(80, 100)){
            int idBiNgoNhiNhanh = 910; //Ngọc xanh 77
            int quantity = Util.nextInt(1, 6);
            ItemMap itemBiNgo = new ItemMap(this.zone, idBiNgoNhiNhanh, 
                    quantity, 
                    this.location.x + Util.nextInt(-50, 50),
                    this.zone.map.yPhysicInTop(this.location.x, this.location.y - 24)
                    , -1);
            Service.gI().dropItemMap(this.zone, itemBiNgo);
        }
        // Rơi đồ thêm
        ItemMap doRoiTuBoss = Util.GetRandomItemRoiTuBoss(10, ConstItem.LoaiDoRoiTuBoss.DoThanLinh,
                ConstItem.CoSaoPhaLe.Co, this.zone, this.location.x + Util.nextInt(-15, 15), this.location.y, plKill.id);
        if(doRoiTuBoss != null){
            Service.gI().dropItemMap(this.zone, doRoiTuBoss);
        }
        
        if(Util.isTrue(40, 100)){
            super.reward(plKill);
        }
    }

    @Override
    public void active() {
        super.active(); //To change body of generated methods, choose Tools | Templates.
        if (Util.canDoWithTime(st, 400000)) {
            this.changeStatus(BossStatus.LEAVE_MAP);
        }
    }
    
    @Override
    public int injured(Player plAtt, int damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 1000)) {
                this.chat("Xí hụt");
                return 0;
            }
            damage = 1;
            this.nPoint.subHP(damage);
            if (isDie()) {
                this.setDie(plAtt);
                die(plAtt);
            }
            return damage;
        } else {
            return 0;
        }
    }
    @Override
    public void joinMap() {
        super.joinMap(); //To change body of generated methods, choose Tools | Templates.
        st = System.currentTimeMillis();
    }

    @Override
    public void attack() {
        super.attack(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    
    
    @Override
    public void affectPlayer(Player player) {
        if(!player.effectSkin.isBiHoaXuong && !player.nPoint.hasHalloweenCt){
            player.effectSkin.isBiHoaXuong = true;
            Service.gI().Send_CaiTrang_Halloween(player, this.getHead(), this.getBody(), this.getLeg());
            ItemTimeService.gI().sendItemTime(player, 5101, (int) player.effectSkin.timeHoaXuong);
            player.effectSkin.lastTimeBiHoaXuong = System.currentTimeMillis();
            this.chat("Cho cái kẹo nè!");
        }
    }
    private long st;
}