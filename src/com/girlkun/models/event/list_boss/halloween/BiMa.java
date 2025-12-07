package com.girlkun.models.event.list_boss.halloween;

import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.boss.BossStatus;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.ItemTimeService;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;



public class BiMa extends BossHalloween {
    public BiMa() throws Exception {
        super(BossID.BI_MA, BossesData.BI_MA);
    }
    
     @Override
    public void reward(Player plKill) {
        // Kẹo bàn tay
        int itemIdBiDrop = 901;
        int soluong = Util.nextInt(1, 4);
        for(int i = 0; i < soluong ; i++){
            Service.getInstance().dropItemMap(this.zone, new ItemMap(zone, itemIdBiDrop, 1, this.location.x, this.location.y, -1));
        }
        super.reward(plKill);
    }
    
    @Override
    public void affectPlayer(Player player) {
        if(!player.effectSkin.isBiHoaXuong && !player.nPoint.hasHalloweenCt){
            player.effectSkin.isBiHoaXuong = true;
            short h = 545;
            switch(player.gender){
                // td
                case 0: {
                    h = 545;
                    break;
                }
                // nm
                case 1: {
                    h = 547;
                    break;
                }
                // xd
                case 2: {
                    h = 546;
                    break;
                }
            }
            Service.gI().Send_CaiTrang_Halloween(player, h, (short)548, (short)549);
            ItemTimeService.gI().sendItemTime(player, 5101, (int) player.effectSkin.timeHoaXuong);
            player.effectSkin.lastTimeBiHoaXuong = System.currentTimeMillis();
            this.chat("Cho cái kẹo nè!");
        }
    }
}