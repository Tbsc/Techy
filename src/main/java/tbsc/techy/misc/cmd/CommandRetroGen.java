package tbsc.techy.misc.cmd;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import tbsc.techy.init.MiscInit;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Command for retrogenerating Techy's ores
 *
 * Created by tbsc on 5/28/16.
 */
public class CommandRetroGen implements ICommand {

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length >= 3) {
            handleElsewherePosCommand(args, sender);
        } else {
            handleThisPosCommand(args, sender);
        }
    }

    private void handleElsewherePosCommand(String[] args, ICommandSender sender) {
        if (args.length >= 3) { // Double checking
            try {
                MiscInit.generateOres(sender.getEntityWorld(), Integer.parseInt(args[2]),
                        sender.getEntityWorld().getChunkFromBlockCoords(new BlockPos(Integer.parseInt(args[0]),
                                63, Integer.parseInt(args[1]))).getChunkCoordIntPair());
            } catch (NumberFormatException e) {
                sender.addChatMessage(new TextComponentString("Invalid parameters"));
            }
        }
    }

    private void handleThisPosCommand(String[] args, ICommandSender sender) {
        if (sender instanceof Entity) { // It has a position
            if (args.length >= 1) {
                try {
                    MiscInit.generateOres(sender.getEntityWorld(), Integer.parseInt(args[0]), sender.getEntityWorld().getChunkFromBlockCoords(sender.getPosition()).getChunkCoordIntPair());
                } catch (NumberFormatException e) {
                    sender.addChatMessage(new TextComponentString("Invalid radius - not a number"));
                }
            } else {
                sender.addChatMessage(new TextComponentString("No radius specified"));
            }
        }
    }

    @Override
    public String getCommandName() {
        return "techyretrogen";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "techyretrogen <radius> OR techyretrogen <xPos> <zPos> <radius>";
    }

    @Override
    public List<String> getCommandAliases() {
        List<String> list = new ArrayList<>();
        list.add("trg");
        list.add("tretrogen");
        list.add("techyrg");
        return list;
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender.canCommandSenderUseCommand(3, "techyretrogen");
    }

    @Override
    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }

}
