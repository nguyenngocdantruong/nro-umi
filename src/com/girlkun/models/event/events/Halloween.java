package com.girlkun.models.event.events;

import com.girlkun.consts.ConstNpc;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossType;
import com.girlkun.models.event.Event;
import com.girlkun.models.item.Item;
import com.girlkun.models.shop.ItemShop;
import com.girlkun.models.shop.Shop;
import com.girlkun.models.shop.TabShop;
import com.girlkun.models.Template.NpcTemplate;
import com.girlkun.server.Manager;
import com.girlkun.services.ItemService;

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
//        // Tạo NPC template trước
//        createBillBiNgoNpcTemplate();
//        // Tạo shop trước khi init các thành phần khác
//        createBillBiNgoShop();
//        super.init();
    }

    /**
     * Tạo NPC template cho Bill bí ngô
     */
    private void createBillBiNgoNpcTemplate() {
        try {
            // Mở rộng list để có đủ index cho BILL_BI_NGO (110)
            int requiredSize = ConstNpc.BILL_BI_NGO + 1;
            while (Manager.NPC_TEMPLATES.size() < requiredSize) {
                Manager.NPC_TEMPLATES.add(null);
            }

            // Tạo NPC template mới
            NpcTemplate npcTemplate = new NpcTemplate();
            npcTemplate.id = ConstNpc.BILL_BI_NGO;
            npcTemplate.name = "Bill Bí Ngô";
            npcTemplate.head = 754;   // Head ID
            npcTemplate.body = 755;   // Body ID
            npcTemplate.leg = 756;    // Leg ID
            npcTemplate.avatar = 739; // Avatar/cải trang ID

            // Set vào đúng vị trí index
            Manager.NPC_TEMPLATES.set(ConstNpc.BILL_BI_NGO, npcTemplate);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tạo shop Bill bí ngô cho event Halloween
     */
    private void createBillBiNgoShop() {
        try {
            // Tạo shop
            billBiNgoShop = new Shop();
            billBiNgoShop.id = 999; // ID tạm thời cho shop event
            billBiNgoShop.npcId = ConstNpc.BILL_BI_NGO; // Sử dụng NPC mới
            billBiNgoShop.tagName = "BILL_BI_NGO";
            billBiNgoShop.typeShop = 3; // SPEC_SHOP

            // Tạo tab shop
            TabShop tabShop = new TabShop();
            tabShop.shop = billBiNgoShop;
            tabShop.id = 9991;
            tabShop.name = "Đổi quà";

            // Tạo item cải trang Bill bí ngô
            ItemShop itemShop = new ItemShop();
            itemShop.tabShop = tabShop;
            itemShop.id = 99901;
            itemShop.temp = ItemService.gI().getTemplate(739); // Template ID của cải trang
            itemShop.isNew = true;
            itemShop.typeSell = 2; // COST_ITEM_SPEC - đổi bằng item
            itemShop.iconSpec = 901; // ID của item dùng để đổi
            itemShop.cost = 99; // Số lượng item cần

            // Thêm options cho cải trang (head, body, leg)
            itemShop.options.add(new Item.ItemOption(77, 754)); // Head ID
            itemShop.options.add(new Item.ItemOption(78, 755)); // Body ID
            itemShop.options.add(new Item.ItemOption(79, 756)); // Leg ID

            // Thêm item vào tab
            tabShop.itemShops.add(itemShop);

            // Thêm tab vào shop
            billBiNgoShop.tabShops.add(tabShop);

            // Thêm shop vào Manager
            Manager.SHOPS.add(billBiNgoShop);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
    @Override
    public void npc() {
        // Tạo NPC Bill bí ngô tại map 5 (Dinh Ký Đỗn)
//        createNpc(5, ConstNpc.BILL_BI_NGO, 1604, 408);
    }

    @Override
    public void boss() {
        // Spawn 10 boss Bí ma
        createBoss(BossType.BI_MA, 10);

        // Spawn 10 boss Ma trơi
        createBoss(BossType.MA_TROI, 10);

        // Spawn 10 boss Dơi
        createBoss(BossType.DOI, 10);
    }

    @Override
    public String getThongBaoLogin() {
        return "Event Halloween đã bắt đầu, hãy đi săn boss Dơi, Ma trơi và Bí ma để đổi những cải trang mới nhất nào. Admin tặng x3 TNSM.";
    }

    @Override
    public void cleanup() {
        // Xóa shop Bill bí ngô khi event kết thúc
        if (billBiNgoShop != null) {
            Manager.SHOPS.remove(billBiNgoShop);
            billBiNgoShop.dispose();
            billBiNgoShop = null;
        }

        // Xóa NPC template (set về null)
        if (Manager.NPC_TEMPLATES.size() > ConstNpc.BILL_BI_NGO) {
            Manager.NPC_TEMPLATES.set(ConstNpc.BILL_BI_NGO, null);
        }
    }
}
