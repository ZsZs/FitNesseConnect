DROP TABLE car if EXISTS ;

CREATE TABLE car (
	id bigint NOT NULL AUTO_INCREMENT,
	make varchar(100),
	model varchar(100),
	description varchar(1000),
	image_url varchar(500),
	year int,
	color varchar(50),
	PRIMARY KEY (id)
);
