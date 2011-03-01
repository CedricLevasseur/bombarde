public class Bombarde {

    public static String DEFAULT_CONF_DIR = "src/main/resources/"
    public static String DEFAULT_MAIL_FILE = "mail.in"
    public static String DEFAULT_CONF_FILE = "bombarde.conf"

    Map<String, String> listOfZipDataFile
    Matching matching
    Conf conf
    Pom pom



    public Bombarde() {
        listOfZipDataFile = new HashMap<String, String>()
        matching=new Matching()
        conf=new Conf(DEFAULT_CONF_DIR+DEFAULT_CONF_FILE)
        conf.parse()
        pom = new Pom(new File(conf.getPomPath()))
    }


    private File load(String filename) {

        File file
        if (filename != null && filename != "") {

            file = new File(filename);
            if (file.exists()) {
                println "Using mail input file :" + file
                return file
            }
            file = new File(DEFAULT_CONF_DIR + filename)
            if (file.exists()) {
                println "Using mail input file :" + file
                return file
            }
        }
        file = new File(DEFAULT_CONF_DIR + DEFAULT_MAIL_FILE)
        if (file.exists()) {
            println "Usingmail input file :" + file
            return file
        }
        println "Mail input file not found"
        System.exit(1)

    }



    public void parse(File input) {

        input.eachLine {
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

    private resetMatchingFile(){
        matching.resetMatchingFile()
    }

    public static void main(String[] args) throws Exception {

        println "Bombarde, a BackOffice Mail Archives Decrypter"

        Bombarde bombarde = new Bombarde()

        def cli = new CliBuilder(usage: 'groovy bombadge -[hr] [file]')
        // Create the list of options.
        cli.with {
            h longOpt: 'help', 'Show usage information'
            r longOpt: 'reset-matching', 'reset matching.groovy (a property file matching mail\'s filename and pom\'s nodename)'
        }

        def options = cli.parse(args)
        if (!options) {
            return
        }
        // Show usage text when -h or --help option is used.
        if (options.h) {
            cli.usage()
            return
        }

        if (options.r) {  // Using short option.
            bombarde.resetMatchingFile()
        }

        // Handle all non-option arguments.
        def inputFilename = ''  // Default is empty prefix.
        def extraArguments = options.arguments()
        if (extraArguments) {
            if (extraArguments.size() > 1) {
                inputFilename = extraArguments[1..-1].join(' ')
            }
        }

        File input = bombarde.load(inputFilename)
        bombarde.parse(input)
        println bombarde

    }


    public String toString() {
        String toReturn = "Bombarde{"
        listOfZipDataFile.each { toReturn += it.toString() + "\n"}
        toReturn += '}';
        return toReturn
    }
}
