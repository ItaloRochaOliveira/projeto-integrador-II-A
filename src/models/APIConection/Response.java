package models.APIConection;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import common.enums.HttpStatus;

public class Response {
    private BufferedWriter out;
    public Response(BufferedWriter out) { this.out = out; }
    public void send(int status, String body) throws IOException {
        HttpStatus statusEnum = getReasonPhrase(status);
        String reason = (statusEnum != null) ? statusEnum.name() : "Unknown Status";

        out.write("HTTP/1.1 " + status + " " + reason + "\r\n");
        out.write("Content-Type: application/json; charset=UTF-8\r\n");
        out.write("Content-Length: " + body.getBytes(StandardCharsets.UTF_8).length + "\r\n");
        out.write("\r\n");
        out.write(body);
        out.flush();
    }

   private HttpStatus getReasonPhrase(int statusCode) {
    return switch (statusCode) {
        case 200 -> HttpStatus.OK;
        case 201 -> HttpStatus.CREATED;
        case 202 -> HttpStatus.ACCEPTED;
        case 204 -> HttpStatus.NO_CONTENT;
        case 400 -> HttpStatus.BAD_REQUEST;
        case 401 -> HttpStatus.UNAUTHORIZED;
        case 403 -> HttpStatus.FORBIDDEN;
        case 404 -> HttpStatus.NOT_FOUND;
        case 405 -> HttpStatus.METHOD_NOT_ALLOWED;
        case 500 -> HttpStatus.INTERNAL_SERVER_ERROR;
        case 501 -> HttpStatus.NOT_IMPLEMENTED;
        case 503 -> HttpStatus.SERVICE_UNAVAILABLE;
        default -> null;             // ou lance exceção, se preferir
    };
}
}