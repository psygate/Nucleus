-- SCRIPT INFORMATION --
-- Version: 12
-- Upgrades: 0 3  45 192 1
-- SCRIPT INFORMATION --

START TRANSACTION;

DROP TABLE IF EXISTS nucleus_plugin_versions;
DROP TABLE IF EXISTS nucleus_user_log;
DROP TABLE IF EXISTS nucleus_usernames;


CREATE TABLE nucleus_plugin_versions (
    plugin_name                 VARCHAR(64)     NOT NULL,
    plugin_version              INTEGER         NOT NULL,
    PRIMARY KEY(plugin_name)
);

CREATE TABLE nucleus_usernames (
	username								VARCHAR(32)		NOT NULL,
	puuid									BINARY(16)		NOT NULL,
	PRIMARY KEY(puuid)
);

CREATE TABLE nucleus_user_log (
	username								VARCHAR(32)		NOT NULL,
	puuid									BINARY(16)		NOT NULL,
	ip										BINARY(4)		NOT NULL,
	seen									TIMESTAMP		NOT NULL						DEFAULT CURRENT_TIMESTAMP,

	PRIMARY KEY(puuid),
	FOREIGN KEY(puuid)				REFERENCES nucleus_usernames(puuid)
);

COMMIT;
