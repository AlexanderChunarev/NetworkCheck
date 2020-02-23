package company;

public enum Colors {
    RED("\033[0;31m"),
    RESET("\033[0m"),
    YELLOW("\u001B[33m"),
    GREEN("\033[0;32m");
    private String code;

    Colors(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
