package com.girlkun.models.event.events;

import com.girlkun.models.event.ConstEvent;
import com.girlkun.models.event.Event;
import com.girlkun.models.event.EventManager;

/**
 * Christmas Event - Placeholder cho tương lai
 *
 * @author Umi Team
 */
public class Christmas extends Event {

    @Override
    public void boss() {
        // TODO: Implement Christmas bosses
        // createBoss(BossID.ONG_GIA_NOEL, 10);
    }

    @Override
    public void npc() {
        // TODO: Thêm NPC Christmas
    }

    @Override
    public String getThongBaoLogin() {
        return "Chào mừng ae đến mùa Noel";
    }

    @Override
    protected String getNameEvent() {
        return "Giáng sinh";
    }

    @Override
    public int getHeSoTnSm() {
        return 2;
    }

    @Override
    protected ConstEvent GetEventType() {
        return ConstEvent.GIANG_SINH;
    }
}
