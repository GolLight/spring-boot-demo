-- 应用启动时会加载这个sql文件
delete from Ingredient_Ref;
delete from Taco;
delete from Taco_Order;

delete from Ingredient;

-- 预加载配料信息
insert into Ingredient (id, name, type) 
                values ('FLTO', 'Flour Tortilla 面粉玉米饼', 'WRAP');
insert into Ingredient (id, name, type) values ('COTO', 'Corn Tortilla 麦粉玉米饼', 'WRAP');
insert into Ingredient (id, name, type) values ('GRBF', 'Ground Beef 牛肉', 'PROTEIN');
insert into Ingredient (id, name, type) values ('CARN', 'Carnitas 猪肉丝', 'PROTEIN');
insert into Ingredient (id, name, type) values ('TMTO', 'Diced Tomatoes 小块番茄', 'VEGGIES');
insert into Ingredient (id, name, type) values ('LETC', 'Lettuce 生菜', 'VEGGIES');
insert into Ingredient (id, name, type) values ('CHED', 'Cheddar 黄色奶酪', 'CHEESE');
insert into Ingredient (id, name, type) values ('JACK', 'Monterrey Jack', 'CHEESE');
insert into Ingredient (id, name, type) values ('SLSA', 'Salsa 辣调味汁', 'SAUCE');
insert into Ingredient (id, name, type) values ('SRCR', 'Sour Cream 醋', 'SAUCE');