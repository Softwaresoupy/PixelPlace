# PixelPlace
Project for CNIT 325.

## General notes:
**Server**
<br> This class opens the socket for clients to connect to. It will (therefore) handle all of the input and outputs that must come and be used for the Server. By starting this server the databases are also open so that when the clients connect, all the database are ready to be used. 
<br> <br> 
**Database (Abstract)**
<br> A blueprint for the other databases. Since both objects need to handle their data / information in different ways it is important that each have their own class however since there is many functionalities that are similar this class is best to have. <br> <br> 
**ArtDatabase & UserDatabase**
<br> Take text files and converts into usable, callable objects. Takes objects and converts into an editable file.

**User**
-> username
-> password
-> art they createn

**Art (image + description time, location?, sjdhfahj)**
-> user
-> title
-> the actual image of the art
-> Location (look into it)
-> description
-> filter of somesort (combobox thing with tags)

**GUI (Abstract)**
**Art creation GUI**
--> Use canvas class which would save as art
--> look into old code for painting thing
--> client (?)
**Art description / upload GUI**
**Art gallery show off yea :D GUI**
**Login + Sign up GUI**

Format idea:

<img width="574" alt="image" src="https://github.com/user-attachments/assets/a507343b-5049-4620-90ff-5930121407cb" />



