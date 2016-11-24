/*
 * Copyright 2016 Pascal Mainini
 * Licensed under MIT license, see included file LICENSE or
 * http://opensource.org/licenses/MIT
 */
package ch.mainini.famodulus.modexp;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Bean encapsulating one or multiple modexps without shared base, exponent or
 * modulus.
 * @todo extend with configuration for returning only the results
 * @author Pascal Mainini
 */
public class ModExpQueryBean {

//////////////////////////////////////// Fields

    private ModExpBean[] modexps;

    /**
     * Default modulus for all modexps which do not specify a modulus
     */
    @XmlAttribute(name = "m")
    private BigInteger modulus;

    /**
     * Default base for all modexps which do not specify a base
     */
    @XmlAttribute(name = "b")
    private BigInteger base;

    /**
     * Default exponent for all modexps which do not specify an exponent
     */
    @XmlAttribute(name = "e")
    private BigInteger exponent;


//////////////////////////////////////// Constructors

    /**
     * Default, empty constructor.
     */
    public ModExpQueryBean() { }

    /**
     * Constructor which directly initializes the modexps for this bean
     * @param modexps
     */
    public ModExpQueryBean(ModExpBean[] modexps) {
        this.modexps = modexps;
    }


//////////////////////////////////////// Methods

    /**
     * @return the modexps
     */
    public ModExpBean[] getModexps() {
        return modexps;
    }

    /**
     * @param modexps the modexps to set
     */
    public void setModexps(ModExpBean[] modexps) {
        this.modexps = modexps;
    }

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
}