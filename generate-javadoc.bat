@echo off
echo Generating JavaDoc for Rental Cosplay CCDP...
echo.

REM Set JAVA_HOME if needed (adjust path to your JDK installation)
REM set JAVA_HOME=C:\Program Files\Java\jdk-17

REM Create docs directory if it doesn't exist
if not exist "docs" mkdir docs

REM Generate JavaDoc
javadoc -d docs ^
    -sourcepath src ^
    -subpackages rental.cosplay ^
    -author ^
    -version ^
    -private ^
    -windowtitle "Rental Cosplay CCDP API Documentation" ^
    -doctitle "<h1>Rental Cosplay CCDP API Documentation</h1>" ^
    -header "<b>Rental Cosplay CCDP</b>" ^
    -bottom "<i>Copyright &copy; 2025 Rental Cosplay CCDP. All rights reserved.</i>" ^
    -charset UTF-8 ^
    -encoding UTF-8 ^
    -use ^
    -link https://docs.oracle.com/en/java/javase/17/docs/api/

echo.
if %ERRORLEVEL% EQU 0 (
    echo JavaDoc generation completed successfully!
    echo Documentation is available in the 'docs' folder.
    echo Open docs\index.html in your web browser to view it.
) else (
    echo JavaDoc generation failed!
    echo Please make sure Java JDK is installed and javadoc is in your PATH.
)

pause

