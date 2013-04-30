/**
 *
 * @author juan--d-_-b
 */
package cobra;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class RedimensionarImagen implements Serializable {

    public static void scale(String src, int width, int height, String dest) throws IOException {
        BufferedImage bsrc = ImageIO.read(new File(src));

        int heightViejo = bsrc.getHeight();
        int widthViejo = bsrc.getWidth();

        height = (width * heightViejo) / widthViejo;

        BufferedImage bdest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bdest.createGraphics();
        AffineTransform at = AffineTransform.getScaleInstance((double) width / bsrc.getWidth(), (double) height / bsrc.getHeight());
        g.drawRenderedImage(bsrc, at);
        ImageIO.write(bdest, "JPG", new File(dest));
    }
}