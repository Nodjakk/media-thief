package works.akus.mediathief.stealer.platforms;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import com.github.kiulian.downloader.Config;
import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.YoutubeProgressCallback;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.videos.VideoDetails;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.Format;

import works.akus.mediathief.stealer.DownloadTask;
import works.akus.mediathief.stealer.Metadata;
import works.akus.mediathief.stealer.PlatformBase;
import works.akus.mediathief.stealer.PlatformInitializer;
import works.akus.mediathief.utils.TextUtils;
import works.akus.mediathief.utils.UrlUtils;

@PlatformInitializer(
        name = "Youtube",
        urls = {"youtu.be", "youtube.com"})
public class YoutubePlatform implements PlatformBase {

    YoutubeDownloader downloader;

    public YoutubePlatform(){
        downloader = new YoutubeDownloader();
        Config config = downloader.getConfig();
        config.setMaxRetries(2);

        videoDirectory.mkdirs();
        bufferTimer = new Timer();
    }

    // Can't put formats in metadata because of university of metadata.
    // It should be used not only within YouTube
    HashMap<String, VideoInfo> videoInfoBuffer = new HashMap<>();

    Timer bufferTimer;
    HashMap<String, TimerTask> bufferTasks = new HashMap<>();

    @Override
    public Metadata getMetadata(String videoIdOrUrl) {

        String videoId = getVideoId(videoIdOrUrl);

        RequestVideoInfo request = new RequestVideoInfo(videoId);
        Response<VideoInfo> response = downloader.getVideoInfo(request);
        VideoInfo video = response.data();
        VideoDetails details = video.details();

        Metadata data = new Metadata(
                details.lengthSeconds(),
                details.title(),
                details.author(),
                details.thumbnails().get(details.thumbnails().size()-1)
        );

        videoInfoBuffer.put(videoId, video);
        deleteFromBuffer(60 * 60, videoId);

        return data;
    }

    private void deleteFromBuffer(int delay, String videoId){

        if(!videoInfoBuffer.containsKey(videoId)) return;

        TimerTask pastTask = bufferTasks.get(videoId);
        if(pastTask != null) pastTask.cancel();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                bufferTasks.remove(videoId);
                videoInfoBuffer.remove(videoId);
            }
        };

        bufferTimer.schedule(task, delay * 1000L);
    }

    public String getVideoId(String videoIdOrUrl){
        String videoId = videoIdOrUrl;
        if(UrlUtils.isURL(videoIdOrUrl)){
            videoId = getId(videoIdOrUrl);
        }

        return videoId;
    }

    File videoDirectory = new File("download/youtube");



    @Override
    public void download(String videoIdOrUrl, DownloadTask task){
        String videoId = getVideoId(videoIdOrUrl);
        VideoInfo info = videoInfoBuffer.get(videoId);
        if(info == null) {
            getMetadata(videoId);
            info = videoInfoBuffer.get(videoId);
        }

        Format format = info.bestVideoWithAudioFormat();

        RequestVideoFileDownload request = new RequestVideoFileDownload(format)
                .callback(new YoutubeProgressCallback<File>() {
                    @Override
                    public void onDownloading(int progress) {
                        task.onProgress(progress);
                    }

                    @Override
                    public void onFinished(File videoInfo) {
                        task.onComplete(videoInfo);
                        System.out.println("Video Downloaded: " + videoInfo.getName());
                    }

                    @Override
                    public void onError(Throwable throwable) {
                       task.onFailed(throwable);
                       System.out.println("Video Download failed: " + throwable.getLocalizedMessage());
                    }
                })
                .saveTo(videoDirectory)
                .renameTo(TextUtils.transliterate(info.details().title()))
                .async();
        downloader.downloadVideoFile(request);
    }

    @Override
    public File downloadSync(String videoIdOrUrl) {
        String videoId = getVideoId(videoIdOrUrl);
        VideoInfo info = videoInfoBuffer.get(videoId);
        if(info == null) {
            getMetadata(videoId);
            info = videoInfoBuffer.get(videoId);
        }

        Format format = info.bestVideoWithAudioFormat();

        RequestVideoFileDownload request = new RequestVideoFileDownload(format).saveTo(videoDirectory)
                .renameTo(TextUtils.transliterate(info.details().title()));
        return downloader.downloadVideoFile(request).data();
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
