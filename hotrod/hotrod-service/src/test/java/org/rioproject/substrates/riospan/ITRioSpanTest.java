package org.rioproject.substrates.riospan;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.rioproject.cybernode.StaticCybernode;

/**
 * Testing the RioSpanService service
 */
@RunWith(Parameterized.class)
@Ignore
public class ITRioSpanTest extends ITAbstractRioSpanTest {
    String opstring;
    HotRodService service;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        String opstring = System.getProperty("opstring");
        Assert.assertNotNull("no opstring given", opstring);
        return Arrays.asList(new Object[][] {{ opstring }});
    }

    public ITRioSpanTest(String opstring) {
        this.opstring = opstring;
    }

    @Before
    public void setup() throws Exception {
        StaticCybernode cybernode = new StaticCybernode();
        Map<String, Object> map = cybernode.activate(new File(opstring));
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String name = entry.getKey();
            Object impl = entry.getValue();
            if (name.equals("RioSpanService"))
                service = (HotRodService) impl;
        }
    }

    @Test
    public void test1() {
        testService(service);
    }
}
