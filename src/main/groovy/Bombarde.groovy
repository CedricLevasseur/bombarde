public class Bombarde {

    public static String DEFAULT_CONF_DIR = "src/main/ressources/"
    public static String DEFAULT_CONF_FILE = "mail.in"

    Map<String, String> listOfZipDataFile



    public Bombarde() {
        listOfZipDataFile = new HashMap<String, String>()
    }


    private File load(String filename) {

        File file
        if (filename != null && filename != "") {

            file = new File(filename);
            if (file.exists()) {
                println "Using Conf file :" + file
                return file
            }
            file = new File(DEFAULT_CONF_DIR + filename)
            if (file.exists()) {
                println "Using Conf file :" + file
                return file
            }
        }
        file = new File(DEFAULT_CONF_DIR + DEFAULT_CONF_FILE)
        if (file.exists()) {
            println "Using Conf file :" + file
            return file
        }
        System.exit(1)

    }


    public void parse(File conf) {

        conf.eachLine {
            line ->
            //String result = line.find(~/\S*\.zip/) //matches("\w\.zip")
            String result = line.find(~/[a-zA-Z_1-9\-.].*\.zip/)
            if (result != null) {
                String left = result.find(~/[^0-9]*/)
                if (left.startsWith("http:")) {
                    left = left.find(~/([^\/]*)$/)
                }
                left = left.replaceFirst("-\$", "")
                String right = result.find(~/[0-9 -_.]*.zip/)
                right = right.replaceFirst("\\.zip\$", "")
                right = right.replaceFirst("^-", "")
                listOfZipDataFile.put(left, right)
            }

        }
    }



    public static void main(String[] args) {
        println "Bombarde, a BackOffice Mail Archives Decrypter"
        Bombarde bombarde = new Bombarde()
        File conf = bombarde.load(args ? args[0] : "")
        bombarde.parse(conf)
        println bombarde


        Project p = new Project()
        p.parse()
        println p

        bombarde.saveProperties()

    }



    public void loadProperties() {
        // create application properties with default


    }

    public void saveProperties() {

        Properties p = new Properties()
        p.put("a", "1")
        p.put("ab", "123")
        def prop = new ConfigSlurper().parse(p)
        println prop.getClass().getName()
        new File(DEFAULT_CONF_DIR + "matching.groovy").withWriter {writer -> prop.writeTo(writer)}


    }



    public String toString() {
        String toReturn = "Bombarde{"
        listOfZipDataFile.each { toReturn += it.toString() + "\n"}
        toReturn += '}';
        return toReturn
    }
}
