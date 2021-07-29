package dev.ebullient.fc5.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MonsterTypeTest extends ParsingTestBase {
    
    @Test
    public void testAnkheg() throws Exception {
        CompendiumType compendium = doParseInputResource("monsterAnkheg.xml");

        Assertions.assertNotNull(compendium);
        Assertions.assertFalse(compendium.monsters.isEmpty(),
            "Monsters should not be empty, found " + compendium);
        
        MonsterType monster = compendium.monsters.get(0);
        Assertions.assertAll(
            () -> assertEquals("Ankheg", monster.name),
            () -> assertEquals(SizeEnum.L, monster.size),
            () -> assertEquals("monstrosity", monster.type),
            () -> assertEquals("Unaligned", monster.alignment),
            () -> assertEquals("14 (natural armor, 11 while prone)", monster.ac),
            () -> assertEquals("39 (6d10+6)", monster.hp),
            () -> assertEquals("walk 30 ft., burrow 10 ft.", monster.speed),

            () -> assertEquals(17, monster.scores.strength),
            () -> assertEquals(11, monster.scores.dexterity),
            () -> assertEquals(13, monster.scores.constitution),
            () -> assertEquals(1, monster.scores.intelligence),
            () -> assertEquals(13, monster.scores.wisdom),
            () -> assertEquals(6, monster.scores.charisma)
            ,
            () -> assertEquals("", monster.save),
            () -> assertEquals("", monster.skill),
            () -> assertEquals(11, monster.passive),
            () -> assertEquals("", monster.languages),

            () -> assertEquals("2", monster.cr),
            () -> assertEquals("", monster.resist),
            () -> assertEquals("", monster.immune),
            () -> assertEquals("", monster.vulnerable),
            () -> assertEquals("", monster.conditionImmune),
            () -> assertEquals("darkvision 60 ft., tremorsense 60 ft.", monster.senses),

            () -> assertEquals(Collections.emptyList(), monster.trait),
            () -> assertEquals(2, monster.action.size()),
            () -> assertEquals(Collections.emptyList(), monster.legendary),
            () -> assertEquals(Collections.emptyList(), monster.reaction),

            () -> assertTrue(monster.description.startsWith("An ankheg resembles ")),
            () -> assertEquals("grassland, forest", monster.environment)
        );
    }
}
