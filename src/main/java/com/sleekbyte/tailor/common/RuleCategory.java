package com.sleekbyte.tailor.common;

/**
 * Categories of {@link Rules}.
 */
public enum RuleCategory {
    BUG_RISK("Bug Risk"),
    CLARITY("Clarity"),
    COMPATIBILITY("Compatibility"),
    COMPLEXITY("Complexity"),
    DUPLICATION("Duplication"),
    PERFORMANCE("Performance"),
    SECURITY("Security"),
    STYLE("Style");

    private final String category;

    RuleCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return category;
    }
}
