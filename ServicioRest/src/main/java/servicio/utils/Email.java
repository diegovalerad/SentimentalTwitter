package servicio.utils;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Clase encargada de enviar correos
 * @author Diego Valera Duran
 *
 */
public class Email {
	/**
	 * Método encargado de enviar un correo desde la dirección
	 * de correo del servidor hasta un destinatario.
	 * <p>
	 * En el correo, se enviará un enlace que permitirá al
	 * usuario receptor validar su correo para el registro
	 * @param to Email al que enviar el correo.
	 */
	public static void enviarCorreoValidacion(String to) {
		Properties props = new Properties();
		try {
			props.load(Email.class.getResourceAsStream("/email.properties"));
			
			final String username = props.getProperty("username");
			final String password = props.getProperty("password");
			final String titulo = props.getProperty("titulo");
			String cuerpo = props.getProperty("cuerpo");
			cuerpo += to;
			
			try {
				Session session = Session.getDefaultInstance(props, new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

				// -- Create a new message --
				Message msg = new MimeMessage(session);

				// -- Set the FROM and TO fields --
				msg.setFrom(new InternetAddress(username));
				msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
				msg.setSubject(titulo);
				msg.setText(cuerpo);
				msg.setSentDate(new Date());
				Transport.send(msg);
			} catch (MessagingException e) {
				System.err.println("Error al enviar el mensaje: " + e);
			}
		} catch (IOException e) {
			System.err.println("Error al acceder al fichero de configuración de Email: " + e);
		}	
	}
}
