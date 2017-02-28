package de.effectivetrainings.teleprompter.adapter.inbound;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class InstructionParserTest {

    private InstructionParser testee;

    @Before
    public void setUp() throws Exception {
        testee = new InstructionParser();
    }

    @Test
    public void parseInstruction_matches() {
        final Optional<Instruction> instruction = new InstructionParser().instruction("### format hinweis das ist ein wirklich wichtiger hinweis");
        assertTrue(instruction.isPresent());
        assertEquals("format", instruction.get().getType());
        assertEquals("hinweis", instruction.get().getHeadline());
        assertEquals("das ist ein wirklich wichtiger hinweis", instruction.get().getContent());
    }

    @Test
        public void parseInstruction_matchesWithDashes() {
            final Optional<Instruction> instruction = new InstructionParser().instruction("### format hinweis-with-dash das ist ein wirklich wichtiger hinweis");
            assertTrue(instruction.isPresent());
            assertEquals("format", instruction.get().getType());
            assertEquals("hinweis-with-dash", instruction.get().getHeadline());
            assertEquals("das ist ein wirklich wichtiger hinweis", instruction.get().getContent());
        }


    @Test
    public void parseInstruction_noMatch_withInvalidPrefix() {
        final Optional<Instruction> instruction = new InstructionParser().instruction("## test test");
        assertFalse(instruction.isPresent());
    }
}