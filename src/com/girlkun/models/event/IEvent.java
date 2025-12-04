package com.girlkun.models.event;

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
     */
    void itemMap();

    /**
     * Cấu hình item drop từ boss
     */
    void itemBoss();

    /**
     * Dọn dẹp tài nguyên khi event kết thúc
     */
    void cleanup();
}
