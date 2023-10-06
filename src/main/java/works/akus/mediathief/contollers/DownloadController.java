package works.akus.mediathief.contollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import works.akus.mediathief.objects.Link;
import works.akus.mediathief.stealer.PlatformManager;

@Controller
@RequestMapping("/")
public class DownloadController {

	@ModelAttribute
	public void createFields(Model model) {
		model.addAttribute("duration", "");
		model.addAttribute("name", "");
		model.addAttribute("author", "");
	}
	
	@ModelAttribute(name = "link")
	public Link getLink() {
		return new Link();
	}

	@PostMapping
	public String processLink(Link link, Model model) {
		if (link.getUrl().isEmpty())
			return "index";
		link.setMeta(PlatformManager.i.getMetadata(link.getUrl()));
		model.addAttribute("duration", link.getMeta().getDuration());
		model.addAttribute("name", link.getMeta().getName());
		model.addAttribute("author", link.getMeta().getAuthor());
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
