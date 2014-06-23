package utilitaires;

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
import beans.Etudiant;
import utilitaires.MailConfigurationException;

public class Mail 
{
	private static final String FICHIER_PROPERTIES       = "/utilitaires/mail.properties";
	private static final String PROPERTY_PROTOCOLE       = "protocole";
    private static final String PROPERTY_HOST            = "host";
    private static final String PROPERTY_NOM_UTILISATEUR = "username";
    private static final String PROPERTY_MOT_DE_PASSE    = "password";
    
    /**
     * Envoie un email
     * 
     * @param destinataire
     * @param sujet
     * @param contenu
     */
    private static void envoyerMail(String destinataire, String sujet, String contenu)
    {
    	Properties properties = new Properties();
    	Transport transport = null;
    	String protocole = null;
		String host = null;
	    String nomUtilisateur = null;
	    String motDePasse = null;
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    InputStream fichierProperties = classLoader.getResourceAsStream(FICHIER_PROPERTIES);

	    if (fichierProperties == null) 
	    {
	        throw new MailConfigurationException("Le fichier properties " + FICHIER_PROPERTIES + " est introuvable.");
	    }

	    try 
	    {
	        properties.load(fichierProperties);
	        protocole = properties.getProperty(PROPERTY_PROTOCOLE);
	        host = properties.getProperty(PROPERTY_HOST);
	        nomUtilisateur = properties.getProperty(PROPERTY_NOM_UTILISATEUR);
	        motDePasse = properties.getProperty(PROPERTY_MOT_DE_PASSE);  
	    } 
	    catch (IOException e) 
	    {
	        throw new MailConfigurationException("Impossible de charger le fichier properties " + FICHIER_PROPERTIES, e);
	    }
	     
        properties.setProperty("mail.transport.protocol", protocole); 
        properties.setProperty("mail.smtp.host", host); 
        properties.setProperty("mail.smtp.user", nomUtilisateur); 
        properties.setProperty("mail.from", nomUtilisateur); 
        properties.setProperty("mail.smtp.starttls.enable", "true");
        Session session = Session.getInstance(properties); 
        MimeMessage message = new MimeMessage(session); 
        
        try 
        { 
        	message.setContent(contenu, "text/html; charset=utf-8");
            message.setSubject(sujet); 
            message.addRecipients(Message.RecipientType.TO, destinataire); 
        } 
        catch (MessagingException e) 
        { 
            e.printStackTrace(); 
        }
        
        try 
        { 
            transport = session.getTransport("smtp"); 
            transport.connect(nomUtilisateur, motDePasse);
            transport.sendMessage(message, new Address[] { new InternetAddress(destinataire)});
        } 
        catch (MessagingException e)
        { 
            e.printStackTrace(); 
        } 
        finally 
        { 
            try 
            { 
                if (transport != null) transport.close();    
            } 
            catch (MessagingException e) 
            { 
                e.printStackTrace(); 
            } 
        } 
    } 
    
    /**
     * Envoie les identifiants de connexion d'un étudiant par mail
     * 
     * @param etudiant
     * @
     */
    public static void envoyerMotDePasseEtudiant(Etudiant etudiant, String motDePasse)
    {
    	String sujet = "Bienvenue sur ZPareo";
    	String contenu = "<html><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'></head><style type='text/css'>body, .content{	margin:0 auto; 	background:#ffffff; 	letter-spacing: 0pt; 	width:100%;	max-width:650px; 	font-family:'Helvetica Neue'; 	font:'Helvetica Neue'; 	font-size:13px; 	color:#707277;}body h4{ 	display:block;    font:'helvetica Neue'; 	font-size:16px;	color:#454545; 	height:30px;	margin:0 15px;	border-bottom:1px solid #e3e3e3; }a{ 	text-decoration:none; 	text-align: center; 	color:#39F;}.content{	width:auto;	margin:0 15px;	line-height:20px;}.gras{	color:#26282b;}</style><body>	<br/>	<h4>Bienvenue sur ZPareo</h4>	<div class='content'>	<p>Bonjour <span class='gras'>Marc</span>,<br/><br/> Un compte étudiant a été crée pour vous sur ZPareo. Vous pouvez dés maintenant vous connecter dessus pour consulter vos notes en cliquant sur : <a href='http://localhost:8080/ZPareo/connexion'>Accéder à mon compte</a><br/><br/>identifiant : <span class='gras'>" + etudiant.getAdresseMail() + "</span><br>mot de passe : <span class='gras'> " + motDePasse + "</span><br/><br/>Cordialement,<br/>Administration ZPareo</div></p></body></html";
    	
    	envoyerMail(etudiant.getAdresseMail(), sujet, contenu);
    }
}