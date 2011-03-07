public class Pom {

    public static String START_MARQUEE = "<!-- #### START Generated with BOMBARDE #### -->"
    public static String END_MARQUEE = "<!-- #### END Generated with BOMBARDE #### -->"

    File pom

    public Pom(File pom) {
        this.pom = pom

    }

    /**
     * Disable a module in pom.xml
     * @param line
     * @return line with xml comment tag
     */
    public String disable(String line) {
        if(line.trim().equals(""))
            return line
        if (line.trim().startsWith("<!--")) {
            //There's no need to comment any more
            return line
        }
        return "<!--" + line + "-->"
    }

    /**
     * Enable a module in pom.xml, by removing xml comment tag
     * @param line
     * @return line without xml comment tag
     */
    public String enable(String line) {
        if(line.trim().equals(""))
            return line
        if (!line.trim().startsWith("<!--")) {
            //There's no need to uncomment
            return line
        }
        return line.replace("<!--", "").replace("-->", "")
    }

    /**
     * enable the modules in pom.xml
     * @param listOfModules to be enabled
     */
    public void enableModules(List<String> listOfModules) {
        StringWriter sw = new StringWriter()
        FileReader fr = new FileReader(pom)

        Boolean canTransform = false

        pom.eachLine  { line ->
            if (line.contains("<modules>"))
                canTransform = true
            if (line.contains("</modules>"))
                canTransform = false

            if (canTransform) {

                String module=getModuleNameFromXml(line)

                if(module in listOfModules){
                    line=enable(line)

                }else{
                    line=disable(line)
                }

            }
            sw.write(line+"\n")

        }
        pom.write(sw.toString())

    }

    /**
     * return the name of the module
     * @param xml, a line from pom.xml which should contain the name of a module
     * @return the name
     */
    public String getModuleNameFromXml(String xml){
        return xml.find(~/([^>\/<]*)<\/module>/){matches, firstmatch -> return firstmatch}

    }

    /**
     * Ca fait quoi ça déjà ? lol
     * @deprecated
     * @param node
     * @param version
     */
    public void replaceVersionInFile(String versionName, String versionNumber) {

        pom.eachLine { line ->
            if (line.contains(versionName)) {
                line = replaceVersionInLine(line, versionNumber)
            }
        }
    }

    /**
     * Place a version number in a xml line between '>' and '<'
     * @param line
     * @param value to be placed
     * @return the line
     */
    public String replaceVersionInLine(String line, String value) {
        int start = line.indexOf('>')
        int end = line.indexOf('<', start)

        return line.substring(0, start + 1) + value + line.substring(end) //TODO complete the line if ending with comment
    }




}
