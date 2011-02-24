public class Bombarde{

    public static String DEFAULT_CONF_DIR = "src/main/ressources/"
    public static String DEFAULT_CONF_FILE = "mail.in"

    Map<String,String> listOfZipDataFile



    public Bombarde(){
        listOfZipDataFile=new HashMap<String,String>()
    }

    public void load(){
        File conf=new File(DEFAULT_CONF_DIR+DEFAULT_CONF_FILE)
        if(!conf.exists()){
            System.exit(1)
        }

        conf.eachLine {
            line->
            String result = line.find(~/(\S{2})*\.zip/) //matches("\w\.zip")
            if(result!=null){
                String left = result.find(~/[^0-9]*/)
                if(left.startsWith("http:")){
                    println left
                    left=left.find(~/([^\/]*)$/)
                    println left
                }
                String right = result.find(~/[0-9 _.]*.zip/)
                listOfZipDataFile.put(left,right.replaceFirst("\\.zip\$",""))
            }

        }



    }



    public static void main(String[] args){
        println "Bombarde, a BackOffice Mail Archives Decrypter"
        Bombarde bombarde= new Bombarde()
        bombarde.load()
        println bombarde
    }



    public String toString ( ) {
    return "Bombarde{" +
    "listOfZipDataFile=" + listOfZipDataFile +
    '}' ;
    }}
