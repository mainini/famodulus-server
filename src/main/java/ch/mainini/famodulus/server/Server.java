/*
 * Copyright 2016 Pascal Mainini
 * Licensed under MIT license, see included file LICENSE or
 * http://opensource.org/licenses/MIT
 */
package ch.mainini.famodulus.server;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;
import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
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
     * Default base URI on which the server listens.
     */
    public static final String DEFAULT_BASE_URI = "http://localhost:8081/";

    /**
     * Path for the API.
     * Needs to be adjusted in resources/ch/mainini/famodulus/server/index.html if changed here.
     */
    public static final String API_PATH = "api";

    private static final Logger LOG = Logger.getLogger(Server.class.getName());


//////////////////////////////////////// Methods

    /**
     * Main method, starts the server.
     * @param args Arguments given by the JVM
     * @throws IOException In case something went wrong
     */
    public static void main(String[] args) throws IOException {
        LOG.info("Configuring and starting famodulus-server webserver...");

        final String base = System.getProperty("famodulus.base", DEFAULT_BASE_URI);
        final HttpServer server = startServer(base + API_PATH);

        LOG.info(String.format("Webserver running at %s !", base));
        LOG.fine(String.format("WADL available at %s/application.wadl", base + API_PATH));

        LOG.info("Hit enter to stop it...");
        System.in.read();

        server.shutdownNow();
    }

    /**
     * Starts the Grizzly HTTP server exposing JAX-RS resources defined in this
     * application at DEFAULT_API_URI.
     *
     * @return The running Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        return startServer(DEFAULT_BASE_URI + API_PATH);
    }

    /**
     * Starts the Grizzly HTTP server exposing JAX-RS resources defined in this
     * application at the given URI.
     *
     * @param apiURI Full URI to start the API at
     * @return The running Grizzly HTTP server.
     */
    public static HttpServer startServer(String apiURI) {
        LOG.fine("Adding JAX-RS resources...");
        final ResourceConfig resourceConfig = new ResourceConfig().packages("ch.mainini.famodulus");
        final HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(apiURI), resourceConfig);
        httpServer.getServerConfiguration().addHttpHandler(new CLStaticHttpHandler(Server.class.getClassLoader(), "ch/mainini/famodulus/server/"), "/");

        return httpServer;
    }
}
