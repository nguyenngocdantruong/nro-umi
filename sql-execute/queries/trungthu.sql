USE umi;

-- Insert NPC
INSERT INTO npc_template (id, NAME, head, body, leg, avatar) VALUES (109, 'Thỏ đại ca', 403, 404, 405, 4118);

-- Shop
INSERT INTO shop (id, npc_id, tag_name, type_shop) VALUES (38, 109, 'THO_DAI_CA', 3);
INSERT INTO shop (id, npc_id, tag_name, type_shop) VALUES (40, 109, 'THO_DAI_CA_BANHTRUNGTHU', 3);

-- Tạo shop Cà rốt cho Thỏ đại ca
INSERT INTO tab_shop (id, shop_id, NAME) VALUES (70, 38, 'Shop<>Cà rốt');
INSERT INTO tab_shop (id, shop_id, NAME) VALUES (71, 38, 'Shop<>vv');

-- Tạo shop Bánh cho Thỏ đại ca
INSERT INTO tab_shop (id, shop_id, NAME) VALUES (73, 40, 'Shop<>Bánh');


-- Di chuyển lại thỏ hsd
UPDATE item_shop SET tab_id = 70, temp_id = 464, is_new = 1, is_sell = 1, type_sell = 2, cost = 35, icon_spec = 4083, create_time = '2025-06-25 05:12:33' WHERE id = 3472;
-- Di chuyển Thỏ vĩnh viễn
UPDATE umi.item_shop SET tab_id = 71, temp_id = 584, is_new = 1, is_sell = 1, type_sell = 2, cost = 250, icon_spec = 4083, create_time = '2025-06-25 05:12:33' WHERE id = 3471;
-- Di chuyển Thỏ đại ca vv
UPDATE umi.item_shop SET tab_id = 71, temp_id = 463, is_new = 1, is_sell = 1, type_sell = 2, cost = 250, icon_spec = 4083, create_time = '2025-06-25 04:55:30' WHERE id = 3470;


-- Thỏ đại ca HSD
INSERT INTO umi.item_shop (id, tab_id, temp_id, is_new, is_sell, type_sell, cost, icon_spec, create_time) VALUES (3482, 70, 463, 1, 1, 2, 50, 4083, '2025-12-05 17:18:03');
INSERT INTO umi.item_shop_option (item_shop_id, option_id, param) VALUES (3482, 115, 0);
INSERT INTO umi.item_shop_option (item_shop_id, option_id, param) VALUES (3482, 116, 0);
INSERT INTO umi.item_shop_option (item_shop_id, option_id, param) VALUES (3482, 16, 15);
INSERT INTO umi.item_shop_option (item_shop_id, option_id, param) VALUES (3482, 93, 15);

-- =====================================================
-- SQL SCRIPT: Event Trung Thu - Shop Configuration
-- Run this in your database after backing up
-- =====================================================
-- LƯU Ý: icon_spec sử dụng ICON_ID của item, không phải template_id!
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

-- 5 Bánh Gà quay (template=890, icon_id=8132) = Lồng đèn con gà (802)
INSERT INTO `item_shop` (`id`, `tab_id`, `temp_id`, `is_new`, `is_sell`, `type_sell`, `cost`, `icon_spec`) VALUES
(3487, 101, 802, 1, 0, 2, 5, 8132);

-- 3 Bánh 2 trứng (template=466, icon_id=4043) = Lồng đèn con bướm (803)
INSERT INTO `item_shop` (`id`, `tab_id`, `temp_id`, `is_new`, `is_sell`, `type_sell`, `cost`, `icon_spec`) VALUES
(3488, 101, 803, 1, 0, 2, 3, 4043);

-- 3 Bánh Thập cẩm (template=891, icon_id=8131) = Lồng đèn bóng gà (1337)
INSERT INTO `item_shop` (`id`, `tab_id`, `temp_id`, `is_new`, `is_sell`, `type_sell`, `cost`, `icon_spec`) VALUES
(3489, 101, 1337, 1, 0, 2, 3, 8131);

-- 2 Bánh Đặc biệt (template=472, icon_id=4125) = Lồng đèn Doraemon (804)
INSERT INTO `item_shop` (`id`, `tab_id`, `temp_id`, `is_new`, `is_sell`, `type_sell`, `cost`, `icon_spec`) VALUES
(3490, 101, 804, 1, 0, 2, 2, 4125);

-- Item options cho các lồng đèn
-- Lồng đèn con gà (item_shop_id = 500): (50,10), (30,0)
INSERT INTO `item_shop_option` (`item_shop_id`, `option_id`, `param`) VALUES
(3487, 50, 10),
(3487, 30, 0);

-- Lồng đèn con bướm (item_shop_id = 501): (50,10), (30,0)
INSERT INTO `item_shop_option` (`item_shop_id`, `option_id`, `param`) VALUES
(3488, 50, 10),
(3488, 30, 0);

-- Lồng đèn bóng gà (item_shop_id = 502): (50,15), (30,0)
INSERT INTO `item_shop_option` (`item_shop_id`, `option_id`, `param`) VALUES
(3489, 50, 15),
(3489, 30, 0);

-- Lồng đèn Doraemon (item_shop_id = 503): (50,12), (30,0)
INSERT INTO `item_shop_option` (`item_shop_id`, `option_id`, `param`) VALUES
(3490, 50, 12),
(3490, 30, 0);


-- =====================================================
-- 4. ITEM SHOP CHO TRUNGTHU_SHOP (tab_id = 100)
-- Đổi Hộp bánh Trung thu (template=473, icon_id=4126) lấy thú cưỡi
-- =====================================================

-- 2 Hộp bánh (template=473, icon_id=4126) = Cân đẩu vân ngũ sắc (733)
INSERT INTO `item_shop` (`id`, `tab_id`, `temp_id`, `is_new`, `is_sell`, `type_sell`, `cost`, `icon_spec`) VALUES
(3491, 100, 733, 1, 0, 2, 2, 4126);

-- 5 Hộp bánh (template=473, icon_id=4126) = Ngọc Thỏ (734)
INSERT INTO `item_shop` (`id`, `tab_id`, `temp_id`, `is_new`, `is_sell`, `type_sell`, `cost`, `icon_spec`) VALUES
(3492, 100, 734, 1, 0, 2, 5, 4126);

-- Item options cho thú cưỡi
-- Cân đẩu vân ngũ sắc (item_shop_id = 504): (89,0), (76,0), (30,0)
INSERT INTO `item_shop_option` (`item_shop_id`, `option_id`, `param`) VALUES
(3491, 89, 0),
(3491, 76, 0),
(3491, 30, 0);

-- Ngọc Thỏ (item_shop_id = 505): (89,0), (76,0), (30,0)
INSERT INTO `item_shop_option` (`item_shop_id`, `option_id`, `param`) VALUES
(3492, 89, 0),
(3492, 76, 0),
(3492, 30, 0);

-- Update lai UI npc Trung Thu
UPDATE umi.npc_template SET NAME = 'Trung thu', head = 120, body = 121, leg = 122, avatar = 1415 WHERE id = 41;


-- =====================================================
-- DONE! Restart server to apply changes
-- =====================================================


