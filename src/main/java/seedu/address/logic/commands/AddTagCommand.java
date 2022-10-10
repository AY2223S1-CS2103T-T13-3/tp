package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEGREETAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBTYPETAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILLTAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.UniqueTagTypeMap;

import java.util.List;

/**
 * Adds a tag of a specific tag type to a person.
 */
public class AddTagCommand extends Command {
    public static final String COMMAND_WORD = "addTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tag of a specific tag type to a person.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + "[" + PREFIX_SKILLTAG + " TAG] "
            + "[" + PREFIX_DEGREETAG + " TAG] "
            + "[" + PREFIX_JOBTYPETAG + " TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + PREFIX_SKILLTAG + "Java "
            + PREFIX_DEGREETAG + "Undergraduate "
            + PREFIX_JOBTYPETAG + "Internship";

    public static final String MESSAGE_SUCCESS = "New tags added: : %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists";

    private final Index targetIndex;
    private final UniqueTagTypeMap toAdd;
    private EditCommand.EditPersonDescriptor editPersonDescriptor;

    /**
     * Creates an AddTagCommand to add the specified {@code Tag}
     */
    public AddTagCommand(Index targetIndex, UniqueTagTypeMap toAdd) {
        requireNonNull(targetIndex);
        requireNonNull(toAdd);
        this.targetIndex = targetIndex;
        this.toAdd = toAdd;
        editPersonDescriptor = new EditCommand.EditPersonDescriptor();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        editPersonDescriptor.setTags(toAdd);

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedPerson));
    }

    private static Person createEditedPerson(Person personToEdit, EditCommand.EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        UniqueTagTypeMap updatedTags = personToEdit.getTags().mergeTagTypeList(editPersonDescriptor.getTags());

        return new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTagCommand // instanceof handles nulls
                && targetIndex.equals(((AddTagCommand) other).targetIndex)
                && toAdd.equals(((AddTagCommand) other).toAdd));
    }
}
