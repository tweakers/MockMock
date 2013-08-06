package com.mockmock.mail;

import com.google.common.eventbus.Subscribe;
import com.mockmock.AppStarter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

public class MailQueue
{
    private static ArrayList<MockMail> mailQueue = new ArrayList<MockMail>();

    @Subscribe
    public void recordMailQueueAddition(MockMail mail)
    {
        MailQueue.add(mail);
    }

    /**
     * Add a MockMail to the queue. Queue is sorted and trimmed right after it.
     * @param mail The MockMail object to add to the queue
     */
    public static void add(MockMail mail)
    {
        mailQueue.add(mail);
        Collections.sort(mailQueue);
        Collections.reverse(mailQueue);

        trimQueue();
    }

    /**
     * @return Returns the complete mailQueue
     */
    public static ArrayList<MockMail> getMailQueue()
    {
        return mailQueue;
    }

    /**
     * Returns the MockMail that belongs to the given ID
     * @param id The id of the mail mail that needs to be retrieved
     * @return Returns the MockMail when found or null otherwise
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

    /**
     * Removes all mail in the queue
     */
    public static void emptyQueue()
    {
        mailQueue.clear();
        mailQueue.trimToSize();
    }

	/**
	 * Removes the mail with the given id from the queue
	 * @param id long
	 * @return boolean
	 */
	public static boolean deleteById(long id)
	{
		for(MockMail mockMail : mailQueue)
		{
			if(mockMail.getId() == id)
			{
				mailQueue.remove(mailQueue.indexOf(mockMail));
				return true;
			}
		}

		return false;
	}

    /**
     * Trims the mail queue so there aren't too many mails in it.
     */
    private static void trimQueue()
    {
        if(mailQueue.size() > AppStarter.maxMailQueueSize)
        {
            for (ListIterator<MockMail> iter = mailQueue.listIterator(mailQueue.size()); iter.hasPrevious();)
            {
                iter.previous();

                if(mailQueue.size() <= AppStarter.maxMailQueueSize)
                {
                    break;
                }
                else
                {
                    iter.remove();
                }
            }
        }

        mailQueue.trimToSize();
    }
}
