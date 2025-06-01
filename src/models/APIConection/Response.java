package models.APIConection;

import java.io.BufferedWriter;
import java.io.IOException;

public class Response {
    private BufferedWriter out;
    public Response(BufferedWriter out) { this.out = out; }
    public void send(String status, String body) throws IOException {
        out.write(status);
        out.write("Content-Type: application/json\r\n");
        out.write("Content-Length: " + body.length() + "\r\n");
        out.write("\r\n");
        out.write(body);
        out.flush();
    }
}