package server.server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.stereotype.Component;

/*
 * Programmer: Jose Angel Morena
 * MAIN APPLICATION 
 */
@SpringBootApplication
public class App {
   public static void main(String[] args) {
      SpringApplication.run(App.class, args);
   }
   
   /*
    * Serve in port 80
    */
   @Component
   public class CustomContainer implements EmbeddedServletContainerCustomizer {
       public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
          configurableEmbeddedServletContainer.setPort(80);
       }

   }
}