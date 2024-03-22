Insert into users(id, username, password, address, privilege)
values ('100','testA23','t65Fest','SANTO TOMAS NO. 549, SAN FRANCISCO, 38097','ADMIN');
Insert into users(id, username, password, address, privilege)
values ('0','9JohnDoe','OlcG6hinni','HENEQUEN NO. 236 Int. S/N, TERRENOS NACIONALES NORTE, 32598','SELLER');
Insert into users(id, username, password, address, privilege)
values ('1','JaneD89oe','rooD40t','ECORREAGA OTE NO. 212, CENTRO, 34000','USER');
Insert into users(id, username, password, address, privilege)
values ('2','Ju67anaPereza','r4Duta','SANTO TOMAS NO. 539, SAN FRANCISCO, 38097','USER');
Insert into users(id, username, password, address, privilege)
values ('3','Jua88nPerez','Ziner56gi','PRISCILIANO SANCHEZ NO. 631, CENTRO, 44100','USER');
Insert into users(id, username, password, address, privilege)
values ('4','Sab33ibialAhami','aaA5dmin','MAGNOCENTRO LT 1 MZ 2 LOCAL 16, LOMAS ANAHUAC, 99000','USER');

Insert into categories(id, categories)
values ('0','Hardware');
Insert into categories(id, categories)
values ('1','Software');
Insert into categories(id, categories)
values ('2','Other');

Insert into orders(id, status, created_date, users_id)
values ('0','OnSale','2024-01-29','3');
Insert into orders(id, status, created_date, users_id)
values ('1','OutofStock','2020-05-13','2');
Insert into orders(id, status, created_date, users_id)
values ('2','Sold','2023-11-29','1');
Insert into orders(id, status, created_date, users_id)
values ('3','Sold','2022-11-29','4');

Insert into products(id, price, name, sold, category_id, orders_id)
values ('0','3300','SBC Libre','0','2','1');
Insert into products(id, price, name, sold, category_id, orders_id)
values ('4','3300','SBC Libre','0','2','1');
Insert into products(id, price, name, sold, category_id, orders_id)
values ('1','1000','HS Intel 5','1','1','0');
Insert into products(id, price, name, sold, category_id, orders_id)
values ('2','4950','GPU 2150','0','0','2');
Insert into products(id, price, name, sold, category_id, orders_id)
values ('3','2000','Metasploit','0','1','3');
Insert into products(id, price, name, sold, category_id)
values ('5','10000.5','Metasploit2','0','1');