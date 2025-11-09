package com.lyviamc.lyvia_creeper_demo.command;

import com.lyviamc.lyvia_creeper_demo.LyviaCreeperDemo;
import com.lyviamc.lyvia_creeper_demo.AreaManagerWithCreeperActivityMonitoring;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.Collection;

public class CreeperBehaviorConfigCommand {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                dispatcher.register(CommandManager.literal("AreaManagerWithCreeperActivityMonitoring").requires(source -> source.hasPermissionLevel(1))
                        .then(CommandManager.literal("add")
                                .then(CommandManager.argument("pos1", Vec3ArgumentType.vec3())
                                        .then(CommandManager.argument("pos2", Vec3ArgumentType.vec3())
                                                .executes(CreeperBehaviorConfigCommand::executeAdd))))
                        .then(CommandManager.literal("remove")
                                .then(CommandManager.argument("index", IntegerArgumentType.integer())
                                        .executes(CreeperBehaviorConfigCommand::executeRemove)))
                        .then(CommandManager.literal("clear")
                                .executes(CreeperBehaviorConfigCommand::executeClear))
                        .then(CommandManager.literal("player")
                                .then(CommandManager.literal("unhostility")
                                        .then(CommandManager.argument("selector", EntityArgumentType.players())
                                                .executes(CreeperBehaviorConfigCommand::executePlayerUnhostility)))
                                .then(CommandManager.literal("hostility")
                                        .then(CommandManager.argument("selector", EntityArgumentType.players())
                                                .executes(CreeperBehaviorConfigCommand::executePlayerHostility)))
                                .then(CommandManager.literal("query")
                                        .then(CommandManager.argument("selector", EntityArgumentType.players())
                                                .executes(CreeperBehaviorConfigCommand::executePlayerQuery))))
                        .then(CommandManager.literal("list")
                                .executes(CreeperBehaviorConfigCommand::executeListAll)
                                .then(CommandManager.argument("index", IntegerArgumentType.integer())
                                        .executes(CreeperBehaviorConfigCommand::executeListIndex)))));
    }

    private static int executePlayerUnhostility(CommandContext<ServerCommandSource> context) {
        try{
            Collection<ServerPlayerEntity> players = EntityArgumentType.getPlayers(context, "selector");
            String result = AreaManagerWithCreeperActivityMonitoring.setPlayersCreeperUnhostility(players);
            context.getSource().sendFeedback(() -> Text.literal(result), true);
            return 1;
        } catch (CommandSyntaxException e) {
            context.getSource().sendError(Text.literal("ERROR to set these players as Creeper-unhostility!"));
            return 0;
        }
    }
    private static int executePlayerQuery(CommandContext<ServerCommandSource> context) {
        try{
            LyviaCreeperDemo.LOGGER.info(String.valueOf(6));

            Collection<ServerPlayerEntity> players = EntityArgumentType.getPlayers(context, "selector");
            String result = AreaManagerWithCreeperActivityMonitoring.queryPlayersCreeperHostility(players);
            context.getSource().sendFeedback(() -> Text.literal(result), true);
            return 1;
        } catch (CommandSyntaxException e) {
            context.getSource().sendError(Text.literal("ERROR to set these players as Creeper-unhostility!"));
            return 0;
        }
    }
    private static int executePlayerHostility(CommandContext<ServerCommandSource> context) {
        try{
            Collection<ServerPlayerEntity> players = EntityArgumentType.getPlayers(context, "selector");
            String result = AreaManagerWithCreeperActivityMonitoring.setPlayersCreeperHostility(players);
            context.getSource().sendFeedback(() -> Text.literal(result), true);
            return 1;
        } catch (CommandSyntaxException e) {
            context.getSource().sendError(Text.literal("ERROR to set these players as Creeper-hostility!"));
            return 0;
        }
    }

    private static int executeAdd(CommandContext<ServerCommandSource> context) {
        try {
            Vec3d vec_pos1 = Vec3ArgumentType.getPosArgument(context,"pos1").getPos(context.getSource());
            Vec3d vec_pos2 = (Vec3d) Vec3ArgumentType.getPosArgument(context,"pos2").getPos(context.getSource());
            String result = AreaManagerWithCreeperActivityMonitoring.setVulnerableArea(vec_pos1,vec_pos2);
            context.getSource().sendFeedback(() -> Text.literal(result), true);
            return 1;
        } catch (Exception e) {
            context.getSource().sendError(Text.literal("ERROR to add!"));
            return 0;
        }
    }

    private static int executeRemove(CommandContext<ServerCommandSource> context) {
        try {
            int index = IntegerArgumentType.getInteger(context, "index");
            String result = AreaManagerWithCreeperActivityMonitoring.delArea(index);
            context.getSource().sendFeedback(() -> Text.literal(result), true);
            return 1;
        } catch (Exception e) {
            context.getSource().sendError(Text.literal("ERROR to remove!"));
            return 0;
        }
    }

    private static int executeListAll(CommandContext<ServerCommandSource> context) {
        try {
            String result = AreaManagerWithCreeperActivityMonitoring.printOutAreas();
            context.getSource().sendFeedback(() -> Text.literal(result), true);
            return 1;
        } catch (Exception e) {
            context.getSource().sendError(Text.literal("ERROR to list out!"));
            return 0;
        }
    }
    private static int executeClear(CommandContext<ServerCommandSource> context) {
        try {
            String result = AreaManagerWithCreeperActivityMonitoring.clearArea();
            context.getSource().sendFeedback(() -> Text.literal(result), true);
            return 1;
        } catch (Exception e) {
            context.getSource().sendError(Text.literal("ERROR to list out!"));
            return 0;
        }
    }
    private static int executeListIndex(CommandContext<ServerCommandSource> context) {
        try {
            int index = IntegerArgumentType.getInteger(context, "index");
            String result = AreaManagerWithCreeperActivityMonitoring.printOutAreas(index);
            context.getSource().sendFeedback(() -> Text.literal(result), true);
            return 1;
        } catch (Exception e) {
            context.getSource().sendError(Text.literal("ERROR to list out!"));
            return 0;
        }
    }
}
