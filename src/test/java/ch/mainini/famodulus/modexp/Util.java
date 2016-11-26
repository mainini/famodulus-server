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
    static final BigInteger P_1024 = new BigInteger("89884656743115795386465259539451236680898848947115328636715040578866337902750481566354238661203768010560056939935696678829394884407208311246423715319737062188883946712432742638151109800623047059726541476042502884419075341171231440736956555270413618581675255342293149119973622969239858152417678164812113740223");

    /**
     * A safe prime with 2048 bits
     */
    static final BigInteger P_2048 = new BigInteger("16158503035655503650357438344334975980222051334857742016065172713762327569433945446598600705761456731844358980460949009747059779575245460547544076193224141560315438683650498045875098875194826053398028819192033784138396109321309878080919047169238085235290822926018152521443787945770532904303776199561965192760957166694834171210342487393282284747428088017663161029038902829665513096354230157075129296432088558362971801859230928678799175576150822952201848806616643615613562842355410104862578550863465661734839271290328348967522998634176499319107762583194718667771801067716614802322659239302476074096777926805529798824879");

    /**
     * A safe prime with 3072 bits
     */
    static final BigInteger P_3072 = new BigInteger("2904802997684979031429751266652287185343487588181447618330743076143601865498555112868668022266559203625663078877490258721995264797270023560831442836093516200516055819853220249422024925494525813600122382903520906197364840270012052413988292184690761146180604389522384946371612875869038489784405654789562755666546621759776892408153190790080930100123746284224075121257652224788593802068214369290495086275786967073127915183202957500434821866026609283416272645553951861415817069299793203345162979862593723584529770402506155104819505875374380008547680367117472878708136497428006654308479264979152338818509590797044264172530642931949135881728647441773319439777155807723223165099627191170008146028545375587766944080959493647795765768349350646133842732758718957895411577422317390130051445859016247698037520949742756905563488653739484537428521855358075060657961012278379620619506576459855478234203189721457470807178553957231283671210463");

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
        return withResult ? String.format("{\"m\":%s,\"b\":%s,\"e\":%s,\"r\":%s}", modexp[0], modexp[1], modexp[2], modexp[3]) :
            String.format("{\"m\":%s,\"b\":%s,\"e\":%s}", modexp[0], modexp[1], modexp[2]) ;
    }

    /**
     * Helper method creating the JSON string for a single modexp without modulus and with or without given result.
     * @param modexp the modexp to serialize
     * @param withResult true to include result, false to omit
     * @return A JSON representation of modexp
     */
    static String serializeModexpNoModulus(BigInteger[] modexp, boolean withResult) {
        return withResult ? String.format("{\"b\":%s,\"e\":%s,\"r\":%s}", modexp[1], modexp[2], modexp[3]) :
            String.format("{\"b\":%s,\"e\":%s}", modexp[1], modexp[2]) ;
    }

        /**
     * Helper method creating the JSON string for a single modexp without base and with or without given result.
     * @param modexp the modexp to serialize
     * @param withResult true to include result, false to omit
     * @return A JSON representation of modexp
     */
    static String serializeModexpNoBase(BigInteger[] modexp, boolean withResult) {
        return withResult ? String.format("{\"m\":%s,\"e\":%s,\"r\":%s}", modexp[0], modexp[2], modexp[3]) :
            String.format("{\"m\":%s,\"e\":%s}", modexp[0], modexp[2]) ;
    }

    /**
     * Helper method creating the JSON string for a single modexp without exponent and with or without given result.
     * @param modexp the modexp to serialize
     * @param withResult true to include result, false to omit
     * @return A JSON representation of modexp
     */
    static String serializeModexpNoExponent(BigInteger[] modexp, boolean withResult) {
        return withResult ? String.format("{\"m\":%s,\"b\":%s,\"r\":%s}", modexp[0], modexp[1], modexp[3]) :
            String.format("{\"m\":%s,\"b\":%s}", modexp[0], modexp[1]) ;
    }

    static String serializeModexpResponse(BigInteger response) {
        return String.format("{\"r\":%s}", response);
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
        query = modulus != null ? query + "\"m\":" + modulus + "," : query;
        query = base != null ? query + "\"b\":" + base + "," : query;
        query = exponent != null ? query + "\"e\":" + exponent + "," : query;
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
            queryResponse = modulus != null ? queryResponse + "\"m\":" + modulus + "," : queryResponse;
            queryResponse = base != null ? queryResponse + "\"b\":" + base + "," : queryResponse;
            queryResponse = exponent != null ? queryResponse + "\"e\":" + exponent + "," : queryResponse;
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