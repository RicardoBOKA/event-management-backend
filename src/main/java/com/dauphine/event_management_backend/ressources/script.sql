-- Création de la table Users
CREATE TABLE users (
                       user_id UUID PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE
);

-- Création de la table Events
CREATE TABLE events (
                        event_id UUID PRIMARY KEY,
                        event_name VARCHAR(255) NOT NULL,
                        created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                        start_event TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                        end_event TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                        location VARCHAR(255) NOT NULL,  -- Assumption: Location is stored as a simple string.
                        description TEXT,
                        organizer_id UUID NOT NULL,
                        FOREIGN KEY (organizer_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Création de la table Registrations
CREATE TABLE registrations (
                               registration_id UUID PRIMARY KEY,
                               user_id UUID NOT NULL,
                               event_id UUID NOT NULL,
                               registration_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                               FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
                               FOREIGN KEY (event_id) REFERENCES events(event_id) ON DELETE CASCADE
);

-- Création de la table Feedbacks
CREATE TABLE feedbacks (
                           feedback_id UUID PRIMARY KEY,
                           user_id UUID NOT NULL,
                           event_id UUID NOT NULL,
                           comment TEXT,
                           rating SMALLINT CHECK (rating BETWEEN 1 AND 5),
                           FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
                           FOREIGN KEY (event_id) REFERENCES events(event_id) ON DELETE CASCADE
);
