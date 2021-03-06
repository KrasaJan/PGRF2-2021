package model;

public class Element {

    private final TopologyType topologyType;
    private final int start;                        // Start of IndexBuffer
    private final int end;                          // End  of IndexBuffer

    /**
     * @param topologyType topology type of the element
     * @param start        index of the starting point in the index buffer
     * @param end          index of the ending point in the index buffer
     */
    public Element(TopologyType topologyType, int start, int end) {
        this.topologyType = topologyType;
        this.start = start;
        this.end = end;
    }

    public TopologyType getTopologyType() {
        return topologyType;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

}
