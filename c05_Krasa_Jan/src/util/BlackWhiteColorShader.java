package util;

import model.Vertex;
import transforms.Col;

public class BlackWhiteColorShader implements Shader<Vertex, Col> {

    @Override
    public Col shade(Vertex v) {
        double z = v.getZ();
        return new Col(1 - z, 1 - z, 1 - z);
    }

}
