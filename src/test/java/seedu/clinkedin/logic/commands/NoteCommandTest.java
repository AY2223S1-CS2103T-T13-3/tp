package seedu.clinkedin.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.clinkedin.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinkedin.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.commons.core.index.Index;
import seedu.clinkedin.model.Model;
import seedu.clinkedin.model.ModelManager;
import seedu.clinkedin.model.UserPrefs;
import seedu.clinkedin.model.person.Note;
import seedu.clinkedin.model.person.Person;
import seedu.clinkedin.testutil.PersonBuilder;

public class NoteCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new NoteCommand(Index.fromZeroBased(0), null));
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new NoteCommand(null, new Note("")));
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Note note = new Note("She is strong at Java.");
        NoteCommand noteCommand = new NoteCommand(Index.fromOneBased(1), note);

        Person personToEdit = model.getFilteredPersonList().get(0);
        Person editedPerson = new PersonBuilder(personToEdit).withNote(note.value).build();
        String expectedMessage = String.format(NoteCommand.MESSAGE_ADD_NOTE_SUCCESS, editedPerson);

        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);
        assertCommandSuccess(noteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noChangeInValue_success() {
        Person personToEdit = model.getFilteredPersonList().get(0);
        NoteCommand noteCommand = new NoteCommand(Index.fromOneBased(1), personToEdit.getNote());

        Person editedPerson = new PersonBuilder(personToEdit).withNote(personToEdit.getNote().value).build();
        String expectedMessage = String.format(NoteCommand.MESSAGE_ADD_NOTE_SUCCESS, editedPerson);

        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);
        assertCommandSuccess(noteCommand, model, expectedMessage, expectedModel);
    }

}
