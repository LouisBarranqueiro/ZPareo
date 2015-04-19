package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import beans.Student;

public class Mail {
    private static final String PROPERTIES_FILE   = "utilities/mail.properties";
    private static final String PROPERTY_PROTOCOL = "protocol";
    private static final String PROPERTY_HOST     = "host";
    private static final String PROPERTY_USERNAME = "username";
    private static final String PROPERTY_PASSWORD = "password";

    /**
     * Send an mail
     * @param recipient
     * @param topic
     * @param content
     */
    private static void sendMail(String recipient, String topic, String content) {
        Properties  properties        = new Properties();
        Transport   transport         = null;
        String      protocol          = null;
        String      host              = null;
        String      username          = null;
        String      password          = null;
        ClassLoader classLoader       = Thread.currentThread().getContextClassLoader();
        InputStream fichierProperties = classLoader.getResourceAsStream(PROPERTIES_FILE);

        if (fichierProperties == null) {
            throw new MailConfigurationException("Le fichier properties " + PROPERTIES_FILE + " est introuvable.");
        }

        try {
            properties.load(fichierProperties);
            protocol = properties.getProperty(PROPERTY_PROTOCOL);
            host = properties.getProperty(PROPERTY_HOST);
            username = properties.getProperty(PROPERTY_USERNAME);
            password = properties.getProperty(PROPERTY_PASSWORD);
        }
        catch (IOException e) {
            throw new MailConfigurationException("Impossible de charger le fichier properties " + PROPERTIES_FILE, e);
        }

        properties.setProperty("mail.transport.protocol", protocol);
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.user", username);
        properties.setProperty("mail.from", username);
        properties.setProperty("mail.smtp.starttls.enable", "true");
        Session     session = Session.getInstance(properties);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setContent(content, "text/html; charset=utf-8");
            message.setSubject(topic);
            message.addRecipients(Message.RecipientType.TO, recipient);
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }

        try {
            transport = session.getTransport("smtp");
            transport.connect(username, password);
            transport.sendMessage(message, new Address[]{new InternetAddress(recipient)});
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (transport != null) {
                    transport.close();
                }
            }
            catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Send student login by mail
     * @param student
     * @param password
     */
    public static void sendStudentPassword(Student student, String password) {
        String topic   = "Bienvenue sur ZPareo";
        String content = "<html><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'></head><style type='text/css'>body, .content{	margin:0 auto; 	background:#ffffff; 	letter-spacing: 0pt; 	width:100%;	max-width:650px; 	font-family:'Helvetica Neue'; 	font:'Helvetica Neue'; 	font-size:13px; 	color:#707277;}body h4{ 	display:block;    font:'helvetica Neue'; 	font-size:16px;	color:#454545; 	height:30px;	margin:0 15px;	border-bottom:1px solid #e3e3e3; }a{ 	text-decoration:none; 	text-align: center; 	color:#39F;}.content{	width:auto;	margin:0 15px;	line-height:20px;}.gras{	color:#26282b;}</style><body>	<br/>	<h4>Bienvenue sur ZPareo</h4>	<div class='content'>	<p>Bonjour <span class='gras'>" + student.getFirstName() + "</span>,<br/><br/> Un compte étudiant a été crée pour vous sur ZPareo. Vous pouvez dés maintenant vous connecter dessus pour consulter vos notes en cliquant sur : <a href='http://localhost:8080/ZPareo/connexion'>Accéder à mon compte</a><br/><br/>Identifiant : <span class='gras'>" + student.getEmailAddress() + "</span><br>Mot de passe : <span class='gras'> " + password + "</span><br/><br/>Cordialement,<br/>Administration ZPareo</div></p></body></html";

        sendMail(student.getEmailAddress(), topic, content);
    }

    /**
     * Send back student login by mail
     * @param student
     * @param password
     */
    public static void sendBackStudentPassword(Student student, String password) {
        String topic   = "Votre nouveau mot de passe sur ZPareo";
        String content = "<html><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'></head><style type='text/css'>body, .content{	margin:0 auto; 	background:#ffffff; 	letter-spacing: 0pt; 	width:100%;	max-width:650px; 	font-family:'Helvetica Neue'; 	font:'Helvetica Neue'; 	font-size:13px; 	color:#707277;}body h4{ 	display:block;    font:'helvetica Neue'; 	font-size:16px;	color:#454545; 	height:30px;	margin:0 15px;	border-bottom:1px solid #e3e3e3; }a{ 	text-decoration:none; 	text-align: center; 	color:#39F;}.content{	width:auto;	margin:0 15px;	line-height:20px;}.gras{	color:#26282b;}</style><body>	<br/>	<h4>ZPareo</h4>	<div class='content'>	<p>Bonjour <span class='gras'>" + student.getFirstName() + "</span>,<br/><br/> Le mot de passe de votre compte ZPareo a été réinitialisé. Vous pouvez de nouveau consulter vos informations et vos notes ici : <a href='http://localhost:8080/ZPareo/connexion'>Accéder à mon compte</a><br/><br/>Identifiant : <span class='gras'>" + student.getEmailAddress() + "</span><br>Mot de passe : <span class='gras'> " + password + "</span><br/><br/>Cordialement,<br/>Administration ZPareo</div></p></body></html";

        sendMail(student.getEmailAddress(), topic, content);
    }
}