alter table athentication drop column can_be_admin;
alter table athentication alter column running_token drop not null;
alter table school alter column address drop not null;
alter table athentication rename to Authentication;