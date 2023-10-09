package works.akus.mediathief.stealer;

import lombok.AllArgsConstructor;

import java.util.concurrent.CompletableFuture;


public interface PlatformBase {

    Metadata getMetadata(String UrlOrId);

    void download(String videoIdOrUrl, DownloadTask task);

    String getId(String url);

    default String[] getUrls(){
        return getClass().getAnnotation(PlatformInitializer.class).urls();
    }

    default String getName(){
        return getClass().getAnnotation(PlatformInitializer.class).name();
    }

}
