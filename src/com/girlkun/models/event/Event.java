package com.girlkun.models.event;

import com.girlkun.models.boss.BossManager;
import com.girlkun.models.npc.NpcFactory;
import com.girlkun.services.MapService;
import com.girlkun.utils.Logger;

/**
 * Abstract base class cho tất cả các Event
 * Cung cấp implementation mặc định và helper methods
 *
 * @author Umi Team
 */
public abstract class Event implements IEvent {
    @Override
    public void init() {
        npc();
        boss();
        itemMap();
        itemBoss();
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
     * @param x Tọa độ X
     * @param y Tọa độ Y
     */
    protected void createNpc(int mapId, int npcId, int x, int y) {
        try {
            MapService.gI().getMapById(mapId).npcs.add(
                NpcFactory.createNPC(mapId, 1, x, y, npcId)
            );
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
     * @param total Số lượng boss cần tạo
     */
    protected void createBoss(int bossId, int total) {
        try {
            for (int i = 0; i < total; i++) {
                BossManager.gI().createBoss(bossId);
                Thread.sleep(1000); // Delay 1 giây giữa mỗi boss spawn
            }
        } catch (Exception e) {
            Logger.logException(Event.class, e, "Lỗi tạo boss sự kiện");
        }
    }

    @Override
    public void itemMap() {
        // Default: không config item map
    }

    @Override
    public void itemBoss() {
        // Default: không config item boss
    }

    @Override
    public void cleanup() {
        // Default: không cần cleanup
    }

    public int getHeSoTnSm() {
        return 1;
    }
    
}
