package br.cefetmg.gestaoentregasview;

public class UserSession {
    private static String perfil;

    public static String getPerfil() {
        return perfil;
    }

    public static void setPerfil(String perfil) {
        UserSession.perfil = perfil;
    }
}
