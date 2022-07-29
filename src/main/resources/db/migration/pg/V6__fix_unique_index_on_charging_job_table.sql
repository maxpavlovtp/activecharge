DROP INDEX charging_job_in_progress_idx;
CREATE UNIQUE INDEX charging_job_in_progress_idx
    ON charging_job (state, station_id)
    WHERE (state = 'IN_PROGRESS');