package model;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    private final List<Element> elements;

    public Scene() {
        elements = new ArrayList<>();
    }

    public List<Element> getElements() {
        return elements;
    }

    public void addElement(Element element) {
        elements.add(element);
    }

    public void removeElement(Element element) {
        elements.remove(element);
    }
}
