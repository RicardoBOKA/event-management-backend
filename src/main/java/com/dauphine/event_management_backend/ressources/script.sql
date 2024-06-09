-- Création de la table 'users'
CREATE TABLE users (
                       user_id UUID PRIMARY KEY,
                       user_name VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE
);

-- Création de la table 'events'
CREATE TABLE events (
                        event_id UUID PRIMARY KEY,
                        event_name VARCHAR(255),
                        created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                        start_event TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                        end_event TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                        location VARCHAR(255),
                        description TEXT,
                        user_id UUID,
                        FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Création de la table 'feedbacks'
CREATE TABLE feedbacks (
                           feedback_id UUID PRIMARY KEY,
                           event_id UUID,
                           user_id UUID,
                           comment TEXT,
                           rating SMALLINT,
                           FOREIGN KEY (event_id) REFERENCES events(event_id),
                           FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Création de la table 'registrations'
CREATE TABLE registrations (
                               registration_id UUID PRIMARY KEY,
                               user_id UUID NOT NULL,
                               event_id UUID NOT NULL,
                               registration_date TIMESTAMP WITHOUT TIME ZONE,
                               FOREIGN KEY (user_id) REFERENCES users(user_id),
                               FOREIGN KEY (event_id) REFERENCES events(event_id)
);
