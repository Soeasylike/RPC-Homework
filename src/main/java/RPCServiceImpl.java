/**
 * Created by lyriccn on 18/3/27.
 */
public class RPCServiceImpl implements  RPCService {

    @Override
    public String request(String s) {
        return ("收到发来的信息："+s);
    }
}
