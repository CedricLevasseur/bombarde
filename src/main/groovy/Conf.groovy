import java.text.ParseException

class Conf {

    File conf
    String pomPath=""

    public Conf(String filename) throws FileNotFoundException{

        conf=new File(filename)
        if(!conf.exists()){
            throw new FileNotFoundException(filename + " not found !!!")
        }

    }


    public void parse() throws ParseException{
        def config = new ConfigSlurper().parse(conf.toURL())
        pomPath=config.pom.path
        if(pomPath.isEmpty())
            throw new ParseException("ERROR : pom.path is empty in "+conf.toURL()+" file")
        File pom = new File(pomPath)
        if(!pom.exists())
            throw new ParseException("ERROR : pom.path is not correct in "+conf.toURL()+" file")
        println "updating $pomPath "
    }

}
