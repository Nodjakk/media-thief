package works.akus.mediathief.stealer;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.jfposton.ytdlp.DownloadProgressCallback;
import com.jfposton.ytdlp.YtDlp;
import com.jfposton.ytdlp.YtDlpRequest;
import com.jfposton.ytdlp.YtDlpResponse;

import lombok.SneakyThrows;
import works.akus.mediathief.utils.UrlUtils;

@Controller
public class PlatformManager {

    public static PlatformManager i;
    public List<PlatformBase> platformList;

    public PlatformManager(){
        i = this;
        ProcessClasses();
    }

    public Metadata getMetadata(String url, String sessionId){
        return getPlatform(url).getMetadata(url);
    }
    
    public Metadata getMetadata(String url){
        return getMetadata(url,"default");
    }

    @SneakyThrows
    public void download(String url, DownloadTask task){
    	String videoUrl = "https://youtu.be/9W44XMcaIhA?si=HeQiKBNIkSagiZrk";
    	DownloadManager.i.getMetadata(videoUrl, "1");

    	// Build request
    	YtDlpRequest request = new YtDlpRequest(videoUrl, null);
    	request.setOption("skip-download");
    	request.setOption("no-clean-info-json ");
    	request.setOption("write-info-json");
    	request.setOption("paths", "download");
    	request.setOption("ignore-errors");
    	request.setOption("retries", 10);	

    	// Make request and return response
    	YtDlpResponse response = YtDlp.execute(request, new DownloadProgressCallback() {
            @Override
            public void onProgressUpdate(float progress, long etaInSeconds) {
                System.out.println(String.valueOf(progress) + "%");
            }
        });
    	System.out.println(response.getOut());
    	System.out.println(1);

        //getPlatform(url).download(url, task);
    }
    public File downloadSync(String url){
        return getPlatform(url).downloadSync(url);
    }

    public PlatformBase getPlatform(String url){
        String domain = UrlUtils.getDomain(url);

        for(PlatformBase platform : platformList){
            if(Arrays.stream(platform.getUrls()).anyMatch(s -> s.equals(domain))){
                return platform;
            }
        }

        return null;
    }

    private void ProcessClasses(){
        platformList = new ManagerProcessor().process();
    }


}
