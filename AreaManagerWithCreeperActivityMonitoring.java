package com.lyviamc.lyvia_creeper_demo;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import java.util.ArrayList;
import java.util.Collection;

public class AreaManagerWithCreeperActivityMonitoring {
    protected static ArrayList<double[]> vulnerableAreas = new ArrayList<>();
    public static String setPlayersCreeperUnhostility(Collection<ServerPlayerEntity> players){
        if (players.isEmpty()) return "No players were selected!";
        else {
            StringBuilder result = new StringBuilder("Set players: ");
            for (ServerPlayerEntity player : players.stream().toList()){
                ((HostilityAccessor) player).lyviaCreeperDemo$setIsHostilityAgainstCreeper(false);
                result.append(player.getName()).append(", ");
            }
            return result.append("as unhostility with creepers.").toString();
        }
    }
    public static String setPlayersCreeperHostility(Collection<ServerPlayerEntity> players){
        if (players.isEmpty()) return "No players were selected!";
        else {
            StringBuilder result = new StringBuilder("Set players: ");
            for (ServerPlayerEntity player : players.stream().toList()){
                ((HostilityAccessor) player).lyviaCreeperDemo$setIsHostilityAgainstCreeper(true);
                result.append(player.getName()).append(", ");
            }
            return result.append("as hostility against creepers.").toString();
        }
    }
    public static String queryPlayersCreeperHostility(Collection<ServerPlayerEntity> players) {
        StringBuilder result = new StringBuilder("The player ");
        for(ServerPlayerEntity player : players.stream().toList()) {
            result.append(player.getName()).append(((HostilityAccessor) player)
                .lyviaCreeperDemo$getIsHostilityAgainstCreeper()?" is ":" isn't ")
                    .append("hostility against creepers\n");}
        return result.toString();
    }
    public static String setVulnerableArea(Vec3d pos1, Vec3d pos2) {
        double[] newElement = {pos1.getX(), pos1.getY(), pos1.getZ(),
                pos2.getX(), pos2.getY(), pos2.getZ()};
        vulnerableAreas.add(newElement);
        return "A new area had been set.";
    }
    /*public static String setVulnerableArea(int x1, int y1, int z1, int x2, int y2, int z2) {
        double[] newElement = {x1, y1, z1, x2, y2, z2};
        vulnerableArea.add(newElement);
        StringBuilder result = new StringBuilder("Set ");
        result.append("X1: ").append(newElement[0]).append(", Y1: ").append(newElement[1]).append(", Z1: ")
                .append(newElement[2]).append(", X2: ").append(newElement[3]).append(", Y2: ")
                .append(newElement[4]).append(", Z2: ").append(newElement[5]).append(" as the new area.\n");
        return result.toString();
    }*/
    private static boolean inRange(double num0, double num1, double num2){
        return ((num0 >= num1) && (num0 <= num2)) || (num0 <= num1 && num0 >= num2);
    }
    public static boolean in3dRange(Vec3d pos0) {
        for (double[] range : vulnerableAreas) {
            if ((inRange(pos0.getX(), range[0], range[3])) && (inRange(pos0.getY(), range[1], range[4]))
                    && (inRange(pos0.getZ(), range[2], range[5]))) {
                return true;
            }
        }
        return false;
    }
    public static String printOutAreas(){
        if (!vulnerableAreas.isEmpty()){
            StringBuilder result = new StringBuilder("The List of Vulnerable Areas:\n");
            for (double[] range : vulnerableAreas) {
                result.append("(").append(range[0]).append(", ").append(range[1]).append(", ")
                        .append(range[2]).append("), (").append(range[3]).append(", ")
                        .append(range[4]).append(", ").append(range[5]).append(").\n");
            }
            return result.toString();
        }
        else {
            return "The list is now empty.";
        }
    }
    public static String printOutAreas(int index){
        StringBuilder result = new StringBuilder();
        index--;
        if(index >= 0 && index < vulnerableAreas.size()) {
            double[] range = vulnerableAreas.get(index);
            result.append("The #").append(index+1).append(" area is:\n").append("(").append(range[0])
                    .append(", ").append(range[1]).append(", ").append(range[2]).append("), (")
                    .append(range[3]).append(", ").append(range[4]).append(", ").append(range[5]).append(").\n");
        }
        else {
            result.append("The number ").append(index+1).append(" is beyond the range of vulnerable areas from 1 to ")
                    .append(vulnerableAreas.size()).append(".");
        }
        return result.toString();

    }
    public static String delArea(int index){
        StringBuilder result = new StringBuilder();
        index--;
        if(index >= 0 && index < vulnerableAreas.size()){
            vulnerableAreas.remove(index);
            result.append("The #").append(index+1).append(" vulnerable area position had been removed and now there're ")
                    .append(vulnerableAreas.size()).append(" positions.");
        }
        else {
            result.append("The number").append(index+1).append(" is beyond the range of vulnerable areas from 1 to ")
                    .append(vulnerableAreas.size()).append(".");
        }
        return result.toString();
    }
    public static String clearArea(){
        StringBuilder result = new StringBuilder();
        result.append(vulnerableAreas.size()).append(" vulnerable areas had been removed.");
        vulnerableAreas.clear();
        return result.toString();
    }
    /*static {
        SERVER_STARTED.register(server -> new AreaManagerWithCreeperActivityMonitoring());
    }*/
}