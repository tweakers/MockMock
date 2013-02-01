package com.sorcix.sirc;

public final class IrcPacketFactory {

	protected static IrcPacket createAWAY(final String reason) {
		return new IrcPacket(null, "AWAY", null, reason);
	}

	protected static IrcPacket createMOTD() {
		return new IrcPacket(null, "MOTD", null, null);
	}

	protected static IrcPacket createNAMES(final String channel) {
		return new IrcPacket(null, "NAMES", channel, null);
	}

	protected static IrcPacket createNICK(final String nick) {
		return new IrcPacket(null, "NICK", nick, null);
	}

	protected static IrcPacket createPASS(final String password) {
		return new IrcPacket(null, "PASS", password, null);
	}

	protected static IrcPacket createQUIT(final String message) {
		return new IrcPacket(null, "QUIT", null, message);
	}

	protected static IrcPacket createUSER(final String username,
			final String realname) {
		return new IrcPacket(null, "USER", username + " Sorcix.com *", realname);
	}

}
