package com.girlkun.models.player;

import BoMong.BoMong;
import com.arriety.card.Card;
import com.girlkun.models.map.MapMaBu.MapMaBu;
import com.girlkun.models.skill.PlayerSkill;

import java.util.List;
//import com.girlkun.Manager.ArchivementManager;
import com.girlkun.models.clan.Clan;
import com.girlkun.models.intrinsic.IntrinsicPlayer;
import com.girlkun.models.map.gas.GasService;
import com.girlkun.models.item.Item;
import com.girlkun.models.item.ItemTime;
import com.girlkun.models.npc.specialnpc.MagicTree;
import com.girlkun.consts.ConstPlayer;
import com.girlkun.consts.ConstTask;
import com.girlkun.models.npc.specialnpc.MabuEgg;
import com.girlkun.models.mob.MobMe;
import com.girlkun.data.DataGame;
import com.girlkun.models.clan.ClanMember;
import com.girlkun.models.map.TrapMap;
import com.girlkun.models.map.Zone;
import com.girlkun.models.map.blackball.BlackBallWar;
import com.girlkun.models.matches.IPVP;
import com.girlkun.models.matches.TYPE_LOSE_PVP;
import com.girlkun.models.matches.TYPE_PVP;
import com.girlkun.models.npc.specialnpc.BillEgg;
import com.girlkun.models.skill.Skill;
import com.girlkun.server.Manager;
import com.girlkun.services.Service;
import com.girlkun.server.io.MySession;
import com.girlkun.models.task.TaskPlayer;
import com.girlkun.network.io.Message;
import com.girlkun.server.Client;
import com.girlkun.services.*;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.FriendAndEnemyService;
import com.girlkun.services.PetService;
import com.girlkun.services.TaskService;
import com.girlkun.services.func.ChangeMapService;
import com.girlkun.services.func.CombineNew;
import com.girlkun.services.func.TopService;
import com.girlkun.utils.Logger;
import com.girlkun.utils.Util;
import com.zaxxer.hikari.util.ConcurrentBag;
import com.girlkun.models.mob.Mob;
import com.girlkun.services.SkillService;
import com.girlkun.utils.SkillUtil;

import java.util.ArrayList;
import lombok.Getter;

public class Player {

    public int goldChallenge;
    public boolean receivedWoodChest;
    public List<String> textRuongGo = new ArrayList<>();

    // private Archivement archivement;

    private MySession session;

    public boolean beforeDispose;

    public boolean isPet;
    public boolean isNewPet;
    public boolean isNewPet1;
    public boolean isBoss;
    public IPVP pvp;
    public int pointPvp;
    public byte maxTime = 30;
    public byte type = 0;

    public int mapIdBeforeLogout;
    public List<Zone> mapBlackBall;
    public List<Zone> mapMaBu;

    public Zone zone;
    public Zone mapBeforeCapsule;
    public List<Zone> mapCapsule;
    public Pet pet;
    public NewPet newpet;
    public NewPet newpet1;
    public MobMe mobMe;
    public Location location;
    public SetClothes setClothes;
    public EffectSkill effectSkill;
    public MabuEgg mabuEgg;
    public BillEgg billEgg;
    public TaskPlayer playerTask;
    public ItemTime itemTime;
    public Fusion fusion;
    public MagicTree magicTree;
    public IntrinsicPlayer playerIntrinsic;
    public Inventory inventory;
    public PlayerSkill playerSkill;
    public CombineNew combineNew;
    public IDMark iDMark;
    public Charms charms;
    public EffectSkin effectSkin;
    public Gift gift;
    public NPoint nPoint;
    public RewardBlackBall rewardBlackBall;
    public EffectFlagBag effectFlagBag;
    public FightMabu fightMabu;
    public SkillSpecial skillSpecial;
    public int goldTai;
    public int goldXiu;

    public Clan clan;
    public ClanMember clanMember;

    public List<Friend> friends;
    public List<Enemy> enemies;
    public BoMong achievement;
    public PlayerEventData playerEventData;
    public long id;
    public String name;
    public byte gender;
    public boolean isNewMember;
    public short head;
    public int cuoc;
    public int cuoc1;
    public int rubyWin = 0;

    public byte typePk;

    public byte cFlag;

    public boolean haveTennisSpaceShip;

    public boolean justRevived;
    public long lastTimeRevived;

    public int violate;
    public byte totalPlayerViolate;
    public long timeChangeZone;
    public long lastTimeUseOption;

    public short idNRNM = -1;
    public short idGo = -1;
    public long lastTimePickNRNM;
    public int goldNormar;
    public int goldVIP;
    public Player pkbahatmit;
    public long lastTimeWin;
    public boolean isWin;
    public List<Card> Cards = new ArrayList<>();
    public short idAura = -1;
    public int levelWoodChest;
    public int vnd;

    public Player() {
        lastTimeUseOption = System.currentTimeMillis();
        location = new Location();
        nPoint = new NPoint(this);
        inventory = new Inventory();
        playerSkill = new PlayerSkill(this);
        setClothes = new SetClothes(this);
        effectSkill = new EffectSkill(this);
        fusion = new Fusion(this);
        playerIntrinsic = new IntrinsicPlayer();
        rewardBlackBall = new RewardBlackBall(this);
        effectFlagBag = new EffectFlagBag();
        fightMabu = new FightMabu(this);
        // ----------------------------------------------------------------------
        iDMark = new IDMark();
        combineNew = new CombineNew();
        playerTask = new TaskPlayer();
        friends = new ArrayList<>();
        enemies = new ArrayList<>();
        itemTime = new ItemTime(this);
        charms = new Charms();
        gift = new Gift(this);
        effectSkin = new EffectSkin(this);
        skillSpecial = new SkillSpecial(this);
        achievement = new BoMong(this);
        playerEventData = new PlayerEventData(this);

        nPoint.calPoint();
    }

    // --------------------------------------------------------------------------
    public boolean isDie() {
        if (this.nPoint != null) {
            return this.nPoint.hp <= 0;
        }
        return true;
    }

    // --------------------------------------------------------------------------
    public void setSession(MySession session) {
        this.session = session;
    }

    public void sendMessage(Message msg) {
        if (this.session != null) {
            session.sendMessage(msg);
        }
    }

    public MySession getSession() {
        return this.session;
    }

    public boolean isPl() {
        return !isPet && !isBoss && !isNewPet && !isNewPet1;
    }

    public void chat(String text) {
        Service.gI().chat(this, text);
    }

    public void update() {
        if (!this.beforeDispose) {
            try {
                if (!iDMark.isBan()) {

                    if (nPoint != null) {
                        nPoint.update();
                    }
                    if (fusion != null) {
                        fusion.update();
                    }
                    if (effectSkin != null) {
                        effectSkill.update();
                    }
                    if (mobMe != null) {
                        mobMe.update();
                    }
                    if (effectSkin != null) {
                        effectSkin.update();
                    }
                    if (pet != null) {
                        pet.update();
                    }
                    if (newpet != null) {
                        newpet.update();
                    }
                    if (this.isPl() && this.clan != null && this.clan.khiGas != null) {
                        GasService.gI().update(this);
                    }
                    if (newpet1 != null) {
                        newpet1.update();
                    }
                    if (magicTree != null) {
                        magicTree.update();
                    }
                    if (itemTime != null) {
                        itemTime.update();
                    }
                    BlackBallWar.gI().update(this);
                    MapMaBu.gI().update(this);
                    if (!isBoss && this.iDMark.isGotoFuture()
                            && Util.canDoWithTime(this.iDMark.getLastTimeGoToFuture(), 6000)) {
                        ChangeMapService.gI().changeMapBySpaceShip(this, 102, -1, Util.nextInt(60, 200));
                        this.iDMark.setGotoFuture(false);
                    }
                    if (this.iDMark.isGoToBDKB() && Util.canDoWithTime(this.iDMark.getLastTimeGoToBDKB(), 6000)) {
                        ChangeMapService.gI().changeMapBySpaceShip(this, 135, -1, 35);
                        this.iDMark.setGoToBDKB(false);
                    }
                    if (this.iDMark.isGoToGas() && Util.canDoWithTime(this.iDMark.getLastTimeGotoGas(), 6000)) {
                        // ChangeMapService.gI().changeMapBySpaceShip(this, 149, -1, 163);
                        ChangeMapService.gI().changeMapBySpaceShip(this, 149, -1, 163);
                        this.iDMark.setGoToGas(false);
                    }

                    if (this.zone != null) {
                        TrapMap trap = this.zone.isInTrap(this);
                        if (trap != null) {
                            trap.doPlayer(this);
                        }
                    }
                    if (this.isPl() && this.inventory.itemsBody.get(7) != null) {
                        Item it = this.inventory.itemsBody.get(7);
                        if (it != null && it.isNotNullItem() && this.newpet == null) {
                            PetService.Pet2(this, it.template.head, it.template.body, it.template.leg);
                            Service.getInstance().point(this);
                        }
                    } else if (this.isPl() && newpet != null && newpet1 != null
                            && !this.inventory.itemsBody.get(7).isNotNullItem()) {
                        newpet.dispose();
                        newpet = null;
                        newpet1.dispose();
                        newpet1 = null;
                    }
                    if (this.isPl() && isWin && this.zone.map.mapId == 51 && Util.canDoWithTime(lastTimeWin, 2000)) {
                        ChangeMapService.gI().changeMapBySpaceShip(this, 52, 0, -1);
                        isWin = false;
                    }
                    // custom-drsylas
                    // mất kết nối - kick khi treo 30p
                    // if (location.lastTimeplayerMove < System.currentTimeMillis() - 30 * 60 *
                    // 1000) {
                    // Client.gI().kickSession(getSession());
                    // }
                } else {
                    if (Util.canDoWithTime(iDMark.getLastTimeBan(), 5000)) {
                        Client.gI().kickSession(session);
                    }
                }
            } catch (Exception e) {
                e.getStackTrace();
                Logger.logException(Player.class, e, "Lỗi tại player: " + this.name);
            }
        }
    }

    // --------------------------------------------------------------------------
    /*
     * {380, 381, 382}: ht lưỡng long nhất thể xayda trái đất
     * {383, 384, 385}: ht porata xayda trái đất
     * {391, 392, 393}: ht namếc
     * {870, 871, 872}: ht c2 trái đất
     * {873, 874, 875}: ht c2 namếc
     * {867, 878, 869}: ht c2 xayda
     */
    private static final short[][] idOutfitFusion = {
            { 383, 384, 385 }, { 383, 384, 385 }, { 391, 392, 393 }, // bt1
            { 870, 871, 872 }, { 873, 874, 875 }, { 867, 868, 869 }, // bt2 td,nm,xd
            { 2097, 2098, 2099 }, { 2094, 2095, 2096 }, { 2091, 2092, 2093 }, // bt3
            { 2062, 2063, 2064 }, { 2056, 2057, 2058 }, { 2053, 2054, 2055 }, // bt4
            { 1274, 1275, 1276 }, { 1277, 1278, 1279 }, { 1271, 1272, 1273 }, // bt4
            { 1440, 1441, 1442 },
    };

    // Sua id vat pham muon co aura lai
    public byte getAura() {
        return (byte) this.idAura;
        // if (this.inventory.itemsBody.isEmpty() || this.inventory.itemsBody.size() <
        // 10) {
        // return -1;
        // }
        // Item item = this.inventory.itemsBody.get(5);
        // if (!item.isNotNullItem()) {
        // return -1;
        // }
        // if (item.template.id == 1284) {
        // return 55;
        // } if (item.template.id == 1285) {
        // return 6;
        // } if (item.template.id == 1286) {
        // return 22;
        // }if (item.template.id == 1259) {
        // return 18;
        // }if (item.template.id == 1260) {
        // return 1;
        // }if (item.template.id == 1261) {
        // return 5;
        // }if (item.template.id == 1288) {
        // return 17;
        // }if (item.template.id == 2011) {
        // return 23;
        // }if (item.template.id == 2012) {
        // return 23;
        // }if (item.template.id == 1018) {
        // return 21;
        // }if (item.template.id == 1264) {
        // return 20;
        // }if (item.template.id == 1265) {
        // return 54;
        // }if (item.template.id == 1266) {
        // return 4;
        // }if (item.template.id == 1262) {
        // return 18;
        // }else {
        // return -1;
        // }

    }

    // hieu ung theo set
    public byte getEffFront() {
        if (this.inventory.itemsBody.isEmpty() || this.inventory.itemsBody.size() < 10) {
            return -1;
        }
        int levelAo = 0;
        Item.ItemOption optionLevelAo = null;
        int levelQuan = 0;
        Item.ItemOption optionLevelQuan = null;
        int levelGang = 0;
        Item.ItemOption optionLevelGang = null;
        int levelGiay = 0;
        Item.ItemOption optionLevelGiay = null;
        int levelNhan = 0;
        Item.ItemOption optionLevelNhan = null;
        Item itemAo = this.inventory.itemsBody.get(0);
        Item itemQuan = this.inventory.itemsBody.get(1);
        Item itemGang = this.inventory.itemsBody.get(2);
        Item itemGiay = this.inventory.itemsBody.get(3);
        Item itemNhan = this.inventory.itemsBody.get(4);
        for (Item.ItemOption io : itemAo.itemOptions) {
            if (io.optionTemplate.id == 72) {
                levelAo = io.param;
                optionLevelAo = io;
                break;
            }
        }
        for (Item.ItemOption io : itemQuan.itemOptions) {
            if (io.optionTemplate.id == 72) {
                levelQuan = io.param;
                optionLevelQuan = io;
                break;
            }
        }
        for (Item.ItemOption io : itemGang.itemOptions) {
            if (io.optionTemplate.id == 72) {
                levelGang = io.param;
                optionLevelGang = io;
                break;
            }
        }
        for (Item.ItemOption io : itemGiay.itemOptions) {
            if (io.optionTemplate.id == 72) {
                levelGiay = io.param;
                optionLevelGiay = io;
                break;
            }
        }
        for (Item.ItemOption io : itemNhan.itemOptions) {
            if (io.optionTemplate.id == 72) {
                levelNhan = io.param;
                optionLevelNhan = io;
                break;
            }
        }
        if (optionLevelAo != null && optionLevelQuan != null && optionLevelGang != null && optionLevelGiay != null
                && optionLevelNhan != null
                && levelAo >= 8 && levelQuan >= 8 && levelGang >= 8 && levelGiay >= 8 && levelNhan >= 8) {
            return 8;
        } else if (optionLevelAo != null && optionLevelQuan != null && optionLevelGang != null
                && optionLevelGiay != null && optionLevelNhan != null
                && levelAo >= 7 && levelQuan >= 7 && levelGang >= 7 && levelGiay >= 7 && levelNhan >= 7) {
            return 7;
        } else if (optionLevelAo != null && optionLevelQuan != null && optionLevelGang != null
                && optionLevelGiay != null && optionLevelNhan != null
                && levelAo >= 6 && levelQuan >= 6 && levelGang >= 6 && levelGiay >= 6 && levelNhan >= 6) {
            return 6;
        } else if (optionLevelAo != null && optionLevelQuan != null && optionLevelGang != null
                && optionLevelGiay != null && optionLevelNhan != null
                && levelAo >= 5 && levelQuan >= 5 && levelGang >= 5 && levelGiay >= 5 && levelNhan >= 5) {
            return 5;
        } else if (optionLevelAo != null && optionLevelQuan != null && optionLevelGang != null
                && optionLevelGiay != null && optionLevelNhan != null
                && levelAo >= 4 && levelQuan >= 4 && levelGang >= 4 && levelGiay >= 4 && levelNhan >= 4) {
            return 4;
        } else {
            return -1;
        }
    }
    
    private short getEffectSkin(int index){
        if (this.effectSkin.isMabuCopying()) {
            return this.effectSkin.bodyCopy[index];
        }
        // Event halloween
        if(this.effectSkin.isBiHoaXuong && this.effectSkill != null){
            return this.effectSkin.bodyHalloween[index];
        }
        // Event trung thu
        if(this.effectSkin.isCarrot){
            return this.effectSkin.bodyCarrot[index];
        }
        return -1;
    }

    public short getHead() {
        short effSkin = getEffectSkin(0);
        if(effSkin != -1) return effSkin;
        
        if (effectSkill != null && effectSkill.isMonkey) {
            return (short) ConstPlayer.HEADMONKEY[effectSkill.levelMonkey - 1];
        } else if (effectSkill != null && effectSkill.isCaiBinhChua) {
            return 2087; // @
        } else if (effectSkill != null && effectSkill.isSocola) {
            return 412;
        } else if (fusion != null && fusion.typeFusion != ConstPlayer.NON_FUSION) {
            if (fusion.typeFusion == ConstPlayer.LUONG_LONG_NHAT_THE) {
                return idOutfitFusion[this.gender == ConstPlayer.NAMEC ? 2 : 0][0];
            } else if (fusion.typeFusion == ConstPlayer.HOP_THE_PORATA) {
                if (this.inventory.itemsBody.get(5).isGOKU() && pet.inventory.itemsBody.get(5).isVEGETA()
                        || this.inventory.itemsBody.get(5).isVEGETA() && pet.inventory.itemsBody.get(5).isGOKU()) {
                    return idOutfitFusion[15][0];
                }
                return idOutfitFusion[this.gender == ConstPlayer.NAMEC ? 2 : 1][0];
            } else if (fusion.typeFusion == ConstPlayer.HOP_THE_PORATA2) {
                if (this.inventory.itemsBody.get(5).isGOKU() && pet.inventory.itemsBody.get(5).isVEGETA()
                        || this.inventory.itemsBody.get(5).isVEGETA() && pet.inventory.itemsBody.get(5).isGOKU()) {
                    return idOutfitFusion[15][0];
                }
                return idOutfitFusion[3 + this.gender][0];
            } else if (fusion.typeFusion == ConstPlayer.HOP_THE_PORATA3) {
                if (this.inventory.itemsBody.get(5).isGOKU() && pet.inventory.itemsBody.get(5).isVEGETA()
                        || this.inventory.itemsBody.get(5).isVEGETA() && pet.inventory.itemsBody.get(5).isGOKU()) {
                    return idOutfitFusion[15][0];
                }
                return idOutfitFusion[6 + this.gender][0];

            } else if (fusion.typeFusion == ConstPlayer.HOP_THE_PORATA4) {
                if (this.inventory.itemsBody.get(5).isGOKU() && pet.inventory.itemsBody.get(5).isVEGETA()
                        || this.inventory.itemsBody.get(5).isVEGETA() && pet.inventory.itemsBody.get(5).isGOKU()) {
                    return idOutfitFusion[15][0];
                }
                return idOutfitFusion[9 + this.gender][0];
            } else if (fusion.typeFusion == ConstPlayer.HOP_THE_PORATA5) {
                if (this.inventory.itemsBody.get(5).isGOKU() && pet.inventory.itemsBody.get(5).isVEGETA()
                        || this.inventory.itemsBody.get(5).isVEGETA() && pet.inventory.itemsBody.get(5).isGOKU()) {
                    return idOutfitFusion[15][0];
                }
                return idOutfitFusion[12 + this.gender][0];
            }
        } else if (inventory != null && inventory.itemsBody.get(5).isNotNullItem()) {
            int head = inventory.itemsBody.get(5).template.head;
            if (head != -1) {
                return (short) head;
            }
        }
        return this.head;
    }

    public short getBody() {
        short effSkin = getEffectSkin(1);
        if(effSkin != -1) return effSkin;
        if (effectSkill != null && effectSkill.isMonkey) {
            return 193;
        } else if (effectSkill != null && effectSkill.isCaiBinhChua) {
            return 2088; // @
        } else if (effectSkill != null && effectSkill.isSocola) {
            return 413;
        } else if (fusion != null && fusion.typeFusion != ConstPlayer.NON_FUSION) {
            if (fusion.typeFusion == ConstPlayer.LUONG_LONG_NHAT_THE) {
                return idOutfitFusion[this.gender == ConstPlayer.NAMEC ? 2 : 0][1];
            } else if (fusion.typeFusion == ConstPlayer.HOP_THE_PORATA) {
                if (this.inventory.itemsBody.get(5).isGOKU() && pet.inventory.itemsBody.get(5).isVEGETA()
                        || this.inventory.itemsBody.get(5).isVEGETA() && pet.inventory.itemsBody.get(5).isGOKU()) {
                    return idOutfitFusion[15][1];
                }
                return idOutfitFusion[this.gender == ConstPlayer.NAMEC ? 2 : 1][1];
            } else if (fusion.typeFusion == ConstPlayer.HOP_THE_PORATA2) {
                if (this.inventory.itemsBody.get(5).isGOKU() && pet.inventory.itemsBody.get(5).isVEGETA()
                        || this.inventory.itemsBody.get(5).isVEGETA() && pet.inventory.itemsBody.get(5).isGOKU()) {
                    return idOutfitFusion[15][1];
                }
                return idOutfitFusion[3 + this.gender][1];
            } else if (fusion.typeFusion == ConstPlayer.HOP_THE_PORATA3) {
                if (this.inventory.itemsBody.get(5).isGOKU() && pet.inventory.itemsBody.get(5).isVEGETA()
                        || this.inventory.itemsBody.get(5).isVEGETA() && pet.inventory.itemsBody.get(5).isGOKU()) {
                    return idOutfitFusion[15][1];
                }
                return idOutfitFusion[6 + this.gender][1];
            } else if (fusion.typeFusion == ConstPlayer.HOP_THE_PORATA4) {
                if (this.inventory.itemsBody.get(5).isGOKU() && pet.inventory.itemsBody.get(5).isVEGETA()
                        || this.inventory.itemsBody.get(5).isVEGETA() && pet.inventory.itemsBody.get(5).isGOKU()) {
                    return idOutfitFusion[15][1];
                }
                return idOutfitFusion[9 + this.gender][1];
            } else if (fusion.typeFusion == ConstPlayer.HOP_THE_PORATA5) {
                if (this.inventory.itemsBody.get(5).isGOKU() && pet.inventory.itemsBody.get(5).isVEGETA()
                        || this.inventory.itemsBody.get(5).isVEGETA() && pet.inventory.itemsBody.get(5).isGOKU()) {
                    return idOutfitFusion[15][1];
                }
                return idOutfitFusion[12 + this.gender][1];
            }
        } else if (inventory != null && inventory.itemsBody.get(5).isNotNullItem()) {
            int body = inventory.itemsBody.get(5).template.body;
            if (body != -1) {
                return (short) body;
            }
        }
        if (inventory != null && inventory.itemsBody.get(0).isNotNullItem()) {
            return inventory.itemsBody.get(0).template.part;
        }
        return (short) (gender == ConstPlayer.NAMEC ? 59 : 57);
    }

    public short getLeg() {
        short effSkin = getEffectSkin(2);
        if(effSkin != -1) return effSkin;
        if (effectSkill != null && effectSkill.isMonkey) {
            return 194;
        } else if (effectSkill != null && effectSkill.isCaiBinhChua) {
            return 2089; // @
        } else if (effectSkill != null && effectSkill.isSocola) {
            return 414;
        } else if (fusion != null && fusion.typeFusion != ConstPlayer.NON_FUSION) {
            if (fusion.typeFusion == ConstPlayer.LUONG_LONG_NHAT_THE) {
                return idOutfitFusion[this.gender == ConstPlayer.NAMEC ? 2 : 0][2];
            } else if (fusion.typeFusion == ConstPlayer.HOP_THE_PORATA) {
                if (this.inventory.itemsBody.get(5).isGOKU() && pet.inventory.itemsBody.get(5).isVEGETA()
                        || this.inventory.itemsBody.get(5).isVEGETA() && pet.inventory.itemsBody.get(5).isGOKU()) {
                    return idOutfitFusion[15][2];
                }
                return idOutfitFusion[this.gender == ConstPlayer.NAMEC ? 2 : 1][2];
            } else if (fusion.typeFusion == ConstPlayer.HOP_THE_PORATA2) {
                if (this.inventory.itemsBody.get(5).isGOKU() && pet.inventory.itemsBody.get(5).isVEGETA()
                        || this.inventory.itemsBody.get(5).isVEGETA() && pet.inventory.itemsBody.get(5).isGOKU()) {
                    return idOutfitFusion[15][2];
                }
                return idOutfitFusion[3 + this.gender][2];
            } else if (fusion.typeFusion == ConstPlayer.HOP_THE_PORATA3) {
                if (this.inventory.itemsBody.get(5).isGOKU() && pet.inventory.itemsBody.get(5).isVEGETA()
                        || this.inventory.itemsBody.get(5).isVEGETA() && pet.inventory.itemsBody.get(5).isGOKU()) {
                    return idOutfitFusion[15][2];
                }
                return idOutfitFusion[6 + this.gender][2];
            } else if (fusion.typeFusion == ConstPlayer.HOP_THE_PORATA4) {
                if (this.inventory.itemsBody.get(5).isGOKU() && pet.inventory.itemsBody.get(5).isVEGETA()
                        || this.inventory.itemsBody.get(5).isVEGETA() && pet.inventory.itemsBody.get(5).isGOKU()) {
                    return idOutfitFusion[15][2];
                }
                return idOutfitFusion[9 + this.gender][2];
            } else if (fusion.typeFusion == ConstPlayer.HOP_THE_PORATA5) {
                if (this.inventory.itemsBody.get(5).isGOKU() && pet.inventory.itemsBody.get(5).isVEGETA()
                        || this.inventory.itemsBody.get(5).isVEGETA() && pet.inventory.itemsBody.get(5).isGOKU()) {
                    return idOutfitFusion[15][2];
                }
                return idOutfitFusion[12 + this.gender][2];
            }
        } else if (inventory != null && inventory.itemsBody.get(5).isNotNullItem()) {
            int leg = inventory.itemsBody.get(5).template.leg;
            if (leg != -1) {
                return (short) leg;
            }
        }
        if (inventory != null && inventory.itemsBody.get(1).isNotNullItem()) {
            return inventory.itemsBody.get(1).template.part;
        }
        return (short) (gender == 1 ? 60 : 58);
    }

    public short getFlagBag() {
        if (this.iDMark.isHoldBlackBall()) {
            return 31;
        } else if (this.idNRNM >= 353 && this.idNRNM <= 359) {
            return 30;
        }
        if (this.inventory.itemsBody.size() >= 10) {
            if (this.inventory.itemsBody.get(8).isNotNullItem()) {
                return this.inventory.itemsBody.get(8).template.part;
            }
        }
        if (TaskService.gI().getIdTask(this) == ConstTask.TASK_3_2) {
            return 28;
        }
        if (this.clan != null) {
            return (short) this.clan.imgId;
        }
        return -1;
    }

    public short getMount() {
        if (this.inventory.itemsBody.isEmpty() || this.inventory.itemsBody.size() < 10) {
            return -1;
        }
        Item item = this.inventory.itemsBody.get(9);
        if (!item.isNotNullItem()) {
            return -1;
        }
        if (item.template.type == 24) {
            if (item.template.gender == 3 || item.template.gender == this.gender) {
                return item.template.id;
            } else {
                return -1;
            }
        } else {
            if (item.template.id < 500) {
                return item.template.id;
            } else {
                return (short) DataGame.MAP_MOUNT_NUM.get(String.valueOf(item.template.id));
            }
        }

    }

    // --------------------------------------------------------------------------
    public int injured(Player plAtt, int damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            if (plAtt != null) {
                switch (plAtt.playerSkill.skillSelect.template.id) {
                    case Skill.KAMEJOKO:
                    case Skill.MASENKO:
                    case Skill.ANTOMIC:
                        if (this.nPoint.voHieuChuong > 0) {
                            com.girlkun.services.PlayerService.gI().hoiPhuc(this, 0,
                                    damage * this.nPoint.voHieuChuong / 100);
                            return 0;
                        }
                }
            }
            if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 100)) {
                return 0;
            }
            damage = this.nPoint.subDameInjureWithDeff(damage);
            if (!piercing && effectSkill.isShielding) {
                if (damage > nPoint.hpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
                damage = 1;
            }
            if (isMobAttack && this.charms.tdBatTu > System.currentTimeMillis() && damage >= this.nPoint.hp) {
                damage = this.nPoint.hp - 1;
            }

            this.nPoint.subHP(damage);
            // custom-drsylas
            // Đệ bảo vệ sư phụ
            if (plAtt != null && this.pet != null) {
                pet.enemyMaster = plAtt;
                pet.chatToNewEnemy();
            }
            if (plAtt != null && !isMobAttack && this.nPoint.tlPST > 0) {
                int damageReflect = damage * this.nPoint.tlPST / 100;
                if (damageReflect > 0) {
                    plAtt.injured(this, damageReflect, true, false);
                }
            }
            if (isDie()) {
                if (this.zone.map.mapId == 112) {
                    plAtt.pointPvp++;
                }
                // Cải trang Saibamen
                if (plAtt != null && Util.isTrue(50, 100)) {
                    this.effectSkin.activeSaibamen(plAtt);
                }
                setDie(plAtt);
            }
            return damage;
        } else {
            return 0;
        }
    }

    protected void setDie(Player plAtt) {
        // xóa phù
        if (this.effectSkin.xHPKI > 1) {
            this.effectSkin.xHPKI = 1;
            Service.gI().point(this);
        }
        // xóa tụ skill đặc biệt
        this.playerSkill.prepareQCKK = false;
        this.playerSkill.prepareLaze = false;
        this.playerSkill.prepareTuSat = false;
        // xóa hiệu ứng skill
        this.effectSkill.removeSkillEffectWhenDie();
        //
        nPoint.setHp(0);
        nPoint.setMp(0);
        // xóa trứng
        if (this.mobMe != null) {
            this.mobMe.mobMeDie();
        }
        Service.gI().charDie(this);
        // add kẻ thù
        if (!this.isPet && !this.isNewPet && !this.isNewPet1 && !this.isBoss && plAtt != null && !plAtt.isPet
                && !plAtt.isNewPet && !plAtt.isNewPet1 && !plAtt.isBoss) {
            if (!plAtt.itemTime.isUseAnDanh) {
                FriendAndEnemyService.gI().addEnemy(this, plAtt);
            }
        }
        if (this.isPl() && plAtt != null && plAtt.isPl()) {
            plAtt.achievement.plusCount(3);
        }
        // kết thúc pk
        if (this.pvp != null) {
            this.pvp.lose(this, TYPE_LOSE_PVP.DEAD);
        }
        // PVPServcice.gI().finishPVP(this, PVP.TYPE_DIE);
        BlackBallWar.gI().dropBlackBall(this);
    }

    // --------------------------------------------------------------------------
    public void setClanMember() {
        if (this.clanMember != null) {
            this.clanMember.powerPoint = this.nPoint.power;
            this.clanMember.head = this.getHead();
            this.clanMember.body = this.getBody();
            this.clanMember.leg = this.getLeg();
        }
    }

    public boolean isAdmin() {
        return this.session.isAdmin;
    }

    public void setJustRevivaled() {
        this.justRevived = true;
        this.lastTimeRevived = System.currentTimeMillis();
    }

    public void preparedToDispose() {

    }

    public void dispose() {
        if (pet != null) {
            pet.dispose();
            pet = null;
        }
        if (newpet != null) {
            newpet.dispose();
            newpet = null;
        }
        if (newpet1 != null) {
            newpet1.dispose();
            newpet1 = null;
        }
        if (mapBlackBall != null) {
            mapBlackBall.clear();
            mapBlackBall = null;
        }
        zone = null;
        mapBeforeCapsule = null;
        if (mapMaBu != null) {
            mapMaBu.clear();
            mapMaBu = null;
        }
        if (billEgg != null) {
            billEgg.dispose();
            billEgg = null;
        }
        zone = null;
        mapBeforeCapsule = null;
        if (mapCapsule != null) {
            mapCapsule.clear();
            mapCapsule = null;
        }
        if (mobMe != null) {
            mobMe.dispose();
            mobMe = null;
        }
        location = null;
        if (setClothes != null) {
            setClothes.dispose();
            setClothes = null;
        }
        if (effectSkill != null) {
            effectSkill.dispose();
            effectSkill = null;
        }
        if (mabuEgg != null) {
            mabuEgg.dispose();
            mabuEgg = null;
        }
        if (playerTask != null) {
            playerTask.dispose();
            playerTask = null;
        }
        if (itemTime != null) {
            itemTime.dispose();
            itemTime = null;
        }
        if (fusion != null) {
            fusion.dispose();
            fusion = null;
        }
        if (magicTree != null) {
            magicTree.dispose();
            magicTree = null;
        }
        if (playerIntrinsic != null) {
            playerIntrinsic.dispose();
            playerIntrinsic = null;
        }
        if (skillSpecial != null) {
            skillSpecial.dispose();
            skillSpecial = null;
        }
        if (inventory != null) {
            inventory.dispose();
            inventory = null;
        }
        if (playerSkill != null) {
            playerSkill.dispose();
            playerSkill = null;
        }
        if (combineNew != null) {
            combineNew.dispose();
            combineNew = null;
        }
        if (iDMark != null) {
            iDMark.dispose();
            iDMark = null;
        }
        if (charms != null) {
            charms.dispose();
            charms = null;
        }
        if (effectSkin != null) {
            effectSkin.dispose();
            effectSkin = null;
        }
        if (gift != null) {
            gift.dispose();
            gift = null;
        }
        if (nPoint != null) {
            nPoint.dispose();
            nPoint = null;
        }
        if (rewardBlackBall != null) {
            rewardBlackBall.dispose();
            rewardBlackBall = null;
        }
        if (effectFlagBag != null) {
            effectFlagBag.dispose();
            effectFlagBag = null;
        }
        if (pvp != null) {
            pvp.dispose();
            pvp = null;
        }
        effectFlagBag = null;
        clan = null;
        clanMember = null;
        friends = null;
        enemies = null;
        session = null;
        name = null;
    }

    public class pkbahatmit {

        public pkbahatmit() {
        }
    }

    public Mob mobTarget;

    public long lastTimeTargetMob;

    public long timeTargetMob;

    public long lastTimeAttack;

    public void moveTo(int x, int y) {
        byte dir = (byte) (this.location.x - x < 0 ? 1 : -1);
        byte move = (byte) Util.nextInt(40, 60);
        PlayerService.gI().playerMove(this, this.location.x + (dir == 1 ? move : -move),
                y + (Util.isTrue(3, 10) ? -50 : 0));
    }

    public Mob getMobAttack() {
        if (this.mobTarget != null && (this.mobTarget.isDie() || !this.zone.equals(this.mobTarget.zone))) {
            this.mobTarget = null;
        }
        if (this.mobTarget == null && Util.canDoWithTime(lastTimeTargetMob, timeTargetMob)) {
            this.mobTarget = this.zone.getRandomMobInMap();
            this.lastTimeTargetMob = System.currentTimeMillis();
            this.timeTargetMob = 500;
        }
        return this.mobTarget;
    }

    public int getRangeCanAttackWithSkillSelect() {
        int skillId = this.playerSkill.skillSelect.template.id;
        if (skillId == Skill.KAMEJOKO || skillId == Skill.MASENKO || skillId == Skill.ANTOMIC) {
            return Skill.RANGE_ATTACK_CHIEU_CHUONG;
        } else if (skillId == Skill.DRAGON || skillId == Skill.DEMON || skillId == Skill.GALICK) {
            return Skill.RANGE_ATTACK_CHIEU_DAM;
        }
        return 752002;
    }

    public void attack() {
        // this.mobTarget = this.getMobAttack();
        if (Util.canDoWithTime(lastTimeAttack, 100) && this.mobTarget != null) {

            this.lastTimeAttack = System.currentTimeMillis();
            try {
                Mob m = this.getMobAttack();
                if (m == null || m.isDie())
                    return;

                this.playerSkill.skillSelect = this.playerSkill.skills
                        .get(Util.nextInt(0, this.playerSkill.skills.size() - 1));
                // System.out.println(m.name);
                if (Util.nextInt(100) < 70) {
                    this.playerSkill.skillSelect = this.playerSkill.skills.get(0);
                }
                if (Util.getDistance(this, m) <= this.getRangeCanAttackWithSkillSelect()) {
                    if (Util.isTrue(5, 20)) {
                        if (SkillUtil.isUseSkillChuong(this)) {
                            this.moveTo(m.location.x + (Util.getOne(-1, 1) * Util.nextInt(20, 200)),
                                    Util.nextInt(10) % 2 == 0 ? m.location.y : m.location.y);
                        } else {
                            this.moveTo(m.location.x + (Util.getOne(-1, 1) * Util.nextInt(10, 40)),
                                    Util.nextInt(10) % 2 == 0 ? m.location.y : m.location.y);
                        }
                    }
                    SkillService.gI().useSkill(this, null, m, null);
                } else {
                    this.moveTo(m.location.x, m.location.y);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.mobTarget = getMobAttack();
        }
    }
    // custom-drsylas

}
