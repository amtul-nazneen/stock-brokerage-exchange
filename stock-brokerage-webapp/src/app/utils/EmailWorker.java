package app.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailWorker {

	public static void sendEmail(int code) {
		Logger.info("In EmailWorker, Code:"+code);
		try {
			String to = "qwertyuioplkjhg49@outlook.com";
			String from = "qwertyuioplkjhg49@outlook.com";
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp-mail.outlook.com");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.auth", "true");

			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("qwertyuioplkjhg49@outlook.com", "*****");
				}
			});
			String msgBody = "Temporary Code:" + code;
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from, "NoReply"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to, "Mr. Recipient"));
			msg.setSubject("Stock Exchange Admin: Temporary Code Received");
			msg.setText(msgBody);
			Transport.send(msg);
			Logger.info("Successfully Emailed the Code");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}