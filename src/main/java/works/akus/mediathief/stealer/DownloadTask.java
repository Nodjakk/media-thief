package works.akus.mediathief.stealer;

import java.io.File;

public interface DownloadTask {

    void onFailed(Throwable throwable);

    void onProgress(float i);

    void onComplete(File file);

}
