package de.helfenkannjeder.come2help.server.util.mail.exception;

public class SMTPServerException extends RuntimeException {

	private static final long serialVersionUID = 4800159977973311499L;

	public SMTPServerException(String message) {
		super(message);
	}
}
