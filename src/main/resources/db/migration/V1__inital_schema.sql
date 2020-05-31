CREATE TABLE "ingredient"
(
    "id"    uuid PRIMARY KEY,
    "name"  varchar(30)    NOT NULL,
    "value" decimal(10, 2) NOT NULL
);

CREATE TABLE "snack"
(
    "id"   uuid PRIMARY KEY,
    "name" varchar(30) NOT NULL
);

CREATE TABLE "snack_ingredient"
(
    "ingredient_id" uuid    NOT NULL,
    "snack_id"      uuid    NOT NULL,
    "amount"        integer NOT NULL
);

CREATE TABLE "address"
(
    "id"         uuid PRIMARY KEY,
    "street"     varchar(100) NOT NULL,
    "number"     bigint       NOT NULL,
    "complement" varchar,
    "zip_code"   varchar      NOT NULL,
    "city"       varchar      NOT NULL,
    "estate"     varchar
);

CREATE TABLE "phone"
(
    "id"           uuid PRIMARY KEY,
    "country_code" varchar NOT NULL,
    "area_code"    int     NOT NULL,
    "number"       bigint  NOT NULL
);

CREATE TABLE "customer"
(
    "id"         uuid PRIMARY KEY,
    "first_name" varchar                  NOT NULL,
    "last_name"  varchar                  NOT NULL,
    "email"      varchar                  NOT NULL,
    "birth_date" timestamp with time zone,
    "created_at" timestamp with time zone NOT NULL,
    "updated_at" timestamp with time zone NOT NULL
);

CREATE TABLE "customer_address"
(
    "customer_id" uuid    NOT NULL,
    "address_id"  uuid    NOT NULL,
    "is_default"  boolean NOT NULL DEFAULT 'false'
);

CREATE TABLE "customer_order"
(
    "id"          uuid PRIMARY KEY,
    "customer_id" uuid                     NOT NULL,
    "address_id"  uuid                     NOT NULL,
    "total_paid"  decimal(10, 2)           NOT NULL,
    "status"      varchar                  NOT NULL,
    "created_at"  timestamp with time zone NOT NULL,
    "updated_at"  timestamp with time zone NOT NULL
);

CREATE TABLE "customer_phone"
(
    "customer_id" uuid    NOT NULL,
    "phone_id"    uuid    NOT NULL,
    "is_default"  boolean NOT NULL DEFAULT 'false'
);

CREATE TABLE "order_snack"
(
    "id"       uuid PRIMARY KEY,
    "order_id" uuid           NOT NULL,
    "snack_id" uuid           NOT NULL,
    "amount"   integer        NOT NULL,
    "value"    decimal(10, 2) not null
);

CREATE TABLE "order_snack_extra_ingredient"
(
    "order_snack_id" uuid    NOT NULL,
    "ingredient_id"  uuid    NOT NULL,
    "amount"         integer NOT NULL
);

CREATE TABLE "order_applied_strategy"
(
    "order_id"       uuid           NOT NULL,
    "discount_value" decimal(10, 2) NOT NULL,
    "description"    varchar        NOT NULL
);

ALTER TABLE "snack_ingredient"
    ADD FOREIGN KEY ("ingredient_id") REFERENCES "ingredient" ("id");

ALTER TABLE "snack_ingredient"
    ADD FOREIGN KEY ("snack_id") REFERENCES "snack" ("id");

ALTER TABLE "customer_address"
    ADD FOREIGN KEY ("customer_id") REFERENCES "customer" ("id");

ALTER TABLE "customer_address"
    ADD FOREIGN KEY ("address_id") REFERENCES "address" ("id");

ALTER TABLE "customer_order"
    ADD FOREIGN KEY ("customer_id") REFERENCES "customer" ("id");

ALTER TABLE "customer_order"
    ADD FOREIGN KEY ("address_id") REFERENCES "address" ("id");

ALTER TABLE "customer_phone"
    ADD FOREIGN KEY ("customer_id") REFERENCES "customer" ("id");

ALTER TABLE "customer_phone"
    ADD FOREIGN KEY ("phone_id") REFERENCES "phone" ("id");

ALTER TABLE "order_snack"
    ADD FOREIGN KEY ("order_id") REFERENCES "customer_order" ("id");

ALTER TABLE "order_snack"
    ADD FOREIGN KEY ("snack_id") REFERENCES "snack" ("id");

ALTER TABLE "order_snack_extra_ingredient"
    ADD FOREIGN KEY ("order_snack_id") REFERENCES "order_snack" ("id");

ALTER TABLE "order_snack_extra_ingredient"
    ADD FOREIGN KEY ("ingredient_id") REFERENCES "ingredient" ("id");

ALTER TABLE "order_applied_strategy"
    ADD FOREIGN KEY ("order_id") REFERENCES "customer_order" ("id");

CREATE UNIQUE INDEX ON "snack_ingredient" ("ingredient_id", "snack_id");

CREATE UNIQUE INDEX ON "customer_address" ("customer_id", "address_id");

CREATE UNIQUE INDEX ON "customer_phone" ("customer_id", "phone_id");

CREATE UNIQUE INDEX ON "order_snack_extra_ingredient" ("order_snack_id", "ingredient_id");

CREATE UNIQUE INDEX ON "order_applied_strategy" ("order_id", "description");

COMMENT ON COLUMN "ingredient"."value" IS 'In BRL';



-- Initial data for snack_api_service

/*
Alface: R$ 0.40
Bacon: R$ 2,00
Hambúrguer: R$ 3,00
Ovo: R$ 0,80
Queijo: R$ 1,50
*/
insert into ingredient (id, name, value)
values ('f960c601-d460-4bd2-be5b-93b114d9118a', 'Alface', 0.40);
insert into ingredient (id, name, value)
values ('22a5c95c-183b-4021-8774-4f7a2954cff6', 'Bacon', 2.0);
insert into ingredient (id, name, value)
values ('d19a9e2f-a032-481f-af39-ab8b0be82737', 'Hamburger', 3.0);
insert into ingredient (id, name, value)
values ('4d79d3eb-ae97-40cc-8bd8-6ab3c2836bc7', 'Ovo', 0.80);
insert into ingredient (id, name, value)
values ('4458b195-a7bf-4b7c-8ffa-bd28ac7ff403', 'Queijo', 1.5);

/*
X-Bacon: Bacon, hambúrguer de carne e queijo
X-Burger: Hambúrguer de carne e queijo
X-Egg: Ovo, hambúrguer de carne e queijo
X-Egg Bacon: Ovo, bacon, hambúrguer de carne e queijo
*/
insert into snack (id, name)
values ('20d19704-07a8-483c-91f2-2567e52c9dd4', 'X-Bacon');
insert into snack (id, name)
values ('f003e923-fa39-4356-b128-92a31024ab90', 'X-Burger');
insert into snack (id, name)
values ('02cf5439-5ded-4a55-a7bb-4b391ec975dd', 'X-Egg');
insert into snack (id, name)
values ('9e7d96d6-4060-460b-bdbd-ad74696a8a05', 'X-Egg Bacon');
insert into snack (id, name)
values ('5338b61c-16c2-4ec1-af8f-72a3333443eb', 'Lanche Personalizado');

-- X-Bacon composition
insert into snack_ingredient(ingredient_id, snack_id, amount)
values ('22a5c95c-183b-4021-8774-4f7a2954cff6', '20d19704-07a8-483c-91f2-2567e52c9dd4', 1);
insert into snack_ingredient(ingredient_id, snack_id, amount)
values ('d19a9e2f-a032-481f-af39-ab8b0be82737', '20d19704-07a8-483c-91f2-2567e52c9dd4', 1);
insert into snack_ingredient(ingredient_id, snack_id, amount)
values ('4458b195-a7bf-4b7c-8ffa-bd28ac7ff403', '20d19704-07a8-483c-91f2-2567e52c9dd4', 1);

-- X-Burger composition
insert into snack_ingredient(ingredient_id, snack_id, amount)
values ('d19a9e2f-a032-481f-af39-ab8b0be82737', 'f003e923-fa39-4356-b128-92a31024ab90', 1);
insert into snack_ingredient(ingredient_id, snack_id, amount)
values ('4458b195-a7bf-4b7c-8ffa-bd28ac7ff403', 'f003e923-fa39-4356-b128-92a31024ab90', 1);

-- X-Egg composition
insert into snack_ingredient(ingredient_id, snack_id, amount)
values ('4d79d3eb-ae97-40cc-8bd8-6ab3c2836bc7', '02cf5439-5ded-4a55-a7bb-4b391ec975dd', 1);
insert into snack_ingredient(ingredient_id, snack_id, amount)
values ('4458b195-a7bf-4b7c-8ffa-bd28ac7ff403', '02cf5439-5ded-4a55-a7bb-4b391ec975dd', 1);
insert into snack_ingredient(ingredient_id, snack_id, amount)
values ('d19a9e2f-a032-481f-af39-ab8b0be82737', '02cf5439-5ded-4a55-a7bb-4b391ec975dd', 1);

-- X-Egg bacon composition
insert into snack_ingredient(ingredient_id, snack_id, amount)
values ('22a5c95c-183b-4021-8774-4f7a2954cff6', '9e7d96d6-4060-460b-bdbd-ad74696a8a05', 1);
insert into snack_ingredient(ingredient_id, snack_id, amount)
values ('d19a9e2f-a032-481f-af39-ab8b0be82737', '9e7d96d6-4060-460b-bdbd-ad74696a8a05', 1);
insert into snack_ingredient(ingredient_id, snack_id, amount)
values ('4d79d3eb-ae97-40cc-8bd8-6ab3c2836bc7', '9e7d96d6-4060-460b-bdbd-ad74696a8a05', 1);
insert into snack_ingredient(ingredient_id, snack_id, amount)
values ('4458b195-a7bf-4b7c-8ffa-bd28ac7ff403', '9e7d96d6-4060-460b-bdbd-ad74696a8a05', 1);

-- Some initial customers
insert into customer
values ('4df5b9a5-52a4-47ab-a9b3-0874aa0b29bb', 'Hugo', 'Barros', 'email@hugobarros.com.br', '1988-02-08', now(), now());
insert into customer
values ('8d45e80f-fa4f-41a0-b16c-61a3b0df0ff6', 'Maria', 'Silva', 'maria@silva.com.br', '1990-02-10', now(), now());
insert into customer
values ('d0463664-bba6-46d4-8b5a-14bf748766a5', 'Renata', 'Christina', 'renata@gmail.com.br', '2001-01-08', now(), now());

-- Some initial customer phones
insert into phone
values ('e7762426-a5ab-4941-89b4-0460e122b1f4', '+55', 11, 999858657);
insert into phone
values ('76649759-b5e8-462c-9134-d266ca26b139', '+55', 84, 998505894);
insert into phone
values ('a6dc7d4b-5f9e-4b43-a1fa-a4fc46040303', '+55', 11, 987324323);
insert into phone
values ('15ca873f-8be7-4352-a197-0dc8dfdd37ca', '+55', 11, 982823432);
insert into phone
values ('4277ca8b-0b03-4ff8-8f93-bd69426ad26b', '+55', 11, 998231092);

-- Some customers and their relationships with phones
insert into customer_phone
values ('4df5b9a5-52a4-47ab-a9b3-0874aa0b29bb', 'e7762426-a5ab-4941-89b4-0460e122b1f4', true);
insert into customer_phone
values ('4df5b9a5-52a4-47ab-a9b3-0874aa0b29bb', '76649759-b5e8-462c-9134-d266ca26b139', false);
insert into customer_phone
values ('8d45e80f-fa4f-41a0-b16c-61a3b0df0ff6', 'a6dc7d4b-5f9e-4b43-a1fa-a4fc46040303', true);
insert into customer_phone
values ('d0463664-bba6-46d4-8b5a-14bf748766a5', '15ca873f-8be7-4352-a197-0dc8dfdd37ca', true);
insert into customer_phone
values ('d0463664-bba6-46d4-8b5a-14bf748766a5', '4277ca8b-0b03-4ff8-8f93-bd69426ad26b', false);

-- Some initial addresses
insert into address
values ('63b2ff11-306d-4641-80f9-7e07fd53f81e', 'Rua Casa do Ator', 821, 'AP 2407', '04633-050', 'São Paulo', 'SP');
insert into address
values ('a6b4eb6a-18cb-4e45-9376-161f35cfb6e4', 'Avenida Brigadeiro Faria Lima', 1004, '11º Andar', '04321-213', 'São Paulo', 'SP');
insert into address
values ('56916202-8f8d-4771-af2f-b51864498c8a', 'Av. Juscelino Kubischek', 123, '', '04093-982', 'São Paulo', 'SP');
insert into address
values ('5e782c8b-153f-4d31-aa19-65ce222c545f', 'Rua Gomes de Carvalho', 1001, 'AP 3111', '04234-203', 'São Paulo', 'SP');

-- Some inital customer and their addresses relationships
insert into customer_address
values ('4df5b9a5-52a4-47ab-a9b3-0874aa0b29bb', '63b2ff11-306d-4641-80f9-7e07fd53f81e', true);
insert into customer_address
values ('4df5b9a5-52a4-47ab-a9b3-0874aa0b29bb', 'a6b4eb6a-18cb-4e45-9376-161f35cfb6e4', false);
insert into customer_address
values ('8d45e80f-fa4f-41a0-b16c-61a3b0df0ff6', '56916202-8f8d-4771-af2f-b51864498c8a', true);
insert into customer_address
values ('d0463664-bba6-46d4-8b5a-14bf748766a5', '5e782c8b-153f-4d31-aa19-65ce222c545f', true);

                                                                                            