delete from station;
delete from charging_job;

insert into station (name, number, provider_device_id) values ('test1', '1', 'device1');
insert into station (name, number, provider_device_id) values ('test2', '2', 'device2');
insert into station (name, number, provider_device_id) values ('test3', '3', 'device3');

insert into charging_job(state, power_wt, charged_kwh, station_id, period_sec)
values ('IN_PROGRESS', 1, 2, (select id from station where station.number = '1'), 10);
insert into charging_job(state, power_wt, charged_kwh, station_id, period_sec)
values ('DONE', 3, 4, (select id from station where station.number = '1'), 10);

insert into charging_job(state, power_wt, charged_kwh, station_id, period_sec)
values ('IN_PROGRESS', 5, 6, (select id from station where station.number = '2'), 10);

insert into charging_job(state, power_wt, charged_kwh, station_id, period_sec)
values ('IN_PROGRESS', 7, 8, (select id from station where station.number = '3'), 10);
insert into charging_job(state, power_wt, charged_kwh, station_id, period_sec)
values ('CANCELED', 9, 10, (select id from station where station.number = '3'), 10);

