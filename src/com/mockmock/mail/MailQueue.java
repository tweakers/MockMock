package com.mockmock.mail;

import java.util.ArrayList;

public class MailQueue
{
    private static ArrayList<MockMail> mailQueue = new ArrayList<>();

    public static void add(MockMail mail)
    {
        mailQueue.add(mail);
    }

    public static int count()
    {
        return mailQueue.size();
    }

    public static ArrayList<MockMail> getMailQueue()
    {
        return mailQueue;
    }
}