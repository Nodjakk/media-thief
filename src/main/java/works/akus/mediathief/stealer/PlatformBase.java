package works.akus.mediathief.stealer;

import lombok.AllArgsConstructor;


public interface PlatformBase {

    Metadata getMetadata(String UrlOrId);

    String getId(String url);

    default String[] getUrls(){
        return getClass().getAnnotation(PlatformInitializer.class).urls();
    }

    default String getName(){
        return getClass().getAnnotation(PlatformInitializer.class).name();
    }

}
