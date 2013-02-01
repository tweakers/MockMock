/*
 * AdvancedListener.java
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
 * Notified when receiving unknown lines. This allows acting on
 * incoming IRC events not supported by sIRC. Note that sIRC only
 * allows a single instance of {@code AdvancedListener}, which means
 * that {@link SIRCService}s should not use it.
 * <p>
 * Note that if sIRC supports new events in the future the lines will
 * no longer be sent to the {@code AdvancedListener}!
 * </p>
 * 
 * @author Sorcix
 */
public interface AdvancedListener {
	
	/**
	 * Received an unknown IRC event.
	 * 
	 * @param irc The {@link IrcConnection} receiving this event.
	 * @param line The incoming line.
	 */
	void onUnknown(IrcConnection irc, IrcPacket line);
	
	/**
	 * Received an unknown numeric server reply.
	 * 
	 * @param irc The {@link IrcConnection} receiving this event.
	 * @param line The incoming line.
	 */
	void onUnknownReply(IrcConnection irc, IrcPacket line);
}
