package es.uji.ei1027.ControlDeAcceso;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.logging.Logger;


@SpringBootApplication
public class ControlAccesoApplication {

	private static final Logger log = Logger.getLogger(ControlAccesoApplication.class.getName());

	public static void main(String[] args) {
		new SpringApplicationBuilder(ControlAccesoApplication.class).run(args);
	}
}