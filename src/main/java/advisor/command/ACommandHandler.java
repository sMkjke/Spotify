package advisor.command;

import advisor.engine.SpotifyEngine;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class ACommandHandler {

    protected Paging pager;

    protected boolean loginRequired = true;

    public abstract void handle(SpotifyEngine spotifyEngine, String parameters) throws IOException,
            InterruptedException, URISyntaxException;

    public boolean isLoginRequired() {
        return loginRequired;
    }

    public boolean hasPaging() {
        return true;
    }

    public void handlePage(SpotifyEngine spotifyEngine,
                           String pageChangeDirection) {
        try {
            if (pager.getOffsetNum() + pager.getPageSize() <= pager.getTotalAvailableItems()
                    && pageChangeDirection.equals("next")) {
                pager.setPageNum(pager.getPageNum() + 1);
                pager.setOffsetNum(pager.getOffsetNum() + pager.getPageSize());
                handle(spotifyEngine, pager.getCurrentParams());
            } else if ((pager.getOffsetNum() - pager.getPageSize() >= 0)
                    && pageChangeDirection.equals("prev")) {
                pager.setPageNum(pager.getPageNum() - 1);
                pager.setOffsetNum(pager.getOffsetNum() - pager.getPageSize());
                handle(spotifyEngine, pager.getCurrentParams());
            } else {
                System.out.println("No more pages.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String getApiUri(String commandUri) {
        URIBuilder uri = new URIBuilder();
        uri.setPath(commandUri);
        uri.addParameter("offset", String.valueOf(pager.getOffsetNum()));
        uri.addParameter("limit", String.valueOf(pager.getPageSize()));
        return uri.toString();
    }

    protected void printCurrentPagingState() {
        System.out.printf("---PAGE %d OF %d---\n",
                pager.getPageNum() + 1, (pager.getTotalAvailableItems() % pager.getPageSize() == 0) ?
                        (pager.getTotalAvailableItems() / pager.getPageSize())
                        : (pager.getTotalAvailableItems() / pager.getPageSize()) + 1);
    }
}