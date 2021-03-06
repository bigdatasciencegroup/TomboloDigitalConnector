package uk.org.tombolo;

import org.junit.Before;
import uk.org.tombolo.core.utils.DatabaseUtils;
import uk.org.tombolo.importer.DownloadUtils;
import uk.org.tombolo.importer.Importer;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public abstract class AbstractTest {

    @Before
    public void clearDatabase() {
        // Ensure we aren't clobbering our production DB
        if(!System.getProperty("environment", "").equals("test")) {
            throw new Error("Not running in test mode. You're going to clobber your database!");
        };

        DatabaseUtils.clearAllData();
    }

    protected void mockDownloadUtils(Importer importer){
        // Make the downloader point to the mocked data cache
        importer.setDownloadUtils(makeTestDownloadUtils());
    }

    protected DownloadUtils makeTestDownloadUtils() {
        return new DownloadUtils("src/test/resources/datacache");
    }

    protected static Properties makeApiKeyProperties() throws IOException {
        Properties apiKeys;
        String filename = ClassLoader.getSystemResource("properties/apikeys.properties").getPath();
        apiKeys = new Properties();
        apiKeys.load(new FileReader(filename));
        return apiKeys;
    }
}
