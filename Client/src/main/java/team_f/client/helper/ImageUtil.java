package team_f.client.helper;

import javafx.scene.image.Image;
import java.io.InputStream;

public class ImageUtil {
    public static Image getImage(String sourcePath) {
        InputStream input = null;
        try {
            Class c = ImageUtil.class;
            input = c.getResourceAsStream(sourcePath);
            Image img = new Image(input);
            return img;
        } finally {
            closeQuietly(input);
        }
    }

    private static void closeQuietly(InputStream is) {
        try {
            if (is != null) {
                is.close();
            }
        } catch (Exception e) {
        }
    }
}
