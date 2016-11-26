/*
 * Copyright 2016 Pascal Mainini
 * Licensed under MIT license, see included file LICENSE or
 * http://opensource.org/licenses/MIT
 */
package ch.mainini.famodulus.modexp;

import ch.mainini.famodulus.Main;
import static ch.mainini.famodulus.modexp.Util.P_3072;
import static ch.mainini.famodulus.modexp.Util.randomModexp;
import static ch.mainini.famodulus.modexp.Util.serializeModexp;
import static ch.mainini.famodulus.modexp.Util.serializeModexpNoBase;
import static ch.mainini.famodulus.modexp.Util.serializeModexpNoExponent;
import static ch.mainini.famodulus.modexp.Util.serializeModexpNoModulus;
import static ch.mainini.famodulus.modexp.Util.serializeModexpResponse;
import static ch.mainini.famodulus.modexp.Util.serializeQuery;
import static ch.mainini.famodulus.modexp.Util.serializeResponse;
import java.math.BigInteger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit test for ModExpResource
 * @author Pascal Mainini
 */
public class ModExpResourceTest {

//////////////////////////////////////// Constants

    private static final String API_PATH = "modexp";

    private static final BigInteger[] MODEXP_1 = randomModexp(P_3072, 3072);
    private static final BigInteger[] MODEXP_2 = randomModexp(P_3072, 3072);
    private static final BigInteger[] MODEXP_3 = randomModexp(P_3072, 3072);
    private static final BigInteger[] MODEXP_4 = randomModexp(P_3072, 3072);
    private static final BigInteger[] MODEXP_5 = randomModexp(P_3072, 3072);

    private static final int HTTP_OK = 200;
    private static final int HTTP_BAD_REQUEST = 400;
    private static final int HTTP_METHOD_NOT_ALLOWED = 405;
    private static final int HTTP_INTERNAL_SERVER_ERROR = 500;

//////////////////////////////////////// Fields

    private static HttpServer server;
    private static WebTarget target;


//////////////////////////////////////// Methods

///////////////////// Initialization

    /**
     * Pre-test setup
     * @throws Exception in case of trouble
     */
    @BeforeClass
    public static void setUp() throws Exception {
        server = Main.startServer();

        final Client c = ClientBuilder.newClient();
        target = c.target(Main.API_URI);
    }

    /**
     * After-test cleanup
     * @throws Exception in case of trouble
     */
    @AfterClass
    public static void tearDown() throws Exception {
        server.shutdownNow();
    }

///////////////////// Failure tests

    /**
     * Test performing a simple HTTP HEAD and checks for the appropriate status code.
     */
    @Test
    public void methodHead() {
        assertEquals(HTTP_METHOD_NOT_ALLOWED, target.path(API_PATH).request().head().getStatus());
    }

    /**
     * Test performing a simple HTTP GET and checks for the appropriate status code.
     */
    @Test
    public void methodGet() {
        assertEquals(HTTP_METHOD_NOT_ALLOWED, target.path(API_PATH).request().get().getStatus());
    }

    /**
     * Test performing a simple HTTP TRACE and checks for the appropriate status code.
     */
    @Test
    public void methodTrace() {
        assertEquals(HTTP_METHOD_NOT_ALLOWED, target.path(API_PATH).request().trace().getStatus());
    }

    /**
     * Test performing a simple HTTP PUT and checks for the appropriate status code.
     */
    @Test
    public void methodPut() {
        assertEquals(HTTP_METHOD_NOT_ALLOWED, target.path(API_PATH).request().put(Entity.entity("{}",MediaType.APPLICATION_JSON)).getStatus());
    }

    /**
     * Test performing a simple HTTP DELETE and checks for the appropriate status code.
     */
    @Test
    public void methodDelete() {
        assertEquals(HTTP_METHOD_NOT_ALLOWED, target.path(API_PATH).request().delete().getStatus());
    }

    /**
     * Test performing a simple HTTP OPTIONS and checks for the appropriate status code.
     */
    @Test
    public void methodOptions() {
        assertEquals(HTTP_OK, target.path(API_PATH).request().options().getStatus());
    }

///////////////////// Correct payload tests

    /**
     * Test if the "brief" option for a query works correctly
     */
    @Test
    public void briefQuery() {
        // expect full response
        assertEquals("{\"brief\":false,\"modexps\":[{\"m\":3,\"b\":2,\"e\":3,\"r\":2}]}", target.path(API_PATH).request().post(
                Entity.entity("{\"brief\":false,\"modexps\":[{\"m\":3,\"b\":2,\"e\":3}]}", MediaType.APPLICATION_JSON), String.class));

        // expect default brief response
        assertEquals("{\"modexps\":[{\"r\":2}]}", target.path(API_PATH).request().post(
                Entity.entity("{\"modexps\":[{\"m\":3,\"b\":2,\"e\":3}]}", MediaType.APPLICATION_JSON), String.class));

        // request brief response
        assertEquals("{\"modexps\":[{\"r\":2}]}", target.path(API_PATH).request().post(
                Entity.entity("{\"brief\":true,\"modexps\":[{\"m\":3,\"b\":2,\"e\":3}]}", MediaType.APPLICATION_JSON), String.class));
    }

    /**
     * Test calculating a single, randomized modexp on the server.
     */
    @Test
    public void modexpSingleFull() {
        final String query = serializeQuery(null, null, null, false, serializeModexp(MODEXP_1, false));
        final String response = serializeResponse(null, null, null, false, serializeModexp(MODEXP_1, true));

        assertEquals(response, target.path(API_PATH).request().post(Entity.entity(query,MediaType.APPLICATION_JSON), String.class));
    }

    /**
     * Test calculating a single, randomized modexp on the server, expecting brief result format.
     */
    @Test
    public void modexpSingleBrief() {
        final String query = serializeQuery(null, null, null, true, serializeModexp(MODEXP_1, false));
        final String response = serializeResponse(null, null, null, true, serializeModexpResponse(MODEXP_1[3]));

        assertEquals(response, target.path(API_PATH).request().post(Entity.entity(query,MediaType.APPLICATION_JSON), String.class));
    }

    /**
     * Test calculating five different, randomized modexps on the server.
     */
    @Test
    public void modexpFiveFull() {
        final String[] modexp1 = new String[] { serializeModexp(MODEXP_1, false), serializeModexp(MODEXP_1, true) };
        final String[] modexp2 = new String[] { serializeModexp(MODEXP_2, false), serializeModexp(MODEXP_2, true) };
        final String[] modexp3 = new String[] { serializeModexp(MODEXP_3, false), serializeModexp(MODEXP_3, true) };
        final String[] modexp4 = new String[] { serializeModexp(MODEXP_4, false), serializeModexp(MODEXP_4, true) };
        final String[] modexp5 = new String[] { serializeModexp(MODEXP_5, false), serializeModexp(MODEXP_5, true) };

        final String query = serializeQuery(null, null, null, false, modexp1[0], modexp2[0], modexp3[0], modexp4[0], modexp5[0]);
        final String response = serializeQuery(null, null, null, false, modexp1[1], modexp2[1], modexp3[1], modexp4[1], modexp5[1]);

        assertEquals(response, target.path(API_PATH).request().post(Entity.entity(query,MediaType.APPLICATION_JSON), String.class));
    }

    /**
     * Test calculating five different, randomized modexps on the server, expecting brief result format.
     */
    @Test
    public void modexpFiveBrief() {
        final String query = serializeQuery(null, null, null, true, serializeModexp(MODEXP_1, false), serializeModexp(MODEXP_2, false),
                serializeModexp(MODEXP_3, false), serializeModexp(MODEXP_4, false), serializeModexp(MODEXP_5, false));
        final String response = serializeResponse(null, null, null, true, serializeModexpResponse(MODEXP_1[3]), serializeModexpResponse(MODEXP_2[3]),
                serializeModexpResponse(MODEXP_3[3]), serializeModexpResponse(MODEXP_4[3]), serializeModexpResponse(MODEXP_5[3]));

        assertEquals(response, target.path(API_PATH).request().post(Entity.entity(query,MediaType.APPLICATION_JSON), String.class));
    }

    /**
     * Test calculating a single, randomized modexp with a specified default modulus on the server.
     */
    @Test
    public void modexpDefaultModulus() {
        final String query = serializeQuery(MODEXP_2[0], null, null, true, serializeModexpNoModulus(MODEXP_2, false));
        final String response = serializeResponse(MODEXP_2[0], null, null, true, serializeModexpResponse(MODEXP_2[3]));
        assertEquals(response, target.path(API_PATH).request().post(Entity.entity(query,MediaType.APPLICATION_JSON), String.class));
    }

    /**
     * Test calculating a single, randomized modexp with a specified default base on the server.
     */
    @Test
    public void modexpDefaultBase() {
        final String query = serializeQuery(null, MODEXP_3[1], null, true, serializeModexpNoBase(MODEXP_3, false));
        final String response = serializeResponse(null, MODEXP_3[1], null, true, serializeModexpResponse(MODEXP_3[3]));
        assertEquals(response, target.path(API_PATH).request().post(Entity.entity(query,MediaType.APPLICATION_JSON), String.class));
    }

    /**
     * Test calculating a single, randomized modexp with a specified default exponent on the server.
     */
    @Test
    public void modexpDefaultExponent() {
        final String query = serializeQuery(null, null, MODEXP_4[2], true, serializeModexpNoExponent(MODEXP_4, false));
        final String response = serializeResponse(null, null, MODEXP_4[2], true, serializeModexpResponse(MODEXP_4[3]));
        assertEquals(response, target.path(API_PATH).request().post(Entity.entity(query,MediaType.APPLICATION_JSON), String.class));
    }

    /**
     * Test if modulus gets overridden by default modulus
     */
    @Test
    public void modexpOverrideModulus() {
        final String query =    "{\"m\":23,\"brief\":false,\"modexps\":[{\"m\":17,\"b\":3,\"e\":5}]}";
        final String response = "{\"m\":23,\"brief\":false,\"modexps\":[{\"m\":17,\"b\":3,\"e\":5,\"r\":5}]}";
        assertEquals(response, target.path(API_PATH).request().post(Entity.entity(query,MediaType.APPLICATION_JSON), String.class));
    }

    /**
     * Test if base gets overridden by default base
     */
    @Test
    public void modexpOverrideBase() {
        final String query =    "{\"b\":9,\"brief\":false,\"modexps\":[{\"m\":17,\"b\":3,\"e\":5}]}";
        final String response = "{\"b\":9,\"brief\":false,\"modexps\":[{\"m\":17,\"b\":3,\"e\":5,\"r\":5}]}";
        assertEquals(response, target.path(API_PATH).request().post(Entity.entity(query,MediaType.APPLICATION_JSON), String.class));
    }

    /**
     * Test if exponent gets overridden by default exponent
     */
    @Test
    public void modexpOverrideExponent() {
        final String query =    "{\"e\":2,\"brief\":false,\"modexps\":[{\"m\":17,\"b\":3,\"e\":5}]}";
        final String response = "{\"e\":2,\"brief\":false,\"modexps\":[{\"m\":17,\"b\":3,\"e\":5,\"r\":5}]}";
        assertEquals(response, target.path(API_PATH).request().post(Entity.entity(query,MediaType.APPLICATION_JSON), String.class));
    }

    /**
     * Test if modulus, base or exponent gets overidden by any default value
     */
    @Test
    public void modexpOverrideAll() {
        final String query =    "{\"m\":23,\"b\":9,\"e\":2,\"brief\":false,\"modexps\":[{\"m\":17,\"b\":3,\"e\":5}]}";
        final String response = "{\"m\":23,\"b\":9,\"e\":2,\"brief\":false,\"modexps\":[{\"m\":17,\"b\":3,\"e\":5,\"r\":5}]}";
        assertEquals(response, target.path(API_PATH).request().post(Entity.entity(query,MediaType.APPLICATION_JSON), String.class));
    }

///////////////////// Incorrect payload tests

    /**
     * Do a HTTP POST with empty body
     */
    @Test
    public void postEmpty() {
        assertEquals(HTTP_BAD_REQUEST, target.path(API_PATH).request().post(Entity.entity("", MediaType.APPLICATION_JSON)).getStatus());
    }

    /**
     * Do a HTTP POST with empty JSON
     */
    @Test
    public void postEmptyJSON() {
        assertEquals(HTTP_INTERNAL_SERVER_ERROR, target.path(API_PATH).request().post(Entity.entity("{}", MediaType.APPLICATION_JSON)).getStatus());
    }

    /**
     * Send various bogus modexp queries
     */
    @Test
    public void postBogusQueries() {
        assertEquals(HTTP_INTERNAL_SERVER_ERROR, target.path(API_PATH).request().post(Entity.entity("{\"m\":23,\"b\":2,\"e\":3}", MediaType.APPLICATION_JSON)).getStatus());
        assertEquals(HTTP_INTERNAL_SERVER_ERROR, target.path(API_PATH).request().post(Entity.entity("{\"m\":23,\"b\":2,\"e\":3,\"modexps\":[]}", MediaType.APPLICATION_JSON)).getStatus());
        assertEquals(HTTP_BAD_REQUEST,           target.path(API_PATH).request().post(Entity.entity("{\"m\":23,\"b\":2,\"e\":3,\"modexps\":[\"m\":\"\"]}", MediaType.APPLICATION_JSON)).getStatus());
    }
}