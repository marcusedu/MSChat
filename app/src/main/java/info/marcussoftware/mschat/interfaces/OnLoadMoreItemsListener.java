package info.marcussoftware.mschat.interfaces;

/**
 * Callback to lazy load
 */
public interface OnLoadMoreItemsListener {
    /**
     * Called when list of chat feed is in end to load and call load more items
     *
     * @param latestItem    The last item loaded
     * @param NumTotalItems Total of item loaded in moment
     */
    void loadMoreItem(Message latestItem, int NumTotalItems);
}