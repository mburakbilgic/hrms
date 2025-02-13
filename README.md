# HRMS
Human Resource Management System

<h1>ERD of HRMS Database</h1>
<a href="https://imgshare.io/image/pI9vGx"><img src="https://imgshare.io/images/2021/05/11/hrms_pgerd.png" alt="hrms_pgerd.png" border="0" /></a>

<h1>Queries of HRMS Database</h1>
<h2>Schema Generation</h2>

        -- SCHEMA: hrms_schema

        -- DROP SCHEMA hrms_schema ;

        CREATE SCHEMA hrms_schema
            AUTHORIZATION postgres;

<h2>Table Generation</h2>

        -- TABLE GENERATION;
        --E.G usement
        --CREATE SEQUENCE "schema_name"."table_name_id_seq" (
        --    "name_of_column" data_type rules_of_null_or_not_null);

        CREATE TABLE "hrms_schema"."users"(
          "id" int GENERATED ALWAYS AS IDENTITY,
          "email" varchar(50) NOT NULL,
          "password" varchar(25) NOT NULL
        );

        CREATE TABLE "hrms_schema"."hrms_personels"(
          "id" int NOT NULL,
          "user_id" int NOT NULL,
          "first_name" varchar(25) NOT NULL,
          "last_name" varchar(25) NOT NULL,
          "nationality_id" varchar(11) NOT NULL,
          "birth_of_date" date NOT NULL
        );

        CREATE TABLE "hrms_schema"."candidates"( 
          "id" int NOT NULL,
          "user_id" int NOT NULL,
          "first_name" varchar(25) NOT NULL,
          "last_name" varchar(25) NOT NULL,
          "nationality_id" varchar(11) NOT NULL,
          "birth_of_date" date NOT NULL
        );

        CREATE TABLE "hrms_schema"."employers"( 
          "id" int NOT NULL,
          "user_id" int NOT NULL,
          "company_name" varchar(250) NOT NULL,
          "web_address" varchar(50) NOT NULL,
          "phone_number" varchar(25) NOT NULL,
          "activate_status" boolean NOT NULL
        );

        CREATE TABLE "hrms_schema"."job_titles"( 
          "id" int GENERATED ALWAYS AS IDENTITY,
          "title" varchar(50) NOT NULL
        );

        CREATE TABLE "hrms_schema"."activation_codes"( 
          "id" int GENERATED ALWAYS AS IDENTITY,
          "activation_code" varchar(50) NOT NULL,
          "is_confirmed" boolean NOT NULL,
          "confirmed_date" date NOT NULL
        );

        CREATE TABLE "hrms_schema"."activation_codes_candidates"( 
          "id" int NOT NULL,
          "candidate_id" int NOT NULL
        );

        CREATE TABLE "hrms_schema"."activation_codes_employers"( 
          "id" int NOT NULL,
          "employer_id" int NOT NULL
        );

        CREATE TABLE "hrms_schema"."activation_status_dashboard_employers"( 
          "id" int NOT NULL,
          "employer_id" int NOT NULL,
          "confirmed_system_personel_id" int,
          "is_confirmed" boolean NOT NULL,
          "confirmed_date" date
        );
        
<h2>Auto-Increment Keys Adjustment</h2>

        -- KEYS (AUTO INCREMENT)
        --E.G usement
        --CREATE SEQUENCE "schema_name"."table_name_id_seq" 
        --    INCREMENT BY 1 START WITH 1 
        --    OWNED BY "schema_name"."table"."auto_incremented_keys";

        CREATE SEQUENCE "hrms_schema"."hrms_personels_id_seq" 
            INCREMENT BY 1 START WITH 1 
            OWNED BY "hrms_schema"."hrms_personels"."id";

        CREATE SEQUENCE "hrms_schema"."candidates_id_seq" 
            INCREMENT BY 1 START WITH 1 
            OWNED BY "hrms_schema"."candidates"."id";

        CREATE SEQUENCE "hrms_schema"."employers_id_seq" 
            INCREMENT BY 1 START WITH 1 
            OWNED BY "hrms_schema"."employers"."id";

        CREATE SEQUENCE "hrms_schema"."activation_status_dashboard_employers_id_seq" 
            INCREMENT BY 1 START WITH 1 
            OWNED BY "hrms_schema"."activation_status_dashboard_employers"."id";

<h2>Primary Keys Adjustment</h2>

        --PRIMARY KEYS
        --E.G usement
        --ALTER TABLE "schema_name"."table_name" 
        --    ADD CONSTRAINT "pk_key_name"  // pk used because with this we understand this key is primary key
        --    PRIMARY KEY ("key");

        ALTER TABLE "hrms_schema"."users" 
            ADD CONSTRAINT "pk_users_id" 
            PRIMARY KEY ("id");

        ALTER TABLE "hrms_schema"."employers" 
            ADD CONSTRAINT "pk_employers_id" 
            PRIMARY KEY ("id");

        ALTER TABLE "hrms_schema"."candidates" 
            ADD CONSTRAINT "pk_candidate_id" 
            PRIMARY KEY ("id");

        ALTER TABLE "hrms_schema"."hrms_personels" 
            ADD CONSTRAINT "pk_hrmspersonels_id" 
            PRIMARY KEY ("id");

        ALTER TABLE "hrms_schema"."job_titles" 
            ADD CONSTRAINT "pk_jobtitles_id" 
            PRIMARY KEY ("id");

        ALTER TABLE "hrms_schema"."activation_codes_candidates" 
            ADD CONSTRAINT "pk_activationcodecandidates_id" 
            PRIMARY KEY ("id");

        ALTER TABLE "hrms_schema"."activation_codes_employers" 
            ADD CONSTRAINT "pk_activationcodeemployers_id" 
            PRIMARY KEY ("id");

        ALTER TABLE "hrms_schema"."activation_codes" 
            ADD CONSTRAINT "pk_activationcodes_id" 
            PRIMARY KEY ("id");

        ALTER TABLE "hrms_schema"."activation_status_dashboard_employers" 
            ADD CONSTRAINT "pk_activationstatusdashboardemployers_id" 
            PRIMARY KEY ("id");

<h2>Unique Constraints Adjustment</h2>
 
        -- UNIQUES
        --E.G usement
        --ALTER TABLE "schema_name"."table_name" 
        --    ADD CONSTRAINT "uc_key_name" // uc used because with this we understand this key is unique constraint
        --    UNIQUE ("key");
        
        ALTER TABLE "hrms_schema"."users" 
            ADD CONSTRAINT "uc_users_email" 
            UNIQUE ("email");

        ALTER TABLE "hrms_schema"."candidates" 
            ADD CONSTRAINT "uc_candidates_nationality_id" 
            UNIQUE ("nationality_id");

        ALTER TABLE "hrms_schema"."activation_codes" 
            ADD CONSTRAINT "uc_activation_codes" 
            UNIQUE ("activation_code");

        ALTER TABLE "hrms_schema"."job_titles" 
            ADD CONSTRAINT "uc_job_titles_title" 
            UNIQUE ("title");

<h2>Foreign Keys Adjustment</h2>

        -- FOREIGN KEYS
        --E.G usement
        --ALTER TABLE "schema_name"."foreign_table_name" 
        --    ADD CONSTRAINT "fk_key_name" // uc used because with this we understand this key is unique constraint
        --    FOREIGN KEY ("foreign_key_from_foreign_table")
        --    REFERENCES "schema_name"."referenced_table_name" ( "reference_key_from_referenced_table");

        ALTER TABLE "hrms_schema"."activation_codes_candidates" 
            ADD CONSTRAINT "fk_activation_code_candidates" 
            FOREIGN KEY ("candidate_id") 
            REFERENCES "hrms_schema"."candidates" ("id");

        ALTER TABLE "hrms_schema"."activation_codes_employers" 
            ADD CONSTRAINT "fk_activation_code_employers" 
            FOREIGN KEY ("employer_id") 
            REFERENCES "hrms_schema"."employers" ("id");

        ALTER TABLE "hrms_schema"."activation_codes" 
            ADD CONSTRAINT "fk_activation_codes_activation_code_candidates" 
            FOREIGN KEY ("id") 
            REFERENCES "hrms_schema"."activation_codes_candidates" ("id");

        ALTER TABLE "hrms_schema"."activation_codes" 
            ADD CONSTRAINT "fk_activation_codes_activation_code_employers" 
            FOREIGN KEY ("id") 
            REFERENCES "hrms_schema"."activation_codes_employers" ("id");

        ALTER TABLE "hrms_schema"."candidates" 
            ADD CONSTRAINT "fk_candidates_users" 
            FOREIGN KEY ("user_id") 
            REFERENCES "hrms_schema"."users" ("id");

        ALTER TABLE "hrms_schema"."hrms_personels" 
            ADD CONSTRAINT "fk_hrms_personels_users" 
            FOREIGN KEY ("user_id") 
            REFERENCES "hrms_schema"."users" ("id");

        ALTER TABLE "hrms_schema"."employers" 
            ADD CONSTRAINT "fk_employers_users" 
            FOREIGN KEY ("user_id") 
            REFERENCES "hrms_schema"."users" ("id");

        ALTER TABLE "hrms_schema"."activation_status_dashboard_employers" 
            ADD CONSTRAINT "fk_activation_status_dashboard_employerss" 
            FOREIGN KEY ("confirmed_system_personel_id") 
            REFERENCES "hrms_schema"."hrms_personels" ("id");

        ALTER TABLE "hrms_schema"."activation_status_dashboard_employers" 
            ADD CONSTRAINT "fk_activation_status_dashboard_employers" 
            FOREIGN KEY ("employer_id") 
            REFERENCES "hrms_schema"."employers" ("id");
