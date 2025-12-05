package com.girlkun.models.event.events;

import com.girlkun.consts.ConstNpc;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.event.ConstEvent;
import com.girlkun.models.event.Event;

/**
 * Trung Thu Event
 * Sử dụng boss Thỏ Đại Ca hiện có
 *
 * @author Umi Team
 */
public class TrungThu extends Event {

    @Override
    public String getThongBaoLogin() {
        return "Chào ae tới server NroSkibidi. Nạp X2 liên hệ admin.";
    }

    @Override
    public void npc() {
        createNpc(42, ConstNpc.THO_DAI_CA, 111, 288);
        super.npc();
    }
    
    
    
    @Override
    public void boss() {
        // Spawn 10 boss Thỏ Đại Ca
        createBoss(BossID.THO_DAI_CA, 10);
    }

    @Override
    protected String getNameEvent() {
        return "Trung thu";
    }

    @Override
    public int getHeSoTnSm() {
        return 3;
    }

    @Override
    protected ConstEvent GetEventType() {
        return ConstEvent.TRUNG_THU;
    }
}
