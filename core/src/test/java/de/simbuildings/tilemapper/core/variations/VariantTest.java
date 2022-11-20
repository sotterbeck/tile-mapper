package de.simbuildings.tilemapper.core.variations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static de.simbuildings.tilemapper.core.junit.TestUtils.getSampleTexture;
import static org.assertj.core.api.Assertions.assertThat;

class VariantTest {

    @Test
    @DisplayName("Should compare two variants by name")
    void compareTo_ShouldCompareTwoVariantsByName() {
        // given
        Variant variantLower = new Variant(getSampleTexture("alternate_sample_1.png"));
        Variant variantGreater = new Variant(getSampleTexture("alternate_sample_2.png"));

        // when

        // then
        assertThat(variantGreater).isGreaterThan(variantLower);
    }
}