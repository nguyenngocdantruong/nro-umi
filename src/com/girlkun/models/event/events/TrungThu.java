package com.girlkun.models.event.events;

import com.girlkun.consts.ConstMap;
import com.girlkun.consts.ConstNpc;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.event.ConstEvent;
import com.girlkun.models.event.Event;
import com.girlkun.models.event.EventManager;
import com.girlkun.models.item.ConstItem;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.map.Map;
import com.girlkun.models.map.Zone;
import com.girlkun.models.npc.Npc;
import com.girlkun.models.player.Player;
import com.girlkun.models.shop.ShopServiceNew;
import com.girlkun.services.InventoryServiceNew;
import com.girlkun.services.ItemService;
import com.girlkun.services.NpcService;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;
import java.util.ArrayList;
import java.util.List;

/**
 * Trung Thu Event
 * Sử dụng boss Thỏ Đại Ca hiện có
 *
 * @author Umi Team
 */
public class TrungThu extends Event {

    // Index menu constants
    private static final int MENU_LAM_BANH = 100;

    public static final String HUONG_DAN_CUA_THO_DAI_CA = "Trăng đã sáng rực rồi, cùng ta điểm qua các sự kiện:\n"
            + "1) SĂN KHỈ ĐÊM TRĂNG TRÒN:\b"
            + "- Trong thời gian diễn ra sự kiện, Boss Khỉ Xayda sẽ xuất hiện ngẫu nhiên ở các map: Thung lũng đen, Vách núi đen, Thành phố Vegeta.\b"
            + "- Khỉ Xayda là một Boss cực khỏe, không bị ảnh hưởng bởi các chiêu khống chế tấn công, cư dân chỉ sử dụng được các chiêu thức cơ bản để tấn công Boss.\b"
            + "- Người chơi hạ được Boss sẽ nhận được Đuôi khỉ để đổi quà hoặc sử dụng để tăng TNSM cho bản thân.\n"
            + "2) TRUY TÌM THỎ ĐẠI CA:\b"
            + "- Thỏ Đại Ca rất thích lui tới ở những nơi sầm uất, đông người như: Làng Aru, Kakarot, Mori và Siêu Thị, Đảo Kame.\b"
            + "- Đặc biệt: Thỏ Đại Ca có khả năng kháng TDHS và biến bất cứ ai chạm vào người thành củ cà rốt trong 5 phút. Khi bị biến thành củ cà rốt, sức tấn công của cư dân sẽ bị giảm 15%.\b"
            + "- Để không bị biến thành củ cà rốt, cư dân mặc các cải trang Thỏ trong sự kiện.\b"
            + "- Hạ được Thỏ Đại Ca sẽ nhận ngẫu nhiên củ cà rốt và quy đổi ở NPC Thỏ đại ca để nhận được các phần quà.\n"
            + "3) LÀM BÁNH TRUNG THU:\b"
            + "A/ Nguyên liệu\b"
            + "1.Trứng, Thịt gà: hạ gục quái ở các khu vực bản đồ\b"
            + "2.Đậu xanh, Bột mì: tham gia doanh trại độc nhãn\n"
            + "Công thức:\b"
            + "- 10 bột mì + 10 đậu xanh + 1 trứng + 20 triệu vàng = 1 bánh Trung thu 1 Trứng\b"
            + "- 15 bột mì + 12 đậu xanh + 2 trứng + 20 ngọc = 1 bánh Trung thu 2 trứng\b"
            + "- 10 bột mì + 5 đậu xanh + 1 gà quay + 20 ngọc = 1 bánh Trung thu Gà quay\b"
            + "- 20 bột mì + 10 đậu xanh + 2 trứng + 1 gà quay + 25 ngọc = 1 bánh Trung thu Thập cẩm\n"
            + "- 25 bột mì + 15 đậu xanh + 2 trứng + 1 gà quay + 40 ngọc = 1 bánh Trung thu Đặc biệt và có thêm 30% cơ hội nhận được nhận thêm bánh Trung thu Thập cẩm\b"
            + "Sưu tập đủ 5 loại bánh ta sẽ đổi cho ngươi 1 Hộp bánh Trung thu dùng để đổi quà tại NPC Trung Thu ở Đảo Kame.\n"
            + "4) RƯỚC ĐÈN TRUNG THU: Nếu ngươi có nhiều bánh Trung thu không ăn hết, ta có thể đổi cho ngươi Đèn lồng để đi rước Trung thu.\bGhé qua Shop đèn lồng của ta để biết thêm chi tiết.\n"
            + "Thôi không nói nữa, ta phải đi ăn trộm cà rốt đây ...";

    public static Npc ThoDaiCa(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (!EventManager.gI().isCurrentEvent(ConstEvent.TRUNG_THU)) {
                    createOtherMenu(player, 0,
                            "Đã làm bánh trung thu chưa?\nTrăng sắp tròn rồi!",
                            "Từ chối");
                    return;
                }
                if (canOpenNpc(player)) {
                    createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Có cà rốt không dùng thì mang cho ta đổi.\nỞ đây chỉ có hàng hiệu!",
                            "Hướng\ndẫn\nthêm",
                            "Làm\nBánh",
                            "Đổi cà\nrốt",
                            "Đổi bánh\nTrung\nthu",
                            "Đổi\nHộp\nbánh",
                            "Đổi\nđuôi\nkhỉ",
                            "Từ chối");
                }
            }

            @Override
            public void confirmMenu(Player pl, int select) {
                if (!EventManager.gI().isCurrentEvent(ConstEvent.TRUNG_THU))
                    return;
                if (canOpenNpc(pl)) {
                    if (pl.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0: // Hướng dẫn
                                NpcService.gI().createTutorial(pl, this.avartar, HUONG_DAN_CUA_THO_DAI_CA);
                                break;
                            case 1: // Làm bánh
                                String menuText = "Chọn loại bánh muốn làm:\n"
                                        + "1. Bánh 1 Trứng " + (checkBanh1Trung(pl) ? "[Đủ]" : "[Thiếu]") + "\n"
                                        + "2. Bánh 2 Trứng " + (checkBanh2Trung(pl) ? "[Đủ]" : "[Thiếu]") + "\n"
                                        + "3. Bánh Gà quay " + (checkBanhGaQuay(pl) ? "[Đủ]" : "[Thiếu]") + "\n"
                                        + "4. Bánh Thập cẩm " + (checkBanhThapCam(pl) ? "[Đủ]" : "[Thiếu]") + "\n"
                                        + "5. Bánh Đặc biệt " + (checkBanhDacBiet(pl) ? "[Đủ]" : "[Thiếu]");
                                createOtherMenu(pl, MENU_LAM_BANH,
                                        menuText,
                                        "Bánh\n1 trứng",
                                        "Bánh\n2 trứng",
                                        "Bánh\nGà quay",
                                        "Bánh\nThập cẩm",
                                        "Bánh\nĐặc biệt",
                                        "Quay\nlại");
                                break;
                            case 2: // Đổi cà rốt
                                ShopServiceNew.gI().opendShop(pl, "THO_DAI_CA", false);
                                break;
                            case 3: // Đổi bánh Trung thu (lấy lồng đèn)
                                ShopServiceNew.gI().opendShop(pl, "THO_DAI_CA_BANHTRUNGTHU", false);
                                break;
                            case 4: // Đổi Hộp bánh (5 bánh -> 1 hộp)
                                doiHopBanh(pl);
                                break;
                            case 5: // Đổi đuôi khỉ (10 đuôi -> cải trang)
                                doiDuoiKhi(pl);
                                break;
                        }
                    } else if (pl.iDMark.getIndexMenu() == MENU_LAM_BANH) {
                        switch (select) {
                            case 0: // Bánh 1 trứng
                                lamBanh1Trung(pl);
                                break;
                            case 1: // Bánh 2 trứng
                                lamBanh2Trung(pl);
                                break;
                            case 2: // Bánh Gà quay
                                lamBanhGaQuay(pl);
                                break;
                            case 3: // Bánh Thập cẩm
                                lamBanhThapCam(pl);
                                break;
                            case 4: // Bánh Đặc biệt
                                lamBanhDacBiet(pl);
                                break;
                            case 5: // Quay lại
                                openBaseMenu(pl);
                                break;
                        }
                    }
                }
            }

            // === CHECK NGUYÊN LIỆU METHODS ===

            private boolean checkBanh1Trung(Player pl) {
                Item botMi = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_BOT_MI);
                Item dauXanh = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_DAU_XANH);
                Item trung = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_TRUNGVIT);
                return botMi != null && botMi.quantity >= 10
                        && dauXanh != null && dauXanh.quantity >= 10
                        && trung != null && trung.quantity >= 1
                        && pl.inventory.gold >= 20000000;
            }

            private boolean checkBanh2Trung(Player pl) {
                Item botMi = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_BOT_MI);
                Item dauXanh = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_DAU_XANH);
                Item trung = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_TRUNGVIT);
                return botMi != null && botMi.quantity >= 15
                        && dauXanh != null && dauXanh.quantity >= 12
                        && trung != null && trung.quantity >= 2
                        && pl.inventory.gem >= 20;
            }

            private boolean checkBanhGaQuay(Player pl) {
                Item botMi = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_BOT_MI);
                Item dauXanh = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_DAU_XANH);
                Item gaQuay = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_GAQUAYNGUYENCON);
                return botMi != null && botMi.quantity >= 10
                        && dauXanh != null && dauXanh.quantity >= 5
                        && gaQuay != null && gaQuay.quantity >= 1
                        && pl.inventory.gem >= 20;
            }

            private boolean checkBanhThapCam(Player pl) {
                Item botMi = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_BOT_MI);
                Item dauXanh = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_DAU_XANH);
                Item trung = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_TRUNGVIT);
                Item gaQuay = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_GAQUAYNGUYENCON);
                return botMi != null && botMi.quantity >= 20
                        && dauXanh != null && dauXanh.quantity >= 10
                        && trung != null && trung.quantity >= 2
                        && gaQuay != null && gaQuay.quantity >= 1
                        && pl.inventory.gem >= 25;
            }

            private boolean checkBanhDacBiet(Player pl) {
                Item botMi = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_BOT_MI);
                Item dauXanh = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_DAU_XANH);
                Item trung = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_TRUNGVIT);
                Item gaQuay = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_GAQUAYNGUYENCON);
                return botMi != null && botMi.quantity >= 25
                        && dauXanh != null && dauXanh.quantity >= 15
                        && trung != null && trung.quantity >= 2
                        && gaQuay != null && gaQuay.quantity >= 1
                        && pl.inventory.gem >= 40;
            }

            // === LÀM BÁNH METHODS ===

            // Bánh 1 Trứng: 10 bột mì + 10 đậu xanh + 1 trứng + 20M vàng
            private void lamBanh1Trung(Player pl) {
                Item botMi = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_BOT_MI);
                Item dauXanh = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_DAU_XANH);
                Item trung = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_TRUNGVIT);

                if (botMi == null || botMi.quantity < 10) {
                    npcChat(pl, "Cần 10 Bột mì!");
                    return;
                }
                if (dauXanh == null || dauXanh.quantity < 10) {
                    npcChat(pl, "Cần 10 Đậu xanh!");
                    return;
                }
                if (trung == null || trung.quantity < 1) {
                    npcChat(pl, "Cần 1 Trứng vịt!");
                    return;
                }
                if (pl.inventory.gold < 20000000) {
                    npcChat(pl, "Cần 20 triệu vàng!");
                    return;
                }
                if (InventoryServiceNew.gI().getCountEmptyBag(pl) < 1) {
                    npcChat(pl, "Hành trang đã đầy!");
                    return;
                }

                // Trừ nguyên liệu
                InventoryServiceNew.gI().subQuantityItemsBag(pl, botMi, 10);
                InventoryServiceNew.gI().subQuantityItemsBag(pl, dauXanh, 10);
                InventoryServiceNew.gI().subQuantityItemsBag(pl, trung, 1);
                pl.inventory.gold -= 20000000;

                // Thêm bánh
                Item banh = ItemService.gI().createNewItem((short) ConstItem.TRUNGTHU_BANHTRUNGTHU_1TRUNG);
                InventoryServiceNew.gI().addItemBag(pl, banh);
                InventoryServiceNew.gI().sendItemBags(pl);
                Service.gI().sendMoney(pl);
                Service.gI().sendThongBao(pl, "Làm thành công Bánh Trung thu 1 Trứng!");
            }

            // Bánh 2 Trứng: 15 bột mì + 12 đậu xanh + 2 trứng + 20 ngọc
            private void lamBanh2Trung(Player pl) {
                Item botMi = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_BOT_MI);
                Item dauXanh = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_DAU_XANH);
                Item trung = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_TRUNGVIT);

                if (botMi == null || botMi.quantity < 15) {
                    npcChat(pl, "Cần 15 Bột mì!");
                    return;
                }
                if (dauXanh == null || dauXanh.quantity < 12) {
                    npcChat(pl, "Cần 12 Đậu xanh!");
                    return;
                }
                if (trung == null || trung.quantity < 2) {
                    npcChat(pl, "Cần 2 Trứng vịt!");
                    return;
                }
                if (pl.inventory.gem < 20) {
                    npcChat(pl, "Cần 20 ngọc!");
                    return;
                }
                if (InventoryServiceNew.gI().getCountEmptyBag(pl) < 1) {
                    npcChat(pl, "Hành trang đã đầy!");
                    return;
                }

                InventoryServiceNew.gI().subQuantityItemsBag(pl, botMi, 15);
                InventoryServiceNew.gI().subQuantityItemsBag(pl, dauXanh, 12);
                InventoryServiceNew.gI().subQuantityItemsBag(pl, trung, 2);
                pl.inventory.gem -= 20;

                Item banh = ItemService.gI().createNewItem((short) ConstItem.TRUNGTHU_BANHTRUNGTHU_2TRUNG);
                InventoryServiceNew.gI().addItemBag(pl, banh);
                InventoryServiceNew.gI().sendItemBags(pl);
                Service.gI().sendMoney(pl);
                Service.gI().sendThongBao(pl, "Làm thành công Bánh Trung thu 2 Trứng!");
            }

            // Bánh Gà quay: 10 bột mì + 5 đậu xanh + 1 gà quay + 20 ngọc
            private void lamBanhGaQuay(Player pl) {
                Item botMi = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_BOT_MI);
                Item dauXanh = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_DAU_XANH);
                Item gaQuay = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_GAQUAYNGUYENCON);

                if (botMi == null || botMi.quantity < 10) {
                    npcChat(pl, "Cần 10 Bột mì!");
                    return;
                }
                if (dauXanh == null || dauXanh.quantity < 5) {
                    npcChat(pl, "Cần 5 Đậu xanh!");
                    return;
                }
                if (gaQuay == null || gaQuay.quantity < 1) {
                    npcChat(pl, "Cần 1 Gà quay nguyên con!");
                    return;
                }
                if (pl.inventory.gem < 20) {
                    npcChat(pl, "Cần 20 ngọc!");
                    return;
                }
                if (InventoryServiceNew.gI().getCountEmptyBag(pl) < 1) {
                    npcChat(pl, "Hành trang đã đầy!");
                    return;
                }

                InventoryServiceNew.gI().subQuantityItemsBag(pl, botMi, 10);
                InventoryServiceNew.gI().subQuantityItemsBag(pl, dauXanh, 5);
                InventoryServiceNew.gI().subQuantityItemsBag(pl, gaQuay, 1);
                pl.inventory.gem -= 20;

                Item banh = ItemService.gI().createNewItem((short) ConstItem.TRUNGTHU_BANHTRUNGTHU_GAQUAY);
                InventoryServiceNew.gI().addItemBag(pl, banh);
                InventoryServiceNew.gI().sendItemBags(pl);
                Service.gI().sendMoney(pl);
                Service.gI().sendThongBao(pl, "Làm thành công Bánh Trung thu Gà quay!");
            }

            // Bánh Thập cẩm: 20 bột mì + 10 đậu xanh + 2 trứng + 1 gà quay + 25 ngọc
            private void lamBanhThapCam(Player pl) {
                Item botMi = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_BOT_MI);
                Item dauXanh = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_DAU_XANH);
                Item trung = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_TRUNGVIT);
                Item gaQuay = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_GAQUAYNGUYENCON);

                if (botMi == null || botMi.quantity < 20) {
                    npcChat(pl, "Cần 20 Bột mì!");
                    return;
                }
                if (dauXanh == null || dauXanh.quantity < 10) {
                    npcChat(pl, "Cần 10 Đậu xanh!");
                    return;
                }
                if (trung == null || trung.quantity < 2) {
                    npcChat(pl, "Cần 2 Trứng vịt!");
                    return;
                }
                if (gaQuay == null || gaQuay.quantity < 1) {
                    npcChat(pl, "Cần 1 Gà quay nguyên con!");
                    return;
                }
                if (pl.inventory.gem < 25) {
                    npcChat(pl, "Cần 25 ngọc!");
                    return;
                }
                if (InventoryServiceNew.gI().getCountEmptyBag(pl) < 1) {
                    npcChat(pl, "Hành trang đã đầy!");
                    return;
                }

                InventoryServiceNew.gI().subQuantityItemsBag(pl, botMi, 20);
                InventoryServiceNew.gI().subQuantityItemsBag(pl, dauXanh, 10);
                InventoryServiceNew.gI().subQuantityItemsBag(pl, trung, 2);
                InventoryServiceNew.gI().subQuantityItemsBag(pl, gaQuay, 1);
                pl.inventory.gem -= 25;

                Item banh = ItemService.gI().createNewItem((short) ConstItem.TRUNGTHU_BANHTRUNGTHU_THAPCAM);
                InventoryServiceNew.gI().addItemBag(pl, banh);
                InventoryServiceNew.gI().sendItemBags(pl);
                Service.gI().sendMoney(pl);
                Service.gI().sendThongBao(pl, "Làm thành công Bánh Trung thu Thập cẩm!");
            }

            // Bánh Đặc biệt: 25 bột mì + 15 đậu xanh + 2 trứng + 1 gà quay + 40 ngọc + 30%
            // thêm Thập cẩm
            private void lamBanhDacBiet(Player pl) {
                Item botMi = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_BOT_MI);
                Item dauXanh = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_DAU_XANH);
                Item trung = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_TRUNGVIT);
                Item gaQuay = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_GAQUAYNGUYENCON);

                if (botMi == null || botMi.quantity < 25) {
                    npcChat(pl, "Cần 25 Bột mì!");
                    return;
                }
                if (dauXanh == null || dauXanh.quantity < 15) {
                    npcChat(pl, "Cần 15 Đậu xanh!");
                    return;
                }
                if (trung == null || trung.quantity < 2) {
                    npcChat(pl, "Cần 2 Trứng vịt!");
                    return;
                }
                if (gaQuay == null || gaQuay.quantity < 1) {
                    npcChat(pl, "Cần 1 Gà quay nguyên con!");
                    return;
                }
                if (pl.inventory.gem < 40) {
                    npcChat(pl, "Cần 40 ngọc!");
                    return;
                }
                int requiredSlots = Util.isTrue(30, 100) ? 2 : 1; // 30% bonus
                if (InventoryServiceNew.gI().getCountEmptyBag(pl) < requiredSlots) {
                    npcChat(pl, "Hành trang cần ít nhất " + requiredSlots + " ô trống!");
                    return;
                }

                InventoryServiceNew.gI().subQuantityItemsBag(pl, botMi, 25);
                InventoryServiceNew.gI().subQuantityItemsBag(pl, dauXanh, 15);
                InventoryServiceNew.gI().subQuantityItemsBag(pl, trung, 2);
                InventoryServiceNew.gI().subQuantityItemsBag(pl, gaQuay, 1);
                pl.inventory.gem -= 40;

                Item banh = ItemService.gI().createNewItem((short) ConstItem.TRUNGTHU_BANHTRUNGTHU_DACBIET);
                InventoryServiceNew.gI().addItemBag(pl, banh);

                // 30% thêm bánh Thập cẩm
                if (Util.isTrue(30, 100)) {
                    Item bonus = ItemService.gI().createNewItem((short) ConstItem.TRUNGTHU_BANHTRUNGTHU_THAPCAM);
                    InventoryServiceNew.gI().addItemBag(pl, bonus);
                    Service.gI().sendThongBao(pl, "May mắn! Nhận thêm Bánh Thập cẩm!");
                }

                InventoryServiceNew.gI().sendItemBags(pl);
                Service.gI().sendMoney(pl);
                Service.gI().sendThongBao(pl, "Làm thành công Bánh Trung thu Đặc biệt!");
            }

            // Đổi 5 loại bánh thành 1 Hộp bánh Trung thu
            private void doiHopBanh(Player pl) {
                Item banh1Trung = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_BANHTRUNGTHU_1TRUNG);
                Item banh2Trung = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_BANHTRUNGTHU_2TRUNG);
                Item banhGaQuay = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_BANHTRUNGTHU_GAQUAY);
                Item banhThapCam = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_BANHTRUNGTHU_THAPCAM);
                Item banhDacBiet = InventoryServiceNew.gI().findItemBag(pl, ConstItem.TRUNGTHU_BANHTRUNGTHU_DACBIET);

                if (banh1Trung == null || banh1Trung.quantity < 1) {
                    npcChat(pl, "Cần 1 Bánh 1 Trứng!");
                    return;
                }
                if (banh2Trung == null || banh2Trung.quantity < 1) {
                    npcChat(pl, "Cần 1 Bánh 2 Trứng!");
                    return;
                }
                if (banhGaQuay == null || banhGaQuay.quantity < 1) {
                    npcChat(pl, "Cần 1 Bánh Gà quay!");
                    return;
                }
                if (banhThapCam == null || banhThapCam.quantity < 1) {
                    npcChat(pl, "Cần 1 Bánh Thập cẩm!");
                    return;
                }
                if (banhDacBiet == null || banhDacBiet.quantity < 1) {
                    npcChat(pl, "Cần 1 Bánh Đặc biệt!");
                    return;
                }
                if (InventoryServiceNew.gI().getCountEmptyBag(pl) < 1) {
                    npcChat(pl, "Hành trang đã đầy!");
                    return;
                }

                // Trừ 5 bánh
                InventoryServiceNew.gI().subQuantityItemsBag(pl, banh1Trung, 1);
                InventoryServiceNew.gI().subQuantityItemsBag(pl, banh2Trung, 1);
                InventoryServiceNew.gI().subQuantityItemsBag(pl, banhGaQuay, 1);
                InventoryServiceNew.gI().subQuantityItemsBag(pl, banhThapCam, 1);
                InventoryServiceNew.gI().subQuantityItemsBag(pl, banhDacBiet, 1);

                // Thêm hộp bánh
                Item hopBanh = ItemService.gI().createNewItem((short) ConstItem.TRUNGTHU_BANHTRUNGTHU_HOPBANHTRUNGTHU);
                InventoryServiceNew.gI().addItemBag(pl, hopBanh);
                InventoryServiceNew.gI().sendItemBags(pl);
                Service.gI().sendThongBao(pl, "Đổi thành công 1 Hộp bánh Trung thu!");
            }

            // Đổi 10 Đuôi khỉ trung thu -> Cải trang Khỉ black
            private void doiDuoiKhi(Player pl) {
                int DUOI_KHI_ID = 2158;
                int CAI_TRANG_KHI_BLACK_ID = 1111;

                Item duoiKhi = InventoryServiceNew.gI().findItemBag(pl, DUOI_KHI_ID);

                if (duoiKhi == null || duoiKhi.quantity < 10) {
                    npcChat(pl, "Cần 10 Đuôi khỉ trung thu!");
                    return;
                }
                if (InventoryServiceNew.gI().getCountEmptyBag(pl) < 1) {
                    npcChat(pl, "Hành trang đã đầy!");
                    return;
                }

                // Trừ 10 đuôi khỉ
                InventoryServiceNew.gI().subQuantityItemsBag(pl, duoiKhi, 10);

                // Tạo cải trang với options random
                Item caiTrang = ItemService.gI().createNewItem((short) CAI_TRANG_KHI_BLACK_ID);
                caiTrang.itemOptions.add(new Item.ItemOption(77, Util.nextInt(15, 20))); // X: [15;20]
                caiTrang.itemOptions.add(new Item.ItemOption(103, Util.nextInt(15, 20))); // Y: [15;20]
                caiTrang.itemOptions.add(new Item.ItemOption(50, Util.nextInt(7, 12))); // Z: [7;12]
                caiTrang.itemOptions.add(new Item.ItemOption(101, Util.nextInt(15, 25))); // W: [15;25]
                caiTrang.itemOptions.add(new Item.ItemOption(93, Util.nextInt(7, 14))); // T: [7;14] (hạn sử dụng)

                InventoryServiceNew.gI().addItemBag(pl, caiTrang);
                InventoryServiceNew.gI().sendItemBags(pl);
                Service.gI().sendThongBao(pl, "Đổi thành công Cải trang Khỉ Black!");
            }
        };
    }

    // NPC Trung Thu ở Đảo Kame - đổi Hộp bánh lấy thú cưỡi
    public static Npc NpcTrungThu(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (!EventManager.gI().isCurrentEvent(ConstEvent.TRUNG_THU)) {
                    createOtherMenu(player, 0,
                            "Trăng thu chưa tròn...\nHãy quay lại khi trăng sáng nhé!",
                            "Từ chối");
                    return;
                }
                if (canOpenNpc(player)) {
                    createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Chào mừng đến lễ hội Trung Thu!\nMang Hộp bánh đến đây đổi quà đi!",
                            "Đổi\nquà",
                            "Hướng\ndẫn",
                            "Từ chối");
                }
            }

            @Override
            public void confirmMenu(Player pl, int select) {
                if (!EventManager.gI().isCurrentEvent(ConstEvent.TRUNG_THU))
                    return;
                if (canOpenNpc(pl)) {
                    switch (select) {
                        case 0: // Đổi quà
                            ShopServiceNew.gI().opendShop(pl, "TRUNGTHU_SHOP", false);
                            break;
                        case 1: // Hướng dẫn
                            Service.gI().sendPopUpMultiLine(pl, tempId, avartar,
                                    "=== HƯỚNG DẪN ĐỔI QUÀ ===\n"
                                            + "Mang Hộp bánh Trung thu đến đây để đổi!\b"
                                            + "Phần thưởng:\b"
                                            + "- 2 Hộp bánh = Cân đẩu vân ngũ sắc\b"
                                            + "- 3 Hộp bánh = X99 Capsule Trung Thu\b"
                                            + "- 5 Hộp bánh = Thú cưỡi Ngọc Thỏ\b"
                                            + "- 10 Hộp bánh = PET Khỉ Bong Bóng\n"
                                            + "Capsule Trung Thu chứa nhiều vật phẩm kì bí có thể nhận được cải trang và đồ Trung Thu vĩnh viễn\n"
                                            + "Hộp bánh được đổi từ 5 loại bánh tại NPC Thỏ Đại Ca:\b"
                                            + "1 Trứng + 2 Trứng + Gà quay + Thập cẩm + Đặc biệt\n"
                                            + "Chúc bạn có mùa Trung Thu vui vẻ!");
                            break;
                    }
                }
            }
        };
    }

    @Override
    public String getThongBaoLogin() {
        return "Chào ae tới server NroSkibidi. Nạp X2 liên hệ admin.";
    }

    @Override
    public void npc() {
        createNpc(ConstMap.VACH_NUI_ARU, ConstNpc.THO_DAI_CA, 111, 288);
        createNpc(ConstMap.DAO_KAME, ConstNpc.TRUNG_THU, 367, 288);
        super.npc();
    }

    @Override
    public List<ItemMap> itemMap(Map map, int MobId, Zone zone, int x, int y, long playerId) {
        List<ItemMap> items = new ArrayList<>();
        int rating = 30;

        // Đậu xanh, bột mì thì phải đi doanh trại
        if (map.type == ConstMap.MAP_DOANH_TRAI) {
            if (Util.isTrue(rating, 100)) {
                items.add(new ItemMap(zone, ConstItem.TRUNGTHU_BOT_MI, 1, x, y, playerId));
            }
            if (Util.isTrue(rating, 100)) {
                items.add(new ItemMap(zone, ConstItem.TRUNGTHU_DAU_XANH, 1, x, y, playerId));
            }
        }
        else{
            // Trứng, thịt gà all map
            if (Util.isTrue(rating, 100)) {
                items.add(new ItemMap(zone, ConstItem.TRUNGTHU_TRUNGVIT, 1, x, y, playerId));
            }
            if (Util.isTrue(rating, 100)) {
                items.add(new ItemMap(zone, ConstItem.TRUNGTHU_GAQUAYNGUYENCON, 1, x, y, playerId));
            }
        }

        return items;
    }

    @Override
    public void boss() {
        // Spawn 10 boss Thỏ Đại Ca
        createBoss(BossID.THO_DAI_CA, 10);
        // Spawn 10 boss Khỉ xayda
        createBoss(BossID.KHI_XAYDA, 10);
    }

    @Override
    protected String getNameEvent() {
        return "Trung thu";
    }

    @Override
    public int getHeSoTnSm() {
        return 3;
    }

    @Override
    protected ConstEvent GetEventType() {
        return ConstEvent.TRUNG_THU;
    }
}
