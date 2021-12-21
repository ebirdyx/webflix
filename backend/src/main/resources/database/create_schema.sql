-- drop all tables --
set foreign_key_checks = 0;

drop table if exists Address;
drop table if exists User;
drop table if exists CreditCard;
drop table if exists PersonRolePlayed;
drop table if exists Customer;
drop table if exists CustomerSubscription;
drop table if exists MovieDVD;
drop table if exists Trailer;
drop table if exists SubscriptionPayment;
drop table if exists Employee;
drop table if exists MovieGenre;
drop table if exists Rentals;
drop table if exists People;
drop table if exists Genre;
drop table if exists MovieLanguages;
drop table if exists MovieProductionCountry;
drop table if exists ProductionCountry;
drop table if exists Scriptwriter;
drop table if exists Movie;
drop table if exists Subscription;

set foreign_key_checks = 1;

-- create schema --
create table Address
(
    id        int primary key auto_increment not null,
    civic_no  int,
    city      varchar(100),
    street    varchar(100),
    province  varchar(100),
    post_code varchar(100)
);

create table User
(
    id         int primary key auto_increment not null,
    username   varchar(100),
    password   varchar(100),
    first_name varchar(100),
    last_name  varchar(100),
    phone_no   varchar(100),
    address_id int references Address (id)
);

create table People
(
    id            int primary key auto_increment not null,
    name          varchar(255),
    dob           date,
    bio           text,
    photo         text,
    birth_city    varchar(100),
    birth_state   varchar(100),
    birth_country varchar(100)
);

create table MovieLanguages
(
    id   int primary key auto_increment NOT NULL,
    name varchar(50)
);

create table Movie
(
    id              int primary key auto_increment NOT NULL,
    title           varchar(100),
    publishing_year int,
    duration        int,
    synopsis        text,
    cover           text,
    language_id     int references MovieLanguages(id),
    director_id     int references People(id)
);

create table Scriptwriter
(
    id   int primary key auto_increment NOT NULL,
    name varchar(100),
    movie_id int references Movie(id)
);

create table PersonRolePlayed
(
    person_id    int references People (id),
    movie_id     int references Movie (id),
    character_name varchar(100),
    primary key (movie_id, person_id)
);

create table ProductionCountry
(
    id   int primary key auto_increment NOT NULL,
    name varchar(100)
);

create table MovieProductionCountry
(
    country_id int references ProductionCountry (id),
    movie_id   int references Movie (id),
    primary key (movie_id, country_id)
);

create table Genre
(
    id   int primary key auto_increment NOT NULL,
    name varchar(50)
);

create table MovieGenre
(
    movie_id int references Movie (id),
    genre_id int references Genre (id),
    primary key (movie_id, genre_id)
);

create table Subscription
(
    id           int primary key auto_increment NOT NULL,
    code         varchar(3),
    cost         int,
    flat_rate    varchar(100),
    max_rentals  int,
    max_duration int
);

create table CreditCard
(
    id                    int primary key auto_increment NOT NULL,
    no                    int,
    type                  varchar(50),
    expiration_date_month int,
    expiration_date_year  int,
    cvv                   int
);

create table Employee
(
    id      int primary key auto_increment NOT NULL,
    user_id int references User (id)
);

create table Customer
(
    id             int primary key auto_increment NOT NULL,
    user_id        int references User (id),
    credit_card_id int references CreditCard (id)
);

create table CustomerSubscription
(
    id              int primary key auto_increment NOT NULL,
    start_date      date,
    end_date        date,
    subscription_id int references Subscription (id),
    customer_id     int references Customer (id)
);

create table Rentals
(
    id            int primary key auto_increment NOT NULL,
    borrowed_date date,
    return_date   date,
    user_id       int references User (id),
    movie_id      int references Movie (id)
);

create table SubscriptionPayment
(
    id                       int primary key auto_increment NOT NULL,
    payment_date             date,
    payment_amount           double,
    payment_month            int,
    payment_year             int,
    customer_subscription_id int references CustomerSubscription (id)
);

create table MovieDVD
(
    id       int primary key auto_increment NOT NULL,
    movie_id int references Movie (id)
);

create table Trailer
(
    id       int primary key auto_increment NOT NULL,
    link     text,
    movie_id int references Movie (id)
);



