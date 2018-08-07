package webj2ee;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailDemo1 {

	public static void main(String[] args) throws AddressException, MessagingException {
		
		String smtpHost = "mail.dareway.com.cn";
		String smtpPort = "25";
		String user = "lzx";
		String password = "***********";
		
		String from = "lzx@dareway.com.cn";
		String to = "webj2ee@126.com";
		String subject = "A very important email.";
		String msg = "Hello, I am webj2ee, good to see you.";
				
		Properties props = System.getProperties();
		props.setProperty("mail.smtp.host", smtpHost);
		props.setProperty("mail.smtp.port", smtpPort);
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.debug", "true");
		
		Session session = Session.getDefaultInstance(props);

		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		message.setSubject(subject);
		message.setText(msg);

		Transport transport = session.getTransport();
		transport.connect(user, password);
		transport.sendMessage(message, message.getAllRecipients());
        transport.close();
	}
}
