package service.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.Connection;
import db.enitites.Phonebook;

public class PhonebookRepository {
    private List<String> allowedFields = List.of("id", "name", "telefone", "email", "user_id");
    Connection conn;

    public PhonebookRepository(Connection conn) {
        this.conn = conn;
    }

    public List<Phonebook> get(String params) {
        List<Phonebook> phonebooks = new ArrayList<>();
        if (!allowedFields.contains(params) && !params.equals("*")) {
            throw new IllegalArgumentException("Campo inválido: " + params);
        }
        String sql = params.equals("*") ? "SELECT * FROM phonebook" : "SELECT " + params + " FROM phonebook";
        try {
            PreparedStatement stmt = conn.getConnection().prepareStatement(sql);
            // stmt.setString(1, params);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Phonebook phonebook = new Phonebook(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getInt("user_id")
                );
                phonebooks.add(phonebook);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phonebooks;
    }

    public List<Phonebook> get() {
        return get("*");
    }

    public List<Phonebook> getById(String params, int idUser) {
        List<Phonebook> phonebooks = new ArrayList<>();
        if (!allowedFields.contains(params) && !params.equals("*")) {
            throw new IllegalArgumentException("Campo inválido: " + params);
        }
        String sql = params.equals("*") ? "SELECT * FROM phonebook WHERE id = ?" : "SELECT " + params + " FROM phonebook WHERE id = ?";
        try {
            PreparedStatement stmt = conn.getConnection().prepareStatement(sql);
            stmt.setInt(1, idUser);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Phonebook phonebook = new Phonebook(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getInt("user_id")
                );
                phonebooks.add(phonebook);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phonebooks;
    }

    public List<Phonebook> getById(int id) {
        return getById("*", id);
    }

    public List<Phonebook> getByIdUser(String params, int idUser) {
        List<Phonebook> phonebooks = new ArrayList<>();
        if (!allowedFields.contains(params) && !params.equals("*")) {
            throw new IllegalArgumentException("Campo inválido: " + params);
        }
        String sql = params.equals("*") ? "SELECT * FROM phonebook WHERE user_id = ?" : "SELECT " + params + " FROM phonebook WHERE user_id = ?";
        try {
            PreparedStatement stmt = conn.getConnection().prepareStatement(sql);
            stmt.setInt(1, idUser);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Phonebook phonebook = new Phonebook(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getInt("user_id")
                );
                phonebooks.add(phonebook);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phonebooks;
    }

    public List<Phonebook> getByIdUser(int id) {
        return getByIdUser("*", id);
    }

    public void create(String name, String telefone, String email, int userId) {
        String sql = "INSERT INTO phonebook (name, telefone, email, user_id) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.getConnection().prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, telefone);
            stmt.setString(3, email);
            stmt.setInt(4, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, String name, String telefone, String email) {
        String sql = "UPDATE phonebook SET name = ?, telefone = ?, email = ? WHERE id = ?";
        try {
            PreparedStatement stmt = conn.getConnection().prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, telefone);
            stmt.setString(3, email);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM phonebook WHERE id = ?";
        try {
            PreparedStatement stmt = conn.getConnection().prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}