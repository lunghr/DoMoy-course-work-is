CREATE OR REPLACE FUNCTION create_post(p_title VARCHAR(255), p_content VARCHAR, p_filename VARCHAR, p_author VARCHAR(255))
    RETURNS TABLE (id INT, title VARCHAR(255), content VARCHAR, created_at TIMESTAMP, filename VARCHAR,
                   author VARCHAR(255)) AS $$
BEGIN
    RETURN QUERY INSERT INTO posts (title, content, created_at, filename, author)
    VALUES (p_title, p_content, NOW(), p_filename, p_author)
    RETURNING posts.id, posts.title, posts.content, posts.created_at, posts.filename, posts.author;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION find_post_by_id(p_post_id INT)
    RETURNS TABLE (id INT, title VARCHAR(255), content VARCHAR, created_at TIMESTAMP, filename VARCHAR,
                   author VARCHAR(255)) AS $$

BEGIN
    RETURN QUERY SELECT posts.id, posts.title, posts.content, posts.created_at, posts.filename, posts.author
                 FROM posts
                 WHERE posts.id = p_post_id;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION update_post(p_post_id INT, p_title VARCHAR(255), p_content VARCHAR, p_filename VARCHAR)
    RETURNS TABLE (id INT, title VARCHAR(255), content VARCHAR, created_at TIMESTAMP, filename VARCHAR,
                   author VARCHAR(255)) AS $$
BEGIN
    RETURN QUERY UPDATE posts
    SET title = p_title, content = p_content, filename = p_filename
    WHERE posts.id = p_post_id
    RETURNING posts.id, posts.title, posts.content, posts.created_at, posts.filename, posts.author;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION delete_post(p_post_id INT)
    RETURNS TABLE (id INT, title VARCHAR(255), content VARCHAR, created_at TIMESTAMP, filename VARCHAR,
                   author VARCHAR(255)) AS $$
BEGIN
    RETURN QUERY DELETE FROM posts
    WHERE posts.id = p_post_id
    RETURNING posts.id, posts.title, posts.content, posts.created_at, posts.filename, posts.author;
END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION find_all_posts()
    RETURNS TABLE (id INT, title VARCHAR(255), content VARCHAR, created_at TIMESTAMP, filename VARCHAR,
                   author VARCHAR(255)) AS $$
BEGIN
    RETURN QUERY SELECT posts.id, posts.title, posts.content, posts.created_at, posts.filename, posts.author
                 FROM posts;
END;
$$ LANGUAGE plpgsql;

