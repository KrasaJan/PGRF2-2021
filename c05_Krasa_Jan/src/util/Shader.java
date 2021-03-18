package util;

@FunctionalInterface
public interface Shader<V, C> {

    C shade(V c);

}
