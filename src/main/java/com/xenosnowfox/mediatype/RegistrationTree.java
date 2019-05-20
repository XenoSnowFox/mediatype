package com.xenosnowfox.mediatype;

/**
 * Represents the different types of allowable registration trees for subtypes.
 */
public enum RegistrationTree {
    /**
     * IANA Standard tree.
     */
    STANDARDS(""),

    /**
     * Vendor Tree.
     */
    VENDOR("vnd."),

    /**
     * Personal or vanity tree.
     */
    PERSONAL("prs."),

    /**
     * Unregistered tree.
     */
    UNREGISTERED("x.");

    /**
     * Textual prefix that is appended to a subtype string.
     */
    private final String prefix;

    /**
     * Instantiates an instance with the given prefix.
     *
     * @param prefix
     *      Textual prefix that is appended to a subtype string.
     * @throws NullPointerException
     *      If the prefix is null
     */
    RegistrationTree(final String prefix) {

        if (prefix == null) {
            throw new NullPointerException("Prefix cannot be null");
        }

        // ensure the prefix ends with a "." character
        String tmpPrefix = prefix.trim().toLowerCase();
        if (prefix.length() > 0 && prefix.charAt(prefix.length() - 1) != '.') {
            tmpPrefix = tmpPrefix + ".";
        }

        this.prefix = tmpPrefix;
    }

    /**
     * Returns the RegistrationTree constant of this type with the specified prefix.
     *
     * @param prefix
     *      Tree prefix.
     * @return
     *      RegistrationTree constant or {@code null} if no match could be found.
     */
    public static RegistrationTree withPrefix(final String prefix) {
        // prepare the prefix
        String tmpPrefix = prefix.trim().toLowerCase();
        if (prefix.length() > 0 && prefix.charAt(prefix.length() - 1) != '.') {
            tmpPrefix = tmpPrefix + ".";
        }

        if (tmpPrefix.length() == 0) {
            return RegistrationTree.STANDARDS;
        }

        for (RegistrationTree tree : RegistrationTree.values()) {
            if (prefix.equalsIgnoreCase(tree.toString())) {
                return tree;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return this.prefix;
    }
}
