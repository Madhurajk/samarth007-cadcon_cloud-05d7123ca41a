ALTER table question ALTER ques_text  drop not null;
ALTER table solution ALTER solution_text  drop not null;

ALTER table question ALTER ques_text  type varchar;
ALTER table solution ALTER solution_text  type varchar;

ALTER TABLE question  ADD COLUMN IF NOT EXISTS label varchar null;