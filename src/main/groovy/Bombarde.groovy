public class Bombarde{

    public static String DEFAULT_CONF_DIR = "src/main/ressources/"
    public static String DEFAULT_CONF_FILE = "mail.in"


    public static void load(){
        File conf=new File(DEFAULT_CONF_DIR+DEFAULT_CONF_FILE)
        if(!conf.exists()){
            System.exit(1)
        }

        println conf.text

    }

    public static void main(String[] args){
        println "hello world"
        Bombarde bombarde= new Bombarde()
        bombarde.load()

    }




}
