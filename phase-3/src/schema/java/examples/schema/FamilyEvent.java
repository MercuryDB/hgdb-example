package examples.schema;

import com.github.mercurydb.annotations.HgValue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FamilyEvent {
    private FamilyRelation family;
    private LocalDate dateOfOccurrence;
    private String description;

    public FamilyEvent(FamilyRelation family, String dateOfOccurrence, String description) {
        this.family = family;
        this.description = description;
        this.dateOfOccurrence = LocalDate.parse(dateOfOccurrence, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    @HgValue("family")
    public FamilyRelation getAssociatedFamily() {
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
