package wtf.gacek.smpcore;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class Utils {
    public static void colorize(@NotNull CommandSender sender, @NotNull String message) {
        sender.sendMessage(colorize(message));
    }

    public static String colorize(@NotNull String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void debug(@NotNull CommandSender sender, @NotNull String message) {
        sender.sendMessage(debug(message));
    }

    public static String debug(@NotNull String message) {
        return colorize("&7[&3DEBUG&7] " + message);
    }

    @Nullable
    public static String convertDate(int diff) {
        if (diff < 0) {
            return null;
        }
        int seconds = diff;
        int days = Math.floorDiv(seconds, 86400);
        seconds -= days * 86400;
        int hours = Math.floorDiv(seconds, 3600);
        seconds -= hours * 3600;
        int minutes = Math.floorDiv(seconds, 60);
        seconds -= minutes * 60;
        return (days != 0 ? (days < 10 ? "0" : "") + days + ":" : "") + (hours != 0 ? (hours < 10 ? "0" : "") + hours + ":" : "") + (minutes < 10 ? "0" : "") + minutes + ":" + (seconds < 10 ? "0" : "") + seconds;
    }

    public static String dateToText(int diff) {
        if (diff <= 0) {
            return "right now";
        }
        int seconds = diff;
        int days = Math.floorDiv(seconds, 86400);
        seconds -= days * 86400;
        int hours = Math.floorDiv(seconds, 3600);
        seconds -= hours * 3600;
        int minutes = Math.floorDiv(seconds, 60);
        seconds -= minutes * 60;
        return "in " + (days != 0 ? days + " days " : "") + (hours != 0 ? hours + " hours " : "") + (minutes != 0 ? minutes + " minutes " : "") + (seconds != 0 ? seconds + " seconds " : "");
    }


    public static String stringRepeat(String s, int n) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            builder.append(s);
        }
        return builder.toString();
    }
}
