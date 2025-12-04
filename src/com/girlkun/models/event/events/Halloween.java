package com.girlkun.models.event.events;

import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossType;
import com.girlkun.models.event.Event;

/**
 * Halloween Event
 * Spawn 3 loại boss: Bí ma, Ma trơi, Dơi (mỗi loại 10 instances)
 *
 * @author Umi Team
 */
public class Halloween extends Event {

    @Override
    public int getHeSoTnSm() {
        return 3; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    
    
    @Override
    public void npc() {
        // Future: Thêm NPC Halloween nếu cần
        // createNpc(5, ConstNpc.NPC_HALLOWEEN, 355, 336);
    }

    @Override
    public void boss() {
        // Spawn 10 boss Bí ma
        createBoss(BossType.BI_MA, 10);

        // Spawn 10 boss Ma trơi
//        createBoss(BossType.MA_TROI, 10);

        // Spawn 10 boss Dơi
//        createBoss(BossType.DOI, 10);
    }
}
