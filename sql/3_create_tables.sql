-- USE 'accountingDB';
--
-- Account owner
--
CREATE TABLE Users(
    user_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,-- pk
    name VARCHAR(50) NOT NULL,
    address VARCHAR(255)
);
--
-- User's account
--
CREATE TABLE Accounts(
    account_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,-- pk
    user_id INTEGER(10) NOT NULL,
    balance DECIMAL(15,3),
    currency VARCHAR(10) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (user_id),
    CHECK (balance >= 0 AND balance <= 2000000000),
    UNIQUE (user_id, currency)
);
--
-- User's transactions store
--
CREATE TABLE Transactions(
    transaction_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,-- pk
    account_id INTEGER(10) NOT NULL,
    amount DECIMAL(15,3) NOT NULL,
    FOREIGN KEY (account_id) REFERENCES Accounts (account_id),
    CHECK (amount <> 0 AND ABS(amount) <= 100000000)
);
PRAGMA TABLE_INFO (Users);
PRAGMA TABLE_INFO (Accounts);
PRAGMA TABLE_INFO (Transactions);
