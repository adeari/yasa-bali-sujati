 @echo off
 
	for /f "tokens=2 delims==" %%a in ('wmic OS Get localdatetime /value') do set "dt=%%a"
	set "YY=%dt:~2,2%" & set "YYYY=%dt:~0,4%" & set "MM=%dt:~4,2%" & set "DD=%dt:~6,2%"
	set "HH=%dt:~8,2%" & set "Min=%dt:~10,2%" & set "Sec=%dt:~12,2%"

	set "datestamp=%YYYY%%MM%%DD%"

   set datestr=%datestamp%
    set filenamee=dbybs_%datestr%.backup
   set BACKUP_FILE=backupdata/dbybs_%datestr%.backup
   
   SET PGPASSWORD=1234
   F:\postgresql\bin\pg_dump -i -h localhost -p 5432 -U ade -F c -b -f %BACKUP_FILE% dbybs
 echo %filenamee%