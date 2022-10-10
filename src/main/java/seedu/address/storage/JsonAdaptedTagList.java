package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.util.List;

/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedTagList {

    private final List<JsonAdaptedTag> tags;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedTagList(List<JsonAdaptedTag> tags) {
        this.tags = tags;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedTagList(UniqueTagList source) {
        this.tags = source.toStream.map(JsonAdaptedTag::new);
    }

    @JsonValue
    public List<JsonAdaptedTag> getTagList() {
        return tags;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public UniqueTagList toModelType() throws IllegalValueException {
        UniqueTagList list = new UniqueTagList();
        for (JsonAdaptedTag tag: tags) {
            list.add(tag.toModelType());
        }
        return list;
    }

}
