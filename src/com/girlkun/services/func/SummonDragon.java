package com.girlkun.services.func;

import java.util.HashMap;
import java.util.Map;
import com.girlkun.models.item.Item;
import com.girlkun.consts.ConstNpc;
import com.girlkun.consts.ConstPlayer;
import com.girlkun.jdbc.daos.GodGK;
import com.girlkun.jdbc.daos.PlayerDAO;
import com.girlkun.models.item.ConstItem;
import com.girlkun.models.item.Item.ItemOption;
import com.girlkun.models.map.Zone;
import com.girlkun.services.NpcService;
import com.girlkun.models.player.Inventory;
import com.girlkun.models.player.Player;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;
import com.girlkun.network.io.Message;
import com.girlkun.server.Client;
import com.girlkun.services.ItemService;
import com.girlkun.services.PlayerService;
import com.girlkun.services.InventoryServiceNew;
import com.girlkun.utils.Logger;
import com.girlkun.services.ItemTimeService;
import java.util.List;

/**
 *
 * @Stole By Arriety💖
 *
 */
public class SummonDragon {

    public static final byte WISHED = 0;
    public static final byte TIME_UP = 1;

    public static final byte DRAGON_SHENRON = 0;
    public static final byte DRAGON_PORUNGA = 1;

    public static final short NGOC_RONG_1_SAO = 14;
    public static final short NGOC_RONG_2_SAO = 15;
    public static final short NGOC_RONG_3_SAO = 16;
    public static final short NGOC_RONG_4_SAO = 17;
    public static final short NGOC_RONG_5_SAO = 18;
    public static final short NGOC_RONG_6_SAO = 19;
    public static final short NGOC_RONG_7_SAO = 20;

    public static final short NGOC_RONGTRB1 = 2091;
    public static final short NGOC_RONGTRB2 = 2092;
    public static final short NGOC_RONGTRB3 = 2093;

    // Ngọc Bí Ngô Halloween (702-708)
    public static final short NGOC_BI_1_SAO = 702;
    public static final short NGOC_BI_2_SAO = 703;
    public static final short NGOC_BI_3_SAO = 704;
    public static final short NGOC_BI_4_SAO = 705;
    public static final short NGOC_BI_5_SAO = 706;
    public static final short NGOC_BI_6_SAO = 707;
    public static final short NGOC_BI_7_SAO = 708;

    public static final String RONG_XUONG_SAY = "Ta là Rồng Xương, ta sẽ ban cho ngươi 1 điều ước. Hãy suy nghĩ thật kỹ!";
    public static final String SUMMON_RONG_XUONG_TUTORIAL = "Để gọi Rồng Xương cần thu thập đủ 7 viên Ngọc Bí (1-7 sao).\n"
            + "Có 3 điều cư dân có thể ước:\n"
            + "1. Thay đổi chiêu 2, 3 đệ tử\n"
            + "2. Thay đổi chiêu 3, 4 đệ tử\n"
            + "3. Nhận ngẫu nhiên 1 món đồ (tầm trung -> thần linh)\n"
            + "Ngọc Bí sẽ mất ngay khi gọi rồng!\n"
            + "Quá 5 phút nếu không ước, Rồng Xương sẽ bay mất.";
    public static final String[] RONG_XUONG_WISHES = new String[] {
            "Thay\nChiêu 2-3\nĐệ tử",
            "Thay\nChiêu 3-4\nĐệ tử",
            "Nhận\nĐồ\nNgẫu nhiên"
    };

    public static final String SUMMON_SHENRON_TUTORIAL = "Có 3 cách gọi rồng thần. Gọi từ ngọc 1 sao, gọi từ ngọc 2 sao, hoặc gọi từ ngọc 3 sao\n"
            + "Các ngọc 4 sao đến 7 sao không thể gọi rồng thần được\n"
            + "Để gọi rồng 1 sao cần ngọc từ 1 sao đến 7 sao\n"
            + "Để gọi rồng 2 sao cần ngọc từ 2 sao đến 7 sao\n"
            + "Để gọi rồng 3 sao cần ngọc từ 3 sao đến 7sao\n"
            + "Điều ước rồng 3 sao: Capsule 3 sao,  hoặc 200k vàng\n"
            + "Điều ước rồng 2 sao: Capsule 2 sao,  hoặc 2 triệu vàng\n"
            + "Điều ước rồng 1 sao: Capsule 1 sao,  hoặc 20 triệu vàng, hoặc đẹp trai, hoặc....\n"
            + "Ngọc rồng sẽ mất ngay khi gọi rồng dù bạn có ước hay không\n"
            + "Quá 5 phút nếu không ước rồng thần sẽ bay mất";
    public static final String SHENRON_SAY = "Ta sẽ ban cho người 1 điều ước, ngươi có 5 phút, hãy suy nghĩ thật kỹ trước khi quyết định";
    public static final String SUMMON_SHENRON_TRB

            = "Để gọi rồng cần ngọc từ 1 sao đến 3 sao\n"
                    + "Điều ước rồng 1 sao: Tăng 30% Sức Đánh , hoặc 30% HP, hoặc 30% KI Trong 30 phút!\n"
                    + "Ngọc rồng sẽ mất ngay khi gọi rồng dù bạn có ước hay không\n"
                    + "Quá 5 phút nếu không ước rồng thần sẽ bay mất";
    public static final String[] SHENRON_1_STAR_TRB = new String[] { "Sức Đánh\n+30%", "HP\n+30%", "KI\n+30%",
            "10K\nHồng Ngọc", "Cải Trang Tiềm Ẩn" };
    public static final String[] SHENRON_1_STAR_WISHES_1 = new String[] { "Giàu có\n+2 Tỏi\nVàng",
            "Găng tay\nđang mang\nlên 1 cấp", "Chí mạng\nGốc +2%",
            "Thay\nChiêu 2-3\nĐệ tử", "Điều ước\nkhác" };
    public static final String[] SHENRON_1_STAR_WISHES_2 = new String[] { "Đẹp trai\nnhất\nVũ trụ",
            "Giàu có\n+10K\nNgọc",
            "Găng tay đệ\nđang mang\nlên 1 cấp",
            "Điều ước\nkhác" };
    public static final String[] SHENRON_2_STARS_WHISHES = new String[] { "Giàu có\n+2K\nNgọc",
            "Giàu có\n+200 Tr\nVàng" };
    public static final String[] SHENRON_3_STARS_WHISHES = new String[] { "Giàu có\n+200\nNgọc",
            "Giàu có\n+20 Tr\nVàng" };
    // --------------------------------------------------------------------------
    private static SummonDragon instance;
    private final Map pl_dragonStar;
    private long lastTimeShenronAppeared;
    private long lastTimeShenronWait;
    private final int timeResummonShenron = 300000;

    private boolean isShenronAppear;
    private final int timeShenronWait = 300000;

    private final Thread update;
    private boolean active;
    private long lastTimetrbAppeared;
    private long lastTimetrbWait;
    private final int timeResummontrb = 300000;
    private int playertrbnronId;

    public boolean isPlayerDisconnect;
    public Player playerSummonShenron;
    private int playerSummonShenronId;
    private Zone mapShenronAppear;
    private byte shenronStar;
    private int menuShenron;
    private byte select;

    private SummonDragon() {
        this.pl_dragonStar = new HashMap<>();
        this.update = new Thread(() -> {
            while (active) {
                try {
                    if (isShenronAppear) {
                        if (isPlayerDisconnect) {

                            List<Player> players = mapShenronAppear.getPlayers();
                            for (Player plMap : players) {
                                if (plMap.id == playerSummonShenronId) {
                                    playerSummonShenron = plMap;
                                    reSummonShenron();
                                    isPlayerDisconnect = false;
                                    break;
                                }
                            }

                        }
                        if (Util.canDoWithTime(lastTimeShenronWait, timeShenronWait)) {
                            shenronLeave(playerSummonShenron, TIME_UP);
                        }
                    }
                    Thread.sleep(1000);
                } catch (Exception e) {
                    Logger.logException(SummonDragon.class, e);
                }
            }
        });
        this.active();
    }

    private void active() {
        if (!active) {
            active = true;
            this.update.start();
        }
    }

    public void summonNamec(Player pl) {
        if (pl.zone.map.mapId == 7) {
            playerSummonShenron = pl;
            playerSummonShenronId = (int) pl.id;
            mapShenronAppear = pl.zone;
            sendNotifyShenronAppear();
            activeShenron(pl, true, SummonDragon.DRAGON_PORUNGA);
            sendWhishesNamec(pl);
        } else {
            Service.gI().sendThongBao(pl, "Không thể thực hiện");
        }
    }

    public static SummonDragon gI() {
        if (instance == null) {
            instance = new SummonDragon();
        }
        return instance;
    }

    public void openMenuSummonShenron(Player pl, byte dragonBallStar) {
        this.pl_dragonStar.put(pl, dragonBallStar);
        NpcService.gI().createMenuConMeo(pl, ConstNpc.SUMMON_SHENRON, -1, "Bạn muốn gọi rồng thần ?",
                "Hướng\ndẫn thêm\n(mới)", "Gọi\nRồng Thần\n" + dragonBallStar + " Sao");
    }

    public void summonShenron(Player pl) {
        if (pl.zone.map.mapId == 0 || pl.zone.map.mapId == 7 || pl.zone.map.mapId == 14) {
            if (checkShenronBall(pl)) {
                if (isShenronAppear) {
                    Service.gI().sendThongBao(pl, "Không thể thực hiện");
                    return;
                }

                if (Util.canDoWithTime(lastTimeShenronAppeared, timeResummonShenron)) {
                    // gọi rồng
                    playerSummonShenron = pl;
                    playerSummonShenronId = (int) pl.id;
                    mapShenronAppear = pl.zone;
                    byte dragonStar = (byte) pl_dragonStar.get(playerSummonShenron);
                    int begin = NGOC_RONG_1_SAO;
                    switch (dragonStar) {
                        case 2:
                            begin = NGOC_RONG_2_SAO;
                            break;
                        case 3:
                            begin = NGOC_RONG_3_SAO;
                            break;
                    }
                    for (int i = begin; i <= NGOC_RONG_7_SAO; i++) {
                        try {
                            InventoryServiceNew.gI().subQuantityItemsBag(pl,
                                    InventoryServiceNew.gI().findItemBag(pl, i), 1);
                        } catch (Exception ex) {
                        }
                    }
                    InventoryServiceNew.gI().sendItemBags(pl);
                    sendNotifyShenronAppear();
                    activeShenron(pl, true, SummonDragon.DRAGON_SHENRON);
                    sendWhishesShenron(pl);
                } else {
                    int timeLeft = (int) ((timeResummonShenron - (System.currentTimeMillis() - lastTimeShenronAppeared))
                            / 1000);
                    Service.gI().sendThongBao(pl, "Vui lòng đợi "
                            + (timeLeft < 7200 ? timeLeft + " giây" : timeLeft / 60 + " phút") + " nữa");
                }
            }
        } else {
            Service.gI().sendThongBao(pl, "Chỉ được gọi rồng thần ở ngôi làng trước nhà");
        }
    }

    public void openMenuSummonShenronTRB(Player pl, byte dragonBallStar) {
        this.pl_dragonStar.put(pl, dragonBallStar);
        NpcService.gI().createMenuConMeo(pl, ConstNpc.SUMMON_SHENRONTRB, -1, "Bạn muốn gọi rồng thần TRB?",
                "Hướng\ndẫn thêm\n(mới)", "Gọi\nRồng Thần\n" + dragonBallStar + " Sao");
    }

    public void summonShenronTRB(Player pl) {
        if (pl.zone.map.mapId == 170) {
            if (checkShenronBalltrb(pl)) {
                if (isShenronAppear) {
                    Service.gI().sendThongBao(pl, "Không thể thực hiện");
                    return;
                }

                if (Util.canDoWithTime(lastTimetrbAppeared, timeResummontrb)) {
                    // gọi rồng
                    playerSummonShenron = pl;
                    playertrbnronId = (int) pl.id;
                    mapShenronAppear = pl.zone;
                    int begin = NGOC_RONGTRB1;

                    for (int i = begin; i <= NGOC_RONGTRB3; i++) {
                        try {
                            InventoryServiceNew.gI().subQuantityItemsBag(pl,
                                    InventoryServiceNew.gI().findItemBag(pl, i), 1);
                        } catch (Exception ex) {
                        }
                    }
                    InventoryServiceNew.gI().sendItemBags(pl);
                    sendNotifyShenronAppear();
                    activeShenron(pl, true, SummonDragon.DRAGON_PORUNGA);
                    sendWhishesShenrontrb(pl);
                } else {
                    int timeLeft = (int) ((timeResummontrb - (System.currentTimeMillis() - lastTimetrbAppeared))
                            / 1000);
                    Service.gI().sendThongBao(pl,
                            "Vui lòng đợi " + (timeLeft < 720 ? timeLeft + " giây" : timeLeft / 60 + " phút") + " nữa");
                }
            }
        } else {
            Service.gI().sendThongBao(pl, "Chỉ được gọi rồng thần ở hành tinh Celreal");
        }
    }

    private void sendWhishesShenrontrb(Player pl) {
        byte dragonStar;
        try {
            dragonStar = (byte) pl_dragonStar.get(pl);
            this.shenronStar = dragonStar;
        } catch (Exception e) {
            dragonStar = this.shenronStar;
        }
        switch (dragonStar) {
            case 1:
                NpcService.gI().createMenuRongThieng(pl, ConstNpc.SHENRONTRB, SHENRON_SAY, SHENRON_1_STAR_TRB);
                break;
            case 2:
                NpcService.gI().createMenuRongThieng(pl, ConstNpc.SHENRONTRB, SHENRON_SAY, SHENRON_1_STAR_TRB);
                break;
            case 3:
                NpcService.gI().createMenuRongThieng(pl, ConstNpc.SHENRONTRB, SHENRON_SAY, SHENRON_1_STAR_TRB);
                break;
        }
    }

    private boolean checkShenronBalltrb(Player pl) {
        byte dragonStar = (byte) this.pl_dragonStar.get(pl);
        if (dragonStar == 1) {
            if (!InventoryServiceNew.gI().isExistItemBag(pl, NGOC_RONGTRB2)) {
                Service.gI().sendThongBao(pl, "Bạn còn thiếu 1 viên ngọc rồng 2 sao");
                return false;
            }
            if (!InventoryServiceNew.gI().isExistItemBag(pl, NGOC_RONGTRB3)) {
                Service.gI().sendThongBao(pl, "Bạn còn thiếu 1 viên ngọc rồng 3 sao");
                return false;
            }
        }
        return true;
    }

    private void reSummonShenron() {
        activeShenron(playerSummonShenron, true, SummonDragon.DRAGON_SHENRON);
        sendWhishesShenron(playerSummonShenron);
    }

    private void sendWhishesShenron(Player pl) {
        byte dragonStar;
        try {
            dragonStar = (byte) pl_dragonStar.get(pl);
            this.shenronStar = dragonStar;
        } catch (Exception e) {
            dragonStar = this.shenronStar;
        }
        switch (dragonStar) {
            case 1:
                NpcService.gI().createMenuRongThieng(pl, ConstNpc.SHENRON_1_1, SHENRON_SAY, SHENRON_1_STAR_WISHES_1);
                break;
            case 2:
                NpcService.gI().createMenuRongThieng(pl, ConstNpc.SHENRON_2, SHENRON_SAY, SHENRON_2_STARS_WHISHES);
                break;
            case 3:
                NpcService.gI().createMenuRongThieng(pl, ConstNpc.SHENRON_3, SHENRON_SAY, SHENRON_3_STARS_WHISHES);
                break;
        }
    }

    private void sendWhishesNamec(Player pl) {
        NpcService.gI().createMenuRongThieng(pl, ConstNpc.NAMEC_1,
                "Ta sẽ ban cho cả bang ngươi 1 điều ước, ngươi có 5 phút, hãy suy nghĩ thật kỹ trước khi quyết định",
                "x99 ngọc rồng 3 sao");
        byte dragonStar;
        try {
            dragonStar = (byte) pl_dragonStar.get(pl);
            this.shenronStar = dragonStar;
        } catch (Exception e) {
            dragonStar = this.shenronStar;
        }
        switch (dragonStar) {
            case 1:
                NpcService.gI().createMenuRongThieng(pl, ConstNpc.NAMEC_1, SHENRON_SAY, SHENRON_1_STAR_WISHES_1);
                break;

        }
    }

    private void activeShenron(Player pl, boolean appear, byte type) {
        Message msg;
        try {
            msg = new Message(-83);
            msg.writer().writeByte(appear ? 0 : (byte) 1);
            if (appear) {
                msg.writer().writeShort(pl.zone.map.mapId);
                msg.writer().writeShort(pl.zone.map.bgId);
                msg.writer().writeByte(pl.zone.zoneId);
                msg.writer().writeInt((int) pl.id);
                msg.writer().writeUTF("");
                msg.writer().writeShort(pl.location.x);
                msg.writer().writeShort(pl.location.y);
                msg.writer().writeByte(type);
                lastTimeShenronWait = System.currentTimeMillis();
                isShenronAppear = true;
            }
            Service.gI().sendMessAllPlayer(msg);
        } catch (Exception e) {
        }
    }

    private boolean checkShenronBall(Player pl) {
        byte dragonStar = (byte) this.pl_dragonStar.get(pl);
        if (dragonStar == 1) {
            if (!InventoryServiceNew.gI().isExistItemBag(pl, NGOC_RONG_2_SAO)) {
                Service.gI().sendThongBao(pl, "Bạn còn thiếu 1 viên ngọc rồng 2 sao");
                return false;
            }
            if (!InventoryServiceNew.gI().isExistItemBag(pl, NGOC_RONG_3_SAO)) {
                Service.gI().sendThongBao(pl, "Bạn còn thiếu 1 viên ngọc rồng 3 sao");
                return false;
            }
        } else if (dragonStar == 2) {
            if (!InventoryServiceNew.gI().isExistItemBag(pl, NGOC_RONG_3_SAO)) {
                Service.gI().sendThongBao(pl, "Bạn còn thiếu 1 viên ngọc rồng 3 sao");
                return false;
            }
        }
        if (!InventoryServiceNew.gI().isExistItemBag(pl, NGOC_RONG_4_SAO)) {
            Service.gI().sendThongBao(pl, "Bạn còn thiếu 1 viên ngọc rồng 4 sao");
            return false;
        }
        if (!InventoryServiceNew.gI().isExistItemBag(pl, NGOC_RONG_5_SAO)) {
            Service.gI().sendThongBao(pl, "Bạn còn thiếu 1 viên ngọc rồng 5 sao");
            return false;
        }
        if (!InventoryServiceNew.gI().isExistItemBag(pl, NGOC_RONG_6_SAO)) {
            Service.gI().sendThongBao(pl, "Bạn còn thiếu 1 viên ngọc rồng 6 sao");
            return false;
        }
        if (!InventoryServiceNew.gI().isExistItemBag(pl, NGOC_RONG_7_SAO)) {
            Service.gI().sendThongBao(pl, "Bạn còn thiếu 1 viên ngọc rồng 7 sao");
            return false;
        }
        return true;
    }

    private void sendNotifyShenronAppear() {
        Message msg;
        try {
            msg = new Message(-25);
            msg.writer().writeUTF(playerSummonShenron.name + " vừa gọi rồng thần tại "
                    + playerSummonShenron.zone.map.mapName + " khu vực " + playerSummonShenron.zone.zoneId);
            Service.gI().sendMessAllPlayerIgnoreMe(playerSummonShenron, msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void confirmWish() {
        switch (this.menuShenron) {
            case ConstNpc.SHENRON_1_1:
                switch (this.select) {
                    case 0: // 20 tr vàng
                        this.playerSummonShenron.inventory.gold = 2000000000;
                        PlayerService.gI().sendInfoHpMpMoney(this.playerSummonShenron);
                        break;
                    case 1: // găng tay đang đeo lên 1 cấp
                        Item item = this.playerSummonShenron.inventory.itemsBody.get(2);
                        if (item.isNotNullItem()) {
                            int level = 0;
                            for (ItemOption io : item.itemOptions) {
                                if (io.optionTemplate.id == 72) {
                                    level = io.param;
                                    if (level < 7) {
                                        io.param++;
                                    }
                                    break;
                                }
                            }
                            if (level < 7) {
                                if (level == 0) {
                                    item.itemOptions.add(new ItemOption(72, 1));
                                }
                                for (ItemOption io : item.itemOptions) {
                                    if (io.optionTemplate.id == 0) {
                                        io.param += (io.param * 10 / 100);
                                        break;
                                    }
                                }
                                InventoryServiceNew.gI().sendItemBody(playerSummonShenron);
                            } else {
                                Service.gI().sendThongBao(playerSummonShenron, "Găng tay của ngươi đã đạt cấp tối đa");
                                reOpenShenronWishes(playerSummonShenron);
                                return;
                            }
                        } else {
                            Service.gI().sendThongBao(playerSummonShenron, "Ngươi hiện tại có đeo găng đâu");
                            reOpenShenronWishes(playerSummonShenron);
                            return;
                        }
                        break;
                    case 2: // chí mạng +2%
                        if (this.playerSummonShenron.nPoint.critg < 9) {
                            this.playerSummonShenron.nPoint.critg += 2;
                        } else {
                            Service.gI().sendThongBao(playerSummonShenron,
                                    "Điều ước này đã quá sức với ta, ta sẽ cho ngươi chọn lại");
                            reOpenShenronWishes(playerSummonShenron);
                            return;
                        }
                        break;
                    case 3: // thay chiêu 2-3 đệ tử
                        if (playerSummonShenron.pet != null) {
                            if (playerSummonShenron.pet.playerSkill.skills.get(1).skillId != -1) {
                                playerSummonShenron.pet.openSkill2();
                                if (playerSummonShenron.pet.playerSkill.skills.get(2).skillId != -1) {
                                    playerSummonShenron.pet.openSkill3();
                                }
                            } else {
                                Service.gI().sendThongBao(playerSummonShenron,
                                        "Ít nhất đệ tử ngươi phải có chiêu 2 chứ!");
                                reOpenShenronWishes(playerSummonShenron);
                                return;
                            }
                        } else {
                            Service.gI().sendThongBao(playerSummonShenron, "Ngươi làm gì có đệ tử?");
                            reOpenShenronWishes(playerSummonShenron);
                            return;
                        }
                        break;
                }
                break;
            case ConstNpc.SHENRON_1_2:
                switch (this.select) {
                    case 0: // đẹp trai nhất vũ trụ
                        if (InventoryServiceNew.gI().getCountEmptyBag(playerSummonShenron) > 0) {
                            byte gender = this.playerSummonShenron.gender;
                            Item avtVip = ItemService.gI().createNewItem((short) (gender == ConstPlayer.TRAI_DAT ? 227
                                    : gender == ConstPlayer.NAMEC ? 228 : 229));
                            avtVip.itemOptions.add(new ItemOption(97, Util.nextInt(5, 10)));
                            avtVip.itemOptions.add(new ItemOption(77, Util.nextInt(10, 20)));
                            InventoryServiceNew.gI().addItemBag(playerSummonShenron, avtVip);
                            InventoryServiceNew.gI().sendItemBags(playerSummonShenron);
                        } else {
                            Service.gI().sendThongBao(playerSummonShenron, "Hành trang đã đầy");
                            reOpenShenronWishes(playerSummonShenron);
                            return;
                        }
                        break;
                    case 1: // +1,5 ngọc
                        this.playerSummonShenron.inventory.ruby += 10000;
                        PlayerService.gI().sendInfoHpMpMoney(this.playerSummonShenron);
                        break;
                    case 2: // găng tay đệ lên 1 cấp
                        if (this.playerSummonShenron.pet != null) {
                            Item item = this.playerSummonShenron.pet.inventory.itemsBody.get(2);
                            if (item.isNotNullItem()) {
                                int level = 0;
                                for (ItemOption io : item.itemOptions) {
                                    if (io.optionTemplate.id == 72) {
                                        level = io.param;
                                        if (level < 7) {
                                            io.param++;
                                        }
                                        break;
                                    }
                                }
                                if (level < 7) {
                                    if (level == 0) {
                                        item.itemOptions.add(new ItemOption(72, 1));
                                    }
                                    for (ItemOption io : item.itemOptions) {
                                        if (io.optionTemplate.id == 0) {
                                            io.param += (io.param * 10 / 100);
                                            break;
                                        }
                                    }
                                    Service.gI().point(playerSummonShenron);
                                } else {
                                    Service.gI().sendThongBao(playerSummonShenron,
                                            "Găng tay của đệ ngươi đã đạt cấp tối đa");
                                    reOpenShenronWishes(playerSummonShenron);
                                    return;
                                }
                            } else {
                                Service.gI().sendThongBao(playerSummonShenron, "Đệ ngươi hiện tại có đeo găng đâu");
                                reOpenShenronWishes(playerSummonShenron);
                                return;
                            }
                        } else {
                            Service.gI().sendThongBao(playerSummonShenron, "Ngươi đâu có đệ tử");
                            reOpenShenronWishes(playerSummonShenron);
                            return;
                        }
                        break;
                }
                break;
            case ConstNpc.SHENRON_2:
                switch (this.select) {
                    case 0: // +150 ngọc
                        this.playerSummonShenron.inventory.ruby += 2000;
                        PlayerService.gI().sendInfoHpMpMoney(this.playerSummonShenron);
                        break;

                    case 1: // 2 tr vàng
                        if (this.playerSummonShenron.inventory.gold > 1800000000) {
                            this.playerSummonShenron.inventory.gold = Inventory.LIMIT_GOLD;
                        } else {
                            this.playerSummonShenron.inventory.gold += 200000000;
                        }
                        PlayerService.gI().sendInfoHpMpMoney(this.playerSummonShenron);
                        break;
                }
                break;
            case ConstNpc.SHENRON_3:
                switch (this.select) {
                    case 0: // +15 ngọc
                        this.playerSummonShenron.inventory.ruby += 200;
                        PlayerService.gI().sendInfoHpMpMoney(this.playerSummonShenron);
                        break;
                    case 1: // 200k vàng
                        if (this.playerSummonShenron.inventory.gold > (2000000000 - 20000000)) {
                            this.playerSummonShenron.inventory.gold = Inventory.LIMIT_GOLD;
                        } else {
                            this.playerSummonShenron.inventory.gold += 20000000;
                        }
                        PlayerService.gI().sendInfoHpMpMoney(this.playerSummonShenron);
                        break;
                }
                break;
            case ConstNpc.SHENRONTRB:
                switch (this.select) {
                    case 0: // +30 sd
                        playerSummonShenron.itemTime.lastTimetrbsd = System.currentTimeMillis();
                        playerSummonShenron.itemTime.istrbsd = true;
                        ItemTimeService.gI().sendAllItemTime(playerSummonShenron);
                        Service.gI().point(playerSummonShenron);
                        break;
                    case 1: // +30hp
                        playerSummonShenron.itemTime.lastTimetrbhp = System.currentTimeMillis();
                        playerSummonShenron.itemTime.istrbhp = true;
                        ItemTimeService.gI().sendAllItemTime(playerSummonShenron);
                        Service.gI().point(playerSummonShenron);
                        break;
                    case 2: // 30ki
                        playerSummonShenron.itemTime.lastTimetrbki = System.currentTimeMillis();
                        playerSummonShenron.itemTime.istrbki = true;
                        ItemTimeService.gI().sendAllItemTime(playerSummonShenron);
                        Service.gI().point(playerSummonShenron);
                        break;
                    case 3: // +150 ngọc
                        this.playerSummonShenron.inventory.ruby += 10000;
                        PlayerService.gI().sendInfoHpMpMoney(this.playerSummonShenron);
                        break;
                    case 4: // găng tay đang đeo lên 1 cấp
                        if (InventoryServiceNew.gI().getCountEmptyBag(playerSummonShenron) > 0) {
                            byte gender = this.playerSummonShenron.gender;
                            Item avtVip = ItemService.gI().createNewItem((short) (gender == ConstPlayer.TRAI_DAT ? 1285
                                    : gender == ConstPlayer.NAMEC ? 1286 : 1284));
                            avtVip.itemOptions.add(new ItemOption(50, Util.nextInt(20, 40)));
                            avtVip.itemOptions.add(new ItemOption(77, Util.nextInt(20, 40)));
                            avtVip.itemOptions.add(new ItemOption(103, Util.nextInt(20, 40)));
                            avtVip.itemOptions.add(new ItemOption(5, Util.nextInt(5, 40)));
                            avtVip.itemOptions.add(new ItemOption(14, Util.nextInt(5, 40)));
                            avtVip.itemOptions.add(new ItemOption(95, Util.nextInt(5, 10)));
                            avtVip.itemOptions.add(new ItemOption(96, Util.nextInt(5, 10)));
                            avtVip.itemOptions.add(new ItemOption(93, Util.nextInt(1, 7)));
                            avtVip.itemOptions.add(new ItemOption(30, 0));
                            InventoryServiceNew.gI().addItemBag(playerSummonShenron, avtVip);
                            InventoryServiceNew.gI().sendItemBags(playerSummonShenron);
                        } else {
                            Service.gI().sendThongBao(playerSummonShenron, "Hành trang đã đầy");
                            reOpenShenronWishes(playerSummonShenron);
                            return;
                        }
                        break;
                }
                break;
            case ConstNpc.NAMEC_1:
                if (select == 0) {
                    if (playerSummonShenron.clan != null) {
                        playerSummonShenron.clan.members.forEach(m -> {
                            if (Client.gI().getPlayer(m.id) != null) {
                                Player p = Client.gI().getPlayer(m.id);
                                Item it = ItemService.gI().createNewItem((short) 16);
                                it.quantity = 99;
                                InventoryServiceNew.gI().addItemBag(p, it);
                                InventoryServiceNew.gI().sendItemBags(p);
                            } else {
                                Player p = GodGK.loadById(m.id);
                                if (p != null) {
                                    Item it = ItemService.gI().createNewItem((short) 16);
                                    it.quantity = 99;
                                    InventoryServiceNew.gI().addItemBag(p, it);
                                    PlayerDAO.updatePlayer(p);
                                }
                            }
                        });
                    } else {
                        Item it = ItemService.gI().createNewItem((short) 16);
                        it.quantity = 99;
                        InventoryServiceNew.gI().addItemBag(playerSummonShenron, it);
                        InventoryServiceNew.gI().sendItemBags(playerSummonShenron);
                    }
                }
                break;
            // ==================== RỒNG XƯƠNG HALLOWEEN ====================
            case ConstNpc.RONG_XUONG:
                switch (this.select) {
                    case 0: // Thay chiêu 2-3 đệ tử
                        if (playerSummonShenron.pet != null) {
                            if (playerSummonShenron.pet.playerSkill.skills.get(1).skillId != -1
                                    && playerSummonShenron.pet.playerSkill.skills.get(2).skillId != -1) {
                                playerSummonShenron.pet.openSkill2();
                                playerSummonShenron.pet.openSkill3();
                                Service.gI().sendThongBao(playerSummonShenron, "Đã đổi chiêu 2, 3 đệ tử thành công!");
                            } else {
                                Service.gI().sendThongBao(playerSummonShenron,
                                        "Đệ tử của ngươi phải có chiêu 2 và chiêu 3!");
                                reOpenRongXuongWishes(playerSummonShenron);
                                return;
                            }
                        } else {
                            Service.gI().sendThongBao(playerSummonShenron, "Ngươi làm gì có đệ tử?");
                            reOpenRongXuongWishes(playerSummonShenron);
                            return;
                        }
                        break;
                    case 1: // Thay chiêu 3-4 đệ tử
                        if (playerSummonShenron.pet != null) {
                            if (playerSummonShenron.pet.playerSkill.skills.get(2).skillId != -1
                                    && playerSummonShenron.pet.playerSkill.skills.get(3).skillId != -1) {
                                playerSummonShenron.pet.openSkill3();
                                playerSummonShenron.pet.openSkill4();
                                Service.gI().sendThongBao(playerSummonShenron, "Đã đổi chiêu 3, 4 đệ tử thành công!");
                            } else {
                                Service.gI().sendThongBao(playerSummonShenron,
                                        "Đệ tử của ngươi phải có chiêu 3 và chiêu 4!");
                                reOpenRongXuongWishes(playerSummonShenron);
                                return;
                            }
                        } else {
                            Service.gI().sendThongBao(playerSummonShenron, "Ngươi làm gì có đệ tử?");
                            reOpenRongXuongWishes(playerSummonShenron);
                            return;
                        }
                        break;
                    case 2: // Nhận đồ ngẫu nhiên
                        if (InventoryServiceNew.gI().getCountEmptyBag(playerSummonShenron) > 0) {
                            Item randomItem = getRandomItemForRongXuong(playerSummonShenron);
                            InventoryServiceNew.gI().addItemBag(playerSummonShenron, randomItem);
                            InventoryServiceNew.gI().sendItemBags(playerSummonShenron);
                            Service.gI().sendThongBao(playerSummonShenron,
                                    "Chúc mừng! Ngươi nhận được " + randomItem.template.name);
                        } else {
                            Service.gI().sendThongBao(playerSummonShenron, "Hành trang đã đầy!");
                            reOpenRongXuongWishes(playerSummonShenron);
                            return;
                        }
                        break;
                }
                break;
        }
        shenronLeave(this.playerSummonShenron, WISHED);
    }

    public void showConfirmShenron(Player pl, int menu, byte select) {
        this.menuShenron = menu;
        this.select = select;
        String wish = null;
        switch (menu) {
            case ConstNpc.SHENRON_1_1:
                wish = SHENRON_1_STAR_WISHES_1[select];
                break;
            case ConstNpc.SHENRON_1_2:
                wish = SHENRON_1_STAR_WISHES_2[select];
                break;
            case ConstNpc.SHENRON_2:
                wish = SHENRON_2_STARS_WHISHES[select];
                break;
            case ConstNpc.SHENRON_3:
                wish = SHENRON_3_STARS_WHISHES[select];
                break;
            case ConstNpc.NAMEC_1:
                wish = "x99 ngọc rồng 3 sao";
                break;
            case ConstNpc.SHENRONTRB:
                wish = SHENRON_1_STAR_TRB[select];
                break;
            case ConstNpc.RONG_XUONG:
                wish = RONG_XUONG_WISHES[select];
                break;
        }
        NpcService.gI().createMenuRongThieng(pl, ConstNpc.SHENRON_CONFIRM, "Ngươi có chắc muốn ước?", wish, "Từ chối");
    }

    public void reOpenShenronWishes(Player pl) {
        switch (menuShenron) {
            case ConstNpc.SHENRON_1_1:
                NpcService.gI().createMenuRongThieng(pl, ConstNpc.SHENRON_1_1, SHENRON_SAY, SHENRON_1_STAR_WISHES_1);
                break;
            case ConstNpc.SHENRON_1_2:
                NpcService.gI().createMenuRongThieng(pl, ConstNpc.SHENRON_1_2, SHENRON_SAY, SHENRON_1_STAR_WISHES_2);
                break;
            case ConstNpc.SHENRON_2:
                NpcService.gI().createMenuRongThieng(pl, ConstNpc.SHENRON_2, SHENRON_SAY, SHENRON_2_STARS_WHISHES);
                break;
            case ConstNpc.SHENRON_3:
                NpcService.gI().createMenuRongThieng(pl, ConstNpc.SHENRON_3, SHENRON_SAY, SHENRON_3_STARS_WHISHES);
                break;
            case ConstNpc.RONG_XUONG:
                NpcService.gI().createMenuRongThieng(pl, ConstNpc.RONG_XUONG, RONG_XUONG_SAY, RONG_XUONG_WISHES);
                break;
        }
    }

    public void shenronLeave(Player pl, byte type) {
        if (type == WISHED) {
            NpcService.gI().createTutorial(pl, -1,
                    "Điều ước của ngươi đã trở thành sự thật\nHẹn gặp ngươi lần sau, ta đi ngủ đây, bái bai");
        } else {
            NpcService.gI().createMenuRongThieng(pl, ConstNpc.IGNORE_MENU,
                    "Ta buồn ngủ quá rồi\nHẹn gặp ngươi lần sau, ta đi đây, bái bai");
        }
        activeShenron(pl, false, SummonDragon.DRAGON_SHENRON);
        this.isShenronAppear = false;
        this.menuShenron = -1;
        this.select = -1;
        this.playerSummonShenron = null;
        this.playerSummonShenronId = -1;
        this.shenronStar = -1;
        this.mapShenronAppear = null;
        lastTimeShenronAppeared = System.currentTimeMillis();
    }

    // --------------------------------------------------------------------------

    // ==================== RỒNG XƯƠNG HALLOWEEN ====================

    public void openMenuSummonRongXuong(Player pl) {
        NpcService.gI().createMenuConMeo(pl, ConstNpc.SUMMON_RONG_XUONG, -1,
                "Bạn muốn gọi Rồng Xương Halloween?",
                "Hướng\ndẫn thêm", "Gọi\nRồng Xương");
    }

    public void summonRongXuong(Player pl) {
        // Kiểm tra đủ 7 viên ngọc bí
        if (!checkNgocBiNgo(pl)) {
            return;
        }

        if (isShenronAppear) {
            Service.gI().sendThongBao(pl, "Rồng đang được triệu hồi, vui lòng đợi!");
            return;
        }

        if (Util.canDoWithTime(lastTimeShenronAppeared, timeResummonShenron)) {
            // Trừ các ngọc bí
            for (int i = NGOC_BI_1_SAO; i <= NGOC_BI_7_SAO; i++) {
                try {
                    InventoryServiceNew.gI().subQuantityItemsBag(pl,
                            InventoryServiceNew.gI().findItemBag(pl, i), 1);
                } catch (Exception ex) {
                }
            }
            InventoryServiceNew.gI().sendItemBags(pl);

            playerSummonShenron = pl;
            playerSummonShenronId = (int) pl.id;
            mapShenronAppear = pl.zone;

            sendNotifyShenronAppear();
            activeShenron(pl, true, SummonDragon.DRAGON_PORUNGA);  // Appearance
            sendWhishesRongXuong(pl);
        } else {
            int timeLeft = (int) ((timeResummonShenron
                    - (System.currentTimeMillis() - lastTimeShenronAppeared)) / 1000);
            Service.gI().sendThongBao(pl, "Vui lòng đợi "
                    + (timeLeft < 60 ? timeLeft + " giây" : timeLeft / 60 + " phút") + " nữa");
        }
    }

    private boolean checkNgocBiNgo(Player pl) {
        for (int i = NGOC_BI_1_SAO; i <= NGOC_BI_7_SAO; i++) {
            if (!InventoryServiceNew.gI().isExistItemBag(pl, i)) {
                Service.gI().sendThongBao(pl, "Bạn còn thiếu Ngọc Bí " + (i - NGOC_BI_1_SAO + 1) + " sao");
                return false;
            }
        }
        return true;
    }

    private void sendWhishesRongXuong(Player pl) {
        this.shenronStar = 1; // Fixed star for Rồng Xương
        NpcService.gI().createMenuRongThieng(pl, ConstNpc.RONG_XUONG, RONG_XUONG_SAY, RONG_XUONG_WISHES);
    }

    public void reOpenRongXuongWishes(Player pl) {
        NpcService.gI().createMenuRongThieng(pl, ConstNpc.RONG_XUONG, RONG_XUONG_SAY, RONG_XUONG_WISHES);
    }

    /**
     * Get random item for Rồng Xương wish (tầm trung -> thần linh)
     * 75% tầm trung, 20% VIP, 5% thần linh
     */
    private Item getRandomItemForRongXuong(Player pl) {
        int random = Util.nextInt(1, 100);
        boolean isHasSPL = Util.nextInt(10) % 2 == 0;
        com.girlkun.models.item.ConstItem.LoaiDoRoiTuBoss type;

        if (random <= 5) {
            type = com.girlkun.models.item.ConstItem.LoaiDoRoiTuBoss.DoThanLinh;
        } else if (random <= 25) { // 5 + 20 = 25
            type = com.girlkun.models.item.ConstItem.LoaiDoRoiTuBoss.DoVip;
        } else {
            type = com.girlkun.models.item.ConstItem.LoaiDoRoiTuBoss.DoTamTrung;
        }

        // Get random item from the type
        short idItem;
        try {
            int index = type.value;
            idItem = com.girlkun.models.item.ConstItem.doRoiTuBoss[index][Util.nextInt(0,
                    com.girlkun.models.item.ConstItem.doRoiTuBoss[index].length - 1)];
        } catch (Exception e) {
            // Fallback to tầm trung
            int index = com.girlkun.models.item.ConstItem.LoaiDoRoiTuBoss.DoTamTrung.value;
            idItem = com.girlkun.models.item.ConstItem.doRoiTuBoss[index][Util.nextInt(0,
                    com.girlkun.models.item.ConstItem.doRoiTuBoss[index].length - 1)];
        }

        Item item = ItemService.gI().createNewItem(idItem);
        if(isHasSPL){
            int randomSao = Util.nextInt(1, 3);
            item.itemOptions.add(new Item.ItemOption(107, randomSao)); // SPL chưa ép
        }
        item.itemOptions.add(new Item.ItemOption(209, 0)); // Đồ từ Rồng Xương
        return item;
    }
}
