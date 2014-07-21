/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.mail;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.catalina.tribes.util.Arrays;
import org.apache.log4j.Logger;

/**
 * javax.mail.Session - ver context.xml
 * http://tomcat.apache.org/tomcat-7.0-doc/jndi-resources-howto.html#JavaMail_Sessions
 *
 * @author David Valdivieso <david.valdivieso@interkont.co>
 * @since July 15th 2014
 */
public class MailFonade {

    Logger log = Logger.getLogger(MailFonade.class);
    private Context initCtx;
    private Context envCtx;
    private Session session;

    public MailFonade() {
        try {
            log.info("Inicializando MailFonade");
            init();
        } catch (Exception e) {
            log.error("ERROR: " + e.getMessage());
        }
    }

    private void init() throws NamingException {
        initCtx = new InitialContext();
        envCtx = (Context) initCtx.lookup("java:comp/env");
        session = (Session) envCtx.lookup("mail/FONADE");
        final Properties props = session.getProperties();
        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(props.getProperty("mail.smtp.user"), props.getProperty("mail.smtp.password"));
            }
        });
        session.setDebug(new Boolean(props.getProperty("debug")));
    }

    /**
     * Este método se encarga de enviar un correo. Usa JNDI (ver context.xml)
     * @param to Arreglo de Strings con las direcciones a las que se le va a enviar el correo
     * @param subject Asunto del correo
     * @param body Cuerpo del mensaje (HTML)
     */
    public void sendMail(String[] to, String subject, String body) {
        MailSender mailSender = new MailSender(session, to, subject, body);
        Thread mailThread = new Thread(mailSender);
        mailThread.start();
    }

    /**
     *
     * @author David Valdivieso <david.valdivieso@interkont.co>
     * @since July 15th 2014
     */
    class MailSender implements Runnable {

        Logger log = Logger.getLogger(MailSender.class);

        private final Session session;
        private final String[] recipients;
        private final String subject;
        private final String body;

        public MailSender(Session session, String[] recipients, String subject, String body) {
            this.session = session;
            this.recipients = recipients;
            this.subject = subject;
            this.body = body;
        }

        @Override
        public void run() {
            log.info("Enviando correo en nuevo hilo.... ");
            try {
                sendMail(session, recipients, subject, body);

            } catch (Exception e) {
                log.error("ERROR: " + e.getMessage());
            }
        }

        public void sendMail(Session session, String[] to, String subject, String body) throws MessagingException {
            log.debug("Mail to: " + Arrays.toString(to));
            log.debug("Mail subject: " + subject);
            log.debug("Mail body: " + body);
            Message message = new MimeMessage(session);
            InternetAddress[] recipients = new InternetAddress[to.length];
            for (int i = 0; i < to.length; i++) {
                recipients[i] = new InternetAddress(to[i]);
            }
            message.setRecipients(Message.RecipientType.TO, recipients);
            message.setSubject(subject);
            message.setContent(body, "text/html; charset=utf-8");
            Transport.send(message);
        }

    }

}
