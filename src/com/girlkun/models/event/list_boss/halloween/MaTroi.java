package com.girlkun.models.event.list_boss.halloween;

import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossesData;

/**
 * Boss Ma trơi - Halloween Event
 * Đặc điểm: Attack cooldown ngẫu nhiên, apply Halloween effect type 4
 *
 * @author Umi Team
 */
public class MaTroi extends BossHalloween {
    public MaTroi() throws Exception {
        super(BossID.MA_TROI, BossesData.MA_TROI);
    }
}
