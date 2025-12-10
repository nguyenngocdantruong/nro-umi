package com.girlkun.models.boss.list_boss.Broly;

import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossManager;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.player.Player;
import com.girlkun.services.EffectSkillService;
import com.girlkun.utils.Logger;
import com.girlkun.utils.Util;

public class Broly extends Boss {

    private int initS = 0;

    public Broly() throws Exception {
        super(BossID.BROLY, BossesData.BROLY_1);
    }

    @Override
    public void reward(Player plKill) {
        if (initS == 1) {
            BossManager.gI().createBoss(BossID.S_BROLY);
            initS = 0;
        }
    }

    @Override
    public void active() {
        super.active();
    }

    @Override
    public int injured(Player plAtt, int damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 1000)) {
                this.chat("Xí hụt");
                return 0;
            }
            damage = this.nPoint.hpg / 100;
            this.nPoint.subHP(damage);
            if (isDie()) {
                this.setDie(plAtt);
                die(plAtt);
            }

            if (this.nPoint.hpMax < 16099999) {
                this.nPoint.hpMax += this.nPoint.hpMax / 100;
                this.nPoint.hpg += this.nPoint.hpg / 100;
                this.nPoint.dameg = this.nPoint.hpMax / 200;

                if (this.nPoint.hpMax > 500000 && initS == 0) {
                    this.chat("Grrr Grr Grrr .... Ta đã đủ sức mạnh biến Super Broly ....");
                    initS = 1;
                }
            }
            else{
                initS = 1;
                this.chat("Hãy xem sức mạnh SuperBroly của ta đây!");
                this.leaveMap();
            }

            return damage;
        } else {
            return 0;
        }
    }
}
