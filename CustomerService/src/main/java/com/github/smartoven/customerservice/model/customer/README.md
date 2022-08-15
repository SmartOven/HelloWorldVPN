# Customer table
## Schema
```sql
-- auto-generated definition
create table CUSTOMER
(
    ID       INTEGER   not null
        primary key,
    USERNAME CHARACTER not null
        unique,
    PASSWORD CHARACTER not null
);
```
