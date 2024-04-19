import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by lyriccn on 18/3/27.
 */
public class DynamicProxyFactory {
    public static Object getProxy(final Class classType, final String host, final int port) {

        InvocationHandler handler = new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object args[]) throws Exception {
                Connector connector = null;
                try {
                    connector = new Connector(host, port);
                    RemoteCall call = new RemoteCall(classType.getName(), method.getName(), method.getParameterTypes(), args);
                    connector.send(call);
                    call = (RemoteCall) connector.receive();
                    Object result = call.getResult();
                    return result;
                } finally {
                    if (connector != null) connector.close();
                }

            }
        };

        return Proxy.newProxyInstance(classType.getClassLoader(), new Class[]{ classType } , handler);
    }
}
