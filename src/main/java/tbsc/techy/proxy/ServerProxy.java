package tbsc.techy.proxy;

/**
 * Server proxy, stuff will run only on server
 *
 * Created by tbsc on 3/27/16.
 */
public class ServerProxy extends CommonProxy {

    @Override
    public void preInitClient() {
        // NO-OP
    }

    @Override
    public void initClient() {

    }

    @Override
    public void preInit() {

    }

}
