package com.beacmc.beacmcboss.util.requirement;

import com.beacmc.beacmcboss.api.requirement.Requirement;
import com.beacmc.beacmcboss.boss.Boss;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConditionRequirement extends Requirement {
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

        boolean condition = false;

        try {
            switch (operator) {
                case "==" : {
                    condition = first.equals(second);
                    break;
                }
                case "!=" : {
                    condition = !first.equals(second);
                    break;
                }
                case "<" : {
                    condition = Integer.parseInt(first) < Integer.parseInt(second);
                    break;
                }
                case ">" : {
                    condition = Integer.parseInt(first) > Integer.parseInt(second);
                    break;
                }
                case "<=" : {
                    condition = Integer.parseInt(first) <= Integer.parseInt(second);
                    break;
                }
                case ">=" : {
                    condition = Integer.parseInt(first) >= Integer.parseInt(second);
                    break;
                }
                default : {
                    condition = false;
                    break;
                }
            };
        } catch (NumberFormatException e) { }
        return condition;
    }
}
