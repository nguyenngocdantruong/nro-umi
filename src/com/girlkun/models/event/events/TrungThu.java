package com.girlkun.models.event.events;

import com.girlkun.models.boss.BossID;
import com.girlkun.models.event.Event;

/**
 * Trung Thu Event
 * Sử dụng boss Thỏ Đại Ca hiện có
 *
 * @author Umi Team
 */
public class TrungThu extends Event {

    @Override
    public void boss() {
        // Spawn 10 boss Thỏ Đại Ca
        createBoss(BossID.THO_DAI_CA, 10);
    }
}
