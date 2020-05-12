insert into category_entity
values (1, 'image src', 'name');

insert into product_entity (id, description, image_src, name, price)
values (1, 'description', 'image src', 'name', 1);

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

insert into shopping_cart_entity(id)
values (1);


insert into shopping_cart_entity_product_entities (shopping_cart_entity_id, product_entities_id)
values (1, 1);

insert into order_entity_products_purchased_entity_list (order_entity_id, products_purchased_entity_list_id)
values (1, 1);

insert into user_entity_order_entity_list (user_entity_id, order_entity_list_id)
values (1, 1);

