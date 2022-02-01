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
drop view if exists v_users;
drop view if exists v_movie_details;
drop view if exists v_my_rentals;
drop function if exists get_user_age_in_years;
drop function if exists calculate_age;
drop function if exists is_card_expired;
drop procedure if exists p_rent_movie;
drop procedure if exists p_return_movie;
drop procedure if exists p_insert_movie;
drop trigger if exists t_check_user_age;
drop trigger if exists t_validate_credit_card;
drop trigger if exists t_generate_movie_dvd;
drop trigger if exists t_check_username_unique;


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
    cvv                   int,
    status                enum ('valid', 'expired')
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
    birth_date     date,
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

-- create views
create view v_users as
select u.id,
       u.user_type,
       u.username,
       concat(u.first_name, ', ', u.last_name) as full_name,
       u.phone_no,
       u.birth_date,
       a.civic_no,
       a.street,
       a.city,
       a.province,
       a.post_code,
       c.no,
       c.type,
       c.expiration_date_month,
       c.expiration_date_year
from User as u
         inner join Address as a on u.address_id = a.id
         inner join CreditCard as c on u.credit_card_id = c.id;

create view v_movie_details as
select m.title,
       m.publishing_year,
       m.duration,
       m.synopsis,
       m.cover,
       g.name as genre,
       p.name as actor,
       s.name as scriptwriter,
       t.link as trailer
from Movie as m
         inner join People as p on m.director_id = p.id
         inner join MovieGenre as mg on m.id = mg.movie_id
         inner join Genre as g on mg.genre_id = g.id
         inner join Scriptwriter as s on m.id = s.movie_id
         inner join Trailer as t on m.id = t.movie_id;

create view v_my_rentals as
select u.username as user,
       m.title    as title,
       r.borrowed_date
from Rentals as r
         inner join MovieDVD as md on r.movie_dvd_id = md.id
         inner join Movie as m on md.movie_id = m.id
         inner join User u on r.user_id = u.id;

-- TODO create view actor played in movies

-- create functions

# create function get_

-- calculate user age
delimiter //
create function calculate_age(
    birth_date date
) returns int
begin
    declare user_age int;
    select TIMESTAMPDIFF(year, birth_date, curdate()) into user_age;
    return user_age;
end //
delimiter ;

delimiter //
create function is_card_expired(
    exp_year int,
    exp_month int
) returns bool
begin
    if exp_year > year(curdate()) then
        return false;
    end if;

    if exp_year < year(curdate()) then
        return true;
    end if;

    if exp_month > month(curdate()) then
        return true;
    end if;

    return false;
end //
delimiter ;

-- procedures
delimiter //
create procedure p_rent_movie(
    in id_user int,
    in id_movie int
)
begin
    declare id_dvd int;

    start transaction;

    select id into id_dvd from MovieDVD where movie_id = id_movie limit 1;

    insert into Rentals (borrowed_date, return_date, user_id, movie_dvd_id)
    values (curdate(), null, id_user, id_dvd);

    update MovieDVD set movie_dvd_status = 'rented' where id = id_dvd;
    commit;
end //
delimiter ;

delimiter //
create procedure p_return_movie(
    in id_user int,
    in id_dvd int
)
begin
    start transaction;

    update MovieDVD set movie_dvd_status = 'available' where id = id_dvd;

    update Rentals
    set return_date = curdate()
    where user_id = id_user
      and movie_dvd_id = id_dvd;
    commit;
end //
delimiter ;

-- triggers
-- trigger for user insert age check > 18
delimiter //
create trigger if not exists t_check_user_age
    before insert
    on User
    for each row
begin
    declare age int;

    select calculate_age(NEW.birth_date) into age;

    if (age < 18) then
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'User age must be greater than 18 years old';
    end if;
end;
//
delimiter ;

-- trigger for validating credit card expiration date
delimiter //
create trigger if not exists t_validate_credit_card
    before insert
    on CreditCard
    for each row
begin
    if is_card_expired(NEW.expiration_date_year, NEW.expiration_date_month) then
        set NEW.status = 'expired';
    else
        set NEW.status = 'valid';
    end if;
end;
//
delimiter ;

delimiter //
create trigger if not exists t_generate_movie_dvd
    after insert
    on Movie
    for each row
begin
    declare movie_id int;
    declare random_dvds int;
    declare dvd_index int;

    select id into movie_id from Movie where id = NEW.id;
    select rand() * (100 - 1) + 1 into random_dvds;

    set dvd_index = 1;

    insertMovieDVDs:
    while dvd_index <= random_dvds
        do
            insert into MovieDVD (movie_id, movie_dvd_status) values (movie_id, 'available');
            set dvd_index = dvd_index + 1;
        end while insertMovieDVDs;
end;
//
delimiter ;

-- TODO: create trigger check if the movie dvd is available before rental

delimiter //
create trigger if not exists t_check_movie_dvd_availability
    before insert
    on Rentals
    for each row
begin
    declare movie_dvd_status enum ('rented','available');

    select movie_dvd_status into movie_dvd_status from MovieDVD where MovieDVD.id = NEW.movie_dvd_id;

    if strcmp(movie_dvd_status, 'rented') then
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Movie dvd is not available';
    end if;
end;
//
delimiter ;

delimiter //
create trigger if not exists t_check_username_unique
    before insert
    on User
    for each row
begin
    declare existing_username varchar(100) default null;

    select count(*)
    into existing_username
    from User
    where NEW.username = User.username;

    if (existing_username > 0) then
        signal sqlstate '45000'
            set message_text = 'This username already exists!';
    end if;
end //
delimiter ;
