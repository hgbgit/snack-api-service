CREATE TYPE "order_status" AS ENUM (
    'CREATING',
    'CREATED',
    'CANCELLED',
    'PREPARING',
    'DELIVERYING',
    'DELIVERED'
    );

CREATE TABLE "ingredient" (
                              "id" uuid PRIMARY KEY,
                              "name" varchar(30) NOT NULL,
                              "value" decimal(10,2) NOT NULL
);

CREATE TABLE "snack" (
                         "id" uuid PRIMARY KEY,
                         "name" varchar(30) NOT NULL
);

CREATE TABLE "snack_ingredient" (
                                    "ingredient_id" uuid NOT NULL,
                                    "snack_id" uuid NOT NULL,
                                    "amount" integer NOT NULL
);

CREATE TABLE "address" (
                           "id" uuid PRIMARY KEY,
                           "street" varchar(100) NOT NULL,
                           "number" bigint NOT NULL,
                           "complement" varchar,
                           "zipcode" varchar NOT NULL,
                           "city" varchar NOT NULL,
                           "estate" varchar
);

CREATE TABLE "phone" (
                         "id" uuid PRIMARY KEY,
                         "country_code" varchar NOT NULL,
                         "area_code" int NOT NULL,
                         "number" bigint NOT NULL
);

CREATE TABLE "customer" (
                            "id" uuid PRIMARY KEY,
                            "first_name" varchar NOT NULL,
                            "last_name" varchar NOT NULL,
                            "email" varchar NOT NULL,
                            "birth_date" timestamp with time zone,
                            "created_at" timestamp with time zone NOT NULL,
                            "updated_at" timestamp with time zone NOT NULL
);

CREATE TABLE "customer_address" (
                                    "customer_id" uuid NOT NULL,
                                    "address_id" uuid NOT NULL,
                                    "default" boolean NOT NULL DEFAULT 'false'
);

CREATE TABLE "customer_phone" (
                                  "customer_id" uuid NOT NULL,
                                  "phone_id" uuid NOT NULL,
                                  "default" boolean NOT NULL DEFAULT 'false'
);

CREATE TABLE "order_snack" (
                               "id" uuid PRIMARY KEY,
                               "order_id" uuid NOT NULL,
                               "snack_id" uuid NOT NULL
);

CREATE TABLE "order_snack_extra_ingredient" (
                                                "order_snack_id" uuid NOT NULL,
                                                "ingredient_id" uuid NOT NULL,
                                                "amount" integer NOT NULL
);

CREATE TABLE "order" (
                         "id" uuid PRIMARY KEY,
                         "customer_id" uuid NOT NULL,
                         "address_id" uuid NOT NULL,
                         "total_paid" decimal(10,2) NOT NULL,
                         "status" order_status NOT NULL,
                         "created_at" timestamp with time zone NOT NULL,
                         "updated_at" timestamp with time zone NOT NULL
);

ALTER TABLE "snack_ingredient" ADD FOREIGN KEY ("ingredient_id") REFERENCES "ingredient" ("id");

ALTER TABLE "snack_ingredient" ADD FOREIGN KEY ("snack_id") REFERENCES "snack" ("id");

ALTER TABLE "customer_address" ADD FOREIGN KEY ("customer_id") REFERENCES "customer" ("id");

ALTER TABLE "customer_address" ADD FOREIGN KEY ("address_id") REFERENCES "address" ("id");

ALTER TABLE "customer_phone" ADD FOREIGN KEY ("customer_id") REFERENCES "customer" ("id");

ALTER TABLE "customer_phone" ADD FOREIGN KEY ("phone_id") REFERENCES "phone" ("id");

ALTER TABLE "order_snack" ADD FOREIGN KEY ("order_id") REFERENCES "order" ("id");

ALTER TABLE "order_snack" ADD FOREIGN KEY ("snack_id") REFERENCES "snack" ("id");

ALTER TABLE "order_snack_extra_ingredient" ADD FOREIGN KEY ("order_snack_id") REFERENCES "order_snack" ("id");

ALTER TABLE "order_snack_extra_ingredient" ADD FOREIGN KEY ("ingredient_id") REFERENCES "ingredient" ("id");

ALTER TABLE "order" ADD FOREIGN KEY ("customer_id") REFERENCES "customer" ("id");

ALTER TABLE "order" ADD FOREIGN KEY ("address_id") REFERENCES "address" ("id");

CREATE UNIQUE INDEX ON "snack_ingredient" ("ingredient_id", "snack_id");

CREATE UNIQUE INDEX ON "customer_address" ("customer_id", "address_id");

CREATE UNIQUE INDEX ON "customer_phone" ("customer_id", "phone_id");

CREATE UNIQUE INDEX ON "order_snack_extra_ingredient" ("order_snack_id", "ingredient_id");

COMMENT ON COLUMN "ingredient"."value" IS 'In BRL';


-- Initial data for snack_api_service

/*
Alface: R$ 0.40
Bacon: R$ 2,00
Hambúrguer: R$ 3,00
Ovo: R$ 0,80
Queijo: R$ 1,50
*/
insert into ingredient (id, name, value) values ('f960c601-d460-4bd2-be5b-93b114d9118a', 'Alface', 0.40) ON CONFLICT DO NOTHING;
insert into ingredient (id, name, value) values ('22a5c95c-183b-4021-8774-4f7a2954cff6', 'Bacon', 2.0) ON CONFLICT DO NOTHING;
insert into ingredient (id, name, value) values ('d19a9e2f-a032-481f-af39-ab8b0be82737', 'Hamburger', 3.0) ON CONFLICT DO NOTHING;
insert into ingredient (id, name, value) values ('4d79d3eb-ae97-40cc-8bd8-6ab3c2836bc7', 'Ovo', 0.80) ON CONFLICT DO NOTHING;
insert into ingredient (id, name, value) values ('4458b195-a7bf-4b7c-8ffa-bd28ac7ff403', 'Queijo', 1.5) ON CONFLICT DO NOTHING;

/*
X-Bacon: Bacon, hambúrguer de carne e queijo
X-Burger: Hambúrguer de carne e queijo
X-Egg: Ovo, hambúrguer de carne e queijo
X-Egg Bacon: Ovo, bacon, hambúrguer de carne e queijo
*/
insert into snack (id, name) values ('20d19704-07a8-483c-91f2-2567e52c9dd4','X-Bacon') ON CONFLICT DO NOTHING;
insert into snack (id, name) values ('f003e923-fa39-4356-b128-92a31024ab90','X-Burger') ON CONFLICT DO NOTHING;
insert into snack (id, name) values ('02cf5439-5ded-4a55-a7bb-4b391ec975dd','X-Egg') ON CONFLICT DO NOTHING;
insert into snack (id, name) values ('9e7d96d6-4060-460b-bdbd-ad74696a8a05','X-Egg Bacon') ON CONFLICT DO NOTHING;

-- X-Bacon composition
insert into snack_ingredient(ingredient_id, snack_id, amount) values ('22a5c95c-183b-4021-8774-4f7a2954cff6','20d19704-07a8-483c-91f2-2567e52c9dd4',1) ON CONFLICT DO NOTHING;
insert into snack_ingredient(ingredient_id, snack_id, amount) values ('d19a9e2f-a032-481f-af39-ab8b0be82737','20d19704-07a8-483c-91f2-2567e52c9dd4',1) ON CONFLICT DO NOTHING;
insert into snack_ingredient(ingredient_id, snack_id, amount) values ('4458b195-a7bf-4b7c-8ffa-bd28ac7ff403','20d19704-07a8-483c-91f2-2567e52c9dd4',1) ON CONFLICT DO NOTHING;

-- X-Burger composition
insert into snack_ingredient(ingredient_id, snack_id, amount) values ('d19a9e2f-a032-481f-af39-ab8b0be82737','f003e923-fa39-4356-b128-92a31024ab90',1) ON CONFLICT DO NOTHING;
insert into snack_ingredient(ingredient_id, snack_id, amount) values ('4458b195-a7bf-4b7c-8ffa-bd28ac7ff403','f003e923-fa39-4356-b128-92a31024ab90',1) ON CONFLICT DO NOTHING;

-- X-Egg composition
insert into snack_ingredient(ingredient_id, snack_id, amount) values ('4d79d3eb-ae97-40cc-8bd8-6ab3c2836bc7','02cf5439-5ded-4a55-a7bb-4b391ec975dd',1) ON CONFLICT DO NOTHING;
insert into snack_ingredient(ingredient_id, snack_id, amount) values ('4458b195-a7bf-4b7c-8ffa-bd28ac7ff403','02cf5439-5ded-4a55-a7bb-4b391ec975dd',1) ON CONFLICT DO NOTHING;
insert into snack_ingredient(ingredient_id, snack_id, amount) values ('d19a9e2f-a032-481f-af39-ab8b0be82737','02cf5439-5ded-4a55-a7bb-4b391ec975dd',1) ON CONFLICT DO NOTHING;

-- X-Egg bacon composition
insert into snack_ingredient(ingredient_id, snack_id, amount) values ('22a5c95c-183b-4021-8774-4f7a2954cff6','9e7d96d6-4060-460b-bdbd-ad74696a8a05',1) ON CONFLICT DO NOTHING;
insert into snack_ingredient(ingredient_id, snack_id, amount) values ('d19a9e2f-a032-481f-af39-ab8b0be82737','9e7d96d6-4060-460b-bdbd-ad74696a8a05',1) ON CONFLICT DO NOTHING;
insert into snack_ingredient(ingredient_id, snack_id, amount) values ('4d79d3eb-ae97-40cc-8bd8-6ab3c2836bc7','9e7d96d6-4060-460b-bdbd-ad74696a8a05',1) ON CONFLICT DO NOTHING;
insert into snack_ingredient(ingredient_id, snack_id, amount) values ('4458b195-a7bf-4b7c-8ffa-bd28ac7ff403','9e7d96d6-4060-460b-bdbd-ad74696a8a05',1) ON CONFLICT DO NOTHING;

