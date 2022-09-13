do
$$
    begin
        if not exists(select from pg_type where typname = 'event_type') then
            create type "event_type" as enum ('COURSE', 'EXAMEN','OTHER');
        end if;
    end

    begin
        if not exists(select from pg_type where typname = 'Supervisor_type') then
            create type "supervisor_type" as enum ('TEACHER', 'ADMINISTRATOR');
        end if;
    end

    begin
        if not exists(select from pg_type where typname = 'Status_type') then
            create type "status_type" as enum ('ONGOING', 'CANCELED', 'FINISHED');
        end if;
    end
$$;

create table if not exists "event"
(
    id                varchar
        constraint fee_pk primary key default uuid_generate_v4(),
    name                varchar                 not null,
    type              event_type                not null,
    date      timestamp with time zone not null,
    start_time         varchar                  not null,
    finish_time        varchar                  not null,
    supervisor      supervisor_type             not null,
    status          status_type                 not null,
    place_id           varchar                  not null
            constraint event_place_id_fk references "place"(id),
    group_id           varchar                  not null
            constraint event_group_id_fk references "group"(id)


);
create index if not exists event_group_id_index on "group" (group_id);
create index if not exists event_place_id_index on "place" (place_id);