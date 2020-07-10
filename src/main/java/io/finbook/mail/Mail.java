package io.finbook.mail;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Properties;

public class Mail {

	private static final String HOST = "smtp.1and1.es";
	private static final String PORT = "25";

	private static final String SENDER_EMAIL = "no_reply@finbook.io";
	private static final String PWD = "}fy-+ryYA{emYhGP5wyu";
	private static final String SENDER_USER = "m87609418-145383889";

	private static final String NEW_LINE = "<br/>";

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

	public void sendMailAttachFile(String userMail, String filename) {
		String pathToFile = "src/main/resources/public/finbook/files/temp/".concat(filename);
		Session session = Session.getDefaultInstance(properties);

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(SENDER_EMAIL));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(userMail));
			message.setSubject("Finbook reporting");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(formatMimeBodyPart(getMainMessage()));
			multipart.addBodyPart(formatMimeBodyPart(getEnviromentMessage()));
			multipart.addBodyPart(attachFile(pathToFile, filename));

			message.setContent(multipart);
			transport(session, message);

			deleteFile(Paths.get(pathToFile));

		} catch (MessagingException | IOException me) {
			me.printStackTrace();
		}
	}

	private static MimeBodyPart formatMimeBodyPart(String text) throws MessagingException {
		MimeBodyPart message = new MimeBodyPart();
		message.setText(text, StandardCharsets.UTF_8.name(), "html");
		return message;
	}

	private static MimeBodyPart attachFile(String pathToFile, String filename) throws MessagingException {
		MimeBodyPart attachFile = new MimeBodyPart();
		DataSource source = new FileDataSource(pathToFile);
		attachFile.setDataHandler(new DataHandler(source));
		attachFile.setFileName(filename);
		return attachFile;
	}

	private static String getMainMessage(){
		return "Dear user," + NEW_LINE + NEW_LINE +
				"Here you have what you asked at the finbook reporting module." + NEW_LINE + NEW_LINE +
				"Best regards!"+ NEW_LINE + NEW_LINE + NEW_LINE;
	}

	private static String getEnviromentMessage(){
		return "Antes de imprimir este correo electrónico, piense bien si es necesario hacerlo: el medio ambiente es una cuestión de todos." +
				NEW_LINE +
				"Please consider the environment before printing this email.";
	}

	private void deleteFile(Path path) throws IOException {
		Files.delete(path);
	}

	private void transport(Session session, MimeMessage message) throws MessagingException {
		Transport transport = session.getTransport("smtp");
		transport.connect(HOST, SENDER_USER, PWD);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}

}
