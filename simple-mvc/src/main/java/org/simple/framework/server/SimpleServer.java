package org.simple.framework.server;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class SimpleServer implements HttpHandler {

    private Integer port;
    private ExecutorService executorService;
    private HttpServer httpServer;
    private Map<String, Method> controllerEndpoints;
    
    public SimpleServer(int port, ExecutorService executorService, Map<String, Method> controllerEndpoints) {
        this.port = port;
        this.executorService = executorService;
        this.controllerEndpoints = controllerEndpoints;
    }
    
    public void start() throws IOException {
        this.httpServer = HttpServer.create(
            new InetSocketAddress(this.port),
            0
        );
        this.httpServer.setExecutor(this.executorService);
        this.httpServer.createContext("/admin", this);
        controllerEndpoints.keySet().forEach(f -> this.httpServer.createContext(f, this));

        System.out.println("Server started at http://localhost:"+port);
        this.httpServer.start();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println(exchange.getRequestURI());
        // execute corresponding method to serve the request
        String response = "Hello, World!";
        exchange.sendResponseHeaders(200, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

}
