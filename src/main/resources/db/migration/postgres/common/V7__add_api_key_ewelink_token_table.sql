UPDATE ewelink_token SET token = null;

ALTER TABLE ewelink_token ADD COLUMN api_key VARCHAR(128) DEFAULT NULL;