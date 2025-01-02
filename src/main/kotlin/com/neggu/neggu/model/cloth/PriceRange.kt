package com.neggu.neggu.model.cloth

enum class PriceRange(val maxPrice: Int?) {
    UNKNOWN(null),
    UNDER_3K(30000),  // 3만원 이하
    UNDER_5K(50000),  // 5만원 이하
    FROM_5K_TO_10K(100000),  // 5~10만원
    FROM_10K_TO_20K(200000),  // 10~20만원
    FROM_20K_TO_30K(300000),  // 20~30만원
    ABOVE_30K(null);  // 30만원 이상 (제한 없음)

    companion object {

        fun fromPrice(price: Int?): PriceRange = when {
            price == null -> UNKNOWN
            price <= 30000 -> UNDER_3K
            price <= 50000 -> UNDER_5K
            price <= 100000 -> FROM_5K_TO_10K
            price <= 200000 -> FROM_10K_TO_20K
            price <= 300000 -> FROM_20K_TO_30K
            else -> ABOVE_30K
        }
    }
}
