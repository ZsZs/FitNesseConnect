-- users in system
insert into ACCOUNT(account_name , password) values('jlong', 'spring');
insert into ACCOUNT(account_name , password) values('pwebb', 'boot');
insert into ACCOUNT(account_name , password) values('rod', 'N/A');
insert into ACCOUNT(account_name , password) values('zsolt', 'zsuffa');



-- oauth client details
insert into CLIENT_DETAILS(   client_id, client_secret,  resource_ids,   scopes,   grant_types,                                  authorities)
                    values(   'acme' ,  'acmesecret',    null,           'openid,read',   'authorization_code,refresh_token,password',  null );