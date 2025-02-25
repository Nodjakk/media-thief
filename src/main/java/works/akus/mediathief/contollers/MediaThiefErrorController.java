package works.akus.mediathief.contollers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MediaThiefErrorController implements ErrorController{
	
	private static final String ERROR_MAPPING = "/error";

    @RequestMapping(value = ERROR_MAPPING)
    public ResponseEntity<String> error() {
        return new ResponseEntity<String>(HttpStatus.EXPECTATION_FAILED);
    }

}
