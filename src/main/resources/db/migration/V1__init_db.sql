create table beverage
(
    id          uuid         not null primary key,
    description varchar(255) not null,
    name        varchar(255) not null
);

alter table beverage
    owner to postgres;

create table action
(
    id             uuid         not null primary key,
    order_index    integer default 1,
    procedure_type varchar(255) not null,
    beverage_id    uuid references beverage
);

alter table action
    owner to postgres;

create table action_argument
(
    id        uuid         not null primary key,
    name      varchar(255) not null,
    value     real         not null,
    action_id uuid references action
);

alter table action_argument
    owner to postgres;

create table beverage_order
(
    id          uuid         not null primary key,
    created     timestamp(6) not null,
    status      varchar(255) not null,
    beverage_id uuid references beverage
);

alter table beverage_order
    owner to postgres;

create table ingredient
(
    id               uuid         not null primary key,
    name             varchar(255) not null,
    capacity         real         not null,
    current_quantity real         not null
);

alter table ingredient
    owner to postgres;

create table action_ingredient
(
    id            uuid not null primary key,
    quantity      real not null,
    action_id     uuid references action,
    ingredient_id uuid references ingredient
);

alter table action_ingredient
    owner to postgres;