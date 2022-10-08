ALTER TABLE station ADD COLUMN cost_per_hour varchar(255) NOT NULL DEFAULT 7;

CREATE TYPE order_state AS ENUM (
	'IN_PROGRESS',
	'DONE');
CREATE TABLE order_220 (
                           id uuid NOT NULL DEFAULT gen_random_uuid(),
                           station_id uuid NOT NULL,
                           invoice_id varchar(255) not null,
                           state order_state NOT NULL DEFAULT 'IN_PROGRESS'::order_state,
                           created_on timestamptz NOT NULL DEFAULT now(),
                           updated_on timestamptz NOT NULL DEFAULT now(),
                           CONSTRAINT order_pkey PRIMARY KEY (id)
);

-- Table Triggers

create trigger update_order_updated_on before
    update
    on
    order_220 for each row execute function update_updated_on();

-- order foreign keys
ALTER TABLE order_220 ADD CONSTRAINT fk_station FOREIGN KEY (station_id) REFERENCES station(id);
-- index for search
CREATE INDEX idx_invoice_id
    ON order_220(invoice_id);
