package works.akus.mediathief.stealer;

import java.io.File;


public interface PlatformBase {

    Metadata getMetadata(String UrlOrId);

    void download(String videoIdOrUrl, DownloadTask task);

    File downloadSync(String videoIdOrUrl);

    String getId(String url);

    default String[] getUrls(){
        return getClass().getAnnotation(PlatformInitializer.class).urls();
    }

    default String getName(){
        return getClass().getAnnotation(PlatformInitializer.class).name();
    }

}
