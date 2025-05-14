Create database if not exists shipping;
use shipping; 
create table if not exists user(
id int auto_increment, 
name varchar(50), 
email varchar(150), 
password varchar(150),
type enum('shipper',"admin","user"));

insert into user (name ,email,password,type) values
('Shenu','bb@getnada.com','Ba123456@'
,'user'); 

select * from user;

-- login
select * from user  where password = 'Aa123456@' and name = 'Chaitanya' limit 1;


-- seller

create table product(
	id int auto_increment,
    name varchar(255),
    description varchar(255),
    primary key (id)
);

insert into product( name , description) 
values ( 'samsung','Mobile'),
('K20','Charger'),
('Modulo','fan');

select *from product;

create table product_seller_relation(
id int auto_increment,price varchar(255),
seller_id int , product_id int,
meta text, shipping_charge int ,primary key(id)
);

insert into product_seller_relation(price,seller_id,product_id,meta,
shipping_charge) values
(1300,5,2,'{"warrenty": "1 year", "color": "red"}',40),
(1000,5,3,'{"warrenty": "3 year", "funtion": "clean"}',60);

select * from product_seller_relation where seller_id = 3;

select * from product_seller_relation as r
 join product as p
 on(r.product_id = p.id)
where seller_id = 3;

create table Orders(
order_id int auto_increment,
psrid int ,
user_id int ,
delivery_D date default null,
status enum ('ready' ,'pending','Cancelled','Delivered') default 'pending',
primary key(order_id));
drop table orders;
select * from user;
-- user
insert into Orders (psrid,user_id )values
(4,1),
(1,6);
select *from orders;

-- seller
update orders
set delivery_D = curdate(), status = 'ready'
where order_id = 2;


-- admin
alter table user add column approval_status enum ('1','2') ;
select * from user;
desc user;

alter table user modify column approval_status enum ('1','2','3') default null;

-- update --> changing value
-- modify --> changing field
-- new users
select * from user where type in ('user','shipper') and approval_status is null ;

update user set approval_status = '1' where id = 1;

update user set approval_status = '2' where id = 3;

update user set approval_status = '1' where id in (3,5,6);

select * from user 
where id = 3 and approval_status ='1';


 -- product list sareproduct 2) verified shipper
select * from user where type = 'shipper';
select * from product_seller_relation where 1 ;
select * from product;
-- list sabhi products seller unapproved na go
select *from user 
join product_seller_relation on seller_id = user.id
join product on product_id = product.id
where approval_status = '1' and type = 'shipper'; 
create table address(
Ad_id int auto_increment,
address text,
zipcode int,
user_id int,
primary key(Ad_id)

);
insert into address (address ,zipcode , user_id)
values ('kasersBazar',457001,1),
('sunarBawdi','457002',1);
select *from address ;
select *from address where user_id = 1; 

alter table product_seller_relation add column dely_status enum('delivered','pending')default 'pending';
alter table product_seller_relation drop column dely_status;
select *from product_seller_relation;

select *from orders;
desc orders; 