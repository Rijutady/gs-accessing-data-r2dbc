drop table account if exists;

create table account (ID bigint identity primary key, NUMBER varchar(9),
                        LAST_NAME varchar(50) not null, FIRST_NAME varchar(50) not null, BALANCE decimal(8,2), unique(NUMBER), NEW_ACCOUNT BIT);

ALTER TABLE account ALTER COLUMN BALANCE SET DEFAULT 0.0;