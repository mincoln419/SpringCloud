drop table if exists orders CASCADE 
create table orders (id bigint generated by default as identity, created_at timestamp default CURRENT_TIMESTAMP not null, order_id varchar(255) not null, product_id varchar(120) not null, qty integer not null, total_price integer not null, unit_price integer not null, user_id varchar(255) not null, primary key (id))
alter table orders add constraint UK_hmsk25beh6atojvle1xuymjj0 unique (order_id)
alter table orders add constraint UK_306w8sghgvp5hmjrqo21dscv7 unique (product_id)