CREATE  FUNCTION update_updated_on()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_on = now();
RETURN NEW;
END;
$$ language 'plpgsql';

create table station
(
    id                      uuid                not null default gen_random_uuid() primary key,
    name                    varchar(255)        not null unique,
    number                  varchar(255)        not null unique,
    provider_device_id      varchar(255)        not null unique,
    created_on              timestamptz         not null default now(),
    updated_on              timestamptz         not null default now()
);

CREATE TRIGGER update_station_updated_on
BEFORE UPDATE
ON
    station
FOR EACH ROW
EXECUTE PROCEDURE update_updated_on();

--- insert data---
insert into station (name, number, provider_device_id) values ('stage', '2', '10013bb124');