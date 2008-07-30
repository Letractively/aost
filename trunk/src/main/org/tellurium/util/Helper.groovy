package org.tellurium.util

import java.lang.reflect.UndeclaredThrowableException

class Helper{

    def static pause(int millisecs) {
        try {
            Thread.sleep(millisecs);
        } catch (InterruptedException e) {
        }
    }

    public static String logException(Exception exception){
         // convert the exception to String
        StackTraceElement[] stackTrace = exception.getStackTrace();
        StringBuilder sb = new StringBuilder("\n");
        String exceptionMessage = "";
    	if(exception instanceof UndeclaredThrowableException){
    		exceptionMessage = exception.getCause().getMessage();
    	}else{
    		exceptionMessage = exception.getMessage();
    	}
        sb.append(exceptionMessage + "\n");

        for (StackTraceElement st: stackTrace) {
            sb.append(st.toString() + "\n\t\t");
        }

        return sb.toString();
    }

}