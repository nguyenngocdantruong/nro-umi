package com.girlkun.models.boss.dhvt;

import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.player.Player;
import com.girlkun.services.EffectSkillService;
import com.girlkun.utils.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author BTH sieu cap vippr0 
 */
public class ThienXinHang extends BossDHVT {

    private long lastTimePhanThan = System.currentTimeMillis();
    private final List<ThienXinHangClone> clones = new ArrayList<>();

    public ThienXinHang(Player player) throws Exception {
        super(BossID.THIEN_XIN_HANG, BossesData.THIEN_XIN_HANG);
        this.playerAtt = player;
    }

    @Override
    public void attack() {
        super.attack();
        try {
            EffectSkillService.gI().removeStun(this);
            if (Util.canDoWithTime(lastTimePhanThan, 20000)) {
                lastTimePhanThan = System.currentTimeMillis();
                phanThan();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void phanThan() {
        try {
            // Xóa các clone cũ trước khi tạo clone mới
            clearAllClones();
            
            // Tạo clone mới và lưu vào danh sách
            clones.add(new ThienXinHangClone(BossID.THIEN_XIN_HANG_CLONE, playerAtt));
            clones.add(new ThienXinHangClone(BossID.THIEN_XIN_HANG_CLONE1, playerAtt));
            clones.add(new ThienXinHangClone(BossID.THIEN_XIN_HANG_CLONE2, playerAtt));
            clones.add(new ThienXinHangClone(BossID.THIEN_XIN_HANG_CLONE3, playerAtt));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void die(Player plKill) {
        super.die(plKill);
        clearAllClones();
    }
    
    @Override
    public void leaveMap() {
        super.leaveMap();
        clearAllClones();
    }
    
    private void clearAllClones() {
        // Xóa tất cả các clone
        for (ThienXinHangClone clone : clones) {
            if (clone != null) {
                clone.leaveMap();
            }
        }
        clones.clear();
    }
}