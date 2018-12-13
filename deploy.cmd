[config]

@echo off

echo ---Deploying site 

copy D:\home\site\repository\funBusBooking\target\*.war %DEPLOYMENT_TARGET%\webapps\*.war


