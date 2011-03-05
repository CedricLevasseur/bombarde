public class Test {


    public static void main(String[] args) {

        init()

        File f = new File("test.tst")

        StringWriter sw = new StringWriter()
        FileReader fr = new FileReader(f)
        fr.transformLine(sw){ line-> if(line.contains("Hello")) line=line.replace("Hello","Coucou") }
        f.write(sw.toString())



    }

    public static void init() {
        File f = new File("test.tst")

        f.withWriter { out ->
            for (i in 1..5) {
                out << "Hello $i\n"
            }

        }

    }


}
