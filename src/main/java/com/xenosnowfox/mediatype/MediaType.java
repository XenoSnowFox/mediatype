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
     * Registration tree the subtype belongs to. Defaults to the standards tree.
     */
    private RegistrationTree registrationTree = RegistrationTree.STANDARDS;

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
     *      If the given string violates RFC standards.
     */
    public MediaType(final String mediaType) throws MediaTypeSyntaxException {
        String[] parameters = mediaType.split(";");

        // item 0 is the media type data
        String[] parts = parameters[0].split("/", 2);
        if (parts.length != 2) {
            throw new MediaTypeSyntaxException(mediaType, "Invalid Syntax", 0);
        }

        // type
        this.type = parts[0].trim().toLowerCase();
        if (this.type.length() == 0) {
            throw new MediaTypeSyntaxException(mediaType, "No top-level type information provided.", 0);
        }

        // extract the sub-type for parsing
        this.subType = parts[1].trim().toLowerCase();

        // determine the suffix
        parts = this.subType.split("\\+");
        if (parts.length > 2) {
            throw new MediaTypeSyntaxException(mediaType, "Too many suffixes provided");
        } else if (parts.length == 2) {
            this.subType = parts[0].trim();
            this.suffix = parts[1].trim();
        }

        // determine the registration tree and sub type
        String[] subTypeParts = this.subType.split("\\.", 2);
        if (subTypeParts.length > 1) {
            RegistrationTree tree = RegistrationTree.withPrefix(subTypeParts[0] + ".");
            if (tree != null) {
                this.registrationTree = tree;
                this.subType = subTypeParts[1];
            }
        }

        // loop over remaining parameters
        for(int i = 1; i < parameters.length; i++) {

            parts = parameters[i].split("=", 2);
            if (parts.length != 2) {
                throw new MediaTypeSyntaxException(mediaType, "Invalid parameter value");
            }

            // parse the value
            String value = parts[1].trim();
            if (value.startsWith("\"") && value.endsWith("\"")) {
                value = value.substring(1, value.length() - 1);
            }
            this.parameterMap.put(parts[0].trim().toLowerCase(), value);
        }
    }

    /**
     * Constructs a Media Type from the given components.
     *
     * @param type
     *      The top level type.
     * @param subType
     *      The specific sub type.
     */
    public MediaType(final String type, final String subType) {
        this(type, subType, null);
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
     */
    public MediaType(final String type, final String subType, final String suffix) {
        this(RegistrationTree.STANDARDS, type, subType, suffix);

        // determine the registration tree and sub type
        String[] subTypeParts = subType.split("\\.", 2);
        if (subTypeParts.length > 1) {
            RegistrationTree tree = RegistrationTree.withPrefix(subTypeParts[0] + ".");
            if (tree != null) {
                this.registrationTree = tree;
                this.subType = subTypeParts[1];
            }
        }
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
     */
    public MediaType(final RegistrationTree registrationTree, final String type, final String subType) {
        this(registrationTree, type, subType, null);
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
     */
    public MediaType(final RegistrationTree registrationTree, final String type, final String subType, final String suffix) {
        this.registrationTree = registrationTree;
        this.type = type;
        this.subType = subType;
        this.suffix = suffix;
    }

    /**
     * Obtains the MediaType's top level type information.
     *
     * @return
     *      Top level type.
     */
    public String getType() {
        return type;
    }

    /**
     * Obtains the MediaType's sub type information.
     *
     * @return
     *      Secondary level sub type.
     */
    public String getSubType() {
        return subType;
    }

    /**
     * Obtains the MediaType's suffix information.
     *
     * @return
     *      Suffix or {@code null} if no suffix is defined.
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * Obtains the registration tree the suffix is part of.
     *
     * @return
     *      Registration tree.
     */
    public RegistrationTree getRegistrationTree() {
        return registrationTree;
    }

    /**
     * Adds the specified parameter to the Media Type, overriding any existing value.
     *
     * @param name
     *      name of the parameter
     * @param value
     *      value to store against the parameter
     * @throws NullPointerException
     *      if either the name or value arguments are {@code null}
     * @throws IllegalArgumentException
     *      if the name argument is blank or empty
     */
    public void putParameter(final String name, final String value) {
        if (name == null) {
            throw new NullPointerException("Parameter name cannot be null.");
        }

        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Parameter name not provided.");
        }

        if (value == null) {
            throw new NullPointerException("Parameter value cannot be null.");
        }

        this.parameterMap.put(name.trim().toLowerCase(), value);
    }

    /**
     * Returns the specified parameter value.
     *
     * @param name
     *      Name of the parameter to return.
     * @return
     *      Parameter value or {@code null} if the parameter has not been defined
     */
    public String getParameter(final String name) {
        return this.parameterMap.get(name.trim().toLowerCase());
    }

    @Override
    public boolean equals(final Object object) {
        if (object == null) {
            return false;
        }

        MediaType other;
        if (object instanceof MediaType) {
            other = (MediaType) object;
        } else if(object instanceof String) {
            try {
                other = new MediaType((String) object);
            } catch (Exception ex) {
                return false;
            }
        } else {
            return false;
        }

        return (
                other.type.equalsIgnoreCase(this.type)
                && other.subType.equalsIgnoreCase(this.subType)
                && other.registrationTree == this.registrationTree
                && (
                        (other.suffix != null && other.suffix.equalsIgnoreCase(this.suffix))
                        || (other.suffix == null && this.suffix == null)
                )
                && other.parameterMap.equals(this.parameterMap)
        );
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
            if (value == null) {
                return;
            }

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

