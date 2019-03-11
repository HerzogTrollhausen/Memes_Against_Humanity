abstract class InsufficientFilesException extends Exception {
    InsufficientFilesException(String msg) {
        super(msg);
    }
    static class MissingImagesException extends InsufficientFilesException {
        MissingImagesException() {
            super("Es gibt keine Bilder in den Ordnern!");
        }
    }
    static class EmptyTextFileException extends InsufficientFilesException {
        EmptyTextFileException() {
            super("Die Textdatei ist leer!");
        }
    }
}
