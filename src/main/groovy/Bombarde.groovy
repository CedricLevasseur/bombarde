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

    }



    public void loadProperties() {
        // create application properties with default


    }

    public void resetMatchingFile() {

        Properties p = new Properties()

        p.put("pharma.version", "html-pharma")
        p.put("para.version", "html-para")
        p.put("supprimes.version", "SUPPRIMES")
        p.put("monograph.images.version", "images")
        p.put("recosDentaires.version", "recoDentaire")
        p.put("toxin.version", "Toxin")
        p.put("fit.version", "FIT")
        p.put("pgr.version", "PGR")
        p.put("bum.version", "BUM")
        p.put("rbu.version", "RBU")
        p.put("useful.info.version", "InfosUtiles")
        p.put("vidal.vmp.fr.version", "")
        p.put("iris.version", "FicheIRIS")
        p.put("vdf.version", "VDF")
        p.put("vidal.recos.version", "vidalrecos-merlin")
        p.put("melusine.recos.version", "vidalofficine")
        p.put("perceval.recos.version", "perceval-recos")
        p.put("galaad.recos.version", "galaad-recos")
        p.put("thera.images.version", "CEDRIC")
        p.put("monos.thera.version", "")
        p.put("hmk.version", "VDE_HMK")
        p.put("db.sqlite.vxp.version", "db-vxp")
        p.put("db.sqlite.vde.version", "db-vde")
        p.put("db.sqlite.vcd.version", "db-vcd")
        p.put("db.mysql.vxl.version", "db-mysql-vxl")
        p.put("db.mysql.vxp.version", "db-mysql-vxp")
        p.put("db.mysql.vxpper.version", "db-mysql-vxpper")
        p.put("db.mysql.vxpj.version", "db-mysql-vxpj")
        p.put("db.mysql.vde.version", "db-mysql-vde")
        p.put("db.mysql.vdej.version", "db-mysql-vdej")
        p.put("db.mysql.vcd.version", "db-mysql-vcd")
        p.put("vcd.index.file.version", "vcd-db-index")
        p.put("vde.index.file.version", "vde-db-index")
        p.put("vxp.index.file.version", "vxp-db-index")

        def prop = new ConfigSlurper().parse(p)

        File matching=new File(DEFAULT_CONF_DIR + "matching.groovy")
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
