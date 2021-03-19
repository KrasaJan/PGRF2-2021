package model;

public class Element {

    private final TopologyType topologyType;
    private final int start;                        // Start of IndexBuffer
    private final int count;                        // Amount of indices to use from IndexBuffer

    /**
     * @param topologyType topology type of the element
     * @param start        index of the starting point in the index buffer
     * @param count        how many indices from index buffer to use
     */
    public Element(TopologyType topologyType, int start, int count) {
        this.topologyType = topologyType;
        this.start = start;
        this.count = count;
    }

    public TopologyType getTopologyType() {
        return topologyType;
    }

    public int getStart() {
        return start;
    }

    public int getCount() {
        return count;
    }

}
