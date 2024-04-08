CREATE TABLE Users(user_id int PRIMARY KEY,
                   name VARCHAR(20),
                   password VARCHAR(20));

CREATE SEQUENCE userSeq
START WITH 1
INCREMENT BY 1
NOCACHE
NOCYCLE;

CREATE TABLE Organisation( org_id int PRIMARY KEY ,
                           org_name varchar(20),
                           contact varchar(10),
                           address varchar(20),
                           password varchar(10),
                           description varchar(30)
);

CREATE SEQUENCE orgSeq
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;


CREATE TABLE LatestMeal(
    meal_id int PRIMARY KEY,
    type varchar(20),
    organisationOrg_id int,
    FOREIGN KEY (organisationOrg_id) REFERENCES Organisation(org_id)
);

CREATE SEQUENCE mealSeq
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE TABLE FoodItem(

    item_id int PRIMARY KEY,
    name varchar(15),
    type varchar(10),
    latestMealMeal_id int,
    description varchar(30),

    FOREIGN KEY (latestMealMeal_id) REFERENCES LATESTMEAL(meal_id)
);

CREATE SEQUENCE itemSeq
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE TABLE Orders(
    order_id int primary key,
    address varchar(20),
    delivered char(1),
    latestMealMeal_id int,
    usersUser_id int,

    FOREIGN KEY (latestMealMeal_id) REFERENCES LatestMeal(meal_id),
    FOREIGN KEY (usersUser_id) REFERENCES Users(user_id)
);

CREATE SEQUENCE orderSeq
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE TABLE Review(
    rev_id int PRIMARY KEY,
    feedback varchar(20),
    rating int,
    latestMealMeal_id int,
    usersUser_id int,

    FOREIGN KEY (latestMealMeal_id) REFERENCES LatestMeal(meal_id),
    FOREIGN KEY (usersUser_id) REFERENCES Users(user_id)
);

CREATE SEQUENCE reviewSeq
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE TABLE Wish(
    wish_id int PRIMARY KEY,
    itemName varchar(20),
    description varchar(30),
    type varchar(10),

    usersUser_id int,
    FOREIGN KEY (usersUser_id) REFERENCES Users(user_id)
);

CREATE SEQUENCE wishSeq
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;
