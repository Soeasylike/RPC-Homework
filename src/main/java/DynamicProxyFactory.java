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

                    System.out.println("classType_Name="+classType.getName());
                    System.out.println("methodName="+method.getName());
                    System.out.println("methodParasType"+method.getParameterTypes());
                    System.out.println("args="+args);

                    RemoteCall call = new RemoteCall(classType.getName(), method.getName(), method.getParameterTypes(), args);
                    connector.send(call);
                    System.out.println("send 成功");
                    call = (RemoteCall) connector.receive();
                    System.out.println("接收返回结果成功");
                    Object result = call.getResult();
                    return result;
                }
                finally {
                    if (connector != null) connector.close();
                }

            }
        };

        return Proxy.newProxyInstance(classType.getClassLoader(), new Class[]{ classType } , handler);
    }
}
