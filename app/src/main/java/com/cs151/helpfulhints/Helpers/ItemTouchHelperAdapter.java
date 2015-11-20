package com.cs151.helpfulhints.Helpers;

public interface ItemTouchHelperAdapter {


    /**
     * Called when an item has been dragged far enough to trigger a move. This is called every time
     * an item is shifted, and not at the end of a "drop" event.
     */
    boolean onItemMove(int fromPosition, int toPosition);

    /**
     * Called when an item has been dismissed by a swipe.
     */
    void onItemDismiss(int position);
}
