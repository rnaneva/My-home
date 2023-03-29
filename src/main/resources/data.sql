INSERT INTO cities(name)
VALUES ('Sofia'),
       ('Varna'),
       ('Bourgas');

INSERT INTO categories(name)
VALUES ('one-bedroom'),
       ('two-bedroom'),
       ('studio');

INSERT INTO roles(role)
VALUES ('USER'),
       ('ADMIN'),
       ('MODERATOR');

INSERT INTO locations(city_id, address)
VALUES (3, 'Sv. Nedelia 6'),
       (2, 'General Kolev 54'),
       (1, 'Tsar Asen 22'),
       (3, 'Graf Ignatiev 4'),
       (3, 'Sea Diamond'),
       (3, 'Harmony Palace'),
       (3, 'Harmony Suites 8'),
       (3, 'Vineyards Panorama'),
       (3, 'Vineyards Panorama'),
       (2, 'Messembria Palace'),
       (1, 'Iztok'),
       (1, 'Dianabad'),
       (3, 'Bratya Miladinovi'),
       (2, 'St. St. Constantine and Elena'),
       (1, 'Center'),
       (1, 'Boyana');


INSERT INTO agencies(name, location_id, phone_number, logo_url, status)
VALUES ('Address', 1, '0898433193',
        'https://res.cloudinary.com/dipiksmcm/image/upload/v1679216206/address_agn_v1u58w.png', 'ACTIVE'),
       ('Revolution', 2, '0892223193',
        'https://res.cloudinary.com/dipiksmcm/image/upload/v1679425547/revolutionesiz_n4sk3h.jpg', 'ACTIVE'),
       ('Luximmo', 3, '0222433193',
        'https://res.cloudinary.com/dipiksmcm/image/upload/v1679425547/buildingbox_xqqfks.jpg', 'INACTIVE'),
       ('Building box', 4, '0898433222',
        'https://res.cloudinary.com/dipiksmcm/image/upload/v1679425547/luximmo_l7acsg.jpg', 'ACTIVE');



--
-- --     User / pass - 1234
INSERT INTO users(names, email, username, password, agency_id)
VALUES ('Ivan Ivanov', 'admin@mail.bg', 'admin', '$2a$10$.G579WPigB9m3Glwd9tzaOSJQ0KnDXYHzpvGmBfvFNQvIVRmK4haC', 1);
INSERT INTO users(names, email, username, password)
VALUES ('Maxim Maximov', 'moderator@mail.bg', 'moderator',
        '$2a$10$.G579WPigB9m3Glwd9tzaOSJQ0KnDXYHzpvGmBfvFNQvIVRmK4haC'),
       ('Maria Marieva', 'maria@mail.bg', 'maria', '$2a$10$.G579WPigB9m3Glwd9tzaOSJQ0KnDXYHzpvGmBfvFNQvIVRmK4haC'),
       ('Pesho Petrov', 'pesho@mail.bg', 'pesho', '$2a$10$.G579WPigB9m3Glwd9tzaOSJQ0KnDXYHzpvGmBfvFNQvIVRmK4haC');



INSERT INTO users_roles(user_entity_id, roles_id)
VALUES (1, 2),
       (2, 3),
       (3, 1),
       (4, 1);


--     Search
INSERT INTO search_criteria(type, category_id, city_id, visible_id)
VALUES ('SALE', 1, 2, 'fffrrr5'),
       ('RENT', 2, 3, 'rrhgjtyl'),
       ('SALE', 1, 1, 'gkgeopelo');


INSERT INTO search_criteria(type, category_id, construction, heating, max_price, min_area,
                            city_id, agency_id, visible_id, sort_by, user_id)
VALUES ('RENT', 1, 'LFW', 'GAS', 1800, 50, 1, 4, 'fjfhfhfjf', 'price', 3),
       ('SALE', 3, 'BRICK', 'ELECTRICITY', 20000, 35, 2, 1, 'fjdyyddj', 'date', 3),
       ('RENT', 2, 'BRICK', 'TTP', 700, 40, 3, 2, 'ncbchdjd', 'rating', 4);

INSERT INTO pictures(url)
VALUES ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524940/4_mhc31u.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524940/3_hjgnqf.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524940/1_cjlmir.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524940/2_tij2ix.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524940/5_r3zbc5.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524940/6_tmgy84.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524940/7_lb6me3.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524941/8_n2m8ai.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524941/9_hmikbt.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524940/10_zfnlu1.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524940/11_ihujxl.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524940/12_n3ywt7.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524941/13_hucz1y.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524941/14_quubqh.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524941/15_wb3b7r.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524941/16_sfkutc.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524941/17_tx8u3x.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524941/18_rgammw.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524941/19_w7sm8a.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524941/20_cvc83v.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524942/21_gwlyx3.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524942/22_khhodk.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524941/23_cbjkmk.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524942/24_ddbmnr.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524942/25_ruaykh.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524942/26_ov969n.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524942/27_aukrzs.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524942/28_mcm3vo.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524942/29_qcvstj.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524942/31_m1wzhh.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524942/30_tl4uo3.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524942/32_jb7awm.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524943/33_ucvhaq.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524943/34_ocjmbg.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524943/35_vennnu.jpg'),
       ('https://res.cloudinary.com/dipiksmcm/image/upload/v1679524943/36_hdgel2.jpg');


-- todo name max 50

INSERT INTO offers_first_page(name, type, category_id, construction, heating, price, area, description)
VALUES ('One bedroom apartment in a quiet and cosy location', 'SALE', 1, 'BRICK', 'ELECTRICITY', 46900, 54,
        'For sale is a one-bedroom apartment in a complex in Sunny Beach, in a quiet and cozy place near the town of Nessebar. The flat is located on the high ground floor of the complex, the total area is 54 m2. It consists of an entrance hall, spacious entrance hall with kitchen, bedroom, bathroom with toilet and balcony overlooking the pool. The flat is fully furnished and equipped. The complex is located in the southern part of Sunny Beach, a ten minute walk to Cocoa Beach. Shops, restaurants and playgrounds are within walking distance. The complex has an outdoor pool, fitness, reception, playground, year-round security, parking.'),
       ('Two bedroom apartment in a beautiful luxury complex', 'SALE', 2, 'LFW', 'TPP', 90000, 108,
        'One bedroom apartment in a unique luxury complex, which has been developed in a luxurious location in the most picturesque and ecologically clean area on the Black Sea coast of Bulgaria, a green oasis on the shores of the warm sea. The complex is designed in a unique architectural style and represents a mega-complex, consisting of several buildings, united by an original and unique territory and architecture. The complex is located in a quiet and cozy area of Sunny Beach. The residents of the complex may appreciate the whole range of facilities and services, which are offered there. The flat of 53 m2 is located on the sixth floor and is sold fully furnished and equipped.'),
       ('Two bedroom apartment in a unique luxury complex', 'SALE', 2, 'LFW', 'TPP', 99500, 75,
        'Two bedroom apartment in a beautiful gated luxury residential complex. The premium class complex is the real epitome of seaside comfort. It is located in a picturesque corner, in the northern, most prestigious part of the resort of Sunny Beach, near the park. It is a five minute walk from the beach, close to excellent shopping, gourmet restaurants, trendy bars and clubs. The largest water park is 800 metres from this magnificent complex. The proximity to the sea, parks and infrastructure make it an attractive and comfortable place to live and relax. This 75m2 flat is located on the sixth floor. It consists of a hallway with kitchen and sitting area, two bedrooms, dressing room, bathroom with WC and a terrace. It is fully furnished and ready to move in.'),
       ('Two-bedroom apartment with in an amazing complex', 'SALE', 2, 'PANEL', 'GAS', 155000, 127,
        'Spacious new panoramic two bedroom apartment with sea views in an unforgettable complex of vineyards on the outskirts of the small seaside town of Aheloy. The flat is on the fourth floor and has a living area of 126.60 m2. It comprises of a living room with kitchen, two bedrooms, bathroom with WC, bathroom unit and balcony. From the balcony there is a splendid view to the sea and the surrounding area. The property is for sale unfurnished, with renovation and equipped bathrooms. The complex has all the necessary infrastructure for living and leisure, including swimming pools, restaurant, playground, security, reception. The international airport is 15-20 minutes away by car.'),
       ('Studio in a new phase of a luxury complex', 'SALE', 3, 'BRICK', 'ELECTRICITY', 46300, 42,
        'Studio in a new phase of a luxury gated development in an area with clean environment. The complex is a couple of kilometres away from the beach and the small picturesque town of Aheloy and the beach. The complex has a large secured area. There is an excellent indoor area with full infrastructure, indoor pool, outdoor pools, spa services, fitness room, lift, security, parking, etc. The flat of 42 m2 is located on the high ground floor of the building. The apartment layout includes an entrance hall, lounge with kitchenette, bathroom with toilet, terrace. It is sold with Euro renovation, unfurnished.'),
       ('Two bedroom apartment in a luxury complex', 'SALE', 2, 'LFW', 'TPP', 79990, 75,
        'Two-bedroom apartment in a luxury gated development in Sunny Beach resort. The flat is well renovated and is being sold fully furnished and with appliances already installed. The size of the apartment is 75 m2 and it is located on the first floor. The distribution of the flat is as follows - entrance hall, living room with kitchenette and sitting area, two bedrooms, bathroom with shower and WC, balcony. Located near the large clean beach "Cacao" and offers a variety of services and entertainment. The lavishly landscaped grounds include restaurants, karaoke bar, large outdoor pool, aqua park, indoor pool, spa centre, gym, beauty salon and more. There is a separate pool and playground for the little ones. There are volleyball, football and even mini golf courts. The complex is open all year round and is under constant security.'),
       ('2-bedroom apartment in Sofia', 'RENT', 2, 'LFW', 'TPP', 1700, 110,
        'We are pleased to present a three bedroom apartment awaiting its new tenants. It is characterized by a top location in the Iztok district, 700 meters from the Joliot Curie metro station, near the Lidl supermarket, Izgrev Metropolitan Municipality, the Japanese Embassy, traffic police, Borisova Gradina park, Sofia Vocational High School of Electronics "John Atanasov", other educational pubs, kindergartens, pharmacies, shops, restaurants, sports facilities.The heating is central and with split system air conditioning for each room. The property is equipped with soundproofing on the floor, an armored entrance door.The building is finished in 2020 with extremely luxurious common areas with natural onyx, 2 cm marble and wood, ventilated facade, Schindler elevator, Pirnar Premium entrance door with HIKVISION intercom system (IP system) with facial recognition.'),
       ('2-bedroom apartment in new emblematic building in Dianabad district', 'RENT', 2, 'BRICK', 'TPP', 1200, 75,
        'Fully furnished and equipped luxury apartment with brand new furniture and electrical appliances of the highest class and top location in Dianabad district, next to metro station G.M. Dimitrov, LITEX Towers, OMV, BILLA supermarket, Dragan Tsankov Blvd. The monthly maintenance fee is 133 BGN. The apartment has 2 bedroom, without transitition, brand new, set on the 9/16 floor of a modern building (2022) with underground garage, live security, video surveillance, luxurious common areas, 4 elevators and unique garden (park type), built especially for the residents of the complex with limited access and recreation areas. The heating is central and with air conditioners in all rooms. The property is finished with LED lighting throughout the apartment, high-end flooring, top-class soundproof windows - triple glazing, external and internal insulation, internet and cable TV. It offers panoramic view of Vitosha mountain and the cityName.'),
       ('2-bedroom apartment in Burgas', 'RENT', 2, 'LFW', 'TPP', 330, 60,
        'We offer you this 2-bedroom apartment with a total area of 80 m2. It was constructed in 1965. The property is supplied with air-conditioners and electrical heating system. Laminate flooring, painted walls, tiled flooring, upvc window frames and wall-to-wall carpet are available in the property. The furniture and appliances that are available include: an air-conditioner, a bathtub, a cooker, an electric boiler and a fridge. The property is connected to electricity, water and mains drainage. Some of the other available facilities that you will certainly consider important include: a balcony, a bathroom and a toilet. From the inside you will be able to enjoy wonderful town views.'),
       ('One-bedroom apartment for rent in the Carpe Vita complex', 'RENT', 1, 'LFW', 'ELECTRICITY', 700, 55,
        'We present to your attention an apartment for rent in an elite gated complex, located 300 m from the beach of St. St. Constantine and Elena. The oldest Bulgarian Black Sea resort, located in a picturesque, natural park. The place harmoniously intertwines ancient history, exotic nature and modern architecture. The gentle rays of the sun, the sparkling waters of the sea, as well as the fresh air of the forest and the magical aroma of the ancient trees, leave an unforgettable impression.The complex consists of five buildings with studios, one- and two bedroom apartments, with an outdoor swimming pool, garden and recreation area, spa complex, restaurant, shop, playground and parking. Each of the luxury buildings offers turn-key apartments finished with natural materials (parquet and stone), with an air conditioner in each room.'),
       ('Top one-bedroom apartment next to the iconic Graf Ignatiev street in Sofia', 'RENT', 1, 'PANEL', 'TPP', 730,
        75,
        'Looking for a property with an uncompromising location in perfect condition in the top center of the capital? View this property - spacious, bright and fully renovated apartment near Vasil Levski Stadium, Graf Ignatiev Str., Knyazhevska gradina and Borisova gradina parks, daily facilities, entertainment and recreation areas, fine restaurants and cafes, boutique shops, cultural attractions from the heart of Sofia.Exclusive offer for rent of an elegant home in the center of the capital, with easy access to the park of the St. Sedmochislenitsi, Patriarh Evtimii Square (Popa), Sofia University St. Kliment Ohridski, National Palace of Culture, Alexander Nevski Cathedral, Ivan Vazov National Theater and other places emblematic for the capital. The heating is with electricity. The floors are with terracotta tiles and laminate. You can see the complete furniture and equipment in the photos.'),
       ('1-bedroom apartment in Boyana quarter', 'RENT', 1, 'PANEL', 'ELECTRICITY', 400, 70,
        'South-facing 1-bedroom apartment with nice location in the preferred part of Boyana district - Gardova Glava area, near Nikola Petkov Blvd., Tsar Boris III Blvd., Zname na mira Square, Fanatastico supermarket, Boyana Residence, kindergarten, school, shopping center, stop of trams 4, 5, 11 and buses 107, 111, 260. The location provides easy and convenient connection with the central part of the cityName and Vitosha Nature Park, with excellent places for walks and relaxation in nature. In the area you will find the Ring road, gas station, Knyazevo mineral water, a number of other amenities and everyday services. The heating is with electricity. The floors are with terracotta tiles and laminate. You can see the complete furniture and equipment in the photos.');



INSERT INTO offers_second_page(location_id, construction_year, parking, elevator, floor,
                               all_floors, bedrooms, bathrooms, balconies)
VALUES (5, 2015, 'YES', 'YES', 5, 6, 1, 1, 1),
       (6, 2015, 'YES', 'YES', 5, 6, 1, 1, 1),
       (7, 2014, 'NO', 'NO', 3, 10, 2, 1, 1),
       (8, 2023, 'YES', 'NO', 4, 4, 2, 1, 2),
       (9, 2023, 'YES', 'YES', 1, 3, 1, 1, 1),
       (10, 2012, 'NO', 'YES', 2, 5, 2, 1, 1),
       (11, 2015, 'NO', 'YES', 4, 7, 2, 2, 1),
       (12, 2022, 'YES', 'YES', 9, 16, 2, 1, 3),
       (13, 1968, 'NO', 'YES', 5, 10, 2, 1, 2),
       (14, 2015, 'YES', 'YES', 2, 5, 1, 2, 1),
       (15, 1998, 'YES', 'NO', 2, 3, 1, 2, 2),
       (16, 2018, 'YES', 'YES', 1, 2, 1, 1, 1);



INSERT INTO offers(created_on, is_deleted, visible_id, agency_id, offer_page1_id, offer_page2_id,
                   status)
VALUES (NOW(), 0, 1111, 1, 1, 1, 'ACTIVE'),
       (NOW(), 0, 1112, 1, 2, 2, 'ACTIVE'),
       (NOW(), 1, 1113, 1, 3, 3, 'INACTIVE'),
       (NOW(), 0, 1114, 1, 4, 4, 'ACTIVE'),
       (NOW(), 0, 1115, 1, 5, 5, 'ACTIVE'),
       (NOW(), 0, 1116, 1, 6, 6, 'ACTIVE'),
       (NOW(), 1, 1117, 1, 7, 7, 'INACTIVE'),
       (NOW(), 0, 1118, 1, 8, 8, 'ACTIVE'),
       (NOW(), 0, 1119, 1, 9, 9, 'ACTIVE'),
       (NOW(), 0, 1120, 1, 10, 10, 'ACTIVE'),
       (NOW(), 0, 1121, 1, 11, 11, 'ACTIVE'),
       (NOW(), 0, 1122, 1, 12, 12, 'ACTIVE');



INSERT INTO offers_pictures(offer_entity_id, pictures_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (2, 5),
       (2, 6),
       (2, 7),
       (2, 8),
       (3, 9),
       (3, 10),
       (3, 11),
       (3, 12),
       (4, 13),
       (4, 14),
       (4, 15),
       (4, 16),
       (5, 17),
       (5, 18),
       (5, 19),
       (5, 20),
       (6, 21),
       (6, 22),
       (6, 23),
       (6, 24),
       (7, 25),
       (7, 26),
       (8, 27),
       (8, 28),
       (9, 29),
       (9, 30),
       (10, 31),
       (10, 32),
       (11, 33),
       (11, 34),
       (12, 35),
       (12, 36);


INSERT INTO requests(date, offer_id, client_name, email, phone,
                     message, notes, status)
VALUES (NOW(), 2, 'Ivanka', 'ivanka@mail.bg', '0899002233',
        'Please send me details fo inspection', 'Answered on 24.03.2022', 'INSPECTION'),
       (NOW(), 2, 'Martin', 'martin@mail.bg', '0899004433',
        'I am interested', 'To call', 'NEW'),
       (NOW(), 2, 'Ivanka', 'ivanka@mail.bg', '0899006633',
        'Please send me details fo inspection', '', 'NEW'),
       (NOW(), 1, 'Selina', 'selina@mail.bg', '0899007733',
        'Hello', 'Not interested', 'REJECTED'),
       (NOW(), 5, 'Petya', 'petya@mail.bg', '089908833',
        'Please send me details fo inspection', 'Answered on 24.03.2022', 'INSPECTION')



