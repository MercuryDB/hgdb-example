package examples.schema;

import com.github.mercurydb.annotations.HgValue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FamilyEvent {
    private Family family;
    private LocalDate dateOfOccurrence;
    private String description;

    public FamilyEvent(Family family, String dateOfOccurrence, String description) {
        this.family = family;
        this.description = description;
        this.dateOfOccurrence = LocalDate.parse(dateOfOccurrence, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    @HgValue("family")
    public Family getAssociatedFamily() {
        return family;
    }

    @HgValue("date")
    public LocalDate getDateOfOccurrence() {
        return dateOfOccurrence;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return dateOfOccurrence + ": " + description;
    }
}
