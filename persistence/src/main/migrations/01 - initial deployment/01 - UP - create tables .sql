
begin;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table "Users"
(
	id serial
		constraint users_pk
			primary key,
	first_name varchar not null,
	last_name varchar not null,
	email varchar not null,
	location varchar not null,
	password varchar not null,
	photo varchar,
	type int not null
);

create unique index users_email_uindex
	on "Users" (email);

create table "Article_Categories"
(
	id serial
		constraint article_categories_pk
			primary key,
	description varchar not null
);

create unique index article_categories_description_uindex
	on "Article_Categories" (description);

create table "Articles"
(
	id serial
		constraint articles_pk
			primary key,
	title varchar not null,
	description varchar not null,
	price_per_day decimal not null,
	id_owner serial not null
		constraint articles_users_id_fk
			references "Users"
				on delete cascade,
	id_category serial
		constraint articles_article_categories_id_fk
			references "Article_Categories" (id)
				on delete restrict
);



create table "Article_Pictures"
(
	id uuid default uuid_generate_v4() not null
		constraint article_pictures_pk
			primary key,
	id_article serial not null
		constraint article_pictures_articles_id_fk
			references "Articles" (id)
				on update cascade on delete cascade
);

create table "Rent_Proposals"
(
	id serial not null
		constraint rent_proposals_pk
			primary key,
	start_date date not null,
	end_date date not null,
	message varchar not null,
	approved bool default false,
	id_article serial not null
		constraint rent_proposals_articles_id_fk
			references "Articles" (id)
				on update cascade on delete cascade,
	id_renter serial not null
		constraint rent_proposals_users_id_fk
			references "Users" (id)
				on update cascade on delete cascade
);

create unique index rent_proposals_id_renter_uindex
	on "Rent_Proposals" (id_renter);

commit;
