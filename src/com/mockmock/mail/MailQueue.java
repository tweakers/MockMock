package com.mockmock.mail;

import java.util.ArrayList;

public class MailQueue
{
    private static ArrayList<MockMail> mailQueue = new ArrayList<>();

    public static void add(MockMail mail)
    {
        mailQueue.add(mail);
    }

    public static ArrayList<MockMail> getMailQueue()
    {
        return mailQueue;
    }

    /**
     * Returns the MockMail that belongs to the given ID
     * @param id long
     * @return MockMail
     */
    public static MockMail getById(long id)
    {
        for(MockMail mockMail : mailQueue)
        {
            if(mockMail.getId() == id)
            {
                return mockMail;
            }
        }

        return null;
    }
}