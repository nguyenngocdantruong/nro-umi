package com.girlkun.models.boss.list_boss.halloween;

import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossManager;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.boss.BossStatus;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.server.Manager;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;

import java.util.Random;


public class BiMa extends Boss {

    public BiMa() throws Exception {
        super(BossID.BI_MA, BossesData.BI_MA);
    }

    @Override
    public void reward(Player plKill) {
        // bí từ 1s - 7s
        int[] listNgocRongXuong = new int[] {702, 703, 704, 705, 706, 707, 708};
        int itemIdBiDrop = listNgocRongXuong[Util.nextInt(0, listNgocRongXuong.length - 1)];
        Service.getInstance().dropItemMap(this.zone, new ItemMap(zone, itemIdBiDrop, 1, this.location.x, this.location.y, plKill.id));
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
            damage = this.nPoint.subDameInjureWithDeff(damage/1);
            if (!piercing && effectSkill.isShielding) {
                if (damage > nPoint.hpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
                damage = damage/1;
            }
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

    private long st;
}






















