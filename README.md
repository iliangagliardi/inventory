# Inventory application
Re-platforming Exercise
Transform this spring-boot rest api application from being based on SQL Server to MongoDB.
The peculiar aspect of this project is that it is using bi-temporal tables (https://docs.microsoft.com/en-us/sql/relational-databases/tables/temporal-tables?view=sql-server-2017)

## Setup
### Install SQL Server 

#### Docker 
There is plenty of Docker images to download for SQL Server: https://hub.docker.com/_/microsoft-mssql-server 
```
docker run -e ‘ACCEPT_EULA=Y’ 
    -e ‘SA_PASSWORD=yourStrong(!)Password’ 
    -e ‘MSSQL_PID=Express’ 
    -p 1433:1433 -d mcr.microsoft.com/mssql/server:2017-latest-ubuntu
```
#### Windows
It can be installed to a Windows Server/10 box, Microsoft lets you download developers/express version
https://www.microsoft.com/en-us/sql-server/sql-server-downloads
Once the db server has been installed, configure it (https://docs.microsoft.com/en-us/sql/database-engine/configure-windows/enable-or-disable-a-server-network-protocol?view=sql-server-ver15)
 

### Create the database
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

![tables ](https://github.com/iliangagliardi/inventory/blob/master/src/main/resources/static/dbscreen1.png?raw=true)

After invoking the rest service *setup inventory* and placing some orders with /order/place Order
![tables ](https://github.com/iliangagliardi/inventory/blob/master/src/main/resources/static/postmanscreen1.png?raw=true)

the database should look like the image
![tables ](https://github.com/iliangagliardi/inventory/blob/master/src/main/resources/static/dbscreen2.png?raw=true)


## Launch the application

Modify the application.properties, configuring the necessary parameters to database connection.

```

spring.datasource.driverClassName=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.datasource.url=jdbc:sqlserver://<your server>;databaseName=inventory
spring.datasource.username=sa
spring.datasource.password=<your password>

```

Here is the maven command to download dependencies, build and run the application.
```
./mvnw spring-boot:run
```

## Test it
Under src/test/resources there is the Postman collection (to import in your Postman workspace) with all the rest api calls to:
 - setup the inventory
 - place an order
 - change the state of an order
 - report and data query
 
## Re-platform
Rewrite the application to use MongoDB and figure out how to manage the "history" of changing data, in order to query data in a **point in time** way.
