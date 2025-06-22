import controller.PhonebookController;
import controller.UserController;
import db.Connection;
import server.ServerCRUD;
import service.PhonebookService;
import service.UserService;
import service.repository.PhonebookRepository;
import service.repository.UserRepository;

public class Server {
    public static void main(String[] args) throws Exception {
        PhonebookController phonebookController = new PhonebookController(
            new PhonebookService(
                new PhonebookRepository(
                    new Connection()
                )
            )
        );
        UserController userController = new UserController(
            new UserService(
                new UserRepository(
                    new Connection()
                )
            )
        );

        ServerCRUD serverCRUD = new ServerCRUD();

        serverCRUD.get("/usuarios", userController.get());

        serverCRUD.get("/contato", phonebookController.get());

        serverCRUD.post("/usuarios", userController.post());

        serverCRUD.post("/contato", phonebookController.post());

        serverCRUD.start();
    }
};
