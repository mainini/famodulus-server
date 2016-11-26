/*
 * Copyright 2016 Pascal Mainini
 * Licensed under MIT license, see included file LICENSE or
 * http://opensource.org/licenses/MIT
 */
package ch.mainini.famodulus;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Main class, starts the server and adds the resources.
 * @author Pascal Mainini
 */
public class Server {

//////////////////////////////////////// Constants

    /**
     * Base URI on which the server listens
     * @todo make configurable
     */
    public static final String BASE_URI = "http://localhost:8080/";

    /**
     * Path to the RESTful API
     * @todo make configurable
     */
    public static final String API_URI = BASE_URI + "api";

    private static final Logger LOG = Logger.getLogger(Server.class.getName());


//////////////////////////////////////// Methods

    /**
     * Main method, starts the server.
     * @param args Arguments given by the JVM
     * @throws IOException In case something went wrong
     */
    public static void main(String[] args) throws IOException {
        LOG.info("Configuring and starting webserver...");
        final HttpServer server = startServer();
        LOG.info(String.format("Webserver running at %s !", BASE_URI));
        LOG.fine(String.format("WADL available at %sapplication.wadl", API_URI));

        LOG.info("Hit enter to stop it...");
        System.in.read();

        server.shutdownNow();
    }

    /**
     * Starts the Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        LOG.fine("Adding JAX-RS resources...");
        final ResourceConfig resourceConfig = new ResourceConfig().packages("ch.mainini.famodulus");

        LOG.fine("Setting up handler for static content...");   // @todo make configurable
        final HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(API_URI), resourceConfig);
        final CustomStaticHttpHandler httpHandler = new CustomStaticHttpHandler();
        httpServer.getServerConfiguration().addHttpHandler(httpHandler, "/");

        return httpServer;
    }
}