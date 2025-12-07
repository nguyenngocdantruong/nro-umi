package com.girlkun.models.event.events;

import com.girlkun.consts.ConstNpc;
import com.girlkun.models.event.ConstEvent;
import com.girlkun.models.event.Event;

/**
 * Default event - chế độ bình thường khi không có sự kiện nào active
 *
 * @author Umi Team
 */
public class Default extends Event {

    @Override
    public void init() {
        // Không làm gì - chế độ bình thường
        super.init();
    }

    @Override
    public void npc() {
        super.npc(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    
    
    @Override
    public String getThongBaoLogin() {
        return "Chào ae tới server NroSkibidi. Hiện tại đang không diễn ra event gì. Nạp X2 liên hệ admin.";
    }

    @Override
    protected String getNameEvent() {
        return "";
    }

    @Override
    public int getHeSoTnSm() {
        return 1;
    }

    @Override
    protected ConstEvent GetEventType() {
        return ConstEvent.MAC_DINH;
    }
}
