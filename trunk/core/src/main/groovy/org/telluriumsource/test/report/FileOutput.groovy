package org.telluriumsource.test.report

import org.telluriumsource.framework.config.Configurable
import org.telluriumsource.framework.config.TelluriumConfigurator
import org.telluriumsource.annotation.Inject

/**
 * Ouput the results as a file
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 4, 2008
 *
 */
class FileOutput implements ResultOutput, Configurable{
    @Inject(name="tellurium.test.result.filename", lazy=true)
    private String filename

    public String output(String results) {
        File wf= new File(filename)
        wf.append(results)

        return results
    }
}