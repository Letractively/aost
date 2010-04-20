package org.telluriumsource.server

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 19, 2010
 *
 */
class Xvfb {
  /**
   * The 'Xvfb' command to execute.
   *
   * @parameter default-value="Xvfb"
   * @required
   */
  String xvfbExecutable ="Xvfb"

  /**
   * Use 'xauth' to setup permissions for the Xvfb server.  This requires 'xauth' be installed
   * and may be required when an X server is already running.
   *
   * @parameter default-value="true"
   */
  boolean xauthEnabled = true

  /**
   * The 'xauth' command to execute.
   *
   * @parameter default-value="xauth"
   */
  String xauthExecutable = "xauth"

  /**
   * The 'xauth' protocol.
   *
   * @parameter default-value="."
   */
  String xauthProtocol = "."

  /**
   * The file where X authentication data is stored for the Xvfb session.
   * Default is to generate a temporary file.
   *
   * @parameter
   */
  File authenticationFile

  /**
   * The default display to use.  SSH usualy eats up :10, so lets use :20.  That starts at port 6020.
   */
  static final int DEFAULT_DISPLAY_NUMBER = 20

  /**
   * The X11 display to use.  Default value is <tt>:20</tt>.
   *
   * @parameter
   */
  String display = ":20"

  /**
   * A list of additional options to pass to the Xvfb process.
   *
   * @parameter
   */
  String[] options

  /**
   * The location of the file to write the display properties which will be picked up
   * by the <tt>start-server</tt> goal.
   *
   * @parameter default-value="${project.build.directory}/selenium/display.properties"
   * @required
   */
  File displayPropertiesFile = new File("./display.properties")

  /**
   * Flag to control if we background the process or block Maven execution.
   *
   * @parameter default-value="true"
   */
  boolean background = true

  def ant = new AntBuilder()

  void run() {

    // Figure out what the display number is, and generate the properties file
    if (!display) {
      display = detectUsableDisplay()
    }
    else {
      if (isDisplayInUse(display)) {
//        fail("It appears that the configured display is already in use: $display")
      }
    }

    if (xauthEnabled) {
      setupXauthority()
    }

    createDisplayProperties()

    def launcher = new ProcessLauncher(name: 'Xvfb', background: background)

    launcher.process = {
      ant.exec(executable: xvfbExecutable, failonerror: true) {
        if (xauthEnabled) {
          env(key: 'XAUTHORITY', file: authenticationFile)
        }

        // Set the display
        arg(value: display)

        // Add extra options
        if (options) {
          options.each {
            arg(value: it)
          }
        }
      }

      if (xauthEnabled) {
        ant.delete(file: authenticationFile)
      }

    }

    launcher.verifier = {
      return isDisplayInUse(display)
    }

    launcher.launch()

  }

  private void createDisplayProperties() {
    // Write out the display properties so that the start-server goal can pick it up
    ant.mkdir(dir: displayPropertiesFile.parentFile)
    def props = new Properties()
    props.setProperty('DISPLAY', display)

    // Write the xauth file so clients pick up the right perms
    if (xauthEnabled) {
      assert authenticationFile
      props.setProperty('XAUTHORITY', authenticationFile.canonicalPath)
    }

    props.store(displayPropertiesFile.newOutputStream(), 'Xvfb Display Properties')
  }

  /**
   * Generate a 128-bit random hexadecimal number for use with the X authority system.
   */
  private String createCookie() {
    def cookie

    byte[] bytes = new byte[16]
    new Random().nextBytes(bytes)
    cookie = new BigInteger(bytes)

    String cookieHex = cookie.abs().toString(16)
    int padding = 32 - cookieHex.length()
    for (int i = 0; i < padding; i++) {
      cookieHex = "0" + cookieHex
    }
    return cookieHex
  }

  /**
   * Setup the X authentication file (Xauthority)
   */
  private void setupXauthority() {
    if (!authenticationFile) {
      authenticationFile = File.createTempFile('Xvfb', '.Xauthority')
      authenticationFile.deleteOnExit()
    }

    ant.delete(file: authenticationFile)

    def cookie = createCookie()

    // Use xauth to configure authentication for the display using a generated cookie
    ant.exec(executable: xauthExecutable, failonerror: true) {
      env(key: 'XAUTHORITY', file: authenticationFile)

      arg(value: 'add')
      arg(value: display)
      arg(value: xauthProtocol)
      arg(value: cookie)

    }

    if (!authenticationFile.exists()) {
//            fail("It appears that 'xauth' failed to create the Xauthority file: $authenticationFile")
    }
  }

  /**
   * Detect which display is usable.
   */
  private String detectUsableDisplay() {

    boolean found = false
    int n = DEFAULT_DISPLAY_NUMBER

    while (!found && (n <= DEFAULT_DISPLAY_NUMBER + 10)) {
      def d = ":$n"

      if (!isDisplayInUse(d)) {
        return d
      }
      else {
        n++
      }
    }

//        fail("Count not find a usable display")
  }

  /**
   * Decode the port number for the display.
   */
  private int decodeDisplayPort(display) {
    assert display

    def m = display =~ /[^:]*:([0-9]*)(\.([0-9]*))?/

    def i = Integer.parseInt(m[0][1])

    //
    // Normally, the first X11 display is on port 6000, the next on port 6001,
    // which get abbreviated as :0, :1 and so on.
    //

    return 6000 + i
  }

  /**
   * Check if the given display is in use or not.
   */
  private boolean isDisplayInUse(display) {
    int port = decodeDisplayPort(display)

//        log.debug("Checking if display is in use: $display on port: $port")

    try {
      def socket = new Socket('localhost', port)
      return true
    }
    catch (ConnectException e) {
      return false
    }
  }
}
