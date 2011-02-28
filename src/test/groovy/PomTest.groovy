import groovy.util.GroovyTestCase

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
        println line
        assertNotNull(line)
        assertEquals("<db.sqlite.vxp.version>201102.2</db.sqlite.vxp.version><!-- Some comments -->",line)


    }



}