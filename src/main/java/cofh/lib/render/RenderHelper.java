package cofh.lib.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Contains various helper functions to assist with rendering.
 *
 * @author King Lemming
 *
 */
public final class RenderHelper {

	public static final double RENDER_OFFSET = 1.0D / 1024.0D;
	public static final ResourceLocation MC_BLOCK_SHEET = new ResourceLocation("textures/atlas/blocks.png");
	public static final ResourceLocation MC_ITEM_SHEET = new ResourceLocation("textures/atlas/items.png");
	public static final ResourceLocation MC_FONT_DEFAULT = new ResourceLocation("textures/font/ascii.png");
	public static final ResourceLocation MC_FONT_ALTERNATE = new ResourceLocation("textures/font/ascii_sga.png");
	public static final ResourceLocation MC_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");

	private RenderHelper() {

	}

	public static final TextureManager engine() {

		return Minecraft.getMinecraft().renderEngine;
	}

	public static void setColor3ub(int color) {

		GL11.glColor3ub((byte) (color >> 16 & 0xFF), (byte) (color >> 8 & 0xFF), (byte) (color & 0xFF));
	}

	public static void setColor4ub(int color) {

		GL11.glColor4ub((byte) (color >> 24 & 0xFF), (byte) (color >> 16 & 0xFF), (byte) (color >> 8 & 0xFF), (byte) (color & 0xFF));
	}

	public static void resetColor() {

		GL11.glColor4f(1F, 1F, 1F, 1F);
	}

	public static final void bindTexture(ResourceLocation texture) {

		engine().bindTexture(texture);
	}

	public static final void setBlockTextureSheet() {

		bindTexture(MC_BLOCK_SHEET);
	}

	public static final void setItemTextureSheet() {

		bindTexture(MC_ITEM_SHEET);
	}

	public static final void setDefaultFontTextureSheet() {

		bindTexture(MC_FONT_DEFAULT);
	}

	public static final void setSGAFontTextureSheet() {

		bindTexture(MC_FONT_ALTERNATE);
	}

	public static final void enableGUIStandardItemLighting() {

		net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
	}

	public static void enableStandardItemLighting() {

		net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
	}

}
