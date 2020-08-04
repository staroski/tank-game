package br.com.staroski.games.tank;

import java.awt.Font;

public final class Fonts {

    public static final Font COURIER;

    public static final Font STENCIL_CARGO_ARMY;

    public static final Font LINTSEC;

    public static final Font BUNDY_YELLOW_SOLID;

    static {
        try {
            COURIER = Font.createFont(Font.TRUETYPE_FONT, Fonts.class.getResourceAsStream("/fonts/Courier.ttf"));
            STENCIL_CARGO_ARMY = Font.createFont(Font.TRUETYPE_FONT, Fonts.class.getResourceAsStream("/fonts/Stencil Cargo Army.ttf"));
            LINTSEC = Font.createFont(Font.TRUETYPE_FONT, Fonts.class.getResourceAsStream("/fonts/Lintsec.ttf"));
            BUNDY_YELLOW_SOLID = Font.createFont(Font.TRUETYPE_FONT, Fonts.class.getResourceAsStream("/fonts/Bundy Yellow Solid.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    private Fonts() {}
}