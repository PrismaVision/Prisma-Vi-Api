package com.api.prisma_vi.color

data class ColorForm(val name: String?,
                     val description: String?,
                     val psychologyTags: Array<String>,
                     val designUsageSuggestions: String?,
                     val colorPallete: Array<SimpleColor>) {
    constructor() : this(
        name = "Descriptive name of the color, e.g. 'Navy Blue'",
        description = "Brief emotional and cultural description of the color. e.g. 'Evokes seriousness and confidence.'",
        psychologyTags = arrayOf("e.g. trust", "e.g. seriousness", "e.g. stability"),
        designUsageSuggestions = "Suggestions for use in design e.g. 'Useful as a background color in corporate apps'",
        colorPallete = arrayOf(
            SimpleColor(
                name = "Name of the complementary or analogous color. e.g. 'Light Blue'",
                hex = "e.g. #ADD8E6"
            ),
            SimpleColor(
                name = "Name another of the complementary or analogous color. e.g. 'Azul Claro'",
                hex = "e.g. #ADD8E6"
            )
        )
    )
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ColorForm

        if (name != other.name) return false
        if (description != other.description) return false
        if (!psychologyTags.contentEquals(other.psychologyTags)) return false
        if (designUsageSuggestions != other.designUsageSuggestions) return false
        if (!colorPallete.contentEquals(other.colorPallete)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + psychologyTags.contentHashCode()
        result = 31 * result + (designUsageSuggestions?.hashCode() ?: 0)
        result = 31 * result + colorPallete.hashCode()
        return result
    }
}
