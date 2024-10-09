CREATE TABLE profiles (
    id SERIAL PRIMARY KEY,
    nickname TEXT NOT NULL UNIQUE,
    profile_photo TEXT NOT NULL,
    user_id INT NOT NULL UNIQUE,
    CONSTRAINT fk_user
        FOREIGN KEY(user_id)
            REFERENCES users(id)
);