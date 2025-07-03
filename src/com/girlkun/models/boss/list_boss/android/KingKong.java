package com.girlkun.models.boss.list_boss.android;

import com.girlkun.consts.ConstPlayer;
import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossManager;
import com.girlkun.models.boss.BossStatus;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.services.Service;
import com.girlkun.services.TaskService;
import com.girlkun.utils.Util;
import java.util.Arrays;

public class KingKong extends Boss {

    private boolean startRespawn = false;

    public KingKong() throws Exception {
        super(BossID.KING_KONG, BossesData.KING_KONG);
    }

    @Override
    public void reward(Player plKill) {
        int[] itemRan = new int[]{382, 383, 384};
        int itemId = itemRan[2];
        if (Util.isTrue(15, 100)) {
            ItemMap it = new ItemMap(this.zone, itemId, 1, this.location.x, this.zone.map.yPhysicInTop(this.location.x,
                    this.location.y - 24), plKill.id);
            Service.gI().dropItemMap(this.zone, it);
        }
        TaskService.gI().checkDoneTaskKillBoss(plKill, this);
    }

    @Override
    public void active() {
        if (this.typePk == ConstPlayer.NON_PK) {
            return;
        }
        this.attack();
    }

    @Override
    public void joinMap() {
        super.joinMap(); //To change body of generated methods, choose Tools | Templates.
        st = System.currentTimeMillis();
    }
    private long st;

    @Override
    public void updateBoss() {
        super.updateBoss();
        bossRestart();
    }

    public void bossRestart() {
        if (this.zone != null && this.getTimeToRestart() != -1
                && Util.canDoWithTime(getTimeToRestart(), super.getSecondsNotify() * 1000)) {
            if (Arrays.asList(getBossAppearTogether()[0]).stream().anyMatch(x -> x.isDie()) && !startRespawn) {
                handleSubBossRestart();
            }
            setLastTimeNotify(System.currentTimeMillis());
        }
    }

    private synchronized void handleSubBossRestart() {
        startRespawn = true;
        for (Boss boss : getBossAppearTogether()[0]) {
            boss.changeStatus(BossStatus.LEAVE_MAP);
        }
        this.changeStatus(BossStatus.LEAVE_MAP);
        System.out.println("aaa");
        BossManager.gI().createBoss(BossID.KING_KONG);
    }
}

/**
 * Vui lòng không sao chép mã nguồn này dưới mọi hình thức. Hãy tôn trọng tác
 * giả của mã nguồn này. Xin cảm ơn! - GirlBeo
 */
