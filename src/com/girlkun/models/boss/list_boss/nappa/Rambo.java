package com.girlkun.models.boss.list_boss.nappa;

import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossStatus;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.item.ConstItem;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.models.skill.Skill;
import com.girlkun.services.PetService;
import com.girlkun.services.Service;
import com.girlkun.services.TaskService;
import com.girlkun.utils.Util;
import java.util.Random;

/**
 *
 * @Stole By Arriety
 */
public class Rambo extends Boss {

    public Rambo() throws Exception {
        super(BossID.RAMBO, BossesData.RAMBO);
    }
    public void active() {
        super.active(); //To change body of generated methods, choose Tools | Templates.
//        if (Util.canDoWithTime(st, 900000)) {
//            this.changeStatus(BossStatus.LEAVE_MAP);
//        }
    }

    @Override
    public void reward(Player plKill) {
        // Rơi đồ thêm
        ItemMap doRoiTuBoss = Util.GetRandomItemRoiTuBoss(15, ConstItem.LoaiDoRoiTuBoss.DoTamTrung,
                ConstItem.CoSaoPhaLe.Co, this.zone, this.location.x + Util.nextInt(-15, 15), this.location.y, plKill.id);
        if(doRoiTuBoss != null){
            Service.gI().dropItemMap(this.zone, doRoiTuBoss);
        }
        super.reward(plKill); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public void joinMap() {
        super.joinMap(); //To change body of generated methods, choose Tools | Templates.
//        st = System.currentTimeMillis();
    }
//    private long st;
}

/**
 * Vui lòng không sao chép mã nguồn này dưới mọi hình thức. Hãy tôn trọng tác
 * giả của mã nguồn này. Xin cảm ơn! - GirlBeo
 */
