package com.girlkun.models.event.events;

import com.girlkun.consts.ConstNpc;
import com.girlkun.models.boss.BossType;
import com.girlkun.models.event.Event;
import com.girlkun.models.shop.Shop;
import com.girlkun.models.event.ConstEvent;
import com.girlkun.models.item.ConstItem;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.map.Map;
import com.girlkun.models.map.Zone;
import com.girlkun.server.Manager;
import com.girlkun.utils.Util;
import java.util.ArrayList;
import java.util.List;

/**
 * Halloween Event
 * Spawn 3 loại boss: Bí ma, Ma trơi, Dơi (mỗi loại 10 instances)
 *
 * @author Umi Team
 */
public class Debug extends Event {


    @Override
    public int getHeSoTnSm() {
        return 20;
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void npc() {
        createNpc(5, ConstNpc.BILL_BI_NGO, 232, 288);
        createNpc(42, ConstNpc.THO_DAI_CA, 111, 288);
        super.npc();
    }
    
    @Override
    public List<ItemMap> itemMap(Map map, int MobId, Zone zone, int x, int y, long playerId) {
        List<ItemMap> items = new ArrayList<>();
        if(Util.isTrue(100, 100)){
            items.add(new ItemMap(zone, ConstItem.HALLOWEEN_BI_NGO_DROP_TU_QUAI, 1, x, y, playerId));
        }
        return items;
    }

    @Override
    public void boss() {
        // Spawn 10 boss Khỉ xayda
        createBoss(BossType.KHI_XAYDA, 10);
        
//        // Spawn 10 boss Bí ma
//        createBoss(BossType.BI_MA, 10);
//
//        // Spawn 10 boss Ma trơi
//        createBoss(BossType.MA_TROI, 10);
//
//        // Spawn 10 boss Dơi
//        createBoss(BossType.DOI, 10);
//
//        // Spawn 10 boss Dracula
//        createBoss(BossType.DRACULA_HALLOWEEN, 10);
    }

    @Override
    public String getThongBaoLogin() {
        return "Đây là event debug. Nếu bạn thấy event này vui lòng báo admin sửa lại.";
    }

    @Override
    protected String getNameEvent() {
        return "Debug";
    }

    @Override
    protected ConstEvent GetEventType() {
        return ConstEvent.DEBUG;
    }
}
