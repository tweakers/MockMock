/*
 * ServerListener.java
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
 * Notified of server/channel changes. Some methods in this listener
 * could be called due to us doing something, use {@link User#isUs()}
 * to check if it was us.
 * 
 * @author Sorcix
 * @see IrcConnection#addServerListener(ServerListener)
 * @see IrcConnection#removeServerListener(ServerListener)
 */
public interface ServerListener {
	
	/**
	 * Connected to the server.
	 * 
	 * @param irc The {@link IrcConnection} receiving this event.
	 */
	void onConnect(IrcConnection irc);
	
	/**
	 * Disconnected from the server.
	 * 
	 * @param irc The {@link IrcConnection} receiving this event.
	 */
	void onDisconnect(IrcConnection irc);
	
	/**
	 * Someone (possibly us) was invited into a channel.
	 * 
	 * @param irc The {@link IrcConnection} receiving this event.
	 * @param sender The user who invited someone.
	 * @param user The user who was invited.
	 * @param channel The channel the user was invited to.
	 */
	void onInvite(IrcConnection irc, User sender, User user, Channel channel);
	
	/**
	 * Someone (possibly us) joined a channel.
	 * 
	 * @param irc The {@link IrcConnection} receiving this event.
	 * @param channel The channel someone joined to.
	 * @param user The user who joined.
	 */
	void onJoin(IrcConnection irc, Channel channel, User user);
	
	/**
	 * Someone (possibly us) was kicked from a channel.
	 * <p>
	 * <strong>Note:</strong> This method does NOT return a shared
	 * user object. That means that it isn't possible to retrieve the
	 * user prefix (or any modes).
	 * </p>
	 * 
	 * @param irc The {@link IrcConnection} receiving this event.
	 * @param channel The channel in which the user was kicked.
	 * @param sender The user who kicked the user.
	 * @param user The user who was kicked.
	 */
	void onKick(IrcConnection irc, Channel channel, User sender, User user, String msg);
	
	/**
	 * Someone (possibly us) changed a channel mode.
	 * 
	 * @param irc The {@link IrcConnection} receiving this event.
	 * @param channel The channel in which the mode was changed.
	 * @param sender The user changing this mode.
	 * @param mode The mode change.
	 */
	void onMode(IrcConnection irc, Channel channel, User sender, String mode);
	
	/**
	 * The server sent the Message of the Day.
	 * 
	 * @param irc The {@link IrcConnection} receiving this event.
	 * @param motd The message of the day.
	 * @see IrcConnection#askMotd()
	 * @since 1.0.2
	 */
	void onMotd(IrcConnection irc, String motd);
	
	/**
	 * Someone (possibly us) changed his nickname. Note that the
	 * {@code oldUser} can not be used to send messages, as that
	 * nickname no longer exists.
	 * <p>
	 * <strong>Note:</strong> This method does NOT return a shared
	 * user object. That means that it isn't possible to retrieve the
	 * user prefix (or any modes).
	 * </p>
	 * 
	 * @param irc The {@link IrcConnection} receiving this event.
	 * @param oldUser The old user nickname.
	 * @param newUser The new user nickname.
	 */
	void onNick(IrcConnection irc, User oldUser, User newUser);
	
	/**
	 * Someone (possibly us) left a channel.
	 * <p>
	 * <strong>Note:</strong> This method does NOT return a shared
	 * user object. That means that it isn't possible to retrieve the
	 * user prefix (or any modes).
	 * </p>
	 * 
	 * @param irc The {@link IrcConnection} receiving this event.
	 * @param channel The channel someone parted from.
	 * @param user The user who parted.
	 * @param message The part message, or {@code null}.
	 */
	void onPart(IrcConnection irc, Channel channel, User user, String message);
	
	/**
	 * Someone quit the IRC server.
	 * 
	 * @param irc The {@link IrcConnection} receiving this event.
	 * @param user The user who quit.
	 * @param message The quit message, or {@code null}.
	 */
	void onQuit(IrcConnection irc, User user, String message);
	
	/**
	 * Someone (possibly us) changed the topic of a channel, or we
	 * joined a new channel and discovered the topic. The {@code
	 * sender} will be {@code null} when we discovered the topic after
	 * joining.
	 * 
	 * @param irc The {@link IrcConnection} receiving this event.
	 * @param channel The channel in which the topic was changed.
	 * @param sender The user who changed the topic, or null.
	 * @param topic The new topic.
	 */
	void onTopic(IrcConnection irc, Channel channel, User sender, String topic);
}
