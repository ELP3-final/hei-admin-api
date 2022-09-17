do
$$
    begin
        if not exists(select from pg_type where typname = 'event_event_role') then
            create type "event_event_role" as enum ('SUPERVISOR', 'ATTENDANT');
        end if;
        if not exists(select from pg_type where typname = 'event_attendance') then
            create type "event_attendance" as enum ('EXPECTED', 'HERE','MISSING');
        end if;
    end
$$;

create table if not exists "event_participant"
(
    id                varchar
            constraint event_participant_pk primary key default uuid_generate_v4(),
        user_id           varchar                  not null
            constraint event_participant_user_fk references "user"(id),
        event_id           varchar                  not null
                    constraint event_participant_event_fk references "event"(id),
        attendance             event_attendance                 not null,
        event_role             event_event_role                 not null
);
