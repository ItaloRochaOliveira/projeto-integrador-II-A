package service.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.Connection;
import db.enitites.User;

public class UserRepository {
    private List<String> allowedFields = List.of("id", "name", "email");
    Connection conn;

    public UserRepository(Connection conn) {
        this.conn = conn;
    }

    public List<User> get(String params) {
        List<User> users = new ArrayList<>();
        if (!allowedFields.contains(params) && !params.equals("*")) {
            throw new IllegalArgumentException("Campo inválido: " + params);
        }
        String sql = params.equals("*") ? "SELECT * FROM users" : "SELECT " + params + " FROM users";
        try {
            PreparedStatement stmt = conn.getConnection().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<User> get() {
        return get("*");
    }

    public List<User> getBy(String params, int id) {
        List<User> users = new ArrayList<>();
        if (!allowedFields.contains(params) && !params.equals("*")) {
            throw new IllegalArgumentException("Campo inválido: " + params);
        }
        String sql = params.equals("*") ? "SELECT * FROM users WHERE id = ?" : "SELECT " + params + " FROM users WHERE id = ?";
        try {
            PreparedStatement stmt = conn.getConnection().prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<User> getBy(int id) {
        return getBy("*", id);
    }

    public void create(String name, String email) {
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        try {
            PreparedStatement stmt = conn.getConnection().prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, String name, String email) {
        String sql = "UPDATE users SET name = ?, email = ? WHERE id = ?";
        try {
            PreparedStatement stmt = conn.getConnection().prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setInt(3, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try {
            PreparedStatement stmt = conn.getConnection().prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}