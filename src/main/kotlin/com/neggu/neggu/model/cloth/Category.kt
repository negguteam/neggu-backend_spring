package com.neggu.neggu.model.cloth

enum class Category(val displayName: String, val subCategories: List<SubCategory>) {
    TOP(
        "상의",
        listOf(
            SubCategory.SWEATSHIRT,
            SubCategory.SHIRT_BLOUSE,
            SubCategory.HOODIE,
            SubCategory.KNIT,
            SubCategory.T_SHIRT,
            SubCategory.SLEEVELESS
        )
    ),

    OUTER(
        "아우터",
        listOf(
            SubCategory.JACKET,
            SubCategory.ZIP_UP_HOODIE,
            SubCategory.CARDIGAN,
            SubCategory.FLEECE,
            SubCategory.COAT,
            SubCategory.PUFFER,
            SubCategory.VEST
        )
    ),

    BOTTOM(
        "하의",
        listOf(
            SubCategory.JEANS,
            SubCategory.SLACKS,
            SubCategory.SHORTS,
            SubCategory.JUMPSUIT,
            SubCategory.SKIRT
        )
    );
}

enum class SubCategory(val displayName: String) {
    // 상의
    SWEATSHIRT("맨투맨"),
    SHIRT_BLOUSE("셔츠/블라우스"),
    HOODIE("후드"),
    KNIT("니트"),
    T_SHIRT("티셔츠"),
    SLEEVELESS("민소매"),

    // 아우터
    JACKET("자켓"),
    ZIP_UP_HOODIE("후드집업"),
    CARDIGAN("가디건"),
    FLEECE("플리스"),
    COAT("코트"),
    PUFFER("패팅"),
    VEST("베스트"),

    // 하의
    JEANS("데님팬츠"),
    SLACKS("슬랙스"),
    SHORTS("숏팬츠"),
    JUMPSUIT("점프슈트"),
    SKIRT("스커트");
}
