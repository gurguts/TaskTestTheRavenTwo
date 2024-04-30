create table customers
(
    id        bigint auto_increment
        primary key,
    created   bigint       not null,
    updated   bigint,
    full_name varchar(50)  not null,
    email     varchar(100) not null,
    phone     varchar(14),
    is_active tinyint(1) not null
);

