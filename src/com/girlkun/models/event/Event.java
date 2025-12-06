package com.girlkun.models.event;

import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossManager;
import com.girlkun.models.event.events.Default;
import com.girlkun.models.item.Item;
import com.girlkun.models.npc.NpcFactory;
import com.girlkun.services.MapService;
import com.girlkun.services.Service;
import com.girlkun.utils.Logger;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class cho tất cả các Event
 * Cung cấp implementation mặc định và helper methods
 *
 * @author Umi Team
 */
public abstract class Event implements IEvent {

    protected abstract String getNameEvent();

    protected abstract ConstEvent GetEventType();

    @Override
    public void init() {
        if (getEventFlag() == false) {
            npc();
            setEventFlagInited();
        }
        if (this instanceof Default){
            Service.gI().sendThongBaoAllPlayer(String.format("Hiện tại chưa diễn ra event gì cả!\nHệ số TNSM: %d!",
                getHeSoTnSm()));
        }
        else{
            Service.gI().sendThongBaoAllPlayer(String.format("Event %s đã bắt đầu mời bạn trải nghiệm!\nHệ số TNSM: %d!",
                getNameEvent(), getHeSoTnSm()));
        }
        boss();
        itemMap();
        itemBoss();
        EventManager.gI().lastTimeChangeEvent = System.currentTimeMillis();
        
    }

    protected boolean getEventFlag() {
        return EventManager.gI().getEventFlag(GetEventType());
    }

    protected void setEventFlagInited() {
        EventManager.gI().setEventFlag(GetEventType());
    }

    @Override
    public void npc() {
        // Default: không có NPC

    }

    /**
     * Helper method để tạo NPC tại vị trí cụ thể
     *
     * @param mapId Map ID
     * @param npcId NPC template ID
     * @param x     Tọa độ X
     * @param y     Tọa độ Y
     */
    protected void createNpc(int mapId, int npcId, int x, int y) {
        try {
            MapService.gI().getMapById(mapId).npcs.add(
                    NpcFactory.createNPC(mapId, 1, x, y, npcId));
        } catch (Exception e) {
            Logger.logException(Event.class, e, "Lỗi tạo NPC sự kiện");
        }
    }

    @Override
    public void boss() {
        // Default: không có boss
    }

    /**
     * Helper method để tạo nhiều boss instances
     *
     * @param bossId Boss type ID
     * @param total  Số lượng boss cần tạo
     */
    protected void createBoss(int bossId, int total) {
        try {
            for (int i = 0; i < total; i++) {
                Boss b = BossManager.gI().createBoss(bossId);
                EventManager.gI().addBossesEvent(b);
                Thread.sleep(5000); // Delay 5 giây giữa mỗi boss spawn (chạy async nên không block)
            }
        } catch (Exception e) {
            Logger.logException(Event.class, e, "Lỗi tạo boss sự kiện");
        }
    }

    @Override
    public List<Item> itemMap() {
        return new ArrayList<>();
    }

    @Override
    public void itemBoss() {
        // Default: không config item boss
    }

    @Override
    public void cleanup() {
        // Default: không cần cleanup
        EventManager.gI().clearBossesEvent();
        if (this instanceof Default)
            return;
        Service.gI()
                .sendThongBaoAllPlayer(String.format("Event %s đã kết thúc! Hẹn bạn lần tới event!", getNameEvent()));
    }

    public abstract int getHeSoTnSm();

}
