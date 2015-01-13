First 
install ant, ibm websphere 6.1, oracle database 10g.

runAnt.bat - launch default action "build"


(For right time on page set parameters in websphere
Servers > server  > Process Definition > Java Virtual Machine > Generic JVM arguments: java -Duser.timezone=GMT+4)

open dir ./ant

use task:
•	clean – clean dirs
•	createdbschema – start script for creating user (schema)
•	deletedbschema – start script for delete user (schema)
•	create-db – create all tables and insert init data
•	drop-db-data – drop inserted data
•	compile – compile all source
•	build – building source and create WAR in dist folder
•	javadoc –  JavaDoc 
•	dist-src – create zip archive with source code
•	dist – war

example:> ant deploy

For more edit ./ant/build.xml properties:

<property name="db.hostname" value="localhost" />
<property name="db.port" value="1521" />
<property name="db.sid" value="xe" />

/* for use create-db target set this parameters */
/* ADMIN DB*/
<property name="db.admin" value="SYSTEM" />                     
<property name="db.passwordForAdmin" value="admin-password" />

/* This is db schema configuration*/
<property name="db.user" value="omptest" />
<property name="db.password" value="test" />

<property name="context.root" value="OnlineMarketplace" />

After sql installation configure IBM Websphere ->
Resources -> JDBC - > Data sources
with schema configuration from ANT script