package works.akus.mediathief;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import works.akus.mediathief.stealer.PlatformManager;

@SpringBootApplication
public class MediaThiefApplication {

	public static void main(String[] args) {
		new PlatformManager();

		SpringApplication.run(MediaThiefApplication.class, args);
	}

}
