/*
 * Copyright 2016 Pascal Mainini
 * Licensed under MIT license, see included file LICENSE or
 * http://opensource.org/licenses/MIT
 */
package ch.mainini.famodulus.util;

import java.math.BigInteger;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Adapter for marshaling BigInteger from/to String
 * @author Pascal Mainini
 */
public class BigIntegerStringAdapter extends XmlAdapter<String, BigInteger> {

//////////////////////////////////////// Methods

    /**
     * Marshals a BigInteger into String
     * @param value the BigInteger to marshal
     * @return the marshaled String
     */
    @Override
    public String marshal(BigInteger value) {
        return value.toString(16);
    }

    /**
     * Unmarshals a String into a BigInteger
     * @param value the String to unmarshal
     * @return the unmarshaled BigInteger
     */
    @Override
    public BigInteger unmarshal(String value) {
        return new BigInteger(value, 16);
    }
}