do
$$
    begin
        if not exists(select from pg_type where typname = 'event_type') then
            create type "event_type" as enum ('COURSE', 'EXAMEN','OTHER');
        end if;
        if not exists(select from pg_type where typname = 'Status_type') then
            create type "status_type" as enum ('ONGOING', 'CANCELED', 'FINISHED');
        end if;
    end
$$;

create table if not exists "event"
(
    id                varchar
        constraint event_pk primary key default uuid_generate_v4(),
    name                varchar                 not null,
    type              event_type                not null,
    start_time      timestamp with time zone        not null,
    finish_time    timestamp with time zone         not null,
    status          status_type                 not null,
    place_id           varchar                  not null
            constraint event_place_id_fk references "place"(id)

);