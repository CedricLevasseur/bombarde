public class Pom {

        File pom

        public Pom(File pom){
            this.pom=pom

        }

        public void disable(String node){}

        public void enable(String node){}




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

            return  line.substring(0,start+1) + value + line.substring(end)
        }


}
