/*
 * Copyright 2016 Pascal Mainini
 * Licensed under MIT license, see included file LICENSE or
 * http://opensource.org/licenses/MIT
 */
package ch.mainini.famodulus.server;

import java.util.logging.Logger;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

/**
 * A filter which adds CORS-headers to all responses.
 * @author Pascal Mainini
 */
@Provider
public class CORSResponseFilter implements ContainerResponseFilter {

//////////////////////////////////////// Constants

    private static final Logger LOG = Logger.getLogger(CORSResponseFilter.class.getName());

    private static final String DEFAULT_ALLOW_ORIGIN = "*";

    private final String origin;

//////////////////////////////////////// Constructors

    /**
     * Default constructor, retrieves the origin parameter from system properites
     */
    public CORSResponseFilter() {
        this.origin = System.getProperty("famodulus.allow_origin", DEFAULT_ALLOW_ORIGIN);
        LOG.finest(String.format("Filter instantiated! Allowing origin: %s", origin));
    }


//////////////////////////////////////// Methods

    /**
     * Filter-method which adds all relevant CORS headers to outgoing responses.
     * @param ctx the context of the request
     * @param res the context of the response
     */
    @Override
    public void filter(ContainerRequestContext ctx, ContainerResponseContext res) {
        MultivaluedMap<String, Object> headers = res.getHeaders();
        headers.add("Access-Control-Allow-Origin", origin);
        headers.add("Access-Control-Allow-Methods", "GET, POST");
        headers.add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type");
    }
}