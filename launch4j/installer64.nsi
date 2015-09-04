; example1.nsi
;
; This script is perhaps one of the simplest NSIs you can make. All of the
; optional settings are left to their default settings. The installer simply 
; prompts the user asking them where to install, and drops a copy of example1.nsi
; there. 

;--------------------------------

; The name of the installer
Name "Intaller64"

; The file to write
OutFile "Intaller64.exe"

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
  File D:\software\javamaster\jre-7u80-windows-x64.exe
  File D:\yasabalisujati\apps.exe
  File D:\yasabalisujati\apps.jar
  File D:\yasabalisujati\exec64.bat
  File jalan64.bat
  
  ExecWait  'jalan64.bat'
  
  CreateShortCut "$DESKTOP\DataYasaBaliSujati.lnk" "D:\yasabalisujati\apps.exe"
  CreateShortCut "$SMPROGRAMS\DataYasaBaliSujati.lnk" "D:\yasabalisujati\apps.exe"
  CreateShortCut "$DESKTOP\DataYasaBaliSujatia.lnk" "D:\yasabalisujati\exec64.bat"
  CreateShortCut "$SMPROGRAMS\DataYasaBaliSujatia.lnk" "D:\yasabalisujati\exec64.bat"
  
SectionEnd ; end the section
