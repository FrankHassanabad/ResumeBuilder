@echo off
setLocal EnableDelayedExpansion
set CLASSPATH="
for /R ./lib %%a in (*.jar) do (
  set CLASSPATH=!CLASSPATH!;%%a
)
set CLASSPATH=!CLASSPATH!"
if "%JAVA_HOME%" == "" (
    echo JAVA_HOME enviornment variable is not set!  Enter path to JAVA_HOME:
    set /p JAVA_HOME=
)
"%JAVA_HOME%/bin/java" -cp %CLASSPATH% frankhassanabad.com.github.Jasperize %*

