package com.girlkun.models.event.events;

import com.girlkun.consts.ConstNpc;
import com.girlkun.models.boss.BossType;
import com.girlkun.models.event.Event;
import com.girlkun.models.shop.Shop;
import com.girlkun.models.event.ConstEvent;
import com.girlkun.server.Manager;

/**
 * Halloween Event
 * Spawn 3 loại boss: Bí ma, Ma trơi, Dơi (mỗi loại 10 instances)
 *
 * @author Umi Team
 */
public class Debug extends Event {


    @Override
    public int getHeSoTnSm() {
        return 20;
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void npc() {
        createNpc(5, ConstNpc.BILL_BI_NGO, 232, 288);
        createNpc(42, ConstNpc.THO_DAI_CA, 111, 288);
        super.npc();
    }

    @Override
    public void boss() {
        // Spawn 10 boss Khỉ xayda
        createBoss(BossType.KHI_XAYDA, 10);
        
//        // Spawn 10 boss Bí ma
//        createBoss(BossType.BI_MA, 10);
//
//        // Spawn 10 boss Ma trơi
//        createBoss(BossType.MA_TROI, 10);
//
//        // Spawn 10 boss Dơi
//        createBoss(BossType.DOI, 10);
//
//        // Spawn 10 boss Dracula
//        createBoss(BossType.DRACULA_HALLOWEEN, 10);
    }

    @Override
    public String getThongBaoLogin() {
        return "Đây là event debug. Nếu bạn thấy event này vui lòng báo admin sửa lại.";
    }

    @Override
    protected String getNameEvent() {
        return "Debug";
    }

    @Override
    protected ConstEvent GetEventType() {
        return ConstEvent.DEBUG;
    }
}
