package util;

import model.Vertex;
import transforms.Col;
import transforms.Vec2D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TextureShader implements Shader<Vertex, Col> {

    private BufferedImage image = null;

    public TextureShader() {
        //TODO
        // Load Image, use BufferedImage

        try {
            File file = new File("assets//image//MomoMinion.png");
            image = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println("The image was not loaded.");
        }

        int imageWidth = -1;
        int imageHeight = -1;
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();
        System.out.println("Height: " + imageHeight + "   Width: " + imageWidth);

    }

    @Override
    public Col shade(Vertex vertex) {
        vertex.getTexCoord().mul(new Vec2D(500,500));
        //TODO
        // (TexCoordinates) mul by size of the image
        return new Col(image.getRGB((int) vertex.getTexCoord().getX(), (int) vertex.getTexCoord().getY()));
    }

}
