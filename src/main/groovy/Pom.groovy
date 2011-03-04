public class Pom {

        File pom

        public Pom(File pom){
            this.pom=pom

        }

        public String disable(String line){
            if(line.trim().startsWith("<!--")){
                //There's no need to comment any more
                return line
            }
            return "<!--"+line+"-->"


        }

        public String enable(String line){
            if(!line.trim().startsWith("<!--")){
                //There's no need to uncomment
                return line
            }
            return line.replace("<!--","").replace("-->","")
        }






        public void replaceVersionInFile(String node, String version){

            //pattern = "<([a-zA-Z0-0.\-_]*)>(\w)<\/(\w)>"
            //pom.text = (pom.text =~ pattern).replace(replacement)
            pom.eachLine { line ->
                if(line.contains(node)){
                    line=replaceVersionInLine(line,version)
                }

            }

        }

        public String replaceVersionInLine(String line,String value){
            int start=line.indexOf('>')
            int end=line.indexOf('<',start)

            return  line.substring(0,start+1) + value + line.substring(end) //TODO complete the line if ending with comment
        }


        //<


}
