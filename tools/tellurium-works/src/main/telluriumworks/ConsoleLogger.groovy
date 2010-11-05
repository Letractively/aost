package telluriumworks

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 24, 2010
 * 
 */
@Singleton
class ConsoleLogger {
  int maxLines = 100
  def view
  
  String[] buffer = new String[maxLines]
  int header = 0
  int tail = 0

  public void log(String info){
    int x = tail % maxLines
    buffer[x] =  new Date().toString() + ": " +info
    tail++

    if(tail - header >= maxLines){
      header++
    }

    view.consoleTxt.text = this.toString()
  }

  String toString(){
    StringBuffer sb = new StringBuffer();
    for(int i=header; i<tail; i++){
      sb.append(buffer[i]).append("\n")
    }

    return sb.toString()
  }

  void clear(){
    header = 0
    tail = 0
  }
}
