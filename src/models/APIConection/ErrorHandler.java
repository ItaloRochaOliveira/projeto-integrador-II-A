package models.APIConection;

public interface ErrorHandler {
    void handle(Request req, Response res, Exception e);
}