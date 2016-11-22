/*
 * Copyright 2016 Pascal Mainini
 * Licensed under MIT license, see included file LICENSE or
 * http://opensource.org/licenses/MIT
 */
package ch.mainini.famodulus;

import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

/**
 * An extended StaticHttpHandler allowing to set additional headers.
 * @author Pascal Mainini
 */
public class CustomStaticHttpHandler extends CLStaticHttpHandler {

//////////////////////////////////////// Constructors

    /**
     * Adds resources starting at the location of the ch.mainini.famodulus.Main-class.
     * This way, external resources can easily be added using maven.
     */
    CustomStaticHttpHandler() {
        super(ch.mainini.famodulus.Main.class.getClassLoader(), "/");
        setFileCacheEnabled(false);    // @todo only enable in debug-mode
    }


//////////////////////////////////////// Methods

    /**
     * Handles incoming requests. Contains a filter to avoid serving class-files and
     * other resources rooted ad ch.mainini.famodulus.
     *
     * @param uri The request URI
     * @param request The Request
     * @param response The Response
     * @return Returnvalue from parent or false if filtered
     * @throws Exception if parent throws any
     */
    @Override
    protected boolean handle(String uri, Request request, Response response) throws Exception {
        if(uri.contains("ch/mainini/famodulus")) {
            return false;
        }

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        response.addHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type");
        return super.handle(uri, request, response);
    }
}