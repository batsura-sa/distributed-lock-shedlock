create table shedlock
(
    name       varchar(70),
    lock_until timestamp with time zone null,
    locked_at  timestamp with time zone null,
    locked_by  varchar(100),
    primary key (name)
);