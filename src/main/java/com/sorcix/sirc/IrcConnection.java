/*
 * IrcConnection.java
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

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;

/**
 * Main IRC Connection class in sIRC.
 * <p>
 * sIRC acts as a layer between an IRC server and java applications. It provides
 * an event-driven architecture to handle common IRC events.
 * </p>
 * 
 * @author Sorcix
 */
public class IrcConnection {

	/** The sIRC about string, used in CTCP */
	public static String ABOUT = "Sorcix Lib-IRC (sIRC) v" + IrcConnection.VERSION;
	/** Debug: Show raw messages */
	protected static final boolean DEBUG_MSG = false;
	/** Whether this library should call garbage collection. */
	protected static final boolean GARBAGE_COLLECTION = false;
	/** sIRC Library version. */
	public static final String VERSION = "1.1.4";
	/** Advanced listener. */
	private AdvancedListener advancedListener = null;
	/** Connection InputStream thread. */
	private IrcInput in = null;
	/** Outgoing message delay. (Flood control) */
	private int messageDelay = 100;
	/** Message listeners. */
	private final List<MessageListener> messageListeners;
	/** Mode listeners. */
	private final List<ModeListener> modeListeners;
	/** Connection OutputStream thread. */
	protected IrcOutput out = null;
	/** Server listeners. */
	private final List<ServerListener> serverListeners;
	/** Services. */
	private final List<SIRCService> services;
	/** Connection socket. */
	private Socket socket = null;
	/** Custom version string. */
	private String version = null;
	/** The server this IrcConnection is connected to. */
	private IrcServer server;
	/** IRC IrcServer state. */
	private final ClientState state;
	/** Whether we're connected or not. */
	private boolean connected;
	/** The Character set to use for encoding the connection */
	private Charset charset = Charset.defaultCharset();
	/** End line character. */
	protected static final String ENDLINE = "\n";
	/** Whether to allow server redirection (bounce) or not. */
	private boolean bounceAllowed = false;

	/**
	 * Creates a new IrcConnection object.
	 */
	public IrcConnection() {
		this(null, IrcServer.DEFAULT_PORT, null);
	}

	/**
	 * Creates a new IrcConnection object.
	 * 
	 * @param server
	 *            Server address.
	 */
	public IrcConnection(final String server) {
		this(server, IrcServer.DEFAULT_PORT, null);
	}

	/**
	 * Creates a new IrcConnection object.
	 * 
	 * @param server
	 *            Server address.
	 * @param port
	 *            Port number to connect to.
	 */
	public IrcConnection(final String server, final int port) {
		this(server, port, null);
	}

	/**
	 * Creates a new IrcConnection object.
	 * 
	 * @param server
	 *            Server address.
	 * @param port
	 *            Port number to connect to
	 * @param password
	 *            The password to use.
	 */
	public IrcConnection(final String server, final int port,
			final String password) {
		this.server = new IrcServer(server, port, password, false);
		this.serverListeners = new Vector<ServerListener>(4);
		this.messageListeners = new Vector<MessageListener>(4);
		this.modeListeners = new Vector<ModeListener>(2);
		this.services = new Vector<SIRCService>(0);
		this.state = new ClientState();
	}

	/**
	 * Creates a new IrcConnection object.
	 * 
	 * @param server
	 *            Server address.
	 * @param password
	 *            The password to use.
	 */
	public IrcConnection(final String server, final String password) {
		this(server, IrcServer.DEFAULT_PORT, password);
	}

	/**
	 * Adds a message listener to this IrcConnection.
	 * 
	 * @param listener
	 *            The message listener to add.
	 */
	public void addMessageListener(final MessageListener listener) {
		if ((listener != null) && !this.messageListeners.contains(listener)) {
			this.messageListeners.add(listener);
		}
	}

	/**
	 * Adds a mode listener to this IrcConnection. Note that adding mode
	 * listeners will cause sIRC to check every incoming mode change for
	 * supported modes. Modes can also be read by using
	 * {@link ServerListener#onMode(IrcConnection, Channel, User, String)} which
	 * can be a lot faster for reading modes.
	 * 
	 * @param listener
	 *            The mode listener to add.
	 */
	public void addModeListener(final ModeListener listener) {
		if ((listener != null) && !this.modeListeners.contains(listener)) {
			this.modeListeners.add(listener);
		}
	}

	/**
	 * Adds a server listener to this IrcConnection.
	 * 
	 * @param listener
	 *            The server listener to add.
	 */
	public void addServerListener(final ServerListener listener) {
		if ((listener != null) && !this.serverListeners.contains(listener)) {
			this.serverListeners.add(listener);
		}
	}

	/**
	 * Add and load a service. {@code IrcConnection} will call the
	 * {@link SIRCService#load(IrcConnection)} method of this
	 * {@code SIRCService} after adding it to the service list.
	 * 
	 * @param service
	 *            The service to add.
	 */
	public void addService(final SIRCService service) {
		if ((service != null) && !this.services.contains(service)) {
			this.services.add(service);
			service.load(this);
		}
	}

	/**
	 * Sends the MOTD command to the server, which makes the server send us the
	 * Message of the Day. (Through ServerListener)
	 * 
	 * @see ServerListener#onMotd(IrcConnection, String)
	 * @since 1.0.2
	 */
	public void askMotd() {
		this.out.send(IrcPacketFactory.createMOTD());
	}

	/**
	 * Send a raw command to the IRC server.  Unrecognized responses
	 * are passed to the AdvancedListener's onUnknown() callback.
	 */
	public void sendRaw(final String line) {
        IrcPacket packet = new IrcPacket(line, this);
		this.out.send(packet);
	}
	
	/**
	 * Asks the userlist for a certain channel.
	 * 
	 * @param channel
	 *            The channel to request the userlist for.
	 */
	protected void askNames(final Channel channel) {
		this.out.send(IrcPacketFactory.createNAMES(channel.getName()));
	}

	/**
	 * Closes all streams.
	 */
	private void close() {
		try {
			this.in.interrupt();
			this.out.interrupt();
			// close input stream
			this.in.close();
			// close output stream
			this.out.close();
			// close socket
			if (this.socket.isConnected()) {
				this.socket.close();
			}
		} catch (final Exception ex) {
			// ignore
		}
	}
	
	/**
	 * Connect to the IRC server. You must set the server details and nickname
	 * before calling this method!
	 * 
	 * @throws UnknownHostException
	 *             When the domain name is invalid.
	 * @throws IOException
	 *             When anything went wrong while connecting.
	 * @throws NickNameException
	 *             If the given nickname is already in use or invalid.
	 * @throws PasswordException
	 *             If the server password is incorrect.
	 * @see #setServer(String, int)
	 * @see #setNick(String)
	 */
	public void connect() throws UnknownHostException, IOException, NickNameException, PasswordException {
		this.connect((SSLContext) null);
	}
	
	/**
	 * Connect to the IRC server. You must set the server details and nickname
	 * before calling this method!
	 * 
	 * @throws UnknownHostException
	 *             When the domain name is invalid.
	 * @throws IOException
	 *             When anything went wrong while connecting.
	 * @throws NickNameException
	 *             If the given nickname is already in use or invalid.
	 * @throws PasswordException
	 *             If the server password is incorrect.
	 * @see #setServer(String, int)
	 * @see #setNick(String)
	 */
	public void connect(SSLContext sslctx) throws UnknownHostException, IOException, NickNameException, PasswordException {
		if (this.server.isSecure()) {
			try {
				if (sslctx == null)
					sslctx = SSLContext.getDefault();
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
			this.connect(sslctx.getSocketFactory());
		} else {
			this.connect(SocketFactory.getDefault());
		}
	}

	/**
	 * Connect to the IRC server. You must set the server details and nickname
	 * before calling this method!
	 * 
	 * @throws UnknownHostException
	 *             When the domain name is invalid.
	 * @throws IOException
	 *             When anything went wrong while connecting.
	 * @throws NickNameException
	 *             If the given nickname is already in use or invalid.
	 * @throws PasswordException
	 *             If the server password is incorrect.
	 * @see #setServer(String, int)
	 * @see #setNick(String)
	 */
	public void connect(SocketFactory sfact) throws UnknownHostException, IOException, NickNameException, PasswordException {
		// check if a server is given
		if ((this.server.getAddress() == null)) {
			throw new IOException("Server address is not set!");
		}
		// connect socket
		if (this.socket == null || !this.socket.isConnected()) {
			Socket socket = sfact.createSocket(this.server.getAddress(), this.server.getPort());
			this.socket = null;
			this.connect(socket);
		} else if (this.socket != null) {
			this.connect(this.socket);
		} else {
			throw new IllegalStateException("invalid socket state");
		}
	}

	/**
	 * Connect to the IRC server. You must set the server details and nickname
	 * before calling this method!
	 * 
	 * @throws UnknownHostException
	 *             When the domain name is invalid.
	 * @throws IOException
	 *             When anything went wrong while connecting.
	 * @throws NickNameException
	 *             If the given nickname is already in use or invalid.
	 * @throws PasswordException
	 *             If the server password is incorrect.
	 * @see #setServer(String, int)
	 * @see #setNick(String)
	 * @since 1.0.0
	 */
	public void connect(Socket sock) throws UnknownHostException, IOException, NickNameException, PasswordException {
		boolean reconnecting = true;
		// don't even try if nickname is empty
		if ((this.state.getClient() == null) || this.state.getClient().getNick().trim().equals("")) {
			throw new NickNameException("Nickname is empty or null!");
		}
		// allows for handling SASL, etc. before doing IRC handshake
		// set to input socket
		if (sock != null && this.socket != sock) {
			this.socket = sock;
			reconnecting = false;
		}
		// open streams
		this.out = new IrcOutput(this, new OutputStreamWriter(this.socket.getOutputStream(), this.charset));
		this.in = new IrcInput(this, new InputStreamReader(this.socket.getInputStream(), this.charset));
		if (!reconnecting) {
			// send password if given
			if (this.server.getPassword() != null) {
				this.out.sendNowEx(IrcPacketFactory.createPASS(this.server
						.getPassword()));
			}
			this.out.sendNowEx(IrcPacketFactory.createUSER(this.state.getClient()
					.getUserName(), this.state.getClient().getNick()));
		}
		this.out.sendNowEx(IrcPacketFactory.createNICK(this.state.getClient()
				.getNick()));
		// wait for reply
		String line = null;
		loop: while ((line = this.in.getReader().readLine()) != null) {
			IrcDebug.log(line);
			final IrcPacket decoder = new IrcPacket(line, this);
			if (decoder.isNumeric()) {
				final int command = decoder.getNumericCommand();
				switch (command) {
				case 1:
				case 2:
				case 3: {
						final String nick = decoder.getArgumentsArray()[0];
						if (!this.state.getClient().getNick().equals(nick))
							this.setNick(nick);
					}; break;
				case 4: // login OK
					break loop;
				case 432:
				case 433: {
						// bad/in-use nickname nickname
						throw new NickNameException("Nickname " + this.state.getClient().getNick() + " already in use or not allowed!");
					} // break; unnecessary due to throw
				case 464: {
						// wrong password
						this.disconnect();
						throw new PasswordException("Invalid password");
					} // break; unnecessary due to throw
				}
			}
			if (line.startsWith("PING ")) {
				this.out.pong(line.substring(5));
			}
		}
		// start listening
		this.in.start();
		this.out.start();
		// we are connected
		this.setConnected(true);
		// send events
		for (final Iterator<ServerListener> it = this.getServerListeners(); it
				.hasNext();) {
			it.next().onConnect(this);
		}
	}

	/**
	 * Creates a {@link Channel} object with given channel name. Note that this
	 * method does not actually create a channel on the IRC server, it just
	 * creates a {@link Channel} object linked to this {@code IrcConnection}. If
	 * the local user is in the channel this method will return a global channel
	 * object containing a user list.
	 * 
	 * @return A {@code Channel} object representing given channel.
	 * @param name
	 *            The channel name, starting with #.
	 * @see Channel#isGlobal()
	 */
	public Channel createChannel(String name) {
		if (Channel.CHANNEL_PREFIX.indexOf(name.charAt(0)) < 0) {
			name = "#" + name;
		}
		if (this.getState().hasChannel(name)) {
			return this.getState().getChannel(name);
		} else {
			return new Channel(name, this, false);
		}
	}

	/**
	 * Creates a {@link User} object with given nickname. This will create a
	 * {@link User} object without any information about modes.
	 * 
	 * @param nick
	 *            The nickname.
	 * @return A {@code User} object representing given user.
	 * @see User#User(String, IrcConnection)
	 */
	public User createUser(final String nick) {
		return new User(nick, this);
	}

	/**
	 * Creates a {@link User} object with given nickname. This will attempt to
	 * retrieve a global {@link User} object for given {@link Channel}
	 * containing information about user modes. If it isn't possible to return a
	 * global {@link User} object, this method will return a new {@link User}.
	 * 
	 * @param nick
	 *            The nickname.
	 * @param channel
	 *            The channel this user is in.
	 * @return A {@code User} object representing given user.
	 */
	public User createUser(final String nick, final String channel) {
		final User empty = this.createUser(nick);
		if (this.getState().hasChannel(channel)
				&& this.getState().getChannel(channel).hasUser(nick)) {
			return this.getState().getChannel(channel).getUser(nick);
		} else {
			return empty;
		}
	}

	/**
	 * Disconnects from the server. In the case a connection to the server is
	 * alive, this method will send the QUIT command and wait for the server to
	 * disconnect us.
	 */
	public void disconnect() {
		this.disconnect(null);
	}

	/**
	 * Disconnects from the server. In the case a connection to the server is
	 * alive, this method will send the QUIT command and wait for the server to
	 * disconnect us.
	 * 
	 * @param message
	 *            The QUIT message to use.
	 */
	public void disconnect(final String message) {
		if (this.isConnected()) {
			this.out.sendNow(IrcPacketFactory.createQUIT(message));
		} else {
			this.close();
			this.getState().removeAll();
			this.garbageCollection();
		}
	}

	/**
	 * Runs garbage collection.
	 */
	protected void garbageCollection() {
		if (IrcConnection.GARBAGE_COLLECTION) {
			System.gc();
		}
	}

	/**
	 * Gives the advanced listener used by this {@code IrcConnection}.
	 * 
	 * @return The advanced listener, or null.
	 */
	protected AdvancedListener getAdvancedListener() {
		return this.advancedListener;
	}

	/**
	 * Gives all channels we're currently in.
	 * 
	 * @return All channels we're currently in.
	 */
	public Iterator<Channel> getChannels() {
		return this.getState().getChannels();
	}

	/**
	 * Returns the character set that is used for the connection's encoding. The
	 * default is the system default returned by
	 * {@link Charset#defaultCharset()}.
	 * 
	 * @return The character set for the connection's encoding.
	 */
	public Charset getCharset() {
		return this.charset;
	}

	/**
	 * Returns the client used by this {@code IrcConnection}.
	 * 
	 * @return User representing this client.
	 */
	public User getClient() {
		return this.state.getClient();
	}

	/**
	 * Returns the outgoing message delay in milliseconds.
	 * 
	 * @return Outgoing message delay in milliseconds.
	 */
	public int getMessageDelay() {
		return this.messageDelay;
	}

	/**
	 * Returns all {@link MessageListener}s registered with this IrcConnection.
	 * 
	 * @return All {@code MessageListeners}.
	 */
	protected Iterator<MessageListener> getMessageListeners() {
		return this.messageListeners.iterator();
	}

	/**
	 * Returns all {@link ModeListener}s registered with this IrcConnection.
	 * 
	 * @return All {@code ModeListeners}.
	 */
	protected Iterator<ModeListener> getModeListeners() {
		return this.modeListeners.iterator();
	}

	/**
	 * Returns the output thread used for sending messages through this
	 * {@code IrcConnection}.
	 * 
	 * @return The {@code IrcOutput} used to send messages.
	 */
	protected IrcOutput getOutput() {
		return this.out;
	}

	/**
	 * Returns the server this {@code IrcConnection} connects to.
	 * 
	 * @return The IRC server.
	 */
	public IrcServer getServer() {
		return this.server;
	}

	/**
	 * Gives the server address this {@code IrcConnection} is using to connect.
	 * 
	 * @return Server address.
	 * @since 1.0.0
	 */
	public String getServerAddress() {
		return this.server.getAddress();
	}

	/**
	 * Returns all {@link ServerListener}s registered with this IrcConnection.
	 * 
	 * @return All {@code ServerListeners}.
	 */
	protected Iterator<ServerListener> getServerListeners() {
		return this.serverListeners.iterator();
	}

	/**
	 * Gives the port number this {@code IrcConnection} is using to connect.
	 * 
	 * @return Port number
	 * @since 1.0.0
	 */
	public int getServerPort() {
		return this.server.getPort();
	}

	/**
	 * Returns all services running on this IrcConnection.
	 * 
	 * @return All running services.
	 */
	private Iterator<SIRCService> getServices() {
		return this.services.iterator();
	}

	/**
	 * Retrieves the {@link ClientState} for this {@code IrcConnection}.
	 * 
	 * @return The {@link ClientState}.
	 * @since 1.1.0
	 */
	public ClientState getState() {
		return this.state;
	}

	/**
	 * Gives the version string used.
	 * 
	 * @return The version string.
	 * @since 0.9.4
	 */
	protected String getVersion() {
		if (this.version != null) {
			return this.version;
		}
		return IrcConnection.ABOUT;
	}

	/**
	 * Returns whether this connection is allowed to be redirected.
	 * 
	 * @return {@code true} if redirection is allowed, {@code false} otherwise.
	 */
	public boolean isBounceAllowed() {
		return this.bounceAllowed;
	}

	/**
	 * Checks whether the client is still connected.
	 * 
	 * @return True if the client is connected, false otherwise.
	 */
	public boolean isConnected() {
		return this.connected;
	}

	/**
	 * Checks if given {@link User} object represents us.
	 * 
	 * @param user
	 *            {@code User} to check
	 * @return True if given {@code User} represents us, false otherwise.
	 */
	public boolean isUs(final User user) {
		return user.equals(this.state.getClient());
	}

	/**
	 * Checks whether this connection is using SSL.
	 * 
	 * @return True if this connection is using SSL, false otherwise.
	 */
	public boolean isUsingSSL() {
		return this.server.isSecure();
	}

	/**
	 * Calls {@link #removeService(SIRCService)} for all registered services.
	 * 
	 * @see #removeService(SIRCService)
	 */
	public void removeAllServices() {
		if (this.services.size() > 0) {
			for (final Iterator<SIRCService> it = this.getServices(); it
					.hasNext();) {
				this.removeService(it.next());
			}
		}
	}

	/**
	 * Removes a message listener from this IrcConnection.
	 * 
	 * @param listener
	 *            The message listener to remove.
	 */
	public void removeMessageListener(final MessageListener listener) {
		if ((listener != null) && this.messageListeners.contains(listener)) {
			this.messageListeners.remove(listener);
		}
	}

	/**
	 * Removes a mode listener from this IrcConnection.
	 * 
	 * @param listener
	 *            The mode listener to remove.
	 */
	public void removeModeListener(final ModeListener listener) {
		if ((listener != null) && this.modeListeners.contains(listener)) {
			this.modeListeners.remove(listener);
		}
	}

	/**
	 * Removes a server listener from this IrcConnection.
	 * 
	 * @param listener
	 *            The server listener to remove.
	 */
	public void removeServerListener(final ServerListener listener) {
		if ((listener != null) && this.serverListeners.contains(listener)) {
			this.serverListeners.remove(listener);
		}
	}

	/**
	 * Remove a service. {@code IrcConnection} will call the
	 * {@link SIRCService#unload(IrcConnection)} method of this
	 * {@code SIRCService} after removing it the service list.
	 * 
	 * @param service
	 *            The service to remove.
	 */
	public void removeService(final SIRCService service) {
		if ((service != null) && !this.services.contains(service)) {
			service.unload(this);
			this.services.remove(service);
		}
	}

	/**
	 * Sets the advanced listener used in this {@code IrcConnection}.
	 * 
	 * @param listener
	 *            The advanced listener to use, or {@code null}.
	 */
	public void setAdvancedListener(final AdvancedListener listener) {
		this.advancedListener = listener;
	}

	/**
	 * Marks you as away on the server. If any user sends a message to you while
	 * marked as away, the the server will send them a message back.
	 * 
	 * @param reason
	 *            The reason for being away.
	 * @see #setNotAway()
	 * @since 1.0.2
	 */
	public void setAway(final String reason) {
		this.out.send(IrcPacketFactory.createAWAY(reason));
	}

	/**
	 * Sets whether this connection is allowed to be redirected. If {@code true}
	 * , sIRC will change server when it receives a bounce reply.
	 * 
	 * @param bounceAllowed
	 *            {@code true} if redirection is allowed, {@code false}
	 *            otherwise.
	 */
	public void setBounceAllowed(final boolean bounceAllowed) {
		this.bounceAllowed = bounceAllowed;
	}

	/**
	 * Sets the character set to use for the connections's encoding. If a
	 * connection is already open, it will need to be closed then reopened
	 * before any encoding changes will take effect.
	 * 
	 * @param charset
	 *            The character set to use for the connection's encoding.
	 */
	public void setCharset(final Charset charset) {
		this.charset = charset;
	}

	/**
	 * Changes the connection state of the client.
	 * 
	 * @param connected
	 *            Whether we are still connected.
	 */
	public void setConnected(final boolean connected) {
		this.connected = connected;
	}

	/**
	 * Sets the outgoing message delay in milliseconds. Note that sending a lot
	 * of messages in a short period of time might cause the server to
	 * disconnect you. The default is 1 message each 100ms.
	 * 
	 * @param messageDelay
	 *            The outgoing message delay in milliseconds.
	 */
	public void setMessageDelay(final int messageDelay) {
		if (messageDelay < 0) {
			throw new IllegalArgumentException(
					"Message Delay can't be negative!");
		}
		this.messageDelay = messageDelay;
	}

	/**
	 * Changes the nickname of this client. While connected, this method will
	 * attempt to change the nickname on the server.
	 * 
	 * @param nick
	 *            New nickname.
	 */
	public void setNick(final String nick) {
		if (!this.isConnected()) {
			if (nick != null) {
				if (this.state.getClient() == null) {
					this.state.setClient(new User(nick, nick, null, null, this));
					return;
				}
				this.state.getClient().setNick(nick);
			}
		} else {
			this.out.sendNow(IrcPacketFactory.createNICK(nick));
		}
	}

	public void setUsername(final String username) {
		setUsername(username, null);
	}
	public void setUsername(final String username, final String realname) {
		if (!this.isConnected()) {
			if (username != null) {
				if (this.state.getClient() == null) {
					this.state.setClient(new User(null, username, null, realname, this));
					return;
				}
			}
		}
	}
	
	/**
	 * Removes the away mark.
	 * 
	 * @see #setAway(String)
	 * @since 1.0.2
	 */
	public void setNotAway() {
		this.setAway(null);
	}

	/**
	 * Sets the server details to use while connecting.
	 * 
	 * @param server
	 *            The server to connect to.
	 */
	public void setServer(final IrcServer server) {
		if (!this.isConnected()) {
			this.server = server;
		}
	}

	/**
	 * Sets the server details to use while connecting.
	 * 
	 * @param address
	 *            The address of the server.
	 * @param port
	 *            The port number to use.
	 * @since 1.0.0
	 */
	public void setServer(final String address, final int port) {
		this.setServerAddress(address);
		this.setServerPort(port);
	}

	/**
	 * Sets the server address to use while connecting.
	 * 
	 * @param address
	 *            The address of the server.
	 * @since 1.0.0
	 */
	public void setServerAddress(final String address) {
		if (!this.isConnected() && (address != null)) {
			this.server.setAddress(address);
		}
	}

	/**
	 * Sets the server address to use while connecting.
	 * 
	 * @param port
	 *            The port number to use.
	 */
	public void setServerPort(final int port) {
		if (!this.isConnected() && (port > 0)) {
			this.server.setPort(port);
		}
	}

	/**
	 * Sets whether this connection should use SSL to connect. Note that the
	 * connection will fail if the server has no valid certificate. This
	 * property can only be changed while sIRC is not connected to an IRC
	 * server.
	 * 
	 * @param usingSSL
	 *            True to use SSL, false otherwise.
	 * @see #setServerPort(int)
	 */
	public void setUsingSSL(final boolean usingSSL) {
		if (!this.isConnected()) {
			this.server.setSecure(usingSSL);
		}
	}

	/**
	 * Set the string returned on CTCP VERSION and FINGER commands.
	 * 
	 * @param version
	 *            The string to return on CTCP VERSION and FINGER commands, or
	 *            {@code null} to use the default sIRC version string.
	 * @since 0.9.4
	 */
	public void setVersion(final String version) {
		this.version = version;
	}
}
