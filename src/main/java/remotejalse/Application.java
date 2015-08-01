package remotejalse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import remotejalse.service.JALSEService;

@SpringBootApplication
public class Application {

    public static void main(final String[] args) {
	final ApplicationContext cxt = SpringApplication.run(Application.class, args);

	// Start up shared engine for JALSE instances
	cxt.getBean(JALSEService.class).initSharedEngine();
    }
}
