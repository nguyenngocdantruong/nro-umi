/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.girlkun.models.item;

import com.girlkun.models.map.ItemMap;
import com.girlkun.models.map.Zone;
import com.girlkun.services.ItemService;
import com.girlkun.utils.Util;

/**
 *
 * @author nguyenngocdantruong
 */
public class ConstItem {
    // ==================================================
    // Id mấy item trong code
    public static final int QUA_TRUNG_UUB = 568;
    public static final int BAN_DO_KHI_GAS = 1375;
    public static final int NHAN_THOI_KHONG_SAI_LECH = 992;
    public static final int DA_BAO_VE = 987;

    // Item Event Halloween - Drop
    public static final int HALLOWEEN_BI_NGO_DROP_TU_QUAI = 585;
    // Item Event Halloween - Craft
    public static final int HALLOWEEN_KEO1MAT = 899;
    public static final int HALLOWEEN_SUPBIHACAM = 900;
    public static final int HALLOWEEN_KEOBANTAY = 901;
    public static final int HALLOWEEN_BANHGATONHEN = 902;
    public static final int HALLOWEEN_HAMBUGERSAU = 903;
    // Item Event Halloween - Phần thưởng
    public static final int HALLOWEEN_BAG_MATROI = 814;
    public static final int HALLOWEEN_BAG_HONMAGOKU = 815;
    public static final int HALLOWEEN_BAG_HONMACADIC = 816;
    public static final int HALLOWEEN_BAG_HONMAPOCOLO = 817;
    public static final int HALLOWEEN_BAG_MEOMUN = 999;
    public static final int HALLOWEEN_THUCUOI_CHOIBAYPHUTHUY = 743;
    // tmp shop
    public static final int HALLOWEEN_BAG_LUOIHAITHANCHET = 740;
    public static final int HALLOWEEN_CAITRANG_CAULIFIA_DOI = 742;

    // Item Event Trung thu - Drop
    public static final int TRUNGTHU_CAROT = 462; // Cà rốt drop từ boss Thỏ Đại Ca
    public static final int TRUNGTHU_BOT_MI = 888;
    public static final int TRUNGTHU_DAU_XANH = 889;
    public static final int TRUNGTHU_TRUNGVIT = 886;
    public static final int TRUNGTHU_GAQUAYNGUYENCON = 887;

    // Item Event Trung thu - Craft
    public static final int TRUNGTHU_BANHTRUNGTHU_GAQUAY = 890;
    public static final int TRUNGTHU_BANHTRUNGTHU_THAPCAM = 891;
    public static final int TRUNGTHU_BANHTRUNGTHU_1TRUNG = 465;
    public static final int TRUNGTHU_BANHTRUNGTHU_2TRUNG = 466;
    public static final int TRUNGTHU_BANHTRUNGTHU_DACBIET = 472;
    public static final int TRUNGTHU_BANHTRUNGTHU_HOPBANHTRUNGTHU = 473;
    public static final int TRUNGTHU_CAPSULETRUNGTHU = 737;

    // Item Event trung thu - Phần thưởng
    public static final int TRUNGTHU_LONGDEN_CONGA = 802;
    public static final int TRUNGTHU_LONGDEN_CONBUOM = 803;
    public static final int TRUNGTHU_LONGDENBONGGA = 1337; // Lồng đèn bóng gà - đẹp phết
    public static final int TRUNGTHU_LONGDEN_DORAEMON = 804;
    public static final int TRUNGTHU_THUCUOI_CANDAUVANNGUSAC = 733;
    public static final int TRUNGTHU_THUCUOI_NGOCTHO = 734; // Thú cưỡi thỏ đẹp phết
    public static final int TRUNGTHU_PET_KHIBONGBONG = 1046;

    // ==================================================
    public enum LoaiDoRoiTuBoss {
        DoTamTrung(0),
        DoVip(1),
        DoThanLinh(2),;

        public int value;

        private LoaiDoRoiTuBoss(int value) {
            this.value = value;
        }
    }

    public enum CoSaoPhaLe {
        Co,
        Khong
    }

    public static final short[] listSPL = new short[] { 441, 442, 443, 444, 445, 446, 447, 964, 965 };
    public static final short[][] doRoiTuBoss = new short[][] {
            { 262, 258, 254, 251, 247, 243, 239, 235, 231, 278, 275, 271, 26 }, // Đồ tầm trung
            { 281, 265, 261, 257, 280, 264, 260, 256, 253, 249, 245, 241, 23 }, // Đồ vip
            { 567, 566, 565, 564, 563, 562, 561, 560, 559, 558, 557, 556, 555 } // Đồ thần linh
    };

}
