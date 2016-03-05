INSERT INTO ability (`id`, `created`, `updated`, `version`, `description`, `name`) VALUES (1, '2016-03-02', '2016-03-02', '0', 'Able to translate documents', 'Translate documents');
INSERT INTO ability (`id`, `created`, `updated`, `version`, `description`, `name`) VALUES (2, '2016-03-02', '2016-03-02', '0', 'Can have a look for people in need', 'Care for people');
INSERT INTO ability (`id`, `created`, `updated`, `version`, `description`, `name`) VALUES (3, '2016-03-02', '2016-03-02', '0', 'Fill and transport sandbags', 'Sandbag carrier');

INSERT INTO user (id, created, updated, version, city, latitude, longitude, street, street_number, zip_code, auth_provider, email, email_verified, external_id, given_name, phone, surname)
VALUES
  (1, NOW(), NOW(), 1, NULL, 49.0, 8.4, NULL, NULL, 76133, 'empty', 'test@helfenkannjeder.de', 0, NULL, 'Given Name', '01234567', 'Surname'),
  (2, NOW(), NOW(), 1, NULL, 49.0, 8.41, NULL, NULL, 76133, 'empty', 'test2@helfenkannjeder.de', 0, NULL, 'Given Name 2', '01234568', 'Surname 2');

INSERT INTO volunteer (id, created, updated, version, user_id) VALUES
  (1, NOW(), NOW(), 1, 1),
  (2, NOW(), NOW(), 1, 2);

INSERT INTO volunteer_abilities (volunteers, abilities) VALUES
  (1, 1),
  (1, 2),
  (2, 1);
