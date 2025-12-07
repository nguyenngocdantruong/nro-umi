package com.girlkun.models.item;

import static com.girlkun.models.item.ItemTime.*;
import com.girlkun.models.player.Player;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;

/**
 *
 * @author nguyenngocdantruong
 */
public class ItemTimeEvent {

    public Player player;
    

    public ItemTimeEvent(Player player) {
        this.player = player;
    }

    // Item event Halloween
    public boolean isUseKeo1Mat;
    public long lastTimeUseKeo1Mat;

    public boolean isUseSupBiHacAm;
    public long lastTimeUseSupBiHacAm;

    public boolean isUseBanhGatoNhen;
    public long lastTimeUseBanhGatoNhen;

    public boolean isUseHambugerSau;
    public long lastTimeUseHambugerSau;
    
    // Item event Trung thu
    public boolean isUseDuoiKhi;
    public long lastTimeUseDuoiKhi;
    
    public long lastTimeUseBanhTrungThuGaQuay;
    public boolean isUseBanhTrungThuGaQuay;
    public long lastTimeUseBanhTrungThuThapCam;
    public boolean isUseBanhTrungThuThapCam;

    void update() {
        // Kẹo 1 mắt
        if (isUseKeo1Mat && Util.canDoWithTime(lastTimeUseKeo1Mat, TIME_BANH)) {
            isUseKeo1Mat = false;
            Service.gI().point(player);
        }

        // Súp bí hắc ám
        if (isUseSupBiHacAm && Util.canDoWithTime(lastTimeUseSupBiHacAm, TIME_BANH)) {
            isUseSupBiHacAm = false;
            Service.gI().point(player);
        }

        // Bánh gato nhện
        if (isUseBanhGatoNhen && Util.canDoWithTime(lastTimeUseBanhGatoNhen, TIME_BANH)) {
            isUseBanhGatoNhen = false;
            Service.gI().point(player);
        }

        // Hamburger nhện
        if (isUseHambugerSau && Util.canDoWithTime(lastTimeUseHambugerSau, TIME_BANH)) {
            isUseHambugerSau = false;
            Service.gI().point(player);
        }
        
        // Đuôi khỉ
        if (isUseDuoiKhi && Util.canDoWithTime(lastTimeUseDuoiKhi, TIME_BANH)) {
            isUseDuoiKhi = false;
            Service.gI().point(player);
        }
        
        // Gà quay
        if (isUseBanhTrungThuGaQuay && Util.canDoWithTime(lastTimeUseBanhTrungThuGaQuay, TIME_60P)) {
            isUseBanhTrungThuGaQuay = false;
            Service.gI().point(player);
        }
        
        // Thập cẩm
        if (isUseBanhTrungThuThapCam && Util.canDoWithTime(lastTimeUseBanhTrungThuThapCam, TIME_60P)) {
            isUseBanhTrungThuThapCam = false;
            Service.gI().point(player);
        }
    }

}
