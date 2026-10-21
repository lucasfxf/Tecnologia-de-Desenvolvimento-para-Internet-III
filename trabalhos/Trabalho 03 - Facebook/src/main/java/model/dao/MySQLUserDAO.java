
package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.User;

public class MySQLUserDAO implements UserDAO {

    @Override
    public boolean save(User user) throws ModelException {

        DBHandler db = new DBHandler();

        String sqlInsert = "INSERT INTO users VALUES "
                + "(DEFAULT, ?, ?, ?, ?);";

        db.prepareStatement(sqlInsert);
        db.setString(1, user.getName());
        db.setString(2, user.getGender());
        db.setString(3, user.getEmail());
        db.setString(4, user.getPassword());

        return db.executeUpdate() > 0;
    }

    @Override
    public boolean update(User user) throws ModelException {

        DBHandler db = new DBHandler();

        String sqlUpdate = "UPDATE users "
                + "SET nome = ?, "
                + "sexo = ?, "
                + "email = ?, "
                + "password = ? "
                + "WHERE id = ?";

        db.prepareStatement(sqlUpdate);

        String password = user.getPassword();

        if (password == null || password.equals("")) {
            password = this.findById(user.getId()).getPassword();
        }

        db.setString(1, user.getName());
        db.setString(2, user.getGender());
        db.setString(3, user.getEmail());
        db.setString(4, password);
        db.setInt(5, user.getId());

        return db.executeUpdate() > 0;
    }

    @Override
    public boolean delete(User user) throws ModelException {

        // 1. Exclui os comentários dos posts desse usuário
        DBHandler dbComments = new DBHandler();

        String sqlComments = "DELETE FROM comments "
                + "WHERE post_id IN "
                + "(SELECT id FROM posts WHERE user_id = ?)";

        dbComments.prepareStatement(sqlComments);
        dbComments.setInt(1, user.getId());
        dbComments.executeUpdate();

        // 2. Exclui os posts desse usuário
        DBHandler dbPosts = new DBHandler();

        String sqlPosts = "DELETE FROM posts WHERE user_id = ?";

        dbPosts.prepareStatement(sqlPosts);
        dbPosts.setInt(1, user.getId());
        dbPosts.executeUpdate();

        // 3. Exclui o usuário
        DBHandler dbUser = new DBHandler();

        String sqlUser = "DELETE FROM users WHERE id = ?";

        dbUser.prepareStatement(sqlUser);
        dbUser.setInt(1, user.getId());

        return dbUser.executeUpdate() > 0;
    }

    @Override
    public List<User> listAll() throws ModelException {

        DBHandler db = new DBHandler();

        List<User> users = new ArrayList<User>();

        String sqlQuery = "SELECT * FROM users ORDER BY id";

        db.createStatement();
        db.executeQuery(sqlQuery);

        while (db.next()) {
            User u = createUser(db);
            users.add(u);
        }

        return users;
    }

    @Override
    public User findById(int id) throws ModelException {

        DBHandler db = new DBHandler();

        String sql = "SELECT * FROM users WHERE id = ?";

        db.prepareStatement(sql);
        db.setInt(1, id);
        db.executeQuery();

        User u = null;

        while (db.next()) {
            u = createUser(db);
            break;
        }

        return u;
    }

    private User createUser(DBHandler db) throws ModelException {

        User u = new User(db.getInt("id"));

        u.setName(db.getString("nome"));
        u.setGender(db.getString("sexo"));
        u.setEmail(db.getString("email"));
        u.setPassword("");
        u.setPasswordHash(db.getString("password"));

        return u;
    }

    @Override
    public User findByEmail(String email) throws ModelException {

        DBHandler db = new DBHandler();

        String sql = "SELECT * FROM users WHERE email = ?";

        db.prepareStatement(sql);
        db.setString(1, email);
        db.executeQuery();

        User u = null;

        while (db.next()) {
            u = createUser(db);
            break;
        }

        return u;
    }
}
