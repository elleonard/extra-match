# ExtraPattern

# --- !Ups

CREATE TABLE "extra_pattern" (
  "id" INTEGER NOT NULL AUTO_INCREMENT,
  "enemy_name" VARCHAR(255) NOT NULL,
  "pattern1" VARCHAR(255),
  "pattern2" VARCHAR(255),
  "pattern3" VARCHAR(255),
  "pattern4" VARCHAR(255),
  "pattern5" VARCHAR(255),
  "pattern6" VARCHAR(255),
  PRIMARY KEY ("id")
);

CREATE TABLE "extra_stage" (
  "id" INTEGER NOT NULL AUTO_INCREMENT,
  "stage_name" VARCHAR(255) NOT NULL,
  PRIMARY KEY ("id"),
  UNIQUE("stage_name")
);

CREATE TABLE "extra_enemy" (
  "id" INTEGER NOT NULL AUTO_INCREMENT,
  "enemy_name" VARCHAR(255) NOT NULL,
  PRIMARY KEY ("id"),
  UNIQUE("enemy_name")
);

CREATE TABLE "enemy_habitat" (
  "id" INTEGER NOT NULL AUTO_INCREMENT,
  "stage_name" VARCHAR(255) NOT NULL,
  "enemy_name" VARCHAR(255) NOT NULL,
  PRIMARY KEY ("id"),
  UNIQUE("stage_name","enemy_name")
);

# --- !Downs

DROP TABLE "extra_pattern";
DROP TABLE "extra_stage";
DROP TABLE "enemy_habitat";
