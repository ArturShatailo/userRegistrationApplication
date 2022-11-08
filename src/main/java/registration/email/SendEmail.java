package registration.email;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;


// Имя файла SendEmail.javaimport java.io. *; импорт java.util. *; Импорт javax.servlet. *; Импорт javax.servlet.http. *; Импорт javax.mail. *; Импорт javax.mail.internet. *; импорт javax.activation. *;
@WebServlet("/sendEmail")
public class SendEmail extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // идентификатор получателя
        String to = "abcd@gmail.com";

        // идентификатор электронной почты отправителя
        String from = "javausageonly@yahoo.com";

        // Предположим, вы отправляете письмо с localhost
        String host = "localhost";

        // Получить свойства системы
        Properties properties = System.getProperties();

        // Настройка почтового сервера
        properties.setProperty("mail.smtp.host", host);

        // Получить объект Session по умолчанию
        Session session = Session.getDefaultInstance(properties);

        // Установить тип содержимого ответа
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try{
            // создаем объект MimeMessage по умолчанию
            MimeMessage message = new MimeMessage(session);
            // Set From: поле заголовка заголовка.
            message.setFrom(new InternetAddress(from));
            // Set To: поле заголовка заголовка.
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));
            // Установить Subject: поле заголовка
            message.setSubject("This is the Subject Line!");
            // установить актуальное сообщение сейчас
            message.setText("This is actual message");
            // отправить сообщение
            Transport.send(message);
            String title = "Отправить письмо";
            String res = "Отправить сообщение успешно ...";
            String docType = "<!DOCTYPE html> \n";
            out.println(docType +
                    "<html>\n" +
                    "<head><title>" + title + "</title></head>\n" +
                    "<body bgcolor=\"#f0f0f0\">\n" +
                    "<h1 align=\"center\">" + title + "</h1>\n" +
                    "<p align=\"center\">" + res + "</p>\n" +
                    "</body></html>");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }}
