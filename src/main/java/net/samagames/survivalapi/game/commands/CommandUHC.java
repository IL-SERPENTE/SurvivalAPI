package net.samagames.survivalapi.game.commands;

import net.samagames.survivalapi.game.SurvivalGame;
import net.samagames.survivalapi.game.SurvivalPlayer;
import net.samagames.survivalapi.game.SurvivalTeam;
import net.samagames.survivalapi.game.types.SurvivalTeamGame;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CommandUHC implements CommandExecutor
{
    private final SurvivalGame game;

    public CommandUHC(SurvivalGame game)
    {
        this.game = game;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings)
    {
        if(!(commandSender instanceof Player))
            return true;

        if (strings.length > 0)
        {
            if (strings[0].equals("invite"))
            {
                if(this.game instanceof SurvivalTeamGame)
                {
                    if(!this.game.isGameStarted())
                    {
                        String teamRaw = strings[1];
                        String playerRaw = strings[2];
                        SurvivalPlayer player = (SurvivalPlayer) this.game.getPlayer(UUID.fromString(playerRaw));
                        SurvivalTeam aTeam = ((SurvivalTeamGame) this.game).getTeams().getTeam(ChatColor.getByChar(teamRaw));

                        if (aTeam == null)
                            return true;

                        if(!player.hasTeam())
                            if(!aTeam.isInvited(player.getUUID()))
                                aTeam.invite(commandSender.getName(), player.getUUID());
                    }
                }
            }
            else if (strings[0].equals("join"))
            {
                if(this.game instanceof SurvivalTeamGame)
                {
                    if(!this.game.isGameStarted())
                    {
                        String teamRaw = strings[1];
                        String playerRaw = strings[2];
                        SurvivalPlayer player = (SurvivalPlayer) this.game.getPlayer(UUID.fromString(playerRaw));
                        SurvivalTeam aTeam = ((SurvivalTeamGame) this.game).getTeams().getTeam(ChatColor.getByChar(teamRaw));

                        if (aTeam == null)
                            return true;

                        if(!player.hasTeam())
                            if(aTeam.isInvited(player.getUUID()))
                                aTeam.join(player.getUUID());
                    }
                }
            }
        }

        return true;
    }
}