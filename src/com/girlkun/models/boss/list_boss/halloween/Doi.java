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
 * Boss Dơi - Halloween Event
 * Đặc điểm: Fast attack speed (400-800ms), weaker damage than others
 *
 * @author Umi Team
 */
public class Doi extends BossHalloween {
    public Doi() throws Exception {
        super(BossID.DOI, BossesData.DOI);
    }
}
