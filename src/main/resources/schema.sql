create table CAR(
  ID int not null,
  REGISTRATION varchar(20) not null,
  TYPE varchar(100),
  constraint uq_reg unique (REGISTRATION)
);

create table PARKING_DETAILS(
  ID int not null,
  CAR_ID int not null,
  DATE_TIME_IN DATE not null,
  DATE_TIME_OUT DATE,
  IS_ACTIVE BOOLEAN,
  PRIMARY KEY ( ID )
);