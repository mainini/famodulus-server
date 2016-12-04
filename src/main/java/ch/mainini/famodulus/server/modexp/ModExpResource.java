/*
 * Copyright 2016 Pascal Mainini
 * Licensed under MIT license, see included file LICENSE or
 * http://opensource.org/licenses/MIT
 */
package ch.mainini.famodulus.server.modexp;

import java.math.BigInteger;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Resource providing the ModExp functionality.
 * @author Pascal Mainini
 */
@Path("modexp")
public class ModExpResource {

//////////////////////////////////////// Constants

    private static final Logger LOG = Logger.getLogger(ModExpResource.class.getName());


//////////////////////////////////////// Methods

    /**
     * This method implements the API for querying modexps.
     * @param query The query containing one or multiple modexps to calculate
     * @return The result of the calculations
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ModExpQueryBean query(ModExpQueryBean query) {
        final BigInteger defaultModulus = query.getModulus();
        final BigInteger defaultBase = query.getBase();
        final BigInteger defaultExponent = query.getExponent();
        final boolean briefResponse = query.getBrief();

        for(ModExpBean modexp: query.getModexps()) {
            final BigInteger m = modexp.getModulus() != null ? modexp.getModulus(): defaultModulus;
            final BigInteger b = modexp.getBase() != null ? modexp.getBase() : defaultBase;
            final BigInteger e = modexp.getExponent() != null ? modexp.getExponent() : defaultExponent;

            final BigInteger r = b.modPow(e, m);
            modexp.setResult(r);
            LOG.finest(String.format("Calculated modexp, m: %s, b: %s, e: %s, r: %s ...", m, b, e, r));

            if(briefResponse) {
                modexp.setModulus(null);
                modexp.setBase(null);
                modexp.setExponent(null);
            }
        }

        if(briefResponse) {
            query.setModulus(null);
            query.setBase(null);
            query.setExponent(null);
            query.setBrief(null);
        }

        return query;
    }
}