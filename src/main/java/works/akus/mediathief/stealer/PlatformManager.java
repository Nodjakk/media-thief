package works.akus.mediathief.stealer;

import org.springframework.stereotype.Controller;
import works.akus.mediathief.utils.UrlUtils;

import java.util.Arrays;
import java.util.List;

@Controller
public class PlatformManager {

    public static PlatformManager i;
    public List<PlatformBase> platformList;

    public PlatformManager(){
        i = this;
        ProcessClasses();
    }

    public Metadata getMetadata(String url){
        return getPlatform(url).getMetadata(url);
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
