package works.akus.mediathief.contollers;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import works.akus.mediathief.objects.Link;
import works.akus.mediathief.stealer.PlatformManager;

@Controller
@RequestMapping("/")
public class HomeController {

	@ModelAttribute
	public void createFields(Model model) {
		model.addAttribute("duration", "");
		model.addAttribute("name", "");
		model.addAttribute("author", "");
		model.addAttribute("img", "");
		model.addAttribute("link-url", "");
		model.addAttribute("contentInactive", true);
	}

	@GetMapping()
	public String processLink(@RequestParam(name = "url", defaultValue = "") String url, Model model, HttpSession session) {
		if (url.isEmpty())
			return "index";
		Link link = new Link();
		session.setAttribute("link-url", url);
		link.setMeta(PlatformManager.i.getMetadata(url));
		model.addAttribute("contentInactive", false);
		model.addAttribute("url", url);
		model.addAttribute("duration", link.getMeta().getDuration());
		model.addAttribute("name", link.getMeta().getName());
		model.addAttribute("author", "by " + link.getMeta().getAuthor());
		model.addAttribute("img", link.getMeta().getImage());
		return "index";
	}

	@PostMapping
	@SneakyThrows
	public ResponseEntity<byte[]> getVideo(HttpSession session) {
		File file = PlatformManager.i.downloadSync((String) session.getAttribute("link-url"));
        byte[] videoResource = Files.readAllBytes(file.toPath());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("video/mp4"))
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=video_%s", file.getName()))
                .body(videoResource);

	}

	@GetMapping("/pravda")
	public String wtf() {
		return "debil";
	}

}
