# Configure connection to main database for pim
To configure a connection to the database, you must create a file
in this directory named:  
`main.credentials`  
In this file you should put your credentials, like this: 

```properties
# Usually no need to change this
host=localhost:5432

# The database name you want the system to use
# Do not manually create this database! Read below
database=e_commerce_pim_db

username=my_user
password=My strong password
```

> <h3 style="color:orange;margin-top:0px;padding-top:0px;padding-bottom:0px;margin-bottom:5px">IMPORTANT!</h3> 
> You should not manually create the database in the postgres server, 
> as the database is automatically created by the system, with the proper setup of
> tables and procedures.  
> 
> If there is already a database present with the same name, but not setup to the exact
> specifications required by the system, the system will **fail**. 
> 
> To reset the state of the system, simply delete the database, so the system can automatically
> recreate a new database with proper setup.   
> E.g. **if the database is messed up** go to Datagrip and delete the database. I.e. 
> ```postgresql
> DROP DATABASE <database_name>
> ```
> **This will result in data loss!!**

> *Tip*: Look in the `example.credentials` file for an example.


## *Rules*  
* Quotations marks e.g. `"` & `'` are taken literally.   
  That means `password="My password"` will set the password
  as `"My password"` with quotation marks!



# Configure connection for **test** database for pim
To configure a test database, you must create a new file named:  
`test.credentials`  
This file is similar to the main credentials. 


```properties
host=localhost:5432
database=test_e_commerce_pim_db
username=my_user
password=My strong password
```

> **IMPORTANT**: The test credentials **MUST** point to a **different database** than
> the main credentials. Otherwise, an Exception is thrown. 


> ***IMPORTANT***  
> You do not need to create the test database on your server, 
> as the database will _automatically_ be created on each test run. 
> > <p>⚠️ The test database will <b style="color:orange">AUTOMATICALLY</b> be <b style="color:orange">DELETED</b>
> > after <b style="color:orange">each test run!</b> ⚠️</p>
> >
> > **Existing data in this database will be lost on each test run!!**


### Using information from `main.credentials`
If you want to use the same _host_, _username_ and _password_ as your main credentials
for your test credentials, then you can simply omit these fields. 

```properties
database=test_e_commerce_pim_db
```

<!--
> *Tip*: If `host`, `username` and `password` are omitted, like in the example above
> the _host_, _username_ and _password_ from the `main.credentials` file are used. 
-->

<br>

After you have created the two credentials, 
the folder structure should be like this:

```
credentials
├── .gitignore
├── README.md
├── example.credentials
├── main.credentials
└── test.credentials
```