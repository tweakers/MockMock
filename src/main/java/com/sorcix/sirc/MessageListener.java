/*
 * MessageListener.java
 * 
 * This file is part of the Sorcix Java IRC Library (sIRC).
 * 
 * Copyright (C) 2008-2010 Vic Demuzere http://sorcix.com
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.sorcix.sirc;

/**
 * Notified of new IRC messages.
 * 
 * @author Sorcix
 * @see IrcConnection#addMessageListener(MessageListener)
 * @see IrcConnection#removeMessageListener(MessageListener)
 */
public interface MessageListener {
	
	/**
	 * Received an action in a channel.
	 * 
	 * @param irc The {@link IrcConnection} receiving this event.
	 * @param sender The user who sent the action.
	 * @param target The channel the action was sent to.
	 * @param action The action message.
	 */
	void onAction(IrcConnection irc, User sender, Channel target, String action);
	
	/**
	 * Received a private action.
	 * 
	 * @param irc The {@link IrcConnection} receiving this event.
	 * @param sender The user who sent the action.
	 * @param action The action message.
	 */
	void onAction(IrcConnection irc, User sender, String action);
	
	/**
	 * Received a CTCP reply. Note that this event is only fired when
	 * receiving CTCP replies supported by sIRC. If you can't send a
	 * CTCP request, you won't get the reply.
	 * 
	 * @param irc The {@link IrcConnection} receiving this event.
	 * @param sender The user sending this CTCP reply.
	 * @param command The CTCP command. (PONG, CLIENTINFO, ...)
	 * @param message The CTCP message.
	 * @see User#sendCtcpClientInfo()
	 * @see User#sendCtcpPing()
	 * @see User#sendCtcpVersion()
	 */
	void onCtcpReply(IrcConnection irc, User sender, String command, String message);
	
	/**
	 * Received a message in a channel.
	 * 
	 * @param irc The {@link IrcConnection} receiving this event.
	 * @param sender The user who sent the message.
	 * @param target The channel the message was sent to.
	 * @param message The message.
	 */
	void onMessage(IrcConnection irc, User sender, Channel target, String message);
	
	/**
	 * Received a notice in a channel.
	 * 
	 * @param irc The {@link IrcConnection} receiving this event.
	 * @param sender The user who sent the notice.
	 * @param target The channel the notice was sent to.
	 * @param message The notice.
	 */
	void onNotice(IrcConnection irc, User sender, Channel target, String message);
	
	/**
	 * Received a private notice.
	 * 
	 * @param irc The {@link IrcConnection} receiving this event.
	 * @param sender The user who sent the notice.
	 * @param message The notice.
	 */
	void onNotice(IrcConnection irc, User sender, String message);
	
	/**
	 * Received a private message.
	 * 
	 * @param irc The {@link IrcConnection} receiving this event.
	 * @param sender The user who sent the message.
	 * @param message The message.
	 */
	void onPrivateMessage(IrcConnection irc, User sender, String message);
}
