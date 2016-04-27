package tbsc.techy.client.gui.element;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementButtonOption;
import cofh.lib.render.RenderHelper;
import net.minecraft.util.ResourceLocation;
import tbsc.techy.api.SideConfiguration;
import tbsc.techy.api.Sides;
import tbsc.techy.tile.TileBase;

import java.util.List;

/**
 * Value 0 - Disabled
 * Value 1 - Input
 * Value 2 - Output
 * Value 3 - IO
 *
 * Created by tbsc on 4/27/16.
 */
public class ElementButtonOptionSide extends ElementButtonOption {

    private TileBase tile;
    private Sides side;
    private SideConfiguration currentConfig;
    int currentValue = 0;
    int maxValue;

    public ElementButtonOptionSide(GuiBase containerScreen, int x, int y, int width, int height, TileBase tile, Sides side, SideConfiguration currentConfig) {
        super(containerScreen, x, y, width, height);
        _values.put(SideConfiguration.DISABLED.ordinal(), SideConfiguration.DISABLED.name());
        _values.put(SideConfiguration.INPUT.ordinal(), SideConfiguration.INPUT.name());
        _values.put(SideConfiguration.OUTPUT.ordinal(), SideConfiguration.OUTPUT.name());
        _values.put(SideConfiguration.IO.ordinal(), SideConfiguration.IO.name());
        this.tile = tile;
        this.side = side;
        this.currentConfig = currentConfig;
        setSelectedIndex(
                // 0
                currentConfig.ordinal()
        );
    }

    @Override
    public void drawBackground(int mouseX, int mouseY, float gameTicks) {
        RenderHelper.bindTexture(new ResourceLocation("Techy:textures/gui/element/sideConfigRender.png"));
        switch (currentConfig) {
            case DISABLED:
                drawTexturedModalRect(posX, posY, 30, 0, sizeX, sizeY);
                break;
            case INPUT:
                drawTexturedModalRect(posX, posY, 0, 0, sizeX, sizeY);
                break;
            case OUTPUT:
                drawTexturedModalRect(posX, posY, 20, 0, sizeX, sizeY);
                break;
            case IO:
                drawTexturedModalRect(posX, posY, 10, 0, sizeX, sizeY);
                break;
            default:
                drawTexturedModalRect(posX, posY, 40, 0, sizeX, sizeY);
                break;
        }
    }

    public void setValue(int value, String label) {
        _values.put(value, label);
        if (value > maxValue) {
            maxValue = value;
        }
    }

    @Override
    public void addTooltip(List<String> list) {
        if (!currentConfig.name().equals("IO")) {
            list.add(currentConfig.name().substring(0, 1) + currentConfig.name().substring(1).toLowerCase());
        } else {
            list.add("IO");
        }
    }

    @Override
    public void onClick() {

        int nextValue = currentValue;
        do {
            nextValue++;
            if (nextValue > maxValue) {
                nextValue = 0;
            }
        } while (_values.get(nextValue) == null);
        setSelectedIndex(nextValue);
    }

    @Override
    public void onRightClick() {
        int nextValue = currentValue;

        do {
            nextValue--;
            if (nextValue < 0) {
                nextValue = maxValue;
            }
        } while (_values.get(nextValue) == null);
        setSelectedIndex(nextValue);
    }

    public int getSelectedIndex() {

        return currentValue;
    }

    public void setSelectedIndex(int index) {

        currentValue = index;
        setText(_values.get(currentValue));
        onValueChanged(currentValue, _values.get(currentValue));
    }

    public String getValue() {
        return _values.get(currentValue);
    }

    @Override
    public void onValueChanged(int value, String label) {
        tile.setConfigurationForSide(side, currentConfig = SideConfiguration.fromString(label));
    }

}
