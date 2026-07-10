package be.moens.schemer.utils.api.trackers;

import dev.twilite.game.facade.Stat;

public record ExperienceGainedEvent(Stat skill, int amount) {}
