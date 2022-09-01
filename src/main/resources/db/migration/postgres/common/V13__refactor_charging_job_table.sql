ALTER TABLE charging_job DROP COLUMN charged_wt_ws;
ALTER TABLE charging_job RENAME COLUMN charging_wt to power_wt;
ALTER TABLE charging_job RENAME COLUMN charged_wt to charged_wt_h;
