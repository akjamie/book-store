insert into `users`(user_name,password,status,phone_number,auth_type,email,alias_name,created_by,created_date,last_modified_by,last_modified_date) values('jamie','$2a$10$AQ517Xm9J7eT3xUh.vDfIux4CIaTAecdxl.2AbuqqIkd1ozbI4Cmy','ACTIVE','008617782830001','MOBILE','jamie@it-meta.space','Jamie','system',current_timestamp(),'system',current_timestamp());
insert into `users`(user_name,password,status,phone_number,auth_type,email,alias_name,created_by,created_date,last_modified_by,last_modified_date) values('jamie-01','$2a$10$AQ517Xm9J7eT3xUh.vDfIux4CIaTAecdxl.2AbuqqIkd1ozbI4Cmy','ACTIVE','008617782830002','PASSWORD','jamie-01@it-meta.space','Jamie','system',current_timestamp(),'system',current_timestamp());
insert into `users`(user_name,password,status,phone_number,auth_type,email,alias_name,created_by,created_date,last_modified_by,last_modified_date) values('jamie-02','$2a$10$AQ517Xm9J7eT3xUh.vDfIux4CIaTAecdxl.2AbuqqIkd1ozbI4Cmy','INACTIVE','008617782830003','EMAIL','jamie-02@it-meta.space','Jamie','system',current_timestamp(),'system',current_timestamp());

INSERT INTO `authorities`(description, name,created_by,created_date,last_modified_by,last_modified_date) VALUES('list user','user:list','system',current_timestamp(),'system',current_timestamp());
INSERT INTO `authorities`(description, name,created_by,created_date,last_modified_by,last_modified_date) VALUES('add user','user:add','system',current_timestamp(),'system',current_timestamp());
INSERT INTO `authorities`(description, name,created_by,created_date,last_modified_by,last_modified_date) VALUES('modify user','user:modify','system',current_timestamp(),'system',current_timestamp());
INSERT INTO `authorities`(description, name,created_by,created_date,last_modified_by,last_modified_date) VALUES('delete user','user:delete','system',current_timestamp(),'system',current_timestamp());

INSERT INTO `groups`(name,description,created_by,created_date,last_modified_by,last_modified_date) VALUES('user-general-readonly','user readonly group','system',current_timestamp(),'system',current_timestamp());
INSERT INTO `groups`(name,description,created_by,created_date,last_modified_by,last_modified_date) VALUES('user-general-devops','user maintainer group','system',current_timestamp(),'system',current_timestamp());
INSERT INTO `groups`(name,description,created_by,created_date,last_modified_by,last_modified_date) VALUES('user-general-admin','user admin group','system',current_timestamp(),'system',current_timestamp());

INSERT INTO `group_members`(user_id, group_id,created_by,created_date,last_modified_by,last_modified_date) values(1,3,'system',current_timestamp(),'system',current_timestamp());
INSERT INTO `group_members`(user_id, group_id,created_by,created_date,last_modified_by,last_modified_date) values(2,2,'system',current_timestamp(),'system',current_timestamp());
INSERT INTO `group_members`(user_id, group_id,created_by,created_date,last_modified_by,last_modified_date) values(3,1,'system',current_timestamp(),'system',current_timestamp());

INSERT INTO `group_authorities`(group_id, authority_id,created_by,created_date,last_modified_by,last_modified_date) values(3,1,'system',current_timestamp(),'system',current_timestamp());
INSERT INTO `group_authorities`(group_id, authority_id,created_by,created_date,last_modified_by,last_modified_date) values(3,2,'system',current_timestamp(),'system',current_timestamp());
INSERT INTO `group_authorities`(group_id, authority_id,created_by,created_date,last_modified_by,last_modified_date) values(3,3,'system',current_timestamp(),'system',current_timestamp());
INSERT INTO `group_authorities`(group_id, authority_id,created_by,created_date,last_modified_by,last_modified_date) values(3,4,'system',current_timestamp(),'system',current_timestamp());
INSERT INTO `group_authorities`(group_id, authority_id,created_by,created_date,last_modified_by,last_modified_date) values(2,1,'system',current_timestamp(),'system',current_timestamp());
INSERT INTO `group_authorities`(group_id, authority_id,created_by,created_date,last_modified_by,last_modified_date) values(2,2,'system',current_timestamp(),'system',current_timestamp());
INSERT INTO `group_authorities`(group_id, authority_id,created_by,created_date,last_modified_by,last_modified_date) values(2,3,'system',current_timestamp(),'system',current_timestamp());
INSERT INTO `group_authorities`(group_id, authority_id,created_by,created_date,last_modified_by,last_modified_date) values(1,1,'system',current_timestamp(),'system',current_timestamp());

