create table jwts
(
    id              varchar(255) not null,
    creation_date   timestamp(6),
    deletion_date   timestamp(6),
    update_date     timestamp(6),
    expiration_time timestamp(6),
    status          varchar(255),
    token           varchar(255),
    user_id         varchar(255),
    primary key (id)
);

create table roles
(
    id            varchar(255) not null,
    creation_date timestamp(6),
    deletion_date timestamp(6),
    update_date   timestamp(6),
    role_name     varchar(255),
    primary key (id)
);

create table users
(
    id            varchar(255) not null,
    creation_date timestamp(6),
    deletion_date timestamp(6),
    update_date   timestamp(6),
    email         varchar(255),
    enabled       boolean,
    first_name    varchar(255),
    last_name     varchar(255),
    password      varchar(255),
    phone_number  varchar(255),
    state         json,
    username      varchar(255),
    primary key (id)
);
create table users_roles
(
    user_id varchar(255) not null,
    role_id varchar(255) not null
);

alter table users add constraint uniqueConstraint unique (username, email);
alter table users add constraint uniqueConstraintUsername unique (username);
alter table users add constraint uniqueConstraintEmail unique (email);

alter table users_roles add constraint foreignConstraint foreign key (role_id) references roles(id);
alter table users_roles add constraint foreignConstraint foreign key (user_id) references users(id);
