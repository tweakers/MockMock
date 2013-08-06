package com.mockmock.mail;

import com.google.common.eventbus.Subscribe;
import com.mockmock.AppStarter;
import com.mockmock.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

@Service
public class MailQueue
{
    private static ArrayList<MockMail> mailQueue = new ArrayList<>();

    private Settings settings;

    /**
     * Add a MockMail to the queue. Queue is sorted and trimmed right after it.
     * @param mail The MockMail object to add to the queue
     */
    @Subscribe
    public void add(MockMail mail)
    {
        mailQueue.add(mail);
        Collections.sort(mailQueue);
        Collections.reverse(mailQueue);

        trimQueue();
    }

    /**
     * @return Returns the complete mailQueue
     */
    public ArrayList<MockMail> getMailQueue()
    {
        return mailQueue;
    }

    /**
     * Returns the MockMail that belongs to the given ID
     * @param id The id of the mail mail that needs to be retrieved
     * @return Returns the MockMail when found or null otherwise
     */
    public MockMail getById(long id)
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
     * Returns the MockMail that was last send.
     *
     * @return  Returns the MockMail when found or null otherwise
     */
    public MockMail getLastSendMail()
    {
        if (mailQueue.size() == 0)
            return null;

        return mailQueue.get(0);
    }

    /**
     * Removes all mail in the queue
     */
    public void emptyQueue()
    {
        mailQueue.clear();
        mailQueue.trimToSize();
    }

	/**
	 * Removes the mail with the given id from the queue
	 * @param id long
	 * @return boolean
	 */
	public boolean deleteById(long id)
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
    private void trimQueue()
    {
        if(mailQueue.size() > settings.getMaxMailQueueSize())
        {
            for (ListIterator<MockMail> iter = mailQueue.listIterator(mailQueue.size()); iter.hasPrevious();)
            {
                iter.previous();

                if(mailQueue.size() <= settings.getMaxMailQueueSize())
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

    @Autowired
    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
