package db.enitites;

public class Phonebook {
    private int id;
    private String name;
    private String telefone;
    private String email;
    private int userId;

    public Phonebook(int id, String name, String telefone, String email, int userId) {
        this.id = id;
        this.name = name;
        this.telefone = telefone;
        this.email = email;
        this.userId = userId;
    }

    

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
