/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.girlkun.models.event.list_boss.trung_thu;

import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossData;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.services.ItemTimeService;
import com.girlkun.services.Service;
import com.girlkun.services.TaskService;
import com.girlkun.utils.Util;

/**
 *
 * @author ndantruong
 */
public class ThoDaiCa extends Boss {
    
    short randomFlagBag; // Cho random ra 1 cái lồng đèn
    
    short[] flagBags = new short[] {42, 43, 44, 87}; // Con gà, Con bướm, Doremon, Bóng gà
    
    public ThoDaiCa() throws Exception {
        super(BossID.THO_DAI_CA, BossesData.THO_DAI_CA);
        randomFlagBag = flagBags[Util.nextInt(flagBags.length)];
    }

    @Override
    public void reward(Player plKill) {
        int itemId = 462; // Củ cà rốt
        int count = Util.nextInt(4, 9);
        for(int i = (-count); i < count ; i++) {
            int x = this.location.x + i * 15;
            ItemMap it = new ItemMap(this.zone, itemId, 1, x, this.zone.map.yPhysicInTop(x,
                    this.location.y - 24), -1);
            Service.gI().dropItemMap(this.zone, it);
        }
        if(Util.isTrue(40, 100)){
            super.reward(plKill);
        }
    }

    @Override
    public int injured(Player plAtt, int damage, boolean piercing, boolean isMobAttack) {
        damage = 1;
        return super.injured(plAtt, damage, piercing, isMobAttack); 
    }

    @Override
    public void affectPlayer(Player player) {
        if(!player.effectSkin.isCarrot && !player.nPoint.khangTDHS){
            player.effectSkin.isCarrot = true;
            Service.gI().Send_Caitrang_Carot(player);
            ItemTimeService.gI().sendItemTime(player, 933, (int) player.effectSkin.TIME_BIEN_CARROT / 1000);
            player.effectSkin.lastTimeBiBienThanhCarrot = System.currentTimeMillis();
            this.chat("Bắt tay cái nào!");
        }
    }

    

    @Override
    public void active() {
        super.active(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public short getFlagBag() {
        return randomFlagBag;
    }
    
}
