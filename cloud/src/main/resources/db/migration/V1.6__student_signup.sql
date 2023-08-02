create table if not exists Student_School_Info (
	id serial PRIMARY KEY,
	student_id integer NOT NULL,
	school_name VARCHAR(255),
	year_of_passing VARCHAR(255)
);

ALTER TABLE Student_School_Info ADD CONSTRAINT Student_School_Info_fk0 FOREIGN KEY (student_id) REFERENCES Person(id);
