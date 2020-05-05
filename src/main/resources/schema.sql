CREATE TABLE IF NOT EXISTS BATCH_JOB_INSTANCE  (
  JOB_INSTANCE_ID BIGINT  PRIMARY KEY ,
  VERSION BIGINT,
  JOB_NAME VARCHAR(100) NOT NULL ,
  JOB_KEY VARCHAR(2500)
);

CREATE TABLE IF NOT EXISTS stock_data (
    Name VARCHAR(20) NOT NULL,
    Market_Date VARCHAR(20) NOT NULL,
    Opening_Price VARCHAR(20),
    Closing_Price VARCHAR(20),
    Bought_At_Price VARCHAR(20),
    Bought_At_Quantity VARCHAR(20),
    Profit VARCHAR(20),
    Percentage_Increase VARCHAR(20),
    Previous_Market_Date VARCHAR(20),
    CONSTRAINT stock_data_keys PRIMARY KEY (Name, Market_Date)
);
