package works.akus.mediathief.contollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import works.akus.mediathief.objects.Link;
import works.akus.mediathief.stealer.PlatformManager;

@Controller
@RequestMapping("/")
public class DownloadController {

	@ModelAttribute(name = "link")
	public Link getLink() {
		return new Link();
	}

	@PostMapping
	public String processLink(Link link) {
		if (link.getUrl().isEmpty())
			return "index";
		link.setMeta(PlatformManager.i.getMetadata(link.getUrl()));
		return "index";
	}

	@GetMapping
	public String index() {
		return "index";
	}

	@GetMapping("/pravda")
	public String wtf() {
		return "debil";
	}


}
