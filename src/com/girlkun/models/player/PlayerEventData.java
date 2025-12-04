package com.girlkun.models.player;

import java.util.HashMap;
import java.util.Map;

/**
 * Quản lý điểm sự kiện của player
 * Mỗi event có điểm riêng để track progress
 *
 * @author Umi Team
 */
public class PlayerEventData {

    private Player player;

    // Event-specific points
    private int halloweenPoints;
    private int christmasPoints;
    private int trungThuPoints;
    private int lunarNewYearPoints;
    private int hungVuongPoints;

    // Generic event data storage cho các event tùy chỉnh
    private Map<String, Integer> customEventData;

    public PlayerEventData(Player player) {
        this.player = player;
        this.customEventData = new HashMap<>();
    }

    //============================================================
    // HALLOWEEN EVENT
    //============================================================
    public int getHalloweenPoints() {
        return halloweenPoints;
    }

    public void setHalloweenPoints(int points) {
        this.halloweenPoints = points;
    }

    public void addHalloweenPoints(int points) {
        this.halloweenPoints += points;
    }

    public void subHalloweenPoints(int points) {
        this.halloweenPoints -= points;
        if (this.halloweenPoints < 0) {
            this.halloweenPoints = 0;
        }
    }

    //============================================================
    // CHRISTMAS EVENT
    //============================================================
    public int getChristmasPoints() {
        return christmasPoints;
    }

    public void setChristmasPoints(int points) {
        this.christmasPoints = points;
    }

    public void addChristmasPoints(int points) {
        this.christmasPoints += points;
    }

    public void subChristmasPoints(int points) {
        this.christmasPoints -= points;
        if (this.christmasPoints < 0) {
            this.christmasPoints = 0;
        }
    }

    //============================================================
    // TRUNG THU EVENT
    //============================================================
    public int getTrungThuPoints() {
        return trungThuPoints;
    }

    public void setTrungThuPoints(int points) {
        this.trungThuPoints = points;
    }

    public void addTrungThuPoints(int points) {
        this.trungThuPoints += points;
    }

    public void subTrungThuPoints(int points) {
        this.trungThuPoints -= points;
        if (this.trungThuPoints < 0) {
            this.trungThuPoints = 0;
        }
    }

    //============================================================
    // LUNAR NEW YEAR (TẾT) EVENT
    //============================================================
    public int getLunarNewYearPoints() {
        return lunarNewYearPoints;
    }

    public void setLunarNewYearPoints(int points) {
        this.lunarNewYearPoints = points;
    }

    public void addLunarNewYearPoints(int points) {
        this.lunarNewYearPoints += points;
    }

    public void subLunarNewYearPoints(int points) {
        this.lunarNewYearPoints -= points;
        if (this.lunarNewYearPoints < 0) {
            this.lunarNewYearPoints = 0;
        }
    }

    //============================================================
    // HUNG VUONG EVENT
    //============================================================
    public int getHungVuongPoints() {
        return hungVuongPoints;
    }

    public void setHungVuongPoints(int points) {
        this.hungVuongPoints = points;
    }

    public void addHungVuongPoints(int points) {
        this.hungVuongPoints += points;
    }

    public void subHungVuongPoints(int points) {
        this.hungVuongPoints -= points;
        if (this.hungVuongPoints < 0) {
            this.hungVuongPoints = 0;
        }
    }

    //============================================================
    // CUSTOM EVENT DATA
    //============================================================
    /**
     * Set custom event data cho các event đặc biệt
     *
     * @param eventKey Key định danh event (ví dụ: "valentine", "summer")
     * @param value Giá trị điểm
     */
    public void setCustomData(String eventKey, int value) {
        customEventData.put(eventKey, value);
    }

    /**
     * Lấy custom event data
     */
    public int getCustomData(String eventKey) {
        return customEventData.getOrDefault(eventKey, 0);
    }

    /**
     * Thêm custom event points
     */
    public void addCustomData(String eventKey, int value) {
        int current = customEventData.getOrDefault(eventKey, 0);
        customEventData.put(eventKey, current + value);
    }

    /**
     * Trừ custom event points
     */
    public void subCustomData(String eventKey, int value) {
        int current = customEventData.getOrDefault(eventKey, 0);
        int newValue = current - value;
        if (newValue < 0) {
            newValue = 0;
        }
        customEventData.put(eventKey, newValue);
    }

    //============================================================
    // UTILITY METHODS
    //============================================================
    /**
     * Tổng điểm của tất cả các event
     */
    public int getTotalEventPoints() {
        return halloweenPoints + christmasPoints + trungThuPoints
               + lunarNewYearPoints + hungVuongPoints;
    }

    /**
     * Reset tất cả event data (dùng khi bắt đầu season mới)
     */
    public void resetAllEventData() {
        halloweenPoints = 0;
        christmasPoints = 0;
        trungThuPoints = 0;
        lunarNewYearPoints = 0;
        hungVuongPoints = 0;
        customEventData.clear();
    }

    /**
     * Reset điểm của một event cụ thể
     */
    public void resetEventPoints(String eventType) {
        if (eventType == null) return;
        String key = eventType.toLowerCase();

        switch (key) {
            case "halloween":
                halloweenPoints = 0;
                break;

            case "christmas":
                christmasPoints = 0;
                break;

            case "trung_thu":
            case "trung thu":
                trungThuPoints = 0;
                break;

            case "lunar_new_year":
            case "tet":
                lunarNewYearPoints = 0;
                break;

            case "hung_vuong":
                hungVuongPoints = 0;
                break;

            default:
                customEventData.remove(eventType);
                break;
        }
    }
}
