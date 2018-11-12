REPLACE INTO `role` VALUES (1,'ROLE_ADMIN');
REPLACE INTO `role` VALUES (2,'ROLE_USER');

insert into user (active, email, last_name, name, password) values (1, "admin@mail.ru", "Dan", "T", "$2a$10$0fCA/eTQNch.3kLF03vK8u15tyR53Pj4kAM9JZFhwK4w7jGZFtK9W");
insert into user_role (user_id, role_id) values (1, 1);

insert into user (active, email, last_name, name, password) values (1, "user@mail.ru", "Dan", "T", "$2a$10$0fCA/eTQNch.3kLF03vK8u15tyR53Pj4kAM9JZFhwK4w7jGZFtK9W");
insert into user_role (user_id, role_id) values (2, 2);