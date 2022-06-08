package de.simbuildings.tilemapper.variations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static de.simbuildings.tilemapper.junit.TestUtils.getSampleTexture;
import static org.assertj.core.api.Assertions.assertThat;

class VariantDtoTest {

    @Test
    @DisplayName("Should compare two variants by name")
    void compareTo_ShouldCompareTwoVariantsByName() {
        // given
        VariantDto variantLower = new VariantDto(getSampleTexture("alternate_sample_1.png"));
        VariantDto variantGreater = new VariantDto(getSampleTexture("alternate_sample_2.png"));

        // when

        // then
        assertThat(variantGreater).isGreaterThan(variantLower);
    }
}