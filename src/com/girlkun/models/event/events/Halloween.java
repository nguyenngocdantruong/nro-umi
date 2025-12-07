package com.girlkun.models.event.events;

import com.girlkun.consts.ConstMap;
import com.girlkun.consts.ConstNpc;
import com.girlkun.models.boss.BossType;
import com.girlkun.models.event.Event;
import com.girlkun.models.shop.Shop;
import com.girlkun.models.event.ConstEvent;
import com.girlkun.models.event.EventManager;
import com.girlkun.models.item.ConstItem;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.map.Map;
import com.girlkun.models.map.Zone;
import com.girlkun.models.npc.Npc;
import com.girlkun.models.player.Player;
import com.girlkun.models.shop.ShopServiceNew;
import com.girlkun.server.Manager;
import com.girlkun.services.InventoryServiceNew;
import com.girlkun.services.ItemService;
import com.girlkun.services.NpcService;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;
import java.util.ArrayList;
import java.util.List;

/**
 * Halloween Event
 * Spawn 3 loại boss: Bí ma, Ma trơi, Dơi (mỗi loại 10 instances)
 *
 * @author Umi Team
 */
public class Halloween extends Event {

    public static final String HUONG_DAN_CUA_BILL = "Đêm Halloween đã đến, cùng ta điểm qua các sự kiện:\n"
            + "1) SĂN NGỌC BÍ NGÔ TRIỆU HỒI RỒNG XƯƠNG:\b"
            + "Các cư dân cần phải thu thập đầy đủ những viên Ngọc bí để gọi Rồng Xương ban một điều ước.\b"
            + "Trong những ngày bầu trời đen tối sẽ xuất hiện các dơi nhí, bộ xương, ma trơi và dracula manh động đánh bất cứ ai mà nó bắt gặp và truyền bệnh để nạn nhân trở thành 1 trong số chúng.\b"
            + "Khi hạ những âm binh này các bạn sẽ có cơ hội nhận được Ngọc bí (1 sao đến 7 sao).\n"
            + "2) VỊ TRÍ XUẤT HIỆN BOSS:\b"
            + "- Dơi nhí: ngẫu nhiên ở Làng Aru, Làng Kakarot, Làng Mori, Nam Kame, Đảo Bulong, Nam Guru, Đông Nam Guru, Thung lũng Đen, Bờ vực đen.\b"
            + "- Ma trơi: ngẫu nhiên ở Siêu Thị, Thung lũng phía bắc, Thị trấn Ginder.\b"
            + "- Bí ma: ngẫu nhiên ở Nam Guru, Nam Kame, Đảo Bulong, Đông Nam Guru, Thung lũng đen, Bờ vực đen.\b"
            + "- Dracula: ngẫu nhiên ở Nam Guru, Nam Kame, Đảo Bulong, Đông Nam Guru, Thung lũng đen, Bờ vực đen.\n"
            + "3) ĐIỀU ƯỚC:\b"
            + "Khi đủ bộ Ngọc Rồng Bí các bạn sẽ nhận được Điều Ước Mới 2025.\n"
            + "4) CHO KẸO HAY BỊ GHẸO:\b"
            + "Trong thời gian diễn ra lễ hội Halloween sẽ xuất hiện Bí Ma, các cư dân cần săn Bí Ma, Dracula để nhặt những Kẹo bàn tay.\b"
            + "- Bí Ma sẽ xuất hiện thường xuyên tại nơi âm u, hẻo lánh, Nam Guru, Nam Kame, nơi vắng người, thung lũng đen.\b"
            + "- Đánh quái mỗi ngày để nhặt bí ngô thường, dùng để đổi quà tại NPC Bill bí ngô ở Vách núi đen.\n"
            + "Hehe, nhớ cho ta kẹo nhé, không thì bị ghẹo đó!";

    public static Npc BillBiNgo(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (!(EventManager.gI().isCurrentEvent(ConstEvent.HALLOWEEN))) {
                    createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Ta đang chuẩn bị bí ngô và hoá trang.\n" +
                                    "Ngươi biết ở đâu bán bí ngô không?",
                            "Từ chối");
                    return;
                }
                if (canOpenNpc(player)) {
                    createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Trick or treat!\n" +
                                    "Ta có thể giúp gì cho ngươi?",
                            "Shop Halloween",
                            "Đổi kẹo",
                            "Đổi bí ngô",
                            "Hướng dẫn");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (!(EventManager.gI().isCurrentEvent(ConstEvent.HALLOWEEN)))
                    return;
                if (canOpenNpc(player)) {
                    if (this.mapId == ConstMap.VACH_NUI_DEN) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: // Shop Halloween
                                    ShopServiceNew.gI().opendShop(player, "BILL_HALLOWEEN", false);
                                    break;
                                case 1: // Đổi kẹo
                                    createOtherMenu(player, ConstNpc.MENU_EVENT,
                                            "Sử dụng Kẹo bàn tay để đổi cải trang\n" +
                                                    "1 Cải trang = 99 Kẹo bàn tay\n"
                                                    + "Cải trang Dơi nhí (40%)\n"
                                                    + "Cải trang Ma trơi (40%)\n"
                                                    + "Cải trang Bộ xương (15%)\n"
                                                    + "Cải trang Bill Bí ngô VIP (5%)",
                                            "Đổi ngay", "Đóng");
                                    break;
                                case 2: // Đổi bí ngô 585 drop từ quái
                                    ShopServiceNew.gI().opendShop(player, "BILL_HALLOWEEN_BINGO", false);
                                    break;
                                case 3: // Hướng dẫn
                                    NpcService.gI().createTutorial(player, this.avartar, HUONG_DAN_CUA_BILL);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_EVENT) {
                            switch (select) {
                                case 0: // Đổi quà
                                    // Logic đổi quà ở đây
                                    Item biNgo = InventoryServiceNew.gI().findItemBag(player, 901); // ID bí ngô
                                    if (biNgo != null && biNgo.quantity >= 99) {
                                        if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
                                            this.npcChat(player, "Hành trang đã đầy");
                                            break;
                                        }

                                        // Trừ bí ngô
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, biNgo, 99);

                                        // Tặng cải trang
                                        Item caiTrang;
                                        // 40% là ma trơi
                                        if (Util.isTrue(40, 100)) {
                                            caiTrang = ItemService.gI().createNewItem((short) 642); // Ma trơi

                                            caiTrang.itemOptions.add(new Item.ItemOption(8, 3)); // Hút 3% hp ki
                                            caiTrang.itemOptions.add(new Item.ItemOption(50, 20)); // 20% sd
                                            caiTrang.itemOptions.add(new Item.ItemOption(77, 17)); // 17% sd
                                            caiTrang.itemOptions.add(new Item.ItemOption(103, 17)); // 17% sd
                                            caiTrang.itemOptions.add(new Item.ItemOption(213, 0)); // 0 bị biến bí ngô
                                            caiTrang.itemOptions.add(new Item.ItemOption(154, 0)); // 0 thể bán lại
                                        }
                                        // 15% là bộ xương
                                        else if (Util.isTrue(15, 100)) {
                                            short caiTrangXuongId = (short) 644; // td
                                            switch (player.gender) {
                                                case 1: { // nm
                                                    caiTrangXuongId = (short) 645;
                                                    break;
                                                }
                                                case 2: { // xd
                                                    caiTrangXuongId = (short) 646;
                                                    break;
                                                }
                                            }
                                            caiTrang = ItemService.gI().createNewItem(caiTrangXuongId);
                                            caiTrang.itemOptions.add(new Item.ItemOption(8, 4)); // Hút 4% hp ki
                                            caiTrang.itemOptions.add(new Item.ItemOption(50, 22)); // 22% sd
                                            caiTrang.itemOptions.add(new Item.ItemOption(77, 20)); // 20% sd
                                            caiTrang.itemOptions.add(new Item.ItemOption(103, 20)); // 20% sd
                                            caiTrang.itemOptions.add(new Item.ItemOption(213, 0)); // 0 bị biến bí ngô
                                            caiTrang.itemOptions.add(new Item.ItemOption(154, 0)); // 0 thể bán lại

                                        }
                                        // 5% là bill bí ngô vip
                                        else if (Util.isTrue(5, 100)) {
                                            short caiTrangXuongId = (short) 739;
                                            caiTrang = ItemService.gI().createNewItem(caiTrangXuongId);

                                            caiTrang.itemOptions.add(new Item.ItemOption(76, 0)); // VIP
                                            caiTrang.itemOptions.add(new Item.ItemOption(50, 30)); // 30% sd
                                            caiTrang.itemOptions.add(new Item.ItemOption(77, 24)); // 24% hp
                                            caiTrang.itemOptions.add(new Item.ItemOption(103, 24)); // 24% sd
                                            caiTrang.itemOptions.add(new Item.ItemOption(163, 0)); // Biến bí ngô
                                            caiTrang.itemOptions.add(new Item.ItemOption(213, 0)); // 0 bị biến bí ngô
                                            caiTrang.itemOptions.add(new Item.ItemOption(154, 0)); // 0 thể bán lại

                                        }
                                        // Còn lại là dơi nhí
                                        else {
                                            caiTrang = ItemService.gI().createNewItem((short) 643); // Dơi nhí

                                            caiTrang.itemOptions.add(new Item.ItemOption(8, 3)); // Hút 3% hp ki
                                            caiTrang.itemOptions.add(new Item.ItemOption(50, 20)); // 20% sd
                                            caiTrang.itemOptions.add(new Item.ItemOption(77, 17)); // 20% sd
                                            caiTrang.itemOptions.add(new Item.ItemOption(103, 17)); // 20% sd
                                            caiTrang.itemOptions.add(new Item.ItemOption(213, 0)); // 0 bị biến bí ngô
                                            caiTrang.itemOptions.add(new Item.ItemOption(154, 0)); // 0 thể bán lại
                                        }

                                        InventoryServiceNew.gI().addItemBag(player, caiTrang);
                                        InventoryServiceNew.gI().sendItemBags(player);

                                        Service.gI().sendThongBao(player, "Đổi thành công!");
                                    } else {
                                        Service.gI().sendThongBao(player, "Bạn cần 99 Kẹo bàn tay trong hành trang!");
                                    }
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    @Override
    public int getHeSoTnSm() {
        return 3;
    }

    @Override
    public List<ItemMap> itemMap(Map map, int MobId, Zone zone, int x, int y, long playerId) {
        List<ItemMap> items = new ArrayList<>();
        if (Util.isTrue(30, 100)) {
            ItemMap biNgoNhiNhanh = new ItemMap(zone, ConstItem.HALLOWEEN_BI_NGO_DROP_TU_QUAI, 1, x, y, playerId);
            biNgoNhiNhanh.quantity = 1;
            items.add(biNgoNhiNhanh);
        }
        return items;
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void npc() {
        // Tạo NPC Bill bí ngô tại map 5 (Đảo Kame), tọa độ (1604, 408)
        createNpc(ConstMap.VACH_NUI_DEN, ConstNpc.BILL_BI_NGO, 1449, 360);
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
