DROP TABLE IF EXISTS credit_card;

create table credit_card (
       card_number bigint not null,
        balance double not null,
        card_holder_name varchar(255),
        card_limit bigint not null,
        primary key (card_number)
);
