create table Orders (
id int auto_increment primary key,
product_id varchar(50) not null,
order_id varchar(50) not null,
qty int default 0,
unit_price int default 0,
total_price int default 0,
create_at datetime default now()
);
-- 마리아DB