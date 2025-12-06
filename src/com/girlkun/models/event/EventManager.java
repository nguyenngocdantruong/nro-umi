package com.girlkun.models.event;

import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossManager;
import com.girlkun.models.boss.BossStatus;
import static com.girlkun.models.event.ConstEvent.GIANG_SINH;
import static com.girlkun.models.event.ConstEvent.HALLOWEEN;
import static com.girlkun.models.event.ConstEvent.MAC_DINH;
import static com.girlkun.models.event.ConstEvent.TRUNG_THU;
import com.girlkun.models.event.events.*;
import com.girlkun.models.player.Player;
import com.girlkun.services.Service;
import com.girlkun.utils.Logger;
import java.util.*;

/**
 * Singleton quản lý toàn bộ Event System
 * Điều khiển việc kích hoạt và chuyển đổi giữa các events
 *
 * @author Umi Team
 */
public class EventManager {

    private static EventManager instance;

    private List<Boss> bossesEvent = new ArrayList<Boss>();

    public void clearBossesEvent() {
        for (Boss boss : bossesEvent) {
            boss.leaveMap();
            BossManager.gI().removeBoss(boss);
        }
        bossesEvent.clear();
    }

    public void addBossesEvent(Boss b) {
        bossesEvent.add(b);
    }

    // Event flags kiểm tra các npc đã init chưa
    public boolean DEBUG = false;
    public boolean HALLOWEEN = false;
    public boolean CHRISTMAS = false;
    public boolean TRUNG_THU = false;
    public boolean LUNAR_NEW_YEAR = false;
    public boolean HUNG_VUONG = false;
    public boolean INTERNATIONAL_WOMENS_DAY = false;
    
    public long lastTimeChangeEvent; 

    public void setEventFlag(ConstEvent ev) {
        switch (ev) {
            case DEBUG:{
                DEBUG = true;
                break;
            }
            case GIANG_SINH: {
                CHRISTMAS = true;
                break;
            }
            case HALLOWEEN: {
                HALLOWEEN = true;
                break;
            }
            case TRUNG_THU: {
                TRUNG_THU = true;
                break;
            }
        }
    }

    public boolean getEventFlag(ConstEvent ev) {
        switch (ev) {
            case DEBUG : {
                return DEBUG;
            }
            case GIANG_SINH: {
                return CHRISTMAS;
            }
            case HALLOWEEN: {
                return HALLOWEEN;
            }
            case TRUNG_THU: {
                return TRUNG_THU;
            }
        }
        return true;
    }

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

        // Không có event nào active
        if(Logger.DEBUG && false){
            Logger.debug("Chế độ Debug event. Nếu bạn thấy cái này vui lòng liên hệ Admin fix lại.");
            currentEvent = new Debug();
        }
        else{
            Logger.debug("Chế độ bình thường (không có sự kiện đặc biệt)");
            currentEvent = new Default();
        }
        currentEvent.init();
    }

    /**
     * Lấy event hiện tại đang active
     */
    public Event getCurrentEvent() {
        if (currentEvent == null) {
            currentEvent = new Default();
        }
        return currentEvent;
    }

    public boolean isCurrentEvent(ConstEvent typeEvent) {
        switch (typeEvent) {
            case DEBUG:{
                return getCurrentEvent() instanceof Debug;
            }
            case GIANG_SINH: {
                return getCurrentEvent() instanceof Christmas;
            }
            case HALLOWEEN: {
                return getCurrentEvent() instanceof Halloween;
            }
            case MAC_DINH: {
                return getCurrentEvent() instanceof Default;
            }
            case TRUNG_THU: {
                return getCurrentEvent() instanceof TrungThu;
            }
            default:
                return false;
        }
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

    /**
     * Thay đổi event - chạy async để không block player
     */
    public void changeEvent(Player pl, ConstEvent typeEvent) {
        int s = (int)(System.currentTimeMillis() - lastTimeChangeEvent)/1000;
        s = 301;
        if(s < 300){ // 5 phút
            String tb = String.format("Bạn thay đổi event quá nhanh, vui lòng đợi %d giây nữa!", 300 - s);
            Service.gI().sendThongBao(pl, tb);
            return;
        }
        
        // Cleanup event cũ (đồng bộ, nhanh)
        if (currentEvent != null) {
            currentEvent.cleanup();
        }
        Service.gI().sendThongBao(pl, "Bắt đầu khởi động event mới!");
        // Tạo event mới
        switch (typeEvent) {
            case DEBUG : {
                currentEvent = new Debug();
                break;
            }
            case GIANG_SINH: {
                currentEvent = new Christmas();
                break;
            }
            case HALLOWEEN: {
                currentEvent = new Halloween();
                break;
            }
            case MAC_DINH: {
                currentEvent = new Default();
                break;
            }
            case TRUNG_THU: {
                currentEvent = new TrungThu();
                break;
            }
        }
        // Init async trong thread riêng để không block player
        new Thread(() -> {
            try {
                currentEvent.init();
                Logger.success("Event " + typeEvent.name() + " đã khởi tạo xong!\n");
                lastTimeChangeEvent = System.currentTimeMillis();
            } catch (Exception e) {
                Logger.logException(EventManager.class, e, "Lỗi init event async");
            }
        }, "EventInitThread-" + typeEvent.name()).start();
    }
}
