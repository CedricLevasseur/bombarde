import groovy.util.GroovyTestCase

public class MatchingTest extends GroovyTestCase {

    public void testGetVersion() {

        Matching matching = new Matching()
        String version = matching.getVersion("Toxin")
        assertNotNull(version)
        assertEquals("toxin.version",version)

    }

    public void testGetModule() {

        Matching matching = new Matching()
        String module = matching.getModule("InfosUtiles")
        assertNotNull(module)
        assertEquals("useful-info",module)

    }


}