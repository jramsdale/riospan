package org.rioproject.substrates.riospan;

import org.junit.Assert;

/**
 * Base class for RioSpanService testing
 */
public abstract class ITAbstractRioSpanTest {

    void testService(HotRodService service) {
        Assert.assertNotNull(service);
        /* Add your testing code here ...*/
    }
}
