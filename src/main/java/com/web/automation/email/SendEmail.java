package com.web.automation.email;

import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail {

	/**
	 * @description: send email
	 * @param from
	 * @param pass
	 * @param to
	 * @param subject
	 * @param Mailbody
	 * @param attachmentpath
	 * @return
	 */
	public boolean sendEmailReport(String hostName, String port,final String from, final String password, String[] to, String subject, String Mailbody, String attachmentpath) {

		String[] cc={""};
		Properties props = new Properties();
		props.put("mail.smtp.host", hostName);
		props.put("mail.smtp.socketFactory.port", port);
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", port);

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from,password);
			}
		});

		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(from));
			InternetAddress[] toAddress = new InternetAddress[to.length];

			// To get the array of addresses
			for( int i = 0; i < to.length; i++ ) {
				toAddress[i] = new InternetAddress(to[i]);
			}

			for( int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}

			InternetAddress[] addressCC = new InternetAddress[cc.length];
			for (int i = 0; i < cc.length; i++)
			{
				addressCC[i] = new InternetAddress(cc[i]);
			}
			message.setRecipients(Message.RecipientType.CC, addressCC);

			message.setSubject(subject);	
			message.setSentDate(new Date());

			// Create the message part 
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(Mailbody + "\n"+ "\n" + "This is an auto-generated email.");
			MimeBodyPart attachmentPart = new MimeBodyPart();
			DataSource source = new FileDataSource(attachmentpath);
			attachmentPart.setDataHandler(new DataHandler(source));
			attachmentPart.setFileName("AutomationResults.html");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			multipart.addBodyPart(attachmentPart);
			message.setContent(multipart);

			//Send an Email
			Transport.send(message, message.getAllRecipients());

			System.out.println("Email successfully sent to "+Arrays.toString(to));
			return true;
		}
		catch (AddressException ae) {
			ae.printStackTrace();
			System.out.println(ae.getMessage());
			return false;
		}
		catch (MessagingException me) {
			me.printStackTrace();
			System.out.println(me.getMessage());
			return false;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
			return false;
		}
	}
}


/********************  Send Email Way 1 ****************************************/

/**
 * @author Cigniti
 * @description: Send an Email
 * @param from
 * @param pass
 * @param to
 * @param subject
 * @param htmlreportpath
 *//*
public static void sendEmailReport(String from, String pass, String[] to, String subject, String htmlreportpath) {
	BufferedReader reader = null;
	try {
		reader = new BufferedReader(new FileReader(htmlreportpath));
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	StringBuffer fileData = new StringBuffer();
	char[] buf = new char[2048];
	int numRead = 0;
	try {
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
		}
	} catch (IOException e) {
		e.printStackTrace();
		System.out.println(e.getMessage());
	}
	try {
		reader.close();
	} catch (IOException e) {
		e.printStackTrace();
		System.out.println(e.getMessage());
	}
	String body = fileData.toString();

	Properties props = System.getProperties();
	String host = "smtp.gmail.com";
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.host", host);
	props.put("mail.smtp.user", from);
	props.put("mail.smtp.password", pass);
	props.put("mail.smtp.port", "587");
	props.put("mail.smtp.auth", "true");

	Session session = Session.getDefaultInstance(props);
	MimeMessage message = new MimeMessage(session);

	try {
		message.setFrom(new InternetAddress(from));
		InternetAddress[] toAddress = new InternetAddress[to.length];

		// To get the array of addresses
		for (int i = 0; i < to.length; i++) {
			toAddress[i] = new InternetAddress(to[i]);
		}
		for (int i = 0; i < toAddress.length; i++) {
			message.addRecipient(Message.RecipientType.TO, toAddress[i]);
		}
		message.setSubject(subject);
		message.setContent(body, "text/html");
		Transport transport = session.getTransport("smtp");
		transport.connect(host, from, pass);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	} catch (AddressException ae) {
		ae.printStackTrace();
		System.out.println(ae.getMessage());
	} catch (MessagingException me) {
		me.printStackTrace();
		System.out.println(me.getMessage());
	}
}*/


/********************  Send Email Way 2 ****************************************/

/*	*//**
 * @author Cigniti
 * @description: send email
 * @param from
 * @param pass
 * @param to
 * @param subject
 * @param Mailbody
 * @param attachmentpath
 * @return
 *//*
	public boolean sendEmailReport(String from, String pass, String[] to, String subject, String Mailbody, String attachmentpath) {
		Properties props = System.getProperties();
		String host = "smtp.gmail.com";
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(from));
			InternetAddress[] toAddress = new InternetAddress[to.length];

			// To get the array of addresses
			for( int i = 0; i < to.length; i++ ) {
				toAddress[i] = new InternetAddress(to[i]);
			}

			for( int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}
			message.setSubject(subject);	
			// Create the message part 
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setText(Mailbody + "\n"+ "\n" + "This is an auto-generated email.");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			// Put parts in message
			message.setContent(multipart);

			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			System.out.println("Email successfully sent to "+Arrays.toString(to));
			return true;
		}
		catch (AddressException ae) {
			ae.printStackTrace();
			System.out.println(ae.getMessage());
			return false;
		}
		catch (MessagingException me) {
			me.printStackTrace();
			System.out.println(me.getMessage());
			return false;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
			return false;
		}
	}
}*/