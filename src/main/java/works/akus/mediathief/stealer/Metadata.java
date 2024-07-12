package works.akus.mediathief.stealer;

import java.util.HashMap;
import java.util.List;

import lombok.Data;

@Data
public class Metadata {
	
	public Metadata(int duration, String title, String uploader, List<HashMap<String, String>> thumbnails, String filename) {
		this.duration = duration;
		this.title = title;
		this.uploader = uploader;
		this.thumbnails = thumbnails;
		this.image = thumbnails.get(thumbnails.size()-2).get("url");
		this.filename = filename;
	}

    final int duration;
    final String title;
    final String uploader;
    final List<HashMap<String, String>> thumbnails;
    String image;
    final String filename;

}
