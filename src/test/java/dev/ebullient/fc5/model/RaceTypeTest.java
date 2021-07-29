package dev.ebullient.fc5.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RaceTypeTest extends ParsingTestBase {

    @Test
    public void testDragonbornRace() throws Exception {
        CompendiumType compendium = doParseInputResource("raceDragonborn.xml");

        Assertions.assertNotNull(compendium);
        Assertions.assertFalse(compendium.races.isEmpty(),
            "Races should not be empty, found " + compendium);
        
        RaceType race = compendium.races.get(0);
        Assertions.assertAll(
            () -> assertEquals("Dragonborn", race.name),
            () -> assertEquals(SizeEnum.M, race.size),
            () -> assertEquals(30, race.speed),
            () -> assertEquals("Str 2, Cha 1", race.ability),
            () -> assertEquals("", race.proficiency.textContent),
            () -> assertEquals(AbilityEnum.NONE, race.spellAbility),
            () -> assertEquals(8, race.traits.size()),
            () -> assertEquals("Description", race.traits.get(0).name),
            () -> assertEquals("Age", race.traits.get(1).name),
            () -> assertEquals("Alignment", race.traits.get(2).name),
            () -> assertEquals("Size", race.traits.get(3).name),
            () -> assertEquals("Draconic Ancestry", race.traits.get(4).name),
            () -> assertEquals("Breath Weapon", race.traits.get(5).name),
            () -> assertEquals("Damage Resistance", race.traits.get(6).name),
            () -> assertEquals("Languages", race.traits.get(7).name)
        );
    }
}
