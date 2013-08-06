/*
 * ModeListener.java
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
 * Notified of user mode changes.
 * 
 * @author Sorcix
 * @see IrcConnection#addModeListener(ModeListener)
 * @see IrcConnection#removeModeListener(ModeListener)
 */
public interface ModeListener {
	
	/**
	 * Someone was given Admin mode.
	 * 
	 * @param irc The {@link IrcConnection} receiving this event.
	 * @param channel The channel in which the user mode was changed.
	 * @param sender The user changing this mode.
	 * @param user The user whose mode was changed.
	 */
	void onAdmin(IrcConnection irc, Channel channel, User sender, User user);
	
	/**
	 * Someone lost Admin mode.
	 * 
	 * @param irc The {@link IrcConnection} receiving this event.
	 * @param channel The channel in which the user mode was changed.
	 * @param sender The user changing this mode.
	 * @param user The user whose mode was changed.
	 */
	void onDeAdmin(IrcConnection irc, Channel channel, User sender, User user);
	
	/**
	 * Someone lost Founder mode.
	 * 
	 * @param irc The {@link IrcConnection} receiving this event.
	 * @param channel The channel in which the user mode was changed.
	 * @param sender The user changing this mode.
	 * @param user The user whose mode was changed.
	 */
	void onDeFounder(IrcConnection irc, Channel channel, User sender, User user);
	
	/**
	 * Someone lost Half-Operator mode.
	 * 
	 * @param irc The {@link IrcConnection} receiving this event.
	 * @param channel The channel in which the user mode was changed.
	 * @param sender The user changing this mode.
	 * @param user The user whose mode was changed.
	 */
	void onDeHalfop(IrcConnection irc, Channel channel, User sender, User user);
	
	/**
	 * Someone lost Operator mode.
	 * 
	 * @param irc The {@link IrcConnection} receiving this event.
	 * @param channel The channel in which the user mode was changed.
	 * @param sender The user changing this mode.
	 * @param user The user whose mode was changed.
	 */
	void onDeOp(IrcConnection irc, Channel channel, User sender, User user);
	
	/**
	 * Someone lost Voice mode.
	 * 
	 * @param irc The {@link IrcConnection} receiving this event.
	 * @param channel The channel in which the user mode was changed.
	 * @param sender The user changing this mode.
	 * @param user The user whose mode was changed.
	 */
	void onDeVoice(IrcConnection irc, Channel channel, User sender, User user);
	
	/**
	 * Someone was given Founder mode.
	 * 
	 * @param irc The {@link IrcConnection} receiving this event.
	 * @param channel The channel in which the user mode was changed.
	 * @param sender The user changing this mode.
	 * @param user The user whose mode was changed.
	 */
	void onFounder(IrcConnection irc, Channel channel, User sender, User user);
	
	/**
	 * Someone was given Half-Operator mode.
	 * 
	 * @param irc The {@link IrcConnection} receiving this event.
	 * @param channel The channel in which the user mode was changed.
	 * @param sender The user changing this mode.
	 * @param user The user whose mode was changed.
	 */
	void onHalfop(IrcConnection irc, Channel channel, User sender, User user);
	
	/**
	 * Someone was given Operator mode.
	 * 
	 * @param irc The {@link IrcConnection} receiving this event.
	 * @param channel The channel in which the user mode was changed.
	 * @param sender The user changing this mode.
	 * @param user The user whose mode was changed.
	 */
	void onOp(IrcConnection irc, Channel channel, User sender, User user);
	
	/**
	 * Someone was given Voice mode.
	 * 
	 * @param irc The {@link IrcConnection} receiving this event.
	 * @param channel The channel in which the user mode was changed.
	 * @param sender The user changing this mode.
	 * @param user The user whose mode was changed.
	 */
	void onVoice(IrcConnection irc, Channel channel, User sender, User user);
}
