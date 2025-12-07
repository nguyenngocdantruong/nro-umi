USE umi;

-- Sửa lại item db
UPDATE umi.item_template SET TYPE = 27, gender = 3, NAME = 'Bí ngô', description = 'Vật phẩm sự kiện Halloween', icon_id = 5138, part = -1, is_up_to_up = 1, power_require = 0, gold = 0, gem = 0, head = -1, body = -1, leg = -1 WHERE id = 585;
INSERT INTO umi.item_template (id, TYPE, gender, NAME, description, icon_id, part, is_up_to_up, power_require, gold, gem, head, body, leg) VALUES (2158, 29, 3, 'Đuôi khỉ Trung thu', 'Vật phẩm sự kiện. Cho x2 TNSM trong 30 phút.', 5072, -1, 1, 15000, 0, 0, -1, -1, -1);
UPDATE umi.item_template SET TYPE = 27, gender = 3, NAME = 'Capsule Halloween', description = 'Vật phẩm sự kiện, bên trong ấn chứa nhiều đồ thú vị', icon_id = 7356, part = -1, is_up_to_up = 1, power_require = 0, gold = 0, gem = 0, head = -1, body = -1, leg = -1 WHERE id = 818;

-- Insert NPC
INSERT INTO npc_template (id, NAME, head, body, leg, avatar) VALUES (110, 'Bill Bí Ngô', 754, 755, 756, 7016);

-- Insert Shop
INSERT INTO shop (id, npc_id, tag_name, type_shop) VALUES (37, 110, 'BILL_HALLOWEEN', 3);
INSERT INTO shop (id, npc_id, tag_name, type_shop) VALUES (39, 110, 'BILL_HALLOWEEN_BINGO', 3);


-- Insert Tab Shop
INSERT INTO tab_shop (id, shop_id, NAME) VALUES (69, 37, 'Shop<>Halloween');
INSERT INTO tab_shop (id, shop_id, NAME) VALUES (72, 39, 'Shop<>Bí ngô');

-- Insert item vào tab Shop bí ngô
INSERT INTO item_shop (id, tab_id, temp_id, is_new, is_sell, type_sell, cost, icon_spec, create_time)
VALUES (3483, 72, 899, 1, 1, 2, 10, 5138, '2025-12-05 13:54:44');
INSERT INTO item_shop (id, tab_id, temp_id, is_new, is_sell, type_sell, cost, icon_spec, create_time)
VALUES (3484, 72, 900, 1, 1, 2, 10, 5138, '2025-12-05 13:54:44');
INSERT INTO item_shop (id, tab_id, temp_id, is_new, is_sell, type_sell, cost, icon_spec, create_time)
VALUES (3485, 72, 902, 1, 1, 2, 10, 5138, '2025-12-05 13:54:44');
INSERT INTO item_shop (id, tab_id, temp_id, is_new, is_sell, type_sell, cost, icon_spec, create_time)
VALUES (3486, 72, 903, 1, 1, 2, 10, 5138, '2025-12-05 13:54:44');
INSERT INTO item_shop_option (item_shop_id, option_id, param) VALUES (3483, 30, 0); -- Không thể giao dịch
INSERT INTO item_shop_option (item_shop_id, option_id, param) VALUES (3484, 30, 0); -- Không thể giao dịch
INSERT INTO item_shop_option (item_shop_id, option_id, param) VALUES (3485, 30, 0); -- Không thể giao dịch
INSERT INTO item_shop_option (item_shop_id, option_id, param) VALUES (3486, 30, 0); -- Không thể giao dịch

-- Bổ sung item option template
INSERT INTO item_option_template (id, NAME) VALUES (213, 'Không bị biến hình trong dịp Halloween');

-- Cải trang Ma trơi
INSERT INTO item_shop (id, tab_id, temp_id, is_new, is_sell, type_sell, cost, icon_spec, create_time) VALUES (3480, 69, 642, 1, 1, 2, 10, 8192, '2025-12-05 13:54:44');
INSERT INTO item_shop_option (item_shop_id, option_id, param) VALUES (3480, 8, 4); -- Hút 4% HP KI xung quanh
INSERT INTO item_shop_option (item_shop_id, option_id, param) VALUES (3480, 50, 10); -- 10% sức đánh
INSERT INTO item_shop_option (item_shop_id, option_id, param) VALUES (3480, 213, 0); -- Không thể bị biến hình Halloween
INSERT INTO item_shop_option (item_shop_id, option_id, param) VALUES (3480, 93, 15); -- HSD 15 ngày

-- Cải trang Dơi nhí
INSERT INTO item_shop (id, tab_id, temp_id, is_new, is_sell, type_sell, cost, icon_spec, create_time) VALUES (3479, 69, 643, 1, 1, 2, 10, 8192, '2025-12-05 13:54:44');
INSERT INTO item_shop_option (item_shop_id, option_id, param) VALUES (3479, 8, 4); -- Hút 4% HP KI xung quanh
INSERT INTO item_shop_option (item_shop_id, option_id, param) VALUES (3479, 50, 10); -- 10% sức đánh
INSERT INTO item_shop_option (item_shop_id, option_id, param) VALUES (3479, 213, 0); -- Không thể bị biến hình Halloween
INSERT INTO item_shop_option (item_shop_id, option_id, param) VALUES (3479, 93, 15); -- HSD 15 ngày

-- Cải trang Dracula Halloween
INSERT INTO item_shop (id, tab_id, temp_id, is_new, is_sell, type_sell, cost, icon_spec, create_time) VALUES (3476, 69, 448, 1, 1, 2, 50, 8192, '2025-12-05 13:54:44');
INSERT INTO item_shop_option (item_shop_id, option_id, param) VALUES (3476, 104, 50); -- Biến 50% quái thành HP
INSERT INTO item_shop_option (item_shop_id, option_id, param) VALUES (3476, 154, 0); -- Không thể bán lại

-- Cánh dơi Dracula
INSERT INTO item_shop (id, tab_id, temp_id, is_new, is_sell, type_sell, cost, icon_spec, create_time) VALUES (3477, 69, 741, 1, 1, 2, 80, 8192, '2025-12-05 13:54:44');
INSERT INTO item_shop_option (item_shop_id, option_id, param) VALUES (3477, 50, 20); -- 20% sức đánh
INSERT INTO item_shop_option (item_shop_id, option_id, param) VALUES (3477, 30, 0); -- Không thể giao dịch

-- Cánh ác quỷ
INSERT INTO item_shop (id, tab_id, temp_id, is_new, is_sell, type_sell, cost, icon_spec, create_time) VALUES (3478, 69, 995, 1, 1, 2, 99, 8192, '2025-12-05 12:50:24');
INSERT INTO item_shop_option (item_shop_id, option_id, param) VALUES (3478, 50, 15); -- 15% sức đánh
INSERT INTO item_shop_option (item_shop_id, option_id, param) VALUES (3478, 103, 15); -- 15% KI
INSERT INTO item_shop_option (item_shop_id, option_id, param) VALUES (3478, 30, 0); -- Không thể giao dịch

-- Cải trang Bill bí ngô
INSERT INTO item_shop (id, tab_id, temp_id, is_new, is_sell, type_sell, cost, icon_spec, create_time) VALUES (3481, 69, 739, 1, 1, 2, 99, 8192, '2025-12-05 13:54:44');
INSERT INTO item_shop_option (item_shop_id, option_id, param) VALUES (3481, 50, 20); -- 20% sức đánh
INSERT INTO item_shop_option (item_shop_id, option_id, param) VALUES (3481, 77, 20); -- 20% HP
INSERT INTO item_shop_option (item_shop_id, option_id, param) VALUES (3481, 103, 20); -- 20% KI
INSERT INTO item_shop_option (item_shop_id, option_id, param) VALUES (3481, 213, 0); -- Không thể bị biến hình Halloween
INSERT INTO item_shop_option (item_shop_id, option_id, param) VALUES (3481, 154, 0); -- Không thể bán lại
