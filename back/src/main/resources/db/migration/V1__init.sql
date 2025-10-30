CREATE TABLE posts (
    id INT NOT NULL AUTO_INCREMENT,
    topic_id INT,
    author_id INT,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
