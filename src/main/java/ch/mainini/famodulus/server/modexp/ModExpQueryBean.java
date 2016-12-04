/*
 * Copyright 2016 Pascal Mainini
 * Licensed under MIT license, see included file LICENSE or
 * http://opensource.org/licenses/MIT
 */
package ch.mainini.famodulus.server.modexp;

import ch.mainini.famodulus.server.util.BigIntegerStringAdapter;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Bean encapsulating one or multiple modexps without shared base, exponent or
 * modulus.
 * @author Pascal Mainini
 */
public class ModExpQueryBean {

//////////////////////////////////////// Fields

    private ModExpBean[] modexps;

    /**
     * Default modulus for all modexps which do not specify a modulus
     */
    @XmlAttribute(name = "m")
    @XmlJavaTypeAdapter(BigIntegerStringAdapter.class)
    private BigInteger modulus;

    /**
     * Default base for all modexps which do not specify a base
     */
    @XmlAttribute(name = "b")
    @XmlJavaTypeAdapter(BigIntegerStringAdapter.class)
    private BigInteger base;

    /**
     * Default exponent for all modexps which do not specify an exponent
     */
    @XmlAttribute(name = "e")
    @XmlJavaTypeAdapter(BigIntegerStringAdapter.class)
    private BigInteger exponent;

    /**
     * Return a brief response with only the results (true) or the full query
     * including results (false).
     */
    private Boolean brief = true;

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

    /**
     * @return the brief
     */
    public Boolean getBrief() {
        return brief;
    }

    /**
     * @param brief the brief to set
     */
    public void setBrief(Boolean brief) {
        this.brief = brief;
    }
}