
CREATE TABLE weather(
                        id bigint NOT NULL AUTO_INCREMENT,
                        time datetime NOT NULL,
                        temperature float,
                        location_id bigint NOT NULL,
                        create_time datetime NOT NULL,
                        update_time datetime NOT NULL,
                        primary key (id),
                        unique key (time, location_id)
) ;
CREATE TABLE location(
                         id bigint NOT NULL AUTO_INCREMENT,
                         lon varchar(255) NOT NULL,
                         lat varchar(255) NOT NULL,
                         create_time datetime NOT NULL,
                         update_time datetime NOT NULL,
                         primary key (id),
                         unique key (lon, lat)
)
