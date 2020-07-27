CREATE TABLE IF NOT EXISTS USERS (
  userid INT PRIMARY KEY auto_increment,
  username VARCHAR(20) NOT NULL,
  salt VARCHAR,
  password VARCHAR,
  firstname VARCHAR(20),
  lastname VARCHAR(20),
  UNIQUE (username)
);

CREATE TABLE IF NOT EXISTS NOTES (
    noteid INT PRIMARY KEY auto_increment,
    title VARCHAR(20),
    description VARCHAR (1000),
    userid INT,
    UNIQUE (title),
    foreign key (userid) references USERS(userid)
);

CREATE TABLE IF NOT EXISTS FILES (
    fileId INT PRIMARY KEY auto_increment,
    filename VARCHAR NOT NULL,
    contenttype VARCHAR,
    filesize VARCHAR,
    userid INT,
    filedata BLOB,
    UNIQUE (filename),
    foreign key (userid) references USERS(userid)
);

CREATE TABLE IF NOT EXISTS CREDENTIALS (
    credentialid INT PRIMARY KEY auto_increment,
    url VARCHAR(100),
    username VARCHAR (30),
    passkey VARCHAR,
    password VARCHAR,
    userid INT,
    UNIQUE (url, username),
    foreign key (userid) references USERS(userid)
);