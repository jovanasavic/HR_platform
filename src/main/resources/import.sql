insert into candidate (id, full_name, date_of_birth, email, contact_number) values (nextval('candidate_seq'), 'ana', to_date('01/01/1996','dd/mm/yyyyy'), 'ana@gmail.com', '064521875');
insert into candidate (id, full_name, date_of_birth, email, contact_number) values (nextval('candidate_seq'), 'jovana', to_date('01/02/1996','dd/mm/yyyyy'), 'jovana@gmail.com', '064521875');
insert into candidate (id, full_name, date_of_birth, email, contact_number) values (nextval('candidate_seq'), 'anabela', to_date('01/02/1996','dd/mm/yyyyy'), 'anabela@gmail.com', '064521875');

insert into skill (id, name) values (nextval('skill_seq'), 'java programming');
insert into skill (id, name) values (nextval('skill_seq'), 'c');
insert into skill (id, name) values (nextval('skill_seq'), 'sql');

insert into candidate_skill values(1,1);
insert into candidate_skill values(1,2);
insert into candidate_skill values(2,1);
insert into candidate_skill values(2,2);
insert into candidate_skill values(3,1);
insert into candidate_skill values(3,2);
insert into candidate_skill values(3,3);

