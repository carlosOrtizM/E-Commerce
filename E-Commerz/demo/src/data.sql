Insert into products(product_id, price, name, sold, category_id)
values ('0','3300','SBC Libre','0','100000');
Insert into products(product_id, price, name, sold, category_id)
values ('1','1000','HS Intel 5','0','100005');
Insert into products(product_id, price, name, sold, category_id)
values ('2','4950','GPU 2150','0','100006');
Insert into products(product_id, price, name, sold, category_id)
values ('3','2000','Metasploit','0','100003');
Insert into products(product_id, price, name, sold, category_id)
values ('4','3300','SBC Libre','0','100001');

Insert into users(user_id, username, password, address, privilege)
values ('0','John Doe','Olchinni','HENEQUEN NO. 236 Int. S/N, TERRENOS NACIONALES NORTE, 32598');
Insert into users(user_id, username, password, address, privilege)
values ('1','Jane Doe','root','ECORREAGA OTE NO. 212, CENTRO, 34000');
Insert into users(user_id, username, password, address, privilege)
values ('2','Juana Pereza','ruta','SANTO TOMAS NO. 539, SAN FRANCISCO, 38097');
Insert into users(user_id, username, password, address, privilege)
values ('3','Juan Perez','Zinergi','PRISCILIANO SANCHEZ NO. 631, CENTRO, 44100');
Insert into users(user_id, username, password, address, privilege)
values ('4','Sabibi al Ahami','admin','MAGNOCENTRO LT 1 MZ 2 LOCAL 16, LOMAS ANAHUAC');
Insert into users(user_id, username, password, address, privilege)
values ('100','test','test','test');

Insert into orders(order_id, status, created_date, user_id)
values ('0','Vendido','2024-01-29','3');
Insert into orders(order_id, status, created_date, user_id)
values ('1','Inicializado','2020-05-13','2');
Insert into orders(order_id, status, created_date, user_id)
values ('2','Vendido','2023-11-29','1');
Insert into orders(order_id, status, created_date, user_id)
values ('3','Vendido','2022-11-29','100');

Insert into product_order(product_id, order_id)
values ('0','3');
Insert into product_order(product_id, order_id)
values ('1','2');
Insert into product_order(product_id, order_id)
values ('2','1');
Insert into product_order(product_id, order_id)
values ('3','0');