package programming3.chatsys.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public abstract class AbstractHandler implements HttpHandler {
    public abstract void handle(HttpExchange exchange);

    protected void sendResponse(HttpExchange exchange, int responseCode, String response) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(exchange.getResponseBody()))) {
            exchange.sendResponseHeaders(responseCode, 0);
            writer.write(response);
        } catch (IOException e) {
            System.out.println("Couldn't send response to client.");
        }
        exchange.close();
    }
}
