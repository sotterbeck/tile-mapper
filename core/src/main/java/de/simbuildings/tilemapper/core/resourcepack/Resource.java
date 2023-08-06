package de.simbuildings.tilemapper.core.resourcepack;

public record Resource(String material, String variant, String namespace) {

    private static final String DEFAULT_NAMESPACE = "minecraft";

    public Resource(String material, String variant) {
        this(material, variant, DEFAULT_NAMESPACE);
    }

    public Resource(String material) {
        this(material, material, DEFAULT_NAMESPACE);
    }

    public Resource withNamespace(String namespace) {
        return new Resource(material, variant, namespace);
    }
}
