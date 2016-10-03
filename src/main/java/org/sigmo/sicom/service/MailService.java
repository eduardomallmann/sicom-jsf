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

    /**
     * Envia email com a senha
     * <p>
     * @param email endereço de e-mail
     * <p>
     * @throws BusinessException caso ocorra erro no envio de e-mail
     */
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

    /**
     * Envia e-mail após cadastro do usuário.
     * <p>
     * @param subscriber usuário cadastrado.
     * <p>
     * @throws BusinessException caso ocorra erro no envio do e-mail.
     */
    public void registerConfirmationMail(Subscriber subscriber) throws BusinessException {

        //recupera o usuário que esqueceu a senha
        Date date = new Date();
        String dayFormat = new SimpleDateFormat("dd/MM/yyyy").format(date);
        String timeFormat = new SimpleDateFormat("HH:mm").format(date);

        try {
            //Criando a sessão da mensagem
            Message msg = new MimeMessage(mailSession);
            //Inserindo o assunto do e-mail
            String subject = "Inscrição III SICOM";
            msg.setSubject(subject);
            // define os destinatarios
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(subscriber.getEmail()));
            //monta o corpo da mensagem
            StringBuffer bodyMessage = new StringBuffer();
            /*
             * HTML CONFIG
             */
            bodyMessage.append("<!DOCTYPE html>");
            bodyMessage.append("<html lang=\"pt\">");
            /*
             * HTML HEAD
             */
            bodyMessage.append("<head>");
            bodyMessage.append("<title>Confirmação de Inscrição SICOM</title>");
            bodyMessage.append("<meta charset=\"utf-8\">");
            bodyMessage.append("<meta name=\"viewport\" content=\"width=device-width\">");
            /*
             * ESTILOS
             */
            bodyMessage.append("<style type=\"text/css\">");
            /*
             * CLIENT-SPECIFIC STYLES
             */
            bodyMessage.append("#outlook a{padding:0;}");// Force Outlook to provide a "view in browser" message
            bodyMessage.append(".ReadMsgBody{width:100%;} .ExternalClass{width:100%;}");// Force Hotmail to display emails at full width
            bodyMessage.append(".ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font,");
            bodyMessage.append(".ExternalClass td, .ExternalClass div {line-height: 100%;}");// Force Hotmail to display normal line spacing
            bodyMessage.append("body, table, td, a{-webkit-text-size-adjust:100%; -ms-text-size-adjust:100%;}");// Prevent WebKit and Windows mobile changing default text sizes
            bodyMessage.append("table, td{mso-table-lspace:0pt; mso-table-rspace:0pt;}");// Remove spacing between tables in Outlook 2007 and up
            bodyMessage.append("img{-ms-interpolation-mode:bicubic;}");// Allow smoother rendering of resized image in Internet Explorer
            /*
             * RESET STYLES
             */
            bodyMessage.append("body{margin:0; padding:0;}");
            bodyMessage.append("img{border:0; height:auto; line-height:100%; outline:none; text-decoration:none;}");
            bodyMessage.append("table{border-collapse:collapse !important;}");
            bodyMessage.append("body{height:100% !important; margin:0; padding:0; width:100% !important;}");
            /*
             * iOS BLUE LINKS
             */
            bodyMessage.append(".appleBody a {color:#68440a; text-decoration: none;}");
            bodyMessage.append(".appleFooter a {color:#999999; text-decoration: none;}");
            /*
             * MOBILE STYLES
             */
            bodyMessage.append("@media screen and (max-width: 525px) {");
            /*
             * ALLOWS FOR FLUID TABLES
             */
            bodyMessage.append("table[class=\"wrapper\"]{ width:100% !important; }");
            /*
             * ADJUSTS LAYOUT OF LOGO IMAGE
             */
            bodyMessage.append("d[class=\"logo\"]{ text-align: left; padding: 20px 0 20px 0 !important; }");
            bodyMessage.append("td[class=\"logo\"] img{ margin:0 auto!important; }");
            /*
             * USE THESE CLASSES TO HIDE CONTENT ON MOBILE
             */
            bodyMessage.append("td[class=\"mobile-hide\"]{ display:none;}");
            bodyMessage.append("img[class=\"mobile-hide\"]{ display: none !important; }");
            bodyMessage.append("img[class=\"img-max\"]{ max-width: 100% !important; height:auto !important; }");
            /*
             * FULL-WIDTH TABLES
             */
            bodyMessage.append("table[class=\"responsive-table\"]{ width:100%!important; }");
            /*
             * UTILITY CLASSES FOR ADJUSTING PADDING ON MOBILE
             */
            bodyMessage.append("td[class=\"padding\"]{ padding: 10px 5% 15px 5% !important; }");
            bodyMessage.append("td[class=\"padding-copy\"]{padding: 10px 5% 10px 5% !important; text-align: center;}");
            bodyMessage.append("td[class=\"padding-meta\"]{ padding: 30px 5% 0px 5% !important; text-align: center; }");
            bodyMessage.append("td[class=\"no-pad\"]{ padding: 0 0 20px 0 !important; }");
            bodyMessage.append("td[class=\"no-padding\"]{ padding: 0 !important; }");
            bodyMessage.append("td[class=\"section-padding\"]{ padding: 50px 15px 50px 15px !important; }");
            bodyMessage.append("td[class=\"section-padding-bottom-image\"]{ padding: 50px 15px 0 15px !important; }");
            /*
             * ADJUST BUTTONS ON MOBILE
             */
            bodyMessage.append("td[class=\"mobile-wrapper\"]{ padding: 10px 5% 15px 5% !important; }");
            bodyMessage.append("table[class=\"mobile-button-container\"]{ margin:0 auto; width:100% !important; }");
            bodyMessage.append("a[class=\"mobile-button\"]{ width:80% !important; padding: 15px !important; ");
            bodyMessage.append("border: 0 !important; font-size: 16px !important; }");
            bodyMessage.append("}");
            bodyMessage.append("</style>");//Fim Estilos
            bodyMessage.append("</head>");//Fim Head
            /*
             * CORPO DO EMAIL
             */
            bodyMessage.append("<body style=\"margin: 0; padding: 0;\">");
            bodyMessage.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">");
            bodyMessage.append("<tr>");
            bodyMessage.append("<td bgcolor=\"#ffffff\" align=\"center\" style=\"padding: 70px 15px 70px 15px;\"");
            bodyMessage.append("class=\"section-padding\">");
            bodyMessage.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"500\" ");
            bodyMessage.append("class=\"responsive-table\">");
            bodyMessage.append("<tr>");
            bodyMessage.append("<td>");
            bodyMessage.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
            /*
             * IMAGEM CABEÇALHO
             */
            bodyMessage.append("<tr>");
            bodyMessage.append("<td>");
            bodyMessage.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
            bodyMessage.append("<tbody>");
            bodyMessage.append("<tr>");
            bodyMessage.append("<td class=\"padding-copy\">");
            bodyMessage.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
            bodyMessage.append("<tr>");
            bodyMessage.append("<td>");
            bodyMessage.append("<a href=\"http://sigmo.org/sicom\" target=\"_blank\"> ");
            bodyMessage.append("<img src=\"https://s3-sa-east-1.amazonaws.com/sigmo-email/banner-sicom.jpg\" ");
            bodyMessage.append("border=\"0\" alt=\"III - SICOM\" style=\"display: block; padding: 0; color: #666666; ");
            bodyMessage.append("text-decoration: none; font-family: Helvetica, arial, sans-serif; font-size: 16px; ");
            bodyMessage.append("width: 500px; height: 200px;\" class=\"img-max\">");
            bodyMessage.append("</a>");
            bodyMessage.append("</td>");
            bodyMessage.append("</tr>");
            bodyMessage.append("</table>");
            bodyMessage.append("</td>");
            bodyMessage.append("</tr>");
            bodyMessage.append("</tbody>");
            bodyMessage.append("</table>");
            bodyMessage.append("</td>");
            bodyMessage.append("</tr>");//Fim imagem cabeçalho
            /*
             * SAUDAÇÃO
             */
            bodyMessage.append("<tr>");
            bodyMessage.append("<td>");
            bodyMessage.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
            bodyMessage.append("<tr>");
            bodyMessage.append("<td align=\"center\" style=\"font-size: 25px; font-family: Helvetica, Arial, ");
            bodyMessage.append("sans-serif; color: #333333; padding-top: 30px;\" class=\"padding-copy\"> ");
            bodyMessage.append("Sua inscrição foi realizada com sucesso!");
            bodyMessage.append("</td>");
            bodyMessage.append("</tr>");//Fim saudação
            /*
             * INFORMAÇÃO DO CADASTRO
             */
            bodyMessage.append("<tr>");
            bodyMessage.append("<td align=\"justify\" style=\"padding: 20px 0 0 0; font-size: 16px; line-height: 25px;");
            bodyMessage.append("font-family: Helvetica, Arial, sans-serif; color: #666666;\" class=\"padding\"> ");
            bodyMessage.append("Olá ").append(subscriber.getFullName()).append(", ");
            bodyMessage.append("você se inscreveu no III Sicom a ser realizado no dia 19 de outubro a partir das 08h30m");
            bodyMessage.append(", com a apresentação dos trabalhos selecionados e finalizado ");
            bodyMessage.append("com a palestra de Gilberto Strunck.");
            bodyMessage.append("</td>");
            bodyMessage.append("</tr>"); //Fim informação do cadastro
            /*
             * CREDENCIAMENTO
             */
            bodyMessage.append("<tr>");
            bodyMessage.append("<td align=\"center\" style=\"padding: 20px 0 0 0; font-size: 25px; line-height: 25px;");
            bodyMessage.append("font-family: Helvetica, Arial, sans-serif; color: #666666;\" class=\"padding-copy\"> ");
            bodyMessage.append("<strong>Atenção!</strong>");
            bodyMessage.append("</td>");
            bodyMessage.append("</tr>");
            bodyMessage.append("<tr>");
            bodyMessage.append("<td align=\"justify\" style=\"padding: 20px 0 0 0; font-size: 16px; line-height: 25px;");
            bodyMessage.append("font-family: Helvetica, Arial, sans-serif; color: #666666;\" class=\"padding\"> ");
            bodyMessage.append("Apenas o credenciamento garantirá o certificado de participação no evento. ");
            bodyMessage.append("O credenciamento ocorrerá no dia do evento, 19 de outubro de 2016, das 08h00 às 10h00.");
            bodyMessage.append(" Após este período não haverá mais credenciamento e a participação será livre, porém");
            bodyMessage.append(" sem direito a certificado.");
            bodyMessage.append("</td>");
            bodyMessage.append("</tr>"); //Fim credenciamento
            bodyMessage.append("</table>");
            bodyMessage.append("</td>");
            bodyMessage.append("</tr>");
            /*
             * BOTÃO ACESSO SICOM
             */
            bodyMessage.append("<tr>");
            bodyMessage.append("<td>");
            bodyMessage.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"mobile-button-container\">");
            bodyMessage.append("<tr>");
            bodyMessage.append("<td align=\"center\" style=\"padding: 25px 0 0 0;\" class=\"padding-copy\">");
            bodyMessage.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"responsive-table\">");
            bodyMessage.append("<tr>");
            bodyMessage.append("<td align=\"center\"> ");
            bodyMessage.append("<a href=\"http://sigmo.org/sicom\" target=\"_blank\" style=\"font-size: 16px; ");
            bodyMessage.append("font-family: Helvetica, Arial, sans-serif; font-weight: normal; color: #ffffff; ");
            bodyMessage.append("text-decoration: none; background-color: #5D9CEC; border-top: 15px solid #5D9CEC; ");
            bodyMessage.append("border-bottom: 15px solid #5D9CEC; border-left: 25px solid #5D9CEC; ");
            bodyMessage.append("border-right: 25px solid #5D9CEC; border-radius: 3px; -webkit-border-radius: 3px; ");
            bodyMessage.append("-moz-border-radius: 3px; display: inline-block;\" class=\"mobile-button\"> ");
            bodyMessage.append("Acesse ao SICOM &rarr;");
            bodyMessage.append("</a>");
            bodyMessage.append("</td>");
            bodyMessage.append("</tr>"); //Fim botão de acesso SICOM
            bodyMessage.append("</table>");
            bodyMessage.append("</td>");
            bodyMessage.append("</tr>");
            bodyMessage.append("</table>");
            bodyMessage.append("</td>");
            bodyMessage.append("</tr>");
            bodyMessage.append("</table>");
            bodyMessage.append("</td>");
            bodyMessage.append("</tr>");
            bodyMessage.append("</table>");
            bodyMessage.append("</td>");
            bodyMessage.append("</tr>");
            bodyMessage.append("</table>"); //Fim Corpo do e-mail
            /*
             * FOOTER
             */
            bodyMessage.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">");
            bodyMessage.append("<tr>");
            bodyMessage.append("<td bgcolor=\"#ffffff\" align=\"center\">");
            bodyMessage.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">");
            bodyMessage.append("<tr>");
            bodyMessage.append("<td style=\"padding: 20px 0px 20px 0px;\">");
            bodyMessage.append("<table width=\"500\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"");
            bodyMessage.append("class=\"responsive-table\">");
            bodyMessage.append("<tr>");
            bodyMessage.append("<td align=\"center\" valign=\"middle\" style=\"font-size: 12px; line-height: 18px; ");
            bodyMessage.append("font-family: Helvetica, Arial, sans-serif; color:#666666;\">");
            bodyMessage.append("<span class=\"appleFooter\" style=\"color:#666666;\">");
            bodyMessage.append("SIGMO - Universidade Federal de Santa Catarina (UFSC), Florianópolis - SC");
            bodyMessage.append("</span>");
            bodyMessage.append("</td>");
            bodyMessage.append("</tr>");
            bodyMessage.append("</table>");
            bodyMessage.append("</td>");
            bodyMessage.append("</tr>");
            bodyMessage.append("</table>");
            bodyMessage.append("</td>");
            bodyMessage.append("</tr>");
            bodyMessage.append("</table>"); //Fim Footer
            bodyMessage.append("</body>"); //Fim Body
            bodyMessage.append("</html>"); //Fim email

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
