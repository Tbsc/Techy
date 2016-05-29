package tbsc.techy.misc.cmd;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
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
        MiscInit.generateOres(sender.getEntityWorld());
    }

    @Override
    public String getCommandName() {
        return "techyretrogen";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "techyretrogen";
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
        return sender.canCommandSenderUseCommand(1, "techyretrogen");
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
