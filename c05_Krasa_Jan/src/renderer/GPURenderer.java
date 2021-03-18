package renderer;

import model.Element;
import model.Vertex;
import transforms.Col;
import transforms.Mat4;
import util.Shader;

import java.util.List;

public interface GPURenderer {

    void draw(List<Element> elements, List<Integer> indexBuffer, List<Vertex> vertexBuffer);

    void clear();

    void setModel(Mat4 model);

    void setView(Mat4 view);

    void setProjection(Mat4 projection);

    void setShader(Shader<Vertex, Col> shader);

}
