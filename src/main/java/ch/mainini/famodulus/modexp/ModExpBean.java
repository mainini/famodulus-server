/*
 * Copyright 2016 Pascal Mainini
 * Licensed under MIT license, see included file LICENSE or
 * http://opensource.org/licenses/MIT
 */
package ch.mainini.famodulus.modexp;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Bean representing a single, modular exponentiation
 * @author Pascal Mainini
 */
public class ModExpBean {

//////////////////////////////////////// Fields

    @XmlAttribute(name = "m")
    private BigInteger modulus;

    @XmlAttribute(name = "b")
    private BigInteger base;

    @XmlAttribute(name = "e")
    private BigInteger exponent;

    @XmlAttribute(name = "r")
    private BigInteger result;

//////////////////////////////////////// Constructors

    /**
     * Default, empty constructor.
     */
    public ModExpBean() { }

    /**
     * Constructor which initializes the modexp without result
     * @param modulus the modulus to use
     * @param base the base to use
     * @param exponent the exponent to use
     */
    public ModExpBean(BigInteger modulus, BigInteger base, BigInteger exponent) {
        this.modulus = modulus;
        this.base = base;
        this.exponent = exponent;
        this.result = null;
    }

    /**
     * Constructor which initializes the modexp with modulus, base, exponent, and result.
     * @param modulus the modulus to use
     * @param base the base to use
     * @param exponent the exponent to use
     * @param result the result to use
     */
    public ModExpBean(BigInteger modulus, BigInteger base, BigInteger exponent, BigInteger result) {
        this.modulus = modulus;
        this.base = base;
        this.exponent = exponent;
        this.result = result;
    }


//////////////////////////////////////// Methods

    /**
     * @return the modulus
     */
    public BigInteger getModulus() {
        return modulus;
    }

    /**
     * @param modulus the modulus to set
     */
    public void setModulus(BigInteger modulus) {
        this.modulus = modulus;
    }

    /**
     * @return the base
     */
    public BigInteger getBase() {
        return base;
    }

    /**
     * @param base the base to set
     */
    public void setBase(BigInteger base) {
        this.base = base;
    }

    /**
     * @return the exponent
     */
    public BigInteger getExponent() {
        return exponent;
    }

    /**
     * @param exponent the exponent to set
     */
    public void setExponent(BigInteger exponent) {
        this.exponent = exponent;
    }

    /**
     * @return the result
     */
    public BigInteger getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(BigInteger result) {
        this.result = result;
    }
}