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
    f_name        varchar(100),
    l_name        varchar(100),
    dob           int,
    bio           text,
    photo         mediumblob,
    birth_city    varchar(100),
    birth_country varchar(100),
    birth_state   varchar(100)
);

create table Roles
(
    id   int primary key auto_increment NOT NULL,
    name varchar(100)
);

create table Characters
(
    id   int primary key auto_increment NOT NULL,
    name varchar(100)
);

create table Movie
(
    id              int primary key auto_increment NOT NULL,
    title           varchar(100),
    publishing_year int,
    language        varchar(100),
    duration        int,
    synopsis        text,
    cover           blob
);

create table PersonRolePlayed
(
    person_id    int,
    role_id      int,
    movie_id     int,
    character_id int,
    primary key (role_id, movie_id, person_id),
    foreign key (person_id) references People (id),
    foreign key (movie_id) references Movie (id),
    foreign key (role_id) references Roles (id),
    foreign key (character_id) references Characters (id)
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



