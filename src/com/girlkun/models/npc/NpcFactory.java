package com.girlkun.models.npc;

import com.girlkun.models.event.events.*;

import com.girlkun.services.NpcService;
import com.girlkun.services.Service;
import com.girlkun.services.ClanService;
import com.girlkun.services.InventoryServiceNew;
import com.girlkun.services.MapService;
import com.girlkun.services.ItemMapService;
import com.girlkun.services.NgocRongNamecService;
import com.girlkun.services.PetService;
import com.girlkun.services.FriendAndEnemyService;
import com.girlkun.services.TaskService;
import com.girlkun.services.OpenPowerService;
import com.girlkun.services.IntrinsicService;
import com.girlkun.services.PlayerService;
import com.girlkun.services.ItemService;
import com.arriety.MaQuaTang.MaQuaTangManager;
import com.girlkun.network.io.Message;
import com.girlkun.models.mob.Mob;
import com.girlkun.services.*;
import com.girlkun.models.boss.BossManager;
import com.girlkun.models.boss.list_boss.NhanBan;
import com.girlkun.consts.ConstMap;
import com.girlkun.models.map.bando.BanDoKhoBau;
import com.girlkun.models.map.bando.BanDoKhoBauService;
import com.girlkun.services.ChatGlobalService;
import com.girlkun.models.map.challenge.MartialCongressService;
import com.girlkun.consts.ConstNpc;
import com.girlkun.consts.ConstPlayer;
import com.girlkun.consts.ConstTask;
import com.girlkun.jdbc.daos.PlayerDAO;
import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossData;
import com.girlkun.models.boss.BossType;
import com.girlkun.models.clan.Clan;
import com.girlkun.models.clan.ClanMember;

import java.util.HashMap;
import java.util.List;

import com.girlkun.services.func.ChangeMapService;
import com.girlkun.services.func.SummonDragon;
import static com.girlkun.services.func.SummonDragon.SHENRON_1_STAR_WISHES_1;
import static com.girlkun.services.func.SummonDragon.SHENRON_1_STAR_WISHES_2;
import static com.girlkun.services.func.SummonDragon.SHENRON_SAY;
import com.girlkun.models.player.Player;
import com.girlkun.models.item.Item;
import com.girlkun.models.item.Item.ItemOption;
import com.girlkun.models.map.Map;
import com.girlkun.models.map.Zone;
import com.girlkun.models.map.blackball.BlackBallWar;
import com.girlkun.models.map.MapMaBu.MapMaBu;
import com.girlkun.models.map.doanhtrai.DoanhTrai;
import com.girlkun.models.map.doanhtrai.DoanhTraiService;
import com.girlkun.models.player.Inventory;
import com.girlkun.models.player.NPoint;
import com.girlkun.models.matches.PVPService;
import com.girlkun.models.shop.ShopServiceNew;
import com.girlkun.models.skill.Skill;
import com.girlkun.models.skill.NClass;
import com.girlkun.models.Template.SkillTemplate;
import com.girlkun.utils.SkillUtil;
import com.girlkun.server.Client;
import com.girlkun.server.Maintenance;
import com.girlkun.server.Manager;
import com.girlkun.services.func.*;
import com.girlkun.services.func.Input;
import com.girlkun.services.func.LuckyRound;
import com.girlkun.services.func.TopService;
import com.girlkun.utils.Logger;
import com.girlkun.utils.TimeUtil;
import com.girlkun.models.map.gas.Gas;
import com.girlkun.models.map.gas.GasService;
import com.girlkun.utils.Util;
import java.util.ArrayList;
import static com.girlkun.services.func.CombineServiceNew.CHE_TAO_TRANG_BI_TS;
import com.arriety.kygui.ItemKyGui;
import com.arriety.kygui.ShopKyGuiService;
import com.arriety.kygui.ShopKyGuiManager;
import com.girlkun.models.event.ConstEvent;
import com.girlkun.models.Template.NpcTemplate;
import com.girlkun.models.event.EventManager;
import com.girlkun.models.event.events.Halloween;
import com.girlkun.models.event.events.TrungThu;
import static com.girlkun.services.func.CombineServiceNew.DAP_SET_KICH_HOAT;
import static com.girlkun.services.func.CombineServiceNew.NANG_CAP_SKH_VIP;
import com.girlkun.models.pariry.PariryServices;
import com.girlkun.models.pariry.pariryManager;
//import com.girlkun.models.player.Archivement;
import java.io.DataOutputStream;
import java.util.Random;
import java.util.logging.Level;
import java.io.IOException;

import java.util.logging.Level;

public class NpcFactory {

    private static final int COST_HD = 50000000;

    public static int timebahatmit;

    private static boolean nhanVang = false;
    private static boolean nhanDeTu = false;

    // playerid - object
    public static final java.util.Map<Long, Object> PLAYERID_OBJECT = new HashMap<Long, Object>();

    // private static Npc npcminuong(int mapId, int status, int cx, int cy, int
    // tempId, int avatar) {
    // throw new UnsupportedOperationException("Not supported yet."); // Generated
    // from
    // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    // }
    private NpcFactory() {

    }

    public static Npc chanmenh(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 211) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "|7|Hồn Hoàn"
                                        + "\n\n|1|Đến Tinh Đấu đại sâm lâm săn Hồn Thú"
                                        + "\n\n|5|Săn Hồn Thú Rôi Hấp Thu Hồn Hoàn"
                                        + "\n|3|Có Thể Tăng Cấp Hồn Hoàn",
                                "Nâng cấp Hồn Hoàn", "Tinh Đấu đại sâm lâm");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 211) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_CHAN_MENH);
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 212, -1, 144);
                                    break; // qua lanh dia
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_START_COMBINE) {
                            switch (player.combineNew.typeCombine) {
                                case CombineServiceNew.NANG_CAP_CHAN_MENH:
                                    if (select == 0) {
                                        CombineServiceNew.gI().startCombine(player);
                                    }
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc gapthu(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        this.createOtherMenu(player, 1234, "|0|- •⊹٭DragonBall Umi⊹• -\n" + "|2|MÁY GẮP TRỨNG\n"
                                + "|1|GẮP X1 : 500 Hồng Ngoc\nGẮP X10 : 5k Hồng Ngoc\nGẮP X100 : 50k Hồng Ngoc\n"
                                + "|7|LƯU Ý : Nếu hành trang đầy sẽ chuyển trứng về ruơng phụ\nNẾU MUỐN NGƯNG AUTO GẤP CHỈ CẦN THOÁT GAME VÀ VÀO LẠI!",
                                "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        if (player.iDMark.getIndexMenu() == 1234) {
                            switch (select) {
                                case 0:
                                    if (player.inventory.ruby < 500) {
                                        Service.gI().sendThongBao(player, "không đủ 500 Hồng Ngoc");
                                        return;
                                    }
                                    if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
                                        Service.gI().sendThongBao(player, "Hết chỗ trống rồi");
                                        return;
                                    }
                                    player.inventory.ruby -= 500;
                                    Service.gI().sendMoney(player);
                                    Item gapt = Util.petrandom(Util.nextInt(2020, 2022));
                                    if (Util.isTrue(40, 100)) {
                                        InventoryServiceNew.gI().addItemBag(player, gapt);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.createOtherMenu(player, 1234,
                                                "|2|Bạn vừa gắp được : " + gapt.template.name
                                                        + "\nSố Hồng Ngọc Trừ : 500" + "\n|7|Chiến tiếp ngay!",
                                                "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                    } else {
                                        this.createOtherMenu(player, 1234,
                                                "|6|Gắp hụt rồi, bạn bỏ cuộc sao?" + "\nSố Hồng Ngọc Trừ : 500"
                                                        + "\n|7|Chiến tiếp ngay!",
                                                "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                    }
                                    break;
                                case 1:
                                    if (player.inventory.ruby < 5000) {
                                        Service.gI().sendThongBao(player, "không đủ 5000 Hồng Ngoc");
                                        return;
                                    }
                                    try {
                                        Service.gI().sendThongBao(player, "Tiến hành auto gắp x10 lần");
                                        int timex10 = 10;
                                        int hn = 0;
                                        while (timex10 > 0) {
                                            timex10--;
                                            hn += 500;
                                            Thread.sleep(100);
                                            if (1 + player.inventory.itemsBoxCrackBall.size() > 100) {
                                                this.createOtherMenu(player, 12345,
                                                        "|7|DỪNG AUTO GẮP, RƯƠNG PHỤ ĐÃ ĐẦY!\n" + "|2|TỔNG LƯỢT GẮP : "
                                                                + (10 - timex10) + " LƯỢT"
                                                                + "\n|7|VUI LÒNG LÀM TRỐNG RƯƠNG PHỤ!",
                                                        "Đóng");
                                                break;
                                            }
                                            player.inventory.ruby -= 500;
                                            Service.gI().sendMoney(player);
                                            Item gapx10 = Util.petrandom(Util.nextInt(2020, 2022));
                                            if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                                                if (Util.isTrue(40, 100)) {
                                                    InventoryServiceNew.gI().addItemBag(player, gapx10);
                                                    InventoryServiceNew.gI().sendItemBags(player);
                                                    this.createOtherMenu(player, 12345,
                                                            "|7|ĐANG TIẾN HÀNH GẮP AUTO X10\nSỐ LƯỢT CÒN : " + timex10
                                                                    + " LƯỢT\n" + "|2|Đã gắp được : "
                                                                    + gapx10.template.name + "\nSố hồng ngọc đã trừ : "
                                                                    + hn + "\n"
                                                                    + "\nNẾU HÀNH TRANG ĐẦY, ITEM SẼ ĐƯỢC THÊM VÀO RƯƠNG PHỤ",
                                                            "Đóng");
                                                } else {
                                                    this.createOtherMenu(player, 12345,
                                                            "|7|ĐANG TIẾN HÀNH GẮP AUTO X10\nSỐ LƯỢT CÒN : " + timex10
                                                                    + " LƯỢT\n" + "|2|Gắp hụt rồi!"
                                                                    + "\nSố hồng ngọc đã trừ : " + hn + "\n"
                                                                    + "\nNẾU HÀNH TRANG ĐẦY, ITEM SẼ ĐƯỢC THÊM VÀO RƯƠNG PHỤ",
                                                            "Đóng");
                                                }
                                            }
                                            if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
                                                if (Util.isTrue(10, 100)) {
                                                    player.inventory.itemsBoxCrackBall.add(gapx10);
                                                    this.createOtherMenu(player, 12345,
                                                            "|7|HÀNH TRANG ĐÃ ĐẦY\nĐANG TIẾN HÀNH GẮP AUTO X10 VÀO RƯƠNG PHỤ\nSỐ LƯỢT CÒN : "
                                                                    + timex10 + " LƯỢT\n" + "|2|Đã gắp được : "
                                                                    + gapx10.template.name + "\nSố hồng ngọc đã trừ : "
                                                                    + hn + "\n",
                                                            "Đóng");
                                                } else {
                                                    this.createOtherMenu(player, 12345,
                                                            "|7|HÀNH TRANG ĐÃ ĐẦY\nĐANG TIẾN HÀNH GẮP AUTO X10 VÀO RƯƠNG PHỤ\nSỐ LƯỢT CÒN : "
                                                                    + timex10 + " LƯỢT\n" + "|2|Gắp hụt rồi!"
                                                                    + "\nSố hồng ngọc đã trừ : " + hn + "\n : ",
                                                            "Đóng");
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                    }
                                    break;
                                case 2:
                                    if (player.inventory.ruby < 50000) {
                                        Service.gI().sendThongBao(player, "không đủ 50000 Hồng Ngoc");
                                        return;
                                    }
                                    try {
                                        Service.gI().sendThongBao(player, "Tiến hành auto gắp x100 lần");
                                        int timex100 = 100;
                                        int hn = 0;
                                        while (timex100 > 0) {
                                            timex100--;
                                            hn += 500;
                                            Thread.sleep(100);
                                            if (1 + player.inventory.itemsBoxCrackBall.size() > 100) {
                                                this.createOtherMenu(player, 12345,
                                                        "|7|DỪNG AUTO GẮP, RƯƠNG PHỤ ĐÃ ĐẦY!\n" + "|2|TỔNG LƯỢT GẮP : "
                                                                + (10 - timex100) + " LƯỢT"
                                                                + "\n|7|VUI LÒNG LÀM TRỐNG RƯƠNG PHỤ!",
                                                        "Đóng");
                                                break;
                                            }
                                            player.inventory.ruby -= 500;
                                            Service.gI().sendMoney(player);
                                            Item gapx100 = Util.petrandom(Util.nextInt(2020, 2022));
                                            if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                                                if (Util.isTrue(10, 100)) {
                                                    InventoryServiceNew.gI().addItemBag(player, gapx100);
                                                    InventoryServiceNew.gI().sendItemBags(player);
                                                    this.createOtherMenu(player, 12345,
                                                            "|7|ĐANG TIẾN HÀNH GẮP AUTO X100\nSỐ LƯỢT CÒN : " + timex100
                                                                    + " LƯỢT\n" + "|2|Đã gắp được : "
                                                                    + gapx100.template.name + "\nSố hồng ngọc đã trừ : "
                                                                    + hn + "\n"
                                                                    + "\nNẾU HÀNH TRANG ĐẦY, ITEM SẼ ĐƯỢC THÊM VÀO RƯƠNG PHỤ",
                                                            "Đóng");
                                                } else {
                                                    this.createOtherMenu(player, 12345,
                                                            "|7|ĐANG TIẾN HÀNH GẮP AUTO X100\nSỐ LƯỢT CÒN : " + timex100
                                                                    + " LƯỢT\n" + "|2|Gắp hụt rồi!"
                                                                    + "\nSố hồng ngọc đã trừ : " + hn + "\n"
                                                                    + "\nNẾU HÀNH TRANG ĐẦY, ITEM SẼ ĐƯỢC THÊM VÀO RƯƠNG PHỤ",
                                                            "Đóng");
                                                }
                                            }
                                            if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
                                                if (Util.isTrue(10, 100)) {
                                                    player.inventory.itemsBoxCrackBall.add(gapx100);
                                                    this.createOtherMenu(player, 12345,
                                                            "|7|HÀNH TRANG ĐÃ ĐẦY\nĐANG TIẾN HÀNH GẮP AUTO X100 VÀO RƯƠNG PHỤ\nSỐ LƯỢT CÒN : "
                                                                    + timex100 + " LƯỢT\n" + "|2|Đã gắp được : "
                                                                    + gapx100.template.name + "\nSố hồng ngọc đã trừ : "
                                                                    + hn + "\n",
                                                            "Đóng");
                                                } else {
                                                    this.createOtherMenu(player, 12345,
                                                            "|7|HÀNH TRANG ĐÃ ĐẦY\nĐANG TIẾN HÀNH GẮP AUTO X100 VÀO RƯƠNG PHỤ\nSỐ LƯỢT CÒN : "
                                                                    + timex100 + " LƯỢT\n" + "|2|Gắp hụt rồi!"
                                                                    + "\nSố hồng ngọc đã trừ : " + hn + "\n",
                                                            "Đóng");
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                    }
                                    break;
                                case 3:
                                    this.createOtherMenu(player, ConstNpc.RUONG_PHU,
                                            "|1|Tình yêu như một dây đàn\n"
                                                    + "Tình vừa được thì đàn đứt dây\n"
                                                    + "Đứt dây này anh thay dây khác\n"
                                                    + "Mất em rồi anh biết thay ai?",
                                            "Rương Phụ\n(" + (player.inventory.itemsBoxCrackBall.size()
                                                    - InventoryServiceNew.gI()
                                                            .getCountEmptyListItem(player.inventory.itemsBoxCrackBall))
                                                    + " món)",
                                            "Xóa Hết\nRương Phụ", "Đóng");
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.RUONG_PHU) {
                            switch (select) {
                                case 0:
                                    ShopServiceNew.gI().opendShop(player, "ITEMS_LUCKY_ROUND", true);
                                    break;
                                case 1:
                                    NpcService.gI().createMenuConMeo(player,
                                            ConstNpc.CONFIRM_REMOVE_ALL_ITEM_LUCKY_ROUND, this.avartar,
                                            "|3|Bạn chắc muốn xóa hết vật phẩm trong rương phụ?\n"
                                                    + "|7|Sau khi xóa sẽ không thể khôi phục!",
                                            "Đồng ý", "Hủy bỏ");
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc docNhan(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (player.clan == null) {
                        this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Chỉ tiếp các bang hội, miễn tiếp mấy thằng lồn k vào bang", "Đóng");
                        return;
                    }
                    if (player.clan.doanhTrai_haveGone) {
                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Ta đã thả ngọc rồng ở tất cả các map,mau đi nhặt đi. Hẹn ngươi quay lại vào ngày mai",
                                "OK");
                        return;
                    }

                    boolean flag = true;
                    for (Mob mob : player.zone.mobs) {
                        if (!mob.isDie()) {
                            flag = false;
                        }
                    }
                    for (Player boss : player.zone.getBosses()) {
                        if (!boss.isDie()) {
                            flag = false;
                        }
                    }

                    if (flag) {
                        player.clan.doanhTrai_haveGone = true;
                        player.clan.doanhTrai.setLastTimeOpen(System.currentTimeMillis() + 290_000);
                        player.clan.doanhTrai.DropNgocRong();
                        for (Player pl : player.clan.membersInGame) {
                            ItemTimeService.gI().sendTextTime(pl, (byte) 0, "Doanh trại độc nhãn sắp kết thúc : ", 300);
                            ItemTimeService.gI().removeTextDoanhTrai(pl);
                        }
                        player.clan.doanhTrai.timePickDragonBall = true;
                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Ta đã thả ngọc rồng ở tất cả các map,mau đi nhặt đi. Hẹn ngươi quay lại vào ngày mai",
                                "OK");
                    } else {
                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Hãy tiêu diệt hết quái và boss trong map", "OK");
                    }

                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstNpc.IGNORE_MENU:
                            if (select == 1) {
                                NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_DOANH_TRAI);
                            }
                            break;
                    }
                }
            }
        };
    }

    private static Npc popo(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        if (player.getSession().is_gift_box) {
                        } else {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                    "Thượng đế vừa phát hiện 1 loại khí đang âm thầm\nhủy diệt mọi mầm sống trên Trái Đất,\nnó được gọi là Destron Gas.\nTa sẽ đưa các cậu đến nơi ấy, các cậu sẵn sàng chưa?",
                                    "OK", "Từ Chối");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:
                                if (player.clan != null) {
                                    if (player.clan.khiGas != null) {
                                        this.createOtherMenu(player, ConstNpc.MENU_OPENED_GAS,
                                                "Bang hội của con đang đi DesTroy Gas cấp độ "
                                                        + player.clan.khiGas.level + "\nCon có muốn đi theo không?",
                                                "Đồng ý", "Từ chối");
                                    } else {
                                        this.createOtherMenu(player, ConstNpc.MENU_OPEN_GAS,
                                                "Khí Gas Huỷ Diệt đã chuẩn bị tiếp nhận các đợt tấn công của quái vật\n"
                                                        + "các con hãy giúp chúng ta tiêu diệt quái vật \n"
                                                        + "Ở đây có ta lo\nNhớ chọn cấp độ vừa sức mình nhé",
                                                "Chọn\ncấp độ", "Từ chối");
                                    }
                                } else {
                                    this.npcChat(player, "Con phải có bang hội ta mới có thể cho con đi");
                                }
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPENED_GAS) {
                        switch (select) {
                            case 0:
                                if (player.isAdmin() || player.nPoint.power >= Gas.POWER_CAN_GO_TO_GAS) {
                                    ChangeMapService.gI().goToGas(player);
                                } else {
                                    this.npcChat(player, "Sức mạnh của con phải ít nhất phải đạt "
                                            + Util.numberToMoney(Gas.POWER_CAN_GO_TO_GAS));
                                }
                                break;

                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPEN_GAS) {
                        switch (select) {
                            case 0:
                                if (player.isAdmin() || player.nPoint.power >= Gas.POWER_CAN_GO_TO_GAS) {
                                    Input.gI().createFormChooseLevelGas(player);
                                } else {
                                    this.npcChat(player, "Sức mạnh của con phải ít nhất phải đạt "
                                            + Util.numberToMoney(Gas.POWER_CAN_GO_TO_GAS));
                                }
                                break;
                        }

                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_ACCPET_GO_TO_GAS) {
                        switch (select) {
                            case 0:
                                GasService.gI().openBanDoKhoBau1(player,
                                        Integer.parseInt(String.valueOf(PLAYERID_OBJECT.get(player.id))));
                                break;
                        }
                    }
                }
            }
        };
    }

    private static Npc Yardrat(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 171) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Đổi Cải Trang Yardrat cần:\b|7|X9999 Bí Kiếp + 1 Tỷ vàng", "Quay Về",
                                "Đổi \n Cải Trang Yardrat", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 171) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 211, -1, -1);
                                    break;
                                case 1: {
                                    Item honLinhThu = null;
                                    try {
                                        honLinhThu = InventoryServiceNew.gI().findItemBag(player, 590);
                                    } catch (Exception e) {
                                        // throw new RuntimeException(e);
                                    }
                                    if (honLinhThu == null || honLinhThu.quantity < 9999) {
                                        this.npcChat(player, "Bạn không đủ 9999 Bí Kiếp");
                                    } else if (player.inventory.gold < 1_000_000_000) {
                                        this.npcChat(player, "Bạn không đủ 1 Tỷ vàng");
                                    } else if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
                                        this.npcChat(player, "Hành trang của bạn không đủ chỗ trống");
                                    } else {
                                        player.inventory.gold -= 1_000_000_000;
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu, 9999);
                                        Service.gI().sendMoney(player);
                                        Item trungLinhThu = ItemService.gI()
                                                .createNewItem((short) (592 + player.gender));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(94, 40));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(33, 1));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(30, 1));
                                        InventoryServiceNew.gI().addItemBag(player, trungLinhThu);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được 1 Cải Trang Yardrat");
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc meothantai(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                createOtherMenu(player, 0,
                        "\b|8|Trò chơi Tài Xỉu đang được diễn ra\n\n|6|Thử vận may của bạn với trò chơi Tài Xỉu! Đặt cược và dự đoán đúng"
                                + "\n kết quả, bạn sẽ được nhận thưởng lớn. Hãy tham gia ngay và\n cùng trải nghiệm sự hồi hộp, thú vị trong trò chơi này!"
                                + "\n\n|7|(Điều kiện tham gia : mở thành viên)\n\n|2|Đặt tối thiểu: 1.000 Hồng ngọc\n Tối đa: 10.000.000 Hồng ngọc"
                                + "\n\n|7| Lưu ý : Thoát game khi chốt Kết quả sẽ MẤT Tiền cược và Tiền thưởng",
                        "Thể lệ", "Tham gia");
            }

            @Override
            public void confirmMenu(Player pl, int select) {
                if (canOpenNpc(pl)) {
                    String time = ((TaiXiu.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) + " giây";
                    if (pl.iDMark.getIndexMenu() == 0) {
                        if (select == 0) {
                            createOtherMenu(pl, ConstNpc.IGNORE_MENU,
                                    "|5|Có 2 nhà cái Tài và Xĩu, bạn chỉ được chọn 1 nhà để tham gia"
                                            + "\n\n|6|Sau khi kết thúc thời gian đặt cược. Hệ thống sẽ tung xí ngầu để biết kết quả Tài Xỉu"
                                            + "\n\nNếu Tổng số 3 con xí ngầu <=10 : XỈU\nNếu Tổng số 3 con xí ngầu >10 : TÀI\nNếu 3 Xí ngầu cùng 1 số : TAM HOA (Nhà cái lụm hết)"
                                            + "\n\n|7|Lưu ý: Số Hồng ngọc nhận được sẽ bị nhà cái lụm đi 20%. Trong quá trình diễn ra khi đặt cược nếu thoát game trong lúc phát thưởng phần quà sẽ bị HỦY",
                                    "Ok");
                        } else if (select == 1) {
                            if (TaiXiu.gI().baotri == false) {
                                if (pl.goldTai == 0 && pl.goldXiu == 0) {
                                    createOtherMenu(pl, 1, "\n|7|---NHÀ CÁI---\n\n|3|Kết quả kì trước:  "
                                            + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Hồng ngọc"
                                            + "\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Hồng ngọc"
                                            + "\n\n|1|Tổng người chơi: "
                                            + (TaiXiu.gI().PlayersTai.size() + TaiXiu.gI().PlayersXiu.size()) + " người"
                                            + "\n|4|Số Tiền Bạn Đã Đặt: " + (pl.goldTai + pl.goldXiu) + " Hồng ngọc"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Theo TÀI", "Theo XỈU",
                                            "Đóng");
                                } else if (pl.goldTai > 0) {
                                    createOtherMenu(pl, 1, "\n|7|---NHÀ CÁI TÀI XỈU---\n\n|3|Kết quả kì trước:  "
                                            + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Hồng ngọc"
                                            + "\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Hồng ngọc"
                                            + "\n\n|1|Tổng người chơi: "
                                            + (TaiXiu.gI().PlayersTai.size() + TaiXiu.gI().PlayersXiu.size()) + " người"
                                            + "\n|4|Số Tiền Bạn Đã Đặt: " + (pl.goldTai + pl.goldXiu) + " Hồng ngọc"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Đóng");
                                } else {
                                    createOtherMenu(pl, 1, "\n|7|---NHÀ CÁI---\n\n|3|Kết quả kì trước:  "
                                            + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Hồng ngọc"
                                            + "\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Hồng ngọc"
                                            + "\n\n|1|Tổng người chơi: "
                                            + (TaiXiu.gI().PlayersTai.size() + TaiXiu.gI().PlayersXiu.size()) + " người"
                                            + "\n|4|Số Tiền Bạn Đã Đặt: " + (pl.goldTai + pl.goldXiu) + " Hồng ngọc"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Đóng");
                                }
                            } else {
                                if (pl.goldTai == 0 && pl.goldXiu == 0) {
                                    createOtherMenu(pl, 1, "\n|7|---NHÀ CÁI---\n\n|3|Kết quả kì trước:  "
                                            + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Hồng ngọc"
                                            + "\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Hồng ngọc"
                                            + "\n\n|1|Tổng người chơi: "
                                            + (TaiXiu.gI().PlayersTai.size() + TaiXiu.gI().PlayersXiu.size()) + " người"
                                            + "\n|4|Số Tiền Bạn Đã Đặt: " + (pl.goldTai + pl.goldXiu) + " Hồng ngọc"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Theo TÀI", "Theo XỈU",
                                            "Đóng");
                                } else if (pl.goldTai > 0) {
                                    createOtherMenu(pl, 1, "\n|7|---NHÀ CÁI---\n\n|3|Kết quả kì trước:  "
                                            + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Hồng ngọc"
                                            + "\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Hồng ngọc"
                                            + "\n\n|1|Tổng người chơi: "
                                            + (TaiXiu.gI().PlayersTai.size() + TaiXiu.gI().PlayersXiu.size()) + " người"
                                            + "\n|4|Số Tiền Bạn Đã Đặt: " + (pl.goldTai + pl.goldXiu) + " Hồng ngọc"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Đóng");
                                } else {
                                    createOtherMenu(pl, 1,
                                            "\n|7|---NHÀ CÁI---\n\n|3|Kết quả kì trước:  " + TaiXiu.gI().x + " : "
                                                    + TaiXiu.gI().y + " : " + TaiXiu.gI().z + "\n\n|6|Tổng nhà TÀI: "
                                                    + Util.format(TaiXiu.gI().goldTai) + " Hồng ngọc"
                                                    + "\n\nTổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu)
                                                    + " Hồng ngọc\n\n|5|Thời gian còn lại: " + time
                                                    + "\n\n|7|Bạn đã cược Xỉu : " + Util.format(pl.goldXiu)
                                                    + " Hồng ngọc" + "\n\n|7|Hệ thống sắp bảo trì",
                                            "Cập nhập", "Đóng");
                                }
                            }
                        }
                    } else if (pl.iDMark.getIndexMenu() == 1) {
                        if (((TaiXiu.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) > 0 && pl.goldTai == 0
                                && pl.goldXiu == 0 && TaiXiu.gI().baotri == false) {
                            switch (select) {
                                case 0:
                                    createOtherMenu(pl, 1, "\n|7|---NHÀ CÁI---\n\n|3|Kết quả kì trước:  "
                                            + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Hồng ngọc"
                                            + "\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Hồng ngọc"
                                            + "\n\n|1|Tổng người chơi: "
                                            + (TaiXiu.gI().PlayersTai.size() + TaiXiu.gI().PlayersXiu.size()) + " người"
                                            + "\n|4|Số Tiền Bạn Đã Đặt: " + (pl.goldTai + pl.goldXiu) + " Hồng ngọc"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Theo TÀI", "Theo XỈU",
                                            "Đóng");
                                    break;
                                case 1:
                                    if (!pl.getSession().actived) {
                                        Service.gI().sendThongBao(pl,
                                                "Vui lòng kích hoạt tài khoản để sử dụng chức năng này");
                                    } else {
                                        Input.gI().TAI_taixiu(pl);
                                    }
                                    break;
                                case 2:
                                    if (!pl.getSession().actived) {
                                        Service.gI().sendThongBao(pl,
                                                "Vui lòng kích hoạt tài khoản để sử dụng chức năng này");
                                    } else {
                                        Input.gI().XIU_taixiu(pl);
                                    }
                                    break;
                            }
                        } else if (((TaiXiu.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) > 0 && pl.goldTai > 0
                                && TaiXiu.gI().baotri == false) {
                            switch (select) {
                                case 0:
                                    createOtherMenu(pl, 1, "\n|7|---NHÀ CÁI---\n\n|3|Kết quả kì trước:  "
                                            + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Hồng ngọc"
                                            + "\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Hồng ngọc"
                                            + "\n\n|1|Tổng người chơi: "
                                            + (TaiXiu.gI().PlayersTai.size() + TaiXiu.gI().PlayersXiu.size()) + " người"
                                            + "\n|4|Số Tiền Bạn Đã Đặt: " + (pl.goldTai + pl.goldXiu) + " Hồng ngọc"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Đóng");

                                    break;
                            }
                        } else if (((TaiXiu.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) > 0 && pl.goldXiu > 0
                                && TaiXiu.gI().baotri == false) {
                            switch (select) {
                                case 0:
                                    createOtherMenu(pl, 1, "\n|7|---NHÀ CÁI---\n\n|3|Kết quả kì trước:  "
                                            + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Hồng ngọc"
                                            + "\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Hồng ngọc"
                                            + "\n\n|1|Tổng người chơi: "
                                            + (TaiXiu.gI().PlayersTai.size() + TaiXiu.gI().PlayersXiu.size()) + " người"
                                            + "\n|4|Số Tiền Bạn Đã Đặt: " + (pl.goldTai + pl.goldXiu) + " Hồng ngọc"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Đóng");
                                    break;
                            }
                        } else if (((TaiXiu.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) > 0 && pl.goldTai > 0
                                && TaiXiu.gI().baotri == true) {
                            switch (select) {
                                case 0:
                                    createOtherMenu(pl, 1, "\n|7|---NHÀ CÁI---\n\n|3|Kết quả kì trước:  "
                                            + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Hồng ngọc"
                                            + "\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Hồng ngọc"
                                            + "\n\n|1|Tổng người chơi: "
                                            + (TaiXiu.gI().PlayersTai.size() + TaiXiu.gI().PlayersXiu.size()) + " người"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Đóng");

                                    break;
                            }
                        } else if (((TaiXiu.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) > 0 && pl.goldXiu > 0
                                && TaiXiu.gI().baotri == true) {
                            switch (select) {
                                case 0:
                                    createOtherMenu(pl, 1, "\n|7|---NHÀ CÁI---\n\n|3|Kết quả kì trước:  "
                                            + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Hồng ngọc"
                                            + "\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Hồng ngọc"
                                            + "\n\n|1|Tổng người chơi: "
                                            + (TaiXiu.gI().PlayersTai.size() + TaiXiu.gI().PlayersXiu.size()) + " người"
                                            + "\n|4|Số Tiền Bạn Đã Đặt: " + (pl.goldTai + pl.goldXiu) + " Hồng ngọc"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Đóng");

                                    break;
                            }
                        } else if (((TaiXiu.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) > 0
                                && pl.goldXiu == 0 && pl.goldTai == 0 && TaiXiu.gI().baotri == true) {
                            switch (select) {
                                case 0:
                                    createOtherMenu(pl, 1, "\n|7|---NHÀ CÁI---\n\n|3|Kết quả kì trước:  "
                                            + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Hồng ngọc"
                                            + "\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Hồng ngọc"
                                            + "\n\n|1|Tổng người chơi: "
                                            + (TaiXiu.gI().PlayersTai.size() + TaiXiu.gI().PlayersXiu.size()) + " người"
                                            + "\n|4|Số Tiền Bạn Đã Đặt: " + (pl.goldTai + pl.goldXiu) + " Hồng ngọc"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Theo TÀI", "Theo XỈU",
                                            "Đóng");

                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc meothantai1(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                createOtherMenu(player, 0,
                        "\b|8|Trò chơi Chẵn Lẻ đang được diễn ra\n\n|6|Thử vận may của bạn với trò chơi Chẵn Lẻ! Đặt cược và dự đoán đúng"
                                + "\n kết quả, bạn sẽ được nhận thưởng lớn. Hãy tham gia ngay và\n cùng trải nghiệm sự hồi hộp, thú vị trong trò chơi này!"
                                + "\n\n|7|(Điều kiện tham gia : mở thành viên)\n\n|2|Đặt tối thiểu: 10 thỏi vàng\n Tối đa: 10.000 thỏi vàng"
                                + "\n\n|7| Lưu ý : Thoát game khi chốt Kết quả sẽ MẤT Tiền cược và Tiền thưởng",
                        "Thể lệ", "Tham gia");
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.getIndexMenu() == 0) {
                        if (select == 0) {
                            createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                    "|5|Có 2 nhà cái Chẵn và Lẻ, bạn chỉ được chọn 1 nhà để tham gia"
                                            + "\n\n|6|Sau khi kết thúc thời gian đặt cược. Hệ thống sẽ tung xí ngầu để biết kết quả Chẵn Lẻ"
                                            + "\n\nNếu Tổng số 3 con xí ngầu là 1,3,5,7,9,11,13,15,17 : Lẻ\nNếu Tổng số 3 con xí ngầu là 2,4,6,8,10,12,14,16,18"
                                            + "\n\n|7|Lưu ý: Số Hồng ngọc nhận được sẽ bị nhà cái lụm đi 20%. Trong quá trình diễn ra khi đặt cược nếu thoát game trong lúc phát thưởng phần quà sẽ bị HỦY",
                                    "Ok");
                        } else if (select == 1) {
                            if (pariryManager.time > 0 && player.cuoc1 == 0 && player.cuoc == 0) {
                                createOtherMenu(player, 1, "\n|7|---NHÀ CÁI---"
                                        + "\n\n|6|Tổng nhà Chẵn: " + Util.format(pariryManager.gI().goldChan)
                                        + " thỏi vàng"
                                        + "\n|6|Tổng nhà Lẻ: " + Util.format(pariryManager.gI().goldLe) + " thỏi vàng"
                                        + "\n\n|4|Số Tiền Bạn Đã Đặt: " + (player.cuoc + player.cuoc1) + " thỏi vàng"
                                        + "\n Bạn đã thắng được " + Util.numberToMoney((int) (player.rubyWin * 1.8))
                                        + " thỏi vàng"
                                        + "\n\n|5|Thời gian còn lại: " + pariryManager.time + " giây", "Cập nhập",
                                        "Theo Chẵn", "Theo Lẻ", "Xem \nlịch sử\nbản thân", "Xem lịch sử",
                                        "Nhận\nphần thưởng", "Đóng");
                            } else if (pariryManager.time > 0 && (player.cuoc1 > 0 || player.cuoc > 0)) {
                                createOtherMenu(player, 1, "\n|7|---NHÀ CÁI---"
                                        + "\n\n|6|Tổng nhà Chẵn: " + Util.format(pariryManager.gI().goldChan)
                                        + " thỏi vàng"
                                        + "\n|6|Tổng nhà Lẻ: " + Util.format(pariryManager.gI().goldLe) + " thỏi vàng"
                                        + "\n\n|4|Số Tiền Bạn Đã Đặt: " + (player.cuoc + player.cuoc1) + " thỏi vàng"
                                        + "\n Bạn đã thắng được " + Util.numberToMoney((int) (player.rubyWin * 1.8))
                                        + " thỏi vàng"
                                        + "\n\n|5|Thời gian còn lại: " + pariryManager.time + " giây", "Cập nhập",
                                        "Đóng");
                            } else {
                                createOtherMenu(player, 1, "\n|7|---NHÀ CÁI---"
                                        + "\n\n|6|Tổng nhà Chẵn: " + Util.format(pariryManager.gI().goldChan)
                                        + " thỏi vàng"
                                        + "\n|6|Tổng nhà Lẻ: " + Util.format(pariryManager.gI().goldLe) + " thỏi vàng"
                                        + "\n\n|4|Số Tiền Bạn Đã Đặt: " + (player.cuoc + player.cuoc1) + " thỏi vàng"
                                        + "\n Bạn đã thắng được " + Util.numberToMoney((int) (player.rubyWin * 1.8))
                                        + " thỏi vàng"
                                        + "\n\n|5|Thời gian còn lại: " + pariryManager.time + " giây"
                                        + "\n\n|7|Hệ thống đang tính toán", "Cập nhập", "Đóng");
                            }
                        }
                    } else if (player.iDMark.getIndexMenu() == 1) {
                        if (pariryManager.time > 0 && player.cuoc == 0 && player.cuoc1 == 0) {
                            switch (select) {
                                case 0:
                                    createOtherMenu(player, 1, "\n|7|---NHÀ CÁI---"
                                            + "\n\n|6|Tổng nhà Chẵn: " + Util.format(pariryManager.gI().goldChan)
                                            + " thỏi vàng"
                                            + "\n|6|Tổng nhà Lẻ: " + Util.format(pariryManager.gI().goldLe)
                                            + " thỏi vàng"
                                            + "\n\n|4|Số Tiền Bạn Đã Đặt: " + (player.cuoc + player.cuoc1)
                                            + " thỏi vàng"
                                            + "\n Bạn đã thắng được " + Util.numberToMoney((int) (player.rubyWin * 1.8))
                                            + " thỏi vàng"
                                            + "\n\n|5|Thời gian còn lại: " + pariryManager.time + " giây", "Cập nhập",
                                            "Theo Chẵn", "Theo Lẻ", "Xem \nlịch sử\nbản thân", "Xem lịch sử",
                                            "Nhận\nphần thưởng", "Đóng");
                                    break;

                                case 1:
                                    if (!player.getSession().actived) {
                                        Service.gI().sendThongBao(player,
                                                "Vui lòng kích hoạt tài khoản để sử dụng chức năng này");
                                    } else {
                                        Input.gI().CHAN(player);
                                    }
                                    break;
                                case 2:
                                    if (!player.getSession().actived) {
                                        Service.gI().sendThongBao(player,
                                                "Vui lòng kích hoạt tài khoản để sử dụng chức năng này");
                                    } else {
                                        Input.gI().LE(player);
                                    }
                                    break;
                                case 3:
                                    Service.getInstance().sendThongBaoOK(player,
                                            PariryServices.gI().getHistoryPlayer(player));
                                    break;
                                case 4:
                                    Service.getInstance().sendThongBaoOK(player,
                                            PariryServices.gI().getHistory());
                                    break;
                                case 5:
                                    if (player.rubyWin <= 0) {
                                        Service.getInstance().sendThongBaoOK(player, "Có cái nịt mà nhận");
                                        break;
                                    }
                                    PariryServices.gI().rewardRuby(player);
                                    break;
                            }
                        } else if (pariryManager.time > 0 && (player.cuoc1 > 0 || player.cuoc > 0)) {
                            switch (select) {
                                case 0:
                                    createOtherMenu(player, 1, "\n|7|---NHÀ CÁI---"
                                            + "\n\n|6|Tổng nhà Chẵn: " + Util.format(pariryManager.gI().goldChan)
                                            + " thỏi vàng"
                                            + "\n|6|Tổng nhà Lẻ: " + Util.format(pariryManager.gI().goldLe)
                                            + " thỏi vàng"
                                            + "\n\n|4|Số Tiền Bạn Đã Đặt: " + (player.cuoc + player.cuoc1)
                                            + " thỏi vàng"
                                            + "\n Bạn đã thắng được " + Util.numberToMoney((int) (player.rubyWin * 1.8))
                                            + " thỏi vàng"
                                            + "\n\n|5|Thời gian còn lại: " + pariryManager.time + " giây", "Cập nhập",
                                            "Đóng");
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc khidaumoi(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (this.mapId == 80) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Bạn muốn nâng cấp khỉ ư?", "Nâng cấp\nkhỉ", "Shop của Khỉ", "Từ chối");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 80) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    this.createOtherMenu(player, 1,
                                            "|2|Mỗi lần nâng cấp tiếp thì mỗi cấp cần thêm đuôi khỉ và thỏi vàng\n Bật cờ giết quái khỉ vào những giờ chẵn sẽ rơi ra đuôi khỉ",
                                            "Khỉ\ncấp 2",
                                            "Khỉ\ncấp 3",
                                            "Khỉ\ncấp 4",
                                            "Khỉ\ncấp 5",
                                            "Khỉ\ncấp 6",
                                            "Khỉ\ncấp 7",
                                            "Khỉ\ncấp 8",
                                            "Từ chối");
                                    break;
                                case 1: // shop
                                    ShopServiceNew.gI().opendShop(player, "KHI", false);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == 1) { // action đổi dồ húy diệt
                            switch (select) {
                                case 0: // trade
                                    try {
                                        Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 579);
                                        Item klv1 = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1111);
                                        Item tv = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 457);
                                        int soLuong = 0;
                                        int soLuongg = 0;
                                        int soLuonggg = 0;
                                        if (dns != null) {
                                            if (klv1 != null) {
                                                if (tv != null) {
                                                    soLuong = dns.quantity;
                                                    soLuongg = klv1.quantity;
                                                    soLuonggg = tv.quantity;
                                                }
                                            }
                                        }
                                        for (int i = 0; i < 12; i++) {
                                            Item klv = InventoryServiceNew.gI().findItem(player.inventory.itemsBag,
                                                    1111);

                                            if (InventoryServiceNew.gI().isExistItemBag(player, 1111) && soLuong >= 20
                                                    && soLuongg >= 1 && soLuonggg >= 10) {
                                                CombineServiceNew.gI().khilv2(player, 1136);
                                                InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 20);
                                                InventoryServiceNew.gI().subQuantityItemsBag(player, klv, 1);
                                                InventoryServiceNew.gI().subQuantityItemsBag(player, tv, 10);
                                                this.npcChat(player, "Upgrede Thành Công!");

                                                break;
                                            } else {
                                                this.npcChat(player,
                                                        "Yêu cầu cần cái trang khỉ cấp 1 với 20 đuôi khỉ với 10 thỏi vàng");
                                            }

                                        }
                                    } catch (Exception e) {

                                    }
                                    break;
                                case 1: // trade
                                    try {
                                        Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 579);
                                        Item klv1 = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1136);
                                        Item tv = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 457);
                                        int soLuong = 0;
                                        int soLuongg = 0;
                                        int soLuonggg = 0;
                                        if (dns != null) {
                                            if (klv1 != null) {
                                                if (tv != null) {
                                                    soLuong = dns.quantity;
                                                    soLuongg = klv1.quantity;
                                                    soLuonggg = tv.quantity;
                                                }
                                            }
                                        }
                                        for (int i = 0; i < 12; i++) {
                                            Item klv = InventoryServiceNew.gI().findItem(player.inventory.itemsBag,
                                                    1136 + i);

                                            if (InventoryServiceNew.gI().isExistItemBag(player, 1136 + i)
                                                    && soLuong >= 30 && soLuongg >= 1 && soLuonggg >= 15) {
                                                CombineServiceNew.gI().khilv3(player, 1137 + i);
                                                InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                                InventoryServiceNew.gI().subQuantityItemsBag(player, klv, 1);
                                                InventoryServiceNew.gI().subQuantityItemsBag(player, tv, 15);
                                                this.npcChat(player, "Upgrede Thành Công!");

                                                break;
                                            } else {
                                                this.npcChat(player,
                                                        "Yêu cầu cần cái trang khỉ cấp 2 với 30 đuôi khỉ với 15 thỏi vàng");
                                            }

                                        }
                                    } catch (Exception e) {

                                    }
                                    break;
                                case 2: // trade
                                    try {
                                        Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 579);
                                        Item klv1 = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1137);
                                        Item tv = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 457);
                                        int soLuong = 0;
                                        int soLuongg = 0;
                                        int soLuonggg = 0;
                                        if (dns != null) {
                                            if (klv1 != null) {
                                                if (tv != null) {
                                                    soLuong = dns.quantity;
                                                    soLuongg = klv1.quantity;
                                                    soLuonggg = tv.quantity;
                                                }
                                            }
                                        }
                                        for (int i = 0; i < 12; i++) {
                                            Item klv = InventoryServiceNew.gI().findItem(player.inventory.itemsBag,
                                                    1137 + i);

                                            if (InventoryServiceNew.gI().isExistItemBag(player, 1137 + i)
                                                    && soLuong >= 40 && soLuongg >= 1 && soLuonggg >= 20) {
                                                CombineServiceNew.gI().khilv4(player, 1138 + i);
                                                InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 40);
                                                InventoryServiceNew.gI().subQuantityItemsBag(player, klv, 1);
                                                InventoryServiceNew.gI().subQuantityItemsBag(player, tv, 20);
                                                this.npcChat(player, "Upgrede Thành Công!");

                                                break;
                                            } else {
                                                this.npcChat(player,
                                                        "Yêu cầu cần cái trang khỉ cấp 3 với 40 đuôi khỉ với 20 thỏi vàng");
                                            }

                                        }
                                    } catch (Exception e) {

                                    }
                                    break;
                                case 3: // trade
                                    try {
                                        Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 579);
                                        Item klv1 = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1138);
                                        Item tv = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 457);
                                        int soLuong = 0;
                                        int soLuongg = 0;
                                        int soLuonggg = 0;
                                        if (dns != null) {
                                            if (klv1 != null) {
                                                if (tv != null) {
                                                    soLuong = dns.quantity;
                                                    soLuongg = klv1.quantity;
                                                    soLuonggg = tv.quantity;
                                                }
                                            }
                                        }
                                        for (int i = 0; i < 12; i++) {
                                            Item klv = InventoryServiceNew.gI().findItem(player.inventory.itemsBag,
                                                    1138 + i);

                                            if (InventoryServiceNew.gI().isExistItemBag(player, 1138 + i)
                                                    && soLuong >= 50 && soLuongg >= 1 && soLuonggg >= 25) {
                                                CombineServiceNew.gI().khilv5(player, 1112 + i);
                                                InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 50);
                                                InventoryServiceNew.gI().subQuantityItemsBag(player, klv, 1);
                                                InventoryServiceNew.gI().subQuantityItemsBag(player, tv, 25);
                                                this.npcChat(player, "Upgrede Thành Công!");

                                                break;
                                            } else {
                                                this.npcChat(player,
                                                        "Yêu cầu cần cái trang khỉ cấp 4 với 50 đuôi khỉ với 25 thỏi vàng");
                                            }

                                        }
                                    } catch (Exception e) {

                                    }
                                    break;
                                case 4: // trade
                                    try {
                                        Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 579);
                                        Item klv1 = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1112);
                                        Item tv = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 457);
                                        int soLuong = 0;
                                        int soLuongg = 0;
                                        int soLuonggg = 0;
                                        if (dns != null) {
                                            if (klv1 != null) {
                                                if (tv != null) {
                                                    soLuong = dns.quantity;
                                                    soLuongg = klv1.quantity;
                                                    soLuonggg = tv.quantity;
                                                }
                                            }
                                        }
                                        for (int i = 0; i < 12; i++) {
                                            Item klv = InventoryServiceNew.gI().findItem(player.inventory.itemsBag,
                                                    1112 + i);

                                            if (InventoryServiceNew.gI().isExistItemBag(player, 1112 + i)
                                                    && soLuong >= 60 && soLuongg >= 1 && soLuonggg >= 30) {
                                                CombineServiceNew.gI().khilv6(player, 1113 + i);
                                                InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 60);
                                                InventoryServiceNew.gI().subQuantityItemsBag(player, klv, 1);
                                                InventoryServiceNew.gI().subQuantityItemsBag(player, tv, 30);
                                                this.npcChat(player, "Upgrede Thành Công!");

                                                break;
                                            } else {
                                                this.npcChat(player,
                                                        "Yêu cầu cần cái trang khỉ cấp 5 với 60 đuôi khỉ với 30 thỏi vàng");
                                            }

                                        }
                                    } catch (Exception e) {

                                    }
                                    break;
                                case 5: // trade
                                    try {
                                        Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 579);
                                        Item klv1 = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1113);
                                        Item tv = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 457);
                                        int soLuong = 0;
                                        int soLuongg = 0;
                                        int soLuonggg = 0;
                                        if (dns != null) {
                                            if (klv1 != null) {
                                                if (tv != null) {
                                                    soLuong = dns.quantity;
                                                    soLuongg = klv1.quantity;
                                                    soLuonggg = tv.quantity;
                                                }
                                            }
                                        }
                                        for (int i = 0; i < 12; i++) {
                                            Item klv = InventoryServiceNew.gI().findItem(player.inventory.itemsBag,
                                                    1113 + i);

                                            if (InventoryServiceNew.gI().isExistItemBag(player, 1113 + i)
                                                    && soLuong >= 70 && soLuongg >= 1 && soLuonggg >= 35) {
                                                CombineServiceNew.gI().khilv7(player, 1114 + i);
                                                InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 70);
                                                InventoryServiceNew.gI().subQuantityItemsBag(player, klv, 1);
                                                InventoryServiceNew.gI().subQuantityItemsBag(player, tv, 35);
                                                this.npcChat(player, "Upgrede Thành Công!");

                                                break;
                                            } else {
                                                this.npcChat(player,
                                                        "Yêu cầu cần cái trang khỉ cấp 6 với 70 đuôi khỉ với 35 thỏi vàng");
                                            }

                                        }
                                    } catch (Exception e) {

                                    }
                                    break;
                                case 6: // trade
                                    try {
                                        Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 579);
                                        Item klv1 = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1114);
                                        Item tv = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 457);
                                        int soLuong = 0;
                                        int soLuongg = 0;
                                        int soLuonggg = 0;
                                        if (dns != null) {
                                            if (klv1 != null) {
                                                if (tv != null) {
                                                    soLuong = dns.quantity;
                                                    soLuongg = klv1.quantity;
                                                    soLuonggg = tv.quantity;
                                                }
                                            }
                                        }
                                        for (int i = 0; i < 12; i++) {
                                            Item klv = InventoryServiceNew.gI().findItem(player.inventory.itemsBag,
                                                    1114 + i);

                                            if (InventoryServiceNew.gI().isExistItemBag(player, 1114 + i)
                                                    && soLuong >= 99 && soLuongg >= 1 && soLuonggg >= 150) {
                                                CombineServiceNew.gI().khilv8(player, 1110 + i);
                                                InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 99);
                                                InventoryServiceNew.gI().subQuantityItemsBag(player, klv, 1);
                                                InventoryServiceNew.gI().subQuantityItemsBag(player, tv, 150);
                                                this.npcChat(player, "Upgrede Thành Công!");

                                                break;
                                            } else {
                                                this.npcChat(player,
                                                        "Yêu cầu cần cái trang khỉ cấp 7 với 99 đuôi khỉ với 150 thỏi vàng");
                                            }

                                        }
                                    } catch (Exception e) {

                                    }
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    private static Npc poTaGe(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 140) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Đa vũ trụ song song \b|7|Con muốn gọi con trong đa vũ trụ \b|1|Với giá 500 tr vàng không?",
                                "Gọi Boss\nNhân bản", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 140) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: {
                                    Boss oldBossClone = BossManager.gI()
                                            .getBossByType(Util.createIdBossClone((int) player.id));
                                    if (oldBossClone != null) {
                                        this.npcChat(player,
                                                "Nhà ngươi hãy tiêu diệt Boss lúc trước gọi ra đã, con boss đó đang ở khu "
                                                        + oldBossClone.zone.zoneId);
                                    } else if (player.inventory.gold < 500_000_000) {
                                        this.npcChat(player, "Nhà ngươi không đủ 500 tr vàng ");
                                    } else {
                                        List<Skill> skillList = new ArrayList<>();
                                        for (byte i = 0; i < player.playerSkill.skills.size(); i++) {
                                            Skill skill = player.playerSkill.skills.get(i);
                                            if (skill.point > 0) {
                                                skillList.add(skill);
                                            }
                                        }
                                        int[][] skillTemp = new int[skillList.size()][3];
                                        for (byte i = 0; i < skillList.size(); i++) {
                                            Skill skill = skillList.get(i);
                                            if (skill.point > 0) {
                                                skillTemp[i][0] = skill.template.id;
                                                skillTemp[i][1] = skill.point;
                                                skillTemp[i][2] = skill.coolDown;
                                            }
                                        }
                                        BossData bossDataClone = new BossData(
                                                "Nhân Bản" + player.name,
                                                player.gender,
                                                new short[] { player.getHead(), player.getBody(), player.getLeg(),
                                                        player.getFlagBag(), player.idAura, player.getEffFront() },
                                                player.nPoint.dame,
                                                new int[] { player.nPoint.hpMax },
                                                new int[] { 140 },
                                                skillTemp,
                                                new String[] { "|-2|Boss nhân bản đã xuất hiện rồi" }, // text chat 1
                                                new String[] { "|-1|Ta sẽ chiếm lấy thân xác của ngươi hahaha!" }, // text
                                                                                                                   // chat
                                                                                                                   // 2
                                                new String[] { "|-1|Lần khác ta sẽ xử đẹp ngươi" }, // text chat 3
                                                60);

                                        try {
                                            new NhanBan(Util.createIdBossClone((int) player.id), bossDataClone,
                                                    player.zone);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        // trừ vàng khi gọi boss
                                        player.inventory.gold -= 200_000_000;
                                        Service.gI().sendMoney(player);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        };
    }

    private static Npc quyLaoKame(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        if (player.getSession().is_gift_box) {
                            // this.createOtherMenu(player, ConstNpc.BASE_MENU, "Chào con, con muốn ta giúp
                            // gì nào?", "Giải tán bang hội", "Nhận quà\nđền bù");
                        } else {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, "Chào con, con muốn bố giúp gì nào?",
                                    "Kho báu dưới biển", "Giải tán bang hội", "Lãnh địa Bang Hội");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:
                                if (player.clan != null) {
                                    if (player.clan.banDoKhoBau != null) {
                                        this.createOtherMenu(player, ConstNpc.MENU_OPENED_DBKB,
                                                "Bang hội của con đang đi tìm kho báu dưới biển cấp độ "
                                                        + player.clan.banDoKhoBau.level
                                                        + "\nCon có muốn đi theo không?",
                                                "Đồng ý", "Từ chối");
                                    } else {

                                        this.createOtherMenu(player, ConstNpc.MENU_OPEN_DBKB,
                                                "Đây là bản đồ kho báu \nCác con cứ yên tâm lên đường\n"
                                                        + "Ở đây có ta lo\nNhớ chọn cấp độ vừa sức mình nhé",
                                                "Chọn\ncấp độ", "Từ chối");
                                    }
                                } else {
                                    this.npcChat(player, "Không có bang đòi đi cái cc à ");
                                }
                                break;
                            case 1:
                                Clan clan = player.clan;
                                if (clan != null) {
                                    ClanMember cm = clan.getClanMember((int) player.id);
                                    if (cm != null) {
                                        if (clan.members.size() > 1) {
                                            Service.gI().sendThongBao(player, "Bang phải còn một người");
                                            break;
                                        }
                                        if (!clan.isLeader(player)) {
                                            Service.gI().sendThongBao(player, "Phải là bảng chủ");
                                            break;
                                        }
                                        //
                                        NpcService.gI().createMenuConMeo(player, ConstNpc.CONFIRM_DISSOLUTION_CLAN, -1,
                                                "Con có chắc chắn muốn giải tán bang hội không? Ta cho con 2 lựa chọn...",
                                                "Đồng ý!", "Từ chối!");
                                    }
                                    break;
                                }
                                Service.gI().sendThongBao(player, "Có bang hội đâu ba!!!");
                                break;
                            case 2:
                                if (player.getSession().player.nPoint.power >= 100000000000L) {

                                    ChangeMapService.gI().changeMapBySpaceShip(player, 153, -1, 432);
                                } else {
                                    this.npcChat(player, "Mày chưa đủ 100 tỏi sức mạnh để vào");
                                }
                                break; // qua lanh dia

                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPENED_DBKB) {
                        switch (select) {
                            case 0:
                                if (player.isAdmin() || player.nPoint.power >= BanDoKhoBau.POWER_CAN_GO_TO_DBKB) {
                                    ChangeMapService.gI().goToDBKB(player);
                                } else {
                                    this.npcChat(player, "Sức mạnh của con phải ít nhất phải đạt "
                                            + Util.numberToMoney(BanDoKhoBau.POWER_CAN_GO_TO_DBKB));
                                }
                                break;

                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPEN_DBKB) {
                        switch (select) {
                            case 0:
                                if (player.isAdmin() || player.nPoint.power >= BanDoKhoBau.POWER_CAN_GO_TO_DBKB) {
                                    Input.gI().createFormChooseLevelBDKB(player);
                                } else {
                                    this.npcChat(player, "Sức mạnh của con phải ít nhất phải đạt "
                                            + Util.numberToMoney(BanDoKhoBau.POWER_CAN_GO_TO_DBKB));
                                }
                                break;
                        }

                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_ACCEPT_GO_TO_BDKB) {
                        switch (select) {
                            case 0:
                                BanDoKhoBauService.gI().openBanDoKhoBau(player,
                                        Byte.parseByte(String.valueOf(PLAYERID_OBJECT.get(player.id))));
                                break;
                        }
                    }
                }
            }
        };
    }

    // Lưu trữ skill được chọn cho mỗi player (dùng cho truongLaoGuru)
    private static final java.util.Map<Long, Integer> PLAYER_SKILL_SELECT_GURU = new HashMap<>();

    public static Npc truongLaoGuru(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Chào con, ta là Trưởng Lão Guru. Ta có thể dạy con các kỹ năng nếu con có đủ tiềm năng.",
                                "Học kĩ năng", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        if (select == 0) { // Học kĩ năng
                            showSkillListMenu(player);
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_SKILL_LIST) {
                        // Player chọn skill từ danh sách
                        handleSkillSelection(player, select);
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_CONFIRM_LEARN_SKILL) {
                        if (select == 0) { // Đồng ý học
                            doLearnSkill(player);
                        }
                    }
                }
            }

            // Hiển thị danh sách skill có thể học
            private void showSkillListMenu(Player player) {
                NClass nClass = Manager.NCLASS.get(player.gender);
                java.util.List<String> menuOptions = new ArrayList<>();

                for (SkillTemplate skillTemp : nClass.skillTemplatess) {
                    Skill playerSkill = player.playerSkill.getSkillbyId(skillTemp.id);
                    String name = skillTemp.name;
                    switch (playerSkill.template.id) {
                        case Skill.SUPER_KAME:
                        case Skill.MA_PHONG_BA:
                        case Skill.LIEN_HOAN_CHUONG: // xd
                            continue;
                        case Skill.MAKANKOSAPPO:
                            name = "Laze";
                            break;
                    }
                    int currentLevel = (playerSkill != null) ? playerSkill.point : 0;
                    int maxLevel = skillTemp.maxPoint;

                    if (currentLevel < maxLevel) {
                        int nextLevel = currentLevel + 1;
                        long cost = calculateSkillCost(skillTemp.id, nextLevel);
                        menuOptions
                                .add(name + "\ncấp " + nextLevel + "\n(" + Util.numberToMoney(cost) + " TN)");
                    }
                }

                if (menuOptions.isEmpty()) {
                    this.npcChat(player, "Con đã học hết tất cả kỹ năng rồi!");
                    return;
                }

                String[] options = menuOptions.toArray(new String[0]);
                this.createOtherMenu(player, ConstNpc.MENU_SKILL_LIST,
                        "Chọn kỹ năng muốn học (TN = tiềm năng):", options);
            }

            // Xử lý khi player chọn skill
            private void handleSkillSelection(Player player, int selectIndex) {
                NClass nClass = Manager.NCLASS.get(player.gender);
                int foundIndex = 0;

                for (SkillTemplate skillTemp : nClass.skillTemplatess) {
                    Skill playerSkill = player.playerSkill.getSkillbyId(skillTemp.id);
                    int currentLevel = (playerSkill != null) ? playerSkill.point : 0;

                    if (currentLevel < skillTemp.maxPoint) {
                        if (foundIndex == selectIndex) {
                            int nextLevel = currentLevel + 1;
                            long cost = calculateSkillCost(skillTemp.id, nextLevel);

                            PLAYER_SKILL_SELECT_GURU.put(player.id, (int) skillTemp.id);

                            this.createOtherMenu(player, ConstNpc.MENU_CONFIRM_LEARN_SKILL,
                                    "Bạn có muốn học " + skillTemp.name + " cấp " + nextLevel +
                                            "?\nChi phí: " + Util.numberToMoney(cost) + " tiềm năng" +
                                            "\nTiềm năng hiện có: " + Util.numberToMoney(player.nPoint.tiemNang),
                                    "Đồng ý", "Từ chối");
                            return;
                        }
                        foundIndex++;
                    }
                }
            }

            // Thực hiện học skill
            private void doLearnSkill(Player player) {
                Integer skillId = PLAYER_SKILL_SELECT_GURU.get(player.id);
                if (skillId == null)
                    return;

                NClass nClass = Manager.NCLASS.get(player.gender);
                SkillTemplate skillTemp = nClass.getSkillTemplate(skillId);
                if (skillTemp == null)
                    return;

                Skill playerSkill = player.playerSkill.getSkillbyId(skillId);
                int currentLevel = (playerSkill != null) ? playerSkill.point : 0;
                int nextLevel = currentLevel + 1;
                long cost = calculateSkillCost(skillId, nextLevel);

                if (player.nPoint.tiemNang < cost) {
                    this.npcChat(player, "Con không đủ tiềm năng! Cần " + Util.numberToMoney(cost) + " tiềm năng.");
                    return;
                }

                // Trừ tiềm năng
                player.nPoint.tiemNang -= cost;

                // Nâng cấp skill (giống UseItem.learnSkill)
                try {
                    Message msg;
                    Skill newSkill = SkillUtil.createSkill(skillId, nextLevel);

                    if (currentLevel == 0) {
                        // Học skill mới - subCommand 23
                        SkillUtil.setSkill(player, newSkill);
                        msg = Service.gI().messageSubCommand((byte) 23);
                        msg.writer().writeShort(newSkill.skillId);
                        player.sendMessage(msg);
                        msg.cleanup();
                    } else {
                        // Nâng cấp skill đã có - subCommand 62
                        SkillUtil.setSkill(player, newSkill);
                        msg = Service.gI().messageSubCommand((byte) 62);
                        msg.writer().writeShort(newSkill.skillId);
                        player.sendMessage(msg);
                        msg.cleanup();
                    }

                    // Gửi thông báo và cập nhật
                    Service.gI().sendThongBao(player, "Học " + skillTemp.name + " cấp " + nextLevel + " thành công!");
                    Service.gI().point(player);

                } catch (Exception e) {
                    com.girlkun.utils.Logger.logException(NpcFactory.class, e);
                }

                PLAYER_SKILL_SELECT_GURU.remove(player.id);
            }

            private long OneK(int count) {
                return (long) (count * 1000);
            }

            private long OneM(float count) {
                return (long) (count * 1000000);
            }

            // Tính tiềm năng cần thiết theo loại skill
            private long calculateSkillCost(int skillId, int level) {
                switch (skillId) {
                    case Skill.DRAGON:
                    case Skill.DEMON:
                    case Skill.GALICK:
                        switch (level) {
                            case 1: {
                                return OneK(1);
                            }
                            case 2: {
                                return OneK(10);
                            }
                            case 3: {
                                return OneK(22);
                            }
                            case 4: {
                                return OneK(66);
                            }
                            case 5: {
                                return OneK(200);
                            }
                            case 6: {
                                return OneK(600);
                            }
                            case 7: {
                                return OneM(1.8f);
                            }
                        }
                        break;
                    case Skill.KAMEJOKO:
                    case Skill.MASENKO:
                    case Skill.ANTOMIC:
                        switch (level) {
                            case 1: {
                                return OneK(10);
                            }
                            case 2: {
                                return OneK(20);
                            }
                            case 3: {
                                return OneK(60);
                            }
                            case 4: {
                                return OneK(180);
                            }
                            case 5: {
                                return OneK(540);
                            }
                            case 6: {
                                return OneM(1.6f);
                            }
                            case 7: {
                                return OneM(4.8f);
                            }
                        }
                        break;
                    case Skill.THAI_DUONG_HA_SAN:
                    case Skill.TRI_THUONG:
                    case Skill.TAI_TAO_NANG_LUONG:
                        switch (level) {
                            case 1: {
                                return OneK(60);
                            }
                            case 2: {
                                return OneK(120);
                            }
                            case 3: {
                                return OneK(360);
                            }
                            case 4: {
                                return OneM(1);
                            }
                            case 5: {
                                return OneM(3.2f);
                            }
                            case 6: {
                                return OneM(10);
                            }
                            case 7: {
                                return OneM(30f);
                            }
                        }
                        break;
                    case Skill.KAIOKEN:
                    case Skill.MAKANKOSAPPO:
                        return OneM(100 + 50 * level);
                    case Skill.QUA_CAU_KENH_KHI:
                    case Skill.DE_TRUNG:
                        return OneM(400 + level * 100);
                    case Skill.BIEN_KHI:
                        return OneM(150 + level * 100);
                    case Skill.TU_SAT:
                        return OneM(200 + level * 50);
                    case Skill.DICH_CHUYEN_TUC_THOI:
                    case Skill.THOI_MIEN:
                    case Skill.LIEN_HOAN:
                    case Skill.SOCOLA:
                    case Skill.HUYT_SAO:
                    case Skill.TROI:
                    case Skill.KHIEN_NANG_LUONG:
                        switch (level) {
                            case 1: {
                                return OneM(10);
                            }
                            case 2: {
                                return OneM(25);
                            }
                            case 3: {
                                return OneM(50);
                            }
                            case 4: {
                                return OneM(125);
                            }
                            case 5: {
                                return OneM(625);
                            }
                            case 6: {
                                return OneM(3125);
                            }
                            case 7: {
                                return OneM(15625);
                            }
                        }
                        break;
                }
                return OneM(1000);
            }
        };
    }

    public static Npc vuaVegeta(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        super.openBaseMenu(player);
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {

                }
            }
        };
    }

    public static Npc ongGohan_ongMoori_ongParagus(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "|7|Mở thành viên chỉ với 10K\n Mở thành viên nhận đuợc 40tv và 10k hn\n|2|Hiện tại mày đang có:"
                                        + player.getSession().coinBar + "Đ"
                                                .replaceAll("%1", player.gender == ConstPlayer.TRAI_DAT ? "Quy lão Kamê"
                                                        : player.gender == ConstPlayer.NAMEC ? "Trưởng lão Guru"
                                                                : "Vua Vegeta"),
                                "Đổi mật khẩu", "Đổi Thỏi Vàng", "Đổi Hồng Ngọc", "Nhận ngọc xanh",
                                "Kích hoạt\n Tài khoản", "GiftCode");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:
                                Input.gI().createFormChangePassword(player);
                                break;
                            case 1:
                                Input.gI().createFormQDTV(player);
                                break;
                            case 2:
                                Input.gI().createFormQDN(player);
                                break;
                            case 3:
                                if (player.inventory.gem > 1000) {
                                    this.npcChat(player, "Đây là server cày chay nhé con !");
                                    break;
                                }
                                player.inventory.gem = 1000;
                                Service.gI().sendMoney(player);
                                Service.gI().sendThongBao(player, "Bạn vừa nhận được 2M ngọc xanh");
                                break;
                            case 4:
                                if (!player.getSession().actived) {
                                    if (player.getSession().coinBar >= 10000) {
                                        player.getSession().actived = true;
                                        if (PlayerDAO.subcoinBar(player, 10000))
                                            ;
                                        Item tv = ItemService.gI().createNewItem((short) 457, 40);
                                        player.inventory.ruby += 10000;
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        InventoryServiceNew.gI().addItemBag(player, tv);
                                        Service.getInstance().sendMoney(player);
                                        Service.gI().sendThongBao(player,
                                                "|7|Kích hoạt thành công, bạn nhận được thêm 40 Thỏi Vàng và 10k Hồng Ngọc");
                                    } else {
                                        this.npcChat(player, "Có cái dái tiền mà mở thành viên...!");
                                    }
                                } else {
                                    this.npcChat(player, "Mày đã mở mõm thành viên rồi!");

                                }
                                break;
                            case 5:
                                Input.gI().createFormGiftCode(player);
                                break;

                        }
                    }
                }

            }

        };
    }

    public static Npc bulmaQK(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Cậu cần trang bị gì cứ đến chỗ tôi nhé", "Cửa\nhàng");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:// Shop
                                if (player.gender == ConstPlayer.TRAI_DAT) {
                                    ShopServiceNew.gI().opendShop(player, "BUNMA", true);
                                } else {
                                    this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                            "Xin lỗi cưng, chị chỉ bán đồ cho người Trái Đất", "Đóng");
                                }
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc dende(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        if (player.idNRNM != -1) {
                            if (player.zone.map.mapId == 7) {
                                this.createOtherMenu(player, 1,
                                        "Ồ, ngọc rồng namếc, bạn thật là may mắn\nnếu tìm đủ 7 viên sẽ được Rồng Thiêng Namếc ban cho điều ước",
                                        "Hướng\ndẫn\nGọi Rồng", "Gọi rồng", "Từ chối");
                            }
                        } else {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                    "Anh cần trang bị gì cứ đến chỗ em nhé", "Cửa\nhàng");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:// Shop
                                if (player.gender == ConstPlayer.NAMEC) {
                                    ShopServiceNew.gI().opendShop(player, "DENDE", true);
                                } else {
                                    this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                            "Xin lỗi mày, tao chỉ bán đồ cho dân tộc Namếc", "Đóng");
                                }
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == 1) {
                        if (player.zone.map.mapId == 7 && player.idNRNM != -1) {
                            if (player.idNRNM == 353) {
                                long timeRemaining = 30 * 1000;
                                NgocRongNamecService.gI().tOpenNrNamec = System.currentTimeMillis() + timeRemaining;
                                NgocRongNamecService.gI().firstNrNamec = true;
                                NgocRongNamecService.gI().timeNrNamec = 0;
                                NgocRongNamecService.gI().doneDragonNamec();
                                NgocRongNamecService.gI().initNgocRongNamec((byte) 1);
                                NgocRongNamecService.gI().reInitNrNamec((long) timeRemaining - 1000);
                                SummonDragon.gI().summonNamec(player);
                            } else {
                                Service.gI().sendThongBao(player, "Anh phải có viên ngọc rồng Namếc 1 sao");
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc appule(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Ngươi cần trang bị gì cứ đến chỗ ta nhé", "Cửa\nhàng");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:// Shop
                                if (player.gender == ConstPlayer.XAYDA) {
                                    ShopServiceNew.gI().opendShop(player, "APPULE", true);
                                } else {
                                    this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                            "Về hành tinh hạ đẳng của ngươi mà mua đồ cùi nhé. Tại đây ta chỉ bán đồ cho người Xayda thôi",
                                            "Đóng");
                                }
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc drDrief(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player pl) {
                if (canOpenNpc(pl)) {
                    if (this.mapId == 84) {
                        this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                "Tàu Vũ Trụ của ta có thể đưa cậu đến hành tinh khác chỉ trong 3 giây. Cậu muốn đi đâu?",
                                pl.gender == ConstPlayer.TRAI_DAT ? "Đến\nTrái Đất"
                                        : pl.gender == ConstPlayer.NAMEC ? "Đến\nNamếc" : "Đến\nXayda");
                    } else if (!TaskService.gI().checkDoneTaskTalkNpc(pl, this)) {
                        if (pl.playerTask.taskMain.id == 7) {
                            NpcService.gI().createTutorial(pl, this.avartar, "Hãy lên đường cứu đứa bé nhà tôi\n"
                                    + "Chắc bây giờ nó đang sợ hãi lắm rồi");
                        } else {
                            this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                    "Tàu Vũ Trụ của ta có thể đưa cậu đến hành tinh khác chỉ trong 3 giây. Cậu muốn đi đâu?",
                                    "Đến\nNamếc", "Đến\nXayda", "Siêu thị");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 84) {
                        ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 24, -1, -1);
                    } else if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:
                                ChangeMapService.gI().changeMapBySpaceShip(player, 25, -1, -1);
                                break;
                            case 1:
                                ChangeMapService.gI().changeMapBySpaceShip(player, 26, -1, -1);
                                break;
                            case 2:
                                ChangeMapService.gI().changeMapBySpaceShip(player, 84, -1, -1);
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc cargo(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player pl) {
                if (canOpenNpc(pl)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(pl, this)) {
                        if (pl.playerTask.taskMain.id == 7) {
                            NpcService.gI().createTutorial(pl, this.avartar, "Hãy lên đường cứu đứa bé nhà tôi\n"
                                    + "Chắc bây giờ nó đang sợ hãi lắm rồi");
                        } else {
                            this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                    "Tàu Điện của tao sẽ đưa chúng mày đi chơi gái chỉ trong 3 giây. MÀY muốn đi đâu?",
                                    "Đến\nTrái Đất", "Đến\nXayda", "Siêu BÍM");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:
                                ChangeMapService.gI().changeMapBySpaceShip(player, 24, -1, -1);
                                break;
                            case 1:
                                ChangeMapService.gI().changeMapBySpaceShip(player, 26, -1, -1);
                                break;
                            case 2:
                                ChangeMapService.gI().changeMapBySpaceShip(player, 84, -1, -1);
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc cui(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            private final int COST_FIND_BOSS = 50000000;

            @Override
            public void openBaseMenu(Player pl) {
                if (canOpenNpc(pl)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(pl, this)) {
                        if (pl.playerTask.taskMain.id == 7) {
                            NpcService.gI().createTutorial(pl, this.avartar, "Hãy lên đường cứu đứa bé nhà tôi\n"
                                    + "Chắc bây giờ nó đang sợ hãi lắm rồi");
                        } else {
                            switch (this.mapId) {
                                case 19:
                                    int taskId = TaskService.gI().getIdTask(pl);
                                    switch (taskId) {
                                        case ConstTask.TASK_19_0:
                                            this.createOtherMenu(pl, ConstNpc.MENU_FIND_KUKU,
                                                    "Đội quân của Fide đang ở Thung lũng Nappa, ta sẽ đưa ngươi đến đó",
                                                    "Đến chỗ\nKuku\n(" + Util.numberToMoney(COST_FIND_BOSS) + " vàng)",
                                                    "Đến Cold", "Đến\nNappa", "Từ chối");
                                            break;
                                        case ConstTask.TASK_19_1:
                                            this.createOtherMenu(pl, ConstNpc.MENU_FIND_MAP_DAU_DINH,
                                                    "Đội quân của Fide đang ở Thung lũng Nappa, ta sẽ đưa ngươi đến đó",
                                                    "Đến chỗ\nMập đầu đinh\n(" + Util.numberToMoney(COST_FIND_BOSS)
                                                            + " vàng)",
                                                    "Đến Cold", "Đến\nNappa", "Từ chối");
                                            break;
                                        case ConstTask.TASK_19_2:
                                            this.createOtherMenu(pl, ConstNpc.MENU_FIND_RAMBO,
                                                    "Đội quân của Fide đang ở Thung lũng Nappa, ta sẽ đưa ngươi đến đó",
                                                    "Đến chỗ\nRambo\n(" + Util.numberToMoney(COST_FIND_BOSS) + " vàng)",
                                                    "Đến Cold", "Đến\nNappa", "Từ chối");
                                            break;
                                        default:
                                            this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                                    "Đội quân của Fide đang ở Thung lũng Nappa, ta sẽ đưa ngươi đến đó",
                                                    "Đến Cold", "Đến\nNappa", "Từ chối");

                                            break;
                                    }
                                    break;
                                case 68:
                                    this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                            "Ngươi muốn về Thành Phố bababa", "Ok em zai", "Có cc");
                                    break;
                                default:
                                    this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                            "Tàu vũ trụ Xayda của bố sử dụng công nghệ mới nhất, "
                                                    + "có thể đưa các con đi đi bất kỳ đâu, chỉ cần trả tiền là được.",
                                            "Đến\nTrái Đất", "Đến\nNamếc", "Siêu thị");
                                    break;
                            }
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 26) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 24, -1, -1);
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 25, -1, -1);
                                    break;
                                case 2:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 84, -1, -1);
                                    break;
                            }
                        }
                    }
                    if (this.mapId == 19) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    if (TaskService.gI().getIdTask(player) < ConstTask.TASK_21_0) {
                                        Service.gI().sendThongBao(player, "Hãy làm nhiệm vụ trước");
                                        return;
                                    } else {
                                        ChangeMapService.gI().changeMapBySpaceShip(player, 109, -1, 295);
                                        break;
                                    }
                                case 1:
                                    if (TaskService.gI().getIdTask(player) < ConstTask.TASK_18_0) {
                                        Service.gI().sendThongBao(player, "Hãy làm nhiệm vụ trước");
                                        return;
                                    } else {
                                        ChangeMapService.gI().changeMapBySpaceShip(player, 68, -1, 90);
                                        break;
                                    }
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_FIND_KUKU) {
                            switch (select) {
                                case 0:
                                    Boss boss = BossManager.gI().getBossByType(BossType.KUKU);
                                    if (boss != null && !boss.isDie()) {
                                        if (player.inventory.gold >= COST_FIND_BOSS) {
                                            Zone z = MapService.gI().getMapCanJoin(player, boss.zone.map.mapId,
                                                    boss.zone.zoneId);
                                            if (z != null && z.getNumOfPlayers() < z.maxPlayer) {
                                                player.inventory.gold -= COST_FIND_BOSS;
                                                ChangeMapService.gI().changeMap(player, boss.zone, boss.location.x,
                                                        boss.location.y);
                                                Service.gI().sendMoney(player);
                                            } else {
                                                Service.gI().sendThongBao(player, "Khu vực đang full.");
                                            }
                                        } else {
                                            Service.gI().sendThongBao(player, "Không đủ vàng, còn thiếu "
                                                    + Util.numberToMoney(COST_FIND_BOSS - player.inventory.gold)
                                                    + " vàng");
                                        }
                                        break;
                                    }
                                    Service.gI().sendThongBao(player, "Chết mẹ rồi...");
                                    break;
                                case 1:
                                    if (TaskService.gI().getIdTask(player) < ConstTask.TASK_29_0) {
                                        Service.gI().sendThongBao(player, "Hãy làm nhiệm vụ trước");
                                        return;
                                    } else {
                                        ChangeMapService.gI().changeMapBySpaceShip(player, 109, -1, 295);
                                        break;
                                    }
                                case 2:
                                    if (TaskService.gI().getIdTask(player) < ConstTask.TASK_18_0) {
                                        Service.gI().sendThongBao(player, "Hãy làm nhiệm vụ trước");
                                        return;
                                    } else {
                                        ChangeMapService.gI().changeMapBySpaceShip(player, 68, -1, 90);
                                        break;
                                    }
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_FIND_MAP_DAU_DINH) {
                            switch (select) {
                                case 0:
                                    Boss boss = BossManager.gI().getBossByType(BossType.MAP_DAU_DINH);
                                    if (boss != null && !boss.isDie()) {
                                        if (player.inventory.gold >= COST_FIND_BOSS) {
                                            Zone z = MapService.gI().getMapCanJoin(player, boss.zone.map.mapId,
                                                    boss.zone.zoneId);
                                            if (z != null && z.getNumOfPlayers() < z.maxPlayer) {
                                                player.inventory.gold -= COST_FIND_BOSS;
                                                ChangeMapService.gI().changeMap(player, boss.zone, boss.location.x,
                                                        boss.location.y);
                                                Service.gI().sendMoney(player);
                                            } else {
                                                Service.gI().sendThongBao(player, "Khu vực đang full.");
                                            }
                                        } else {
                                            Service.gI().sendThongBao(player, "Không đủ vàng, còn thiếu "
                                                    + Util.numberToMoney(COST_FIND_BOSS - player.inventory.gold)
                                                    + " vàng");
                                        }
                                        break;
                                    }
                                    Service.gI().sendThongBao(player, "Chết mẹ rồi...");
                                    break;
                                case 1:
                                    if (TaskService.gI().getIdTask(player) < ConstTask.TASK_29_0) {
                                        Service.gI().sendThongBao(player, "Hãy làm nhiệm vụ trước");
                                        return;
                                    } else {
                                        ChangeMapService.gI().changeMapBySpaceShip(player, 109, -1, 295);
                                        break;
                                    }
                                case 2:
                                    if (TaskService.gI().getIdTask(player) < ConstTask.TASK_18_0) {
                                        Service.gI().sendThongBao(player, "Hãy làm nhiệm vụ trước");
                                        return;
                                    } else {
                                        ChangeMapService.gI().changeMapBySpaceShip(player, 68, -1, 90);
                                        break;
                                    }
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_FIND_RAMBO) {
                            switch (select) {
                                case 0:
                                    Boss boss = BossManager.gI().getBossByType(BossType.RAMBO);
                                    if (boss != null && !boss.isDie()) {
                                        if (player.inventory.gold >= COST_FIND_BOSS) {
                                            Zone z = MapService.gI().getMapCanJoin(player, boss.zone.map.mapId,
                                                    boss.zone.zoneId);
                                            if (z != null && z.getNumOfPlayers() < z.maxPlayer) {
                                                player.inventory.gold -= COST_FIND_BOSS;
                                                ChangeMapService.gI().changeMap(player, boss.zone, boss.location.x,
                                                        boss.location.y);
                                                Service.gI().sendMoney(player);
                                            } else {
                                                Service.gI().sendThongBao(player, "Khu vực đang full.");
                                            }
                                        } else {
                                            Service.gI().sendThongBao(player, "Không đủ vàng, còn thiếu "
                                                    + Util.numberToMoney(COST_FIND_BOSS - player.inventory.gold)
                                                    + " vàng");
                                        }
                                        break;
                                    }
                                    Service.gI().sendThongBao(player, "Chết mẹ rồi...");
                                    break;
                                case 1:
                                    if (TaskService.gI().getIdTask(player) < ConstTask.TASK_29_0) {
                                        Service.gI().sendThongBao(player, "Hãy làm nhiệm vụ trước");
                                        return;
                                    } else {
                                        ChangeMapService.gI().changeMapBySpaceShip(player, 109, -1, 295);
                                        break;
                                    }
                                case 2:
                                    if (TaskService.gI().getIdTask(player) < ConstTask.TASK_18_0) {
                                        Service.gI().sendThongBao(player, "Hãy làm nhiệm vụ trước");
                                        return;
                                    } else {
                                        ChangeMapService.gI().changeMapBySpaceShip(player, 68, -1, 90);
                                        break;
                                    }
                            }
                        }
                    }
                    if (this.mapId == 68) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 19, -1, 1100);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc genshin(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Xin chào, cửa hàng genshin?",
                            "Cửa Hàng", "Đến Teyvat");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 211) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: // shop
                                    ShopServiceNew.gI().opendShop(player, "GENSHIN", false);
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 202, -1, 1100);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc santa(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Xin chào, ta có bán những vật phẩm độc lạ mà Trái đất mình ta có!",
                            "Cửa Hàng", "Hỗ Trợ");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5 || this.mapId == 13 || this.mapId == 20) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: // shop
                                    ShopServiceNew.gI().opendShop(player, "SANTA", false);
                                    break;
                                case 1:
                                    ShopServiceNew.gI().opendShop(player, "SANTA_RUBY", false);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc obito(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Xin chào, CHỖ TAO CHỈ BÁN MA TÚY ĐÁ CHO DÂN CHƠI?",
                            "Cửa Hàng");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 211) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: // shop
                                    ShopServiceNew.gI().opendShop(player, "OBITO", false);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc fa(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    createOtherMenu(player, ConstNpc.BASE_MENU,
                            "lewlew đồ fa không có ny",
                            "Cửa Hàng");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 109) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: // shop
                                    ShopServiceNew.gI().opendShop(player, "FA", false);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc ngokhong(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    createOtherMenu(player, ConstNpc.BASE_MENU,
                            "lewlew đồ fa không có ny",
                            "Cửa Hàng");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 122) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: // shop
                                    ShopServiceNew.gI().opendShop(player, "NK", false);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc vip(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Xin lỗi cửa hàng đã đóng cửa. Chúng tôi đã bị buộc đóng cửa do cư dân biểu tình vì đây là server cày chay.",
                            "Từ chối");
                }
                // if (canOpenNpc(player)) {
                // createOtherMenu(player, ConstNpc.BASE_MENU,
                // "Xin chào, shop bán toàn đồ vip",
                // "Cửa Hàng");
                // }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5 || this.mapId == 13 || this.mapId == 20) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                // case 0: // shop
                                // ShopServiceNew.gI().opendShop(player, "VIP", false);
                                // break;
                                // case 1:
                                // ShopServiceNew.gI().opendShop(player, "SANTA_RUBY", false);
                                // break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc uron(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player pl) {
                if (canOpenNpc(pl)) {
                    ShopServiceNew.gI().opendShop(pl, "URON", false);
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {

                }
            }
        };
    }

    public static Npc baHatMit(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Xin chào, ta có thể giúp gì?",
                                "Ép sao\ntrang bị", "Pha lê\nhóa trang bị", "Nâng Cấp SKH",
                                "Nâng Cấp SKH Vip");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.EP_SAO_TRANG_BI);
                                    break;
                                case 1:
                                    CombineServiceNew.gI().openTabCombine(player,
                                            CombineServiceNew.PHA_LE_HOA_TRANG_BI);
                                    break;
                                case 2:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.DAP_SET_KICH_HOAT);
                                    break;
                                case 3:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_SKH_VIP);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_START_COMBINE) {
                            switch (player.combineNew.typeCombine) {
                                case CombineServiceNew.EP_SAO_TRANG_BI:
                                case CombineServiceNew.PHA_LE_HOA_TRANG_BI:
                                case CombineServiceNew.NANG_CAP_SKH_VIP:
                                case CombineServiceNew.DAP_SET_KICH_HOAT:
                                    switch (select) {
                                        case 0:
                                            if (player.combineNew.typeCombine == CombineServiceNew.PHA_LE_HOA_TRANG_BI) {
                                                player.combineNew.quantities = 1;
                                            }
                                            break;
                                        case 1:
                                            if (player.combineNew.typeCombine == CombineServiceNew.PHA_LE_HOA_TRANG_BI) {
                                                player.combineNew.quantities = 10;
                                            }
                                            break;
                                        case 2:
                                            if (player.combineNew.typeCombine == CombineServiceNew.PHA_LE_HOA_TRANG_BI) {
                                                player.combineNew.quantities = 100;
                                            }
                                            break;
                                    }
                                    CombineServiceNew.gI().startCombine(player);
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc baHatMit2(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 42 || this.mapId == 43 || this.mapId == 44 || this.mapId == 84) {

                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Mày tìm bố có việc gì?",
                                "Cửa hàng\nBùa", "Nâng cấp\nVật phẩm",
                                "Nhập\nNgọc Rồng");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 42 || this.mapId == 43 || this.mapId == 44 || this.mapId == 84) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: // shop bùa
                                    createOtherMenu(player, ConstNpc.MENU_OPTION_SHOP_BUA,
                                            "Bùa của ta rất lợi hại, nhìn ngươi yếu đuối thế này, chắc muốn mua bùa để "
                                                    + "mạnh mẽ à, mua không ta bán cho, xài rồi lại thích cho mà xem.",
                                            "Bùa\n1 giờ", "Bùa\n8 giờ", "Bùa\n1 tháng");
                                    break;
                                case 1:// nâng cấp vật phẩm
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_VAT_PHAM);
                                    break;
                                case 2:// nhập ngọc rồng
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NHAP_NGOC_RONG);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPTION_SHOP_BUA) {
                            switch (select) {
                                case 0:
                                    ShopServiceNew.gI().opendShop(player, "BUA_1H", true);
                                    break;
                                case 1:
                                    ShopServiceNew.gI().opendShop(player, "BUA_8H", true);
                                    break;
                                case 2:
                                    ShopServiceNew.gI().opendShop(player, "BUA_1M", true);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_START_COMBINE) {
                            switch (player.combineNew.typeCombine) {
                                case CombineServiceNew.NANG_CAP_VAT_PHAM:
                                case CombineServiceNew.NHAP_NGOC_RONG:

                                    if (select == 0) {
                                        CombineServiceNew.gI().startCombine(player);
                                    }
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc tosukaio(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 50) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Mày tìm bố có việc gì?", "Bông tai\nPorata Cấp 2", "Bông tai\nPorata Cấp 3",
                                "Bông tai\nPorata Cấp 4", "Bông tai\nPorata Cấp 5");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 50) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: // shop bùa
                                    createOtherMenu(player, ConstNpc.MENU_BONG_TAI_2,
                                            "Ngươi muốn làm gì?",
                                            "Nâng Cấp", "Mở Chỉ Số");
                                    break;
                                case 1: // shop bùa
                                    createOtherMenu(player, ConstNpc.MENU_BONG_TAI_3,
                                            "Ngươi muốn làm gì?",
                                            "Nâng Cấp", "Mở Chỉ Số");
                                    break;
                                case 2: // shop bùa
                                    createOtherMenu(player, ConstNpc.MENU_BONG_TAI_4,
                                            "Ngươi muốn làm gì?",
                                            "Nâng Cấp", "Mở Chỉ Số");
                                    break;
                                case 3: // shop bùa
                                    createOtherMenu(player, ConstNpc.MENU_BONG_TAI_5,
                                            "Ngươi muốn làm gì?",
                                            "Nâng Cấp", "Mở Chỉ Số");
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_BONG_TAI_2) {
                            switch (select) {
                                case 0: // nâng cấp bông tai
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_BONG_TAI);
                                    break;
                                case 1: //
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.MO_CHI_SO_BONG_TAI);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_BONG_TAI_3) {
                            switch (select) {
                                case 0: // nâng cấp bông tai
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_BONG_TAI3);
                                    break;
                                case 1: //
                                    CombineServiceNew.gI().openTabCombine(player,
                                            CombineServiceNew.MO_CHI_SO_BONG_TAI3);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_BONG_TAI_4) {
                            switch (select) {
                                case 0: // nâng cấp bông tai
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_BONG_TAI4);
                                    break;
                                case 1: //
                                    CombineServiceNew.gI().openTabCombine(player,
                                            CombineServiceNew.MO_CHI_SO_BONG_TAI4);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_BONG_TAI_5) {
                            switch (select) {
                                case 0: // nâng cấp bông tai
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_BONG_TAI5);
                                    break;
                                case 1: //
                                    CombineServiceNew.gI().openTabCombine(player,
                                            CombineServiceNew.MO_CHI_SO_BONG_TAI5);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_START_COMBINE) {
                            switch (player.combineNew.typeCombine) {
                                case CombineServiceNew.NANG_CAP_BONG_TAI:
                                case CombineServiceNew.MO_CHI_SO_BONG_TAI:
                                case CombineServiceNew.NANG_CAP_BONG_TAI3:
                                case CombineServiceNew.MO_CHI_SO_BONG_TAI3:
                                case CombineServiceNew.NANG_CAP_BONG_TAI4:
                                case CombineServiceNew.MO_CHI_SO_BONG_TAI4:
                                case CombineServiceNew.NANG_CAP_BONG_TAI5:
                                case CombineServiceNew.MO_CHI_SO_BONG_TAI5:

                                    if (select == 0) {
                                        CombineServiceNew.gI().startCombine(player);
                                    }
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc ruongDo(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    InventoryServiceNew.gI().sendItemBox(player);
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {

                }
            }
        };
    }

    public static Npc duongtank(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (mapId == 0) {
                        this.createOtherMenu(player, 0, "Ngũ Hành chảo x2 Tnsm\nHỗ trợ cho Ae Từ\b|1|100Ty trở lên",
                                "OK", "Từ chối");
                    }
                    if (mapId == 123) {
                        this.createOtherMenu(player, 0, "Mày Có Muốn Về Với Mẹ Không?", "OK", "Từ chối");

                    }

                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (select) {
                        case 0:
                            if (mapId == 0) {
                                if (player.nPoint.power <= 100000000000L) {
                                    Service.gI().sendThongBao(player, "Sức mạnh bạn không phù hợp để qua map!");
                                    return;
                                }
                                ChangeMapService.gI().changeMapInYard(player, 123, -1, 174);
                            }
                            if (mapId == 123) {
                                ChangeMapService.gI().changeMapInYard(player, 0, -1, 469);
                            }
                    }
                }
            }
        };
    }

    public static Npc dauThan(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    player.magicTree.openMenuTree();
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstNpc.MAGIC_TREE_NON_UPGRADE_LEFT_PEA:
                            if (select == 0) {
                                player.magicTree.harvestPea();
                            } else if (select == 1) {
                                if (player.magicTree.level == 10) {
                                    player.magicTree.fastRespawnPea();
                                } else {
                                    player.magicTree.showConfirmUpgradeMagicTree();
                                }
                            } else if (select == 2) {
                                player.magicTree.fastRespawnPea();
                            }
                            break;
                        case ConstNpc.MAGIC_TREE_NON_UPGRADE_FULL_PEA:
                            if (select == 0) {
                                player.magicTree.harvestPea();
                            } else if (select == 1) {
                                player.magicTree.showConfirmUpgradeMagicTree();
                            }
                            break;
                        case ConstNpc.MAGIC_TREE_CONFIRM_UPGRADE:
                            if (select == 0) {
                                player.magicTree.upgradeMagicTree();
                            }
                            break;
                        case ConstNpc.MAGIC_TREE_UPGRADE:
                            if (select == 0) {
                                player.magicTree.fastUpgradeMagicTree();
                            } else if (select == 1) {
                                player.magicTree.showConfirmUnuppgradeMagicTree();
                            }
                            break;
                        case ConstNpc.MAGIC_TREE_CONFIRM_UNUPGRADE:
                            if (select == 0) {
                                player.magicTree.unupgradeMagicTree();
                            }
                            break;
                    }
                }
            }
        };
    }

    public static Npc calick(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            private final byte COUNT_CHANGE = 50;
            private int count;

            private void changeMap() {
                if (this.mapId != 102) {
                    count++;
                    if (this.count >= COUNT_CHANGE) {
                        count = 0;
                        this.map.npcs.remove(this);
                        Map map = MapService.gI().getMapForCalich();
                        this.mapId = map.mapId;
                        this.cx = Util.nextInt(100, map.mapWidth - 100);
                        this.cy = map.yPhysicInTop(this.cx, 0);
                        this.map = map;
                        this.map.npcs.add(this);
                    }
                }
            }

            @Override
            public void openBaseMenu(Player player) {
                player.iDMark.setIndexMenu(ConstNpc.BASE_MENU);
                if (TaskService.gI().getIdTask(player) < ConstTask.TASK_22_0) {
                    Service.gI().hideWaitDialog(player);
                    Service.gI().sendThongBao(player, "Bạn Chưa Hoàn Thành Nhiệm Vụ");
                    return;
                }
                if (this.mapId != player.zone.map.mapId) {
                    Service.gI().sendThongBao(player, "Calích đã rời khỏi map!");
                    Service.gI().hideWaitDialog(player);
                    return;
                }

                if (this.mapId == 102) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Chào chú, cháu có thể giúp gì?",
                            "Kể\nChuyện", "Quay về\nQuá khứ");
                } else {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Chào chú, cháu có thể giúp gì?", "Kể\nChuyện", "Đi đến\nTương lai", "Từ chối");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (this.mapId == 102) {
                    if (player.iDMark.isBaseMenu()) {
                        if (select == 0) {
                            // kể chuyện
                            NpcService.gI().createTutorial(player, this.avartar, ConstNpc.CALICK_KE_CHUYEN);
                        } else if (select == 1) {
                            // về quá khứ
                            ChangeMapService.gI().goToQuaKhu(player);
                        }
                    }
                } else if (player.iDMark.isBaseMenu()) {
                    if (select == 0) {
                        // kể chuyện
                        NpcService.gI().createTutorial(player, this.avartar, ConstNpc.CALICK_KE_CHUYEN);
                    } else if (select == 1) {
                        // đến tương lai
                        // changeMap();
                        if (TaskService.gI().getIdTask(player) >= ConstTask.TASK_22_0) {
                            ChangeMapService.gI().goToTuongLai(player);
                        }
                    } else {
                        Service.gI().sendThongBao(player, "Bạn Chưa Hoàn Thành Nhiệm Vụ");
                    }
                }
            }
        };
    }

    public static Npc jaco(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 24 || this.mapId == 25 || this.mapId == 26) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Gô Tên, Calich và Monaka đang gặp chuyện ở hành tinh Potaufeu \n Hãy đến đó ngay",
                                "Đến \nPotaufeu");
                    } else if (this.mapId == 139) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Người muốn trở về?", "Quay về", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 24 || this.mapId == 25 || this.mapId == 26) {
                        if (player.getSession().player.nPoint.power >= 80000000000L) {

                            ChangeMapService.gI().goToPotaufeu(player);
                        } else {
                            this.npcChat(player, "Bạn chưa đủ 80 tỷ sức mạnh để vào!");
                        }
                    } else if (this.mapId == 139) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                // về trạm vũ trụ
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 24 + player.gender, -1, -1);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc thuongDe(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 45) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Con muốn làm gì nào", "Đến Kaio", "Quay số\nmay mắn", "Đóng");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 45) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 48, -1, 354);
                                    break;
                                case 1:
                                    this.createOtherMenu(player, ConstNpc.MENU_CHOOSE_LUCKY_ROUND,
                                            "Con muốn làm gì nào?", "Quay bằng\nHồng Ngọc",
                                            "Rương phụ\n("
                                                    + (player.inventory.itemsBoxCrackBall.size()
                                                            - InventoryServiceNew.gI().getCountEmptyListItem(
                                                                    player.inventory.itemsBoxCrackBall))
                                                    + " món)",
                                            "Xóa hết\ntrong rương", "Đóng");
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_CHOOSE_LUCKY_ROUND) {
                            switch (select) {
                                case 0:
                                    LuckyRound.gI().openCrackBallUI(player, LuckyRound.USING_RUBY);
                                    break;
                                case 1:
                                    ShopServiceNew.gI().opendShop(player, "ITEMS_LUCKY_ROUND", true);
                                    break;
                                case 2:
                                    NpcService.gI().createMenuConMeo(player,
                                            ConstNpc.CONFIRM_REMOVE_ALL_ITEM_LUCKY_ROUND, this.avartar,
                                            "Con có chắc muốn xóa hết vật phẩm trong rương phụ? Sau khi xóa "
                                                    + "sẽ không thể khôi phục!",
                                            "Đồng ý", "Hủy bỏ");
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc thanVuTru(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 48) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Con muốn làm gì nào", "Di chuyển");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 48) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    this.createOtherMenu(player, ConstNpc.MENU_DI_CHUYEN,
                                            "Con muốn đi đâu?", "Về\nthần điện", "Thánh địa\nKaio", "Từ chối");
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_DI_CHUYEN) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 45, -1, 354);
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMap(player, 50, -1, 318, 336);
                                    break;
                            }
                        }
                    }
                }
            }

        };
    }

    public static Npc kibit(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    // Kaio
                    if (this.mapId == 50) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Đến\nKaio", "Từ chối");
                    }
                    // Babidi map cuoi
                    if (this.mapId == 114) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Từ chối");
                    }

                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 50) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMap(player, 48, -1, 354, 240);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc osin(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 50) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Đến\nKaio", "Đến\nhành tinh\nBill", "Từ chối");
                    } // DHVT
                    else if (this.mapId == 52) {
                        try {
                            MapMaBu.gI().setTimeJoinMapMaBu();
                            if (this.mapId == 52) {
                                long now = System.currentTimeMillis();
                                if (now > MapMaBu.TIME_OPEN_MABU && now < MapMaBu.TIME_CLOSE_MABU) {
                                    this.createOtherMenu(player, ConstNpc.MENU_OPEN_MMB, "Đại chiến Ma Bư đã mở, "
                                            + "ngươi có muốn tham gia không?",
                                            "Hướng dẫn\nthêm", "Tham gia", "Từ chối");
                                } else {
                                    this.createOtherMenu(player, ConstNpc.MENU_NOT_OPEN_MMB,
                                            "Ta có thể giúp gì cho ngươi?", "Hướng dẫn", "Từ chối");
                                }

                            }
                        } catch (Exception ex) {
                            Logger.error("Lỗi mở menu osin");
                        }

                    } else if (this.mapId >= 114 && this.mapId < 120 && this.mapId != 116) {
                        // custom-drsylas
                        // this.createOtherMenu(player, ConstNpc.GO_UPSTAIRS_MENU, "Ta có thể giúp gì
                        // cho ngươi ?",
                        // "Lên Tầng!", "Quay về", "Từ chối");
                        if (player.fightMabu.pointMabu >= player.fightMabu.POINT_MAX) {
                            this.createOtherMenu(player, ConstNpc.GO_UPSTAIRS_MENU, "Ta có thể giúp gì cho ngươi ?",
                                    "Lên Tầng!", "Quay về", "Từ chối");
                        } else {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                    "Quay về", "Từ chối");
                        }
                    } else if (this.mapId == 120) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Quay về", "Từ chối");
                    } else {
                        super.openBaseMenu(player);
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 50) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMap(player, 48, -1, 354, 240);
                                    break;
                                case 1:
                                    if (player.getSession().player.nPoint.power >= 60000000000L) {
                                        // Service.gI().sendMoney(player);
                                        ChangeMapService.gI().changeMap(player, 154, -1, 200, 312);
                                    } else {
                                        this.npcChat(player, "Bạn chưa đủ 60 tỷ để vào");
                                    }
                                    break;
                            }
                        }
                    } else if (this.mapId == 52) {
                        switch (player.iDMark.getIndexMenu()) {
                            case ConstNpc.MENU_REWARD_MMB:
                                break;
                            case ConstNpc.MENU_OPEN_MMB:
                                if (select == 0) {
                                    NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_MAP_MA_BU);
                                } else if (select == 1) {
                                    // if (!player.getSession().actived) {
                                    // Service.gI().sendThongBao(player, "Vui lòng kích hoạt tài khoản để sử dụng
                                    // chức năng này");
                                    // } else
                                    ChangeMapService.gI().changeMap(player, 114, -1, 318, 336);
                                }
                                break;
                            case ConstNpc.MENU_NOT_OPEN_BDW:
                                if (select == 0) {
                                    NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_MAP_MA_BU);
                                }
                                break;
                        }
                    } else if (this.mapId >= 114 && this.mapId < 120 && this.mapId != 116) {
                        if (player.iDMark.getIndexMenu() == ConstNpc.GO_UPSTAIRS_MENU) {
                            if (select == 0) {
                                player.fightMabu.clear();
                                ChangeMapService.gI().changeMap(player, this.map.mapIdNextMabu((short) this.mapId), -1,
                                        this.cx, this.cy);
                            } else if (select == 1) {
                                ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 21, 0, -1);
                            }
                        } else {
                            if (select == 0) {
                                ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 21, 0, -1);
                            }
                        }
                    } else if (this.mapId == 120) {
                        if (player.iDMark.getIndexMenu() == ConstNpc.BASE_MENU) {
                            if (select == 0) {
                                ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 21, 0, -1);
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc dai(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 48) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Zeno đang nổi điên ngươi có dám ngăn ngài ấy lại không ?",
                                "Đồng ý", "Từ chối");
                    } else if (this.mapId == 177) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Quay về", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 48) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 177, -1, 456);
                                    break;
                            }
                        }
                    } else if (this.mapId == 177) {
                        if (player.iDMark.getIndexMenu() == ConstNpc.BASE_MENU) {
                            if (select == 0) {
                                ChangeMapService.gI().changeMapBySpaceShip(player, 48, -1, 456);
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc tele(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 212) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Quay về", "Từ chối");
                    } else if (this.mapId == 216) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Quay về", "Từ chối");
                    } else if (this.mapId == 202) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Quay về", "Từ chối");
                    } else if (this.mapId == 5) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Đến khe nứt không gian không?",
                                "Đồng ý", "Từ chối");
                    } else if (this.mapId == 211) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Đến \n Ngoại Môn", "Quay về \n đảo", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 212) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 211, -1, 456);
                                    break;
                            }
                        }
                    } else if (this.mapId == 216) {
                        if (player.iDMark.getIndexMenu() == ConstNpc.BASE_MENU) {
                            if (select == 0) {
                                ChangeMapService.gI().changeMapBySpaceShip(player, 211, -1, 456);
                            }
                        }
                    } else if (this.mapId == 202) {
                        if (player.iDMark.getIndexMenu() == ConstNpc.BASE_MENU) {
                            if (select == 0) {
                                ChangeMapService.gI().changeMapBySpaceShip(player, 211, -1, 456);
                            }
                        }
                    } else if (this.mapId == 5) {
                        if (player.iDMark.getIndexMenu() == ConstNpc.BASE_MENU) {
                            if (select == 0) {
                                ChangeMapService.gI().changeMapBySpaceShip(player, 211, -1, 456);
                            }
                        }
                    } else if (this.mapId == 211) {
                        if (player.iDMark.getIndexMenu() == ConstNpc.BASE_MENU) {
                            if (select == 0) {
                                ChangeMapService.gI().changeMapBySpaceShip(player, 171, -1, 456);
                            }
                            if (select == 1) {
                                ChangeMapService.gI().changeMapBySpaceShip(player, 5, -1, 456);
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc nami(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 211) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Đến đảo SkyPiea không?",
                                "Cửa Hàng", "Đồng ý", "Từ chối");
                    } else if (this.mapId == 217) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Quay về", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 211) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: // shop
                                    ShopServiceNew.gI().opendShop(player, "NAMI", false);
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 217, -1, 456);
                                    break;
                            }
                        }
                    } else if (this.mapId == 217) {
                        if (player.iDMark.getIndexMenu() == ConstNpc.BASE_MENU) {
                            if (select == 0) {
                                ChangeMapService.gI().changeMapBySpaceShip(player, 211, -1, 456);
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc dn(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 211) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ngươi có muốn xuống địa ngục không ?",
                                "Cửa Hàng", "Đồng ý", "Từ chối");
                    } else if (this.mapId == 204) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Quay về", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 211) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: // shop
                                    ShopServiceNew.gI().opendShop(player, "DN", false);
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 204, -1, 456);
                                    break;
                            }
                        }
                    } else if (this.mapId == 204) {
                        if (player.iDMark.getIndexMenu() == ConstNpc.BASE_MENU) {
                            if (select == 0) {
                                ChangeMapService.gI().changeMapBySpaceShip(player, 211, -1, 456);
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc tan(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 211) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Săn Quỷ", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 211) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 216, -1, 336);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc fu(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 154) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Về thánh địa", "Đến\nhành tinh\nngục tù", "Từ chối");
                    } else if (this.mapId == 155) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Quay về", "Từ chối");
                    }
                }

            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 154) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMap(player, 50, -1, 318, 336);
                                    break;
                                case 1:

                                    ChangeMapService.gI().changeMap(player, 155, -1, 111, 792);
                                    break;
                            }
                        }
                    } else if (this.mapId == 155) {
                        if (player.iDMark.isBaseMenu()) {
                            if (select == 0) {
                                ChangeMapService.gI().changeMap(player, 154, -1, 200, 312);
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc champa(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 154) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Về thánh địa", "Vùng Đất Hủy Diệt", "Từ chối");
                    } else if (this.mapId == 146) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Quay về", "Từ chối");
                    }
                }

            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 154) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMap(player, 50, -1, 318, 336);
                                    break;
                                case 1:
                                    if (TaskService.gI().getIdTask(player) < ConstTask.TASK_29_0) {
                                        Service.gI().sendThongBao(player, "Hãy làm nhiệm vụ trước");
                                        return;
                                    } else {
                                        ChangeMapService.gI().changeMapBySpaceShip(player, 146, -1, 295);
                                        break;
                                    }
                            }
                        }
                    } else if (this.mapId == 146) {
                        if (player.iDMark.isBaseMenu()) {
                            if (select == 0) {
                                ChangeMapService.gI().changeMap(player, 154, -1, 200, 312);
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc linhCanh(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (player.clan == null) {
                        this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Chỉ tiếp các bang hội, miễn tiếp khách vãng lai", "Đóng");
                        return;
                    }
                    // if (player.clan.getMembers().size() < DoanhTrai.N_PLAYER_CLAN) {
                    // this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    // "Bang hội phải có ít nhất 5 thành viên mới có thể mở", "Đóng");
                    // return;
                    // }

                    // if (player.clan.doanhTrai != null &&
                    // player.clanMember.getNumDateFromJoinTimeToToday() >= 2 &&
                    // !player.clan.doanhTrai_haveGone) {
                    if (player.clan.doanhTrai != null) {
                        createOtherMenu(player, ConstNpc.MENU_JOIN_DOANH_TRAI,
                                "Bang hội của ngươi đang đánh trại độc nhãn\n"
                                        + "Thời gian còn lại là "
                                        + TimeUtil.getMinLeft(player.clan.doanhTrai.getLastTimeOpen(),
                                                DoanhTrai.TIME_DOANH_TRAI / 1000)
                                        + " Phút" + ". Ngươi có muốn tham gia không?",
                                "Tham gia", "Không", "Hướng\ndẫn\nthêm");
                        return;
                    }
                    int nPlSameClan = 0;
                    for (Player pl : player.zone.getPlayers()) {
                        if (!pl.equals(player) && pl.clan != null
                                && pl.clan.equals(player.clan) && pl.location.x >= 1285
                                && pl.location.x <= 1645) {
                            nPlSameClan++;
                        }
                    }
                    // if (nPlSameClan < DoanhTrai.N_PLAYER_MAP) {
                    // createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    // "Ngươi phải có ít nhất " + DoanhTrai.N_PLAYER_MAP + " đồng đội cùng bang đứng
                    // gần mới có thể\nvào\n"
                    // + "tuy nhiên ta khuyên ngươi nên đi cùng với 1 người để khỏi chết.\n"
                    // + "Hahaha.", "OK", "Hướng\ndẫn\nthêm");
                    // return;
                    // }
                    // if (player.clanMember.getNumDateFromJoinTimeToToday() < 2 ||
                    // (player.clan.doanhTrai != null &&
                    // player.clanMember.getNumDateFromJoinTimeToToday() < 2)) {
                    // createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    // "Doanh trại chỉ cho phép những người ở trong bang trên 2 ngày. Hẹn ngươi quay
                    // lại vào lúc khác",
                    // "OK", "Hướng\ndẫn\nthêm");
                    // return;
                    // }

                    if (!player.clan.doanhTrai_haveGone) {
                        createOtherMenu(player, ConstNpc.MENU_JOIN_DOANH_TRAI,
                                "Hôm nay bang hội của ngươi chưa vào trại lần nào. Ngươi có muốn vào\n"
                                        + "không?\nĐể vào, ta khuyên ngươi nên có 3-4 người cùng bang đi cùng",
                                "Vào\n(miễn phí)", "Không", "Hướng\ndẫn\nthêm");
                        return;
                    } else if (player.clan.doanhTrai_haveGone) {
                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Bang hội của ngươi đã đi trại lúc "
                                        + TimeUtil.formatTime(player.clan.doanhTrai_lastTimeOpen, "HH:mm:ss")
                                        + " hôm nay. Người mở\n"
                                        + "(" + player.clan.doanhTrai_playerOpen + "). Hẹn ngươi quay lại vào ngày mai",
                                "OK", "Hướng\ndẫn\nthêm");
                        return;
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstNpc.MENU_JOIN_DOANH_TRAI:
                            if (select == 0) {
                                try {
                                    player.clan.doanhTrai_haveGone = (new java.sql.Date(
                                            player.clan.doanhTrai_lastTimeOpen))
                                            .getDay() == (new java.sql.Date(System.currentTimeMillis())).getDay();
                                    DoanhTraiService.gI().joinDoanhTrai(player);
                                } catch (Exception ex) {
                                    java.util.logging.Logger.getLogger(NpcFactory.class.getName()).log(Level.SEVERE,
                                            null, ex);
                                }
                            } else if (select == 2) {
                                NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_DOANH_TRAI);
                            }
                            break;
                        case ConstNpc.IGNORE_MENU:
                            if (select == 1) {
                                NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_DOANH_TRAI);
                            }
                            break;
                    }
                }
            }
        };
    }

    public static Npc quaTrung(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            private final int COST_AP_TRUNG_NHANH = 1000000000;

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == (21 + player.gender)) {
                        player.mabuEgg.sendMabuEgg();
                        if (player.mabuEgg.getSecondDone() != 0) {
                            this.createOtherMenu(player, ConstNpc.CAN_NOT_OPEN_EGG, "Bư bư bư...",
                                    "Hủy bỏ\ntrứng", "Ấp nhanh\n" + Util.numberToMoney(COST_AP_TRUNG_NHANH) + " vàng",
                                    "Đóng");
                        } else {
                            this.createOtherMenu(player, ConstNpc.CAN_OPEN_EGG, "Bư bư bư...", "Nở", "Hủy bỏ\ntrứng",
                                    "Đóng");
                        }
                    }
                    if (this.mapId == 7) {
                        player.billEgg.sendBillEgg();
                        if (player.billEgg.getSecondDone() != 0) {
                            this.createOtherMenu(player, ConstNpc.CAN_NOT_OPEN_EGG, "Bư bư bư...",
                                    "Hủy bỏ\ntrứng", "Ấp nhanh\n" + Util.numberToMoney(COST_AP_TRUNG_NHANH) + " vàng",
                                    "Đóng");
                        } else {
                            this.createOtherMenu(player, ConstNpc.CAN_OPEN_EGG, "Bư bư bư...", "Nở", "Hủy bỏ\ntrứng",
                                    "Đóng");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == (21 + player.gender)) {
                        switch (player.iDMark.getIndexMenu()) {
                            case ConstNpc.CAN_NOT_OPEN_EGG:
                                if (select == 0) {
                                    this.createOtherMenu(player, ConstNpc.CONFIRM_DESTROY_EGG,
                                            "Bạn có chắc chắn muốn hủy bỏ trứng Mabư?", "Đồng ý", "Từ chối");
                                } else if (select == 1) {
                                    if (player.inventory.gold >= COST_AP_TRUNG_NHANH) {
                                        player.inventory.gold -= COST_AP_TRUNG_NHANH;
                                        player.mabuEgg.timeDone = 0;
                                        Service.gI().sendMoney(player);
                                        player.mabuEgg.sendMabuEgg();
                                    } else {
                                        Service.gI().sendThongBao(player,
                                                "Bạn không đủ vàng để thực hiện, còn thiếu "
                                                        + Util.numberToMoney(
                                                                (COST_AP_TRUNG_NHANH - player.inventory.gold))
                                                        + " vàng");
                                    }
                                }
                                break;
                            case ConstNpc.CAN_OPEN_EGG:
                                switch (select) {
                                    case 0:
                                        this.createOtherMenu(player, ConstNpc.CONFIRM_OPEN_EGG,
                                                "Bạn có chắc chắn cho trứng nở?\n"
                                                        + "Đệ tử của bạn sẽ được thay thế bằng đệ Mabư",
                                                "Đệ mabư\nTrái Đất", "Đệ mabư\nNamếc", "Đệ mabư\nXayda", "Từ chối");
                                        break;
                                    case 1:
                                        this.createOtherMenu(player, ConstNpc.CONFIRM_DESTROY_EGG,
                                                "Bạn có chắc chắn muốn hủy bỏ trứng Mabư?", "Đồng ý", "Từ chối");
                                        break;
                                }
                                break;
                            case ConstNpc.CONFIRM_OPEN_EGG:
                                switch (select) {
                                    case 0:
                                        player.mabuEgg.openEgg(ConstPlayer.TRAI_DAT);
                                        break;
                                    case 1:
                                        player.mabuEgg.openEgg(ConstPlayer.NAMEC);
                                        break;
                                    case 2:
                                        player.mabuEgg.openEgg(ConstPlayer.XAYDA);
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            case ConstNpc.CONFIRM_DESTROY_EGG:
                                if (select == 0) {
                                    player.mabuEgg.destroyEgg();
                                }
                                break;
                        }
                    }

                }
            }
        };
    }

    public static Npc quocVuong(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                this.createOtherMenu(player, ConstNpc.BASE_MENU,
                        "Ngươi muốn mở giới hạn sức mạnh cho ai?",
                        "Cho\n sư phụ", "Cho\n đệ tử", "từ chối");
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:
                                if (player.nPoint.limitPower < NPoint.MAX_LIMIT) {
                                    this.createOtherMenu(player, ConstNpc.OPEN_POWER_MYSEFT,
                                            "Nâng giới hạn sức mạnh sư phụ đến "
                                                    + Util.numberToMoney(player.nPoint.getPowerNextLimit()),
                                            "Nâng\ngiới hạn\nsức mạnh",
                                            "Nâng ngay\n"
                                                    + Util.numberToMoney(OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER)
                                                    + " vàng",
                                            "Đóng");
                                } else {
                                    this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                            "giới hạn sức mạnh sư phụ đã đạt mức tối đa",
                                            "Đóng");
                                }
                                break;
                            case 1:
                                if (player.pet != null) {
                                    if (player.pet.nPoint.limitPower < NPoint.MAX_LIMIT) {
                                        this.createOtherMenu(player, ConstNpc.OPEN_POWER_PET,
                                                "Nâng giới hạn sức mạnh đệ tử đến "
                                                        + Util.numberToMoney(player.pet.nPoint.getPowerNextLimit()),
                                                "Nâng ngay\n" + Util.numberToMoney(
                                                        OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER) + " vàng",
                                                "Đóng");
                                    } else {
                                        this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                                "giới hạn sức mạnh đệ tử đã đạt mức tối đa",
                                                "Đóng");
                                    }
                                } else {
                                    Service.gI().sendThongBao(player, "Không thể thực hiện");
                                }
                                // giới hạn đệ tử
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.OPEN_POWER_MYSEFT) {
                        switch (select) {
                            case 0:
                                OpenPowerService.gI().openPowerBasic(player);
                                break;
                            case 1:
                                if (player.inventory.gold >= OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER) {
                                    if (OpenPowerService.gI().openPowerSpeed(player)) {
                                        player.inventory.gold -= OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER;
                                        Service.gI().sendMoney(player);
                                    }
                                } else {
                                    Service.gI().sendThongBao(player,
                                            "Hết vàng rồi thằng óc chó "
                                                    + Util.numberToMoney((OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER
                                                            - player.inventory.gold))
                                                    + " vàng");
                                }
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.OPEN_POWER_PET) {
                        if (select == 0) {
                            if (player.inventory.gold >= OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER) {
                                if (OpenPowerService.gI().openPowerSpeed(player.pet)) {
                                    player.inventory.gold -= OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER;
                                    Service.gI().sendMoney(player);
                                }
                            } else {
                                Service.gI().sendThongBao(player,
                                        "Hết vàng rồi thằng óc chó "
                                                + Util.numberToMoney((OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER
                                                        - player.inventory.gold))
                                                + " vàng");
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc mai(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 102) {
                        if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, "Cậu bé muốn mua gì nào?", "Cửa hàng");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 102) {
                        if (player.iDMark.isBaseMenu()) {
                            if (select == 0) {
                                ShopServiceNew.gI().opendShop(player, "BUNMA_FUTURE", true);
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc rongOmega(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    BlackBallWar.gI().setTime();
                    if (this.mapId == 24 || this.mapId == 25 || this.mapId == 26) {
                        try {
                            long now = System.currentTimeMillis();
                            if (now > BlackBallWar.TIME_OPEN && now < BlackBallWar.TIME_CLOSE) {
                                this.createOtherMenu(player, ConstNpc.MENU_OPEN_BDW,
                                        "Vào đại chiến ngọc rồng đen để buồi to hơn, "
                                                + "Có mún vô không thằng đần?",
                                        "Hướng dẫn\nthêm", "ZOZO", "Từ chối");
                            } else {
                                String[] optionRewards = new String[7];
                                int index = 0;
                                for (int i = 0; i < 7; i++) {
                                    if (player.rewardBlackBall.timeOutOfDateReward[i] > System.currentTimeMillis()) {
                                        String quantily = player.rewardBlackBall.quantilyBlackBall[i] > 1
                                                ? "x" + player.rewardBlackBall.quantilyBlackBall[i] + " "
                                                : "";
                                        optionRewards[index] = quantily + (i + 1) + " sao";
                                        index++;
                                    }
                                }
                                if (index != 0) {
                                    String[] options = new String[index + 1];
                                    for (int i = 0; i < index; i++) {
                                        options[i] = optionRewards[i];
                                    }
                                    options[options.length - 1] = "Từ chối";
                                    this.createOtherMenu(player, ConstNpc.MENU_REWARD_BDW,
                                            "Ngươi có một vài phần thưởng ngọc "
                                                    + "rồng sao đen đây!",
                                            options);
                                } else {
                                    this.createOtherMenu(player, ConstNpc.MENU_NOT_OPEN_BDW,
                                            "Ta có thể giúp gì cho ngươi?", "Hướng dẫn", "Từ chối");
                                }
                            }
                        } catch (Exception ex) {
                            Logger.error("Lỗi mở menu rồng Omega");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstNpc.MENU_REWARD_BDW:
                            player.rewardBlackBall.getRewardSelect((byte) select);
                            break;
                        case ConstNpc.MENU_OPEN_BDW:
                            if (select == 0) {
                                NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_BLACK_BALL_WAR);
                            } else if (select == 1) {
                                // if (!player.getSession().actived) {
                                // Service.gI().sendThongBao(player, "Vui lòng kích hoạt tài khoản để sử dụng
                                // chức năng này");
                                //
                                // } else
                                player.iDMark.setTypeChangeMap(ConstMap.CHANGE_BLACK_BALL);
                                ChangeMapService.gI().openChangeMapTab(player);
                            }
                            break;
                        case ConstNpc.MENU_NOT_OPEN_BDW:
                            if (select == 0) {
                                NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_BLACK_BALL_WAR);
                            }
                            break;
                    }
                }
            }

        };
    }

    public static Npc rong1_to_7s(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isHoldBlackBall()) {
                        this.createOtherMenu(player, ConstNpc.MENU_PHU_HP, "Ta có thể giúp gì cho ngươi?",
                                "Phù ĐỂ CHO BUỒI NÓ TO", "Từ chối");
                    } else {
                        if (BossManager.gI().existBossOnPlayer(player)
                                || player.zone.items.stream()
                                        .anyMatch(itemMap -> ItemMapService.gI().isBlackBall(itemMap.itemTemplate.id))
                                || player.zone.getPlayers().stream().anyMatch(p -> p.iDMark.isHoldBlackBall())) {
                            this.createOtherMenu(player, ConstNpc.MENU_OPTION_GO_HOME, "Ta có thể giúp gì cho ngươi?",
                                    "Về nhà", "Từ chối");
                        } else {
                            this.createOtherMenu(player, ConstNpc.MENU_OPTION_GO_HOME, "Ta có thể giúp gì cho ngươi?",
                                    "Về nhà", "Từ chối", "Gọi BOSS");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.getIndexMenu() == ConstNpc.MENU_PHU_HP) {
                        if (select == 0) {
                            this.createOtherMenu(player, ConstNpc.MENU_OPTION_PHU_HP,
                                    "Ta sẽ giúp ngươi tăng HP lên mức kinh hoàng, ngươi chọn đi",
                                    "x3 HP\n" + Util.numberToMoney(BlackBallWar.COST_X3) + " vàng",
                                    "x5 HP\n" + Util.numberToMoney(BlackBallWar.COST_X5) + " vàng",
                                    "x7 HP\n" + Util.numberToMoney(BlackBallWar.COST_X7) + " vàng",
                                    "Từ chối");
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPTION_GO_HOME) {
                        if (select == 0) {
                            ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 21, -1, 250);
                        } else if (select == 2) {
                            BossManager.gI().callBoss(player, mapId);
                        } else if (select == 1) {
                            this.npcChat(player, "Để ta xem ngươi trụ được bao lâu");
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPTION_PHU_HP) {
                        if (player.effectSkin.xHPKI > 1) {
                            Service.gI().sendThongBao(player, "Bạn đã được phù hộ rồi!");
                            return;
                        }
                        switch (select) {
                            case 0:
                                BlackBallWar.gI().xHPKI(player, BlackBallWar.X3);
                                break;
                            case 1:
                                BlackBallWar.gI().xHPKI(player, BlackBallWar.X5);
                                break;
                            case 2:
                                BlackBallWar.gI().xHPKI(player, BlackBallWar.X7);
                                break;
                            case 3:
                                this.npcChat(player, "Để ta xem ngươi trụ được bao lâu");
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc npcThienSu64(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!player.setClothes.huydietClothers) {
                        this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Ngươi Hãy Mặc đủ 5 món Huỷ Diệt",
                                "Đóng");
                    } else {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Đã tìm đủ nguyên liệu cho tôi chưa?\n Tôi sẽ giúp cậu mạnh lên kha khá đấy!",
                                "Cửa hàng thiên sứ", "Từ Chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (this.mapId) {
                        case 48:
                            switch (player.iDMark.getIndexMenu()) {
                                case ConstNpc.BASE_MENU:
                                    if (select == 0) {
                                        ShopServiceNew.gI().opendShop(player, "THIEN_SU", true);
                                        break;
                                    }
                                    break;
                            }
                            break;
                    }
                }
            }
        };
    }

    public static Npc meokarin(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Cậu muốn gì ở tôi? ",
                            "Không");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstNpc.BASE_MENU:
                            if (select == 0) {
                                if (!player.getSession().bg) {
                                    player.getSession().bg = true;
                                    if (PlayerDAO.subcoinBar1(player, 0))
                                        ;
                                }
                            }
                            break;
                    }
                }
            }
        };
    }

    public static Npc npcThienSu65(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (this.mapId == 154) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Đã tìm đủ nguyên liệu cho tôi chưa?\n Tôi sẽ giúp cậu mạnh lên kha khá đấy!",
                            "Chế Tạo trang bị thiên sứ", "Từ Chối");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu() && this.mapId == 154) {
                        if (select == 0) {
                            CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.CHE_TAO_TRANG_BI_TS);
                        }

                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_DAP_DO) {
                        if (select == 0) {
                            CombineServiceNew.gI().startCombine(player);
                        }
                    }
                }
            }

        };
    }

    public static Npc bill(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Đói bụng quá.. ngươi mang cho ta 99 phần đồ ăn,\nta sẽ cho một món đồ Hủy Diệt.\n Nếu tâm trạng ta vui ngươi có thể nhận được trang bị\ntăng đến 15%!",
                            "OK");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (this.mapId) {
                        case 48:
                            switch (player.iDMark.getIndexMenu()) {
                                case ConstNpc.BASE_MENU:
                                    if (select == 0) {
                                        Item pudding = InventoryServiceNew.gI().findItemBag(player, 663);
                                        Item xucxich = InventoryServiceNew.gI().findItemBag(player, 664);
                                        Item kemdau = InventoryServiceNew.gI().findItemBag(player, 665);
                                        Item mily = InventoryServiceNew.gI().findItemBag(player, 666);
                                        Item sushi = InventoryServiceNew.gI().findItemBag(player, 667);
                                        if (pudding != null && pudding.quantity >= 99
                                                || xucxich != null && xucxich.quantity >= 99
                                                || kemdau != null && kemdau.quantity >= 99
                                                || mily != null && mily.quantity >= 99
                                                || sushi != null && sushi.quantity >= 99) {
                                            ShopServiceNew.gI().opendShop(player, "BILL", true);
                                            break;
                                        } else {
                                            this.npcChat(player, "Còn không mau đem x99 thức ăn đến cho ta !!");
                                            break;
                                        }
                                    }
                            }
                            break;
                    }
                }
            }
        };
    }

    public static Npc boMong(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 47 || this.mapId == 84) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Xin chào, cậu muốn tôi giúp gì?", "Nhiệm vụ\nhàng ngày", "Nhiệm vụ\nthành tích",
                                "Từ chối");
                    }
                    // if (this.mapId == 47) {
                    // this.createOtherMenu(player, ConstNpc.BASE_MENU,
                    // "Xin chào, cậu muốn tôi giúp gì?", "Từ chối");
                    // }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 47 || this.mapId == 84) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    if (player.playerTask.sideTask.template != null) {
                                        String npcSay = "Nhiệm vụ hiện tại: " + player.playerTask.sideTask.getName()
                                                + " ("
                                                + player.playerTask.sideTask.getLevel() + ")"
                                                + "\nHiện tại đã hoàn thành: " + player.playerTask.sideTask.count + "/"
                                                + player.playerTask.sideTask.maxCount + " ("
                                                + player.playerTask.sideTask.getPercentProcess()
                                                + "%)\nSố nhiệm vụ còn lại trong ngày: "
                                                + player.playerTask.sideTask.leftTask + "/" + ConstTask.MAX_SIDE_TASK;
                                        this.createOtherMenu(player, ConstNpc.MENU_OPTION_PAY_SIDE_TASK,
                                                npcSay, "Trả nhiệm\nvụ", "Hủy nhiệm\nvụ");
                                    } else {
                                        this.createOtherMenu(player, ConstNpc.MENU_OPTION_LEVEL_SIDE_TASK,
                                                "Tôi có vài nhiệm vụ theo cấp bậc, "
                                                        + "sức cậu có thể làm được cái nào?",
                                                "Dễ", "Bình thường", "Khó", "Siêu khó", "Địa ngục", "Từ chối");
                                    }
                                    break;
                                case 1:
                                    player.achievement.Show();
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPTION_LEVEL_SIDE_TASK) {
                            switch (select) {
                                case 0:
                                case 1:
                                case 2:
                                case 3:
                                case 4:
                                    TaskService.gI().changeSideTask(player, (byte) select);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPTION_PAY_SIDE_TASK) {
                            switch (select) {
                                case 0:
                                    TaskService.gI().paySideTask(player);
                                    break;
                                case 1:
                                    TaskService.gI().removeSideTask(player);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc mavuong(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 153) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Xin chào, tôi có thể giúp gì cho cậu?", "Tây thánh địa", "Từ chối");
                    } else if (this.mapId == 156) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Người muốn trở về?", "Quay về", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 153) {
                        if (player.iDMark.isBaseMenu()) {
                            if (select == 0) {
                                // đến tay thanh dia
                                ChangeMapService.gI().changeMapBySpaceShip(player, 156, -1, 360);
                            }
                        }
                    } else if (this.mapId == 156) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                // về lanh dia bang hoi
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 153, -1, 432);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc tapion(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (mapId == 19) {
                        this.createOtherMenu(player, 0, "Ngươi đã trên 80Ty có muốn săn đệ mabu không?", "Có", "Đéo");
                    }
                    if (mapId == 201) {
                        this.createOtherMenu(player, 0, "Mày Có Muốn Về Với Mẹ Không?", "OK", "Từ chối");

                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (select) {
                        case 0:
                            if (mapId == 19) {
                                if (player.nPoint.power < 79999999999L) {
                                    Service.gI().sendThongBao(player, "Sức mạnh bạn không phù hợp để qua map!");
                                    return;
                                }
                                ChangeMapService.gI().changeMapInYard(player, 201, -1, 174);
                            }
                            if (mapId == 201) {
                                ChangeMapService.gI().changeMapInYard(player, 19, -1, 469);
                            }

                    }
                }
            }
        };
    }

    public static Npc GhiDanh(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            String[] menuselect = new String[] {};

            @Override
            public void openBaseMenu(Player pl) {
                if (canOpenNpc(pl)) {
                    if (this.mapId == 52) {
                        this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                "Đại hội võ thuật lần thứ 23\nDiễn ra bất kể ngày đêm,ngày nghỉ ngày lễ\nPhần thưởng vô cùng quý giá\nNhanh chóng tham gia nào",
                                "Đại Hội\nVõ Thuật\nLần thứ\n23", "Từ chối");

                    } else if (this.mapId == 129) {
                        int goldchallenge = pl.goldChallenge;
                        if (pl.levelWoodChest == 0) {
                            menuselect = new String[] { "Thi đấu\n" + Util.numberToMoney(goldchallenge) + " vàng",
                                    "Về\nĐại Hội\nVõ Thuật" };
                        } else {
                            menuselect = new String[] { "Thi đấu\n" + Util.numberToMoney(goldchallenge) + " vàng",
                                    "Nhận thưởng\nRương cấp\n" + pl.levelWoodChest, "Về\nĐại Hội\nVõ Thuật" };
                        }
                        this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                "Đại hội võ thuật lần thứ 23\nDiễn ra bất kể ngày đêm,ngày nghỉ ngày lễ\nPhần thưởng vô cùng quý giá\nNhanh chóng tham gia nào",
                                menuselect, "Từ chối");

                    } else {
                        super.openBaseMenu(pl);
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 52) {
                        switch (select) {
                            case 0:
                                ChangeMapService.gI().changeMapNonSpaceship(player, 129, player.location.x, 360);
                                break;
                        }
                    } else if (this.mapId == 129) {
                        int goldchallenge = player.goldChallenge;
                        if (player.levelWoodChest == 0) {
                            switch (select) {
                                case 0:
                                    if (InventoryServiceNew.gI().finditemWoodChest(player)) {
                                        if (player.inventory.gold >= goldchallenge) {
                                            MartialCongressService.gI().startChallenge(player);
                                            Service.getInstance().releaseCooldownSkill(player);
                                            player.inventory.gold -= (goldchallenge);
                                            PlayerService.gI().sendInfoHpMpMoney(player);
                                            player.goldChallenge += 2000000;
                                        } else {
                                            Service.getInstance().sendThongBao(player,
                                                    "Không đủ vàng, còn thiếu "
                                                            + Util.numberToMoney(goldchallenge - player.inventory.gold)
                                                            + " vàng");
                                        }
                                    } else {
                                        Service.getInstance().sendThongBao(player, "Hãy mở rương báu vật trước");
                                    }
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMapNonSpaceship(player, 52, player.location.x, 336);
                                    break;
                            }
                        } else {
                            switch (select) {
                                case 0:
                                    if (InventoryServiceNew.gI().finditemWoodChest(player)) {
                                        if (player.inventory.gold >= goldchallenge) {
                                            MartialCongressService.gI().startChallenge(player);
                                            player.inventory.gold -= (goldchallenge);
                                            PlayerService.gI().sendInfoHpMpMoney(player);
                                            player.goldChallenge += 2000000;
                                        } else {
                                            Service.getInstance().sendThongBao(player,
                                                    "Không đủ vàng, còn thiếu "
                                                            + Util.numberToMoney(goldchallenge - player.inventory.gold)
                                                            + " vàng");
                                        }
                                    } else {
                                        Service.getInstance().sendThongBao(player, "Hãy mở rương báu vật trước");
                                    }
                                    break;
                                case 1:
                                    if (!player.receivedWoodChest) {
                                        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                                            Item it = ItemService.gI().createNewItem((short) 570);
                                            it.itemOptions.add(new Item.ItemOption(72, player.levelWoodChest));
                                            it.itemOptions.add(new Item.ItemOption(30, 0));
                                            it.createTime = System.currentTimeMillis();
                                            InventoryServiceNew.gI().addItemBag(player, it);
                                            InventoryServiceNew.gI().sendItemBags(player);

                                            player.receivedWoodChest = true;
                                            player.levelWoodChest = 0;
                                            Service.getInstance().sendThongBao(player, "Bạn nhận được rương gỗ");
                                        } else {
                                            this.npcChat(player, "Hành trang đã đầy");
                                        }
                                    } else {
                                        Service.getInstance().sendThongBao(player,
                                                "Mỗi ngày chỉ có thể nhận rương báu 1 lần");
                                    }
                                    break;
                                case 2:
                                    ChangeMapService.gI().changeMapNonSpaceship(player, 52, player.location.x, 336);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc monaito(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 7) {
                        this.createOtherMenu(player, 0,
                                "Chào bạn tôi sẽ đưa bạn đến hành tinh Cereal?", "Đồng ý", "Từ chối");
                    }
                    if (this.mapId == 170) {
                        this.createOtherMenu(player, 0,
                                "Ta ở đây để đưa con về", "Về Làng Mori", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 7) {
                        if (player.iDMark.getIndexMenu() == 0) { //
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 170, -1, 264);
                                    break; // den hanh tinh cereal
                            }
                        }
                    }
                    if (this.mapId == 170) {
                        if (player.iDMark.getIndexMenu() == 0) { //
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 7, -1, 432);
                                    break; // quay ve

                            }
                        }
                    }
                }
            }
        };
    }

    private static Npc kyGui(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    createOtherMenu(player, 0,
                            "Cửa hàng chúng tôi chuyên mua bán hàng hiệu, hàng độc, cảm ơn bạn đã ghé thăm.",
                            "Hướng\ndẫn\nthêm", "Mua bán\nKý gửi", "Từ chối");
                }
            }

            @Override
            public void confirmMenu(Player pl, int select) {
                if (canOpenNpc(pl)) {
                    switch (select) {
                        case 0:
                            Service.gI().sendPopUpMultiLine(pl, tempId, avartar,
                                    "Cửa hàng chuyên nhận ký gửi mua bán vật phẩm\bChỉ với 1 thỏi vàng\bGiá trị ký gửi 1-100k thỏi vàng hoặc 1-1tr hồng ngọc\bMột người bán, vạn người mua, mại dô, mại dô");
                            break;
                        case 1:
                            if (!pl.getSession().actived) {
                                Service.gI().sendThongBao(pl, "Vui lòng kích hoạt tài khoản để sử dụng chức năng này");
                            } else {
                                ShopKyGuiService.gI().openShopKyGui(pl);
                            }
                            break;

                    }
                }
            }
        };
    }

    public static Npc createNPC(int mapId, int status, int cx, int cy, int tempId) {
        NpcTemplate npcTemplate = Manager.NPC_TEMPLATES.get(tempId);
        if (npcTemplate == null) {
            Logger.error("NPC template không tồn tại hoặc bị null: tempId=" + tempId + "\n");
            return null;
        }
        int avatar = npcTemplate.avatar;
        try {
            switch (tempId) {
                case ConstNpc.THO_DAI_CA:
                    return TrungThu.ThoDaiCa(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.TRUNG_THU:
                    return TrungThu.NpcTrungThu(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.GHI_DANH:
                    return GhiDanh(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.CUA_HANG_KY_GUI:
                    return kyGui(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.POPO:
                    return popo(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.POTAGE:
                    return poTaGe(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.QUY_LAO_KAME:
                    return quyLaoKame(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.TRUONG_LAO_GURU:
                    return truongLaoGuru(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.VUA_VEGETA:
                    return vuaVegeta(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.ONG_GOHAN:
                case ConstNpc.ONG_MOORI:
                case ConstNpc.ONG_PARAGUS:
                    return ongGohan_ongMoori_ongParagus(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.BUNMA:
                    return bulmaQK(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.CHAN_MENH:
                    return chanmenh(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.DENDE:
                    return dende(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.APPULE:
                    return appule(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.DR_DRIEF:
                    return drDrief(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.CARGO:
                    return cargo(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.CUI:
                    return cui(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.CHAMPA:
                    return champa(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.SANTA:
                    return santa(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.OBITO:
                    return obito(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.NGO_KHONG:
                    return ngokhong(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.FA:
                    return fa(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.BILL_BI_NGO:
                    return Halloween.BillBiNgo(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.VIP:
                    return vip(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.GENSHIN:
                    return genshin(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.DOC_NHAN:
                    return docNhan(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.URON:
                    return uron(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.BA_HAT_MIT:
                    return baHatMit(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.BA_HAT_MIT2:
                    return baHatMit2(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.RUONG_DO:
                    return ruongDo(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.DAU_THAN:
                    return dauThan(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.CALICK:
                    return calick(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.JACO:
                    return jaco(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.THUONG_DE:
                    return thuongDe(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.GIUMA_DAU_BO:
                    return mavuong(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.Monaito:
                    return monaito(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.TAPION:
                    return tapion(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.THAN_VU_TRU:
                    return thanVuTru(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.KIBIT:
                    return kibit(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.OSIN:
                    return osin(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.TELE:
                    return tele(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.DAI:
                    return dai(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.NAMI:
                    return nami(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.DN:
                    return dn(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.TAN:
                    return tan(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.FU:
                    return fu(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.MAI:
                    return mai(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.KHI_DAU_MOI:
                    return khidaumoi(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.LINH_CANH:
                    return linhCanh(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.QUA_TRUNG:
                    return quaTrung(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.QUOC_VUONG:
                    return quocVuong(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.RONG_OMEGA:
                    return rongOmega(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.RONG_1S:
                case ConstNpc.RONG_2S:
                case ConstNpc.RONG_3S:
                case ConstNpc.RONG_4S:
                case ConstNpc.RONG_5S:
                case ConstNpc.RONG_6S:
                case ConstNpc.RONG_7S:
                    return rong1_to_7s(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.NPC_64:
                    return npcThienSu64(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.HAHA:
                    return gapthu(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.MEO_THAN_TAI:
                    return meothantai(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.MEO_THAN_TAI1:
                    return meothantai1(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.THAN_MEO_KARIN:
                    return meokarin(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.VADOS:
                    return npcThienSu65(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.Yardrat:
                    return Yardrat(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.BILL:
                    return bill(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.BO_MONG:
                    return boMong(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.DUONG_TANG:
                    return duongtank(mapId, status, cx, cy, tempId, avatar);

                case ConstNpc.TO_SU_KAIO:
                    return tosukaio(mapId, status, cx, cy, tempId, avatar);
                default:
                    return new Npc(mapId, status, cx, cy, tempId, avatar) {
                        @Override
                        public void openBaseMenu(Player player) {
                            if (canOpenNpc(player)) {
                                super.openBaseMenu(player);
                            }
                        }

                        @Override
                        public void confirmMenu(Player player, int select) {
                            if (canOpenNpc(player)) {
                                // ShopService.gI().openShopNormal(player, this, ConstNpc.SHOP_BUNMA_TL_0, 0,
                                // player.gender);
                            }
                        }
                    };
            }
        } catch (Exception e) {
            Logger.logException(NpcFactory.class, e, "Lỗi load npc");
            return null;
        }
    }

    // girlbeo-mark
    public static void createNpcRongThieng() {
        Npc npc = new Npc(-1, -1, -1, -1, ConstNpc.RONG_THIENG, -1) {
            @Override
            public void confirmMenu(Player player, int select) {
                switch (player.iDMark.getIndexMenu()) {
                    case ConstNpc.IGNORE_MENU:

                        break;
                    case ConstNpc.SHENRON_CONFIRM:
                        if (select == 0) {
                            SummonDragon.gI().confirmWish();
                        } else if (select == 1) {
                            SummonDragon.gI().reOpenShenronWishes(player);
                        }
                        break;
                    case ConstNpc.SHENRON_1_1:
                        if (player.iDMark.getIndexMenu() == ConstNpc.SHENRON_1_1
                                && select == SHENRON_1_STAR_WISHES_1.length - 1) {
                            NpcService.gI().createMenuRongThieng(player, ConstNpc.SHENRON_1_2, SHENRON_SAY,
                                    SHENRON_1_STAR_WISHES_2);
                            break;
                        }
                    case ConstNpc.SHENRON_1_2:
                        if (player.iDMark.getIndexMenu() == ConstNpc.SHENRON_1_2
                                && select == SHENRON_1_STAR_WISHES_2.length - 1) {
                            NpcService.gI().createMenuRongThieng(player, ConstNpc.SHENRON_1_1, SHENRON_SAY,
                                    SHENRON_1_STAR_WISHES_1);
                            break;
                        }
                    default:
                        SummonDragon.gI().showConfirmShenron(player, player.iDMark.getIndexMenu(), (byte) select);
                        break;
                }
            }
        };
    }

    public static void createNpcConMeo() {
        Npc npc = new Npc(-1, -1, -1, -1, ConstNpc.CON_MEO, 351) {
            @Override
            public void confirmMenu(Player player, int select) {
                switch (player.iDMark.getIndexMenu()) {
                    case ConstNpc.IGNORE_MENU:

                        break;
                    case ConstNpc.MAKE_MATCH_PVP: // if (player.getSession().actived)
                    {
                        if (Maintenance.isRuning) {
                            break;
                        }
                        PVPService.gI().sendInvitePVP(player, (byte) select);
                        break;
                    }
                    // else {
                    // Service.gI().sendThongBao(player, "|5|VUI LÒNG KÍCH HOẠT TÀI KHOẢN
                    // TẠI\n|7|NROGOD.COM\n|5|ĐỂ MỞ KHÓA TÍNH NĂNG");
                    // break;
                    // }
                    case ConstNpc.MAKE_FRIEND:
                        if (select == 0) {
                            Object playerId = PLAYERID_OBJECT.get(player.id);
                            if (playerId != null) {
                                FriendAndEnemyService.gI().acceptMakeFriend(player,
                                        Integer.parseInt(String.valueOf(playerId)));
                            }
                        }
                        break;
                    case ConstNpc.REVENGE:
                        if (select == 0) {
                            PVPService.gI().acceptRevenge(player);
                        }
                        break;
                    case ConstNpc.TUTORIAL_SUMMON_DRAGON:
                        if (select == 0) {
                            NpcService.gI().createTutorial(player, -1, SummonDragon.SUMMON_SHENRON_TUTORIAL);
                        }
                        break;
                    case ConstNpc.SUMMON_SHENRON:
                        if (select == 0) {
                            NpcService.gI().createTutorial(player, -1, SummonDragon.SUMMON_SHENRON_TUTORIAL);
                        } else if (select == 1) {
                            SummonDragon.gI().summonShenron(player);
                        }
                        break;
                    case ConstNpc.TUTORIAL_SUMMON_DRAGONTRB:// TRB
                        if (select == 0) {
                            NpcService.gI().createTutorial(player, -1, SummonDragon.SUMMON_SHENRON_TRB);
                        }
                        break;
                    case ConstNpc.SUMMON_SHENRONTRB:
                        if (select == 0) {
                            NpcService.gI().createTutorial(player, -1, SummonDragon.SUMMON_SHENRON_TRB);
                        } else if (select == 1) {
                            SummonDragon.gI().summonShenronTRB(player);
                        }
                        break;
                    // ==================== RỒNG XƯƠNG HALLOWEEN ====================
                    case ConstNpc.TUTORIAL_RONG_XUONG:
                        if (select == 0) {
                            NpcService.gI().createTutorial(player, -1, SummonDragon.SUMMON_RONG_XUONG_TUTORIAL);
                        }
                        break;
                    case ConstNpc.SUMMON_RONG_XUONG:
                        if (select == 0) {
                            NpcService.gI().createTutorial(player, -1, SummonDragon.SUMMON_RONG_XUONG_TUTORIAL);
                        } else if (select == 1) {
                            SummonDragon.gI().summonRongXuong(player);
                        }
                        break;
                    case ConstNpc.RONG_XUONG:
                        SummonDragon.gI().showConfirmShenron(player, ConstNpc.RONG_XUONG, (byte) select);
                        break;
                    case ConstNpc.MENU_OPTION_USE_ITEM1105:
                        if (select == 0) {
                            IntrinsicService.gI().sattd(player);
                        } else if (select == 1) {
                            IntrinsicService.gI().satnm(player);
                        } else if (select == 2) {
                            IntrinsicService.gI().setxd(player);
                        }
                        break;
                    case ConstNpc.MENU_OPTION_USE_ITEM2000:
                    case ConstNpc.MENU_OPTION_USE_ITEM2001:
                    case ConstNpc.MENU_OPTION_USE_ITEM2002:
                        try {
                            ItemService.gI().OpenSKH(player, player.iDMark.getIndexMenu(), select);
                        } catch (Exception e) {
                            Logger.error("Lỗi mở hộp quà");
                        }
                        break;
                    case ConstNpc.MENU_OPTION_USE_ITEM2003:
                    case ConstNpc.MENU_OPTION_USE_ITEM2004:
                    case ConstNpc.MENU_OPTION_USE_ITEM2005:
                        try {
                            ItemService.gI().OpenDHD(player, player.iDMark.getIndexMenu(), select);
                        } catch (Exception e) {
                            Logger.error("Lỗi mở hộp quà");
                        }
                        break;
                    case ConstNpc.MENU_OPTION_USE_ITEM736:
                        try {
                            ItemService.gI().OpenDHD(player, player.iDMark.getIndexMenu(), select);
                        } catch (Exception e) {
                            Logger.error("Lỗi mở hộp quà");
                        }
                        break;
                    case ConstNpc.INTRINSIC:
                        if (select == 0) {
                            IntrinsicService.gI().showAllIntrinsic(player);
                        } else if (select == 1) {
                            IntrinsicService.gI().showConfirmOpen(player);
                        } else if (select == 2) {
                            IntrinsicService.gI().showConfirmOpenVip(player);
                        }
                        break;
                    case ConstNpc.CONFIRM_OPEN_INTRINSIC:
                        if (select == 0) {
                            IntrinsicService.gI().open(player);
                        }
                        break;
                    case ConstNpc.CONFIRM_OPEN_INTRINSIC_VIP:
                        if (select == 0) {
                            IntrinsicService.gI().openVip(player);
                        }
                        break;
                    case ConstNpc.CONFIRM_LEAVE_CLAN:
                        if (select == 0) {
                            ClanService.gI().leaveClan(player);
                        }
                        break;
                    case ConstNpc.CONFIRM_NHUONG_PC:
                        if (select == 0) {
                            ClanService.gI().phongPc(player, (int) PLAYERID_OBJECT.get(player.id));
                        }
                        break;
                    case ConstNpc.BAN_PLAYER:
                        if (select == 0) {
                            PlayerService.gI().banPlayer((Player) PLAYERID_OBJECT.get(player.id));
                            Service.gI().sendThongBao(player,
                                    "Ban người chơi " + ((Player) PLAYERID_OBJECT.get(player.id)).name + " thành công");
                        }
                        break;

                    case ConstNpc.BUFF_PET:
                        if (select == 0) {
                            Player pl = (Player) PLAYERID_OBJECT.get(player.id);
                            if (pl.pet == null) {
                                PetService.gI().createNormalPet(pl);
                                Service.gI().sendThongBao(player, "Phát đệ tử cho "
                                        + ((Player) PLAYERID_OBJECT.get(player.id)).name + " thành công");
                            }
                        }
                        break;
                    case ConstNpc.UP_TOP_ITEM:
                        if (select == 0) {
                            if (player.inventory.ruby >= 50 && player.iDMark.getIdItemUpTop() != -1) {
                                ItemKyGui it = ShopKyGuiService.gI().getItemBuy(player.iDMark.getIdItemUpTop());
                                if (it == null || it.isBuy) {
                                    Service.gI().sendThongBao(player, "Vật phẩm không tồn tại hoặc đã được bán");
                                    return;
                                }
                                if (it.player_sell != player.id) {
                                    Service.gI().sendThongBao(player, "Vật phẩm không thuộc quyền sở hữu");
                                    ShopKyGuiService.gI().openShopKyGui(player);
                                    return;
                                }
                                player.inventory.ruby -= 50;
                                Service.gI().sendMoney(player);
                                Service.gI().sendThongBao(player, "Thành công");
                                it.isUpTop += 1;
                                ShopKyGuiService.gI().openShopKyGui(player);
                            } else {
                                Service.gI().sendThongBao(player, "Bạn không đủ hồng ngọc");
                                player.iDMark.setIdItemUpTop(-1);
                            }
                        }
                        break;

                    case ConstNpc.MENU_ADMIN:
                        switch (select) {
                            case 0:
                                if (player.isAdmin()) {
                                    System.out.println(player.name);
                                    // PlayerService.gI().baoTri();
                                    Maintenance.gI().start(15);
                                    System.out.println(player.name);
                                }
                                break;
                            case 1:
                                Input.gI().createFormFindPlayer(player);
                                break;
                            case 2:
                                Input.gI().createFormBuffItemVip(player);
                                break;
                            case 3:
                                if (player.isAdmin()) {
                                    NpcService.gI().createMenuConMeo(player, ConstNpc.MENU_BOSSV1, -1, "|7| Menu Boss",
                                            "CALL\nBoss", "Check\nBoss", "Đóng");
                                }
                                break;
                        }
                        break;
                    case ConstNpc.MENU_BOSSV1:
                        switch (select) {
                            case 0:
                                this.createOtherMenu(player, ConstNpc.CALL_BOSS,
                                        "Chọn Boss?", "Full Cụm\nANDROID", "BLACK", "BROLY", "Cụm\nCell",
                                        "Cụm\nCold", "Cụm\nDoraemon", "Cụm \n FIDE", "Cụm \nBojack", "Cụm\nGINYU",
                                        "Cụm\nNAPPA", "Cụm \nngục tù",
                                        "Cụm \nGSI", "Cụm \n One Piece", "Cụm \n Toboro", "Cụm\n KYB", "Cụm \n Hồn thú",
                                        "Cụm\n Địa Ngục", "Cụm \nHủy Diệt");
                                break;
                            case 1:
                                BossManager.gI().showListBoss(player);
                                break;
                            case 2:

                                break;

                        }
                    case ConstNpc.CALL_BOSS:
                        switch (select) {
                            case 0:
                                BossManager.gI().createBoss(BossType.ANDROID_14);
                                BossManager.gI().createBoss(BossType.DR_KORE);
                                BossManager.gI().createBoss(BossType.KING_KONG);
                                BossManager.gI().createBoss(BossType.SUPER_ANDROID_17);
                                break;
                            case 1:
                                BossManager.gI().createBoss(BossType.BLACK);
                                BossManager.gI().createBoss(BossType.BLACK2);
                                BossManager.gI().createBoss(BossType.ZAMASMAX);
                                BossManager.gI().createBoss(BossType.ZAMASZIN);
                                break;
                            case 2:
                                BossManager.gI().createBoss(BossType.BROLY);

                                break;
                            case 3:
                                BossManager.gI().createBoss(BossType.SIEU_BO_HUNG);
                                BossManager.gI().createBoss(BossType.XEN_BO_HUNG);
                                BossManager.gI().createBoss(BossType.XEN_CON_1);
                                break;
                            case 4:
                                BossManager.gI().createBoss(BossType.COOLER);
                                BossManager.gI().createBoss(BossType.VEGETA);
                                break;
                            case 5:
                                BossManager.gI().createBoss(BossType.DORAEMON);
                                break;
                            case 6:
                                BossManager.gI().createBoss(BossType.FIDE);
                                break;
                            case 7:
                                BossManager.gI().createBoss(BossType.D_TANG);
                                break;
                            case 8:
                                BossManager.gI().createBoss(BossType.TDST);
                                break;
                            case 9:
                                BossManager.gI().createBoss(BossType.KUKU);
                                BossManager.gI().createBoss(BossType.MAP_DAU_DINH);
                                BossManager.gI().createBoss(BossType.RAMBO);
                                break;
                            case 10:
                                BossManager.gI().createBoss(BossType.SONGOKU_TA_AC);
                                break;
                            case 11:
                                BossManager.gI().createBoss(BossType.FIRE);
                                BossManager.gI().createBoss(BossType.WIND);
                                BossManager.gI().createBoss(BossType.ICE);
                                break;
                            case 12:
                                BossManager.gI().createBoss(BossType.Brook);
                                BossManager.gI().createBoss(BossType.Mihawk);
                                BossManager.gI().createBoss(BossType.Along);
                                BossManager.gI().createBoss(BossType.Kaido);
                                BossManager.gI().createBoss(BossType.linh);
                                break;
                            case 13:
                                BossManager.gI().createBoss(BossType.GAS);
                                BossManager.gI().createBoss(BossType.GRANOLA);
                                break;
                            case 14:
                                BossManager.gI().createBoss(BossType.tan);
                                BossManager.gI().createBoss(BossType.nezu);
                                BossManager.gI().createBoss(BossType.zen);
                                BossManager.gI().createBoss(BossType.hino);
                                BossManager.gI().createBoss(BossType.gojo);
                                BossManager.gI().createBoss(BossType.akaza);
                                break;
                            case 15:
                                BossManager.gI().createBoss(BossType.THOR_2);
                                BossManager.gI().createBoss(BossType.MIU);
                                BossManager.gI().createBoss(BossType.HO);
                                break;
                            case 16:
                                BossManager.gI().createBoss(BossType.Janemba);
                                BossManager.gI().createBoss(BossType.pikkon);
                                break;
                            case 17:
                                BossManager.gI().createBoss(BossType.THIEN_SU_VADOS);
                                BossManager.gI().createBoss(BossType.THIEN_SU_WHIS);
                                break;
                        }
                        break;
                    case ConstNpc.menutd:
                        switch (select) {
                            case 0:
                                try {
                                    ItemService.gI().settaiyoken(player);
                                } catch (Exception e) {
                                }
                                break;
                            case 1:
                                try {
                                    ItemService.gI().setgenki(player);
                                } catch (Exception e) {
                                }
                                break;
                            case 2:
                                try {
                                    ItemService.gI().setkamejoko(player);
                                } catch (Exception e) {
                                }
                                break;
                        }
                        break;

                    case ConstNpc.menunm:
                        switch (select) {
                            case 0:
                                try {
                                    ItemService.gI().setgodki(player);
                                } catch (Exception e) {
                                }
                                break;
                            case 1:
                                try {
                                    ItemService.gI().setgoddam(player);
                                } catch (Exception e) {
                                }
                                break;
                            case 2:
                                try {
                                    ItemService.gI().setsummon(player);
                                } catch (Exception e) {
                                }
                                break;
                        }
                        break;

                    case ConstNpc.menuxd:
                        switch (select) {
                            case 0:
                                try {
                                    ItemService.gI().setgodgalick(player);
                                } catch (Exception e) {
                                }
                                break;
                            case 1:
                                try {
                                    ItemService.gI().setmonkey(player);
                                } catch (Exception e) {
                                }
                                break;
                            case 2:
                                try {
                                    ItemService.gI().setgodhp(player);
                                } catch (Exception e) {
                                }
                                break;
                        }
                        break;

                    case ConstNpc.CONFIRM_DISSOLUTION_CLAN:
                        switch (select) {
                            case 0:
                                Clan clan = player.clan;
                                clan.deleteDB(clan.id);
                                Manager.CLANS.remove(clan);
                                player.clan = null;
                                player.clanMember = null;
                                ClanService.gI().sendMyClan(player);
                                ClanService.gI().sendClanId(player);
                                Service.gI().sendThongBao(player, "Đã giải tán bang hội.");
                                break;
                        }
                        break;
                    case ConstNpc.CONFIRM_REMOVE_ALL_ITEM_LUCKY_ROUND:
                        if (select == 0) {
                            for (int i = 0; i < player.inventory.itemsBoxCrackBall.size(); i++) {
                                player.inventory.itemsBoxCrackBall.set(i, ItemService.gI().createItemNull());
                            }
                            player.inventory.itemsBoxCrackBall.clear();
                            Service.gI().sendThongBao(player, "Đã xóa hết vật phẩm trong rương");
                        }
                        break;
                    case ConstNpc.MENU_FIND_PLAYER:
                        Player p = (Player) PLAYERID_OBJECT.get(player.id);
                        if (p != null) {
                            switch (select) {
                                case 0:
                                    if (p.zone != null) {
                                        ChangeMapService.gI().changeMapYardrat(player, p.zone, p.location.x,
                                                p.location.y);
                                    }
                                    break;
                                case 1:
                                    if (p.zone != null) {
                                        ChangeMapService.gI().changeMap(p, player.zone, player.location.x,
                                                player.location.y);
                                    }
                                    break;
                                case 2:
                                    Input.gI().createFormChangeName(player, p);
                                    break;
                                case 3:
                                    String[] selects = new String[] { "Đồng ý", "Hủy" };
                                    NpcService.gI().createMenuConMeo(player, ConstNpc.BAN_PLAYER, -1,
                                            "Bạn có chắc chắn muốn ban " + p.name, selects, p);
                                    break;
                                case 4:
                                    Service.gI().sendThongBao(player, "Kik người chơi " + p.name + " thành công");
                                    Client.gI().getPlayers().remove(p);
                                    Client.gI().kickSession(p.getSession());
                                    break;
                            }
                        }
                        break;
                    case ConstNpc.MENU_EVENT:
                        switch (select) {
                            case 0:
                                Service.gI().sendThongBaoOK(player,
                                        "Điểm sự kiện: " + player.inventory.event + " ngon ngon...");
                                break;
                            case 1:
                                Service.gI().showListTop(player, Manager.topSK);
                                break;
                            case 2:
                                Service.gI().sendThongBao(player, "Sự kiện đã kết thúc...");
                                // NpcService.gI().createMenuConMeo(player, ConstNpc.MENU_GIAO_BONG, -1, "Người
                                // muốn giao bao nhiêu bông...",
                                // "100 bông", "1000 bông", "10000 bông");
                                break;
                            case 3:
                                Service.gI().sendThongBao(player, "Sự kiện đã kết thúc...");
                                // NpcService.gI().createMenuConMeo(player, ConstNpc.CONFIRM_DOI_THUONG_SU_KIEN,
                                // -1, "Con có thực sự muốn đổi thưởng?\nPhải giao cho ta 3000 điểm sự kiện
                                // đấy... ",
                                // "Đồng ý", "Từ chối");
                                break;

                        }
                        break;
                    case ConstNpc.MENU_GIAO_BONG:
                        ItemService.gI().giaobong(player, (int) Util.tinhLuyThua(10, select + 2));
                        break;
                    case ConstNpc.CONFIRM_DOI_THUONG_SU_KIEN:
                        if (select == 0) {
                            ItemService.gI().openBoxVip(player);
                        }
                        break;
                    case ConstNpc.CONFIRM_DOI_DIEM_DUA:
                        if (select == 0) {
                            ItemService.gI().openBoxCongThuc(player);
                        }
                        break;
                    case ConstNpc.CONFIRM_DOI_DIEM_ITEMC2:
                        if (select == 0) {
                            ItemService.gI().openBoxitemc2(player);
                        }
                        break;
                    case ConstNpc.CONFIRM_DOI_ITEM_NR:
                        if (select == 0) {
                            ItemService.gI().openBoxitemnr(player);
                        }
                        break;
                    case ConstNpc.CONFIRM_DOI_DIEM_CT:
                        if (select == 0) {
                            ItemService.gI().openBoxCt(player);
                        }
                        break;
                    case ConstNpc.CONFIRM_TELE_NAMEC:
                        if (select == 0) {
                            NgocRongNamecService.gI().teleportToNrNamec(player);
                            player.inventory.subGemAndRuby(50);
                            Service.gI().sendMoney(player);
                        }
                        break;
                }
            }
        };
    }

}
