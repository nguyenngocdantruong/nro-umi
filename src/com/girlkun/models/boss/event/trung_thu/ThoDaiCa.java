/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.girlkun.models.boss.event.trung_thu;

import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossData;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.services.Service;
import com.girlkun.services.TaskService;
import com.girlkun.utils.Util;

/**
 *
 * @author ndantruong
 */
public class ThoDaiCa extends Boss {
    
    public ThoDaiCa() throws Exception {
        super(BossID.THO_DAI_CA, BossesData.THO_DAI_CA);
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
        super.reward(plKill);
    }

    @Override
    public int injured(Player plAtt, int damage, boolean piercing, boolean isMobAttack) {
        damage = 1;
        return super.injured(plAtt, damage, piercing, isMobAttack); 
    }

    @Override
    public void active() {
        super.active(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
}
