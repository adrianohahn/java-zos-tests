# Dataset Info

## Usage

`zotest.dsinfo.Main <DDNAME1> [DDNAME2] [...]`

### JCL Example

```jcl
...
//*                                                                     
//*--------------------------------------------------------------------*
//* Execute Java Program  hahn.zos.dsinfo.App                           
//*--------------------------------------------------------------------*
//DSINFO   EXEC PROC=JVMPRC86,REGSIZE=1024M,                            
//         JAVACLS='zostests.dsinfo.App'                                
//*                                                                                                
//VBSTE44  DD DSN=&TMP3,DISP=(,DELETE),                                 
//         BLKSIZE=27998,RECFM=VB,LRECL=44,                             
//         SPACE=(TRK,1)                                                
//VBASTE44  DD DSN=&TMP4,DISP=(,DELETE),                                
//         BLKSIZE=27998,RECFM=VBA,LRECL=145,                           
//         SPACE=(TRK,1)                                                
//MAINARGS DD *,SYMBOLS=JCLONLY                                         
VBSTE44 VBASTE44                                       
/*                                                                      
//STDENV DD *,SYMBOLS=JCLONLY                                           
. /etc/profile                                                          
APP_HOME=<replace with application jar path>                                                                                   
                                                                        
export JAVA_HOME=/usr/lpp/java/J8.0_64                                                               
                                                                        
export PATH=$APP_HOME:/bin:"${JAVA_HOME}"/bin                           
                                                                        
LIBPATH=/lib:/usr/lib:"${JAVA_HOME}"/bin                                
LIBPATH="$LIBPATH":"${JAVA_HOME}"/lib/s390x                             
LIBPATH="$LIBPATH":"${JAVA_HOME}"/lib/s390x/j9vm                        
LIBPATH="$LIBPATH":"${JAVA_HOME}"/bin/classic                           
export LIBPATH="$LIBPATH":                                              
                                                                        
CLASSPATH=$APP_HOME:"${JAVA_HOME}"/lib:"${JAVA_HOME}"/lib/ext                
                                                                         
# Add Application required jars to end of CLASSPATH                      
for i in "${APP_HOME}"/*.jar; do                                         
    CLASSPATH="$CLASSPATH":"$i"                                          
    done                                                                 
                                                                         
export CLASSPATH="$CLASSPATH":                                           
export APP_HOME="$APP_HOME":                                             
                                                                         
# Set JZOS specific options                                              
# Use this variable to specify encoding for DD STDOUT and STDERR         
export JZOS_OUTPUT_ENCODING=Cp1047                                       
# Use this variable to prevent JZOS from handling MVS operator commands  
#export JZOS_ENABLE_MVS_COMMANDS=false                                   
# Use this variable to supply additional arguments to main               
#export JZOS_MAIN_ARGS=""                                                                                        
                                                                         
# Configure JVM options                                                  
IJO="-Xms512m -Xmx1024m"                                                 
# Uncomment the following to aid in debugging "Class Not Found" problems 
# IJO="$IJO -verbose:class"                                              
# Uncomment the following if you want to run with Ascii file encoding..  
#IJO="$IJO -Dfile.encoding=ISO8859-1"                                    
export IBM_JAVA_OPTIONS="$IJO "                                                        
```