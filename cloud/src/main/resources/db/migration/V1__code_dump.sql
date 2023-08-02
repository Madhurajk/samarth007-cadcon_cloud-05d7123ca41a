CREATE TABLE Person (
	id serial NOT NULL,
	address integer NOT NULL,
	first_name TEXT NOT NULL,
	last_name TEXT,
	CONSTRAINT Person_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Address (
	id serial NOT NULL,
	pincode TEXT NOT NULL,
	state TEXT NOT NULL,
	district TEXT NOT NULL,
	taluk TEXT NOT NULL,
	post TEXT NOT NULL,
	address_line_1 TEXT,
	address_line_2 TEXT,
	CONSTRAINT Address_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Phone (
	number TEXT NOT NULL,
	code TEXT NOT NULL,
	isPrimary BOOLEAN NOT NULL DEFAULT 'false',
	id serial NOT NULL,
	CONSTRAINT Phone_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Language (
	id serial NOT NULL,
	name serial NOT NULL,
	CONSTRAINT Language_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Student_Profile (
	person integer NOT NULL,
	student_id serial NOT NULL UNIQUE,
	school integer NOT NULL,
	is_active BOOLEAN NOT NULL,
	language VARCHAR(255) NOT NULL,
	CONSTRAINT Student_Profile_pk PRIMARY KEY (student_id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Subject (
	id serial NOT NULL,
	name serial NOT NULL,
	description TEXT NOT NULL,
	language integer NOT NULL,
	CONSTRAINT Subject_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Plan (
	Name TEXT NOT NULL,
	id serial NOT NULL,
	description serial NOT NULL,
	validity integer NOT NULL,
	language VARCHAR(255) NOT NULL,
	CONSTRAINT Plan_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Subject_Capsule (
	id serial NOT NULL,
	name serial NOT NULL,
	subject integer NOT NULL,
	learn_root integer NOT NULL,
	CONSTRAINT Subject_Capsule_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE LearningNode (
	Name TEXT NOT NULL,
	id integer NOT NULL,
	identification_color VARCHAR(255) NOT NULL,
	description TEXT NOT NULL,
	identification_image TEXT NOT NULL,
	parent_node integer NOT NULL,
	semister_tags VARCHAR(255) NOT NULL,
	CONSTRAINT LearningNode_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE School (
	id serial NOT NULL,
	Name TEXT NOT NULL,
	code TEXT NOT NULL UNIQUE,
	Address integer NOT NULL,
	CONSTRAINT School_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE school_phone (
	phone integer NOT NULL,
	school integer NOT NULL
) WITH (
  OIDS=FALSE
);



CREATE TABLE person_phone (
	person integer NOT NULL,
	phone integer NOT NULL
) WITH (
  OIDS=FALSE
);



CREATE TABLE Plan_subjects (
	subject_capsule integer NOT NULL,
	plan integer NOT NULL,
	CONSTRAINT Plan_subjects_pk PRIMARY KEY (subject_capsule,plan)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Question (
	id serial NOT NULL,
	ques_text TEXT NOT NULL,
	ques_media integer NOT NULL,
	solution integer NOT NULL,
	CONSTRAINT Question_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE ques_option (
	question integer NOT NULL,
	option_serial integer NOT NULL,
	option_text TEXT NOT NULL,
	option_media integer NOT NULL,
	is_correct BOOLEAN NOT NULL,
	marks_associated integer NOT NULL,
	id serial NOT NULL,
	CONSTRAINT ques_option_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE media (
	id serial NOT NULL,
	link TEXT NOT NULL,
	meme_type TEXT NOT NULL,
	label VARCHAR(255) NOT NULL,
	description VARCHAR(255) NOT NULL,
	CONSTRAINT media_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Solution (
	id integer NOT NULL,
	solution_text TEXT NOT NULL,
	solution_media integer NOT NULL,
	CONSTRAINT Solution_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Practice_Questions (
	practice_test integer NOT NULL,
	question integer NOT NULL,
	CONSTRAINT Practice_Questions_pk PRIMARY KEY (practice_test,question)
) WITH (
  OIDS=FALSE
);



CREATE TABLE athentication (
	person serial NOT NULL,
	secret VARCHAR(255) NOT NULL,
	user_name VARCHAR(255) NOT NULL UNIQUE,
	running_token VARCHAR(255) NOT NULL,
	can_be_admin BOOLEAN NOT NULL DEFAULT 'false',
	CONSTRAINT athentication_pk PRIMARY KEY (person)
) WITH (
  OIDS=FALSE
);



CREATE TABLE person_meta (
	person integer NOT NULL,
	login_attempt TIME NOT NULL,
	loging_success BOOLEAN NOT NULL,
	login_mode integer NOT NULL,
	login_device_identifier VARCHAR(255) NOT NULL
) WITH (
  OIDS=FALSE
);



CREATE TABLE test (
	id integer NOT NULL,
	name TEXT NOT NULL,
	for_node integer NOT NULL,
	CONSTRAINT test_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE test_questions (
	test integer NOT NULL,
	test_question integer NOT NULL,
	CONSTRAINT test_questions_pk PRIMARY KEY (test,test_question)
) WITH (
  OIDS=FALSE
);



CREATE TABLE node_media (
	node integer NOT NULL,
	media integer NOT NULL,
	id serial NOT NULL,
	CONSTRAINT node_media_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE subscribed_plan (
	plan integer NOT NULL,
	student_profile integer NOT NULL,
	bought_on TIME NOT NULL,
	first_day TIME NOT NULL,
	last_day TIME NOT NULL,
	id serial NOT NULL,
	CONSTRAINT subscribed_plan_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE email (
	id serial NOT NULL,
	email_string VARCHAR(255) NOT NULL,
	isPrimary BOOLEAN NOT NULL DEFAULT 'false',
	CONSTRAINT email_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE school_email (
	email integer NOT NULL,
	school integer NOT NULL
) WITH (
  OIDS=FALSE
);



CREATE TABLE person_email (
	email integer NOT NULL,
	person integer NOT NULL
) WITH (
  OIDS=FALSE
);



CREATE TABLE student_media_progress (
	student integer NOT NULL,
	node_media integer NOT NULL,
	watched_duration integer NOT NULL,
	video_duration integer NOT NULL
) WITH (
  OIDS=FALSE
);



CREATE TABLE student_test_progress (
	student integer NOT NULL,
	test integer NOT NULL,
	marks integer NOT NULL,
	max integer NOT NULL
) WITH (
  OIDS=FALSE
);



CREATE TABLE PracticeTest (
	id serial NOT NULL,
	name TEXT,
	for_media integer NOT NULL,
	CONSTRAINT PracticeTest_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



ALTER TABLE Person ADD CONSTRAINT Person_fk0 FOREIGN KEY (address) REFERENCES Address(id);




ALTER TABLE Student_Profile ADD CONSTRAINT Student_Profile_fk0 FOREIGN KEY (person) REFERENCES Person(id);
ALTER TABLE Student_Profile ADD CONSTRAINT Student_Profile_fk1 FOREIGN KEY (school) REFERENCES School(id);

ALTER TABLE Subject ADD CONSTRAINT Subject_fk0 FOREIGN KEY (language) REFERENCES Language(id);


ALTER TABLE Subject_Capsule ADD CONSTRAINT Subject_Capsule_fk0 FOREIGN KEY (subject) REFERENCES Subject(id);
ALTER TABLE Subject_Capsule ADD CONSTRAINT Subject_Capsule_fk1 FOREIGN KEY (learn_root) REFERENCES LearningNode(id);

ALTER TABLE LearningNode ADD CONSTRAINT LearningNode_fk0 FOREIGN KEY (parent_node) REFERENCES LearningNode(id);

ALTER TABLE School ADD CONSTRAINT School_fk0 FOREIGN KEY (Address) REFERENCES Address(id);

ALTER TABLE school_phone ADD CONSTRAINT school_phone_fk0 FOREIGN KEY (phone) REFERENCES Phone(id);
ALTER TABLE school_phone ADD CONSTRAINT school_phone_fk1 FOREIGN KEY (school) REFERENCES School(id);

ALTER TABLE person_phone ADD CONSTRAINT person_phone_fk0 FOREIGN KEY (person) REFERENCES Person(id);
ALTER TABLE person_phone ADD CONSTRAINT person_phone_fk1 FOREIGN KEY (phone) REFERENCES Phone(id);

ALTER TABLE Plan_subjects ADD CONSTRAINT Plan_subjects_fk0 FOREIGN KEY (subject_capsule) REFERENCES Subject_Capsule(id);
ALTER TABLE Plan_subjects ADD CONSTRAINT Plan_subjects_fk1 FOREIGN KEY (plan) REFERENCES Plan(id);

ALTER TABLE Question ADD CONSTRAINT Question_fk0 FOREIGN KEY (ques_media) REFERENCES media(id);
ALTER TABLE Question ADD CONSTRAINT Question_fk1 FOREIGN KEY (solution) REFERENCES Solution(id);

ALTER TABLE ques_option ADD CONSTRAINT ques_option_fk0 FOREIGN KEY (question) REFERENCES Question(id);
ALTER TABLE ques_option ADD CONSTRAINT ques_option_fk1 FOREIGN KEY (option_media) REFERENCES media(id);


ALTER TABLE Solution ADD CONSTRAINT Solution_fk0 FOREIGN KEY (solution_media) REFERENCES media(id);

ALTER TABLE Practice_Questions ADD CONSTRAINT Practice_Questions_fk0 FOREIGN KEY (practice_test) REFERENCES PracticeTest(id);
ALTER TABLE Practice_Questions ADD CONSTRAINT Practice_Questions_fk1 FOREIGN KEY (question) REFERENCES Question(id);

ALTER TABLE athentication ADD CONSTRAINT athentication_fk0 FOREIGN KEY (person) REFERENCES Person(id);

ALTER TABLE person_meta ADD CONSTRAINT person_meta_fk0 FOREIGN KEY (person) REFERENCES Person(id);

ALTER TABLE test ADD CONSTRAINT test_fk0 FOREIGN KEY (for_node) REFERENCES LearningNode(id);

ALTER TABLE test_questions ADD CONSTRAINT test_questions_fk0 FOREIGN KEY (test) REFERENCES test(id);
ALTER TABLE test_questions ADD CONSTRAINT test_questions_fk1 FOREIGN KEY (test_question) REFERENCES Question(id);

ALTER TABLE node_media ADD CONSTRAINT node_media_fk0 FOREIGN KEY (node) REFERENCES LearningNode(id);
ALTER TABLE node_media ADD CONSTRAINT node_media_fk1 FOREIGN KEY (media) REFERENCES media(id);

ALTER TABLE subscribed_plan ADD CONSTRAINT subscribed_plan_fk0 FOREIGN KEY (plan) REFERENCES Plan(id);
ALTER TABLE subscribed_plan ADD CONSTRAINT subscribed_plan_fk1 FOREIGN KEY (student_profile) REFERENCES Student_Profile(student_id);


ALTER TABLE school_email ADD CONSTRAINT school_email_fk0 FOREIGN KEY (email) REFERENCES email(id);
ALTER TABLE school_email ADD CONSTRAINT school_email_fk1 FOREIGN KEY (school) REFERENCES School(id);

ALTER TABLE person_email ADD CONSTRAINT person_email_fk0 FOREIGN KEY (email) REFERENCES email(id);
ALTER TABLE person_email ADD CONSTRAINT person_email_fk1 FOREIGN KEY (person) REFERENCES Person(id);

ALTER TABLE student_media_progress ADD CONSTRAINT student_media_progress_fk0 FOREIGN KEY (student) REFERENCES Student_Profile(student_id);
ALTER TABLE student_media_progress ADD CONSTRAINT student_media_progress_fk1 FOREIGN KEY (node_media) REFERENCES node_media(id);

ALTER TABLE student_test_progress ADD CONSTRAINT student_test_progress_fk0 FOREIGN KEY (student) REFERENCES Student_Profile(student_id);
ALTER TABLE student_test_progress ADD CONSTRAINT student_test_progress_fk1 FOREIGN KEY (test) REFERENCES test(id);

ALTER TABLE PracticeTest ADD CONSTRAINT PracticeTest_fk0 FOREIGN KEY (for_media) REFERENCES node_media(id);
