CREATE TABLE IF NOT EXISTS BATCH_JOB_INSTANCE  (
  job_instance_id BIGINT  PRIMARY KEY ,
  version BIGINT,
  job_name VARCHAR(100) NOT NULL ,
  Job_key VARCHAR(2500)
);

CREATE TABLE IF NOT EXISTS stock_data (
    name VARCHAR(20) NOT NULL,
    market_date VARCHAR(20) NOT NULL,
    opening_price VARCHAR(20),
    closing_price VARCHAR(20),
    bought_at_price VARCHAR(20),
    bought_at_quantity VARCHAR(20),
    profit VARCHAR(20),
    percentage_increase VARCHAR(20),
    previous_market_date VARCHAR(20),
    CONSTRAINT stock_data_keys PRIMARY KEY (Name, Market_Date)
);
