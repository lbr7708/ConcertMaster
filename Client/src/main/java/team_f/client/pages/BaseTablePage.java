package team_f.client.pages;

import java.util.List;

public abstract class BaseTablePage<R, V, L, S> extends BasePage<R, V, L, S> {
    protected PageAction<R, V> _onChange;
    protected PageAction<List<L>, S> _loadList;

    /**
     * sets the event handler for recognizing when a table content changed
     * @param action
     */
    public void setOnListChanged(PageAction<R, V> action) {
        _onChange = action;
    }

    /**
     * sets the event handler for changing the list content of the component
     * @param action
     */
    public void setOnLoadList(PageAction<List<L>, S> action) {
        _loadList = action;
    }
}
