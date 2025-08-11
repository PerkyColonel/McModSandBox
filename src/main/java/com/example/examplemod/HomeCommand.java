package com.example.examplemod;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;

public class HomeCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("home")
            .requires(source -> source.isPlayer())
            .executes(HomeCommand::execute));
    }

    private static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        ServerLevel currentLevel = (ServerLevel) player.level();
        
        HomeManager homeManager = HomeManager.get(currentLevel);
        
        if (!homeManager.hasHome(player)) {
            player.sendSystemMessage(Component.literal("You don't have a home set! Use /sethome to set one."));
            return 0;
        }

        HomeManager.HomeData homeData = homeManager.getHome(player);
        
        // Get the target dimension
        ServerLevel targetLevel = player.getServer().getLevel(homeData.dimension);
        if (targetLevel == null) {
            player.sendSystemMessage(Component.literal("Your home dimension no longer exists!"));
            return 0;
        }

        // Teleport player
        Vec3 homePos = new Vec3(homeData.position.getX() + 0.5, homeData.position.getY(), homeData.position.getZ() + 0.5);
        
        if (targetLevel != currentLevel) {
            // Cross-dimensional teleport using DimensionTransition
            DimensionTransition transition = new DimensionTransition(
                targetLevel, homePos, Vec3.ZERO, player.getYRot(), player.getXRot(), 
                DimensionTransition.DO_NOTHING
            );
            player.changeDimension(transition);
        } else {
            // Same dimension teleport
            player.teleportTo(homePos.x, homePos.y, homePos.z);
        }

        player.sendSystemMessage(Component.literal(
            "Teleported home to " + homeData.position.getX() + ", " + homeData.position.getY() + ", " + homeData.position.getZ()
        ));

        return 1;
    }
}