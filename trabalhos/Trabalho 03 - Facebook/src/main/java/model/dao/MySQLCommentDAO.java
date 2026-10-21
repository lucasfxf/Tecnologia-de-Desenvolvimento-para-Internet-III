
package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.Comment;
import model.ModelException;

public class MySQLCommentDAO implements CommentDAO {

    @Override
    public boolean save(Comment comment) throws ModelException {
        DBHandler db = new DBHandler();

        String sql = "INSERT INTO comments (content, post_id) VALUES (?, ?)";

        db.prepareStatement(sql);
        db.setString(1, comment.getContent());
        db.setInt(2, comment.getPostId());

        return db.executeUpdate() > 0;
    }

    @Override
    public List<Comment> listAll() throws ModelException {
        DBHandler db = new DBHandler();
        List<Comment> comments = new ArrayList<>();

        String sql = "SELECT * FROM comments ORDER BY id DESC";

        db.createStatement();
        db.executeQuery(sql);

        while (db.next()) {
            Comment comment = new Comment();
            comment.setId(db.getInt("id"));
            comment.setContent(db.getString("content"));
            comment.setPostId(db.getInt("post_id"));

            comments.add(comment);
        }

        return comments;
    }
    

    @Override
    public boolean delete(Comment comment) throws ModelException {
        DBHandler db = new DBHandler();

        String sql = "DELETE FROM comments WHERE id = ?";

        db.prepareStatement(sql);
        db.setInt(1, comment.getId());

        return db.executeUpdate() > 0;
    }

}
