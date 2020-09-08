insert into category_entity
values (1, 'image src', 'category 1'),
(2, 'image src', 'category 2'),
(3, 'image src', 'category 3');


insert into product_entity (id, description, image_src, name, price, number_of_purchases)
values (1,
        'Lorem desc1 ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
        'https://worldstrides.com/wp-content/uploads/2015/07/iStock_000040849990_Large.jpg', 'name', 1,
        12),
       (2,
        'Lorem desc1 ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
        'https://worldstrides.com/wp-content/uploads/2015/07/iStock_000040849990_Large.jpg', 'name', 1,
        10),
       (3,
        'Lorem descdesc1 ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
        'https://worldstrides.com/wp-content/uploads/2015/07/iStock_000040849990_Large.jpg', 'name', 1,
        20),
       (4,
        'Lorem descdesc2 ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
        'https://worldstrides.com/wp-content/uploads/2015/07/iStock_000040849990_Large.jpg', 'name', 1,
        2);

insert into product_entity_category_entity_list(product_entities_id, category_entity_list_id)
values (1, 1),
       (2, 1),
       (3, 1),
       (4, 1);

insert into product_purchase_entity (id, count, individual_price_at_purchase, product_entity_id)
values (1, 10, 0, 1);

insert into user_entity(id, email, first_name, is_account_expired, is_account_non_locked, is_credentials_non_expired, is_enabled, last_name, password)
values (1, 'p@gm.c', 'pooria', 0, 1, 1, 1, 'poorsarvi', 'password');

-- TODO move this two role creations in the code
insert into role (role) values ('ROLE_USER');
insert into role (role) values ('ROLE_ADMIN');

insert into user_entity_roles (user_entity_id, roles_role)
values (1, 'ROLE_USER');

insert into order_entity(id, address, amount_payed, purchase_date, finalised, owner_id)
values (1, 'address', 12, '2003-12-31', 0, 1);



insert into order_entity_products_purchased_entity_list (order_entity_id, products_purchased_entity_list_id)
values (1, 1);

insert into user_entity_order_entity_list (user_entity_id, order_entity_list_id)
values (1, 1);


insert into banner_entity (id, image_src, url)
values (1,
        'https://www.sanyoappliance.in/wp-content/uploads/2017/12/banner-1.jpg',
        '/main/tabs/cart');



insert into banner_entity (id, image_src, url)
values (2,
        'https://www.tcl.com/content/dam/tcl-dam/region/in/product/launch-banner-1.jpg',
        '/main/tabs/cart');



insert into banner_entity (id, image_src, url)
values (3,
        'https://www.sky-futures.com/wp-content/uploads/2018/04/Sky-Futures-web-banner-background-7.png',
        '/main/tabs/cart');

