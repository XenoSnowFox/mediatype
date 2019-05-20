package com.xenosnowfox.mediatype;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Media Type reference.
 *
 * <pre><code>
 *      type "/" [tree "."] subtype ["+" suffix] *[";" parameter]
 * </code></pre>
 */
public class MediaType {

    /**
     * The top level type string.
     */
    private String type;

    /**
     * Registration tree the subtype belongs to.
     */
    private RegistrationTree registrationTree;

    /**
     * Sub type text string.
     */
    private String subType;

    /**
     * Suffix String.
     */
    private String suffix;

    /**
     * Collection of parameters.
     */
    private Map<String, String> parameterMap = new HashMap<>();

    /**
     * Constructs a Media Type by parsing the given string.
     *
     * @param mediaType
     *      The string to be parsed into a media type.
     * @throws MediaTypeSyntaxException
     *      If the given string violates RFC ????.
     */
    public MediaType(final String mediaType) throws MediaTypeSyntaxException {

    }

    /**
     * Constructs a Media Type from the given components.
     *
     * @param type
     *      The top level type.
     * @param subType
     *      The specific sub type.
     * @throws MediaTypeSyntaxException
     *      If the given string violates RFC ????.
     */
    public MediaType(final String type, final String subType) throws MediaTypeSyntaxException {

    }

    /**
     * Constructs a Media Type from the given components.
     *
     * @param type
     *      The top level type.
     * @param subType
     *      The specific sub type.
     * @param suffix
     *      Addition suffix information
     * @throws MediaTypeSyntaxException
     *      If the given string violates RFC ????.
     */
    public MediaType(final String type, final String subType, final String suffix) throws MediaTypeSyntaxException {

    }

    /**
     * Constructs a Media Type from the given components.
     *
     * @param registrationTree
     *      The registration tree the specified subtype belongs to.
     * @param type
     *      The top level type.
     * @param subType
     *      The specific sub type.
     * @throws MediaTypeSyntaxException
     *      If the given string violates RFC ????.
     */
    public MediaType(final RegistrationTree registrationTree, final String type, final String subType) throws MediaTypeSyntaxException {

    }

    /**
     * Constructs a Media Type from the given components.
     *
     * @param registrationTree
     *      The registration tree the specified subtype belongs to.
     * @param type
     *      The top level type.
     * @param subType
     *      The specific sub type.
     * @param suffix
     *      Addition suffix information
     * @throws MediaTypeSyntaxException
     *      If the given string violates RFC 7231.
     */
    public MediaType(final RegistrationTree registrationTree, final String type, final String subType, final String suffix) throws MediaTypeSyntaxException {
        this.registrationTree = registrationTree;
        this.type = type;
        this.subType = subType;
        this.suffix = suffix;
    }

    public void putParameter(final String name, final String value) {
        this.parameterMap.put(name, value);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.type).append("/").append(this.registrationTree.toString()).append(this.subType);

        // add suffix if provided
        if (this.suffix != null && this.suffix.length() > 0) {
            sb.append("+").append(this.suffix);
        }

        // add parameters
        this.parameterMap.forEach((key, value) -> {
            sb.append("; ").append(key).append("=");

            if (value.contains("\"")) {
                sb.append(
                        value.replace("\\", "\\\\").replace("\"", "\\\"")
                ).append("\"");
            } else {
                sb.append(value);
            }
        });

        return sb.toString();
    }
}

