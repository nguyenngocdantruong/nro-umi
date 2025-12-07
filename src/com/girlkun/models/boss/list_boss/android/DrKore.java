package com.girlkun.models.boss.list_boss.android;

import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.item.ConstItem;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.models.skill.Skill;
import com.girlkun.services.PlayerService;
import com.girlkun.services.Service;
import com.girlkun.services.TaskService;
import com.girlkun.utils.Util;


public class DrKore extends Boss {

    public DrKore() throws Exception {
        super(BossID.DR_KORE, BossesData.DR_KORE);
    }

    @Override
    public void reward(Player plKill) {
        if(Util.isTrue(15, 100)){
            // Rơi cải trang
            int caiTrangId = 525;
            int quantity = 1;
            ItemMap item = new ItemMap(this.zone, caiTrangId, 
                    quantity, 
                    this.location.x + Util.nextInt(-50, 50),
                    this.location.y
                    , plKill.id);

            item.options.add(new Item.ItemOption(3, 100)); // Vô hiệu và biến 100% thành KI
            item.options.add(new Item.ItemOption(4, 2)); // Hồi phục 2% KI khi bị đánh
            item.options.add(new Item.ItemOption(5, 15)); // +15% sức đánh chí mạng
            Service.gI().dropItemMap(this.zone, item);
        }
        // Rơi đồ thêm
        ItemMap doRoiTuBoss = Util.GetRandomItemRoiTuBoss(30, ConstItem.LoaiDoRoiTuBoss.DoTamTrung,
                ConstItem.CoSaoPhaLe.Co, this.zone, this.location.x + Util.nextInt(-15, 15), this.location.y, plKill.id);
        if(doRoiTuBoss != null){
            Service.gI().dropItemMap(this.zone, doRoiTuBoss);
        }
        super.reward(plKill);
    }

    @Override
    public void chatM() {
        if (Util.isTrue(60, 61)) {
            super.chatM();
            return;
        }
        if (this.getBossAppearTogether() == null || this.getBossAppearTogether()[this.getCurrentLevel()] == null) {
            return;
        }
        for (Boss boss : this.getBossAppearTogether()[this.getCurrentLevel()]) {
            if (boss.id == BossID.ANDROID_19 && !boss.isDie()) {
                this.chat("Hút năng lượng của nó, mau lên");
                boss.chat("Tuân lệnh đại ca, hê hê hê");
                break;
            }
        }
    }

     @Override
    public void active() {
        super.active(); //To change body of generated methods, choose Tools | Templates.
     //   if(Util.canDoWithTime(st,900000)){
    //        this.changeStatus(BossStatus.LEAVE_MAP);
    //    }
    }

    @Override
    public void joinMap() {
        super.joinMap(); //To change body of generated methods, choose Tools | Templates.
        st= System.currentTimeMillis();
    }
    private long st;

    @Override
    public int injured(Player plAtt, int damage, boolean piercing, boolean isMobAttack) {
        if (plAtt != null) {
            switch (plAtt.playerSkill.skillSelect.template.id) {
                case Skill.KAMEJOKO:
                case Skill.MASENKO:
                case Skill.ANTOMIC:
                    PlayerService.gI().hoiPhuc(this, damage, 0);
                    if (Util.isTrue(1, 5)) {
                        this.chat("Hấp thụ.. các ngươi nghĩ sao vậy?");
                    }
                    return 0;
            }
        }
        return super.injured(plAtt, damage, piercing, isMobAttack);
    }

    @Override
    public void doneChatS() {
        for (Boss boss : this.getBossAppearTogether()[this.getCurrentLevel()]) {
            if (boss.id == BossID.ANDROID_19) {
                boss.changeToTypePK();
                break;
            }
        }
    }

    @Override
    public void changeToTypePK() {
        super.changeToTypePK();
        this.chat("Mau đền mạng cho thằng em trai ta");
    }
}

/**
 * Vui lòng không sao chép mã nguồn này dưới mọi hình thức. Hãy tôn trọng tác
 * giả của mã nguồn này. Xin cảm ơn! - GirlBeo
 */
