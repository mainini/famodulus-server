/*
 * Copyright 2016 Pascal Mainini
 * Licensed under MIT license, see included file LICENSE or
 * http://opensource.org/licenses/MIT
 */
package ch.mainini.famodulus.modexp;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * This class contains various helper functions for supporting the unit tests of the famodulus.modexp package.
 * @author Pascal Mainini
 */
class Util {

//////////////////////////////////////// Constants

    /**
     * A safe prime with 1024 bits
     */
    static final BigInteger P_1024 = new BigInteger("80000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001981bf", 16);

    /**
     * A safe prime with 2048 bits
     */
    static final BigInteger P_2048 = new BigInteger("800000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000ad3af", 16);

    /**
     * A safe prime with 3072 bits
     */
    static final BigInteger P_3072 = new BigInteger("8000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000006119df", 16);

    private static final SecureRandom RND = new SecureRandom();


//////////////////////////////////////// Methods

    /**
     * This method randomly choses a base and an exponent with numbits and calculates a modexp using the given modulus for these values.
     * @param modulus the modulus to use
     * @param numbits number of bits to use for random number generation
     * @return Array with [0] = modulus, [1] = base, [2] = exponent and [3] = modexp
     */
    static BigInteger[] randomModexp(BigInteger modulus, int numbits) {
        BigInteger[] modexp = new BigInteger[4];
        modexp[0] = modulus;
        modexp[1] = new BigInteger(numbits, RND);
        modexp[2] = new BigInteger(numbits, RND);
        modexp[3] = modexp[1].modPow(modexp[2], modulus);
        return modexp;
    }

    /**
     * Helper method creating the JSON string for a single modexp with or without given result.
     * @param modexp the modexp to serialize
     * @param withResult true to include result, false to omit
     * @return A JSON representation of modexp
     */
    static String serializeModexp(BigInteger[] modexp, boolean withResult) {
        return withResult ? String.format("{\"m\":\"%s\",\"b\":\"%s\",\"e\":\"%s\",\"r\":\"%s\"}", modexp[0].toString(16), modexp[1].toString(16), modexp[2].toString(16), modexp[3].toString(16)) :
            String.format("{\"m\":\"%s\",\"b\":\"%s\",\"e\":\"%s\"}", modexp[0].toString(16), modexp[1].toString(16), modexp[2].toString(16)) ;
    }

    /**
     * Helper method creating the JSON string for a single modexp without modulus and with or without given result.
     * @param modexp the modexp to serialize
     * @param withResult true to include result, false to omit
     * @return A JSON representation of modexp
     */
    static String serializeModexpNoModulus(BigInteger[] modexp, boolean withResult) {
        return withResult ? String.format("{\"b\":\"%s\",\"e\":\"%s\",\"r\":\"%s\"}", modexp[1].toString(16), modexp[2].toString(16), modexp[3].toString(16)) :
            String.format("{\"b\":\"%s\",\"e\":\"%s\"}", modexp[1].toString(16), modexp[2].toString(16)) ;
    }

        /**
     * Helper method creating the JSON string for a single modexp without base and with or without given result.
     * @param modexp the modexp to serialize
     * @param withResult true to include result, false to omit
     * @return A JSON representation of modexp
     */
    static String serializeModexpNoBase(BigInteger[] modexp, boolean withResult) {
        return withResult ? String.format("{\"m\":\"%s\",\"e\":\"%s\",\"r\":\"%s\"}", modexp[0].toString(16), modexp[2].toString(16), modexp[3].toString(16)) :
            String.format("{\"m\":\"%s\",\"e\":\"%s\"}", modexp[0].toString(16), modexp[2].toString(16)) ;
    }

    /**
     * Helper method creating the JSON string for a single modexp without exponent and with or without given result.
     * @param modexp the modexp to serialize
     * @param withResult true to include result, false to omit
     * @return A JSON representation of modexp
     */
    static String serializeModexpNoExponent(BigInteger[] modexp, boolean withResult) {
        return withResult ? String.format("{\"m\":\"%s\",\"b\":\"%s\",\"r\":\"%s\"}", modexp[0].toString(16), modexp[1].toString(16), modexp[3].toString(16)) :
            String.format("{\"m\":\"%s\",\"b\":\"%s\"}", modexp[0].toString(16), modexp[1].toString(16)) ;
    }

    static String serializeModexpResponse(BigInteger response) {
        return String.format("{\"r\":\"%s\"}", response.toString(16));
    }

    /**
     * Helper method creating the JSON string for a modexp-queryResponse with optional default base, exponent and modulus.
     * @param modulus the default modulus to use or null to omit
     * @param base the default base to use or null to omit
     * @param exponent the default exponent to use or null to omit
     * @param brief should the server return a brief or full response?
     * @param modexps JSON strings of modexps to queryResponse for
     * @return A JSON representation of a modexp-queryResponse
     */
    static String serializeQuery(BigInteger modulus, BigInteger base, BigInteger exponent, boolean brief, String... modexps) {
        String query = "{";
        query = modulus != null ? query + "\"m\":\"" + modulus.toString(16) + "\"," : query;
        query = base != null ? query + "\"b\":\"" + base.toString(16) + "\"," : query;
        query = exponent != null ? query + "\"e\":\"" + exponent.toString(16) + "\"," : query;
        query = brief ? query : query + "\"brief\":false,";

        query = modexps.length > 0 ? query + "\"modexps\":[" : query;
        for(int i = 0; i < modexps.length; i++) {
            query += modexps[i];
            query += i < modexps.length - 1 ? "," : "" ;
        }
        query = modexps.length > 0 ? query + "]" : query;

        return query + "}";
    }

    /**
     * Helper method creating the JSON string for the queryResponse to a modexp-queryResponse, with optional default base, exponent and modulus.
     * @param modulus the default modulus to use or null to omit
     * @param base the default base to use or null to omit
     * @param exponent the default exponent to use or null to omit
     * @param brief do we expect a brief or full response?
     * @param response JSON strings of response to queryResponse for
     * @return A JSON representation of a modexp-queryResponse
     */
    static String serializeResponse(BigInteger modulus, BigInteger base, BigInteger exponent, boolean brief, String... response) {
        String queryResponse = "{";
        if(!brief) {
            queryResponse = modulus != null ? queryResponse + "\"m\":\"" + modulus.toString(16) + "\"," : queryResponse;
            queryResponse = base != null ? queryResponse + "\"b\":\"" + base.toString(16) + "\"," : queryResponse;
            queryResponse = exponent != null ? queryResponse + "\"e\":\"" + exponent.toString(16) + "\"," : queryResponse;
            queryResponse = queryResponse + "\"brief\":false,";
        }

        queryResponse = response.length > 0 ? queryResponse + "\"modexps\":[" : queryResponse;
        for(int i = 0; i < response.length; i++) {
            queryResponse += response[i];
            queryResponse += i < response.length - 1 ? "," : "" ;
        }
        queryResponse = response.length > 0 ? queryResponse + "]" : queryResponse;

        return queryResponse + "}";
    }
}