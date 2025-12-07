package com.girlkun.models.event;

import com.girlkun.models.item.Item;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.map.Map;
import com.girlkun.models.map.Zone;
import java.util.List;

/**
 * Interface định nghĩa các phương thức cơ bản cho Event System
 *
 * @author Umi Team
 */
public interface IEvent {

    /**
     * Khởi tạo event - được gọi khi event được kích hoạt
     */
    void init();

    /**
     * Tạo các NPC liên quan đến event
     */
    void npc();

    /**
     * Tạo các Boss liên quan đến event
     */
    void boss();

    /**
     * Cấu hình item spawn trên map
     * @param map Map hiện tại để kiểm tra xem có hợp lệ không
     * @param MobId Quái hiện tại xem có drop k
     * @param zone Khu để drop nếu hợp lệ
     * @param x Toạ độ
     * @param y Toạ độ
     * @param playerId Người nhặt được nó (-1 nếu ai cũng nhặt được)
     * @return Return ra list item rơi ra từ quái trong event
     */
    List<ItemMap> itemMap(Map map, int MobId, Zone zone, int x, int y, long playerId);

    /**
     * Cấu hình item drop từ boss
     */
    void itemBoss();

    /**
     * Dọn dẹp tài nguyên khi event kết thúc
     */
    void cleanup();
    
    /**
     * Hiển thị message khi user login
     */
    String getThongBaoLogin();
}
