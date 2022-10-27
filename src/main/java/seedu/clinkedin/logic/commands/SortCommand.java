package seedu.clinkedin.logic.commands;

import seedu.clinkedin.logic.commands.exceptions.CommandException;
import seedu.clinkedin.model.Model;
import seedu.clinkedin.model.person.Person;

import java.util.Comparator;

import static java.util.Objects.requireNonNull;

/**
 * Sorts candidates based on their ratings. Candidates with no ratings will be placed at the bottom of the list.
 *
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts persons by their rating"
            + "from high to low.\n"
            + "Parameters: "
            + "(none)"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Sorted by rating!";

    private final Comparator<Person> comparator;

    public SortCommand() {
        this.comparator = (p1, p2) -> p1.compareByRating(p2);
    }

    public Comparator<Person> getComparator() {
        return comparator;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateSort(getComparator());
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
            || (other instanceof SortCommand // instanceof handles nulls
                && comparator.equals(((SortCommand) other).comparator)); // state check
    }

}
