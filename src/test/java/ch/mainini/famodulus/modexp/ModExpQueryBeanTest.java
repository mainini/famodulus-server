/*
 * Copyright 2016 Pascal Mainini
 * Licensed under MIT license, see included file LICENSE or
 * http://opensource.org/licenses/MIT
 */
package ch.mainini.famodulus.modexp;

import java.math.BigInteger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit test for ModExpQueryBean
 * @author Pascal Mainini
 */
public class ModExpQueryBeanTest {

//////////////////////////////////////// Constants

    private static final ModExpBean MODEXP_1 = new ModExpBean();
    private static final ModExpBean MODEXP_2 = new ModExpBean();
    private static final ModExpBean[] MODEXPS = new ModExpBean[] { MODEXP_1, MODEXP_2 };

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
        final ModExpQueryBean bean = new ModExpQueryBean();
        assertNull(bean.getModulus());
        assertNull(bean.getBase());
        assertNull(bean.getExponent());
        assertTrue(bean.getBrief());
    }

    /**
     * Test for the constructor with given MODEXPS
     */
    @Test
    public void constructorModexps() {
        final ModExpQueryBean bean = new ModExpQueryBean(MODEXPS);
        ModExpBean[] modexps = bean.getModexps();
        assertEquals(modexps[0], modexps[0]);
        assertEquals(modexps[1], modexps[1]);
        assertNull(bean.getModulus());
        assertNull(bean.getBase());
        assertNull(bean.getExponent());
        assertTrue(bean.getBrief());
    }

    /**
     * Test for setting the MODEXPS
     */
    @Test
    public void setModexps() {
        final ModExpQueryBean bean = new ModExpQueryBean();
        bean.setModexps(MODEXPS);
        assertEquals(MODEXPS[0], MODEXPS[0]);
        assertEquals(MODEXPS[1], MODEXPS[1]);
    }

    /**
     * Test for setting the modulus
     */
    @Test
    public void setModulus() {
        final ModExpQueryBean bean = new ModExpQueryBean();
        bean.setModulus(BigInteger.ONE);
        assertEquals(BigInteger.ONE, bean.getModulus());
    }

    /**
     * Test for setting the base
     */
    @Test
    public void setBase() {
        final ModExpQueryBean bean = new ModExpQueryBean();
        bean.setBase(BigInteger.ONE);
        assertEquals(BigInteger.ONE, bean.getBase());
    }

    /**
     * Test for setting the exponent
     */
    @Test
    public void setExponent() {
        final ModExpQueryBean bean = new ModExpQueryBean();
        bean.setExponent(BigInteger.ONE);
        assertEquals(BigInteger.ONE, bean.getExponent());
    }

    /**
     * Test for setting the brief flag
     */
    @Test
    public void setBrief() {
        final ModExpQueryBean bean = new ModExpQueryBean();
        bean.setBrief(false);
        assertFalse(bean.getBrief());
    }
}