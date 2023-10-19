INSERT INTO manufacturer (id, name)
VALUES
    (1, 'Electro-Harmonix'),
    (2, 'Strymon'),
    (3, 'Catalinbread'),
    (4, 'Malekko');


INSERT INTO guitar_pedal (id, manufacturer_id, name, has_stereo_output, date_purchased, date_sold, used_value)
VALUES
    (1, 1, 'Big Muff Fuzz', false, '2020-10-31', null, 75),
    (2, 2, 'Deco: Tape Saturation and Double Tracker', true, '2021-07-19', null, 250),
    (3, 3, 'Soft Focus Reverb', false, '2022-09-11', null, 200),
    (4, 4, 'Sneak Attack: Attack/Decay and Tremolo', false, '2023-01-01', '2023-03-21', 150);

INSERT INTO guitar_pedal_tag (id, guitar_pedal_id, tag)
VALUES
    (100, 1, 'fuzz'),
    (101, 1, 'shoegaze'),
    (200, 2, 'tape'),
    (201, 2, 'saturation'),
    (202, 2, 'chorus'),
    (203, 2, 'flanger'),
    (204, 2, 'delay'),
    (205, 2, 'double-tracking'),
    (300, 3, 'reverb'),
    (301, 3, 'lo-fi'),
    (302, 3, '90s'),
    (303, 3, 'shoegaze'),
    (400, 4, 'attack/decay'),
    (401, 4, 'swells'),
    (402, 4, 'tremolo');