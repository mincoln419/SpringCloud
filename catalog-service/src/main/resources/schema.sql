drop table if exists catalog CASCADE 
create table catalog (id bigint generated by default as identity, created_at timestamp default CURRENT_TIMESTAMP not null, product_id varchar(120) not null, product_name varchar(255) not null, stock integer not null, unit_price integer not null, primary key (id))
alter table catalog add constraint UK_9gggyslu2usn0rxs32mf055wq unique (product_id)