package controller;

import java.util.List;

import models.APIConection.Handler;
import models.APIConection.Request;
import models.APIConection.Response;
import service.UserService;
import db.enitites.User;

public class UserController {
    UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    public Handler get() {
        return (Request req, Response res) -> {
            try {
                String[] partes = req.path.split("/");
                if (partes.length == 3) { // /usuarios/{id}
                    if (partes[2].isEmpty()) throw new Exception("id do usuário não informado");
                    int id = Integer.parseInt(partes[2]);
                    res.send(200, toJSON(userService.get(id)));
                } else {
                    res.send(200, toJSON(userService.getAll()));
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    res.send(400, "{\"erro\": \"Requisição inválida\"}");
                } catch (java.io.IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        };
    }

    public Handler post() {
        return (Request req, Response res) -> {
            try {
                String body = req.body;
                String name = extractJsonString(body, "name");
                String email = extractJsonString(body, "email");
                userService.post(name, email);
                res.send(201, "{\"message\": \"Usuário criado com sucesso\"}");
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    res.send(400, "{\"erro\": \"Requisição inválida\"}");
                } catch (java.io.IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        };
    }

    public Handler put() {
        return (Request req, Response res) -> {
            try {
                String[] partes = req.path.split("/");
                if (partes.length < 4) throw new Exception("Caminho inválido");
                int userId = Integer.parseInt(partes[3]);
                String body = req.body;
                String name = extractJsonString(body, "name");
                String email = extractJsonString(body, "email");
                userService.put(userId, name, email);
                res.send(200, "{\"message\": \"Usuário atualizado com sucesso\"}");
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    res.send(400, "{\"erro\": \"Requisição inválida\"}");
                } catch (java.io.IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        };
    }

    public Handler delete() {
        return (Request req, Response res) -> {
            try {
                String[] partes = req.path.split("/");
                if (partes.length < 4) throw new Exception("Caminho inválido");
                int userId = Integer.parseInt(partes[3]);
                userService.delete(userId);
                res.send(200, "{\"message\": \"Usuário removido com sucesso\"}");
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    res.send(400, "{\"erro\": \"Requisição inválida\"}");
                } catch (java.io.IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        };
    }

    public static String toJSON(List<?> lista){
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < lista.size(); i++) {
            Object item = lista.get(i);
            if (item == null) {
                sb.append("null");
            } else if (item instanceof User) {
                User user = (User) item;
                sb.append(String.format("{\"id\":%d,\"name\":\"%s\",\"email\":\"%s\"}", user.getId(), user.getName(), user.getEmail()));
            } else {
                sb.append('\"').append(item.toString().replace("\"", "\\\"")).append('\"');
            }
            if (i < lista.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }

    private String extractJsonString(String json, String key) {
        String[] parts = json.split("\"" + key + "\"\\s*:\\s*\"");
        if (parts.length < 2) return null;
        return parts[1].split("\"")[0];
    }
}