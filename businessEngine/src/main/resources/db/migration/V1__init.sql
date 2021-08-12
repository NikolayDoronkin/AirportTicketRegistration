create table public.user
(
    id           bigserial    not null,
    email        varchar(255) not null,
    last_name    varchar(255) not null,
    user_name    varchar(255) not null,
    password     varchar(255) not null,
    phone_number bigint       not null,
    sur_name     varchar(255) not null,
    role         varchar(255) not null,
    status       varchar(255) not null,
    primary key (id)
);

create table public.airport
(
    id   bigserial    not null,
    city varchar(255) not null,
    name varchar(255) not null,
    primary key (id)
);

create table public.flight
(
    id                   bigserial not null,
    arrival_date         timestamp not null,
    arrival_id           bigint    not null,
    departure_date       timestamp not null,
    departure_id         bigint    not null,
    arrival_airport_id   bigint,
    departure_airport_id bigint,
    primary key (id),
    foreign key (departure_airport_id) references public.airport,
    foreign key (arrival_airport_id) references public.airport
);

create table public.ticket
(
    id        bigserial not null,
    flight_id bigint    not null,
    user_id   bigint    not null,
    primary key (id),
    foreign key (flight_id) references public.flight,
    foreign key (user_id) references public.user
);


