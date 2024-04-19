import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ConfigReader {

    public static void main(String[] args) {
        register("RPCService");
    }
    public static Object register(String interfaceName ){//接受接口名
        Object obj=null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse("config.xml");
            doc.getDocumentElement().normalize();

            NodeList serverList = doc.getElementsByTagName("server");
            NodeList serverImplList = doc.getElementsByTagName("serverImpl");
            int len1=serverList.getLength();
            String server="";
            String serverImpl="";
            for(int i=0;i<len1;i++){
                Element serverElement = (Element) serverList.item(i);
                server = serverElement.getTextContent();
                if(server.equals(interfaceName)) {
                    Element serverImplElement = (Element) serverImplList.item(i);
                    serverImpl = serverImplElement.getTextContent();//得到实现类名
                    //利用发射获取实现类
                    obj=Class.forName(serverImpl).newInstance();
                    break;
                }
            }
            System.out.println("Server: " + server);
            System.out.println("serverImpl: " + serverImpl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
