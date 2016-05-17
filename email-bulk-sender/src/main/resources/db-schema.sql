
/* TODO: Update / Complete */

CREATE TABLE email
(
  id bigint NOT NULL,
  body character varying(255),
  sender character varying(255),
  subject character varying(255),
  CONSTRAINT email_pkey PRIMARY KEY (id)
)


CREATE TABLE email_attachments_paths
(
  email_id bigint NOT NULL,
  attachments_paths character varying(255),
  CONSTRAINT fk_to_email FOREIGN KEY (email_id)
      REFERENCES email (id)
)

CREATE TABLE bulk_email
(
  id bigint NOT NULL,
  uuid character varying(255),
  email_id bigint,
  CONSTRAINT bulk_email_pkey PRIMARY KEY (id),
  CONSTRAINT fk_to_email FOREIGN KEY (email_id)
      REFERENCES email (id)
)

CREATE TABLE bulk_email_recipients
(
  bulk_email_id bigint NOT NULL,
  recipients character varying(255),
  CONSTRAINT fk_to_bulk_email FOREIGN KEY (bulk_email_id)
      REFERENCES bulk_email (id)
)
