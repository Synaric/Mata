package com.synaric.app.mata.event;

/**
 * 请求变化侧边栏。
 * <br/><br/>Created by Synaric on 2016/10/13 0013.
 */
public class RequestToggleDrawer {

    private boolean toggleOpen ;

    private RequestToggleDrawer() {
        this(true);
    }

    private RequestToggleDrawer(boolean toggleOpen) {
        this.toggleOpen = toggleOpen;
    }

    public static RequestToggleDrawer get() {
        return Holder.INSTANCE;
    }

    public boolean isToggleOpen() {
        return toggleOpen;
    }

    private static class Holder {

        private static RequestToggleDrawer INSTANCE = new RequestToggleDrawer();
    }
}
