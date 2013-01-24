package com.mockmock.mail;

import org.junit.Assert;
import org.junit.Test;

public class MockMailTest
{
    @Test
    public void testCompareToEarlier()
    {
        MockMail mailEarlier = new MockMail();
        // Thursday, January 24th 2013, 21:00:00 (GMT +1)
        mailEarlier.setReceivedTime(1359057600);

        MockMail mailLater = new MockMail();
        // Thursday, January 24th 2013, 22:00:00 (GMT +1)
        mailLater.setReceivedTime(1359061200);

        Assert.assertEquals(-3600, mailEarlier.compareTo(mailLater));
    }

    @Test
    public void testCompareToSame()
    {
        MockMail mailEarlier = new MockMail();
        // Thursday, January 24th 2013, 21:00:00 (GMT +1)
        mailEarlier.setReceivedTime(1359057600);

        MockMail mailLater = new MockMail();
        // Thursday, January 24th 2013, 21:00:00 (GMT +1)
        mailLater.setReceivedTime(1359057600);

        Assert.assertEquals(0, mailEarlier.compareTo(mailLater));
    }

    @Test
    public void testCompareToLater()
    {
        MockMail mailEarlier = new MockMail();
        // Thursday, January 24th 2013, 21:00:00 (GMT +1)
        mailEarlier.setReceivedTime(1359057600);

        MockMail mailLater = new MockMail();
        // Thursday, January 24th 2013, 22:00:00 (GMT +1)
        mailLater.setReceivedTime(1359061200);

        Assert.assertEquals(3600, mailLater.compareTo(mailEarlier));
    }
}
