package br.com.staroski.games;

import java.util.*;

public final class RendererMapper {

	private static final class Holder {
		static final RendererMapper INSTANCE = new RendererMapper();
	}

	public static RendererMapper get() {
		return Holder.INSTANCE;
	}

	private final Map<Integer, Renderer> renderers;

	private RendererMapper() {
		renderers = new HashMap<Integer, Renderer>();
	}

	public Renderer get(int id) {
		return renderers.get(Integer.valueOf(id));
	}

	public void put(int id, Renderer renderer) {
		renderers.put(Integer.valueOf(id), renderer);
	}
}
