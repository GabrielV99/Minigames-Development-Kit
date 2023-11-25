package ro.Gabriel.Placeholder.test2;

public enum CustomPlaceholder {

    COMMAND_PLACEHOLDER("%command%") {
        @Override
        public String makeReplacement(String message) {
            return null;
        }
    };

    CustomPlaceholder(String placeholder) {

    }

    private String placeholder;

    public abstract String makeReplacement(String message);

}
