public class Pom {

    public static String START_MARQUEE = "<!-- #### START Generated with BOMBARDE #### -->"
    public static String END_MARQUEE = "<!-- #### END Generated with BOMBARDE #### -->"

    File pom

    public Pom(File pom) {
        this.pom = pom

    }

    public String disable(String line) {
        if(line.trim().equals(""))
            return line
        if (line.trim().startsWith("<!--")) {
            //There's no need to comment any more
            return line
        }
        return "<!--" + line + "-->"


    }

    public String enable(String line) {
        if(line.trim().equals(""))
            return line
        if (!line.trim().startsWith("<!--")) {
            //There's no need to uncomment
            return line
        }
        return line.replace("<!--", "").replace("-->", "")
    }



    public void enableModules(List<String> listOfModules) {
        StringWriter sw = new StringWriter()
        FileReader fr = new FileReader(pom)

        println listOfModules

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


    public String getModuleNameFromXml(String xml){
        return xml.find(~/([^>\/<]*)<\/module>/){matches, firstmatch -> return firstmatch}

    }




    public void replaceVersionInFile(String node, String version) {

        //pattern = "<([a-zA-Z0-0.\-_]*)>(\w)<\/(\w)>"
        //pom.text = (pom.text =~ pattern).replace(replacement)
        pom.eachLine { line ->
            if (line.contains(node)) {
                line = replaceVersionInLine(line, version)
            }

        }

    }

    public String replaceVersionInLine(String line, String value) {
        int start = line.indexOf('>')
        int end = line.indexOf('<', start)

        return line.substring(0, start + 1) + value + line.substring(end) //TODO complete the line if ending with comment
    }

    //<


}
