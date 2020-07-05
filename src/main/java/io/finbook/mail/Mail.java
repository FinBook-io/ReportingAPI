package io.finbook.mail;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

public class Mail {

	private static final String HOST = "smtp.1and1.es";
	private static final String PORT = "25";

	private static final String SENDER_EMAIL = "no_reply@finbook.io";
	private static final String PWD = "}fy-+ryYA{emYhGP5wyu";
	private static final String SENDER_USER = "m87609418-145383889";

	private Properties properties;

	public Mail() {
		properties = System.getProperties();
		setUpProperties();
	}

	private void setUpProperties() {
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

	public void sendMailAttachFile(String userMail, String filename) {
		String pathToFile = "src/main/resources/public/finbook/files/temp/".concat(filename);

		Session session = Session.getDefaultInstance(properties);

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(SENDER_EMAIL));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(userMail));
			message.setSubject("Finbook reporting");

			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setText("Hello, here you have what you asked at the finbook reporting module");

			MimeBodyPart messageBodyPart2 = new MimeBodyPart();

			DataSource source = new FileDataSource(pathToFile);
			messageBodyPart2.setDataHandler(new DataHandler(source));
			messageBodyPart2.setFileName(filename);

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			multipart.addBodyPart(messageBodyPart2);

			message.setContent(multipart);

			Transport transport = session.getTransport("smtp");
			transport.connect(HOST, SENDER_USER, PWD);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();

			File fileAttached = new File(pathToFile);
			if (fileAttached.delete()) {
				System.out.println("File deleted successfully");
			} else {
				System.out.println("Failed to delete the file");
			}

		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}

}
