/*
 * SIRCService.java
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
 * A service plugin running on sIRC. A service extends sIRC by adding
 * much needed features such as auto rejoin, remembering channels and
 * common bot features.
 * 
 * @author Sorcix
 * @see IrcConnection#addService(SIRCService)
 * @see IrcConnection#removeService(SIRCService)
 * @see IrcConnection#removeAllServices()
 */
public interface SIRCService {
	
	/**
	 * Returns the name of this service. Used for listing active
	 * services.
	 * 
	 * @return The name of this service.
	 */
	String getName();
	
	/**
	 * Called upon loading this service on sIRC.
	 * 
	 * @param irc The {@link IrcConnection} running this service.
	 */
	void load(IrcConnection irc);
	
	/**
	 * Called upon removing this service from sIRC. Please make sure
	 * all listeners are removed from sIRC before returning this
	 * method.
	 * 
	 * @param irc The {@link IrcConnection} running this service.
	 */
	void unload(IrcConnection irc);
}
