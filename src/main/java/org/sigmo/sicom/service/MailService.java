/*
 * Copyright (c) 2016 SIGMO. Todos os direitos reservados.
 * Este software é confidencial e um produto proprietário do grupo de pesquisa da UFSC - SIGMO.
 * Qualquer uso não autorizado, reprodução ou transferência deste software é terminantemente proibida.
 */
package org.sigmo.sicom.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.sigmo.sicom.entity.Subscriber;
import org.sigmo.sicom.exception.BusinessException;
import org.sigmo.sicom.exception.ExceptionMessage;

/**
 * <p>
 * <b>Descrição da Classe:</b>
 * <br>Classe MailService.
 * <p>
 * <b>Forma de Uso:</b>
 * <br>
 * Esta classe é responsavel por implementar os servicos de envio de e-mail da aplicação.
 *
 * @author Eduardo Mallmann <eduardo.mallmann@sippulse.com>
 */
@Stateless
public class MailService extends BaseService<Subscriber> {

    @Resource(name = "mail/SicomGmailService")
    private Session mailSession;

    @EJB
    private SubscriberService subscriberService;

    public void sentPasswordByEmail(final String email) throws BusinessException {

        //recupera o usuário que esqueceu a senha
        Subscriber subscriber = this.subscriberService.findByEmail(email);
        Date date = new Date();
        String dayFormat = new SimpleDateFormat("dd/MM/yyyy").format(date);
        String timeFormat = new SimpleDateFormat("HH:mm").format(date);

        try {
            //Criando a sessão da mensagem
            Message msg = new MimeMessage(mailSession);
            //Inserindo o assunto do e-mail
            String subject = "Senha Esquecida Sigmo";
            msg.setSubject(subject);
            // define os destinatarios
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(subscriber.getEmail()));
            //monta o corpo da mensagem
            StringBuffer bodyMessage = new StringBuffer();
            /*
             * HTML CONFIG
             */
            bodyMessage.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" ");
            bodyMessage.append(" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
            bodyMessage.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
            /*
             * HTML HEAD
             */
            bodyMessage.append("<head>");
            bodyMessage.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
            bodyMessage.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />");
            bodyMessage.append("<title>SICOM ESQUECI A SENHA E-MAIL</title>");
            /*
             * ESTILOS
             */
            bodyMessage.append("<style type=\"text/css\" media=\"screen\">");
            bodyMessage.append(".ExternalClass { display: block !important; width: 100%; }");
            bodyMessage.append(".ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font, ");
            bodyMessage.append(".ExternalClass td, .ExternalClass div { line-height: 100%; }");
            bodyMessage.append("body, p, h1, h2, h3, h4, h5, h6 { margin: 0; padding: 0; }");
            bodyMessage.append("body, p, td { font-family: Arial, Helvetica, sans-serif; font-size: 15px; ");
            bodyMessage.append("color: #333333; line-height: 1.5em; }");
            bodyMessage.append("h1 { font-size: 24px; font-weight: normal;  line-height: 24px; }");
            bodyMessage.append("body, p{margin-bottom:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;}");
            bodyMessage.append("img { outline: none; text-decoration: none; -ms-interpolation-mode: bicubic; }");
            bodyMessage.append("a img { border: none; }");
            bodyMessage.append(".background { background-color: #333333; }");
            bodyMessage.append("table.background { margin: 0; padding: 0; width: 100% !important; }");
            bodyMessage.append(".block-img { display: block;  line-height: 0; }");
            bodyMessage.append("a { color: white; text-decoration: none; }");
            bodyMessage.append("a, a:link { color: #2A5DB0; text-decoration: underline; }");
            bodyMessage.append("table td { border-collapse: collapse; }");
            bodyMessage.append("td { vertical-align: top; text-align: left; }");
            bodyMessage.append(".wrap { width: 600px; }");
            bodyMessage.append(".wrap-cell { padding-top: 30px; padding-bottom: 30px; }");
            bodyMessage.append(".header-cell, .body-cell, .footer-cell { padding-left: 20px; padding-right: 20px; }");
            bodyMessage.append(".header-cell { background-color: #eeeeee; font-size: 24px; color: #ffffff; }");
            bodyMessage.append(".body-cell { background-color: #ffffff; padding-top: 30px; padding-bottom: 34px; }");
            bodyMessage.append(".footer-cell { background-color: #eeeeee; text-align: center; font-size: 13px; ");
            bodyMessage.append("padding-top: 30px; padding-bottom: 30px; }");
            bodyMessage.append(".card { width: 400px; margin: 0 auto; }");
            bodyMessage.append(".data-heading { text-align: right; padding: 10px; background-color: #ffffff; ");
            bodyMessage.append("font-weight: bold; }");
            bodyMessage.append(".data-value { text-align: left; padding: 10px; background-color: #ffffff; }");
            bodyMessage.append(".force-full-width { width: 100% !important; }");
            bodyMessage.append("</style>");
            bodyMessage.append("<style type=\"text/css\" media=\"only screen and (max-width: 600px)\">");
            bodyMessage.append("@media only screen and (max-width: 600px) {");
            bodyMessage.append("body[class*=\"background\"], table[class*=\"background\"], td[class*=\"background\"] ");
            bodyMessage.append("{ background: #eeeeee !important; }");
            bodyMessage.append("table[class=\"card\"] { width: auto !important; }");
            bodyMessage.append("td[class=\"data-heading\"], td[class=\"data-value\"] { display: block !important; }");
            bodyMessage.append("td[class=\"data-heading\"] { text-align: left !important; padding: 10px 10px 0; }");
            bodyMessage.append("table[class=\"wrap\"] { width: 100% !important; }");
            bodyMessage.append("td[class=\"wrap-cell\"] { padding-top: 0 !important; padding-bottom: 0 !important; }");
            bodyMessage.append("}");
            bodyMessage.append("</style>");
            bodyMessage.append("</head>");
            /*
             * CORPO DO E-MAIL
             */
            bodyMessage.append("<body leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" ");
            bodyMessage.append("offset=\"0\" bgcolor=\"\" class=\"background\">");
            bodyMessage.append("<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" ");
            bodyMessage.append("height=\"100%\" width=\"100%\" class=\"background\">");
            bodyMessage.append("<tr>");
            bodyMessage.append("<td align=\"center\" valign=\"top\" width=\"100%\" class=\"background\">");
            bodyMessage.append("<center>");
            bodyMessage.append("<table cellpadding=\"0\" cellspacing=\"0\" width=\"600\" class=\"wrap\">");
            bodyMessage.append("<tr>");
            bodyMessage.append("<td valign=\"top\" class=\"wrap-cell\" style=\"padding-top:30px; ");
            bodyMessage.append("padding-bottom:30px;\">");
            bodyMessage.append("<table cellpadding=\"0\" cellspacing=\"0\" class=\"force-full-width\">");
            
            bodyMessage.append("<tr>");
            bodyMessage.append("<td height=\"60\" style=\"text-align: center\" valign=\"top\" class=\"header-cell\">");
            bodyMessage.append("<img src=\"https://s3-sa-east-1.amazonaws.com/sigmo-email/marca-sicom\" ");
            bodyMessage.append("alt=\"III - SICOM\">");
            bodyMessage.append("</td>");
            bodyMessage.append("</tr>");
            
            bodyMessage.append("<tr>");
            bodyMessage.append("<td valign=\"top\" style=\"padding-top:15px;\"class=\"body-cell\">");
            bodyMessage.append("<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#ffffff\">");
            
            bodyMessage.append("<tr>");
            bodyMessage.append("<td valign=\"top\" style=\"padding-bottom:15px; background-color:#ffffff;\">");
            bodyMessage.append("<h1>Olá ").append(subscriber.getFullName()).append(",</h1>");
            bodyMessage.append("</td>");
            bodyMessage.append("</tr>");
            
            bodyMessage.append("<tr>");
            bodyMessage.append("<td valign=\"top\" style=\"padding-bottom:20px; background-color:#ffffff;\">");
            bodyMessage.append("Conforme solicitado em ").append(dayFormat).append(" às ").append(timeFormat);
            bodyMessage.append(", segue sua senha de acesso a inscrição nos eventos parte de nosso seminário.");
            bodyMessage.append("<br>");
            bodyMessage.append("<b>Senha: ").append(subscriber.getUnencryptedPassword()).append("</b>");
            bodyMessage.append("</td>");
            bodyMessage.append("</tr>");
            
            bodyMessage.append("<tr>");
            bodyMessage.append("<td>");
            bodyMessage.append("<table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#ffffff\">");
            bodyMessage.append("<tr>");
            bodyMessage.append("<td style=\"width:200px;background:#104b62;\">");
            bodyMessage.append("<div>");
            bodyMessage.append("<!--[if mso]>");
            bodyMessage.append("<v:rect xmlns:v=\"urn:schemas-microsoft-com:vml\" ");
            bodyMessage.append("xmlns:w=\"urn:schemas-microsoft-com:office:word\" ");
            bodyMessage.append("href=\"#\" style=\"height:40px;v-text-anchor:middle;width:200px;\" stroke=\"f\" ");
            bodyMessage.append("fillcolor=\"#104b62\">");
            bodyMessage.append("<w:anchorlock/>");
            bodyMessage.append("<center>");
            bodyMessage.append("<![endif]-->");
            bodyMessage.append("<a href=\"http://sigmo.org/sicom\"");
            bodyMessage.append("style=\"background-color:#104b62;color:#ffffff;display:inline-block;");
            bodyMessage.append("font-family:sans-serif;font-size:18px;line-height:40px;text-align:center;");
            bodyMessage.append("text-decoration:none;width:200px;-webkit-text-size-adjust:none;\">");
            bodyMessage.append("Acesse o Sicom");
            bodyMessage.append("</a>");
            bodyMessage.append("<!--[if mso]>");
            bodyMessage.append("</center>");
            bodyMessage.append("</v:rect>");
            bodyMessage.append("<![endif]-->");
            bodyMessage.append("</div>");
            bodyMessage.append("</td>");
            bodyMessage.append("<td width=\"360\" style=\"background-color:#ffffff; font-size:0; line-height:0;\">&nbsp;</td>");
            bodyMessage.append("</tr>");
            bodyMessage.append("</table>");
            bodyMessage.append("</td>");
            bodyMessage.append("</tr>");
            
            bodyMessage.append("<tr>");
            bodyMessage.append("<td style=\"padding-top:20px;background-color:#ffffff;\">");
            bodyMessage.append("Muito obrigado por participar de nossos eventos,");
            bodyMessage.append("<br>");
            bodyMessage.append("Grupo Sigmo.");
            bodyMessage.append("</td>");
            bodyMessage.append("</tr>");
            
            bodyMessage.append("</table>");
            bodyMessage.append("</td>");
            bodyMessage.append("</tr>");
            
            bodyMessage.append("<tr>");
            bodyMessage.append("<td valign=\"top\" class=\"footer-cell\">");
            bodyMessage.append("Sigmo - UFSC");
            bodyMessage.append("<br>");
            bodyMessage.append("Grupo de Pesquisa");
            bodyMessage.append("</td>");
            bodyMessage.append("</tr>");
            
            bodyMessage.append("</table>");
            bodyMessage.append("</td>");
            bodyMessage.append("</tr>");
            bodyMessage.append("</table>");
            bodyMessage.append("</center>");
            bodyMessage.append("</td>");
            bodyMessage.append("</tr>");
            bodyMessage.append("</table>");
            bodyMessage.append("</body>");
            bodyMessage.append("</html>");
            
            //define o conteudo da mensagem
            msg.setContent(bodyMessage.toString(),
                           "text/html; charset=UTF-8");
            //executa o envio
            Transport.send(msg);

        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), new ExceptionMessage("error.mail.sent"));
        }

    }
    
    public Session getMailSession() {
        return mailSession;
    }

    public void setMailSession(Session mailSession) {
        this.mailSession = mailSession;
    }

}
