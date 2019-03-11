set path="C:\Program Files\Java\jdk1.8.0_181\bin"
cd src
javac -d ..\meinOutput MainFrame.java
cd ..\meinOutput
jar cfm ..\MemesAgainstHumanity.jar ..\src\Manifest *.class
cd ..
REM jar cfM MemesAgainstHumanity.zip MemesAgainstHumanity.jar