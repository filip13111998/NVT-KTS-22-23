

--ADMINS TABLE
insert into admins ( username , password , first_name , last_name , city , phone)
            values ( 'adm1' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Nikola' , 'Nikolic' , 'Novi Sad' , '432-555');
insert into admins ( username , password , first_name , last_name , city , phone)
            values ('adm2' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Petar' , 'Peric' , 'Novi Sad' , '433-111');
insert into admins (username , password , first_name , last_name , city , phone)
            values ( 'adm3' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Zarko' , 'Dragic' , 'Loznica' , '800-767');
insert into admins ( username , password , first_name , last_name , city , phone)
            values ( 'adm44' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Dobrosav' , 'Drazenovic' , 'Pozarevac' , '000-239');
insert into admins (username , password , first_name , last_name , city , phone)
            values ( 'adm55' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Nino' , 'Ninic' , 'Beograd' , '333-111');

--CITIZENS TABLE
insert into citizens ( username , password , first_name , last_name , city , phone , tokens, verify , block)
            values ('ct1' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Zarko' , 'Lausevic' , 'Novi Sad' , '171-171',5600 , false, false);
insert into citizens ( username , password , first_name , last_name , city , phone , tokens, verify , block)
            values ( 'ct2' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Marko' , 'Lazarevic' , 'Novi Sad' , '666-666',9000 , true, false);
insert into citizens (username , password , first_name , last_name , city , phone , tokens, verify , block)
            values ('ct33' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Darko' , 'Aleksic' , 'Beograd' , '989-989',12000 , true, false);
insert into citizens ( username , password , first_name , last_name , city , phone , tokens, verify , block)
            values ('ct54' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Pavle' , 'Avramovic' , 'Subotica' , '100-000',2000 , true, false);
insert into citizens ( username , password , first_name , last_name , city , phone , tokens, verify , block)
            values ('ct55' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Zivorad' , 'Vasic' , 'Sabac' , '090-777',800 , true, false);
insert into citizens ( username , password , first_name , last_name , city , phone , tokens, verify , block)
            values ('ct6' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Mladen' , 'Zejic' , 'Zajecar' , '413-371',100 , true, false);
insert into citizens (username , password , first_name , last_name , city , phone , tokens, verify , block)
            values ('ct7' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Zivko' , 'Coklic' , 'Negotin' , '867-867',15600 , true, false);
insert into citizens ( username , password , first_name , last_name , city , phone , tokens, verify , block)
            values ('ct77' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Aleksa' , 'Perovic' , 'Bor' , '334-222',45000 , true, false);
insert into citizens ( username , password , first_name , last_name , city , phone , tokens, verify , block)
            values ('ct91' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Aleksandar' , 'Lausevic' , 'Bor' , '120-129',4999 , true, false);
insert into citizens ( username , password , first_name , last_name , city , phone , tokens, verify , block)
            values ('ct99' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Adem' , 'Muratovic' , 'Tutin' , '477-779',3800 , true, false);

--LOCATIONS TABLE

    --VEHICLES START LOCATION
insert into locations ( longitude , latitude)
            values ( 45.2222,45.1711);
insert into locations ( longitude , latitude)
            values ( 45.2222,45.1711);
insert into locations ( longitude , latitude)
            values (45.2222,45.1711);
insert into locations ( longitude , latitude)
            values ( 45.2222,45.1711);
insert into locations ( longitude , latitude)
            values (45.2222,45.1711);
insert into locations ( longitude , latitude)
            values ( 45.2222,45.1711);
insert into locations ( longitude , latitude)
            values ( 45.2222,45.1711);
insert into locations ( longitude , latitude)
            values (45.2222,45.1711);
insert into locations ( longitude , latitude)
            values (45.2222,45.1711);
insert into locations ( longitude , latitude)
            values ( 45.2222,45.1711);

--VEHICLES TABLE
insert into vehicles ( name , type , busy , pet_friendly , baby_friendly , location_id)
            values ('audi a6' , 'CAR' , false , true , true , 1);
insert into vehicles ( name , type , busy , pet_friendly , baby_friendly , location_id)
            values ( 'skoda' , 'CAR' , false , false , false , 2);
insert into vehicles ( name , type , busy , pet_friendly , baby_friendly , location_id)
            values ( 'audi a6' , 'CAR' , false , false , true , 3);
insert into vehicles ( name , type , busy , pet_friendly , baby_friendly , location_id)
            values ('passat' , 'CAR' , true , true , true , 4);
insert into vehicles ( name , type , busy , pet_friendly , baby_friendly , location_id)
            values ('bmw' , 'CAR' , true , false , true , 5);
insert into vehicles ( name , type , busy , pet_friendly , baby_friendly , location_id)
            values ('reno' , 'VAN' , false , true , true , 6);
insert into vehicles ( name , type , busy , pet_friendly , baby_friendly , location_id)
            values ('reno' , 'VAN' , false , true , true , 7);
insert into vehicles ( name , type , busy , pet_friendly , baby_friendly , location_id)
            values ('fiat' , 'VAN' , true , false , true , 8);
insert into vehicles (name , type , busy , pet_friendly , baby_friendly , location_id)
            values ('fiat' , 'MINI' , false , true , true , 9);
insert into vehicles ( name , type , busy , pet_friendly , baby_friendly , location_id)
            values ('citroen' , 'MINI' , false , false , false , 10);

-- DRIVERS TABLE
insert into drivers ( username , password , first_name , last_name , city , phone , active , edit , block,future , vehicle_id)
            values ('dr1' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Nikola' , 'Tolic' , 'Novi Sad' , '111-111',false , false , false ,false, 1);
insert into drivers (username , password , first_name , last_name , city , phone , active , edit , block,future , vehicle_id)
            values ('dr2' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Petar' , 'Perovic' , 'Beograd' , '222-222',false , false , false ,false, 2);
insert into drivers ( username , password , first_name , last_name , city , phone , active , edit , block,future , vehicle_id)
            values ('dr3' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Mladen' , 'Malic' , 'Nis' , '333-555',false , false , false,false , 3);
insert into drivers ( username , password , first_name , last_name , city , phone , active , edit , block,future , vehicle_id)
            values ('dr4' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Marko' , 'Aksic' , 'Nis' , '555-333',true , false , false,false , 4);
insert into drivers ( username , password , first_name , last_name , city , phone , active , edit , block,future , vehicle_id)
            values ('dr5' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Aco' , 'Zavovic' , 'Subotica' , '345-345',true , false , false ,false, 5);
insert into drivers ( username , password , first_name , last_name , city , phone , active , edit , block ,future, vehicle_id)
            values ('dr6' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Aleksa' , 'Tolic' , 'Kragujevac' , '399-399',true , false , false ,false, 6);
insert into drivers ( username , password , first_name , last_name , city , phone , active , edit , block,future , vehicle_id)
            values ('dr7' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Vuk' , 'Keric' , 'Cacak' , '994-993',true , false , false ,false, 7);
insert into drivers ( username , password , first_name , last_name , city , phone , active , edit , block,future , vehicle_id)
            values ('dr8' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Nemanja' , 'Mamovic' , 'Lubovija' , '109-901',true , false , false,false , 8);
insert into drivers ( username , password , first_name , last_name , city , phone , active , edit , block,future , vehicle_id)
            values ( 'dr9' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Stefan' , 'Zulovic' , 'Nis' , '632-572',true , false , false ,false, 9);
insert into drivers ( username , password , first_name , last_name , city , phone , active , edit , block,future , vehicle_id)
            values ('dr11' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Ognjen' , 'Zaric' , 'Leskovac' , '782-888',true , false , false ,false, 10);




