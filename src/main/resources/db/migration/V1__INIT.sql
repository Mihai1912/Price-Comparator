CREATE TABLE IF NOT EXISTS products (
                                        id text,
                                        name text,
                                        category text,
                                        brand text,
                                        package_quantity decimal,
                                        package_unit text,
                                        price decimal,
                                        currency text,
                                        market text,
                                        publication_date timestamp,

                                        PRIMARY KEY (id, market, publication_date)
    );

CREATE TABLE IF NOT EXISTS discounts (
                                         id text,
                                         name text,
                                         brand text,
                                         package_quantity decimal,
                                         package_unit text,
                                         product_category text,
                                         from_date timestamp,
                                         to_date timestamp,
                                         percentage_of_discount decimal,
                                         market text,
                                         publication_date timestamp,

                                         PRIMARY KEY (id, market, publication_date)
    );

CREATE TABLE IF NOT EXISTS users (
                                     id int,
                                     username text,
                                     email text,
                                     password text,

                                     PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS user_alerts (
                                           id int,
                                           username text,
                                           product_name text,
                                           price decimal,

                                           PRIMARY KEY (id)
    );