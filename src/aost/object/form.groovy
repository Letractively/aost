package aost.object

class Form  extends Container{

    def submit(Closure c){
        c(locator)
    }
}