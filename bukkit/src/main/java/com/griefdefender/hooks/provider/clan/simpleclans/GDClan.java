/*
 * This file is part of GDHooks, licensed under the MIT License (MIT).
 *
 * Copyright (c) bloodmc
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.griefdefender.hooks.provider.clan.simpleclans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.checkerframework.checker.nullness.qual.Nullable;

import com.griefdefender.api.Clan;
import com.griefdefender.api.ClanPlayer;
import com.griefdefender.api.clan.ClanHome;
import com.griefdefender.api.clan.Rank;
import com.griefdefender.hooks.GDHooks;
import com.griefdefender.lib.flowpowered.math.vector.Vector3i;

import com.griefdefender.lib.kyori.adventure.text.Component;

public class GDClan implements Clan {

    private Map<String, Rank> ranks = new HashMap<>();
    private final net.sacredlabyrinth.phaed.simpleclans.Clan pluginClan;

    public GDClan(net.sacredlabyrinth.phaed.simpleclans.Clan clan) {
        this.pluginClan = clan;
        for (net.sacredlabyrinth.phaed.simpleclans.Rank rank : this.pluginClan.getRanks()) {
            this.ranks.put(rank.getName(), new GDRank(rank));
        }
    }

    @Override
    public String getId() {
        return "simpleclans:" + this.pluginClan.getName().toLowerCase();
    }

    @Override
    public String getName() {
        return this.pluginClan.getName().toLowerCase();
    }

    @Override
    public Component getNameComponent() {
        return Component.text(this.pluginClan.getName());
    }

    @Override
    public Component getTagComponent() {
        return Component.text(this.pluginClan.getColorTag());
    }

    @Override
    public String getTag() {
        return this.pluginClan.getTag();
    }

    @Override
    public String getDescription() {
        return this.pluginClan.getDescription();
    }

    @Override
    public UUID getBaseWorldUniqueId() {
        return this.pluginClan.getHomeLocation().getWorld().getUID();
    }

    @Override
    public Vector3i getBasePos() {
        return new Vector3i(this.pluginClan.getHomeLocation().getBlockX(), this.pluginClan.getHomeLocation().getBlockY(), this.pluginClan.getHomeLocation().getBlockZ());
    }

    @Override
    public List<Clan> getAllies() {
        List<Clan> clans = new ArrayList<>();
        for (String tag : this.pluginClan.getAllies()) {
            final Clan clan = GDHooks.getInstance().getClanProvider().getClan(tag);
            if (clan != null) {
                clans.add(clan);
            }
        }
        return clans;
    }

    @Override
    public List<Clan> getRivals() {
        List<Clan> clans = new ArrayList<>();
        for (String tag : this.pluginClan.getRivals()) {
            final Clan clan = GDHooks.getInstance().getClanProvider().getClan(tag);
            if (clan != null) {
                clans.add(clan);
            }
        }
        return clans;
    }

    @Override
    public List<ClanPlayer> getMembers(boolean onlineOnly) {
        final List<ClanPlayer> clanPlayers = new ArrayList<>();
        for (net.sacredlabyrinth.phaed.simpleclans.ClanPlayer pluginClanPlayer : this.pluginClan.getMembers()) {
            final ClanPlayer clanPlayer = GDHooks.getInstance().getClanProvider().getClanPlayer(pluginClanPlayer.getUniqueId());
            if (clanPlayer != null) {
                clanPlayers.add(clanPlayer);
            }
        }

        return clanPlayers;
    }

    @Override
    public List<ClanPlayer> getLeaders(boolean onlineOnly) {
        final List<ClanPlayer> clanPlayers = new ArrayList<>();
        for (net.sacredlabyrinth.phaed.simpleclans.ClanPlayer pluginClanPlayer : this.pluginClan.getLeaders()) {
            final ClanPlayer clanPlayer = GDHooks.getInstance().getClanProvider().getClanPlayer(pluginClanPlayer.getUniqueId());
            if (clanPlayer != null) {
                clanPlayers.add(clanPlayer);
            }
        }

        return clanPlayers;
    }

    @Override
    public List<com.griefdefender.api.clan.Rank> getRanks() {
        List<com.griefdefender.api.clan.Rank> ranks = new ArrayList<>();
        for (net.sacredlabyrinth.phaed.simpleclans.Rank rank : this.pluginClan.getRanks()) {
            ranks.add(new GDRank(rank));
        }
        return ranks;
    }

    public net.sacredlabyrinth.phaed.simpleclans.Clan getInternalClan() {
        return this.pluginClan;
    }

    @Override
    public @Nullable Rank getRank(String rank) {
        return this.ranks.get(rank);
    }

    @Override
    public boolean isAlly(String tag) {
        return this.pluginClan.isAlly(tag);
    }

    @Override
    public boolean isRival(String tag) {
        return this.pluginClan.isRival(tag);
    }

    @Override
    public List<ClanHome> getHomes() {
        return Collections.emptyList();
    }
}
