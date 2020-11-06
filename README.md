# Inventory application
Re-platforming Exercise
Transform this spring-boot rest api application from being based on SQL Server to MongoDB.
The peculiar aspect of this project is that it is using bi-temporal tables (https://docs.microsoft.com/en-us/sql/relational-databases/tables/temporal-tables?view=sql-server-2017)

## Install SQL Server 

### Docker 
There is plenty of Docker images to download for SQL Server: https://hub.docker.com/_/microsoft-mssql-server 
```
docker run -e ‘ACCEPT_EULA=Y’ 
    -e ‘SA_PASSWORD=yourStrong(!)Password’ 
    -e ‘MSSQL_PID=Express’ 
    -p 1433:1433 -d mcr.microsoft.com/mssql/server:2017-latest-ubuntu
```
### Windows
or install it to a Windows Server/10 box

## Create the database
Create, under the default schema, a database named **inventory**.

In *"src/resources"* there is the *"schema.sql"* script that will create the tables.
```iso92-sql

    create table inventory (
       id int identity not null,
        stock numeric(19,0),
        product_id numeric(19,0),
        start_time datetime2(0) GENERATED ALWAYS AS ROW START NOT NULL,
        end_time datetime2(0) GENERATED ALWAYS AS ROW END NOT NULL,
        PERIOD FOR SYSTEM_TIME ([start_time], [end_time]),
        primary key (id)
    ) with (SYSTEM_VERSIONING = ON (HISTORY_TABLE = [dbo].[inventory_history]));

    create table products (
       id numeric(19,0) identity not null,
        description varchar(5000),
        name varchar(255) not null,
        primary key (id)
    )

    create table purchase (
       id numeric(19,0) identity not null,
        canceled bit default 0,
        customer varchar(255),
        date datetime,
        delivered bit default 0,
        shipped bit default 0,
        start_time datetime2(0) GENERATED ALWAYS AS ROW START NOT NULL,
        end_time datetime2(0) GENERATED ALWAYS AS ROW END NOT NULL,
        PERIOD FOR SYSTEM_TIME ([start_time], [end_time]),
        primary key (id)
    ) with (SYSTEM_VERSIONING = ON (HISTORY_TABLE = [dbo].[purchase_history]));

    create table purchase_items (
       id numeric(19,0) identity not null,
        quantity numeric(19,0),
        product_id numeric(19,0),
        purchase_id numeric(19,0),
        primary key (id)
    )

    alter table products 
       add constraint UK_name unique (name)

    alter table inventory 
       add constraint FKinventoryProducts
       foreign key (product_id) 
       references products

    alter table purchase_items 
       add constraint FKpurchasedProduct
       foreign key (product_id) 
       references products

    alter table purchase_items 
       add constraint FKpurchasedItems
       foreign key (purchase_id) 
       references purchase

```
Two tables, Inventory and Purchases, are bi-temporal. Every operation that alters values or the entire row, will be recorded in a second historical table.

## Launch the application
```
./mvnw spring-boot:run
```

## Test it
Under src/test/resources there is the Postman collection with all the rest api calls to:
 - setup the inventory
 - place an order
 - change the state of an order
 - report and data query
 
## Re-platform

