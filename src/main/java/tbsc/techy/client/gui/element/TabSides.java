package tbsc.techy.client.gui.element;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.TabBase;
import tbsc.techy.api.Sides;
import tbsc.techy.tile.TileBase;

import java.util.List;

/**
 * This tab will add support for changing the configuration of the ISidedInventory interface.
 *
 * Created by tbsc on 4/26/16.
 */
public class TabSides extends TabBase {

    public TileBase tile;

    public TabSides(GuiBase gui, int x, int y, int width, int height, TileBase tile) {
        super(gui);
        this.setPosition(x, y);
        this.setSize(width, height);
        this.setName("Configure Sides");
        this.tile = tile;
        addElement(new ElementButtonOptionSide(gui, 19, height - 10 - 24, 8, 8, tile, Sides.UP, tile.getConfigurationForSide(Sides.UP)));
        addElement(new ElementButtonOptionSide(gui, 19, height - 10 - 14, 8, 8, tile, Sides.FRONT, tile.getConfigurationForSide(Sides.FRONT)));
        addElement(new ElementButtonOptionSide(gui, 29, height - 10 - 14, 8, 8, tile, Sides.RIGHT, tile.getConfigurationForSide(Sides.RIGHT)));
        addElement(new ElementButtonOptionSide(gui, 9, height - 10 - 14, 8, 8, tile, Sides.LEFT, tile.getConfigurationForSide(Sides.LEFT)));
        addElement(new ElementButtonOptionSide(gui, 19, height - 10 - 4, 8, 8, tile, Sides.DOWN, tile.getConfigurationForSide(Sides.DOWN)));
        addElement(new ElementButtonOptionSide(gui, 29, height - 10 - 4, 8, 8, tile, Sides.BACK, tile.getConfigurationForSide(Sides.BACK)));
    }

    @Override
    public void addTooltip(List<String> list) {
        list.add("Configure Sides");
    }

    @Override
    protected void drawBackground() {
        super.drawBackground();
        if (isFullyOpened()) {
            gui.getFontRenderer().drawString("Sides", posX + 24 - getFontRenderer().getStringWidth("Sides") / 2, posY + 4, 0x404040);
        }
    }

    @Override
    protected void drawForeground() {
        super.drawForeground();
    }

}
