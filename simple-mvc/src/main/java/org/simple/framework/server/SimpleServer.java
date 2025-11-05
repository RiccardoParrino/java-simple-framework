package org.simple.framework.server;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.simple.framework.beans.ApplicationContext;

import com.parrino.riccardo.Tuple;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class SimpleServer implements HttpHandler {

    private Integer port;
    private ExecutorService executorService;
    private HttpServer httpServer;
    private Map<String, Tuple<Method,Object>> controllerEndpoints;
    private ApplicationContext applicationContext;
    
    public SimpleServer(ApplicationContext applicationContext, int port, ExecutorService executorService, Map<String, Tuple<Method,Object>> controllerEndpoints) {
        this.port = port;
        this.executorService = executorService;
        this.controllerEndpoints = controllerEndpoints;
        this.applicationContext = applicationContext;
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
        System.out.println(exchange.getRequestMethod() + " " + exchange.getRequestURI());

        // execute corresponding method to serve the request
        Tuple<Method,Object> endpoint = controllerEndpoints.get(exchange.getRequestURI().toString());
        Method method = endpoint.getKey();
        Object object = endpoint.getValue();
        Object response = null;
        try {
            response = method.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException | RuntimeException e) {
            e.printStackTrace();
        }

        String responseAsString = (String) response;
        exchange.sendResponseHeaders(200, responseAsString.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseAsString.getBytes());
        }
    }

}
