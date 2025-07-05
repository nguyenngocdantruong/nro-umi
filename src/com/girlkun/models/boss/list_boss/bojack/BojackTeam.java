package com.girlkun.models.boss.list_boss.bojack;

import com.girlkun.models.item.Item;
import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossStatus;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.models.skill.Skill;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.ItemService;
import com.girlkun.services.Service;
import com.girlkun.services.TaskService;
import com.girlkun.utils.Util;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BojackTeam extends Boss {

    public BojackTeam() throws Exception {
        // HHAINHI = Bido
        // STANG = Zangya
        // TBACGIOI = Kogu
        // NKHONG = Bojack
        // D_TANG = Super Bojack
        super(BossID.HHAINHI, BossesData.HHAINHI, BossesData.STANG, BossesData.TBACGIOI, BossesData.NKHONG, BossesData.D_TANG);
    }

    @Override
    public void moveTo(int x, int y) {
        if (this.currentLevel == 4) {
            return;
        }
        super.moveTo(x, y);
    }

    @Override
    public void reward(Player plKill) {
        // 60% roi cai trang
        if(Util.isTrue(60, 100)){
            ItemMap caitrang = new ItemMap(this.zone, 806, 1, plKill.location.x, plKill.location.y, plKill.id);
            List<Item.ItemOption> list_io = new ArrayList<>();
            short tempId;
            list_io.add(new Item.ItemOption(50, Util.nextInt(11, 20))); // Sức đánh +#%
            switch (this.currentLevel) {
                case 0: { // Bido
                    tempId = 426;
                    break;
                }
                case 1: { // Zangya
                    tempId = 425;
                    break;
                }
                case 2: { // Kogu
                    tempId = 424;
                    break;
                }
                case 3: { // Bojack
                    tempId = 427;
                    break;
                }
                case 4: { // Super Bojack
                    tempId = 428;
                    list_io.add(new Item.ItemOption(19, Util.nextInt(5, 17))); // Tấn công +#% khi đánh quái
                    break;
                }
                default: {
                    tempId = -1;
                }
            }
            caitrang.itemTemplate = ItemService.gI().getTemplate((short) tempId);
            // 95 % cải trang là HSD
            if (Util.isTrue(95, 100)) {
                list_io.add(new Item.ItemOption(93, Util.nextInt(2, 7))); // HSD # ngày
            }
            caitrang.options.addAll(list_io);
            Service.gI().dropItemMap(this.zone, caitrang);
        }
        super.reward(plKill);
    }

    @Override
    protected void notifyJoinMap() {
        super.notifyJoinMap();
    }

    @Override
    public void active() {
        super.active();
        if (Util.canDoWithTime(st, 300000)) {
            this.changeStatus(BossStatus.LEAVE_MAP);
        }
    }

    @Override
    public void joinMap() {
        super.joinMap();
        st = System.currentTimeMillis();
    }

    private long st;

}
/**
 * Vui lòng không sao chép mã nguồn này dưới mọi hình thức. Hãy tôn trọng tác
 * giả của mã nguồn này. Xin cảm ơn! - GirlBeo
 */
