imagename und port für die einzelnen Services:

* api-gateway  9000
* masterdata   9001
* booking      9002
* opscanner    9003
* ringme       9004

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

Starten der Services außerhalb eines Docker Containers (getestet unter Windows 10 und Ubuntu 15.10):

Gehe direkt in die einzelne Project Folder (zb api-gateway)

**mvn clean install**

**mvn spring-boot:run**


Der Ringme-Service kann dann zB so getestet werden:
http://localhost:9004/ringme/hello

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

Starten der Services innerhalb eines Docker Container (getestet unter Ubuntu 15.10):

**mvn clean install docker:build**

**docker run -ti -p <dockerport>:<hostport> <imagename>**


Ein Docker run für zB den RingMe Service wäre dann:

docker run -ti -p 9004:9004 ringme

Dann wieder einfach erreichbar testbar über http://localhost:9004/ringme/hello