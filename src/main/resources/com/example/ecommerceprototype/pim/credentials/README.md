# Configure connection to main database for pim
To configure a connection to the database, you must create a file
in this directory named:  
`main.credentials`  
In this file you should put your credentials, like this: 

```properties
host=localhost:5432

# The database name you want the system to use
database=e_commerce_pim_db

username=my_user
password=My strong password
```

> *Tip*: Look in the `example.credentials` file for an example.

 
> *IMPORTANT*: You should not manually create the database in the postgres server, 
> as the database is automatically created by the system, with the proper setup of
> tables and procedures.

## *Rules*  
* Quotations marks e.g. `"` & `'` are taken literally.   
  That means `password="My password"` will set the password
  as `"My password"` with quotation marks!



# Configure connection to test database for pim
To configure a test database, you must create a new file named:  
`test.credentials`  
This file is similar the main credentials. 


```properties
host=localhost:5432
database=test_e_commerce_pim_db
username=my_user
password=My strong password
```

> **IMPORTANT**: The test credentials MUST point to a different database than
> the main credentials. Otherwise, an Exception is thrown. 


> ***NOTE*** You do not need to create the test database on your server, 
> as the database will automatically be created on each test run. 
> > ⚠️The test database will **AUTOMATICALLY** be **DELETED**
> > after **each test run**! ⚠️


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