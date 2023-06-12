

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
            values ('c1' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Zarko' , 'Lausevic' , 'Novi Sad' , '171-171',5600 , false, false);
insert into citizens ( username , password , first_name , last_name , city , phone , tokens, verify , block)
            values ( 'c2' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Marko' , 'Lazarevic' , 'Novi Sad' , '666-666',9000 , true, false);
insert into citizens (username , password , first_name , last_name , city , phone , tokens, verify , block)
            values ('c33' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Darko' , 'Aleksic' , 'Beograd' , '989-989',12000 , true, false);
insert into citizens ( username , password , first_name , last_name , city , phone , tokens, verify , block)
            values ('c54' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Pavle' , 'Avramovic' , 'Subotica' , '100-000',2000 , true, false);
insert into citizens ( username , password , first_name , last_name , city , phone , tokens, verify , block)
            values ('c55' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Zivorad' , 'Vasic' , 'Sabac' , '090-777',800 , true, false);
insert into citizens ( username , password , first_name , last_name , city , phone , tokens, verify , block)
            values ('c6' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Mladen' , 'Zejic' , 'Zajecar' , '413-371',100 , true, false);
insert into citizens (username , password , first_name , last_name , city , phone , tokens, verify , block)
            values ('c7' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Zivko' , 'Coklic' , 'Negotin' , '867-867',15600 , true, false);
insert into citizens ( username , password , first_name , last_name , city , phone , tokens, verify , block)
            values ('c77' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Aleksa' , 'Perovic' , 'Bor' , '334-222',45000 , true, false);
insert into citizens ( username , password , first_name , last_name , city , phone , tokens, verify , block)
            values ('c91' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Aleksandar' , 'Lausevic' , 'Bor' , '120-129',4999 , true, false);
insert into citizens ( username , password , first_name , last_name , city , phone , tokens, verify , block)
            values ('c99' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Adem' , 'Muratovic' , 'Tutin' , '477-779',3800 , true, false);

--LOCATIONS TABLE

    --VEHICLES START LOCATION
insert into locations ( longitude , latitude)
            values (45.259370, 19.822701);
insert into locations ( longitude , latitude)
            values (45.258766, 19.803475);
insert into locations ( longitude , latitude)
            values (45.252603, 19.838237);
insert into locations ( longitude , latitude)
            values (45.250851,19.790858);
insert into locations ( longitude , latitude)
            values (45.256168, 19.811543);
insert into locations ( longitude , latitude)
            values (45.262814, 19.839696);
insert into locations ( longitude , latitude)
            values (45.244022, 19.841413);
insert into locations ( longitude , latitude)
            values (45.248857, 19.842721);
insert into locations ( longitude , latitude)
            values (45.249491, 19.831735);
insert into locations ( longitude , latitude)
            values (45.254680, 19.829750);

--RIDE 1 LOCATIONS
insert into locations ( longitude , latitude)
            values (45.240336, 19.826660);
insert into locations ( longitude , latitude)
            values (45.242209, 19.834213);
insert into locations ( longitude , latitude)
            values (45.243720, 19.840994);
insert into locations ( longitude , latitude)
            values (45.249038, 19.838677);
insert into locations ( longitude , latitude)
            values (45.252301, 19.837046);
insert into locations ( longitude , latitude)
            values (45.256228, 19.834986);
insert into locations ( longitude , latitude)
values (45.256228, 19.834986);
insert into locations ( longitude , latitude)
values (45.260880, 19.832068);
insert into locations ( longitude , latitude)
            values (45.260880, 19.832068);
insert into locations ( longitude , latitude)
            values (45.264324, 19.830179);
insert into locations ( longitude , latitude)
            values (45.266197, 19.834986);
insert into locations ( longitude , latitude)
            values (45.269398, 19.831810);

--FUTURE LOCATIONS
insert into locations ( longitude , latitude)
            values (43.434343, 43.434343);
insert into locations ( longitude , latitude)
            values (88.888888, 88.888888);
insert into locations ( longitude , latitude)
            values (43.434343, 43.434343);
insert into locations ( longitude , latitude)
            values (88.888888, 88.888888);

--VEHICLES TABLE
insert into vehicles ( name , type , busy , pet_friendly , baby_friendly , location_id)
            values ('audi a6' , 'CAR' , false , true , true , 1);
insert into vehicles ( name , type , busy , pet_friendly , baby_friendly , location_id)
            values ( 'skoda' , 'CAR' , false , false , false , 2);
insert into vehicles ( name , type , busy , pet_friendly , baby_friendly , location_id)
            values ( 'audi a6' , 'CAR' , false , false , true , 3);
insert into vehicles ( name , type , busy , pet_friendly , baby_friendly , location_id)
            values ('passat' , 'CAR' , false , true , true , 4);
insert into vehicles ( name , type , busy , pet_friendly , baby_friendly , location_id)
            values ('bmw' , 'CAR' , true , false , true , 5);
insert into vehicles ( name , type , busy , pet_friendly , baby_friendly , location_id)
            values ('reno' , 'VAN' , false , true , true , 6);
insert into vehicles ( name , type , busy , pet_friendly , baby_friendly , location_id)
            values ('reno' , 'VAN' , false , true , true , 7);
insert into vehicles ( name , type , busy , pet_friendly , baby_friendly , location_id)
            values ('fiat' , 'VAN' , false , false , true , 8);
insert into vehicles (name , type , busy , pet_friendly , baby_friendly , location_id)
            values ('fiat' , 'MINI' , false , true , true , 9);
insert into vehicles ( name , type , busy , pet_friendly , baby_friendly , location_id)
            values ('citroen' , 'MINI' , false , false , false , 10);

--ROUTE TABLE(CURRENT)
insert into routes(route_index)
            values(0);
insert into routes(route_index)
values(0);
insert into routes(route_index)
            values(0);

--FUTURE RIDE
insert into routes(route_index)
            values(0);

--ROUTES LOCATION TABLE
insert into routes_locations(route_id,locations_id)
            values(1 ,11);
insert into routes_locations(route_id,locations_id)
            values(1 ,12);
insert into routes_locations(route_id,locations_id)
            values(1 ,13);
insert into routes_locations(route_id,locations_id)
            values(1 ,14);
insert into routes_locations(route_id,locations_id)
            values(1 ,15);
insert into routes_locations(route_id,locations_id)
            values(1 ,16);
insert into routes_locations(route_id,locations_id)
values(2 ,17);
insert into routes_locations(route_id,locations_id)
values(2 ,18);
insert into routes_locations(route_id,locations_id)
            values(3 ,19);
insert into routes_locations(route_id,locations_id)
            values(3 ,20);
insert into routes_locations(route_id,locations_id)
            values(3 ,21);
insert into routes_locations(route_id,locations_id)
            values(3 ,22);

--FUTURE ROUTES LOCATIONS
insert into routes_locations(route_id,locations_id)
            values(4 ,23);
insert into routes_locations(route_id,locations_id)
            values(4 ,24);
insert into routes_locations(route_id,locations_id)
            values(4 ,25);
insert into routes_locations(route_id,locations_id)
            values(4 ,26);

-- DRIVERS TABLE
insert into drivers ( username , password , first_name , last_name , city , phone , active , edit , block , vehicle_id,counter)
            values ('dr1' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Nikola' , 'Tolic' , 'Novi Sad' , '111-111',false , false , false , 1,0);
insert into drivers (username , password , first_name , last_name , city , phone , active , edit , block , vehicle_id,counter)
            values ('dr2' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Petar' , 'Perovic' , 'Beograd' , '222-222',false , false , false , 2,0);
insert into drivers ( username , password , first_name , last_name , city , phone , active , edit , block , vehicle_id,counter)
            values ('dr3' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Mladen' , 'Malic' , 'Nis' , '333-555',false , false , false , 3,0);
insert into drivers ( username , password , first_name , last_name , city , phone , active , edit , block , vehicle_id,counter)
            values ('dr4' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Marko' , 'Aksic' , 'Nis' , '555-333',true , false , false , 4,0);
insert into drivers ( username , password , first_name , last_name , city , phone , active , edit , block , vehicle_id,counter)
            values ('dr5' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Aco' , 'Zavovic' , 'Subotica' , '345-345',true , false , false , 5,0);
insert into drivers ( username , password , first_name , last_name , city , phone , active , edit , block , vehicle_id,counter)
            values ('dr6' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Aleksa' , 'Tolic' , 'Kragujevac' , '399-399',true , false , false , 6,0);
insert into drivers ( username , password , first_name , last_name , city , phone , active , edit , block , vehicle_id,counter)
            values ('dr7' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Vuk' , 'Keric' , 'Cacak' , '994-993',true , false , false , 7,0);
insert into drivers ( username , password , first_name , last_name , city , phone , active , edit , block , vehicle_id,counter)
            values ('dr8' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Nemanja' , 'Mamovic' , 'Lubovija' , '109-901',true , false , false , 8,0);
insert into drivers ( username , password , first_name , last_name , city , phone , active , edit , block , vehicle_id,counter)
            values ( 'dr9' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Stefan' , 'Zulovic' , 'Nis' , '632-572',true , false , false , 9,0);
insert into drivers ( username , password , first_name , last_name , city , phone , active , edit , block , vehicle_id,counter)
            values ('dr11' , '$2a$12$zf/xQmgOilOlFgiZylI/veC2SWmgcNUYbpnsBwL4dFu0sjun4xD2C' , 'Ognjen' , 'Zaric' , 'Leskovac' , '782-888',true , false , false , 10,0);

--CURRENT RIDE
INSERT INTO rides(BABY_FRIENDLY,PRICE, COMMENT, END_DATE, METERS, NAME, PAID, PANIC, PET_FRIENDLY, START, STATUS, TYPE, DRIVER_ID)
VALUES (true,3232,'', DATEDIFF('MS', '1970-01-01 00:00:00', DATEADD('MI', 50, CURRENT_TIMESTAMP())), 340, 'voznja posao', 'c2', false, true, DATEDIFF('MS', '1970-01-01 00:00:00', CURRENT_TIMESTAMP()), 'START', 'MINI', 5);

--CURRENT RIDE
UPDATE routes SET ride_id = 1 WHERE id = 1;
UPDATE routes SET ride_id = 1 WHERE id = 2;
UPDATE routes SET ride_id = 1 WHERE id = 3;

UPDATE drivers SET current_ride_id = 1 WHERE id = 5;

--FUTURE RIDE
INSERT INTO rides(BABY_FRIENDLY,PRICE, COMMENT, END_DATE,  METERS, NAME, PAID, PANIC, PET_FRIENDLY, START, STATUS, TYPE, DRIVER_ID)
VALUES (true,5545, '', DATEDIFF('MS', '1972-01-01 00:00:00', DATEADD('MI', 50, CURRENT_TIMESTAMP())), 790, 'voznja next', 'c2', false, true, DATEDIFF('MS', '1972-01-01 00:00:00', CURRENT_TIMESTAMP()), 'PAID', 'CAR', 5);


UPDATE routes SET ride_id = 2 WHERE id = 4;

UPDATE drivers SET future_ride_id = 2 WHERE id = 5;

insert into CITIZENS_RIDES(CITIZENS_ID,RIDES_ID)
        values(2 ,1);
insert into CITIZENS_RIDES(CITIZENS_ID,RIDES_ID)
values(2 ,2);


--FOR DELETE AFTER KTS

--FUTURE RIDE
INSERT INTO rides(BABY_FRIENDLY,PRICE, COMMENT, END_DATE,  METERS, NAME, PAID, PANIC, PET_FRIENDLY, START, STATUS, TYPE, DRIVER_ID)
VALUES (true,5545, '', DATEDIFF('MS', '1972-01-01 00:00:00', DATEADD('MI', 50, CURRENT_TIMESTAMP())), 790, 'voznja next', 'c2', false, true, DATEDIFF('MS', '1972-01-01 00:00:00', CURRENT_TIMESTAMP()), 'PAID', 'CAR', 4);


UPDATE drivers SET future_ride_id = 3 WHERE id = 4;

INSERT INTO rides(BABY_FRIENDLY,PRICE, COMMENT, END_DATE,  METERS, NAME, PAID, PANIC, PET_FRIENDLY, START, STATUS, TYPE, DRIVER_ID)
VALUES (true,5545, '', DATEDIFF('MS', '1972-01-01 00:00:00', DATEADD('MI', 50, CURRENT_TIMESTAMP())), 790, 'voznja next', 'c2', false, true, DATEDIFF('MS', '1972-01-01 00:00:00', CURRENT_TIMESTAMP()), 'END', 'MINI', 6);

UPDATE drivers SET current_ride_id = 4 WHERE id = 6;

INSERT INTO rides(BABY_FRIENDLY,PRICE, COMMENT, END_DATE,  METERS, NAME, PAID, PANIC, PET_FRIENDLY, START, STATUS, TYPE, DRIVER_ID)
VALUES (true,5545, '', DATEDIFF('MS', '1972-01-01 00:00:00', DATEADD('MI', 50, CURRENT_TIMESTAMP())), 790, 'voznja next', 'c2', false, true, DATEDIFF('MS', '1972-01-01 00:00:00', CURRENT_TIMESTAMP()), 'PAID', 'MINI', 7);

UPDATE drivers SET future_ride_id = 5 WHERE id = 7;

INSERT INTO rides(BABY_FRIENDLY,PRICE, COMMENT, END_DATE,  METERS, NAME, PAID, PANIC, PET_FRIENDLY, START, STATUS, TYPE, DRIVER_ID)
VALUES (true,5545, '', DATEDIFF('MS', '1972-01-01 00:00:00', DATEADD('MI', 50, CURRENT_TIMESTAMP())), 790, 'voznja next', '', false, true, DATEDIFF('MS', '1972-01-01 00:00:00', CURRENT_TIMESTAMP()), 'CREATE', 'MINI', 8);

UPDATE drivers SET future_ride_id = 6 WHERE id = 8;

insert into CITIZENS_RIDES(CITIZENS_ID , RIDES_ID) values (3,6);

--RIDE TABLE
--insert into rides(BABY_FRIENDLY,COMMENT,END_DATE,FUTURE,METERS,NAME,PAID,PANIC,PET_FRIENDLY,START,STATUS,TYPE,DRIVER_ID)
--            values(true,'',CURRENT_TIMESTAMP , false,340 , 'voznja posao' , '',false , true , CURRENT_TIMESTAMP , 'START','MINI',5);


insert into CITIZENS_FAVORITE(CITIZEN_ID , FAVORITE_ID) values (2,1);

insert into MESSAGES(SENDER,RECEIVER,MESSAGE,DATE) values ('c2' , 'ADMIN' , 'ajde admine' , 695696);
insert into MESSAGES(SENDER,RECEIVER,MESSAGE,DATE) values ('ADMIN' , 'c2' , 'evo adminanadsa' , 4567907);
insert into MESSAGES(SENDER,RECEIVER,MESSAGE,DATE) values ('c2' , 'ADMIN' , 'vidim sve' , 43678911);
insert into MESSAGES(SENDER,RECEIVER,MESSAGE,DATE) values ('c2' , 'ADMIN' , 'sta se radii' , 324679763);
insert into MESSAGES(SENDER,RECEIVER,MESSAGE,DATE) values ('c33' , 'ADMIN' , 'odg miii' , 589898);
insert into MESSAGES(SENDER,RECEIVER,MESSAGE,DATE) values ('dr5' , 'ADMIN' , 'odg miii' , 589898);