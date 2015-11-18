package de.helfenkannjeder.come2help.server.util.mail;

import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import de.helfenkannjeder.come2help.server.util.mail.exception.SMTPServerException;

@Component
public class SMTPServerTLS implements SMTPServer {
	
	private static final String PROP_SMTP_AUTH			= "mail.smtp.auth";
	private static final String PROP_SMTP_TLS_ENABLED	= "mail.smtp.starttls.enable";
	private static final String PROP_SMTP_HOST			= "mail.smtp.host";
	private static final String PROP_SMTP_PORT			= "mail.smtp.port";
	
	@Value("${server.smtp.host}")
	private String host;
	
	@Value("${server.smtp.port}")
	private int port;
	
	@Value("${server.smtp.tls}")
	private boolean tlsEnabled;
	
	@Value("${server.smtp.auth}")
	private boolean authEnabled;
	
	@Value("${server.smtp.username}")
	private String username;
	
	@Value("${server.smtp.password}")
	private String password;
	
	@Value("${server.smtp.sender}")
	private String sender;
	
	private Properties properties;
	private Authenticator authenticator;
	
	@Autowired
	public SMTPServerTLS(Authenticator authenticator) {
		this.authenticator = authenticator;
	}
	
	@Override
	public void configure() {
		this.properties = new Properties();
		this.properties.put(PROP_SMTP_AUTH, authEnabled);
		this.properties.put(PROP_SMTP_TLS_ENABLED, tlsEnabled);
		this.properties.put(PROP_SMTP_HOST, host);
		this.properties.put(PROP_SMTP_PORT, port);
	}

	@Override
	public void send(List<String> recipientStrings, String subject, String text)
			throws SMTPServerException {
		
		if (this.properties == null) {
			configure();
		}
		
		Session session = Session.getInstance(properties, authenticator);
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(sender));
			InternetAddress[] recipients = getRecipients(recipientStrings);
			message.setRecipients(Message.RecipientType.TO, recipients);
			message.setSubject(subject);
			message.setText(text);
			
			Transport.send(message);
		} catch (MessagingException e) {
			throw new SMTPServerException(e.getMessage());
		}
	}

	private InternetAddress[] getRecipients(List<String> recipientStrings) throws AddressException {
		
		InternetAddress[] recipients = new InternetAddress[recipientStrings.size()];
		int i = 0;
		for (String recipientString: recipientStrings) {
			recipients[i] = new InternetAddress(recipientString);
			i++;
		}
		return recipients;
	}

}
