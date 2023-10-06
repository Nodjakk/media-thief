package works.akus.mediathief.objects;

import lombok.Data;
import works.akus.mediathief.stealer.Metadata;

@Data
public class Link {
	
	private String url;
	private Metadata meta;
	
}
