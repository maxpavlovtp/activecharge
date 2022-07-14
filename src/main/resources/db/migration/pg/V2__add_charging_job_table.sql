create type charging_job_state AS ENUM('IN_PROGRESS', 'DONE', 'CANCELED', 'FAILED');
create cast (varchar as charging_job_state) with inout as implicit;

create table charging_job
(
    id              uuid                        not null default gen_random_uuid() primary key,
    number          serial                      not null unique,
    state           charging_job_state          not null default 'IN_PROGRESS',
    created_on      timestamptz                 not null default now(),
    updated_on      timestamptz                 not null default now(),
    station_id      uuid                        not null,
    charging_wt     numeric                     not null default 0,
    charged_wt      numeric                     not null default 0,
    reason          varchar(255)                default '',
    period_sec      integer                     not null,
    constraint      fk_station                  foreign key (station_id) REFERENCES station (id)
);

CREATE INDEX charging_job_in_progress_idx
    ON charging_job (state, station_id)
    WHERE (state = 'IN_PROGRESS');

CREATE TRIGGER update_charging_job_updated_on
    BEFORE UPDATE
    ON
        charging_job
    FOR EACH ROW
EXECUTE PROCEDURE update_updated_on();