CREATE TABLE posts (
    id INT NOT NULL AUTO_INCREMENT,
    topic_id INT,
    author_id INT,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE topics (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE users (
    id INT NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

ALTER TABLE posts
ADD CONSTRAINT fk_posts_topic
FOREIGN KEY (topic_id)
REFERENCES topics(id)
ON DELETE CASCADE;

CREATE TABLE subscriptions (
    user_id INT NOT NULL,
    topic_id INT NOT NULL,
    PRIMARY KEY (user_id, topic_id),
    CONSTRAINT fk_subscription_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_subscription_topic
        FOREIGN KEY (topic_id)
        REFERENCES topics(id)
        ON DELETE CASCADE
);
