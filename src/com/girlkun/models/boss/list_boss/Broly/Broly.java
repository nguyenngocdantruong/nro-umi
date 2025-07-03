package com.girlkun.models.boss.list_boss.Broly;

import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossManager;
import com.girlkun.models.boss.BossStatus;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.models.skill.Skill;
import com.girlkun.server.Manager;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.PetService;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;
import java.util.Random;

public class Broly extends Boss {

    private int initS = 0;
    private int damePST;

    public Broly() throws Exception {
        super(BossID.BROLY, BossesData.BROLY_1);
    }

    public void reward(Player plKill) {
        if (initS == 1) {
            BossManager.gI().createBoss(BossID.S_BROLY);
            initS = 0;
        }
//        Service.gI().sendThongBao(plKill, "Bạn vừa nhận được 5 điểm ngũ hành sơn");
    }

    @Override
    public void active() {
        super.active();
    }

    @Override
    public int injured(Player plAtt, int damage, boolean piercing, boolean isMobAttack) {
        if (plAtt != null) {
            switch (plAtt.playerSkill.skillSelect.template.id) {
                case Skill.ANTOMIC:
                case Skill.DEMON:
                case Skill.DRAGON:
                case Skill.GALICK:
                case Skill.KAIOKEN:
                case Skill.DICH_CHUYEN_TUC_THOI:
                case Skill.LIEN_HOAN:
                    damage = this.nPoint.hpMax / 100;
                    if (this.nPoint.hpMax < 16099999) {
                        if (this.nPoint.hpMax > 500_000) {
                            initS = 1;
                        }
                        this.nPoint.hpMax += this.nPoint.hpMax / 100;
                        this.nPoint.dame += this.nPoint.hpMax / 200;
                    }
                    if (this.nPoint.hpMax > 16099999) {
                        this.nPoint.hpMax = 16099999;
                    }

                    if (this.nPoint.hp < 1) {
                        if (initS == 1) {
                            BossManager.gI().createBoss(BossID.S_BROLY);
                            initS = 0;
                        }
                        this.setDie(plAtt);
                        die(this);
                    }
                    return super.injured(plAtt, damage, !piercing, isMobAttack);
            }
        }
        return super.injured(plAtt, damage, piercing, isMobAttack);
    }

}
