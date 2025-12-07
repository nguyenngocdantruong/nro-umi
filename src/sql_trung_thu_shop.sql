-- =====================================================
-- SQL SCRIPT: Event Trung Thu - Shop Configuration
-- Run this in your database after backing up
-- =====================================================
-- LƯU Ý: icon_spec sử dụng ICON_ID của item, không phải template_id!
-- LƯU Ý: is_sell = 1 để hiển thị trong shop!
-- =====================================================

-- =====================================================
-- 1. TẠO SHOP MỚI: TRUNGTHU_SHOP (NPC Trung Thu ở Đảo Kame)
-- =====================================================
INSERT INTO `shop` (`id`, `npc_id`, `tag_name`, `type_shop`) VALUES 
(41, 41, 'TRUNGTHU_SHOP', 3);

-- Tab cho shop TRUNGTHU_SHOP (shop_id = 41)
INSERT INTO `tab_shop` (`id`, `shop_id`, `NAME`) VALUES 
(100, 41, 'Đổi<>quà');

-- =====================================================
-- 2. TAB CHO SHOP THO_DAI_CA_BANHTRUNGTHU (shop_id = 40)
-- =====================================================
INSERT INTO `tab_shop` (`id`, `shop_id`, `NAME`) VALUES 
(101, 40, 'Đổi<>bánh');

-- =====================================================
-- 3. ITEM SHOP CHO THO_DAI_CA_BANHTRUNGTHU (tab_id = 101)
-- Đổi bánh thừa lấy lồng đèn
-- =====================================================
-- type_sell = 2 = đổi bằng item khác
-- cost = số lượng item cần đổi
-- icon_spec = ICON_ID của item cần để đổi (KHÔNG PHẢI template_id!)
-- is_sell = 1 (BẮT BUỘC để hiển thị trong shop)

-- 5 Bánh Gà quay (template=890, icon_id=8132) = Lồng đèn con gà (802)
INSERT INTO `item_shop` (`id`, `tab_id`, `temp_id`, `is_new`, `is_sell`, `type_sell`, `cost`, `icon_spec`) VALUES 
(500, 101, 802, 1, 1, 2, 5, 8132);

-- 3 Bánh 2 trứng (template=466, icon_id=4043) = Lồng đèn con bướm (803)
INSERT INTO `item_shop` (`id`, `tab_id`, `temp_id`, `is_new`, `is_sell`, `type_sell`, `cost`, `icon_spec`) VALUES 
(501, 101, 803, 1, 1, 2, 3, 4043);

-- 3 Bánh Thập cẩm (template=891, icon_id=8131) = Lồng đèn bóng gà (1337)
INSERT INTO `item_shop` (`id`, `tab_id`, `temp_id`, `is_new`, `is_sell`, `type_sell`, `cost`, `icon_spec`) VALUES 
(502, 101, 1337, 1, 1, 2, 3, 8131);

-- 2 Bánh Đặc biệt (template=472, icon_id=4125) = Lồng đèn Doraemon (804)
INSERT INTO `item_shop` (`id`, `tab_id`, `temp_id`, `is_new`, `is_sell`, `type_sell`, `cost`, `icon_spec`) VALUES 
(503, 101, 804, 1, 1, 2, 2, 4125);

-- Item options cho các lồng đèn
-- Lồng đèn con gà (item_shop_id = 500): (50,10), (30,0)
INSERT INTO `item_shop_option` (`item_shop_id`, `option_id`, `param`) VALUES 
(500, 50, 10),
(500, 30, 0);

-- Lồng đèn con bướm (item_shop_id = 501): (50,10), (30,0)
INSERT INTO `item_shop_option` (`item_shop_id`, `option_id`, `param`) VALUES 
(501, 50, 10),
(501, 30, 0);

-- Lồng đèn bóng gà (item_shop_id = 502): (50,15), (30,0)
INSERT INTO `item_shop_option` (`item_shop_id`, `option_id`, `param`) VALUES 
(502, 50, 15),
(502, 30, 0);

-- Lồng đèn Doraemon (item_shop_id = 503): (50,12), (30,0)
INSERT INTO `item_shop_option` (`item_shop_id`, `option_id`, `param`) VALUES 
(503, 50, 12),
(503, 30, 0);


-- =====================================================
-- 4. ITEM SHOP CHO TRUNGTHU_SHOP (tab_id = 100)
-- Đổi Hộp bánh Trung thu (template=473, icon_id=4126) lấy thú cưỡi
-- =====================================================

-- 2 Hộp bánh (template=473, icon_id=4126) = Cân đẩu vân ngũ sắc (733)
INSERT INTO `item_shop` (`id`, `tab_id`, `temp_id`, `is_new`, `is_sell`, `type_sell`, `cost`, `icon_spec`) VALUES 
(504, 100, 733, 1, 1, 2, 2, 4126);

-- 5 Hộp bánh (template=473, icon_id=4126) = Ngọc Thỏ (734)
INSERT INTO `item_shop` (`id`, `tab_id`, `temp_id`, `is_new`, `is_sell`, `type_sell`, `cost`, `icon_spec`) VALUES 
(505, 100, 734, 1, 1, 2, 5, 4126);

-- Item options cho thú cưỡi
-- Cân đẩu vân ngũ sắc (item_shop_id = 504): (89,0), (76,0), (30,0)
INSERT INTO `item_shop_option` (`item_shop_id`, `option_id`, `param`) VALUES 
(504, 89, 0),
(504, 76, 0),
(504, 30, 0);

-- Ngọc Thỏ (item_shop_id = 505): (89,0), (76,0), (30,0)
INSERT INTO `item_shop_option` (`item_shop_id`, `option_id`, `param`) VALUES 
(505, 89, 0),
(505, 76, 0),
(505, 30, 0);


-- =====================================================
-- DONE! Restart server to apply changes
-- =====================================================


