package io.github.some_example_name.assets;

public class Notes {

    private int notesQuantity;

    public Notes() {

    }

    public int getNotesQuantity() {
        return notesQuantity;
    }

    public void setNotesQuantity(int notesQuantity) {
        this.notesQuantity = notesQuantity;
    }

    public void addNote() {
        notesQuantity++;
    }
}
