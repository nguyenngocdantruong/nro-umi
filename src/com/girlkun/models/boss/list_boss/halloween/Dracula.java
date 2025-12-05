package com.girlkun.models.boss.list_boss.halloween;

import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.boss.BossStatus;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.ItemTimeService;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;



public class Dracula extends BossHalloween {
    public Dracula() throws Exception {
        super(BossID.DRACULA_HALLOWEEN, BossesData.DRACULA_HALLOWEEN);
    }
    
     @Override
    public void reward(Player plKill) {
        // Kẹo bàn tay
        int itemIdBiDrop = 901;
        int soluong = Util.nextInt(1, 5);
        for(int i = 0; i < soluong ; i++){
            Service.getInstance().dropItemMap(this.zone, new ItemMap(zone, itemIdBiDrop, 1, this.location.x, this.location.y, -1));
        }
        // 10% rơi ra cải trang Dracula vĩnh viễn
        if(Util.isTrue(10, 100)){
            int ctDracula = 448;
            ItemMap item = new ItemMap(zone, ctDracula, 1, this.location.x, this.location.y, plKill.id);
            item.options.add(new Item.ItemOption(104, 50));
            item.options.add(new Item.ItemOption(154, 0));
            item.options.add(new Item.ItemOption(209, 0));
            Service.getInstance().dropItemMap(this.zone, item);
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