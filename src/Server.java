import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import controller.PhonebookController;
import controller.UserController;
import db.Connection;
import server.ServerCRUD;
import service.PhonebookService;
import service.UserService;
import service.repository.PhonebookRepository;
import service.repository.UserRepository;

public class Server {
    //to not up properties
    public static void main(String[] args) throws Exception {
        String url;
        String userDb;
        String password;
        Properties props = new Properties();

        try (FileInputStream input = new FileInputStream("src/properties/config.properties")) {
            props.load(input);

            url = props.getProperty("db.url");
            userDb = props.getProperty("db.user");
            password = props.getProperty("db.password");
        } catch (IOException e) {
            throw new Exception(e);
        };

        PhonebookController phonebookController = new PhonebookController(
            new PhonebookService(
                new PhonebookRepository(
                    new Connection(url, userDb, password)
                )
            )
        );
        UserController userController = new UserController(
            new UserService(
                new UserRepository(
                    new Connection(url, userDb, password)
                )
            )
        );

        ServerCRUD serverCRUD = new ServerCRUD();

        serverCRUD.get("/usuarios", userController.get());

        serverCRUD.get("/contatos", phonebookController.get());

        serverCRUD.post("/usuarios", userController.post());

        serverCRUD.post("/contatos", phonebookController.post());

        serverCRUD.put("/usuarios", userController.put());

        serverCRUD.put("/contatos", phonebookController.put());

        serverCRUD.delete("/usuarios", userController.delete());

        serverCRUD.delete("/contatos", phonebookController.delete());

        serverCRUD.start();
    }
};
