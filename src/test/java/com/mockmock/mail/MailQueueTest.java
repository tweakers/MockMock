package com.mockmock.mail;

import com.mockmock.AppStarter;
import org.junit.Assert;
import org.junit.Test;

public class MailQueueTest
{
    @Test
    public void testEmptyQueue()
    {
        MockMail mail = new MockMail();
        MailQueue.add(mail);
        Assert.assertFalse(MailQueue.getMailQueue().isEmpty());

        MailQueue.emptyQueue();
        Assert.assertTrue(MailQueue.getMailQueue().isEmpty());
    }

    @Test
    public void testAdd()
    {
        MailQueue.emptyQueue();

        MockMail mail = new MockMail();
        MailQueue.add(mail);

        Assert.assertTrue(MailQueue.getMailQueue().size() == 1);
    }

    @Test
    public void testMultipleAdd()
    {
        MailQueue.emptyQueue();

        for (int i = 0; i < AppStarter.maxMailQueueSize; i++)
        {
            MockMail mail = new MockMail();
            MailQueue.add(mail);
        }

        Assert.assertTrue(MailQueue.getMailQueue().size() == AppStarter.maxMailQueueSize);

        for (int i = 0; i < 10; i++)
        {
            MockMail mail = new MockMail();
            MailQueue.add(mail);
        }

        Assert.assertTrue(MailQueue.getMailQueue().size() == AppStarter.maxMailQueueSize);
    }

    @Test
    public void testGetById()
    {
        MailQueue.emptyQueue();

        long id = 23986;
        MockMail mail = new MockMail();
        mail.setId(id);
        mail.setSubject("Test subject");
        MailQueue.add(mail);

        long id2 = 87632;
        MockMail mail2 = new MockMail();
        mail2.setId(id2);
        mail2.setSubject("Test subject 2");
        MailQueue.add(mail2);

        Assert.assertEquals(mail, MailQueue.getById(id));
        Assert.assertEquals(mail2, MailQueue.getById(id2));
    }
}
