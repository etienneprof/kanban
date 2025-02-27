#Kanban project (mini Trello-like)
##How to run the project
In order to run this project, you need to setup an MSSQL database named "KANBAN" running on localhost.
Remember to check the "persistence.xml" file to use your own username and password.
Finally, you may run the "index.html" file located in webapp/kanban directly on your Tomcat server by right-clicking > Run as... > Run on server.

##Current state
Currently, it is possible to create columns and issues with the corresponding inputs. You may click an issue to change its title or fill a description. Columns and issues can be deleted.

The Drag and Drop feature allows one to change the state of an issue, but it's not possible to rearrange the order of issues/columns as of now.

##What you can do if want to keep working on this project
You should check how the Drag and Drop function works in order to use it to rearrange the order of issues and columns. To achieve that, you need to change to "order" attribute of the element you want to move. You may need to create new endpoints in your controllers to update this specific attribute.
