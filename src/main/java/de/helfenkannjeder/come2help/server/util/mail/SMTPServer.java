package de.helfenkannjeder.come2help.server.util.mail;

import java.util.List;

import de.helfenkannjeder.come2help.server.util.mail.exception.SMTPServerException;

public interface SMTPServer {
	
	public void configure();
	
	public void send(List<String> recipients, String subject, String text)
			throws SMTPServerException;
	
}
