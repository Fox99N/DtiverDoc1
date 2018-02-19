package docreader;

import java.awt.*;
import java.io.File;

/**
 * Created by fox on 13.02.18.
 */
public  class FactoryMetod {

    public AbstractDownload getWriter(Object object) {
        AbstractDownload writer = null;

        if (object instanceof File) {
            writer = new AbstractDownloadFile() {
            };

        } else if (object instanceof Image) {
            writer = new AbstractDownloadImage();

        } else if (object instanceof File) {
            writer = new AbstractDownloadDocument();
        }
        return writer;

    }
}
