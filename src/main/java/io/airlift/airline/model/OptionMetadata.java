package io.airlift.airline.model;

import com.google.common.collect.ImmutableSet;
import io.airlift.airline.Accessor;
import io.airlift.airline.OptionType;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Streams.stream;
import static java.util.Objects.requireNonNull;

public class OptionMetadata
{
    private final OptionType optionType;
    private final Set<String> options;
    private final String title;
    private final String description;
    private final int arity;
    private final boolean required;
    private final boolean hidden;
    private final Set<String> allowedValues;
    private final Set<Accessor> accessors;

    public OptionMetadata(OptionType optionType,
            Iterable<String> options,
            String title,
            String description,
            int arity,
            boolean required,
            boolean hidden,
            Iterable<String> allowedValues,
            Iterable<Field> path)
    {
        requireNonNull(optionType, "optionType is null");
        requireNonNull(options, "options is null");
        checkArgument(stream(options).findAny().isPresent(), "options is empty");
        requireNonNull(title, "title is null");
        requireNonNull(path, "path is null");
        checkArgument(stream(path).findAny().isPresent(), "path is empty");

        this.optionType = optionType;
        this.options = ImmutableSet.copyOf(options);
        this.title = title;
        this.description = description;
        this.arity = arity;
        this.required = required;
        this.hidden = hidden;

        if (allowedValues != null) {
            this.allowedValues = ImmutableSet.copyOf(allowedValues);
        }
        else {
            this.allowedValues = null;
        }

        this.accessors = ImmutableSet.of(new Accessor(path));
    }

    public OptionMetadata(Iterable<OptionMetadata> options)
    {
        requireNonNull(options, "options is null");
        checkArgument(stream(options).findAny().isPresent(), "options is empty");

        OptionMetadata option = options.iterator().next();

        this.optionType = option.optionType;
        this.options = option.options;
        this.title = option.title;
        this.description = option.description;
        this.arity = option.arity;
        this.required = option.required;
        this.hidden = option.hidden;
        if (option.allowedValues != null) {
            this.allowedValues = ImmutableSet.copyOf(option.allowedValues);
        }
        else {
            this.allowedValues = null;
        }

        Set<Accessor> accessors = new HashSet<>();
        for (OptionMetadata other : options) {
            checkArgument(option.equals(other),
                    "Conflicting options definitions: %s, %s", option, other);

            accessors.addAll(other.getAccessors());
        }
        this.accessors = ImmutableSet.copyOf(accessors);
    }

    public OptionType getOptionType()
    {
        return optionType;
    }

    public Set<String> getOptions()
    {
        return options;
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }

    public int getArity()
    {
        return arity;
    }

    public boolean isRequired()
    {
        return required;
    }

    public boolean isHidden()
    {
        return hidden;
    }

    public boolean isMultiValued()
    {
        return accessors.iterator().next().isMultiValued();
    }

    public Class<?> getJavaType()
    {
        return accessors.iterator().next().getJavaType();
    }

    public Set<Accessor> getAccessors()
    {
        return accessors;
    }

    public Set<String> getAllowedValues()
    {
        return allowedValues;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OptionMetadata that = (OptionMetadata) o;

        if (arity != that.arity) {
            return false;
        }
        if (hidden != that.hidden) {
            return false;
        }
        if (required != that.required) {
            return false;
        }
        if (allowedValues != null ? !allowedValues.equals(that.allowedValues) : that.allowedValues != null) {
            return false;
        }
        if (description != null ? !description.equals(that.description) : that.description != null) {
            return false;
        }
        if (optionType != that.optionType) {
            return false;
        }
        if (!options.equals(that.options)) {
            return false;
        }
        if (!title.equals(that.title)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = optionType.hashCode();
        result = 31 * result + options.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + arity;
        result = 31 * result + (required ? 1 : 0);
        result = 31 * result + (hidden ? 1 : 0);
        result = 31 * result + (allowedValues != null ? allowedValues.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append("OptionMetadata");
        sb.append("{optionType=").append(optionType);
        sb.append(", options=").append(options);
        sb.append(", title='").append(title).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", arity=").append(arity);
        sb.append(", required=").append(required);
        sb.append(", hidden=").append(hidden);
        sb.append(", accessors=").append(accessors);
        sb.append('}');
        return sb.toString();
    }
}
