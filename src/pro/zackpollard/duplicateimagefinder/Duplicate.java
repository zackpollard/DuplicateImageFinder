package pro.zackpollard.duplicateimagefinder;

public class Duplicate {

    private final String key;
    private final String value;

    public Duplicate(String key, String value) {

        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}