import groovy.util.GroovyTestCase
import groovy.mock.interceptor.MockFor

public class PomTest extends GroovyTestCase {






    public void testReplaceVersionInLine() {

        //Pom pom=new Pom(new File("test.xml"))
        Pom pom=new Pom((File) null)

        String line = "<db.sqlite.vxp.version>201103.3</db.sqlite.vxp.version>"
        line = pom.replaceVersionInLine(line,"201102.2")
        assertNotNull(line)
        assertEquals("<db.sqlite.vxp.version>201102.2</db.sqlite.vxp.version>",line)


        line = "<db.sqlite.vxp.version>201103.3</db.sqlite.vxp.version><!-- Some comments -->"
        line = pom.replaceVersionInLine(line,"201102.2")
        assertNotNull(line)
        assertEquals("<db.sqlite.vxp.version>201102.2</db.sqlite.vxp.version><!-- Some comments -->",line)


    }

    //Bof, test dependant of filesystem :(
    public void testReplaceVersionInFile() {

        def result=false

        File pomFile=new File("pomTest.xml")
        Pom pom=new Pom(pomFile)

        pom.replaceVersionInFile("db.sqlite.vxp.version","201103.3")
        pomFile.eachLine { line->
            if(line.contains("<db.sqlite.vxp.version>201103.3</db.sqlite.vxp.version>")){
                result=true
            }
        }
        assertTrue(result)

    }

    public void testEnable(){
        File pomFile=new File("pomTest.xml")
        Pom pom=new Pom()

        def line = "Allons enfants de la patrie, "
        String result =pom.enable(line)
        assertNotNull result
        assertEquals(line,result)


        line = "<!-- Le jour de gloire est arrivé -->"
        result = pom.enable(line)
        assertNotNull(line)
        assertEquals(" Le jour de gloire est arrivé ", result)
    }

    public void testDisable(){
        File pomFile=new File("pomTest.xml")
        Pom pom=new Pom()

        def line = "Allons enfants de la patrie, "
        String result =pom.disable(line)
        assertNotNull result
        //println line.compareTo("<!--Allons enfants de la patrie, -->")
        assertEquals("<!--Allons enfants de la patrie, -->",result)


        line = "<!-- Le jour de gloire est arrivé -->"
        result = pom.disable(line)
        assertNotNull(line)
        assertEquals(line, result)
    }


    public void testGetModuleNameFromXml(){

        File pomFile=new File("pomTest.xml")

        Pom pom=new Pom(pomFile)


        assertEquals("toxin", pom.getModuleNameFromXml("      <module>./documents/toxin</module>"))


    }

    public void testEnableModules(){

        File pomFile=new File("src/test/resources/pomTest.xml")

        Pom pom=new Pom(pomFile)

        List<String> listOfModules=new ArrayList<String>()
        listOfModules.add("useful-info")
        listOfModules.add("toxin")

        pom.enableModules(listOfModules)


        println  pomFile.text.find(~/<!--.*useful/)


        assertTrue(pomFile.text.find(~/<!--.*useful.*module.*/).equals(null))
        assertTrue(pomFile.text.find(~/<!--.*toxin.*module.*/).equals(null))
        assertFalse(pomFile.text.find(~/<!--.*bum.*module.*/).equals(null))
    }

}