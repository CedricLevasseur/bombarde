public class Matching {

    private Properties matching

    Matching() {
        load()
    }

    /**
     * load src/main/resources/matching.groovy
     */
    private void load() {

        matching = new Properties()
        File matchingFile = new File(Bombarde.DEFAULT_CONF_DIR + "matching.groovy")
        matchingFile.withInputStream {
            stream -> matching.load(stream)
        }

    }

/**
 * reset /src/main/resources/matching.groovy on Filesystem
 * note : if BackOffice create a new zipFile, it must be add to this function,
 * and matching.groovy regenerated
 * the format of the matching.groovy is
 * key : the name of the zip file, without date or number version
 * value : a string with two part separated by a semi-colon
 *  part1 : the name of version in pom.xml
 *  part2 : the name of module in pom.xml
 */
    public void resetMatchingFile() {

        Properties p = new Properties()
        //TO BE COMPLETED, on file presence on tux1
//        p.put( "html-pharma","pharma.version")
//        p.put( "html-para","para.version")
//        p.put( "SUPPRIMES","supprimes.version")
//        p.put( "images","monograph.images.version")
        p.put("recoDentaire", "recosDentaires.version;recoDentaire")
        p.put("Toxin", "toxin.version;toxin")
        p.put("FIT", "fit.version;fit")
        p.put("PGR", "pgr.version;pgr")
        p.put("BUM", "bum.version;bum")
        p.put("RBU", "rbu.version;rbu")
        p.put("InfosUtiles", "useful.info.version;useful-info")
//        p.put( "VMP????","vidal.vmp.fr.version;vmp")
//        p.put( "FicheIRIS","iris.version")
//        p.put( "VDF","vdf.version")
        p.put("vidalrecos-merlin", "vidal.recos.version;vidal-recos")
        p.put("vidalofficine", "melusine.recos.version;melusine-recos")
        p.put("perceval-recos", "perceval.recos.version;perceval-recos")
        p.put("galaad-recos", "galaad.recos.version;galaad-recos")
        p.put("THERA????", "thera.images.version;monos-thera")
        p.put("MONOS????", "monos.thera.version;monograph")
//        p.put( "VDE_HMK","hmk.version")
        p.put("db-vxp", "db.sqlite.vxp.version;db-vxp")
        p.put("db-vde", "db.sqlite.vde.version;db-vde")
        p.put("db-vcd", "db.sqlite.vcd.version;db-vcd")
        p.put("db-mysql-vxl", "db.mysql.vxl.version;db-mysql-vxl")
        p.put("db-mysql-vxp", "db.mysql.vxp.version;db-mysql-vxp")
        p.put("db-mysql-vxpper", "db.mysql.vxpper.versi;db-mysql-vxpper")
        p.put("db-mysql-vxpj", "db.mysql.vxpj.version;db-mysql-vxpj")
        p.put("db-mysql-vde", "db.mysql.vde.versi;db-mysql-vde")
        p.put("db-mysql-vdej", "db.mysql.vdej.version;db-mysql-vdej")
        p.put("db-mysql-vcd", "db.mysql.vcd.version;db-mysql-vcd")
        p.put("vcd-db-index", "vcd.index.file.version;index-db-vcd-mysql")
        p.put("vde-db-index", "vde.index.file.version;index-db-vde-mysql")
        p.put("vxp-db-index", "vxp.index.file.version;index-db-vxp-mysql")
        def prop = new ConfigSlurper().parse(p)

        File matching = new File(Bombarde.DEFAULT_CONF_DIR + "matching.groovy")
        println "reseting " + Bombarde.DEFAULT_CONF_DIR + "matching.groovy" + " with defaults values"

        matching.delete()
        matching.withWriter {writer -> prop.writeTo(writer)}


    }

    /**
     * get a version tag name from matching.groovy
     * @param zipname
     * @return a String matching version in pom.xml
     */
    public String getVersion(String zipname) {
        try {


            String[] values = ((String) matching.get(zipname).replace('"', "").trim()).split(";")
            def version = values[0]
            return version
        } catch (NullPointerException npe) {
            println "Error in matching.groovy File : $zipname doesn't have version"
            return null
        }

    }

    /**
     * get a module tag name from matching.groovy
     * @param zipname
     * @return a String matching module in pom.xml
     */
    public String getModule(String zipname) {
        try {
            String[] values = ((String) matching.get(zipname).replace('"', "").trim()).split(";")
            def module = values[1]
            return module
        } catch (NullPointerException npe) {
            println "Error in matching.groovy File : $zipname doesn't have module"
            return null
        }

    }

    public String toString() {
        return matching.toString()
    }

}
