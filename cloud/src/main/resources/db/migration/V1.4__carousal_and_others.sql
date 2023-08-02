CREATE TABLE Carousal (
    school integer NOT NULL,
    media integer NOT NULL,
    sequence integer NOT NULL,
    CONSTRAINT carousal_pk PRIMARY KEY(school, media, sequence)
) WITH (
    OIDS = FALSE
);

ALTER TABLE Carousal ADD CONSTRAINT carousal_school_fk FOREIGN KEY (school) REFERENCES School(id);
ALTER TABLE Carousal ADD CONSTRAINT carousal_media_fk FOREIGN KEY (media) REFERENCES Media(id);

CREATE TABLE Sponsors(
    school integer NOT NULL,
    media integer NOT NULL,
    CONSTRAINT sponsers_pk PRIMARY KEY(school, media)
) WITH (
      OIDS = FALSE
);

ALTER TABLE Sponsors ADD CONSTRAINT sponsers_school_fk FOREIGN KEY (school) REFERENCES School(id);
ALTER TABLE Sponsors ADD CONSTRAINT sponsers_media_fk FOREIGN KEY (media) REFERENCES Media(id);

ALTER TABLE LearningNode ADD COLUMN is_active BOOLEAN;

UPDATE LearningNode SET is_active = false;

ALTER TABLE LearningNode ALTER COLUMN is_active SET NOT NULL;

ALTER TABLE LearningNode ALTER COLUMN is_active SET DEFAULT 'false';

ALTER TABLE LearningNode ADD COLUMN sequence INTEGER;

UPDATE LearningNode SET sequence = 0;

ALTER TABLE LearningNode ALTER COLUMN sequence SET NOT NULL;