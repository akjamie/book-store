insert into `users`(user_name,password,status,phone_number,auth_type,email,alias_name,created_by,created_at,updated_by,updated_at) values('jamie','$2a$10$AQ517Xm9J7eT3xUh.vDfIux4CIaTAecdxl.2AbuqqIkd1ozbI4Cmy','ACTIVE','008617782830001','MOBILE','jamie@it-meta.space','Jamie','system',current_timestamp(),'system',current_timestamp());
insert into `users`(user_name,password,status,phone_number,auth_type,email,alias_name,created_by,created_at,updated_by,updated_at) values('jamie-01','$2a$10$AQ517Xm9J7eT3xUh.vDfIux4CIaTAecdxl.2AbuqqIkd1ozbI4Cmy','ACTIVE','008617782830002','PASSWORD','jamie-01@it-meta.space','Jamie','system',current_timestamp(),'system',current_timestamp());
insert into `users`(user_name,password,status,phone_number,auth_type,email,alias_name,created_by,created_at,updated_by,updated_at) values('jamie-02','$2a$10$AQ517Xm9J7eT3xUh.vDfIux4CIaTAecdxl.2AbuqqIkd1ozbI4Cmy','INACTIVE','008617782830003','EMAIL','jamie-02@it-meta.space','Jamie','system',current_timestamp(),'system',current_timestamp());

INSERT INTO `authorities`(description, name,created_by,created_at,updated_by,updated_at) VALUES('list user','user:list','system',current_timestamp(),'system',current_timestamp());
INSERT INTO `authorities`(description, name,created_by,created_at,updated_by,updated_at) VALUES('add user','user:add','system',current_timestamp(),'system',current_timestamp());
INSERT INTO `authorities`(description, name,created_by,created_at,updated_by,updated_at) VALUES('modify user','user:modify','system',current_timestamp(),'system',current_timestamp());
INSERT INTO `authorities`(description, name,created_by,created_at,updated_by,updated_at) VALUES('delete user','user:delete','system',current_timestamp(),'system',current_timestamp());

INSERT INTO `groups`(name,description,created_by,created_at,updated_by,updated_at) VALUES('user-viewer','user readonly group','system',current_timestamp(),'system',current_timestamp());
INSERT INTO `groups`(name,description,created_by,created_at,updated_by,updated_at) VALUES('user-editor','user maintainer group','system',current_timestamp(),'system',current_timestamp());
INSERT INTO `groups`(name,description,created_by,created_at,updated_by,updated_at) VALUES('user-admin','user admin group','system',current_timestamp(),'system',current_timestamp());

INSERT INTO `group_members`(user_id, group_id,created_by,created_at,updated_by,updated_at) values(1,3,'system',current_timestamp(),'system',current_timestamp());
INSERT INTO `group_members`(user_id, group_id,created_by,created_at,updated_by,updated_at) values(2,2,'system',current_timestamp(),'system',current_timestamp());
INSERT INTO `group_members`(user_id, group_id,created_by,created_at,updated_by,updated_at) values(3,1,'system',current_timestamp(),'system',current_timestamp());

INSERT INTO `group_authorities`(group_id, authority_id,created_by,created_at,updated_by,updated_at) values(3,1,'system',current_timestamp(),'system',current_timestamp());
INSERT INTO `group_authorities`(group_id, authority_id,created_by,created_at,updated_by,updated_at) values(3,2,'system',current_timestamp(),'system',current_timestamp());
INSERT INTO `group_authorities`(group_id, authority_id,created_by,created_at,updated_by,updated_at) values(3,3,'system',current_timestamp(),'system',current_timestamp());
INSERT INTO `group_authorities`(group_id, authority_id,created_by,created_at,updated_by,updated_at) values(3,4,'system',current_timestamp(),'system',current_timestamp());
INSERT INTO `group_authorities`(group_id, authority_id,created_by,created_at,updated_by,updated_at) values(2,1,'system',current_timestamp(),'system',current_timestamp());
INSERT INTO `group_authorities`(group_id, authority_id,created_by,created_at,updated_by,updated_at) values(2,2,'system',current_timestamp(),'system',current_timestamp());
INSERT INTO `group_authorities`(group_id, authority_id,created_by,created_at,updated_by,updated_at) values(2,3,'system',current_timestamp(),'system',current_timestamp());
INSERT INTO `group_authorities`(group_id, authority_id,created_by,created_at,updated_by,updated_at) values(1,1,'system',current_timestamp(),'system',current_timestamp());

