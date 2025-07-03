package com.girlkun.models.boss.list_boss.Broly;

import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossData;
import com.girlkun.models.boss.BossStatus;
import com.girlkun.consts.ConstPlayer;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossManager;
import static com.girlkun.models.boss.BossStatus.ACTIVE;
import static com.girlkun.models.boss.BossStatus.JOIN_MAP;
import static com.girlkun.models.boss.BossStatus.RESPAWN;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.boss.list_boss.cell.SieuBoHung;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.map.Zone;
import com.girlkun.models.map.challenge.MartialCongressService;
import com.girlkun.models.player.Player;
import com.girlkun.models.skill.Skill;
import com.girlkun.server.Manager;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.PetService;
import com.girlkun.services.PlayerService;
import com.girlkun.services.Service;
import com.girlkun.services.SkillService;
import com.girlkun.services.func.ChangeMapService;
import com.girlkun.utils.SkillUtil;
import com.girlkun.utils.Util;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SuperBroly extends Boss {

    public SuperBroly() throws Exception {
        super(BossID.S_BROLY, BossesData.BROLY_3);
    }

    public void reward(Player plKill) {
        byte randomDo = (byte) new Random().nextInt(Manager.itemIds_NR_SB.length - 1);
        if (plKill.pet == null) {
            int gender = Util.nextInt(0, 2);
            PetService.gI().createNormalPet(plKill, gender);
            Service.getInstance().sendThongBao(plKill, "Bạn vừa nhận được đệ tử");
        } else {
            //    Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, Manager.itemIds_TL[randomDo], 1, this.location.x, this.location.y, plKill.id));
        }
    }

    @Override
    public void joinMap() {
        super.joinMap();
    }

    @Override
    public void active() {
        super.active();

    }

    @Override
    public void leaveMap() {
        super.leaveMap();
        BossManager.gI().removeBoss(this);

    }

}
