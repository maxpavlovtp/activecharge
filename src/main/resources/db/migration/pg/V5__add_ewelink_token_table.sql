create table ewelink_token
(
    id              uuid                        not null default gen_random_uuid() primary key,
    token           varchar(128)                default null,
    created_on      timestamptz                 not null default now(),
    updated_on      timestamptz                 not null default now()
);

CREATE TRIGGER update_ewelink_token_updated_on
    BEFORE UPDATE
    ON
        ewelink_token
    FOR EACH ROW
EXECUTE PROCEDURE update_updated_on();

INSERT INTO ewelink_token DEFAULT VALUES returning id;