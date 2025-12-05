package com.girlkun.services;

import com.girlkun.models.item.Item;
import com.girlkun.models.item.ItemTime;
import com.girlkun.models.player.Friend;
import com.girlkun.models.player.Fusion;
import com.girlkun.models.player.Inventory;
import com.girlkun.models.player.Player;
import com.girlkun.models.skill.Skill;
import com.girlkun.server.Manager;
import com.girlkun.server.io.MySession;
import com.girlkun.utils.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Timestamp;

/**
 * Service để export SQL statements khi player logout
 * Files được lưu tại: data/account/{playerName}/
 */
public class SQLExportService {

    private static SQLExportService instance;

    public static SQLExportService gI() {
        if (instance == null) {
            instance = new SQLExportService();
        }
        return instance;
    }

    /**
     * Export SQL khi player logout
     */
    public void exportOnLogout(MySession session, Player player) {
        if (session == null || player == null) {
            return;
        }

        try {
            String folderPath = "data/account/" + player.name + "/";

            // Tạo thư mục nếu chưa có
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // Export account SQL
            String accountSQL = generateAccountSQL(session);
            writeToFile(folderPath, "account.sql", accountSQL);

            // Export player SQL
            String playerSQL = generatePlayerSQL(player);
            writeToFile(folderPath, "player.sql", playerSQL);

            Logger.log(Logger.CYAN, "Exported SQL for player: " + player.name + "\n");

        } catch (Exception e) {
            Logger.logException(SQLExportService.class, e, "Error exporting SQL for player: " + player.name);
        }
    }

    /**
     * Generate UPDATE statement cho bảng account
     */
    private String generateAccountSQL(MySession session) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE account SET\n");
        sql.append("    thoi_vang = ").append(session.goldBar).append(",\n");
        sql.append("    vnd = ").append(session.vnd).append(",\n");
        sql.append("    coin = ").append(session.coinBar).append(",\n");
        sql.append("    last_time_logout = '").append(new Timestamp(System.currentTimeMillis())).append("',\n");
        sql.append("    active = ").append(session.actived ? 1 : 0).append("\n");
        sql.append("WHERE id = ").append(session.userId).append(";\n");
        return sql.toString();
    }

    /**
     * Generate UPDATE statement cho bảng player
     * Tham khảo logic từ PlayerDAO.updatePlayer()
     */
    private String generatePlayerSQL(Player player) {
        if (!player.iDMark.isLoadedAllDataPlayer()) {
            return "-- Player data not fully loaded, cannot export\n";
        }

        try {
            JSONArray dataArray = new JSONArray();

            // data kim lượng
            dataArray.add(player.inventory.gold > Inventory.LIMIT_GOLD ? Inventory.LIMIT_GOLD : player.inventory.gold);
            dataArray.add(player.inventory.gem);
            dataArray.add(player.inventory.ruby);
            dataArray.add(player.inventory.coupon);
            dataArray.add(player.inventory.event);
            String inventory = dataArray.toJSONString();
            dataArray.clear();

            // data vị trí
            int mapId = player.mapIdBeforeLogout;
            int x = player.location.x;
            int y = player.location.y;
            int hp = player.nPoint.hp;
            int mp = player.nPoint.mp;
            if (player.isDie()) {
                mapId = player.gender + 21;
                x = 300;
                y = 336;
                hp = 1;
                mp = 1;
            } else {
                if (MapService.gI().isMapDoanhTrai(mapId) || MapService.gI().isMapBlackBallWar(mapId)
                        || MapService.gI().isMapDiaNguc(mapId) || MapService.gI().isMapKhiGas(mapId)
                        || MapService.gI().isMapBanDoKhoBau(mapId) || MapService.gI().isMapMaBu(mapId)) {
                    mapId = player.gender + 21;
                    x = 300;
                    y = 336;
                }
            }
            dataArray.add(mapId);
            dataArray.add(x);
            dataArray.add(y);
            String location = dataArray.toJSONString();
            dataArray.clear();

            // data chỉ số
            dataArray.add(player.nPoint.limitPower);
            dataArray.add(player.nPoint.power);
            dataArray.add(player.nPoint.tiemNang);
            dataArray.add(player.nPoint.stamina);
            dataArray.add(player.nPoint.maxStamina);
            dataArray.add(player.nPoint.hpg);
            dataArray.add(player.nPoint.mpg);
            dataArray.add(player.nPoint.dameg);
            dataArray.add(player.nPoint.defg);
            dataArray.add(player.nPoint.critg);
            dataArray.add(0);
            dataArray.add(hp);
            dataArray.add(mp);
            String point = dataArray.toJSONString();
            dataArray.clear();

            // data đậu thần
            dataArray.add(player.magicTree.level);
            dataArray.add(player.magicTree.currPeas);
            dataArray.add(player.magicTree.isUpgrade ? 10 : 0);
            dataArray.add(player.magicTree.lastTimeHarvest);
            dataArray.add(player.magicTree.lastTimeUpgrade);
            String magicTree = dataArray.toJSONString();
            dataArray.clear();

            // data body
            String itemsBody = buildItemsJSON(player.inventory.itemsBody, dataArray);

            // data bag
            String itemsBag = buildItemsJSON(player.inventory.itemsBag, dataArray);

            // data box
            String itemsBox = buildItemsJSON(player.inventory.itemsBox, dataArray);

            // data box crack ball
            String itemsBoxLuckyRound = buildItemsJSON(player.inventory.itemsBoxCrackBall, dataArray);

            // data bạn bè
            JSONArray dataFE = new JSONArray();
            for (Friend f : player.friends) {
                dataFE.add(f.id);
                dataFE.add(f.name);
                dataFE.add(f.head);
                dataFE.add(f.body);
                dataFE.add(f.leg);
                dataFE.add(f.bag);
                dataFE.add(f.power);
                dataArray.add(dataFE.toJSONString());
                dataFE.clear();
            }
            String friend = dataArray.toJSONString();
            dataArray.clear();

            // data kẻ thù
            for (Friend e : player.enemies) {
                dataFE.add(e.id);
                dataFE.add(e.name);
                dataFE.add(e.head);
                dataFE.add(e.body);
                dataFE.add(e.leg);
                dataFE.add(e.bag);
                dataFE.add(e.power);
                dataArray.add(dataFE.toJSONString());
                dataFE.clear();
            }
            String enemy = dataArray.toJSONString();
            dataArray.clear();

            // data nội tại
            dataArray.add(player.playerIntrinsic.intrinsic.id);
            dataArray.add(player.playerIntrinsic.intrinsic.param1);
            dataArray.add(player.playerIntrinsic.intrinsic.param2);
            dataArray.add(player.playerIntrinsic.countOpen);
            String intrinsic = dataArray.toJSONString();
            dataArray.clear();

            // data item time
            dataArray.add((player.itemTime.isUseBoHuyet
                    ? (ItemTime.TIME_ITEM - (System.currentTimeMillis() - player.itemTime.lastTimeBoHuyet))
                    : 0));
            dataArray.add((player.itemTime.isUseBoKhi
                    ? (ItemTime.TIME_ITEM - (System.currentTimeMillis() - player.itemTime.lastTimeBoKhi))
                    : 0));
            dataArray.add((player.itemTime.isUseGiapXen
                    ? (ItemTime.TIME_ITEM - (System.currentTimeMillis() - player.itemTime.lastTimeGiapXen))
                    : 0));
            dataArray.add((player.itemTime.isUseCuongNo
                    ? (ItemTime.TIME_ITEM - (System.currentTimeMillis() - player.itemTime.lastTimeCuongNo))
                    : 0));
            dataArray.add((player.itemTime.isUseAnDanh
                    ? (ItemTime.TIME_ITEM - (System.currentTimeMillis() - player.itemTime.lastTimeAnDanh))
                    : 0));
            dataArray.add((player.itemTime.isOpenPower
                    ? (ItemTime.TIME_OPEN_POWER - (System.currentTimeMillis() - player.itemTime.lastTimeOpenPower))
                    : 0));
            dataArray.add((player.itemTime.isUseMayDo
                    ? (ItemTime.TIME_MAY_DO - (System.currentTimeMillis() - player.itemTime.lastTimeUseMayDo))
                    : 0));
            dataArray.add((player.itemTime.isUseMayDo2
                    ? (ItemTime.TIME_MAY_DO - (System.currentTimeMillis() - player.itemTime.lastTimeUseMayDo2))
                    : 0));
            dataArray.add((player.itemTime.isEatMeal
                    ? (ItemTime.TIME_EAT_MEAL - (System.currentTimeMillis() - player.itemTime.lastTimeEatMeal))
                    : 0));
            dataArray.add(player.itemTime.iconMeal);
            dataArray.add((player.itemTime.isUseTDLT
                    ? ((player.itemTime.timeTDLT - (System.currentTimeMillis() - player.itemTime.lastTimeUseTDLT)) / 60
                            / 1000)
                    : 0));
            String itemTime = dataArray.toJSONString();
            dataArray.clear();

            // data nhiệm vụ
            dataArray.add(player.playerTask.taskMain.id);
            dataArray.add(player.playerTask.taskMain.index);
            dataArray.add(player.playerTask.taskMain.subTasks.get(player.playerTask.taskMain.index).count);
            String task = dataArray.toJSONString();
            dataArray.clear();

            // data nhiệm vụ hàng ngày
            dataArray.add(player.playerTask.sideTask.template != null ? player.playerTask.sideTask.template.id : -1);
            dataArray.add(player.playerTask.sideTask.receivedTime);
            dataArray.add(player.playerTask.sideTask.count);
            dataArray.add(player.playerTask.sideTask.maxCount);
            dataArray.add(player.playerTask.sideTask.leftTask);
            dataArray.add(player.playerTask.sideTask.level);
            String sideTask = dataArray.toJSONString();
            dataArray.clear();

            // data trứng bư
            if (player.mabuEgg != null) {
                dataArray.add(player.mabuEgg.lastTimeCreate);
                dataArray.add(player.mabuEgg.timeDone);
            }
            String mabuEgg = dataArray.toJSONString();
            dataArray.clear();

            // data trứng bill
            if (player.billEgg != null) {
                dataArray.add(player.billEgg.lastTimeCreate);
                dataArray.add(player.billEgg.timeDone);
            }
            String billEgg = dataArray.toJSONString();
            dataArray.clear();

            // data bùa
            dataArray.add(player.charms.tdTriTue);
            dataArray.add(player.charms.tdManhMe);
            dataArray.add(player.charms.tdDaTrau);
            dataArray.add(player.charms.tdOaiHung);
            dataArray.add(player.charms.tdBatTu);
            dataArray.add(player.charms.tdDeoDai);
            dataArray.add(player.charms.tdThuHut);
            dataArray.add(player.charms.tdDeTu);
            dataArray.add(player.charms.tdTriTue3);
            dataArray.add(player.charms.tdTriTue4);
            String charm = dataArray.toJSONString();
            dataArray.clear();

            // data skill
            JSONArray dataSkill = new JSONArray();
            for (Skill skill : player.playerSkill.skills) {
                dataSkill.add(skill.template.id);
                dataSkill.add(skill.point);
                dataSkill.add(skill.lastTimeUseThisSkill);
                dataSkill.add(skill.currLevel);
                dataArray.add(dataSkill.toJSONString());
                dataSkill.clear();
            }
            String skills = dataArray.toJSONString();
            dataArray.clear();

            // data skill shortcut
            player.playerSkill.skillShortCut[8] = -1;
            player.playerSkill.skillShortCut[9] = -1;
            for (int skillId : player.playerSkill.skillShortCut) {
                dataArray.add(skillId);
            }
            String skillShortcut = dataArray.toJSONString();
            dataArray.clear();

            // data pet
            String pet = buildPetData(player, dataArray);

            // data thưởng ngọc rồng đen
            for (int i = 0; i < player.rewardBlackBall.timeOutOfDateReward.length; i++) {
                JSONArray dataBlackBall = new JSONArray();
                dataBlackBall.add(player.rewardBlackBall.timeOutOfDateReward[i]);
                dataBlackBall.add(player.rewardBlackBall.lastTimeGetReward[i]);
                dataBlackBall.add(player.rewardBlackBall.quantilyBlackBall[i]);
                dataArray.add(dataBlackBall.toJSONString());
            }
            String dataBlackBall = dataArray.toJSONString();
            dataArray.clear();

            // data item time siêu cấp
            dataArray.add((player.itemTime.isUseBoHuyet2
                    ? (ItemTime.TIME_ITEM - (System.currentTimeMillis() - player.itemTime.lastTimeBoHuyet2))
                    : 0));
            dataArray.add((player.itemTime.isUseBoKhi2
                    ? (ItemTime.TIME_ITEM - (System.currentTimeMillis() - player.itemTime.lastTimeBoKhi2))
                    : 0));
            dataArray.add((player.itemTime.isUseGiapXen2
                    ? (ItemTime.TIME_ITEM - (System.currentTimeMillis() - player.itemTime.lastTimeGiapXen2))
                    : 0));
            dataArray.add((player.itemTime.isUseCuongNo2
                    ? (ItemTime.TIME_ITEM - (System.currentTimeMillis() - player.itemTime.lastTimeCuongNo2))
                    : 0));
            dataArray.add((player.itemTime.isUseAnDanh2
                    ? (ItemTime.TIME_ITEM - (System.currentTimeMillis() - player.itemTime.lastTimeAnDanh2))
                    : 0));
            String itemTimeSC = dataArray.toJSONString();
            dataArray.clear();

            // data achievement
            JSONObject achievementObject = new JSONObject();
            achievementObject.put("numPvpWin", player.achievement.numPvpWin);
            achievementObject.put("numSkillChuong", player.achievement.numSkillChuong);
            achievementObject.put("numFly", player.achievement.numFly);
            achievementObject.put("numKillMobFly", player.achievement.numKillMobFly);
            achievementObject.put("numKillNguoiRom", player.achievement.numKillNguoiRom);
            achievementObject.put("numHourOnline", player.achievement.numHourOnline);
            achievementObject.put("numGivePea", player.achievement.numGivePea);
            achievementObject.put("numSellItem", player.achievement.numSellItem);
            achievementObject.put("numPayMoney", player.achievement.numPayMoney);
            achievementObject.put("numKillSieuQuai", player.achievement.numKillSieuQuai);
            achievementObject.put("numHoiSinh", player.achievement.numHoiSinh);
            achievementObject.put("numSkillDacBiet", player.achievement.numSkillDacBiet);
            achievementObject.put("numPickGem", player.achievement.numPickGem);
            dataArray.addAll(player.achievement.listReceiveGem);
            achievementObject.put("listReceiveGem", dataArray);
            String infoAchieve = achievementObject.toJSONString();
            dataArray.clear();

            // Build SQL statement
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE player SET\n");
            sql.append("    info_achievement = '").append(escapeSQL(infoAchieve)).append("',\n");
            sql.append("    data_item_time_sieu_cap = '").append(escapeSQL(itemTimeSC)).append("',\n");
            sql.append("    head = ").append(player.head).append(",\n");
            sql.append("    have_tennis_space_ship = ").append(player.haveTennisSpaceShip ? 1 : 0).append(",\n");
            sql.append("    clan_id_sv").append(Manager.SERVER).append(" = ")
                    .append(player.clan != null ? player.clan.id : -1).append(",\n");
            sql.append("    data_inventory = '").append(escapeSQL(inventory)).append("',\n");
            sql.append("    data_location = '").append(escapeSQL(location)).append("',\n");
            sql.append("    data_point = '").append(escapeSQL(point)).append("',\n");
            sql.append("    data_magic_tree = '").append(escapeSQL(magicTree)).append("',\n");
            sql.append("    items_body = '").append(escapeSQL(itemsBody)).append("',\n");
            sql.append("    items_bag = '").append(escapeSQL(itemsBag)).append("',\n");
            sql.append("    items_box = '").append(escapeSQL(itemsBox)).append("',\n");
            sql.append("    items_box_lucky_round = '").append(escapeSQL(itemsBoxLuckyRound)).append("',\n");
            sql.append("    friends = '").append(escapeSQL(friend)).append("',\n");
            sql.append("    enemies = '").append(escapeSQL(enemy)).append("',\n");
            sql.append("    data_intrinsic = '").append(escapeSQL(intrinsic)).append("',\n");
            sql.append("    data_item_time = '").append(escapeSQL(itemTime)).append("',\n");
            sql.append("    data_task = '").append(escapeSQL(task)).append("',\n");
            sql.append("    data_mabu_egg = '").append(escapeSQL(mabuEgg)).append("',\n");
            sql.append("    pet = '").append(escapeSQL(pet)).append("',\n");
            sql.append("    data_black_ball = '").append(escapeSQL(dataBlackBall)).append("',\n");
            sql.append("    data_side_task = '").append(escapeSQL(sideTask)).append("',\n");
            sql.append("    data_charm = '").append(escapeSQL(charm)).append("',\n");
            sql.append("    skills = '").append(escapeSQL(skills)).append("',\n");
            sql.append("    skills_shortcut = '").append(escapeSQL(skillShortcut)).append("',\n");
            sql.append("    pointPvp = ").append(player.pointPvp).append(",\n");
            sql.append("    data_card = '").append(escapeSQL(JSONValue.toJSONString(player.Cards))).append("',\n");
            sql.append("    bill_data = '").append(escapeSQL(billEgg)).append("'\n");
            sql.append("WHERE id = ").append(player.id).append(";\n");

            return sql.toString();

        } catch (Exception e) {
            Logger.logException(SQLExportService.class, e, "Error generating player SQL");
            return "-- Error generating SQL: " + e.getMessage() + "\n";
        }
    }

    /**
     * Build JSON cho items list
     */
    private String buildItemsJSON(java.util.List<Item> items, JSONArray dataArray) {
        JSONArray dataItem = new JSONArray();
        for (Item item : items) {
            JSONArray opt = new JSONArray();
            if (item.isNotNullItem()) {
                dataItem.add(item.template.id);
                dataItem.add(item.quantity);
                JSONArray options = new JSONArray();
                for (Item.ItemOption io : item.itemOptions) {
                    opt.add(io.optionTemplate.id);
                    opt.add(io.param);
                    options.add(opt.toJSONString());
                    opt.clear();
                }
                dataItem.add(options.toJSONString());
            } else {
                dataItem.add(-1);
                dataItem.add(0);
                dataItem.add(opt.toJSONString());
            }
            dataItem.add(item.createTime);
            dataArray.add(dataItem.toJSONString());
            dataItem.clear();
        }
        String result = dataArray.toJSONString();
        dataArray.clear();
        return result;
    }

    /**
     * Build pet data JSON
     */
    private String buildPetData(Player player, JSONArray dataArray) {
        if (player.pet == null) {
            return dataArray.toJSONString();
        }

        try {
            JSONArray dataItem = new JSONArray();

            // pet info
            JSONArray petInfo = new JSONArray();
            petInfo.add(player.pet.typePet);
            petInfo.add(player.pet.gender);
            petInfo.add(player.pet.name);
            petInfo.add(player.fusion.typeFusion);
            int timeLeftFusion = (int) (Fusion.TIME_FUSION
                    - (System.currentTimeMillis() - player.fusion.lastTimeFusion));
            petInfo.add(timeLeftFusion < 0 ? 0 : timeLeftFusion);
            petInfo.add(player.pet.status);

            // pet point
            JSONArray petPoint = new JSONArray();
            petPoint.add(player.pet.nPoint.limitPower);
            petPoint.add(player.pet.nPoint.power);
            petPoint.add(player.pet.nPoint.tiemNang);
            petPoint.add(player.pet.nPoint.stamina);
            petPoint.add(player.pet.nPoint.maxStamina);
            petPoint.add(player.pet.nPoint.hpg);
            petPoint.add(player.pet.nPoint.mpg);
            petPoint.add(player.pet.nPoint.dameg);
            petPoint.add(player.pet.nPoint.defg);
            petPoint.add(player.pet.nPoint.critg);
            petPoint.add(player.pet.nPoint.hp);
            petPoint.add(player.pet.nPoint.mp);

            // pet body
            JSONArray petBody = new JSONArray();
            JSONArray options = new JSONArray();
            JSONArray opt = new JSONArray();
            for (Item item : player.pet.inventory.itemsBody) {
                if (item.isNotNullItem()) {
                    dataItem.add(item.template.id);
                    dataItem.add(item.quantity);
                    for (Item.ItemOption io : item.itemOptions) {
                        opt.add(io.optionTemplate.id);
                        opt.add(io.param);
                        options.add(opt.toJSONString());
                        opt.clear();
                    }
                    dataItem.add(options.toJSONString());
                } else {
                    dataItem.add(-1);
                    dataItem.add(0);
                    dataItem.add(options.toJSONString());
                }
                dataItem.add(item.createTime);
                petBody.add(dataItem.toJSONString());
                dataItem.clear();
                options.clear();
            }

            // pet skills
            JSONArray petSkills = new JSONArray();
            for (Skill s : player.pet.playerSkill.skills) {
                JSONArray pskill = new JSONArray();
                if (s.skillId != -1) {
                    pskill.add(s.template.id);
                    pskill.add(s.point);
                } else {
                    pskill.add(-1);
                    pskill.add(0);
                }
                petSkills.add(pskill.toJSONString());
            }

            dataArray.add(petInfo.toJSONString());
            dataArray.add(petPoint.toJSONString());
            dataArray.add(petBody.toJSONString());
            dataArray.add(petSkills.toJSONString());

            String result = dataArray.toJSONString();
            dataArray.clear();
            return result;

        } catch (Exception e) {
            dataArray.clear();
            return "[]";
        }
    }

    /**
     * Escape special characters cho SQL string
     */
    private String escapeSQL(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("'", "''").replace("\\", "\\\\");
    }

    /**
     * Ghi nội dung ra file
     */
    private void writeToFile(String folderPath, String fileName, String content) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(folderPath + fileName))) {
            writer.print(content);
        } catch (Exception e) {
            Logger.logException(SQLExportService.class, e, "Error writing file: " + folderPath + fileName);
        }
    }
}
