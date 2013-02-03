/*
 * IrcAdaptor.java
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
 * Implements all sIRC listeners. Extending this class allows you to
 * listen to events by overriding its methods. This requires you to
 * register the class as {@code ServerListener}, {@code
 * MessageListener} and {@code ModeListener} based on which events you
 * want to receive.
 * 
 * @author Sorcix
 */
public abstract class IrcAdaptor implements ServerListener, MessageListener, ModeListener {
	
	@Override
	public void onAction(final IrcConnection irc, final User sender, final Channel target, final String action) {}
	
	@Override
	public void onAction(final IrcConnection irc, final User sender, final String action) {}
	
	@Override
	public void onAdmin(final IrcConnection irc, final Channel channel, final User sender, final User user) {}
	
	@Override
	public void onConnect(final IrcConnection irc) {}
	
	@Override
	public void onCtcpReply(final IrcConnection irc, final User sender, final String command, final String message) {}
	
	@Override
	public void onDeAdmin(final IrcConnection irc, final Channel channel, final User sender, final User user) {}
	
	@Override
	public void onDeFounder(final IrcConnection irc, final Channel channel, final User sender, final User user) {}
	
	@Override
	public void onDeHalfop(final IrcConnection irc, final Channel channel, final User sender, final User user) {}
	
	@Override
	public void onDeOp(final IrcConnection irc, final Channel channel, final User sender, final User user) {}
	
	@Override
	public void onDeVoice(final IrcConnection irc, final Channel channel, final User sender, final User user) {}
	
	@Override
	public void onDisconnect(final IrcConnection irc) {}
	
	@Override
	public void onFounder(final IrcConnection irc, final Channel channel, final User sender, final User user) {}
	
	@Override
	public void onHalfop(final IrcConnection irc, final Channel channel, final User sender, final User user) {}
	
	@Override
	public void onInvite(final IrcConnection irc, final User sender, final User user, final Channel channel) {}
	
	@Override
	public void onJoin(final IrcConnection irc, final Channel channel, final User user) {}
	
	@Override
	public void onKick(final IrcConnection irc, final Channel channel, final User sender, final User user, final String message) {}
	
	@Override
	public void onMessage(final IrcConnection irc, final User sender, final Channel target, final String message) {}
	
	@Override
	public void onMode(final IrcConnection irc, final Channel channel, final User sender, final String mode) {}
	
	@Override
	public void onMotd(final IrcConnection irc, final String motd) {}
	
	@Override
	public void onNick(final IrcConnection irc, final User oldUser, final User newUser) {}
	
	@Override
	public void onNotice(final IrcConnection irc, final User sender, final Channel target, final String message) {}
	
	@Override
	public void onNotice(final IrcConnection irc, final User sender, final String message) {}
	
	@Override
	public void onOp(final IrcConnection irc, final Channel channel, final User sender, final User user) {}
	
	@Override
	public void onPart(final IrcConnection irc, final Channel channel, final User user, String message) {}
	
	@Override
	public void onPrivateMessage(final IrcConnection irc, final User sender, final String message) {}
	
	@Override
	public void onQuit(final IrcConnection irc, final User user, String message) {}
	
	@Override
	public void onTopic(final IrcConnection irc, final Channel channel, final User sender, final String topic) {}
	
	@Override
	public void onVoice(final IrcConnection irc, final Channel channel, final User sender, final User user) {}
}
