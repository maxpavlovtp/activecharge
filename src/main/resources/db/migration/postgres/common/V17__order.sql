ALTER TABLE station
	ADD COLUMN cost_per_hour varchar(255) NOT NULL DEFAULT 7;

CREATE TYPE ORDER_STATE AS ENUM('CREATED', 'IN_PROGRESS', 'DONE', 'CANCELED', 'FAILED');
CREATE CAST (VARCHAR AS ORDER_STATE) WITH INOUT AS IMPLICIT;

CREATE TABLE order_220
(
	id         uuid              NOT NULL DEFAULT gen_random_uuid(),
	station_number varchar(255)  NOT NULL,
	invoice_id varchar(255)      NOT NULL,
	state      order_state       NOT NULL DEFAULT 'CREATED'::order_state,
	period_sec integer           NOT NULL,
	created_on timestamptz       NOT NULL DEFAULT now(),
	updated_on timestamptz       NOT NULL DEFAULT now(),

	CONSTRAINT order_pkey PRIMARY KEY (id)
);

-- Table Triggers

create trigger update_order_updated_on
	before
		update
	on
		order_220
	for each row execute function update_updated_on();

-- index for search
CREATE INDEX idx_invoice_id
	ON order_220 (invoice_id);
