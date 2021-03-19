package shader;

import model.Vertex;
import transforms.Col;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TextureShader implements Shader<Vertex, Col> {

    private BufferedImage image = null;
    private int imageWidth = -1;
    private int imageHeight = -1;

    public TextureShader() {

        try {
            File file = new File("c05_Krasa_Jan/resources/assets/image/MomoMinion.png");
            image = ImageIO.read(file);
            imageWidth = image.getWidth() - 1;
            imageHeight = image.getHeight() - 1;
        } catch (IOException e) {
            System.out.println("The image was not loaded.");
        }

    }

    @Override
    public Col shade(Vertex vertex) {
        if ((vertex.getTexCoord().getX() < 0) || (vertex.getTexCoord().getY() < 0)) {
            return vertex.getColor();
        }
        return new Col(image.getRGB((int) (vertex.getTexCoord().getX() * imageWidth), (int) (vertex.getTexCoord().getY() * imageHeight)));
    }

}
