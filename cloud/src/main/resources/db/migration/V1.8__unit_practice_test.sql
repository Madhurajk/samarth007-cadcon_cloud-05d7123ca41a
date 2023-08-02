ALTER TABLE student_test_progress ADD COLUMN id SERIAL PRIMARY KEY;

alter table student_test_progress add column created_at bigint NOT NULL DEFAULT EXTRACT(EPOCH FROM NOW());

alter table student_test_progress add column time_taken bigint NOT NULL default 0;

CREATE TABLE if not exists student_practice_test_progress (
	id serial NOT NULL, 
	student integer NOT NULL,
	test integer NOT NULL,
	marks integer NOT NULL,
	max integer NOT NULL,
	created_at bigint NOT NULL,
	time_taken bigint NOT NULL,
	CONSTRAINT StudentPracticeTestProgress_pk PRIMARY KEY (id)

) WITH (
  OIDS=FALSE
);

ALTER TABLE IF EXISTS test RENAME TO unit_test;
