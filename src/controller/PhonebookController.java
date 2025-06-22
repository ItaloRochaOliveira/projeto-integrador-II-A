package controller;

import java.util.List;

import db.enitites.Phonebook;
import models.APIConection.Handler;
import models.APIConection.Request;
import models.APIConection.Response;
import service.PhonebookService;

public class PhonebookController {
    PhonebookService phonebookService;

    public PhonebookController(PhonebookService phonebookService){
        this.phonebookService = phonebookService;
    }

    public interface RouteHandler {
        void handle(Request req, Response res) throws Exception;
    }

    public Handler get() {
        return (Request req, Response res) -> {
            System.out.print(req.body);
            try {
                String[] partes = req.path.split("/");
                if (partes.length == 3 ) {

                    if(partes[2].isEmpty()) throw new Exception("id do usuário não informado");

                    int id = Integer.parseInt(partes[2]);
                    res.send(200, toJSON(phonebookService.getByIdUser(id)));
                } else { 
                    res.send(200, toJSON(phonebookService.get()));
                } 
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    res.send(400, "{\"erro\": \"" + e.getMessage() + "\" }");
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
                String telefone = extractJsonString(body, "telefone");
                String email = extractJsonString(body, "email");
                Integer userId = extractJsonInt(body, "userId");
                phonebookService.post(name, telefone, email, userId);
                res.send(201, "{\"message\": \"Contato criado com sucesso\"}");
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    res.send(400, "{\"erro\": \"" + e.getMessage() + "\" }");
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
                if (partes.length < 3) throw new Exception("Caminho inválido");
                int phonebookId = Integer.parseInt(partes[2]);
                String body = req.body;
                String name = extractJsonString(body, "name");
                String telefone = extractJsonString(body, "telefone");
                String email = extractJsonString(body, "email");
                phonebookService.put(phonebookId, name, telefone, email);
                res.send(200, "{\"message\": \"Contato atualizado com sucesso\"}");
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    res.send(400, "{\"erro\": \"" + e.getMessage() + "\" }");
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
                if (partes.length < 3) throw new Exception("Caminho inválido");
                int phonebookId = Integer.parseInt(partes[2]);
                phonebookService.delete(phonebookId);
                res.send(200, "{\"message\": \"Contato removido com sucesso\"}");
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    res.send(400, "{\"erro\": \"" + e.getMessage() + "\" }");
                } catch (java.io.IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        };
    };

    public interface JsonSerializable {
        String toJson();
    }

    public static String toJSON(List<?> lista){
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < lista.size(); i++) {
            Object item = lista.get(i);
            if (item == null) {
                sb.append("null");
            } else if (item instanceof Phonebook) {
                Phonebook pb = (Phonebook) item;
                sb.append(String.format(
                    "{\"id\":%d,\"name\":\"%s\",\"telefone\":\"%s\",\"email\":\"%s\",\"user_id\":%d}",
                    pb.getId(),
                    pb.getName().replace("\"", "\\\""),
                    pb.getTelefone().replace("\"", "\\\""),
                    pb.getEmail().replace("\"", "\\\""),
                    pb.getUserId()
                ));
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

    public Integer extractJsonInt(String json, String key) throws Exception {
        if (!json.contains("\"" + key + "\"")) return null;
        String valor = extractJsonString(json, key);
        if (valor == null || valor.isBlank()) return null;
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
