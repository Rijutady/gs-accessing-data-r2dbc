drop table T_ACCOUNT if exists;

create table T_ACCOUNT (ID bigint identity primary key, NUMBER varchar(9),
                        NAME varchar(50) not null, BALANCE decimal(8,2), unique(NUMBER));
                        
ALTER TABLE T_ACCOUNT ALTER COLUMN BALANCE SET DEFAULT 0.0;
drop table T_POST if exists;
create table T_POST (ID bigint identity primary key,
                        AUTHOR varchar(50) not null,
                        CONTENT varchar(500) not null,
                        THREADID bigint
                        );