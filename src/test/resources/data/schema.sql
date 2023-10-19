CREATE TABLE manufacturer (
    id BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(32) NOT NULL
);

CREATE TABLE guitar_pedal (
    id BIGINT NOT NULL PRIMARY KEY,
    manufacturer_id BIGINT NOT NULL REFERENCES manufacturer,
    name VARCHAR(64) NOT NULL,
    has_stereo_output BOOLEAN NOT NULL,
    date_purchased DATE NOT NULL,
    date_sold DATE,
    used_value INT NOT NULL
);

CREATE TABLE guitar_pedal_tag (
    id BIGINT NOT NULL PRIMARY KEY,
    guitar_pedal_id BIGINT NOT NULL REFERENCES guitar_pedal,
    tag VARCHAR (16) NOT NULL
);
