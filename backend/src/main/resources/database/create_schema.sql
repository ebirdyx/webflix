-- drop all tables --
set foreign_key_checks = 0;

drop table if exists Address;
drop table if exists User;
drop table if exists CreditCard;
drop table if exists PersonRolePlayed;
drop table if exists CustomerSubscription;
drop table if exists MovieDVD;
drop table if exists Trailer;
drop table if exists SubscriptionPayment;
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
drop procedure if exists InsertMovie;

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

create table CreditCard
(
    id                    int primary key auto_increment                 not null,
    no                    text,
    type                  enum ('MasterCard', 'Visa', 'AmericanExpress') not null,
    expiration_date_month int,
    expiration_date_year  int,
    cvv                   int
);

create table User
(
    id             int primary key auto_increment not null,
    user_type      enum ('Customer', 'Employee')  not null,
    username       varchar(100),
    password       text,
    first_name     varchar(100),
    last_name      varchar(100),
    phone_no       varchar(100),
    address_id     int references Address (id),
    credit_card_id int references CreditCard (id)
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
    id   int primary key auto_increment not null,
    name varchar(50)
);

create table Movie
(
    id              int primary key auto_increment not null,
    title           varchar(100),
    publishing_year int,
    duration        int,
    synopsis        text,
    cover           text,
    language_id     int references MovieLanguages (id),
    director_id     int references People (id)
);

create table Scriptwriter
(
    id       int primary key auto_increment not null,
    name     varchar(100),
    movie_id int references Movie (id)
);

create table PersonRolePlayed
(
    id             int primary key auto_increment not null,
    character_name varchar(100),
    person_id      int references People (id),
    movie_id       int references Movie (id)
);

create table ProductionCountry
(
    id   int primary key auto_increment not null,
    name varchar(100)
);

create table MovieProductionCountry
(
    id         int primary key auto_increment not null,
    country_id int references ProductionCountry (id),
    movie_id   int references Movie (id)
);

create table Genre
(
    id   int primary key auto_increment not null,
    name varchar(50)
);

create table MovieGenre
(
    id       int primary key auto_increment not null,
    movie_id int references Movie (id),
    genre_id int references Genre (id)
);

create table Subscription
(
    id           int primary key auto_increment not null,
    name         varchar(100),
    code         varchar(3),
    cost         float(10, 2),
    max_rentals  int,
    max_duration int
);

create table CustomerSubscription
(
    id              int primary key auto_increment not null,
    start_date      date,
    end_date        date,
    subscription_id int references Subscription (id),
    customer_id     int references User (id)
);

create table MovieDVD
(
    id               int primary key auto_increment not null,
    movie_id         int references Movie (id),
    movie_dvd_status enum ('rented', 'available')   not null
);

create table Rentals
(
    id            int primary key auto_increment not null,
    borrowed_date date,
    return_date   date,
    user_id       int references User (id),
    movie_dvd_id  int references MovieDVD (id)
);

create table SubscriptionPayment
(
    id                       int primary key auto_increment not null,
    payment_date             date,
    payment_amount           double,
    payment_month            int,
    payment_year             int,
    customer_subscription_id int references CustomerSubscription (id)
);

create table Trailer
(
    id       int primary key auto_increment not null,
    link     text,
    movie_id int references Movie (id)
);

-- create functions
-- TODO: split procedure InsertMovie into multiple functions

-- create procedures
-- TODO: use InsertMovie procedure to insert movies from xml in MovieDao
delimiter //
create procedure InsertMovie(
    in id int,
    in title varchar(100),
    in listOfCountries text,
    in language varchar(50),
    in duration int,
    in synopsis text,
    in listOfGenres text,
    in directorId int,
    in directorName varchar(255),
    in listOfScriptwriters text,
    in listOfActors text,
    in cover text,
    in listOfTrailers text
)
begin
    declare scriptwriter varchar(100);
    declare trailer text;
    declare actor text;
    declare actor_character_name varchar(100);
    declare actor_person_id int;

    insertScriptwriters:
    while char_length(listOfScriptwriters) > 0
        do
            set scriptwriter = substring_index(listOfScriptwriters, ';', 1);
            insert into Scriptwriter (name, movie_id) values (scriptwriter, id);
            set listOfScriptwriters = substring(listOfScriptwriters, locate(';', listOfScriptwriters));
        end while insertScriptwriters;

    insertTrailers:
    while char_length(listOfTrailers) > 0
        do
            set trailer = substring_index(listOfTrailers, ';', 1);
            insert into Trailer (link, movie_id) values (trailer, id);
            set listOfTrailers = substring(listOfTrailers, locate(';', listOfTrailers));
        end while insertTrailers;

    insertActors:
    while char_length(listOfActors) > 0
        do
            set actor = substring_index(listOfActors, ';', 1);
            set actor_character_name = substring_index(actor, ',', 1);
            set actor_person_id = substring_index(actor, ',', 2);

            insert into PersonRolePlayed (character_name, person_id, movie_id)
            values (actor_character_name, actor_person_id, id);

            set listOfActors = substring(listOfActors, locate(';', listOfActors));
        end while insertActors;
    -- TODO: insert movieGenres {UNFINISHED}
    create procedure InsertMovieGenres(
        in id int,
        in movie_id
        in genre_id
    )

    -- TODO: insert movieProductionCountries

    -- TODO: finally insert the movie

    -- TODO: generate movie dvds between 1 and 100 for each movie with default status available

end //
delimiter ;

-- triggers
-- TODO: create trigger for user insert age check > 18
-- TODO: create trigger check for movie dvd is available before rental {UNFINISHIED}
CREATE TRIGGER TR_movies_status ON rental
BEFORE InsertMovie AS
    SELECT movie_dvd_status FROM MovieDVD
GO