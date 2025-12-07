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
import com.girlkun.models.skill.Skill;
import com.girlkun.services.ItemTimeService;
import com.girlkun.services.PlayerService;
import com.girlkun.services.Service;
import com.girlkun.services.TaskService;
import com.girlkun.utils.Util;

/**
 *
 * @author ndantruong
 */
public class KhiXayda extends Boss {

    public KhiXayda() throws Exception {
        super(BossID.KHI_XAYDA, BossesData.KHI_XAYDA);
        // Set trạng thái khỉ vĩnh viễn
        if (this.effectSkill != null) {
            this.effectSkill.isMonkey = true;
            this.effectSkill.levelMonkey = 1; // Level khỉ cao nhất
            this.effectSkill.lastTimeUpMonkey = System.currentTimeMillis();
            this.effectSkill.timeMonkey = Integer.MAX_VALUE; // Thời gian rất dài
        }
    }

    @Override
    public void updateBoss() {
        // Reset timer để boss luôn ở trạng thái khỉ, không bao giờ gọi monkeyDown()
        if (this.effectSkill != null && this.effectSkill.isMonkey) {
            this.effectSkill.lastTimeUpMonkey = System.currentTimeMillis();
        }
        super.updateBoss();
    }

    @Override
    public void reward(Player plKill) {
        int itemId = 2158; // Đuôi khỉ trung thu
        int count = 1 + (Util.isTrue(40, 100) ? 1 : 0) + (Util.isTrue(20, 100) ? 1 : 0);
        for (int i = (-count); i < count; i++) {
            if(i == 0){
                ItemMap it = new ItemMap(this.zone, itemId, 1, this.location.x, this.zone.map.yPhysicInTop(this.location.x,
                    this.location.y - 24), plKill.id);
                Service.gI().dropItemMap(this.zone, it);
                continue;
            }
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
        return super.injured(plAtt, damage, piercing, isMobAttack);
    }

    @Override
    public void affectPlayer(Player player) {
        // if(!player.effectSkin.isCarrot && !player.nPoint.khangTDHS){
        // player.effectSkin.isCarrot = true;
        // Service.gI().Send_Caitrang_Carot(player);
        // ItemTimeService.gI().sendItemTime(player, 933, (int)
        // player.effectSkin.TIME_BIEN_CARROT / 1000);
        // player.effectSkin.lastTimeBiBienThanhCarrot = System.currentTimeMillis();
        // this.chat("Bắt tay cái nào!");
        // }
    }

    @Override
    public void active() {
        super.active(); 
    }

}
