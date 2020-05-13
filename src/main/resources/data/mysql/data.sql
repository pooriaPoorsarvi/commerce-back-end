insert into category_entity
values (1, 'image src', 'name');

insert into product_entity (id, description, image_src, name, price, number_of_purchases)
values (1,
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
        'https://worldstrides.com/wp-content/uploads/2015/07/iStock_000040849990_Large.jpg', 'name', 1,
        12),
       (2,
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
        'https://worldstrides.com/wp-content/uploads/2015/07/iStock_000040849990_Large.jpg', 'name', 1,
        10),
       (3,
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
        'https://worldstrides.com/wp-content/uploads/2015/07/iStock_000040849990_Large.jpg', 'name', 1,
        20),
       (4,
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
        'https://worldstrides.com/wp-content/uploads/2015/07/iStock_000040849990_Large.jpg', 'name', 1,
        2);

insert into product_entity_category_entity_list(product_entity_id, category_entity_list_id)
values (1, 1);

insert into product_purchase_entity (id, count, individual_price_at_purchase, product_entity_id)
values (1, 10, 0, 1);

insert into category_entity_product_entities(category_entity_id, product_entities_id)
values (1, 1);

insert into user_entity(id, email, first_name, last_name)
values (1, 'p@gm.c', 'pooria', 'poorsarvi');


insert into order_entity(id, address, amount_payed, purchase_date, finalised, owner_id)
values (1, 'address', 12, '2003-12-31', 0, 1);



insert into order_entity_products_purchased_entity_list (order_entity_id, products_purchased_entity_list_id)
values (1, 1);

insert into user_entity_order_entity_list (user_entity_id, order_entity_list_id)
values (1, 1);

