package works.akus.mediathief.stealer.platforms;

import com.github.kiulian.downloader.Config;
import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.videos.VideoDetails;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import works.akus.mediathief.stealer.Metadata;
import works.akus.mediathief.stealer.PlatformBase;
import works.akus.mediathief.stealer.PlatformInitializer;
import works.akus.mediathief.utils.UrlUtils;

import java.util.Objects;

@PlatformInitializer(
        name = "Youtube",
        urls = {"youtu.be", "youtube.com"})
public class YoutubePlatform implements PlatformBase {

    YoutubeDownloader downloader;

    public YoutubePlatform(){
        downloader = new YoutubeDownloader();
        Config config = downloader.getConfig();
        config.setMaxRetries(2);
    }

    @Override
    public Metadata getMetadata(String videoIdOrUrl) {

        String videoId = videoIdOrUrl;
        if(UrlUtils.isURL(videoIdOrUrl)){
            videoId = getId(videoIdOrUrl);
        }

        RequestVideoInfo request = new RequestVideoInfo(videoId);
        Response<VideoInfo> response = downloader.getVideoInfo(request);
        VideoInfo video = response.data();
        VideoDetails details = video.details();

        return new Metadata(
                details.lengthSeconds(),
                details.title(),
                details.author()
        );
    }

    @Override
    public String getId(String url) {
        String domain = UrlUtils.getDomain(url);
        Objects.requireNonNull(domain);

        return switch (domain) {
            case "youtu.be" -> UrlUtils.getLastPath(url);
            case "youtube.com" -> UrlUtils.getParams(url).get("v");
            default -> null;
        };

    }
}
