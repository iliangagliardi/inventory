
    create table inventory (
       id int identity not null,
        stock numeric(19,0),
        product_id numeric(19,0),
        startTime datetime2(0) GENERATED ALWAYS AS ROW START NOT NULL,
        endTime datetime2(0) GENERATED ALWAYS AS ROW END NOT NULL,
        PERIOD FOR SYSTEM_TIME ([startTime], [endTime]),
        primary key (id)
    ) WITH (SYSTEM_VERSIONING = ON (HISTORY_TABLE = [dbo].[inventory_history]));

    create table products (
       id numeric(19,0) identity not null,
        description varchar(5000),
        name varchar(255) not null,
        primary key (id)
    )

    create table purchase (
       id numeric(19,0) identity not null,
        customer varchar(255),
        date datetime,
        shipped bit default 0,
        canceled bit default 0,
        delivered bit default 0,
        primary key (id)
    )

    create table purchase_items (
       id numeric(19,0) identity not null,
        quantity numeric(19,0),
        product_id numeric(19,0),
        purchase_id numeric(19,0),
        primary key (id)
    )

    alter table products
       add constraint UK_o61fmio5yukmmiqgnxf8pnavn unique (name)

    alter table inventory
       add constraint FKq2yge7ebtfuvwufr6lwfwqy9l
       foreign key (product_id)
       references products

    alter table purchase_items
       add constraint FKbwtjp8gfcre77l1mxverb6up0
       foreign key (product_id)
       references products

    alter table purchase_items
       add constraint FKfbch0rs5h1ih9ng5tn5ydi7vm
       foreign key (purchase_id)
       references purchase
