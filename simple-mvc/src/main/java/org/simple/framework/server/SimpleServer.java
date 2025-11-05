package org.simple.framework.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.ExecutorService;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class SimpleServer {

    private Integer port;
    private ExecutorService executorService;
    private HttpServer httpServer;
    private List<RestEndpointHandler> restEndpointHandlers;
    
    public SimpleServer(int port, ExecutorService executorService) {
        this.port = port;
        this.executorService = executorService;
    }
    
    public void start() throws IOException {
        this.httpServer = HttpServer.create(
            new InetSocketAddress(this.port),
            0
        );
        this.httpServer.setExecutor(this.executorService);
        restEndpointHandlers.forEach(f -> this.httpServer.createContext(f.getPath(), f.getHttpHandler()));

        System.out.println("Server started at http://localhost:8080");
        this.httpServer.start();
    }

    public List<RestEndpointHandler> addRestEndpointHandler(String path, HttpHandler httpHandler) {
        this.restEndpointHandlers.add(new RestEndpointHandler(path, httpHandler));
        return this.restEndpointHandlers;
    }

    class RestEndpointHandler {
        private String path;
        private HttpHandler httpHandler;

        public RestEndpointHandler(String path, HttpHandler httpHandler) {
            this.path = path;
            this.httpHandler = httpHandler;
        }

        public String getPath() {
            return path;
        }

        public HttpHandler getHttpHandler() {
            return httpHandler;
        }

        public RestEndpointHandler setPath(String path) {
            this.path = path;
            return this;
        } 

        public RestEndpointHandler setHttpHandler(HttpHandler httpHandler) {
            this.httpHandler = httpHandler;
            return this;
        }
    }

}
