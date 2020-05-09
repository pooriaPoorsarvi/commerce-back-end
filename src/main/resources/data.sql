insert into category_entity
values (1, 'image src', 'name');

insert into product_entity (id, description, image_src, name, price)
values (1, 'description', 'image src', 'name', 1);

insert into product_entity_category_entity_list(product_entity_id, category_entity_list_id)
values (1, 1);

insert into category_entity_product_entity_list(category_entity_id, product_entity_list_id)
values (1, 1);


