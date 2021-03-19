package model;

import model.solids.Solid;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    private final List<Solid> solids;

    public Scene() {
        solids = new ArrayList<>();
    }

    public List<Solid> getSolids() {
        return solids;
    }

    public Solid getSolid(Solid solid) {
        return solid;
    }

    public void addSolid(Solid solid) {
        solids.add(solid);
    }

    public void removeSolid(Solid solid) {
        solids.remove(solid);
    }
}
