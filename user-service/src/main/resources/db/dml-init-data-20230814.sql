INSERT INTO `authorities`(description, name,created_by,created_at,updated_by,updated_at) VALUES('list category','category:list','system',current_timestamp(),'system',current_timestamp());
INSERT INTO `authorities`(description, name,created_by,created_at,updated_by,updated_at) VALUES('add category','category:add','system',current_timestamp(),'system',current_timestamp());
INSERT INTO `authorities`(description, name,created_by,created_at,updated_by,updated_at) VALUES('modify category','category:modify','system',current_timestamp(),'system',current_timestamp());
INSERT INTO `authorities`(description, name,created_by,created_at,updated_by,updated_at) VALUES('delete category','category:delete','system',current_timestamp(),'system',current_timestamp());

INSERT INTO `authorities`(description, name,created_by,created_at,updated_by,updated_at) VALUES('list inventory','inventory:list','system',current_timestamp(),'system',current_timestamp());
INSERT INTO `authorities`(description, name,created_by,created_at,updated_by,updated_at) VALUES('add inventory','inventory:add','system',current_timestamp(),'system',current_timestamp());
INSERT INTO `authorities`(description, name,created_by,created_at,updated_by,updated_at) VALUES('modify inventory','inventory:modify','system',current_timestamp(),'system',current_timestamp());
INSERT INTO `authorities`(description, name,created_by,created_at,updated_by,updated_at) VALUES('delete inventory','inventory:delete','system',current_timestamp(),'system',current_timestamp());

INSERT INTO `authorities`(description, name,created_by,created_at,updated_by,updated_at) VALUES('list product','product:list','system',current_timestamp(),'system',current_timestamp());
INSERT INTO `authorities`(description, name,created_by,created_at,updated_by,updated_at) VALUES('add product','product:add','system',current_timestamp(),'system',current_timestamp());
INSERT INTO `authorities`(description, name,created_by,created_at,updated_by,updated_at) VALUES('modify product','product:modify','system',current_timestamp(),'system',current_timestamp());
INSERT INTO `authorities`(description, name,created_by,created_at,updated_by,updated_at) VALUES('delete product','product:delete','system',current_timestamp(),'system',current_timestamp());

INSERT INTO `groups`(name,description,created_by,created_at,updated_by,updated_at) VALUES('category-viewer','category readonly group','system',current_timestamp(),'system',current_timestamp());
INSERT INTO `groups`(name,description,created_by,created_at,updated_by,updated_at) VALUES('category-editor','category maintainer group','system',current_timestamp(),'system',current_timestamp());
INSERT INTO `groups`(name,description,created_by,created_at,updated_by,updated_at) VALUES('category-admin','category admin group','system',current_timestamp(),'system',current_timestamp());

INSERT INTO `groups`(name,description,created_by,created_at,updated_by,updated_at) VALUES('inventory-viewer','inventory readonly group','system',current_timestamp(),'system',current_timestamp());
INSERT INTO `groups`(name,description,created_by,created_at,updated_by,updated_at) VALUES('inventory-editor','inventory maintainer group','system',current_timestamp(),'system',current_timestamp());
INSERT INTO `groups`(name,description,created_by,created_at,updated_by,updated_at) VALUES('inventory-owner','inventory admin group','system',current_timestamp(),'system',current_timestamp());

INSERT INTO `groups`(name,description,created_by,created_at,updated_by,updated_at) VALUES('product-viewer','product readonly group','system',current_timestamp(),'system',current_timestamp());
INSERT INTO `groups`(name,description,created_by,created_at,updated_by,updated_at) VALUES('product-editor','product maintainer group','system',current_timestamp(),'system',current_timestamp());
INSERT INTO `groups`(name,description,created_by,created_at,updated_by,updated_at) VALUES('product-owner','product admin group','system',current_timestamp(),'system',current_timestamp());

INSERT INTO `group_members`(user_id, group_id,created_by,created_at,updated_by,updated_at) values(1,6,'system',current_timestamp(),'system',current_timestamp());
INSERT INTO `group_members`(user_id, group_id,created_by,created_at,updated_by,updated_at) values(2,9,'system',current_timestamp(),'system',current_timestamp());
INSERT INTO `group_members`(user_id, group_id,created_by,created_at,updated_by,updated_at) values(3,12,'system',current_timestamp(),'system',current_timestamp());

INSERT INTO `group_authorities`(group_id, authority_id,created_by,created_at,updated_by,updated_at) values(6,5,'system',current_timestamp(),'system',current_timestamp());
INSERT INTO `group_authorities`(group_id, authority_id,created_by,created_at,updated_by,updated_at) values(6,6,'system',current_timestamp(),'system',current_timestamp());
INSERT INTO `group_authorities`(group_id, authority_id,created_by,created_at,updated_by,updated_at) values(6,7,'system',current_timestamp(),'system',current_timestamp());
INSERT INTO `group_authorities`(group_id, authority_id,created_by,created_at,updated_by,updated_at) values(6,8,'system',current_timestamp(),'system',current_timestamp());

INSERT INTO `group_authorities`(group_id, authority_id,created_by,created_at,updated_by,updated_at) values(7,5,'system',current_timestamp(),'system',current_timestamp());
INSERT INTO `group_authorities`(group_id, authority_id,created_by,created_at,updated_by,updated_at) values(7,6,'system',current_timestamp(),'system',current_timestamp());
INSERT INTO `group_authorities`(group_id, authority_id,created_by,created_at,updated_by,updated_at) values(7,7,'system',current_timestamp(),'system',current_timestamp());

INSERT INTO `group_authorities`(group_id, authority_id,created_by,created_at,updated_by,updated_at) values(9,9,'system',current_timestamp(),'system',current_timestamp());
INSERT INTO `group_authorities`(group_id, authority_id,created_by,created_at,updated_by,updated_at) values(9,10,'system',current_timestamp(),'system',current_timestamp());
INSERT INTO `group_authorities`(group_id, authority_id,created_by,created_at,updated_by,updated_at) values(9,11,'system',current_timestamp(),'system',current_timestamp());
INSERT INTO `group_authorities`(group_id, authority_id,created_by,created_at,updated_by,updated_at) values(9,12,'system',current_timestamp(),'system',current_timestamp());

INSERT INTO `group_authorities`(group_id, authority_id,created_by,created_at,updated_by,updated_at) values(12,13,'system',current_timestamp(),'system',current_timestamp());
INSERT INTO `group_authorities`(group_id, authority_id,created_by,created_at,updated_by,updated_at) values(12,14,'system',current_timestamp(),'system',current_timestamp());
INSERT INTO `group_authorities`(group_id, authority_id,created_by,created_at,updated_by,updated_at) values(12,15,'system',current_timestamp(),'system',current_timestamp());
INSERT INTO `group_authorities`(group_id, authority_id,created_by,created_at,updated_by,updated_at) values(12,16,'system',current_timestamp(),'system',current_timestamp());

