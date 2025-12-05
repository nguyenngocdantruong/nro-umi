package com.girlkun.models.event.events;

import com.girlkun.consts.ConstNpc;
import com.girlkun.models.boss.BossType;
import com.girlkun.models.event.Event;
import com.girlkun.models.shop.Shop;
import com.girlkun.models.event.ConstEvent;
import com.girlkun.server.Manager;

/**
 * Halloween Event
 * Spawn 3 loại boss: Bí ma, Ma trơi, Dơi (mỗi loại 10 instances)
 *
 * @author Umi Team
 */
public class Halloween extends Event {

    private Shop billBiNgoShop;

    @Override
    public int getHeSoTnSm() {
        return 3;
    }

    @Override
    public void init() {
        // Gọi super.init() để trigger npc(), boss(), itemMap(), itemBoss()
        super.init();

        // Nếu cần tạo shop, uncomment dòng dưới
        // createBillBiNgoShop();
    }

    @Override
    public void npc() {
        // Tạo NPC Bill bí ngô tại map 5 (Đảo Kame), tọa độ (1604, 408)
        createNpc(5, ConstNpc.BILL_BI_NGO, 232, 288);
    }

    @Override
    public void boss() {
        // Spawn 10 boss Bí ma
        createBoss(BossType.BI_MA, 10);

        // Spawn 10 boss Ma trơi
        createBoss(BossType.MA_TROI, 10);

        // Spawn 10 boss Dơi
        createBoss(BossType.DOI, 10);

        // Spawn 10 boss Dracula
        createBoss(BossType.DRACULA_HALLOWEEN, 10);
    }

    @Override
    public String getThongBaoLogin() {
        return "Event Halloween đã bắt đầu, hãy đi săn boss Dơi, Ma trơi và Bí ma để đổi những cải trang mới nhất nào. Admin tặng x3 TNSM.";
    }

    @Override
    protected String getNameEvent() {
        return "Halloween";
    }

    @Override
    protected ConstEvent GetEventType() {
        return ConstEvent.HALLOWEEN;
    }
}
