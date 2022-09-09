create extension if not exists "uuid-ossp";

create table if not exists "course"
(
    id                varchar
        constraint group_pk primary key                 default uuid_generate_v4(),
    name              varchar                  not null,
    ref               varchar                  not null
        constraint group_ref_unique unique,
    credits  integer not null,

    total_hours integer not null
);
