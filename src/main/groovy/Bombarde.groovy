public class Bombarde {

    public static String DEFAULT_CONF_DIR = "src/main/ressources/"
    public static String DEFAULT_MAIL_FILE = "mail.in"
    public static String DEFAULT_CONF_FILE = "bombarde.conf"

    Map<String, String> listOfZipDataFile



    public Bombarde() {
        listOfZipDataFile = new HashMap<String, String>()
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
        Conf conf=new Conf(DEFAULT_CONF_DIR+DEFAULT_CONF_FILE)
        conf.parse()
        println bombarde

    }



    public void loadProperties() {
        // create application properties with default


    }

    public void resetMatchingFile() {

        Properties p = new Properties()
        //TO BE COMPLETED, on file presence on tux1
        p.put( "html-pharma",["pharma.version"])
        p.put( "html-para",["para.version"])
        p.put( "SUPPRIMES",["supprimes.version"])
        p.put( "images",["monograph.images.version"])
        p.put( "recoDentaire",["recosDentaires.version","recoDentaire"])
        p.put( "Toxin",["toxin.version","toxin"])
        p.put( "FIT",["fit.version","fit"])
        p.put( "PGR",["pgr.version","pgr"])
        p.put( "BUM",["bum.version","bum"])
        p.put( "RBU",["rbu.version","rbu"])
        p.put( "InfosUtiles",["useful.info.version","useful-info"])
        p.put( "VMP????",["vidal.vmp.fr.version","vmp"])
        p.put( "FicheIRIS",["iris.version"])
        p.put( "VDF",["vdf.version"])
        p.put( "vidalrecos-merlin",["vidal.recos.version","vidal-recos"])
        p.put( "vidalofficine",["melusine.recos.version","melusine-recos"])
        p.put( "perceval-recos",["perceval.recos.version","perceval-recos"])
        p.put( "galaad-recos",["galaad.recos.version","galaad-recos"])
        p.put( "THERA????",["thera.images.version","monos-thera"])
        p.put( "MONOS????",["monos.thera.version","monograph"])
        p.put( "VDE_HMK",["hmk.version"])
        p.put( "db-vxp",["db.sqlite.vxp.version","db-vxp"])
        p.put( "db-vde",["db.sqlite.vde.version","db-vde"])
        p.put( "db-vcd",["db.sqlite.vcd.version","db-vcd"])
        p.put( "db-mysql-vxl",["db.mysql.vxl.version","db-mysql-vxl"])
        p.put( "db-mysql-vxp",["db.mysql.vxp.version","db-mysql-vxp"])
        p.put( "db-mysql-vxpper",["db.mysql.vxpper.version","db-mysql-vxpper"])
        p.put( "db-mysql-vxpj",["db.mysql.vxpj.version","db-mysql-vxpj"])
        p.put( "db-mysql-vde",["db.mysql.vde.version","db-mysql-vde"])
        p.put( "db-mysql-vdej",["db.mysql.vdej.version","db-mysql-vdej"])
        p.put( "db-mysql-vcd",["db.mysql.vcd.version","db-mysql-vcd"])
        p.put( "vcd-db-index",["vcd.index.file.version","index-db-vcd-mysql"])
        p.put( "vde-db-index",["vde.index.file.version","index-db-vde-mysql"])
        p.put( "vxp-db-index",["vxp.index.file.version","index-db-vxp-mysql"])
        def prop = new ConfigSlurper().parse(p)

        File matching = new File(DEFAULT_CONF_DIR + "matching.groovy")
        matching.delete()
        matching.withWriter {writer -> prop.writeTo(writer)}


    }



    public String toString() {
        String toReturn = "Bombarde{"
        listOfZipDataFile.each { toReturn += it.toString() + "\n"}
        toReturn += '}';
        return toReturn
    }
}
