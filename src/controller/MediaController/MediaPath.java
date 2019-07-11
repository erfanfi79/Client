package controller.MediaController;

public enum MediaPath {
    MENU("src/resources/music/music_menu.mp3"),
    SELECT("src/resources/sfx/sfx_ui_select.m4a"),
    ATTACK("src/resources/sfx/sfx_voidhunter_attack_swing.m4a"),
    HIT("src/resources/sfx/sfx_voidhunter_hit.m4a"),
    ERROR("src/resources/sfx/sfx_ui_error.m4a"),
    VICTORY("src/resources/sfx/sfx_victory_match_w_vo.m4a"),
    UNIT_ON_CLICK("src/resources/sfx/sfx_unit_onclick.m4a"),
    DEATH("src/resources/sfx/sfx_voidhunter_death.m4a"),
    YOUR_TURN("src/resources/sfx/sfx_ui_yourturn.m4a"),
    EXPLOSION("src/resources/sfx/sfx_ui_explosion.m4a"),
    MOVE("src/resources/sfx/sfx_unit_deploy_1.m4a");


    private String path = "";

    MediaPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
