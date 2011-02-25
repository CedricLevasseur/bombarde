/**
 * Created by IntelliJ IDEA.
 * User: vidal
 * Date: 25/02/11
 * Time: 09:05
 * To change this template use File | Settings | File Templates.
 */
class Project {
    String version
    String parentVersion
    Map<String, String> properties = new HashMap<String, String>()

    public void parse() {
        File file = new File("src/main/ressources/project.xml")
        def parser = new XmlParser()
        def result = parser.parse(file);

        version = result.version.text()
        parentVersion = result.parentVersion.text()
        println result.properties.key1.text()
//        result.properties.iterator(){  it ->
//                .each(){
//            properties.put(it.name().it.text())
//        }


    }

    @Override
    String toString() {
        return "version=$version,parentVersion=$parentVersion,properties=$properties"
    }


}
