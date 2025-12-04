package com.girlkun.models.event;

import com.girlkun.models.event.events.*;
import com.girlkun.utils.Logger;

/**
 * Singleton quản lý toàn bộ Event System
 * Điều khiển việc kích hoạt và chuyển đổi giữa các events
 *
 * @author Umi Team
 */
public class EventManager {

    private static EventManager instance;

    // Event flags - bật/tắt các sự kiện
    public static boolean HALLOWEEN = true;
    public static boolean CHRISTMAS = false;
    public static boolean TRUNG_THU = false;
    public static boolean LUNAR_NEW_YEAR = false;
    public static boolean HUNG_VUONG = false;
    public static boolean INTERNATIONAL_WOMENS_DAY = false;

    private Event currentEvent;

    /**
     * Singleton accessor theo Umi style
     */
    public static EventManager gI() {
        if (instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    /**
     * Private constructor cho singleton pattern
     */
    private EventManager() {
        // Private constructor
    }

    /**
     * Khởi tạo event system - kiểm tra flags và active event tương ứng
     * Chỉ một event có thể active tại một thời điểm (priority order)
     */
    
    // ------------------ InitEvent ------------------
    public void init() {
        Logger.log(Logger.CYAN, "Đang khởi tạo hệ thống sự kiện...\n");

        // Cleanup event trước nếu có
        if (currentEvent != null) {
            try {
                currentEvent.cleanup();
            } catch (Exception e) {
                Logger.logException(EventManager.class, e, "Lỗi cleanup event cũ");
            }
        }

        // Khởi tạo event dựa trên flags (priority: Halloween > Christmas > Trung Thu > ...)
        if (HALLOWEEN) {
            currentEvent = new Halloween();
            currentEvent.init();
            Logger.success("✓ Sự kiện Halloween đã được kích hoạt\n");
        } else if (CHRISTMAS) {
            currentEvent = new Christmas();
            currentEvent.init();
            Logger.success("✓ Sự kiện Christmas đã được kích hoạt\n");
        } else if (TRUNG_THU) {
            currentEvent = new TrungThu();
            currentEvent.init();
            Logger.success("✓ Sự kiện Trung Thu đã được kích hoạt\n");
        } else if (LUNAR_NEW_YEAR) {
            // Future implementation
            Logger.log(Logger.YELLOW, "⚠ Sự kiện Tết chưa được triển khai\n");
            currentEvent = new Default();
            currentEvent.init();
        } else if (HUNG_VUONG) {
            // Future implementation
            Logger.log(Logger.YELLOW, "⚠ Sự kiện Hùng Vương chưa được triển khai\n");
            currentEvent = new Default();
            currentEvent.init();
        } else if (INTERNATIONAL_WOMENS_DAY) {
            // Future implementation
            Logger.log(Logger.YELLOW, "⚠ Sự kiện 20/10 chưa được triển khai\n");
            currentEvent = new Default();
            currentEvent.init();
        } else {
            // Không có event nào active
            currentEvent = new Default();
            currentEvent.init();
            Logger.log(Logger.WHITE, "✓ Chế độ bình thường (không có sự kiện đặc biệt)\n");
        }
    }

    /**
     * Lấy event hiện tại đang active
     */
    public Event getCurrentEvent() {
        if(currentEvent == null){
            currentEvent = new Default();
        }
        return currentEvent;
    }

    /**
     * Kiểm tra có event nào đang active không
     */
    public boolean isEventActive() {
        return HALLOWEEN || CHRISTMAS || TRUNG_THU || LUNAR_NEW_YEAR
               || HUNG_VUONG || INTERNATIONAL_WOMENS_DAY;
    }

    /**
     * Reload event system (hot reload không cần restart server)
     */
    public void reload() {
        Logger.log(Logger.CYAN, "Đang reload hệ thống sự kiện...\n");
        init();
    }
}
