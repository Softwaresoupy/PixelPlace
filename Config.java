import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties props = new Properties();
    
    static {
        try (InputStream input = Config.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            props.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config", e);
        }
    }
    
    public static String getGoogleMapsKey() {
        return props.getProperty("google.maps.api.key");
    }
}
