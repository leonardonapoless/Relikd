package com.leocodes.relikd.model;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum Era {
    PIONEERS("The Pioneers (1970s)", false),
    HOME_COMPUTER("Home Computer Revolution (1977–1985)", false),
    RISE_OF_GUI("Rise of the GUI (1984–1990)", false),
    CLONE_WARS("The Clone Wars (1985–1994)", false),
    MULTIMEDIA_BOOM("Multimedia Boom (1993–1998)", false),
    DOT_COM("The Dot-Com Era (1998–2003)", false),
    LATE_VINTAGE("Late Vintage (2003–2008)", false),

    APPLE_I_AND_II("Apple I & Apple II (1976–1983)", true),
    COMPACT_MAC("Compact Macintosh (1984–1992)", true),
    MAC_II_AND_QUADRA("Macintosh II & Quadra (1987–1995)", true),
    POWERPC_MAC("PowerPC Macintosh (1994–1998)", true),
    IMAC_AND_G("iMac & Power Mac G (1998–2006)", true);

    private final String label;
    private final boolean appleEra;

    Era(String label, boolean appleEra) {
        this.label = label;
        this.appleEra = appleEra;
    }

    public String getLabel() {
        return label;
    }

    public boolean isAppleEra() {
        return appleEra;
    }

    public Era getGeneralEra() {
        return switch (this) {
            case APPLE_I_AND_II -> HOME_COMPUTER;
            case COMPACT_MAC -> RISE_OF_GUI;
            case MAC_II_AND_QUADRA -> CLONE_WARS;
            case POWERPC_MAC -> MULTIMEDIA_BOOM;
            case IMAC_AND_G -> DOT_COM;
            default -> this;
        };
    }

    public static List<Era> generalEras() {
        return Arrays.stream(values())
                .filter(era -> !era.appleEra)
                .toList();
    }

    public static List<Era> appleEras() {
        return Arrays.stream(values())
                .filter(Era::isAppleEra)
                .toList();
    }

    public static Optional<Era> fromLabel(String label) {
        return Arrays.stream(values())
                .filter(era -> era.label.equals(label))
                .findFirst();
    }

    @Override
    public String toString() {
        return label;
    }
}
