package com.girlkun.models.boss.list_boss.Doraemon;

import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossStatus;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.item.ConstItem;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.services.ItemService;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;
import java.util.ArrayList;
import java.util.List;

public class Doraemon extends Boss {

    public Doraemon() throws Exception {
        super(BossID.DORAEMON,BossesData.DORAEMON,BossesData.NOBITA,  BossesData.XUKA,  BossesData.CHAIEN, BossesData.XEKO);
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
        // 80% rơi cải trang
        if(Util.isTrue(80, 100)){
            ItemMap caitrang = new ItemMap(this.zone, 806, 1, plKill.location.x, plKill.location.y, plKill.id);
            List<Item.ItemOption> list_io = new ArrayList<>();
            short tempId;
            list_io.add(new Item.ItemOption(77, Util.nextInt(10, 30))); // HP +%
            list_io.add(new Item.ItemOption(103, Util.nextInt(10, 30))); // KI +%
            switch(this.currentLevel){
                case 0:{ // Doraemon
                    tempId = 806;
                    list_io.add(new Item.ItemOption(94, Util.nextInt(10, 20))); // Giáp +#%
                    list_io.add(new Item.ItemOption(108, Util.nextInt(10, 25))); // Giáp +#%
                    break;
                }
                case 1:{ // Nobita
                    tempId = 862;
                    list_io.add(new Item.ItemOption(14, Util.nextInt(5, 10))); // Chí mạng +#%
                    list_io.add(new Item.ItemOption(98, Util.nextInt(10, 25))); // Xuyên giáp #% chưởng
                    break;
                }
                case 2:{ // Xuka
                    tempId = 819;
                    list_io.add(new Item.ItemOption(117, Util.nextInt(10, 15))); // Đẹp +#% sức đánh cho mọi người xung quanh
                    break;
                }
                case 3:{ // Chaien
                    tempId = 864;
                    list_io.add(new Item.ItemOption(50, Util.nextInt(10, 30))); // Sức đánh +#%
                    break;
                }
                case 4:{ // Xeko
                    tempId = 863;
                    list_io.add(new Item.ItemOption(19, Util.nextInt(5, 17))); // Tấn công +#% khi đánh quái
                    break;
                }
                default:{
                    tempId = -1;
                }
            }
            caitrang.itemTemplate = ItemService.gI().getTemplate((short) tempId);
            list_io.add(new Item.ItemOption(176, 0)); // Ở gần đủ 5 loại +20% sđ, 50% tđ chạy
            // 95 % cải trang là HSD
            if(Util.isTrue(95, 100)){
                list_io.add(new Item.ItemOption(93, Util.nextInt(2, 7))); // HSD # ngày
            }
            caitrang.options.addAll(list_io);
            Service.gI().dropItemMap(this.zone, caitrang);
        }
        
        // Rơi đồ thêm
        ItemMap doRoiTuBoss = Util.GetRandomItemRoiTuBoss(15, ConstItem.LoaiDoRoiTuBoss.DoVip,
                ConstItem.CoSaoPhaLe.Co, this.zone, this.location.x + Util.nextInt(-15, 15), this.location.y, plKill.id);
        if(doRoiTuBoss != null){
            Service.gI().dropItemMap(this.zone, doRoiTuBoss);
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
    