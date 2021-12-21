package al.edu.cit.webflix.languages;

public class LanguageBuilder {
    private final Language Language = new Language();

    public LanguageBuilder setId(int id) {
        Language.setId(id);
        return this;
    }

    public LanguageBuilder setName(String name) {
        Language.setName(name);
        return this;
    }

    public Language build() {
        return Language;
    }
}
