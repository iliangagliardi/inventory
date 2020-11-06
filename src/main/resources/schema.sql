
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
