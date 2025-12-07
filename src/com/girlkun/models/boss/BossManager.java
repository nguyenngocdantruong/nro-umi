package com.girlkun.models.boss;

import com.girlkun.models.event.list_boss.halloween.BiMa;
import com.girlkun.models.event.list_boss.halloween.MaTroi;
import com.girlkun.models.event.list_boss.halloween.Dracula;
import com.girlkun.models.event.list_boss.halloween.Doi;
import com.girlkun.models.boss.list_boss.TBR.*;
import com.girlkun.models.boss.list_boss.nappa.MapDauDinh;
import com.girlkun.models.boss.list_boss.nappa.Rambo;
import com.girlkun.models.boss.list_boss.nappa.Kuku;
import com.girlkun.models.boss.list_boss.android.Android19;
import com.girlkun.models.boss.list_boss.android.Android14;
import com.girlkun.models.boss.list_boss.android.Android13;
import com.girlkun.models.boss.list_boss.android.DrKore;
import com.girlkun.models.boss.list_boss.android.Android15;
import com.girlkun.models.boss.list_boss.android.Pic;
import com.girlkun.models.boss.list_boss.android.Poc;
import com.girlkun.models.boss.list_boss.android.SuperAndroid17;
import com.girlkun.models.boss.list_boss.android.KingKong;
import com.girlkun.models.boss.list_boss.BLACK.ZamasMax;
import com.girlkun.models.boss.list_boss.BLACK.SuperBlack2;
import com.girlkun.models.boss.list_boss.BLACK.Black;
import com.girlkun.models.boss.list_boss.haitac.*;
import com.girlkun.models.boss.list_boss.BLACK.ZamasKaio;
import com.girlkun.models.boss.list_boss.BLACK.BlackGokuTl;
import com.girlkun.models.boss.list_boss.Cooler.*;
import com.girlkun.models.boss.list_boss.sieuthi.*;
import com.girlkun.models.boss.list_boss.HuyDiet.Champa;
import com.girlkun.models.boss.list_boss.HuyDiet.ThanHuyDiet;
import com.girlkun.models.boss.list_boss.HuyDiet.ThienSuWhis;
import com.girlkun.models.boss.list_boss.HuyDiet.Vados;
import com.girlkun.models.boss.list_boss.Doraemon.Doraemon;
import com.girlkun.models.boss.list_boss.Mabu;
import com.girlkun.models.boss.list_boss.cell.Xencon;
import com.girlkun.models.boss.list_boss.ginyu.TDST;
import com.girlkun.models.boss.list_boss.cell.SieuBoHung;
import com.girlkun.models.boss.list_boss.cell.XenBoHung;
import com.girlkun.models.boss.list_boss.Broly.Broly;
import com.girlkun.models.boss.list_boss.NgucTu.SongokuTaAc;
import com.girlkun.models.boss.list_boss.fide.Fide;
import com.girlkun.models.boss.list_boss.NRD.Rong1Sao;
import com.girlkun.models.boss.list_boss.NRD.Rong2Sao;
import com.girlkun.models.boss.list_boss.NRD.Rong3Sao;
import com.girlkun.models.boss.list_boss.NRD.Rong4Sao;
import com.girlkun.models.boss.list_boss.NRD.Rong5Sao;
import com.girlkun.models.boss.list_boss.NRD.Rong6Sao;
import com.girlkun.models.boss.list_boss.NRD.Rong7Sao;
import com.girlkun.models.boss.list_boss.Mabu12h.MabuBoss;
import com.girlkun.models.boss.list_boss.Mabu12h.BuiBui;
import com.girlkun.models.boss.list_boss.Mabu12h.BuiBui2;
import com.girlkun.models.boss.list_boss.Mabu12h.Drabura;
import com.girlkun.models.boss.list_boss.Mabu12h.Drabura2;
import com.girlkun.models.boss.list_boss.Mabu12h.Yacon;
import com.girlkun.models.boss.list_boss.bojack.BojackTeam;
import com.girlkun.models.boss.list_boss.Broly.Broly;
import com.girlkun.models.boss.list_boss.New.*;
import com.girlkun.models.boss.list_boss.Broly.SuperBroly;
import com.girlkun.models.map.Zone;
import com.girlkun.consts.ConstPlayer;
import com.girlkun.models.event.list_boss.trung_thu.KhiXayda;
import com.girlkun.models.event.list_boss.trung_thu.ThoDaiCa;
import com.girlkun.models.player.Player;
import com.girlkun.models.skill.Skill;
import com.girlkun.network.io.Message;
import com.girlkun.services.ItemMapService;
import com.girlkun.services.MapService;
import com.girlkun.services.Service;
import com.girlkun.server.Manager;
import com.girlkun.services.func.ChangeMapService;
import com.girlkun.utils.Util;
import com.girlkun.utils.Logger;
import java.io.IOException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BossManager {

    private static BossManager I;
    public static final byte ratioReward = 20;

    public static BossManager gI() {
        if (BossManager.I == null) {
            BossManager.I = new BossManager();
        }
        return BossManager.I;
    }

    public int idBase = 1;

    public int idGroup = 1;
    protected ConcurrentHashMap<Integer, Long> setTimeSpawnBoss;

    private BossManager() {
        this.bosses = new CopyOnWriteArrayList<>();
        this.setTimeSpawnBoss = new ConcurrentHashMap<>();
    }

    private boolean loadedBoss;
    protected CopyOnWriteArrayList<Boss> bosses;

    public void addBoss(Boss boss) {
        bosses.add(boss);
    }

    public void removeBoss(Boss boss) {
        bosses.remove(boss);
    }

    // ------------------ LoadBoss ------------------
    public void loadBoss() {
        action(150);
        try {
            if (this.loadedBoss) {
                return;
            }
            int[] bossTypes = {
                    BossType.KUKU,
                    BossType.MAP_DAU_DINH,
                    BossType.RAMBO,
                    BossType.TDST,
                    BossType.FIDE,
                    BossType.DR_KORE,
                    BossType.KING_KONG,
                    BossType.ANDROID_14,
                    BossType.XEN_BO_HUNG,
                    BossType.XEN_CON_1,
                    BossType.XEN_CON_1,
                    BossType.XEN_CON_1,
                    BossType.XEN_CON_1,
                    BossType.SIEU_BO_HUNG,
                    BossType.DORAEMON,
                    BossType.BLACK,
                    BossType.BLACK1,
                    BossType.BLACK2,
                    BossType.ZAMASMAX,
                    BossType.ZAMASZIN,
                    BossType.THIEN_SU_VADOS,
                    BossType.THIEN_SU_WHIS,
                    BossType.SONGOKU_TA_AC,
                    BossType.COOLER,
                    BossType.VEGETA,
                    BossType.D_TANG,
                    BossType.MABU,
                    BossType.FIRE,
                    BossType.WIND,
                    BossType.ICE,
                    BossType.GAS,
                    BossType.GRANOLA,
                    BossType.Brook,
                    BossType.Mihawk,
                    BossType.Kaido,
                    BossType.Along,
                    BossType.Janemba,
                    BossType.pikkon,
                    BossType.tan,
                    BossType.nezu,
                    BossType.zen,
                    BossType.hino,
                    BossType.gojo,
                    BossType.akaza,
                    BossType.linh,
                    BossType.Luffy,
                    BossType.SUPER_ANDROID_17,
                    BossType.BROLY,
                    BossType.THOR_2,
                    BossType.MIU,
                    BossType.HO,
                    BossType.gogeta,
                    BossType.sieunhann,
                    BossType.sexyyy,
                    BossType.pan,
                    BossType.zeno,
                    BossType.vt6,
                    BossType.bergamo, };
            if(Logger.DEBUG){
                bossTypes = new int[] { } ;
            }
            for (int i = 0; i < bossTypes.length; i++) {
                switch (bossTypes[i]) {
                    case BossType.KUKU:
                    case BossType.MAP_DAU_DINH:
                    case BossType.RAMBO:
                    case BossType.TDST:
                        for (int j = 0; j < 2; j++) {
                            createBoss(bossTypes[i]);
                            Thread.sleep(1000);
                        }
                        break;
                    case BossType.BROLY:
                        for (int q = 0; q < 10; q++) {
                            createBoss(BossType.BROLY);
                            Thread.sleep(1000);
                        }
                        break;
                    case BossType.THO_DAI_CA:
                        for (int q = 0; q < 10; q++) {
                            createBoss(BossType.THO_DAI_CA);
                            Thread.sleep(1000);
                        }
                        break;
                    default:
                        createBoss(bossTypes[i]);
                        Thread.sleep(1000);
                        break;
                }
            }
            this.loadedBoss = true;
        } catch (InterruptedException e) {
        }
    }

    public Boss createBoss(int bossID) {
        try {
            switch (bossID) {
                case BossType.KHI_XAYDA:
                    return new KhiXayda();
                case BossType.THO_DAI_CA:
                    return new ThoDaiCa();
                case BossType.DRACULA_HALLOWEEN:
                    return new Dracula();
                case BossType.BI_MA:
                    return new BiMa();
                case BossType.MA_TROI:
                    return new MaTroi();
                case BossType.DOI:
                    return new Doi();
                case BossType.S_BROLY:
                    return new SuperBroly();
                case BossType.zeno:
                    return new zeno();
                case BossType.THOR_1:
                    return new thotrang();
                case BossType.THOR_2:
                    return new thoden();
                case BossType.MIU:
                    return new miu();
                case BossType.HOT:
                    return new hotrang();
                case BossType.HOV:
                    return new hovang();
                case BossType.sexy:
                    return new sexy();
                case BossType.sexyy:
                    return new sexyy();
                case BossType.sexyyy:
                    return new sexyyy();
                case BossType.sieunhan:
                    return new sieunhan();
                case BossType.sieunhann:
                    return new sieunhann();
                case BossType.pan:
                    return new pan();
                case BossType.HO:
                    return new ho();
                case BossType.akaza:
                    return new akaza();
                case BossType.linh:
                    return new linh();
                case BossType.KUKU:
                    return new Kuku();
                case BossType.pikkon:
                    return new pikkon();
                case BossType.Janemba:
                    return new Janemba();
                case BossType.gojo:
                    return new gojo();
                case BossType.hino:
                    return new hino();
                case BossType.zen:
                    return new zen();
                case BossType.tan:
                    return new tan();
                case BossType.nezu:
                    return new nezu();
                case BossType.MAP_DAU_DINH:
                    return new MapDauDinh();
                case BossType.RAMBO:
                    return new Rambo();
                case BossType.DRABURA:
                    return new Drabura();
                case BossType.DRABURA_2:
                    return new Drabura2();
                case BossType.BUI_BUI:
                    return new BuiBui();
                case BossType.BUI_BUI_2:
                    return new BuiBui2();
                case BossType.YA_CON:
                    return new Yacon();
                case BossType.MABU_12H:
                    return new MabuBoss();
                case BossType.Rong_1Sao:
                    return new Rong1Sao();
                case BossType.Rong_2Sao:
                    return new Rong2Sao();
                case BossType.Rong_3Sao:
                    return new Rong3Sao();
                case BossType.Rong_4Sao:
                    return new Rong4Sao();
                case BossType.Rong_5Sao:
                    return new Rong5Sao();
                case BossType.Rong_6Sao:
                    return new Rong6Sao();
                case BossType.Rong_7Sao:
                    return new Rong7Sao();
                case BossType.FIDE:
                    return new Fide();
                case BossType.DR_KORE:
                    return new DrKore();
                case BossType.ANDROID_19:
                    return new Android19();
                case BossType.SUPER_ANDROID_17:
                    return new SuperAndroid17();
                case BossType.gogeta:
                    return new gogeta();
                case BossType.goku:
                    return new goku();
                case BossType.ANDROID_13:
                    return new Android13();
                case BossType.ANDROID_14:
                    return new Android14();
                case BossType.ANDROID_15:
                    return new Android15();
                case BossType.PIC:
                    return new Pic();
                case BossType.POC:
                    return new Poc();
                case BossType.KING_KONG:
                    return new KingKong();
                case BossType.XEN_BO_HUNG:
                    return new XenBoHung();
                case BossType.SIEU_BO_HUNG:
                    return new SieuBoHung();
                case BossType.DORAEMON:
                    return new Doraemon();
                case BossType.D_TANG:
                    return new BojackTeam();
                case BossType.COOLER:
                    return new Cooler();
                case BossType.FIRE:
                    return new Fire();
                case BossType.WIND:
                    return new Wind();
                case BossType.ICE:
                    return new Ice();
                case BossType.basil:
                    return new basil();
                case BossType.lavender:
                    return new lavender();
                case BossType.bergamo:
                    return new bergamo();
                case BossType.OLL:
                    return new Oll();
                case BossType.ELEC:
                    return new Elec();
                case BossType.MACKL:
                    return new Mackl();
                case BossType.GAS:
                    return new Gas();
                case BossType.GRANOLA:
                    return new Granola();
                case BossType.BROLYY:
                    return new Brolyy();
                case BossType.GOKU:
                    return new Goku();
                case BossType.VEGETA:
                    return new Vegeta();
                case BossType.ZAMASMAX:
                    return new ZamasMax();
                case BossType.ZAMASZIN:
                    return new ZamasKaio();
                case BossType.BLACK2:
                    return new SuperBlack2();
                case BossType.BLACK1:
                    return new BlackGokuTl();
                case BossType.BLACK:
                    return new Black();
                case BossType.XEN_CON_1:
                    return new Xencon();
                case BossType.MABU:
                    return new Mabu();
                case BossType.Luffy:
                    return new Luffy();
                case BossType.Zoro:
                    return new Zoro();
                case BossType.Sanji:
                    return new Sanji();
                case BossType.Nami:
                    return new Nami();
                case BossType.Chopper:
                    return new Chopper();
                case BossType.Franky:
                    return new Franky();
                case BossType.Robin:
                    return new Robin();
                case BossType.Along:
                    return new Along();
                case BossType.Mihawk:
                    return new Mihawk();
                case BossType.Kaido:
                    return new Kaido();
                case BossType.Usopp:
                    return new Usopp();
                case BossType.Brook:
                    return new Brook();
                case BossType.TDST:
                    return new TDST();
                case BossType.vt6:
                    return new vt6();
                case BossType.THAN_HUY_DIET_CHAMPA:
                    return new Champa();
                case BossType.THIEN_SU_VADOS:
                    return new Vados();
                case BossType.THAN_HUY_DIET:
                    return new ThanHuyDiet();
                case BossType.THIEN_SU_WHIS:
                    return new ThienSuWhis();
                case BossType.SONGOKU_TA_AC:
                    return new SongokuTaAc();
                case BossType.BROLY:
                    return new Broly();
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public boolean existBossOnPlayer(Player player) {
        return !player.zone.getBosses().isEmpty();
    }

    public void timBoss(Player player, int id) {
        Boss boss = BossManager.gI().getBossById(id);
        if (boss != null && !boss.isDie()) {

            Zone z = null;
            if (boss.zone != null) {
                z = MapService.gI().getMapCanJoin(player, boss.zone.map.mapId,
                        boss.zone.zoneId);
            }
            if (z != null && z.getNumOfPlayers() < z.maxPlayer) {
                player.inventory.gold -= 0;
                ChangeMapService.gI().changeMapBySpaceShip(player, boss.zone, boss.location.x);
                Service.gI().sendMoney(player);
            } else {
                Service.gI().sendThongBao(player, "Khu vực đang full.");
            }
        }

    }

    public void showListBoss(Player player) {
        if (!player.isAdmin()) {
            return;
        }
        Message msg;
        try {
            msg = new Message(-96);
            msg.writer().writeByte(0);
            msg.writer().writeUTF("Boss");
            msg.writer()
                    .writeByte(
                            (int) bosses.stream()
                                    .filter(boss -> !MapService.gI().isMapMaBu(boss.data[0].getMapJoin()[0])
                                            && !MapService.gI().isMapBlackBallWar(boss.data[0].getMapJoin()[0]))
                                    .count());
            for (int i = 0; i < bosses.size(); i++) {
                Boss boss = this.bosses.get(i);
                if (MapService.gI().isMapMaBu(boss.data[0].getMapJoin()[0])
                        || MapService.gI().isMapBlackBallWar(boss.data[0].getMapJoin()[0])
                        || MapService.gI().isMapBanDoKhoBau(boss.data[0].getMapJoin()[0])
                        || MapService.gI().isMapDoanhTrai(boss.data[0].getMapJoin()[0])
                        || MapService.gI().isMapKhiGas(boss.data[0].getMapJoin()[0])) {
                    continue;
                }
                msg.writer().writeInt((int) boss.id);
                msg.writer().writeInt((int) boss.id);
                msg.writer().writeShort(boss.data[0].getOutfit()[0]);
                if (player.getSession().version > 214) {
                    msg.writer().writeShort(-1);
                }
                msg.writer().writeShort(boss.data[0].getOutfit()[1]);
                msg.writer().writeShort(boss.data[0].getOutfit()[2]);
                msg.writer().writeUTF(boss.data[0].getName());
                if (boss.zone != null) {
                    msg.writer().writeUTF("Sống");
                    msg.writer().writeUTF(
                            boss.zone.map.mapName + "(" + boss.zone.map.mapId + ") khu " + boss.zone.zoneId + "");
                } else {
                    msg.writer().writeUTF("Chết");
                    msg.writer().writeUTF("Chết rồi");
                }
            }
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.logException(Manager.class, e, "Lỗi show list boss");
        }
    }

    public synchronized void callBoss(Player player, int mapId) {
        try {
            if (BossManager.gI().existBossOnPlayer(player)
                    || player.zone.items.stream()
                            .anyMatch(itemMap -> ItemMapService.gI().isBlackBall(itemMap.itemTemplate.id))
                    || player.zone.getPlayers().stream().anyMatch(p -> p.iDMark.isHoldBlackBall())) {
                return;
            }
            Boss k = null;
            switch (mapId) {
            }
            if (k != null) {
                k.setCurrentLevel(0);
                k.joinMapByZone(player);
            }
        } catch (Exception e) {
        }
    }

    public Boss getBossByType(int type) {
        return (Boss) BossManager.gI().bosses.stream().filter(boss -> boss.getTypeBoss() == type).findFirst()
                .orElse(null);
    }

    public Boss getBossById(int bossId) {
        return BossManager.gI().bosses.stream().filter(boss -> boss.id == bossId && !boss.isDie()).findFirst()
                .orElse(null);
    }

    public void bossNotify(Boss boss) {
        if (boss.getSecondsNotify() == 0) {
            return;
        }
        if (Util.canDoWithTime(boss.getLastTimeNotify(), boss.getSecondsNotify())) {
            boss.setLastTimeNotify(System.currentTimeMillis());
        }
    }

    private Timer timer;
    private TimerTask timerTask;
    private boolean active = false;

    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public void action(int delay) {
        if (!active) {
            active = true;
            scheduler.scheduleAtFixedRate(() -> {
                updateBossTask();
                loopCreateBoss();
            }, delay, delay, TimeUnit.MILLISECONDS);
        }
    }

    public void close() {
        if (active) {
            active = false;
            scheduler.shutdown();
        }
    }

    private void loopCreateBoss() {
        for (Map.Entry<Integer, Long> entry : setTimeSpawnBoss.entrySet()) {
            if (System.currentTimeMillis() >= entry.getValue()) {
                createBoss(entry.getKey());
                setTimeSpawnBoss.remove(entry.getKey());
            }
        }
    }

    private void updateBossTask() {
        long st = System.currentTimeMillis();
        for (int i = 0; i < bosses.size(); i++) {
            Boss boss = bosses.get(i);
            if (boss != null && boss.bossInstance != null) {
                try {
                    boss.updateBoss();
                } catch (Exception e) {
                    Logger.logException(BossManager.class, e, "Error updating boss: " + boss.name);
                }
            }
        }

    }
}
