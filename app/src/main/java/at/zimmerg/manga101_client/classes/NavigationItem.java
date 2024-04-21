package at.zimmerg.manga101_client.classes;

public class NavigationItem {
    private boolean isTop;

    public NavigationItem(boolean isTop) {
        this.isTop = isTop;
    }

    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
    }
}
