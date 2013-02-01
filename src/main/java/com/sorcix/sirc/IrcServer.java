/*
 * IrcServer.java
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
 * Contains information about an IRC server.
 * 
 * @author Sorcix
 * @since 1.1.0
 */
public class IrcServer {
	
	/** The server address. */
	private String address;
	/** The server port. */
	private int port;
	/** The server password (or null if there is none). */
	private String password;
	/** Whether the server uses SSL. */
	private boolean secure;
	/** Default port (6667) */
	public static final int DEFAULT_PORT = 6667;
	
	/**
	 * Creates a new (non-SSL) IrcServer on default port.
	 * 
	 * @param address The server address.
	 */
	public IrcServer(final String address) {
		this(address, IrcServer.DEFAULT_PORT, null, false);
	}
	
	/**
	 * Creates a new IrcServer.
	 * 
	 * @param address The server address.
	 * @param port The server port.
	 * @param password The password to use (or null).
	 * @param secure Whether to use SSL.
	 */
	public IrcServer(final String address, final int port, final String password, final boolean secure) {
		this.address = address;
		this.port = port;
		this.password = password;
		this.secure = secure;
	}
	
	/**
	 * Creates a new (non-SSL) IrcServer.
	 * 
	 * @param address The server address.
	 * @param password The server port.
	 */
	public IrcServer(final String address, final String password) {
		this(address, IrcServer.DEFAULT_PORT, password, false);
	}
	
	/**
	 * Retrieves the server address.
	 * 
	 * @return The server address.
	 */
	public String getAddress() {
		return this.address;
	}
	
	/**
	 * Retrieves the password.
	 * 
	 * @return The server password, or {@code null}.
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * Retrieves the port number.
	 * 
	 * @return The server port.
	 */
	public int getPort() {
		return this.port;
	}
	
	/**
	 * Checks whether this server is using SSL.
	 * 
	 * @return True if this server is using SSL, false otherwise.
	 */
	public boolean isSecure() {
		return this.secure;
	}
	
	/**
	 * Changes the server address.
	 * 
	 * @param address The new server address.
	 */
	protected void setAddress(final String address) {
		this.address = address;
	}
	
	/**
	 * Changes the server password.
	 * 
	 * @param password The new server password.
	 */
	protected void setPassword(final String password) {
		this.password = password;
	}
	
	/**
	 * Changes the server port.
	 * 
	 * @param port The new server port.
	 */
	protected void setPort(final int port) {
		this.port = port;
	}
	
	/**
	 * Changes whether this server is using SSL.
	 * 
	 * @param secure Whether this server is using SSL.
	 */
	protected void setSecure(final boolean secure) {
		this.secure = secure;
	}
}
