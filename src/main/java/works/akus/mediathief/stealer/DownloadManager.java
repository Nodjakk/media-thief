package works.akus.mediathief.stealer;

import java.io.File;
import java.io.FileReader;

import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.jfposton.ytdlp.DownloadProgressCallback;
import com.jfposton.ytdlp.YtDlp;
import com.jfposton.ytdlp.YtDlpException;
import com.jfposton.ytdlp.YtDlpRequest;

import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;

@Controller
public class DownloadManager {

	private Gson gson = new Gson();

	public static DownloadManager i;

	public DownloadManager() {
		i = this;
	}

	@SneakyThrows
	public Metadata getMetadata(String url, HttpSession session) {
		String fileDirectory = "download/" + session.getId();

		Integer videosRequested = (Integer) session.getAttribute("videosRequested");

		// Build request
		YtDlpRequest request = new YtDlpRequest(url, null);
		request.setOption("output", Integer.toString(videosRequested));
		request.setOption("skip-download");
		request.setOption("no-clean-info-json ");
		request.setOption("write-info-json");
		request.setOption("paths", fileDirectory);
		request.setOption("ignore-errors");

		// Make request
		YtDlp.execute(request);

		session.setAttribute("videosRequested", videosRequested + 1);

		File metaDataFile = new File(fileDirectory + "/" + videosRequested + ".info.json");
		JsonReader reader = new JsonReader(new FileReader(metaDataFile));

		Metadata meta = gson.fromJson(reader, Metadata.class);
		meta.image = meta.getThumbnails().get(meta.getThumbnails().size() - 1).get("url");
		return meta;
	}

	@SneakyThrows
	public void download(String url, DownloadTask task, HttpSession session) {
		String fileDirectory = "download/" + session.getId();

		Integer videosRequested = (Integer) session.getAttribute("videosRequested");

		// Build request
		YtDlpRequest request = new YtDlpRequest(url, null);
		request.setOption("output", Integer.toString(videosRequested) + ".mp4");
		request.setOption("paths", fileDirectory);
		request.setOption("ignore-errors");
		new Thread(() -> {
			try {
				YtDlp.execute(request, new DownloadProgressCallback() {
					@Override
					public void onProgressUpdate(float progress, long etaInSeconds) {
						task.onProgress(progress);
					}
				});
				
				session.setAttribute("videosRequested", videosRequested + 1);

				File videoFile = new File(fileDirectory + "/" + videosRequested + ".mp4");
				task.onComplete(videoFile);

			} catch (YtDlpException e) {
				e.printStackTrace();
			}
		}).start();
	}

}
