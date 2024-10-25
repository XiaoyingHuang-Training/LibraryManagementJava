CREATE DATABASE LibraryDB;
USE LibraryDB;


DROP TABLE IF EXISTS Current_transactions;
DROP TABLE IF EXISTS Archived_transactions;
DROP TABLE IF EXISTS Members;
DROP TABLE IF EXISTS Books;
DROP TABLE IF EXISTS Fines;

CREATE TABLE Members (
Member_ID INT AUTO_INCREMENT NOT NULL,
PRIMARY KEY (Member_ID),
First_Name VARCHAR(30) NOT NULL,
Last_Name VARCHAR(30) NOT NULL,
Email VARCHAR(255) NOT NULL,
Phone_Number VARCHAR(11) NOT NULL,
Address VARCHAR(255) NOT NULL,
DOB DATE NOT NULL,
Password_hash VARCHAR(255) NOT NULL,
Loan_Limit INT NOT NULL,
Current_Loans INT NOT NULL,
Status BOOLEAN NOT NULL
);

INSERT INTO Members (First_Name, Last_Name, Email, Phone_Number, Address, DOB, Password_hash, Loan_Limit, Current_Loans, Status)
VALUES ('John', 'Doe', 'John.Doe@me.com', 07561729610, '9 Main Street', '2005-01-01', 'password', 5, 1, True);

UPDATE Members SET Loan_Limit=Loan_Limit-1 WHERE Member_ID=1;

SELECT * FROM Members;

CREATE TABLE Books (
ISBN CHAR(13) NOT NULL,
PRIMARY KEY (ISBN),
Book_ID INT NOT NULL,
Title VARCHAR(255) NOT NULL,
`Author(s)` VARCHAR(255) NOT NULL,
Loan_Length INT NOT NULL,
Price DECIMAL(8,2) NOT NULL,
Location VARCHAR(30) NOT NULL,
Publisher VARCHAR(30) NOT NULL,
Publication_Year YEAR NOT NULL,
Edition INT NOT NULL,
Max_renewals INT NOT NULL
);

INSERT INTO Books (ISBN, Book_ID, Title, `Author(s)`, Loan_Length, Price, Location, Publisher, Publication_Year, Edition, Max_renewals)
VALUES ('1234567891012', 10, 'SQL For Beginners', 'Bob and Jim', 23, 43.95, '11.008.23', 'Raven', 2014, 3, 5);

INSERT INTO Books (ISBN, Book_ID, Title, `Author(s)`, Publication_Year, Loan_Length, Price, Location, Publisher, Edition, Max_renewals) VALUES
('9780743273565', 551, 'The Great Gatsby', 'F. Scott Fitzgerald', 1925, 14, 10.00, '813.52', 'Scribner', 1, 5),
('9780743273566', 551, 'The Great Gatsby', 'F. Scott Fitzgerald', 1925, 14, 10.00, '813.52', 'Scribner', 1, 5),
('9780743273567', 551, 'The Great Gatsby', 'F. Scott Fitzgerald', 1925, 14, 10.00, '813.52', 'Scribner', 1, 5),
('9780743273568', 551, 'The Great Gatsby', 'F. Scott Fitzgerald', 1925, 14, 10.00, '813.52', 'Scribner', 1, 5),
('9780743273569', 551, 'The Great Gatsby', 'F. Scott Fitzgerald', 1925, 14, 10.00, '813.52', 'Scribner', 1, 5),
('9780743273570', 551, 'The Great Gatsby', 'F. Scott Fitzgerald', 1925, 14, 10.00, '813.52', 'Scribner', 1, 5),
('9780743273571', 551, 'The Great Gatsby', 'F. Scott Fitzgerald', 1925, 14, 10.00, '813.52', 'Scribner', 1, 5),
('9780743273572', 551, 'The Great Gatsby', 'F. Scott Fitzgerald', 1925, 14, 10.00, '813.52', 'Scribner', 1, 5),
('9780743273573', 551, 'The Great Gatsby', 'F. Scott Fitzgerald', 1925, 14, 10.00, '813.52', 'Scribner', 1, 5),
('9780743273574', 551, 'The Great Gatsby', 'F. Scott Fitzgerald', 1925, 14, 10.00, '813.52', 'Scribner', 1, 5),
('9780195127447', 562, 'Ulysses', 'James Joyce', 1922, 14, 10.00, '823.912', 'Shakespeare and Company', 1, 5),
('9780679417392', 563, 'Lolita', 'Vladimir Nabokov', 1955, 14, 10.00, '813.54', 'G.P. Putnam\'s Sons', 1, 5),
('9780679417393', 563, 'Lolita', 'Vladimir Nabokov', 1955, 14, 10.00, '813.54', 'G.P. Putnam\'s Sons', 1, 5),
('9780679417394', 563, 'Lolita', 'Vladimir Nabokov', 1955, 14, 10.00, '813.54', 'G.P. Putnam\'s Sons', 1, 5),
('9780679417395', 563, 'Lolita', 'Vladimir Nabokov', 1955, 14, 10.00, '813.54', 'G.P. Putnam\'s Sons', 1, 5),
('9780679417396', 563, 'Lolita', 'Vladimir Nabokov', 1955, 14, 10.00, '813.54', 'G.P. Putnam\'s Sons', 1, 5),
('9780679417397', 563, 'Lolita', 'Vladimir Nabokov', 1955, 14, 10.00, '813.54', 'G.P. Putnam\'s Sons', 1, 5),
('9780679417398', 563, 'Lolita', 'Vladimir Nabokov', 1955, 14, 10.00, '813.54', 'G.P. Putnam\'s Sons', 1, 5),
('9780060850524', 569, 'Brave New World', 'Aldous Huxley', 1932, 14, 10.00, '823.912', 'Chatto & Windus', 1, 5),
('9780679732259', 570, 'The Sound and the Fury', 'William Faulkner', 1929, 14, 10.00, '813.52', 'Jonathan Cape', 1, 5),
('9780679732260', 570, 'The Sound and the Fury', 'William Faulkner', 1929, 14, 10.00, '813.52', 'Jonathan Cape', 1, 5),
('9780679732261', 570, 'The Sound and the Fury', 'William Faulkner', 1929, 14, 10.00, '813.52', 'Jonathan Cape', 1, 5),
('9780679732262', 570, 'The Sound and the Fury', 'William Faulkner', 1929, 14, 10.00, '813.52', 'Jonathan Cape', 1, 5),
('9781451626650', 574, 'Catch-22', 'Joseph Heller', 1961, 14, 10.00, '813.54', 'Simon & Schuster', 1, 5),
('9780143039433', 575, 'The Grapes of Wrath', 'John Steinbeck', 1939, 14, 10.00, '813.54', 'The Viking Press', 1, 5),
('9780143039716', 576, 'I, Claudius', 'Robert Graves', 1934, 14, 10.00, '823.914', 'Arthur Baker', 1, 5),
('9780156711425', 577, 'To the Lighthouse', 'Virginia Woolf', 1927, 14, 10.00, '823.912', 'Hogarth Press', 1, 5),
('9780440180296', 578, 'Slaughterhouse-Five', 'Kurt Vonnegut', 1969, 14, 10.00, '813.54', 'Delta', 1, 5),
('9780679734968', 579, 'Invisible Man', 'Ralph Ellison', 1952, 14, 10.00, '813.54', 'Random House', 1, 5),
('9780061148504', 580, 'Native Son', 'Richard Wright', 1940, 14, 10.00, '813.54', 'Harper & Brothers', 1, 5),
('9780060851149', 581, 'USA Trilogy', 'John Dos Passos', 1930, 14, 10.00, '813.54', 'Harcourt, Brace and Company', 1, 5),
('9780143039747', 582, 'A Passage to India', 'E.M. Forster', 1924, 14, 10.00, '823.912', 'Edward Arnold', 1, 5),
('9780743273599', 583, 'Tender is the Night', 'F. Scott Fitzgerald', 1934, 14, 10.00, '813.52', 'Charles Scribner\'s Sons', 1, 5);

SELECT * FROM Books;

CREATE TABLE Current_Transactions (
ISBN CHAR(13) NOT NULL, FOREIGN KEY(ISBN) REFERENCES Books(ISBN),
Date_Borrowed TIMESTAMP,
Member_ID INT NOT NULL, FOREIGN KEY(Member_ID) REFERENCES Members(Member_ID),
Renewals_Left INT NOT NULL
);

INSERT INTO Current_Transactions (ISBN, Member_ID, Renewals_Left)
VALUES ('1234567891012', 1, 3);

INSERT INTO Current_Transactions(ISBN, Member_ID, Renewals_Left) VALUES('9780743273599',1,(SELECT Max_renewals FROM Books WHERE ISBN='9780743273599'));

SELECT * FROM Current_Transactions;

CREATE TABLE Archived_Transactions(
ISBN CHAR(13) NOT NULL, FOREIGN KEY(ISBN) REFERENCES Books(ISBN),
Member_ID INT NOT NULL, FOREIGN KEY(Member_ID) REFERENCES Members(Member_ID),
Date_Borrowed TIMESTAMP,
Date_Returned TIMESTAMP
);

INSERT INTO Archived_Transactions (ISBN, Member_ID)
VALUES ('1234567891012', 1);

SELECT * FROM Archived_Transactions;

CREATE TABLE Fines (
Member_ID INT NOT NULL, FOREIGN KEY(Member_ID) REFERENCES Members(Member_ID),
ISBN CHAR(13) NOT NULL, FOREIGN KEY(ISBN) REFERENCES Books(ISBN),
Due_Payment DECIMAL(10,2) NOT NULL,
Due_Date DATE NOT NULL
);

INSERT INTO Fines (Member_ID, ISBN, Due_Payment, Due_Date)
VALUES (1, '1234567891012', 543.90, "2021-10-07");

UPDATE Members SET Loan_Limit=2 WHERE Member_ID=1;