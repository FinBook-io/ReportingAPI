package io.finbook.mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mail {

	private static final String HOST = "smtp.1and1.es";
	private static final String PORT = "25";

	private static final String SENDER_EMAIL = "no_reply@finbook.io";
	private static final String PWD = "}fy-+ryYA{emYhGP5wyu";
	private static final String SENDER_USER = "m87609418-145383889";

	private Properties properties;

	public Mail() {
		properties =  System.getProperties();
		setUpProperties();
	}

	private void setUpProperties(){
		properties.put("mail.smtp.host", HOST);
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.port", PORT);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.user", SENDER_USER);
		properties.put("mail.smtp.clave", PWD);
	}

	public void sendMail(String userMail) {
		Session session = Session.getDefaultInstance(properties);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(SENDER_EMAIL));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(userMail));
			message.setSubject("Finbook reporting");
			message.setText("Hello, here you have what you asked at the finbook reporting module");
			Transport transport = session.getTransport("smtp");
			transport.connect(HOST, SENDER_USER, PWD);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (MessagingException me) {
			me.printStackTrace();
		}

	}
}
