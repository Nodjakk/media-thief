package works.akus.mediathief.contollers;

import java.io.File;
import java.nio.file.Files;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import works.akus.mediathief.objects.Link;
import works.akus.mediathief.stealer.DownloadTask;
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
		model.addAttribute("videoNotDownloading", true);
	}

	@GetMapping()
	public String processLink(@RequestParam(name = "url", defaultValue = "") String url, Model model,
			HttpSession session) {
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

	@GetMapping(params = "requestType=startDownload")
	public ResponseEntity<Integer> startDownloadVideo(HttpSession session, Model model) {
		session.setAttribute("videoProgress", 0);
		if	(session.getAttribute("downloadTask") == null) {
			DownloadTask downloadTask = new DownloadTask() {

				@Override
				public void onProgress(int i) {
					session.setAttribute("videoProgress", i);
					
				}

				@Override
				public void onFailed(Throwable throwable) {

				}

				@Override
				public void onComplete(File file) {
					session.setAttribute("downloadedFile", file);
				}
			};
			PlatformManager.i.download((String) session.getAttribute("link-url"), downloadTask);
			session.setAttribute("downloadTask", downloadTask);
		}

		return ResponseEntity.ok().body(1);
	}
	
	@GetMapping(params = "requestType=getVideoStatus")
	public ResponseEntity<Integer> getVideoStatus(HttpSession session) {
		return ResponseEntity.ok().body((Integer) session.getAttribute("videoProgress"));
	}
	
	@GetMapping("/download")
	@SneakyThrows
	public ResponseEntity<byte[]> getVideo(HttpSession session) {
		File file = (File) session.getAttribute("downloadedFile");
		byte[] videoResource = Files.readAllBytes(file.toPath());

		session.removeAttribute("videoProgress");
		session.setAttribute("downloadTask", null);
		return ResponseEntity.ok().contentType(MediaType.parseMediaType("video/mp4"))
				.header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=video_%s", file.getName()))
				.body(videoResource);
	}

	@GetMapping("/pravda")
	public String wtf() {
		return "debil";
	}

}
