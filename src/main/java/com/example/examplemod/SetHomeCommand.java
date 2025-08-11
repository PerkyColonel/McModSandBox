package com.example.examplemod;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;

public class SetHomeCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("sethome")
            .requires(source -> source.isPlayer())
            .executes(SetHomeCommand::execute));
    }

    private static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        ServerLevel level = (ServerLevel) player.level();
        BlockPos position = player.blockPosition();

        HomeManager homeManager = HomeManager.get(level);
        homeManager.setHome(player, position);

        player.sendSystemMessage(Component.literal(
            "Home set at " + position.getX() + ", " + position.getY() + ", " + position.getZ() 
            + " in " + level.dimension().location()
        ));

        return 1;
    }
}