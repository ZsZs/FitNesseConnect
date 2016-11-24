DROP TABLE car if EXISTS ;

CREATE TABLE car (
	id bigint NOT NULL,
	make varchar(100),
	model varchar(100),
	year int,
	color varchar(200),
	PRIMARY KEY (id)
);
