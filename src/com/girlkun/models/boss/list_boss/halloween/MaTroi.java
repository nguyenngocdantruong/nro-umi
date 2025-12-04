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
public class MaTroi extends BossHalloween {
    public MaTroi() throws Exception {
        super(BossID.MA_TROI, BossesData.MA_TROI);
    }
}
