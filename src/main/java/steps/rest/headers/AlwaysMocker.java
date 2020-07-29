package steps.rest.headers;

public class AlwaysMocker extends Mocker {
    @Override
    public boolean isMocable(String url) {
        return true;
    }
}
