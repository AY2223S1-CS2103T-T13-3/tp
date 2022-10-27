package seedu.clinkedin.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.clinkedin.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinkedin.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinkedin.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.commons.core.index.Index;
import seedu.clinkedin.logic.commands.NoteCommand;
import seedu.clinkedin.model.person.Note;

public class NoteCommandParserTest {
    private final NoteCommandParser parser = new NoteCommandParser();

    @Test
    public void parse_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // No index given
        assertParseFailure(parser, "note/Test",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE));

        // No valid prefix given
        assertParseFailure(parser, "1 Test", String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsNoteCommand() {
        // no leading and trailing whitespaces
        NoteCommand expectedNoteCommand = new NoteCommand(Index.fromOneBased(1), new Note("Test"));
        assertParseSuccess(parser, "1 note/Test", expectedNoteCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n \t1 note/Test\t", expectedNoteCommand);
    }
}
