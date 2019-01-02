package com.amazon.correo.controladores;

import com.amazon.correo.entidades.Cliente;
import com.sendgrid.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("correo")
public class EnviarCorreoController {

    @GetMapping("nuevoCliente")
    public ResponseEntity registroCliente(@RequestBody Cliente cliente){

        Email from = new Email("drand@amazon.com");
        String subject = "Bienvenido "+cliente.getNombre() + " a Drand";
        Email to = new Email(cliente.getCorreo());
        Content content = new Content("text/plain", " \n Para acceder a su cuenta debe entrar a drand.me y acceder con su correo: " + cliente.getCorreo() + " y su contraseña: " + cliente.getContrasena());
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            try {
                throw ex;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        return new ResponseEntity(HttpStatus.OK);
    }

}
