package com.girlkun.services.func;

import com.girlkun.database.GirlkunDB;
import com.girlkun.consts.ConstNpc;
import com.girlkun.jdbc.daos.PlayerDAO;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.Zone;
import com.girlkun.models.npc.Npc;
import com.girlkun.models.npc.NpcManager;
import com.girlkun.models.player.Inventory;
import com.girlkun.models.player.Player;
import com.girlkun.network.io.Message;
import com.girlkun.network.session.ISession;
import com.girlkun.result.GirlkunResultSet;
import com.girlkun.server.Client;
import com.girlkun.services.Service;
import com.girlkun.services.GiftService;
import com.girlkun.services.InventoryServiceNew;
import com.girlkun.services.ItemService;
import com.girlkun.services.ItemTimeService;
import com.girlkun.services.NapThe;
import com.girlkun.services.NpcService;
import com.girlkun.utils.Util;
import com.girlkun.models.pariry.PariryServices;
import com.girlkun.models.pariry.pariryManager;

import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


public class Input {
    
    public static  String LOAI_THE;
     public static  String MENH_GIA;
    private static final Map<Integer, Object> PLAYER_ID_OBJECT = new HashMap<Integer, Object>();

    public static final int CHANGE_PASSWORD = 500;
    public static final int GIFT_CODE = 501;
    public static final int FIND_PLAYER = 502;
    public static final int CHANGE_NAME = 503;
    public static final int CHOOSE_LEVEL_BDKB = 504;
    public static final int CHOOSE_LEVEL_GAS = 5012;
    public static final int NAP_THE = 505;
    public static final int CHANGE_NAME_BY_ITEM = 506;
    public static final int QUY_DOI_COIN = 508;
    public static final int QUY_DOI_NGOC = 512;
     public static final int BUFF_ITEM_VIP = 513;
    public static final int XIU_taixiu = 5164;
    public static final int TAI_taixiu = 5165;
    public static final int LE = 555;
    public static final int CHAN = 556;
    public static final byte NUMERIC = 0;
    public static final byte ANY = 1;
    public static final byte PASSWORD = 2;
 public static final int UseGold = 3;
    private static Input intance;
    private int rubyTrade;

    private Input() {

    }

    public static Input gI() {
        if (intance == null) {
            intance = new Input();
        }
        return intance;
    }

    public void doInput(Player player, Message msg) {
        try {
            String[] text = new String[msg.reader().readByte()];
            for (int i = 0; i < text.length; i++) {
                text[i] = msg.reader().readUTF();
            }
            switch (player.iDMark.getTypeInput()) {
                case CHANGE_PASSWORD:
                    Service.gI().changePassword(player, text[0], text[1], text[2]);
                    break;
               case GIFT_CODE:{
                    String textLevel = text[0];
                    Input.gI().addItemGiftCodeToPlayer(player, textLevel);
                    break;}
                case FIND_PLAYER:
                    Player pl = Client.gI().getPlayer(text[0]);
                    if (pl != null) {
                        NpcService.gI().createMenuConMeo(player, ConstNpc.MENU_FIND_PLAYER, -1, "|7|Nhân vật: " + pl.name
                        + "\n|3|Số vnd đang có: "+ Util.numberToMoney(pl.getSession().coinBar)
                        + "\n|3|Số thỏi vàng đang có: " + Util.numberToMoney(InventoryServiceNew.gI().findItem(pl.inventory.itemsBag, 457).quantity)
                        + "\n|3|Số hồng ngọc đang có: " + Util.numberToMoney(pl.inventory.ruby)
                        + "\n\n|2|[Trạng thái tài khoản : "+ (!pl.getSession().actived ? " Chưa MTV]" : " Đã MTV]")
                        + "\n\n|8|Sức Mạnh hiện tại: " + Util.numberToMoney(pl.nPoint.power)
                        + "\n|1|Chỉ số hiện tại"        
                        + "\n|4|Hp: " + pl.nPoint.hp + "/" + pl.nPoint.hpMax
                        + "\n|4|Ki: " + pl.nPoint.mp + "/" + pl.nPoint.mpMax
                        + "\n|4|Sức đánh: " + pl.nPoint.dame
                        + "\n\n|7|Nhiệm vụ hiện tại: " + pl.playerTask.taskMain.name,
                                new String[]{"Đi tới chỗ\n" + pl.name, "Gọi " + pl.name + "\ntới đây", "Đổi tên\n" + pl.name, "Ban\n" + pl.name, "Kick\n" + pl.name},
                                pl);
                    } else {
                        Service.gI().sendThongBao(player, "Người chơi không tồn tại hoặc đang offline");
                    }
                    break;
                    case UseGold:
                    int Gold = Integer.parseInt(text[0]);
                    Item thoivangchange = null;
                    for (Item item : player.inventory.itemsBag) {
                        if (item.isNotNullItem() && item.template.id == 457) {
                            thoivangchange = item;
                            break;
                        }
                    }
                    if(thoivangchange.quantity > Gold  && Gold > 0)
                    {
                        player.inventory.gold += (long)(500000000L* (long)Gold);
                        InventoryServiceNew.gI().subQuantityItemsBag(player, thoivangchange, Gold);
                        InventoryServiceNew.gI().sendItemBags(player);
                        Service.gI().sendMoney(player);
                        Service.gI().sendThongBao(player, "Đổi Thành Công");
                    }
                    else 
                    {
                        Service.gI().sendThongBao(player, "Vui lòng nhập lại số lượng");
                    }
                    break; 
                    case BUFF_ITEM_VIP:
                    if (player.isAdmin()) {
                        Player pBuffItem = Client.gI().getPlayer(text[0]);
                        int idItemBuff = Integer.parseInt(text[1]);
                        String idOptionBuff = text[2].trim();

                        int slItemBuff = Integer.parseInt(text[3]);

                        try {
                            if (pBuffItem != null) {
                                String txtBuff = "Buff to player: " + pBuffItem.name + "\b";

                                Item itemBuffTemplate = ItemService.gI().createNewItem((short) idItemBuff, slItemBuff);
                                if (!idOptionBuff.isEmpty()) {
                                    String arr[] = idOptionBuff.split("&");
                                    for (int i = 0; i < arr.length; i++) {
                                        String arr2[] = arr[i].split("-");
                                        int idoption = Integer.parseInt(arr2[0].trim());
                                        int param = Integer.parseInt(arr2[1].trim());
                                        itemBuffTemplate.itemOptions.add(new Item.ItemOption(idoption, param));
                                    }

                                }
                                txtBuff += "x" + slItemBuff + " " + itemBuffTemplate.template.name + "\b";
                                InventoryServiceNew.gI().addItemBag(pBuffItem, itemBuffTemplate);
                                InventoryServiceNew.gI().sendItemBags(pBuffItem);
                                NpcService.gI().createTutorial(player, 24, txtBuff);
                                if (player.id != pBuffItem.id) {
                                    NpcService.gI().createTutorial(pBuffItem, 24, txtBuff);
                                }
                            } else {
                                Service.getInstance().sendThongBao(player, "Player không online");
                            }
                        } catch (Exception e) {
                            Service.getInstance().sendThongBao(player, "Đã có lỗi xảy ra vui lòng thử lại");
                        }

                    }
                    break;
                    
                case CHANGE_NAME: {
                    Player plChanged = (Player) PLAYER_ID_OBJECT.get((int) player.id);
                    if (plChanged != null) {
                        if (GirlkunDB.executeQuery("select * from player where name = ?", text[0]).next()) {
                            Service.gI().sendThongBao(player, "Tên nhân vật đã tồn tại");
                        } else {
                            plChanged.name = text[0];
                            GirlkunDB.executeUpdate("update player set name = ? where id = ?", plChanged.name, plChanged.id);
                            Service.gI().player(plChanged);
                            Service.gI().Send_Caitrang(plChanged);
                            Service.gI().sendFlagBag(plChanged);
                            Zone zone = plChanged.zone;
                            ChangeMapService.gI().changeMap(plChanged, zone, plChanged.location.x, plChanged.location.y);
                            Service.gI().sendThongBao(plChanged, "Chúc mừng bạn đã có cái tên mới đẹp đẽ hơn tên ban đầu");
                            Service.gI().sendThongBao(player, "Đổi tên người chơi thành công");
                        }
                    }
                }
                break;
                case CHANGE_NAME_BY_ITEM: {
                    if (player != null) {
                        if (GirlkunDB.executeQuery("select * from player where name = ?", text[0]).next()) {
                            Service.gI().sendThongBao(player, "Tên nhân vật đã tồn tại");
                            createFormChangeNameByItem(player);
                        } else {
                            Item theDoiTen = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 2006);
                            if (theDoiTen == null) {
                                Service.gI().sendThongBao(player, "Không tìm thấy thẻ đổi tên");
                            }
                            else {
                                InventoryServiceNew.gI().subQuantityItemsBag(player,theDoiTen,1);
                                player.name = text[0];
                                GirlkunDB.executeUpdate("update player set name = ? where id = ?", player.name, player.id);
                                Service.gI().player(player);
                                Service.gI().Send_Caitrang(player);
                                Service.gI().sendFlagBag(player);
                                Zone zone = player.zone;
                                ChangeMapService.gI().changeMap(player, zone, player.location.x, player.location.y);
                                Service.gI().sendThongBao(player, "Chúc mừng bạn đã có cái tên mới đẹp đẽ hơn tên ban đầu");
                            }
                        }
                    }
                }
                break;
                 case TAI_taixiu:
                    int sotvxiu1 = Integer.valueOf(text[0]);
                    try {
                        if (sotvxiu1 >= 1000 && sotvxiu1 <= 10000000) {
                            if (player.inventory.ruby >= sotvxiu1) {
                                player.inventory.ruby -= sotvxiu1;
                                player.goldTai += sotvxiu1;
                                TaiXiu.gI().goldTai += sotvxiu1;
                                Service.gI().sendThongBao(player, "Bạn đã đặt " + Util.format(sotvxiu1) + " Hồng ngọc vào TÀI");
                                TaiXiu.gI().addPlayerTai(player);
                                InventoryServiceNew.gI().sendItemBags(player);
                                Service.getInstance().sendMoney(player);
                                PlayerDAO.updatePlayer(player);
                                ItemTimeService.gI().sendTextTX(player);
                            } else {
                                Service.gI().sendThongBao(player, "Bạn không đủ Hồng ngọc để chơi.");
                            }
                        } else {
                            Service.gI().sendThongBao(player, "Cược ít nhất 1.000 - nhiều nhất 1.000.000.000 Hồng ngọc");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Service.gI().sendThongBao(player, "Lỗi.");
                    }
                    break;
                     case XIU_taixiu:
                    int sotvxiu2 = Integer.valueOf(text[0]);
                    try {
                        if (sotvxiu2 >= 1000 && sotvxiu2 <= 10000000) {
                            if (player.inventory.ruby >= sotvxiu2) {
                                player.inventory.ruby -= sotvxiu2;
                                player.goldXiu += sotvxiu2;
                                TaiXiu.gI().goldXiu += sotvxiu2;
                                Service.gI().sendThongBao(player, "Bạn đã đặt " + Util.format(sotvxiu2) + " Hồng ngọc vào XỈU");
                                TaiXiu.gI().addPlayerXiu(player);
                                InventoryServiceNew.gI().sendItemBags(player);
                                Service.getInstance().sendMoney(player);
                                PlayerDAO.updatePlayer(player);
                                ItemTimeService.gI().sendTextTX(player);
                            } else {
                                Service.gI().sendThongBao(player, "Bạn không đủ Hồng ngọc để chơi.");
                            }
                        } else {
                            Service.gI().sendThongBao(player, "Cược ít nhất 1.000 - nhiều nhất 1.000.000.000 Hồng ngọc ");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Service.gI().sendThongBao(player, "Lỗi.");
                        System.out.println("nnnnn2  ");
                    }
                    break;
                    case CHAN:
                    int tvCuoc1 = Integer.valueOf(text[0]);
                    Item tv1 = null;
                        for (Item item : player.inventory.itemsBag) {
                            if (item.isNotNullItem() && item.template.id == 457) {
                                tv1 = item;
                                break;
                            }
                        }try {
                        if (tvCuoc1 >= 10 && tvCuoc1 <= 10000) {
                            if (tv1.quantity >= tvCuoc1) {
                                InventoryServiceNew.gI().subQuantityItemsBag(player, tv1, tvCuoc1);
                                InventoryServiceNew.gI().sendItemBags(player);
                                Service.gI().sendThongBao(player, "Bạn đã đặt " + Util.format(tvCuoc1) + " thỏi vàng vào Chẵn");
                                player.cuoc1 = tvCuoc1;
                                pariryManager.gI().goldChan += tvCuoc1;
                                PariryServices.gI().addPlayerEven(player);
                                ItemTimeService.gI().sendTextCL(player);
                            } else {
                                Service.gI().sendThongBao(player, "Bạn không đủ thỏi vàng để chơi.");
                            }
                        } else {
                            Service.gI().sendThongBao(player, "Cược ít nhất 10 - nhiều nhất 10.000 thỏi vàng");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Service.gI().sendThongBao(player, "Lỗi.");
                    }
                    break;
                case LE:
                    int tvCuoc = Integer.valueOf(text[0]);
                    Item tv = null;
                        for (Item item : player.inventory.itemsBag) {
                            if (item.isNotNullItem() && item.template.id == 457) {
                                tv = item;
                                break;
                            }
                        }try {
                        if (tvCuoc >= 10 && tvCuoc <= 10000) {
                            if (tv.quantity >= tvCuoc) {
                                InventoryServiceNew.gI().subQuantityItemsBag(player, tv, tvCuoc);
                                InventoryServiceNew.gI().sendItemBags(player);
                                Service.gI().sendThongBao(player, "Bạn đã đặt " + Util.format(tvCuoc) + " thỏi vàng vào Lẻ");
                                player.cuoc = tvCuoc;
                                pariryManager.gI().goldLe += tvCuoc;
                                PariryServices.gI().addPlayerOdd(player);
                                ItemTimeService.gI().sendTextCL(player);
                            } else {
                                Service.gI().sendThongBao(player, "Bạn không đủ thỏi vàng để chơi.");
                            }
                        } else {
                            Service.gI().sendThongBao(player, "Cược ít nhất 10 - nhiều nhất 10.000 thỏi vàng");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Service.gI().sendThongBao(player, "Lỗi.");
                    }
                    break;
                    case CHOOSE_LEVEL_GAS:
                    int levele = Integer.parseInt(text[0]);
                    if (levele >= 1 && levele <= 110) {
                        Npc npc = NpcManager.getByIdAndMap(ConstNpc.POPO, player.zone.map.mapId);
                        if (npc != null) {
                            npc.createOtherMenu(player, ConstNpc.MENU_ACCPET_GO_TO_GAS,
                                    "Con có chắc chắn muốn tới Khí gas huỷ diệt cấp độ " + levele + "?",
                                    new String[]{"Đồng ý, Let's Go", "Từ chối"}, levele);
                        }
                    } else {
                        Service.getInstance().sendThongBao(player, "Không thể thực hiện");
                    }
                    
//                    BanDoKhoBauService.gI().openBanDoKhoBau(player, (byte) );
                    break;
               case CHOOSE_LEVEL_BDKB:
                    int level = Integer.parseInt(text[0]);
                    if (level >= 1 && level <= 110) {
                        Npc npc = NpcManager.getByIdAndMap(ConstNpc.QUY_LAO_KAME, player.zone.map.mapId);
                        if (npc != null) {
                            npc.createOtherMenu(player, ConstNpc.MENU_ACCEPT_GO_TO_BDKB,
                                    "Con có chắc chắn muốn tới bản đồ kho báu cấp độ " + level + "?",
                                    new String[]{"Đồng ý", "Từ chối"}, level);
                        } 
                    } else {
                        Service.getInstance().sendThongBao(player, "Không thể thực hiện");
                    }
                    break;
                    
                
                case QUY_DOI_COIN:
                    int ratioGold = 8; // tỉ lệ đổi tv
                    int coinGold = 1000; // là cái loz
                    int goldTrade = Integer.parseInt(text[0]);
                    if(goldTrade<=0 || goldTrade>= 500)
                    {
                       Service.gI().sendThongBao(player, "Quá giới hạn mỗi lần chỉ được 500K");
                    }
                    else if(player.getSession().coinBar >= goldTrade*coinGold){
                        PlayerDAO.subcoinBar(player, goldTrade*coinGold);
                        Item thoiVang =ItemService.gI().createNewItem((short)457,goldTrade*8);// x4
                        InventoryServiceNew.gI().addItemBag(player,thoiVang);
                       InventoryServiceNew.gI().sendItemBags(player);
                        Service.gI().sendThongBao(player, "Bạn nhận được " +goldTrade*ratioGold
                         +" " + thoiVang.template.name);
                    }else{
                        Service.gI().sendThongBao(player, "Số tiền của bạn là " +player.getSession().coinBar+ " không đủ để quy "
                                + " đổi " + goldTrade + " thỏi vàng " + " " + "bạn cần thêm" +(player.getSession().coinBar-goldTrade));
                    }
                    break;
                    
                    case QUY_DOI_NGOC:
                    int rationgoc = 4000; // tỉ lệ đổi ngọc
                    int coinngoc = 1000; // là cái vnd
                    int ngocTrade = Integer.parseInt(text[0]);
                    if(ngocTrade<=0 || ngocTrade>= 500)
                    {
                       Service.gI().sendThongBao(player, "Quá giới hạn mỗi lần chỉ được 500K");
                    }
                    else if(player.getSession().coinBar >= ngocTrade*coinngoc){
                        PlayerDAO.subcoinBar(player, ngocTrade*coinngoc);
                        Item thoiVang =ItemService.gI().createNewItem((short)861,ngocTrade*4000);// x5000
                        InventoryServiceNew.gI().addItemBag(player,thoiVang);
                       InventoryServiceNew.gI().sendItemBags(player);
                        Service.gI().sendThongBao(player, "bạn nhận được " +ngocTrade*rationgoc
                         +" " + thoiVang.template.name);
                    }else{
                        Service.gI().sendThongBao(player, "Số tiền của bạn là " +player.getSession().coinBar+ " không đủ để quy "
                                + " đổi " + ngocTrade + " ngọc " + " " + "bạn cần thêm" +(player.getSession().coinBar-ngocTrade));
                    }
                    break;    
                    
            }
               } catch (Exception e) {
        }
    }

    public void createForm(Player pl, int typeInput, String title, SubInput... subInputs) {
        pl.iDMark.setTypeInput(typeInput);
        Message msg;
        try {
            msg = new Message(-125);
            msg.writer().writeUTF(title);
            msg.writer().writeByte(subInputs.length);
            for (SubInput si : subInputs) {
                msg.writer().writeUTF(si.name);
                msg.writer().writeByte(si.typeInput);
            }
            pl.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void createForm(ISession session, int typeInput, String title, SubInput... subInputs) {
        Message msg;
        try {
            msg = new Message(-125);
            msg.writer().writeUTF(title);
            msg.writer().writeByte(subInputs.length);
            for (SubInput si : subInputs) {
                msg.writer().writeUTF(si.name);
                msg.writer().writeByte(si.typeInput);
            }
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void createFormChangePassword(Player pl) {
        createForm(pl, CHANGE_PASSWORD, "Đổi Mật Khẩu", new SubInput("Nhập mật khẩu hiện tại", PASSWORD),
                new SubInput("Mật khẩu mới", PASSWORD),
                new SubInput("Nhập lại mật khẩu mới", PASSWORD));
    }
    public void createFormUseGold(Player pl) {
        createForm(pl, UseGold, "Nhập số lượng cần dùng", new SubInput("1 thỏi vàng dùng sẽ được 500tr vàng", NUMERIC));
    }

    public void createFormGiftCode(Player pl) {
        createForm(pl, GIFT_CODE, "Gift code ", new SubInput("Gift-code", ANY));
    }

    public void createFormFindPlayer(Player pl) {
        createForm(pl, FIND_PLAYER, "Tìm kiếm người chơi", new SubInput("Tên người chơi", ANY));
    }
    public void createFormQDTV(Player pl) {
      
        createForm(pl, QUY_DOI_COIN, "Quy đổi thỏi vàng, 10.000 coin = 80 tv (sk x2 đên 12/12)"
              , new SubInput("Nhập số vnd muốn đổi (bỏ 3 số 0)", NUMERIC));
    }
    public void createFormQDN(Player pl) {
      
        createForm(pl, QUY_DOI_NGOC, "Quy đổi hồng ngọc, 10.000 coin = 40.000 hn (sk x2 đên 12/12) "
              , new SubInput("Nhập số vnd muốn đổi (bỏ 3 số 0)", NUMERIC));
    }
    public void createFormChangeName(Player pl, Player plChanged) {
        PLAYER_ID_OBJECT.put((int) pl.id, plChanged);
        createForm(pl, CHANGE_NAME, "Đổi tên " + plChanged.name, new SubInput("Tên mới", ANY));
    }
    
    public void createFormChangeNameByItem(Player pl) {
        createForm(pl, CHANGE_NAME_BY_ITEM, "Đổi tên " + pl.name, new SubInput("Tên mới", ANY));
    }

    public void createFormChooseLevelBDKB(Player pl) {
        createForm(pl, CHOOSE_LEVEL_BDKB, "Chọn cấp độ", new SubInput("Cấp độ (1-110)", NUMERIC));
    }
   public void createFormChooseLevelGas(Player pl) {
        createForm(pl, CHOOSE_LEVEL_GAS, "Chọn cấp độ", new SubInput("Cấp độ (1-110)", NUMERIC));
    }
    public void XIU_taixiu(Player pl) {
        createForm(pl, XIU_taixiu, "Chọn số hồng ngọc đặt Xỉu", new SubInput("Số Hồng ngọc cược", ANY));//????
    }
    public void TAI_taixiu(Player pl) {
        createForm(pl, TAI_taixiu, "Chọn số hồng ngọc đặt Tài", new SubInput("Số Hồng ngọc cược", ANY));//????
    }
    public void CHAN(Player pl) {
        createForm(pl, CHAN, "Nhập số thỏi vàng đặt chẵn", new SubInput("Số thỏi vàng", ANY));
    }

    public void LE(Player pl) {
        createForm(pl, LE, "Nhập số thỏi vàng đặt lẻ", new SubInput("Số thỏi vàng", ANY));
    }
public void createFormBuffItemVip(Player pl) {
        createForm(pl, BUFF_ITEM_VIP, "BUFF VIP", new SubInput("Tên người chơi", ANY), new SubInput("Id Item", ANY), new SubInput("Chuỗi option vd : 50-20&30-1", ANY), new SubInput("Số lượng", ANY));
    }

    public static class SubInput {

        private String name;
        private byte typeInput;

        public SubInput(String name, byte typeInput) {
            this.name = name;
            this.typeInput = typeInput;
        }
    }
    public void addItemGiftCodeToPlayer(Player p, final String giftcode) {
        try {
            final GirlkunResultSet red = GirlkunDB.executeQuery("SELECT * FROM `giftcode` WHERE `code` LIKE '" + Util.strSQL(giftcode) + "' LIMIT 1;");
            if (red.first()) {
                String text = "Mã quà tặng" + ": " + giftcode + "\b- " + "Phần quà của bạn là:" + "\b";
                final byte type = red.getByte("type");
                int limit = 9999;
                final boolean isDelete = red.getBoolean("Delete");
                final boolean isCheckbag = red.getBoolean("bagCount");
                final JSONArray listUser = (JSONArray) JSONValue.parseWithException("[]");
                final JSONArray listItem = (JSONArray)JSONValue.parseWithException(red.getString("listItem"));
                final JSONArray option= (JSONArray) JSONValue.parseWithException(red.getString("itemoption"));
                if (limit == 0) {
                    NpcService.gI().createTutorial(p,24, "Số lượng mã quà tặng này đã hết.");
                }
                else {
                    if (type == 1) {
                        for (int i = 0; i < listUser.size(); ++i) {
                            final int playerId = Integer.parseInt(listUser.get(i).toString());
                            if (playerId == p.id) {
                                NpcService.gI().createTutorial(p,24, "Mỗi tài khoản chỉ được phép sử dụng mã quà tặng này 1 lần duy nhất.");
                                return;
                            }
                        }
                    }
                    if (isCheckbag && listItem.size() > InventoryServiceNew.gI().getCountEmptyBag(p)) {
                        NpcService.gI().createTutorial(p,24,  "Hành trang cần phải có ít nhất " + listItem.size() + " ô trống để nhận vật phẩm");
                    }
                    else {
                        for (int i = 0; i < listItem.size(); ++i) {
                            final JSONObject item = (JSONObject)listItem.get(i);
                            final int idItem = Integer.parseInt(item.get("id").toString());
                            final int quantity = Integer.parseInt(item.get("quantity").toString());
                            
                            
                            if (idItem == -1) {
                                p.inventory.gold = Math.min(p.inventory.gold + (long) quantity, Inventory.LIMIT_GOLD);
                                text += quantity + " vàng\b";
                            } else if (idItem == -2) {
                                p.inventory.gem = Math.min(p.inventory.gem + quantity, 2000000000);
                                text += quantity + " ngọc\b";
                            } else if (idItem == -3) {
                                p.inventory.ruby = Math.min(p.inventory.ruby + quantity, 2000000000);
                                text += quantity + " ngọc khóa\b";
                            } else {
                                Item itemGiftTemplate = ItemService.gI().createNewItem((short) idItem);
                                
                                itemGiftTemplate.quantity = quantity;
                                if(option!=null){
                    for(int u = 0;u<option.size();u++){
                    JSONObject jsonobject = (JSONObject) option.get(u);
                            itemGiftTemplate.itemOptions.add(new Item.ItemOption(Integer.parseInt(jsonobject.get("id").toString()),Integer.parseInt(jsonobject.get("param").toString())));
                                                      
                           
                            }
                    
                            }
                                text += "x" + quantity + " " + itemGiftTemplate.template.name + "\b";
                                InventoryServiceNew.gI().addItemBag(p, itemGiftTemplate);
                                InventoryServiceNew.gI().sendItemBags(p);
                            }

                            if (i < listItem.size() - 1) {
                                text += "";
                            }
                        }
                        if (limit != -1) {
                            --limit;
                        }
                        listUser.add(p.id);
                        GirlkunDB.executeUpdate("UPDATE `giftcode` SET `limit` = " + limit + ", `listUser` = '" + listUser.toJSONString() + "' WHERE `code` LIKE '" + Util.strSQL(giftcode) + "';");
                        NpcService.gI().createTutorial(p,24, text);
                    }
                }
            }
            else {
                NpcService.gI().createTutorial(p,24, "Mã quà tặng không tồn tại hoặc đã được sử dụng");
            }
        }
        catch (Exception e) {
            NpcService.gI().createTutorial(p,24,  "Có lỗi sảy ra  hãy báo ngay cho QTV để khắc phục.");
            e.printStackTrace();
        }
    }


}
