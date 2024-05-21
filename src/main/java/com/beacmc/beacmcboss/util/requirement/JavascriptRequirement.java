package com.beacmc.beacmcboss.util.requirement;

import com.beacmc.beacmcboss.api.requirement.Requirement;
import com.beacmc.beacmcboss.boss.Boss;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavascriptRequirement extends Requirement {
    @Override
    public String getName() {
        return "[condition]";
    }

    @Override
    public String getDescription() {
        return "copy of checks from JavaScript";
    }

    @Override
    public boolean execute(Player player, Boss boss, String param) {
        final String msg = PlaceholderAPI.setPlaceholders(player, param);
        if(msg.isEmpty())
            return false;

        final Pattern pattern = Pattern.compile("(.+?)(==|!=|<=|>=|<|>)(.+)");
        final Matcher matcher = pattern.matcher(msg);

        if(!matcher.matches())
            return false;

        final String first = matcher.group(1).trim();
        final String operator = matcher.group(2);
        final String second = matcher.group(3).trim();

        try {
            return switch (operator) {
                case "==" -> first.equals(second);
                case "!=" -> !first.equals(second);
                case "<" -> Integer.parseInt(first) < Integer.parseInt(second);
                case ">" -> Integer.parseInt(first) > Integer.parseInt(second);
                case "<=" -> Integer.parseInt(first) <= Integer.parseInt(second);
                case ">=" -> Integer.parseInt(first) >= Integer.parseInt(second);
                default -> false;
            };
        } catch (NumberFormatException e) { }
        return false;
    }
}
