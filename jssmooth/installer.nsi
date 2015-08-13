; example1.nsi
;
; This script is perhaps one of the simplest NSIs you can make. All of the
; optional settings are left to their default settings. The installer simply 
; prompts the user asking them where to install, and drops a copy of example1.nsi
; there. 

;--------------------------------

; The name of the installer
Name "Intaller"

; The file to write
OutFile "Intaller.exe"

; The default installation directory
InstallDir "D:\yasabalisujati"

; Request application privileges for Windows Vista
RequestExecutionLevel admin

;--------------------------------

; The stuff to install
Section "" ;No components page, name is not important

  ; Set output path to the installation directory.
  SetOutPath $INSTDIR
  
  ; Put file there
  File D:\software\javamaster\jre-7u80-windows-i586.exe
  File D:\yasabalisujati\apps.exe
  File D:\yasabalisujati\apps.jar
  File D:\yasabalisujati\appsconfig.xml
  File jalan.bat
  
  ExecWait  'jalan.bat'
  
  CreateShortCut "$DESKTOP\apps.lnk" "D:\yasabalisujati\apps.exe"
  CreateShortCut "$SMPROGRAMS\apps.lnk" "D:\yasabalisujati\apps.exe"
  
SectionEnd ; end the section
