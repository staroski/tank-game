package br.com.staroski.games.tank.client;

import br.com.staroski.games.RendererMapper;
import br.com.staroski.games.tank.Resources;

final class Presets {

    public void execute() {
        RendererMapper mapper = RendererMapper.get();
        mapper.put(Resources.STAGE_01.getId(), new STAGE_01_Renderer());
        mapper.put(Resources.PLAYER_01.getId(), new PLAYER_01_Renderer());
        mapper.put(Resources.BULLET_01.getId(), new BULLET_01_Renderer());
    }
}
