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

    @Resource(name = "mail/GmailService")
    private Session mailSession;
    
    @EJB
    private SubscriberService subscriberService;
    
    @EJB
    private SubscriberOrderService subscriberOrderService;
    
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
            /* HTML CONFIG */
            bodyMessage.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" ");
            bodyMessage.append(" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
            bodyMessage.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
            bodyMessage.append("<head>");
            bodyMessage.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
            bodyMessage.append("<meta name=\"viewport\" content=\"width=device-width\"/>");
            /* ESTILOS */
            bodyMessage.append("<style>");
            bodyMessage.append("body{ width:100% !important; min-width: 100%; -webkit-text-size-adjust:100%; ");
            bodyMessage.append(" -ms-text-size-adjust:100%; margin:0; padding:0; }");
            bodyMessage.append("img {  outline:none; text-decoration:none; -ms-interpolation-mode: bicubic;");
            bodyMessage.append(" width: auto;  max-width: 100%; float: left; clear: both; display: block; }");
            bodyMessage.append("center { width: 100%;  min-width: 580px; }");
            bodyMessage.append("a img { border: none; }");
            bodyMessage.append("p {  margin: 0 0 0 10px; }");
            bodyMessage.append("table {  border-spacing: 0;  border-collapse: collapse; }");
            bodyMessage.append("td { word-break: break-word;-webkit-hyphens: auto; -moz-hyphens: auto; hyphens: auto;");
            bodyMessage.append(" border-collapse: collapse !important; }");
            bodyMessage.append("table, tr, td { padding: 0; vertical-align: top; text-align: left; }");
            bodyMessage.append("hr {  color: #d9d9d9; background-color: #d9d9d9; height: 1px; border: none; }");
            bodyMessage.append("table.body { height: 100%; width: 100%; }");
            bodyMessage.append("table.container { width: 580px; margin: 0 auto; text-align: inherit; }");
            bodyMessage.append("table.row { padding: 0px; width: 100%; position: relative; }");
            bodyMessage.append("table.container table.row { display: block; }");
            bodyMessage.append("td.wrapper { padding: 10px 20px 0px 0px; position: relative; }");
            bodyMessage.append("table.columns,");
            bodyMessage.append("table.column { margin: 0 auto; }");
            bodyMessage.append("table.columns td,");
            bodyMessage.append("table.column td { padding: 0px 0px 10px; }");
            bodyMessage.append("table.columns td.sub-columns,");
            bodyMessage.append("table.column td.sub-columns,");
            bodyMessage.append("table.columns td.sub-column,");
            bodyMessage.append("table.column td.sub-column { padding-right: 10px; }");
            bodyMessage.append("td.sub-column, td.sub-columns { min-width: 0px; }");
            bodyMessage.append("table.row td.last,");
            bodyMessage.append("table.container td.last { padding-right: 0px; }");
            bodyMessage.append("table.six { width: 280px; }");
            bodyMessage.append("table.twelve { width: 580px; }");
            bodyMessage.append("table.six center { min-width: 280px; }");
            bodyMessage.append("table.twelve center { min-width: 580px; }");
            bodyMessage.append("table.six .panel center { min-width: 260px; }");
            bodyMessage.append("table.twelve .panel center { min-width: 560px; }");
            bodyMessage.append(".body .columns td.six,");
            bodyMessage.append(".body .column td.six { width: 50%; }");
            bodyMessage.append(".body .columns td.twelve,");
            bodyMessage.append(".body .column td.twelve { width: 100%; }");
            bodyMessage.append("td.expander { visibility: hidden; width: 0px; padding: 0 !important; }");
            bodyMessage.append("table.columns .left-text-pad,");
            bodyMessage.append("table.column .left-text-pad { padding-left: 10px; }");
            bodyMessage.append("table.columns .right-text-pad,");
            bodyMessage.append("table.column .right-text-pad { padding-right: 10px; }");
            bodyMessage.append("table.center, td.center { text-align: center; }");
            bodyMessage.append("body, table.body, h5, p, td { ");
            bodyMessage.append("color: #222222;");
            bodyMessage.append("font-family: \"Helvetica\", \"Arial\", sans-serif; ");
            bodyMessage.append("font-weight: normal; ");
            bodyMessage.append("padding:0; ");
            bodyMessage.append("margin: 0;");
            bodyMessage.append("text-align: left; ");
            bodyMessage.append("line-height: 1.3; }");
            bodyMessage.append("h5 { word-break: normal; }");
            bodyMessage.append("h5 {font-size: 24px;}");
            bodyMessage.append("body, table.body, p, td { font-size: 14px; line-height:19px; }");
            bodyMessage.append("p.lead, p.lede, p.leed { font-size: 18px; line-height:21px; }");
            bodyMessage.append("p { margin-bottom: 10px; }");
            bodyMessage.append("a { color: #2ba6cb; text-decoration: none; }");
            bodyMessage.append("a:hover { color: #2795b6 !important; }");
            bodyMessage.append("a:active { color: #2795b6 !important; }");
            bodyMessage.append("a:visited { color: #2ba6cb !important; }");
            bodyMessage.append(".panel { background: #f2f2f2; border: 1px solid #d9d9d9; padding: 10px !important; }");
            bodyMessage.append("table.tiny-button { width: 100%; overflow: hidden; }");
            bodyMessage.append("table.tiny-button td { display: block; width: auto !important; text-align: center; ");
            bodyMessage.append("background: #2ba6cb; border: 1px solid #2284a1; color: #ffffff; padding: 8px 0; }");
            bodyMessage.append("table.tiny-button td { padding: 5px 0 4px; }");
            bodyMessage.append("table.tiny-button td a { font-weight: bold; text-decoration: none; ");
            bodyMessage.append("font-family: Helvetica, Arial, sans-serif; color: #ffffff; font-size: 16px; }");
            bodyMessage.append("table.tiny-button td a { font-size: 12px; font-weight: normal; }");
            bodyMessage.append("table.tiny-button:hover td { background: #2795b6 !important; }");
            bodyMessage.append("table.tiny-button:hover td a,");
            bodyMessage.append("table.tiny-button:active td a,");
            bodyMessage.append("table.tiny-button td a:visited { color: #ffffff !important; }");
            bodyMessage.append("table.facebook td { background: #3b5998; border-color: #2d4473; }");
            bodyMessage.append("table.facebook:hover td { background: #2d4473 !important; }");
            bodyMessage.append(".callout .wrapper { padding-bottom: 20px; }");
            bodyMessage.append(".callout .panel { background: #ECF8FF; border-color: #b9e5ff; }");
            bodyMessage.append(".header { background: #FFF; }");
            bodyMessage.append(".footer .wrapper { background: #ebebeb; }");
            bodyMessage.append(".footer h5 { padding-bottom: 10px; }");
            bodyMessage.append("table.columns .left-text-pad { padding-left: 10px; }");
            bodyMessage.append("table.columns .right-text-pad { padding-right: 10px; }");
            bodyMessage.append("@media only screen and (max-width: 600px) {");
            bodyMessage.append("table[class=\"body\"] img { width: auto !important; height: auto !important; }");
            bodyMessage.append("table[class=\"body\"] center { min-width: 0 !important; }");
            bodyMessage.append("table[class=\"body\"] .container { width: 95% !important; }");
            bodyMessage.append("table[class=\"body\"] .row { width: 100% !important; display: block !important; }");
            bodyMessage.append("table[class=\"body\"] .wrapper { display: block !important; ");
            bodyMessage.append(" padding-right: 0 !important; }");
            bodyMessage.append("table[class=\"body\"] .columns,");
            bodyMessage.append("table[class=\"body\"] .column {");
            bodyMessage.append("table-layout: fixed !important;");
            bodyMessage.append("float: none !important;");
            bodyMessage.append("width: 100% !important;");
            bodyMessage.append("padding-right: 0px !important;");
            bodyMessage.append("padding-left: 0px !important;");
            bodyMessage.append("display: block !important; }");
            bodyMessage.append("table[class=\"body\"] table.columns td,");
            bodyMessage.append("table[class=\"body\"] table.column td { width: 100% !important; }");
            bodyMessage.append("table[class=\"body\"] .columns td.six,");
            bodyMessage.append("table[class=\"body\"] .column td.six { width: 50% !important; }");
            bodyMessage.append("table[class=\"body\"] .columns td.twelve,");
            bodyMessage.append("table[class=\"body\"] .column td.twelve { width: 100% !important; }");
            bodyMessage.append("table[class=\"body\"] table.columns td.expander { width: 1px !important; }");
            bodyMessage.append("table[class=\"body\"] .right-text-pad { padding-left: 10px !important; }");
            bodyMessage.append("table[class=\"body\"] .left-text-pad { padding-right: 10px !important; }");
            bodyMessage.append("}");
            bodyMessage.append("</style>");
            /* CORPO DO E-MAIL */
            bodyMessage.append("<body>");
            bodyMessage.append("<table class=\"body\">");
            bodyMessage.append("<tr>");
            bodyMessage.append("<td class=\"center\" align=\"center\" valign=\"top\">");
            bodyMessage.append("<center>");
            
            bodyMessage.append("<table class=\"row header\">");
            bodyMessage.append("<tr>");
            bodyMessage.append("<td class=\"center\" align=\"center\">");
            bodyMessage.append("<center>");
            bodyMessage.append("<table class=\"container\">");
            bodyMessage.append("<tr>");
            bodyMessage.append("<td class=\"wrapper last\">");
            bodyMessage.append("<table class=\"twelve columns\">");
            bodyMessage.append("<tr>");
            bodyMessage.append("<td class=\"twelve sub-columns\">");
            bodyMessage.append("<img src=\"https://s3-sa-east-1.amazonaws.com/sigmo-email/marca-sicom\" ");
            bodyMessage.append(" style=\"float: right\">");
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
            
            bodyMessage.append("<table class=\"container\">");
            bodyMessage.append("<tr>");
            bodyMessage.append("<td>");
            
            bodyMessage.append("<table class=\"row\">");
            bodyMessage.append("<tr>");
            bodyMessage.append("<td class=\"wrapper last\">");
            bodyMessage.append("<table class=\"twelve columns\">");
            bodyMessage.append("<tr>");
            bodyMessage.append("<td>");
            bodyMessage.append("<p class=\"lead\">Olá, ").append(subscriber.getFullName()).append("</p>");
            bodyMessage.append("<p>Conforme solicitado em ").append(dayFormat).append(" às ").append(timeFormat);
            bodyMessage.append(", segue sua senha de acesso a inscrição nos eventos parte de nosso seminário.</p>");
            bodyMessage.append("<p>");
            bodyMessage.append("<strong>Senha:</strong>");
            bodyMessage.append(subscriber.getUnencryptedPassword());
            bodyMessage.append("</p>");
            bodyMessage.append("</td>");
            bodyMessage.append("<td class=\"expander\"></td>");
            bodyMessage.append("</tr>");
            bodyMessage.append("</table>");
            bodyMessage.append("</td>");
            bodyMessage.append("</table>");
            bodyMessage.append("</td>");
            bodyMessage.append("</tr>");
            bodyMessage.append("</table>");
            
            bodyMessage.append("<table class=\"row footer\">");
            bodyMessage.append("<tr>");
            
            bodyMessage.append("<td class=\"wrapper\">");
            bodyMessage.append("<table class=\"six columns\">");
            bodyMessage.append("<tr>");
            bodyMessage.append("<td class=\"left-text-pad\">");
            bodyMessage.append("<h5>Fique por dentro:</h5>");
            bodyMessage.append("<table class=\"tiny-button facebook\">");
            bodyMessage.append("<tr>");
            bodyMessage.append("<td>");
            bodyMessage.append("<a href=\"https://www.facebook.com/SigmoUfsc\">Facebook</a>");
            bodyMessage.append("</td>");
            bodyMessage.append("</tr>");
            bodyMessage.append("</table>");
            bodyMessage.append("</td>");
            bodyMessage.append("<td class=\"expander\"></td>");
            bodyMessage.append("</tr>");
            bodyMessage.append("</table>");
            bodyMessage.append("</td>");
            
            bodyMessage.append("<td class=\"wrapper last\">");
            bodyMessage.append("<table class=\"six columns\">");
            bodyMessage.append("<tr>");
            bodyMessage.append("<td class=\"last right-text-pad\">");
            bodyMessage.append("<h5>Contato: </h5>");
            bodyMessage.append("<p>Fone: +55 (48) 3721-6609</p>");
            bodyMessage.append("<p>E-mail: <a href=\"mailto:sigmo.ufsc@gmail.com\">sigmo.ufsc@gmail.com</a></p>");
            bodyMessage.append("</td>");
            bodyMessage.append("<td class=\"expander\"></td>");
            bodyMessage.append("</tr>");
            bodyMessage.append("</table>");
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
}
