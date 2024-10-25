USE qa;

DROP TABLE IF EXISTS qa.users;

CREATE TABLE users(
userid int auto_increment primary key,
username varchar(100),
email varchar(100),
passwordhash varchar(255),
createdat datetime default current_timestamp);

DROP PROCEDURE IF EXISTS AddNewUser;

DELIMITER //
CREATE PROCEDURE AddNewUser(
IN p_username varchar(100),
IN p_email varchar(100),
IN p_passwordhash varchar(255)
)

BEGIN
INSERT INTO users(username, email, passwordhash) values(p_username, p_email, p_passwordhash);
select last_insert_id() as newuserid;
END // DELIMITER ;

USE qa;
CALL AddNewUser('John Doe', 'john.doe@example.com','hashed_password_here');
CALL AddNewUser('Alan Smith', 'alan.smith@example.com','hashed_password_here');
CALL AddNewUser('Peter Brown', 'peter.brown@example.com','hashed_password_here');

SELECT * from users;