import groovy.util.GroovyTestCase

public class MatchingTest extends GroovyTestCase {

    public void testGetVersion() {

        Matching matching = new Matching()
        String version = matching.getVersion("Toxin")
        println ">>>>"+version

        assertNotNull(version)




    }



}