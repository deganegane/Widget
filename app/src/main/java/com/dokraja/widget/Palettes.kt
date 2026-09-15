package com.dokraja.widget

import androidx.compose.ui.graphics.Color

data class SchemeColors(
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,
    val tertiaryContainer: Color,
    val onTertiaryContainer: Color
)

data class ThemePalette(val light: SchemeColors, val dark: SchemeColors)

val PRESET_PALETTES: LinkedHashMap<String, ThemePalette> = linkedMapOf(
    "purple" to ThemePalette(
        light = SchemeColors(
            primary = Color(0xFF6750A4), onPrimary = Color(0xFFFFFFFF),
            primaryContainer = Color(0xFFEADDFF), onPrimaryContainer = Color(0xFF21005D),
            secondaryContainer = Color(0xFFE8DEF8), onSecondaryContainer = Color(0xFF1E192B),
            tertiaryContainer = Color(0xFFFFD8E4), onTertiaryContainer = Color(0xFF31111D)
        ),
        dark = SchemeColors(
            primary = Color(0xFFD0BCFF), onPrimary = Color(0xFF381E72),
            primaryContainer = Color(0xFF4F378B), onPrimaryContainer = Color(0xFFEADDFF),
            secondaryContainer = Color(0xFF4A4458), onSecondaryContainer = Color(0xFFE8DEF8),
            tertiaryContainer = Color(0xFF633B48), onTertiaryContainer = Color(0xFFFFD8E4)
        )
    ),
    "teal" to ThemePalette(
        light = SchemeColors(
            primary = Color(0xFF00696D), onPrimary = Color(0xFFFFFFFF),
            primaryContainer = Color(0xFF9CF1F1), onPrimaryContainer = Color(0xFF002020),
            secondaryContainer = Color(0xFFD0E4E2), onSecondaryContainer = Color(0xFF0D201F),
            tertiaryContainer = Color(0xFFFFDCC0), onTertiaryContainer = Color(0xFF341100)
        ),
        dark = SchemeColors(
            primary = Color(0xFF82D4D4), onPrimary = Color(0xFF003738),
            primaryContainer = Color(0xFF004F51), onPrimaryContainer = Color(0xFF9CF1F1),
            secondaryContainer = Color(0xFF354B49), onSecondaryContainer = Color(0xFFD0E4E2),
            tertiaryContainer = Color(0xFF5B3E1B), onTertiaryContainer = Color(0xFFFFDCC0)
        )
    ),
    "coral" to ThemePalette(
        light = SchemeColors(
            primary = Color(0xFF9C4200), onPrimary = Color(0xFFFFFFFF),
            primaryContainer = Color(0xFFFFDBC8), onPrimaryContainer = Color(0xFF331200),
            secondaryContainer = Color(0xFFF5DED2), onSecondaryContainer = Color(0xFF2B1710),
            tertiaryContainer = Color(0xFFC3E8E3), onTertiaryContainer = Color(0xFF051F1C)
        ),
        dark = SchemeColors(
            primary = Color(0xFFFFB68E), onPrimary = Color(0xFF552100),
            primaryContainer = Color(0xFF7A3200), onPrimaryContainer = Color(0xFFFFDBC8),
            secondaryContainer = Color(0xFF5C4033), onSecondaryContainer = Color(0xFFF5DED2),
            tertiaryContainer = Color(0xFF234E49), onTertiaryContainer = Color(0xFFC3E8E3)
        )
    ),
    "pink" to ThemePalette(
        light = SchemeColors(
            primary = Color(0xFF984061), onPrimary = Color(0xFFFFFFFF),
            primaryContainer = Color(0xFFFFD9E3), onPrimaryContainer = Color(0xFF3E001D),
            secondaryContainer = Color(0xFFF2DDE3), onSecondaryContainer = Color(0xFF2B151B),
            tertiaryContainer = Color(0xFFFCE2B9), onTertiaryContainer = Color(0xFF2B1B00)
        ),
        dark = SchemeColors(
            primary = Color(0xFFFFB0C8), onPrimary = Color(0xFF5E1133),
            primaryContainer = Color(0xFF7A2948), onPrimaryContainer = Color(0xFFFFD9E3),
            secondaryContainer = Color(0xFF5C4048), onSecondaryContainer = Color(0xFFF2DDE3),
            tertiaryContainer = Color(0xFF5A4300), onTertiaryContainer = Color(0xFFFCE2B9)
        )
    ),
    "blue" to ThemePalette(
        light = SchemeColors(
            primary = Color(0xFF0061A4), onPrimary = Color(0xFFFFFFFF),
            primaryContainer = Color(0xFFD1E4FF), onPrimaryContainer = Color(0xFF001D36),
            secondaryContainer = Color(0xFFD9E3F1), onSecondaryContainer = Color(0xFF101C2B),
            tertiaryContainer = Color(0xFFFFDAD4), onTertiaryContainer = Color(0xFF341008)
        ),
        dark = SchemeColors(
            primary = Color(0xFF9ECAFF), onPrimary = Color(0xFF003258),
            primaryContainer = Color(0xFF00497D), onPrimaryContainer = Color(0xFFD1E4FF),
            secondaryContainer = Color(0xFF3B4858), onSecondaryContainer = Color(0xFFD9E3F1),
            tertiaryContainer = Color(0xFF5D3F36), onTertiaryContainer = Color(0xFFFFDAD4)
        )
    ),
    "green" to ThemePalette(
        light = SchemeColors(
            primary = Color(0xFF3B6939), onPrimary = Color(0xFFFFFFFF),
            primaryContainer = Color(0xFFBCF0B4), onPrimaryContainer = Color(0xFF002204),
            secondaryContainer = Color(0xFFDCE5D6), onSecondaryContainer = Color(0xFF151E10),
            tertiaryContainer = Color(0xFFC7E7EB), onTertiaryContainer = Color(0xFF051F23)
        ),
        dark = SchemeColors(
            primary = Color(0xFFA0D399), onPrimary = Color(0xFF0A3910),
            primaryContainer = Color(0xFF215024), onPrimaryContainer = Color(0xFFBCF0B4),
            secondaryContainer = Color(0xFF414A3B), onSecondaryContainer = Color(0xFFDCE5D6),
            tertiaryContainer = Color(0xFF29494D), onTertiaryContainer = Color(0xFFC7E7EB)
        )
    )
)
