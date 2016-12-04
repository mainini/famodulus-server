/*
 * Copyright 2016 Pascal Mainini
 * Licensed under MIT license, see included file LICENSE or
 * http://opensource.org/licenses/MIT
 */
package ch.mainini.famodulus.server.modexp;

import java.math.BigInteger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit test for ModExpBean
 * @author Pascal Mainini
 */
public class ModExpBeanTest {

//////////////////////////////////////// Methods

    /**
     * Pre-test setup
     * @throws Exception in case of trouble
     */
    @BeforeClass
    public static void setUp() throws Exception {
    }

    /**
     * Test for the default, empty constructor
     */
    @Test
    public void constructorDefault() {
        final ModExpBean bean = new ModExpBean();
        assertNull(bean.getModulus());
        assertNull(bean.getBase());
        assertNull(bean.getExponent());
        assertNull(bean.getResult());
    }

    /**
     * Test for the constructor without result
     */
    @Test
    public void constructorNoResult() {
        final ModExpBean bean = new ModExpBean(BigInteger.ZERO, BigInteger.ONE, BigInteger.TEN);
        assertEquals(BigInteger.ZERO, bean.getModulus());
        assertEquals(BigInteger.ONE, bean.getBase());
        assertEquals(BigInteger.TEN, bean.getExponent());
        assertNull(bean.getResult());
    }

    /**
     * Test for the constructor with result
     */
    @Test
    public void constructorWithResult() {
        final ModExpBean bean = new ModExpBean(BigInteger.ZERO, BigInteger.ONE, BigInteger.TEN, BigInteger.ZERO);
        assertEquals(BigInteger.ZERO, bean.getModulus());
        assertEquals(BigInteger.ONE, bean.getBase());
        assertEquals(BigInteger.TEN, bean.getExponent());
        assertEquals(BigInteger.ZERO, bean.getResult());
    }

    /**
     * Test for setting the modulus
     */
    @Test
    public void setModulus() {
        final ModExpBean bean = new ModExpBean();
        bean.setModulus(BigInteger.ONE);
        assertEquals(BigInteger.ONE, bean.getModulus());
    }

    /**
     * Test for setting the base
     */
    @Test
    public void setBase() {
        final ModExpBean bean = new ModExpBean();
        bean.setBase(BigInteger.ONE);
        assertEquals(BigInteger.ONE, bean.getBase());
    }

    /**
     * Test for setting the exponent
     */
    @Test
    public void setExponent() {
        final ModExpBean bean = new ModExpBean();
        bean.setExponent(BigInteger.ONE);
        assertEquals(BigInteger.ONE, bean.getExponent());
    }

    /**
     * Test for setting the result
     */
    @Test
    public void setResult() {
        final ModExpBean bean = new ModExpBean();
        bean.setResult(BigInteger.ONE);
        assertEquals(BigInteger.ONE, bean.getResult());
    }
}