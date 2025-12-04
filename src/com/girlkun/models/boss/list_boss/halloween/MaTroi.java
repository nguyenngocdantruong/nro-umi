package com.girlkun.models.boss.list_boss.halloween;

import com.girlkun.consts.ConstPlayer;
import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossStatus;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.Service;
import com.girlkun.services.SkillService;
import com.girlkun.utils.SkillUtil;
import com.girlkun.utils.Util;

/**
 * Boss Ma trơi - Halloween Event
 * Đặc điểm: Attack cooldown ngẫu nhiên, apply Halloween effect type 4
 *
 * @author Umi Team
 */
public class MaTroi extends Boss {

    private long spawnTime;

    public MaTroi() throws Exception {
        super(BossID.MA_TROI, BossesData.MA_TROI);
    }

    @Override
    public void reward(Player plKill) {
        int itemId = 77; // TEMPORARY
        int x = this.location.x + Util.nextInt(-50, 50);
        int y = this.zone.map.yPhysicInTop(x, this.location.y - 24);
        ItemMap itemMap = new ItemMap(this.zone, itemId, Util.nextInt(1, 3), x, y, plKill.id);
        Service.gI().dropItemMap(this.zone, itemMap);

        if (plKill.playerEventData != null) {
            int points = Util.nextInt(5, 15);
            plKill.playerEventData.addHalloweenPoints(points);
            Service.gI().sendThongBao(plKill,
                    "Bạn nhận được " + points + " điểm Halloween! Tổng: "
                            + plKill.playerEventData.getHalloweenPoints());
        }

        super.reward(plKill);
    }

    @Override
    public int injured(Player plAtt, int damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(10, 1000)) {
                this.chat("Xí hụt");
                return 0;
            }

            applyHalloweenEffect(plAtt);
            damage = this.nPoint.subDameInjureWithDeff(damage / 7);

            if (!piercing && effectSkill.isShielding) {
                if (damage > nPoint.hpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
            }

            if (damage > this.nPoint.hpMax / 50) {
                damage = this.nPoint.hpMax / 50;
            }

            this.nPoint.subHP(damage);
            if (isDie()) {
                this.setDie(plAtt);
                die(plAtt);
            }
            return damage;
        }
        return 0;
    }

    private void applyHalloweenEffect(Player player) {
        if (player != null && player.effectSkill != null && !player.effectSkill.isHalloween) {
            try {
                // Effect type 4 (different from BiMa), duration 30 minutes
                EffectSkillService.gI().setIsHalloween(player, 4, 1800000);
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void active() {
        super.active();
        autoLeaveMap();
        // Random cooldown between 500-1000ms
        if (Util.canDoWithTime(this.lastTimeAttack, Util.nextInt(500, 1000))
                && this.typePk == ConstPlayer.PK_ALL) {
            this.lastTimeAttack = System.currentTimeMillis();
            try {
                Player pl = getPlayerAttack();
                if (pl == null || pl.isDie()) {
                    return;
                }

                this.nPoint.dameg = pl.nPoint.hpMax / Util.nextInt(30, 50);
                this.playerSkill.skillSelect = this.playerSkill.skills.get(
                        Util.nextInt(0, this.playerSkill.skills.size() - 1));

                if (Util.getDistance(this, pl) <= this.getRangeCanAttackWithSkillSelect()) {
                    if (Util.isTrue(5, 20)) {
                        if (SkillUtil.isUseSkillChuong(this)) {
                            this.moveTo(
                                    pl.location.x + (Util.getOne(-1, 1) * Util.nextInt(20, 200)),
                                    Util.nextInt(10) % 2 == 0 ? pl.location.y : pl.location.y - Util.nextInt(0, 70));
                        } else {
                            this.moveTo(
                                    pl.location.x + (Util.getOne(-1, 1) * Util.nextInt(10, 40)),
                                    Util.nextInt(10) % 2 == 0 ? pl.location.y : pl.location.y - Util.nextInt(0, 50));
                        }
                    }

                    applyHalloweenEffect(pl);
                    SkillService.gI().useSkill(this, pl, null, null);
                    checkPlayerDie(pl);
                } else {
                    if (Util.isTrue(1, 2)) {
                        this.moveToPlayer(pl);
                    }
                }
            } catch (Exception ex) {
            }
        }
    }

    @Override
    public void joinMap() {
        this.name = String.format("Ma trơi %d", Util.nextInt(10, 100));
        super.joinMap();
        spawnTime = System.currentTimeMillis();
    }

    @Override
    public void leaveMap() {
        super.leaveMap();
        spawnTime = 0;
    }

    /**
     * Auto despawn logic - được gọi trong active()
     */
    private void autoLeaveMap() {
        if (spawnTime > 0 && Util.canDoWithTime(spawnTime, 900000) &&
                (this.zone == null || this.zone.getNumOfPlayers() == 0)) {
            this.changeStatus(BossStatus.LEAVE_MAP);
        }
        if (this.zone != null && this.zone.getNumOfPlayers() > 0) {
            spawnTime = System.currentTimeMillis();
        }
    }
}
