create extension if not exists "uuid-ossp";

create table if not exists "place"
(
    id                varchar
        constraint place_pk primary key default uuid_generate_v4(),
    label               varchar                  not null
        constraint place_label_unique unique
);
create index if not exists place_label_index on "place" (label);
