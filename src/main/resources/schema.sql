CREATE TABLE IF NOT EXISTS customers (
    First_Name VARCHAR(255),
    Last_Name VARCHAR(255),
    Age INTEGER,
    PRIMARY KEY (Age)
);

CREATE TABLE IF NOT EXISTS stock_data (
    Name VARCHAR(20) NOT NULL,
    Market_Date VARCHAR(20) NOT NULL,
    Opening_Price VARCHAR(20),
    Closing_Price VARCHAR(20),
    PRIMARY KEY (Name)
);