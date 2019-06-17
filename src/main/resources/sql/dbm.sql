-- SCRIPT INFORMATION --
-- Types: mysql mariadb
-- Version: 1
-- Upgrades: 0
-- SCRIPT INFORMATION --

START TRANSACTION;
SET foreign_key_checks = 0;

DROP TABLE IF EXISTS nucleus_plugin_versions;
DROP TABLE IF EXISTS nucleus_user_log;
DROP TABLE IF EXISTS nucleus_usernames;
DROP TABLE IF EXISTS nucleus_ban_data;
DROP TABLE IF EXISTS nucleus_banned_ip;
DROP TABLE IF EXISTS nucleus_banned_uuid;
DROP TABLE IF EXISTS nucleus_protection_data;


CREATE TABLE nucleus_plugin_versions (
  plugin_name    VARCHAR(64) NOT NULL,
  plugin_version INTEGER     NOT NULL,
  PRIMARY KEY (plugin_name)
);

CREATE TABLE nucleus_usernames (
  username VARCHAR(32) NOT NULL,
  puuid    BINARY(16)  NOT NULL,
  PRIMARY KEY (puuid),
  UNIQUE (username)
);

CREATE TABLE nucleus_user_log (
  username VARCHAR(32)   NOT NULL,
  puuid    BINARY(16)    NOT NULL,
  ip       VARBINARY(16) NOT NULL,
  seen     TIMESTAMP     NOT NULL            DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE nucleus_protection_data (
  puuid                             BINARY(16) NOT NULL,
  multi_account_ban_protection_bool BOOLEAN    NOT NULL,
  rename_protection_bool            BOOLEAN    NOT NULL,
  imposter_check_protection_bool    BOOLEAN    NOT NULL,
  ip_range_ban_bool                 BOOLEAN    NOT NULL,
  PRIMARY KEY (puuid)
);

CREATE TABLE nucleus_ban_data (
  ban_id              BIGINT       NOT NULL                        AUTO_INCREMENT,
  ban_type            INTEGER      NOT NULL,
  banned_by_puuid     BINARY(16)   NOT NULL,
  ban_time            TIMESTAMP    NOT NULL                        DEFAULT CURRENT_TIMESTAMP,
  banned_until        TIMESTAMP    NOT NULL,
  banned_forever_bool BOOLEAN      NOT NULL,
  ban_reason          VARCHAR(255) NOT NULL,
  PRIMARY KEY (ban_id)
);

CREATE TABLE nucleus_banned_ip (
  ban_id  BIGINT        NOT NULL,
  ip      VARBINARY(16) NOT NULL,
  netmask VARBINARY(16) NOT NULL,
  FOREIGN KEY (ban_id) REFERENCES nucleus_ban_data (ban_id)
    ON DELETE CASCADE,
  UNIQUE (ip, netmask)
);

CREATE TABLE nucleus_banned_uuid (
  ban_id BIGINT NOT NULL,
  puuid  BINARY(16),
  FOREIGN KEY (ban_id) REFERENCES nucleus_ban_data (ban_id)
    ON DELETE CASCADE,
  UNIQUE (puuid)
);

CREATE INDEX username_log_idx ON nucleus_user_log (username);
CREATE INDEX ip_log_idx ON nucleus_user_log (ip);
CREATE INDEX puuid_log_idx ON nucleus_user_log (puuid);

INSERT INTO nucleus_usernames VALUES ('$INVALID$', 0x0);

SET foreign_key_checks = 1;
COMMIT;