package webj2ee;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class ApacheCommonsMailDemo1 {
	public static void main(String[] args) throws EmailException {		
		Email email = new SimpleEmail();
		email.setHostName("mail.dareway.com.cn");
		email.setSmtpPort(25);
		
		email.setAuthenticator(new DefaultAuthenticator("lzx", "********"));
		
		email.setFrom("lzx@dareway.com.cn");
		email.setSubject("A very important email.");
		email.setMsg("Hello, I am webj2ee, good to see you.");
		email.addTo("webj2ee@126.com");
		
		email.send();
	}
}
