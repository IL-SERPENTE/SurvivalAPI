package net.samagames.survivalapi.game.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.Witch;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * This file is part of SurvivalAPI.
 *
 * SurvivalAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SurvivalAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SurvivalAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
public class NaturalListener implements Listener
{
    /**
     * Disable weather
     *
     * @param event Event
     */
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event)
    {
        event.setCancelled(true);
    }

    /**
     * Patching witch's potions
     *
     * @param event Event
     */
    @EventHandler
    public void onPotionSplash(PotionSplashEvent event)
    {
        ThrownPotion potion = event.getPotion();

        if (potion.getShooter() instanceof Witch)
        {
            event.setCancelled(true);

            List<PotionEffectType> potionEffects = new ArrayList<>();
            potionEffects.add(PotionEffectType.SLOW_DIGGING);
            potionEffects.add(PotionEffectType.CONFUSION);
            potionEffects.add(PotionEffectType.NIGHT_VISION);
            potionEffects.add(PotionEffectType.HUNGER);
            potionEffects.add(PotionEffectType.BLINDNESS);

            PotionEffect selected = new PotionEffect(potionEffects.get(new Random().nextInt(potionEffects.size())), 20 * 15, 1);

            for (LivingEntity ent : event.getAffectedEntities())
                ent.addPotionEffect(selected);
        }
    }

    /**
     * Cancelling natural regeneration
     *
     * @param event Event
     */
    @EventHandler
    public void onEntityRegainHealth(EntityRegainHealthEvent event)
    {
        if (event.getRegainReason() == EntityRegainHealthEvent.RegainReason.EATING || event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED)
            event.setCancelled(true);
    }
}
